package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryEmpty extends LootEntry
{
    public LootEntryEmpty(int weightIn, int qualityIn, LootCondition[] conditionsIn)
    {
        super(weightIn, qualityIn, conditionsIn);
    }

    public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context)
    {
    }

    protected void serialize(JsonObject json, JsonSerializationContext context)
    {
    }

    public static LootEntryEmpty deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn)
    {
        return new LootEntryEmpty(weightIn, qualityIn, conditionsIn);
    }
}
