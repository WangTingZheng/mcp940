package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetCount extends LootFunction {
   private final RandomValueRange field_186568_a;

   public SetCount(LootCondition[] p_i46623_1_, RandomValueRange p_i46623_2_) {
      super(p_i46623_1_);
      this.field_186568_a = p_i46623_2_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      p_186553_1_.func_190920_e(this.field_186568_a.func_186511_a(p_186553_2_));
      return p_186553_1_;
   }

   public static class Serializer extends LootFunction.Serializer<SetCount> {
      protected Serializer() {
         super(new ResourceLocation("set_count"), SetCount.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, SetCount p_186532_2_, JsonSerializationContext p_186532_3_) {
         p_186532_1_.add("count", p_186532_3_.serialize(p_186532_2_.field_186568_a));
      }

      public SetCount func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         return new SetCount(p_186530_3_, (RandomValueRange)JsonUtils.func_188174_a(p_186530_1_, "count", p_186530_2_, RandomValueRange.class));
      }
   }
}
