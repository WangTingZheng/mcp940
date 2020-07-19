package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class RandomChance implements LootCondition {
   private final float field_186630_a;

   public RandomChance(float p_i46615_1_) {
      this.field_186630_a = p_i46615_1_;
   }

   public boolean func_186618_a(Random p_186618_1_, LootContext p_186618_2_) {
      return p_186618_1_.nextFloat() < this.field_186630_a;
   }

   public static class Serializer extends LootCondition.Serializer<RandomChance> {
      protected Serializer() {
         super(new ResourceLocation("random_chance"), RandomChance.class);
      }

      public void func_186605_a(JsonObject p_186605_1_, RandomChance p_186605_2_, JsonSerializationContext p_186605_3_) {
         p_186605_1_.addProperty("chance", Float.valueOf(p_186605_2_.field_186630_a));
      }

      public RandomChance func_186603_b(JsonObject p_186603_1_, JsonDeserializationContext p_186603_2_) {
         return new RandomChance(JsonUtils.func_151217_k(p_186603_1_, "chance"));
      }
   }
}
