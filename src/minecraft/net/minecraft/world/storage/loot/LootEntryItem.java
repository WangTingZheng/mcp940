package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class LootEntryItem extends LootEntry
{
    protected final Item item;
    protected final LootFunction[] functions;

    public LootEntryItem(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn, LootCondition[] conditionsIn)
    {
        super(weightIn, qualityIn, conditionsIn);
        this.item = itemIn;
        this.functions = functionsIn;
    }

    public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context)
    {
        ItemStack itemstack = new ItemStack(this.item);

        for (LootFunction lootfunction : this.functions)
        {
            if (LootConditionManager.testAllConditions(lootfunction.getConditions(), rand, context))
            {
                itemstack = lootfunction.apply(itemstack, rand, context);
            }
        }

        if (!itemstack.isEmpty())
        {
            if (itemstack.getCount() < this.item.getItemStackLimit())
            {
                stacks.add(itemstack);
            }
            else
            {
                int i = itemstack.getCount();

                while (i > 0)
                {
                    ItemStack itemstack1 = itemstack.copy();
                    itemstack1.setCount(Math.min(itemstack.getMaxStackSize(), i));
                    i -= itemstack1.getCount();
                    stacks.add(itemstack1);
                }
            }
        }
    }

    protected void serialize(JsonObject json, JsonSerializationContext context)
    {
        if (this.functions != null && this.functions.length > 0)
        {
            json.add("functions", context.serialize(this.functions));
        }

        ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(this.item);

        if (resourcelocation == null)
        {
            throw new IllegalArgumentException("Can't serialize unknown item " + this.item);
        }
        else
        {
            json.addProperty("name", resourcelocation.toString());
        }
    }

    public static LootEntryItem deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn)
    {
        Item item = JsonUtils.getItem(object, "name");
        LootFunction[] alootfunction;

        if (object.has("functions"))
        {
            alootfunction = (LootFunction[])JsonUtils.deserializeClass(object, "functions", deserializationContext, LootFunction[].class);
        }
        else
        {
            alootfunction = new LootFunction[0];
        }

        return new LootEntryItem(item, weightIn, qualityIn, alootfunction, conditionsIn);
    }
}
