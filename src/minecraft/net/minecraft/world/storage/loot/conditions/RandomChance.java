package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class RandomChance implements LootCondition
{
    private final float chance;

    public RandomChance(float chanceIn)
    {
        this.chance = chanceIn;
    }

    public boolean testCondition(Random rand, LootContext context)
    {
        return rand.nextFloat() < this.chance;
    }

    public static class Serializer extends LootCondition.Serializer<RandomChance>
    {
        protected Serializer()
        {
            super(new ResourceLocation("random_chance"), RandomChance.class);
        }

        public void serialize(JsonObject json, RandomChance value, JsonSerializationContext context)
        {
            json.addProperty("chance", Float.valueOf(value.chance));
        }

        public RandomChance deserialize(JsonObject json, JsonDeserializationContext context)
        {
            return new RandomChance(JsonUtils.getFloat(json, "chance"));
        }
    }
}
