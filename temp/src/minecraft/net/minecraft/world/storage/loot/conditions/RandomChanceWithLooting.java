package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class RandomChanceWithLooting implements LootCondition {
   private final float field_186627_a;
   private final float field_186628_b;

   public RandomChanceWithLooting(float p_i46614_1_, float p_i46614_2_) {
      this.field_186627_a = p_i46614_1_;
      this.field_186628_b = p_i46614_2_;
   }

   public boolean func_186618_a(Random p_186618_1_, LootContext p_186618_2_) {
      int i = 0;
      if (p_186618_2_.func_186492_c() instanceof EntityLivingBase) {
         i = EnchantmentHelper.func_185283_h((EntityLivingBase)p_186618_2_.func_186492_c());
      }

      return p_186618_1_.nextFloat() < this.field_186627_a + (float)i * this.field_186628_b;
   }

   public static class Serializer extends LootCondition.Serializer<RandomChanceWithLooting> {
      protected Serializer() {
         super(new ResourceLocation("random_chance_with_looting"), RandomChanceWithLooting.class);
      }

      public void func_186605_a(JsonObject p_186605_1_, RandomChanceWithLooting p_186605_2_, JsonSerializationContext p_186605_3_) {
         p_186605_1_.addProperty("chance", Float.valueOf(p_186605_2_.field_186627_a));
         p_186605_1_.addProperty("looting_multiplier", Float.valueOf(p_186605_2_.field_186628_b));
      }

      public RandomChanceWithLooting func_186603_b(JsonObject p_186603_1_, JsonDeserializationContext p_186603_2_) {
         return new RandomChanceWithLooting(JsonUtils.func_151217_k(p_186603_1_, "chance"), JsonUtils.func_151217_k(p_186603_1_, "looting_multiplier"));
      }
   }
}
