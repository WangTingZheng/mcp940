package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final LootTable EMPTY_LOOT_TABLE = new LootTable(new LootPool[0]);
    private final LootPool[] pools;

    public LootTable(LootPool[] poolsIn)
    {
        this.pools = poolsIn;
    }

    public List<ItemStack> generateLootForPools(Random rand, LootContext context)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        if (context.addLootTable(this))
        {
            for (LootPool lootpool : this.pools)
            {
                lootpool.generateLoot(list, rand, context);
            }

            context.removeLootTable(this);
        }
        else
        {
            LOGGER.warn("Detected infinite loop in loot tables");
        }

        return list;
    }

    public void fillInventory(IInventory inventory, Random rand, LootContext context)
    {
        List<ItemStack> list = this.generateLootForPools(rand, context);
        List<Integer> list1 = this.getEmptySlotsRandomized(inventory, rand);
        this.shuffleItems(list, list1.size(), rand);

        for (ItemStack itemstack : list)
        {
            if (list1.isEmpty())
            {
                LOGGER.warn("Tried to over-fill a container");
                return;
            }

            if (itemstack.isEmpty())
            {
                inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), ItemStack.EMPTY);
            }
            else
            {
                inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), itemstack);
            }
        }
    }

    /**
     * shuffles items by changing their order and splitting stacks
     */
    private void shuffleItems(List<ItemStack> stacks, int p_186463_2_, Random rand)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();
        Iterator<ItemStack> iterator = stacks.iterator();

        while (iterator.hasNext())
        {
            ItemStack itemstack = iterator.next();

            if (itemstack.isEmpty())
            {
                iterator.remove();
            }
            else if (itemstack.getCount() > 1)
            {
                list.add(itemstack);
                iterator.remove();
            }
        }

        p_186463_2_ = p_186463_2_ - stacks.size();

        while (p_186463_2_ > 0 && !list.isEmpty())
        {
            ItemStack itemstack2 = list.remove(MathHelper.getInt(rand, 0, list.size() - 1));
            int i = MathHelper.getInt(rand, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.splitStack(i);

            if (itemstack2.getCount() > 1 && rand.nextBoolean())
            {
                list.add(itemstack2);
            }
            else
            {
                stacks.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && rand.nextBoolean())
            {
                list.add(itemstack1);
            }
            else
            {
                stacks.add(itemstack1);
            }
        }

        stacks.addAll(list);
        Collections.shuffle(stacks, rand);
    }

    private List<Integer> getEmptySlotsRandomized(IInventory inventory, Random rand)
    {
        List<Integer> list = Lists.<Integer>newArrayList();

        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i).isEmpty())
            {
                list.add(Integer.valueOf(i));
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable>
    {
        public LootTable deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "loot table");
            LootPool[] alootpool = (LootPool[])JsonUtils.deserializeClass(jsonobject, "pools", new LootPool[0], p_deserialize_3_, LootPool[].class);
            return new LootTable(alootpool);
        }

        public JsonElement serialize(LootTable p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("pools", p_serialize_3_.serialize(p_serialize_1_.pools));
            return jsonobject;
        }
    }
}
