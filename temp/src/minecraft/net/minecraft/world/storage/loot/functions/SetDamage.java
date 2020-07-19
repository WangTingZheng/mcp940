package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDamage extends LootFunction {
   private static final Logger field_186565_a = LogManager.getLogger();
   private final RandomValueRange field_186566_b;

   public SetDamage(LootCondition[] p_i46622_1_, RandomValueRange p_i46622_2_) {
      super(p_i46622_1_);
      this.field_186566_b = p_i46622_2_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      if (p_186553_1_.func_77984_f()) {
         float f = 1.0F - this.field_186566_b.func_186507_b(p_186553_2_);
         p_186553_1_.func_77964_b(MathHelper.func_76141_d(f * (float)p_186553_1_.func_77958_k()));
      } else {
         field_186565_a.warn("Couldn't set damage of loot item {}", (Object)p_186553_1_);
      }

      return p_186553_1_;
   }

   public static class Serializer extends LootFunction.Serializer<SetDamage> {
      protected Serializer() {
         super(new ResourceLocation("set_damage"), SetDamage.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, SetDamage p_186532_2_, JsonSerializationContext p_186532_3_) {
         p_186532_1_.add("damage", p_186532_3_.serialize(p_186532_2_.field_186566_b));
      }

      public SetDamage func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         return new SetDamage(p_186530_3_, (RandomValueRange)JsonUtils.func_188174_a(p_186530_1_, "damage", p_186530_2_, RandomValueRange.class));
      }
   }
}
