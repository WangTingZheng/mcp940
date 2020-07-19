package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class EnchantWithLevels extends LootFunction {
   private final RandomValueRange field_186577_a;
   private final boolean field_186578_b;

   public EnchantWithLevels(LootCondition[] p_i46627_1_, RandomValueRange p_i46627_2_, boolean p_i46627_3_) {
      super(p_i46627_1_);
      this.field_186577_a = p_i46627_2_;
      this.field_186578_b = p_i46627_3_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      return EnchantmentHelper.func_77504_a(p_186553_2_, p_186553_1_, this.field_186577_a.func_186511_a(p_186553_2_), this.field_186578_b);
   }

   public static class Serializer extends LootFunction.Serializer<EnchantWithLevels> {
      public Serializer() {
         super(new ResourceLocation("enchant_with_levels"), EnchantWithLevels.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, EnchantWithLevels p_186532_2_, JsonSerializationContext p_186532_3_) {
         p_186532_1_.add("levels", p_186532_3_.serialize(p_186532_2_.field_186577_a));
         p_186532_1_.addProperty("treasure", Boolean.valueOf(p_186532_2_.field_186578_b));
      }

      public EnchantWithLevels func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.func_188174_a(p_186530_1_, "levels", p_186530_2_, RandomValueRange.class);
         boolean flag = JsonUtils.func_151209_a(p_186530_1_, "treasure", false);
         return new EnchantWithLevels(p_186530_3_, randomvaluerange, flag);
      }
   }
}
