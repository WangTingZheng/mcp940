package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ItemPredicate
{
    public static final ItemPredicate ANY = new ItemPredicate();
    private final Item item;
    private final Integer data;
    private final MinMaxBounds count;
    private final MinMaxBounds durability;
    private final EnchantmentPredicate[] enchantments;
    private final PotionType potion;
    private final NBTPredicate nbt;

    public ItemPredicate()
    {
        this.item = null;
        this.data = null;
        this.potion = null;
        this.count = MinMaxBounds.UNBOUNDED;
        this.durability = MinMaxBounds.UNBOUNDED;
        this.enchantments = new EnchantmentPredicate[0];
        this.nbt = NBTPredicate.ANY;
    }

    public ItemPredicate(@Nullable Item item, @Nullable Integer data, MinMaxBounds count, MinMaxBounds durability, EnchantmentPredicate[] enchantments, @Nullable PotionType potion, NBTPredicate nbt)
    {
        this.item = item;
        this.data = data;
        this.count = count;
        this.durability = durability;
        this.enchantments = enchantments;
        this.potion = potion;
        this.nbt = nbt;
    }

    public boolean test(ItemStack item)
    {
        if (this.item != null && item.getItem() != this.item)
        {
            return false;
        }
        else if (this.data != null && item.getMetadata() != this.data.intValue())
        {
            return false;
        }
        else if (!this.count.test((float)item.getCount()))
        {
            return false;
        }
        else if (this.durability != MinMaxBounds.UNBOUNDED && !item.isItemStackDamageable())
        {
            return false;
        }
        else if (!this.durability.test((float)(item.getMaxDamage() - item.getItemDamage())))
        {
            return false;
        }
        else if (!this.nbt.test(item))
        {
            return false;
        }
        else
        {
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(item);

            for (int i = 0; i < this.enchantments.length; ++i)
            {
                if (!this.enchantments[i].test(map))
                {
                    return false;
                }
            }

            PotionType potiontype = PotionUtils.getPotionFromItem(item);

            if (this.potion != null && this.potion != potiontype)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    public static ItemPredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "item");
            MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(jsonobject.get("count"));
            MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(jsonobject.get("durability"));
            Integer integer = jsonobject.has("data") ? JsonUtils.getInt(jsonobject, "data") : null;
            NBTPredicate nbtpredicate = NBTPredicate.deserialize(jsonobject.get("nbt"));
            Item item = null;

            if (jsonobject.has("item"))
            {
                ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "item"));
                item = Item.REGISTRY.getObject(resourcelocation);

                if (item == null)
                {
                    throw new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                }
            }

            EnchantmentPredicate[] aenchantmentpredicate = EnchantmentPredicate.deserializeArray(jsonobject.get("enchantments"));
            PotionType potiontype = null;

            if (jsonobject.has("potion"))
            {
                ResourceLocation resourcelocation1 = new ResourceLocation(JsonUtils.getString(jsonobject, "potion"));

                if (!PotionType.REGISTRY.containsKey(resourcelocation1))
                {
                    throw new JsonSyntaxException("Unknown potion '" + resourcelocation1 + "'");
                }

                potiontype = PotionType.REGISTRY.getObject(resourcelocation1);
            }

            return new ItemPredicate(item, integer, minmaxbounds, minmaxbounds1, aenchantmentpredicate, potiontype, nbtpredicate);
        }
        else
        {
            return ANY;
        }
    }

    public static ItemPredicate[] deserializeArray(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonArray jsonarray = JsonUtils.getJsonArray(element, "items");
            ItemPredicate[] aitempredicate = new ItemPredicate[jsonarray.size()];

            for (int i = 0; i < aitempredicate.length; ++i)
            {
                aitempredicate[i] = deserialize(jsonarray.get(i));
            }

            return aitempredicate;
        }
        else
        {
            return new ItemPredicate[0];
        }
    }
}
