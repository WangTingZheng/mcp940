package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomly extends LootFunction
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Enchantment> enchantments;

    public EnchantRandomly(LootCondition[] conditionsIn, @Nullable List<Enchantment> enchantmentsIn)
    {
        super(conditionsIn);
        this.enchantments = enchantmentsIn == null ? Collections.emptyList() : enchantmentsIn;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
        Enchantment enchantment;

        if (this.enchantments.isEmpty())
        {
            List<Enchantment> list = Lists.<Enchantment>newArrayList();

            for (Enchantment enchantment1 : Enchantment.REGISTRY)
            {
                if (stack.getItem() == Items.BOOK || enchantment1.canApply(stack))
                {
                    list.add(enchantment1);
                }
            }

            if (list.isEmpty())
            {
                LOGGER.warn("Couldn't find a compatible enchantment for {}", (Object)stack);
                return stack;
            }

            enchantment = list.get(rand.nextInt(list.size()));
        }
        else
        {
            enchantment = this.enchantments.get(rand.nextInt(this.enchantments.size()));
        }

        int i = MathHelper.getInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());

        if (stack.getItem() == Items.BOOK)
        {
            stack = new ItemStack(Items.ENCHANTED_BOOK);
            ItemEnchantedBook.addEnchantment(stack, new EnchantmentData(enchantment, i));
        }
        else
        {
            stack.addEnchantment(enchantment, i);
        }

        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<EnchantRandomly>
    {
        public Serializer()
        {
            super(new ResourceLocation("enchant_randomly"), EnchantRandomly.class);
        }

        public void serialize(JsonObject object, EnchantRandomly functionClazz, JsonSerializationContext serializationContext)
        {
            if (!functionClazz.enchantments.isEmpty())
            {
                JsonArray jsonarray = new JsonArray();

                for (Enchantment enchantment : functionClazz.enchantments)
                {
                    ResourceLocation resourcelocation = Enchantment.REGISTRY.getNameForObject(enchantment);

                    if (resourcelocation == null)
                    {
                        throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchantment);
                    }

                    jsonarray.add(new JsonPrimitive(resourcelocation.toString()));
                }

                object.add("enchantments", jsonarray);
            }
        }

        public EnchantRandomly deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
        {
            List<Enchantment> list = Lists.<Enchantment>newArrayList();

            if (object.has("enchantments"))
            {
                for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "enchantments"))
                {
                    String s = JsonUtils.getString(jsonelement, "enchantment");
                    Enchantment enchantment = Enchantment.REGISTRY.getObject(new ResourceLocation(s));

                    if (enchantment == null)
                    {
                        throw new JsonSyntaxException("Unknown enchantment '" + s + "'");
                    }

                    list.add(enchantment);
                }
            }

            return new EnchantRandomly(conditionsIn, list);
        }
    }
}
