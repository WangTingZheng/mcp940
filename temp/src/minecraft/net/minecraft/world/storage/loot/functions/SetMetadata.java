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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetMetadata extends LootFunction {
   private static final Logger field_186572_a = LogManager.getLogger();
   private final RandomValueRange field_186573_b;

   public SetMetadata(LootCondition[] p_i46621_1_, RandomValueRange p_i46621_2_) {
      super(p_i46621_1_);
      this.field_186573_b = p_i46621_2_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      if (p_186553_1_.func_77984_f()) {
         field_186572_a.warn("Couldn't set data of loot item {}", (Object)p_186553_1_);
      } else {
         p_186553_1_.func_77964_b(this.field_186573_b.func_186511_a(p_186553_2_));
      }

      return p_186553_1_;
   }

   public static class Serializer extends LootFunction.Serializer<SetMetadata> {
      protected Serializer() {
         super(new ResourceLocation("set_data"), SetMetadata.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, SetMetadata p_186532_2_, JsonSerializationContext p_186532_3_) {
         p_186532_1_.add("data", p_186532_3_.serialize(p_186532_2_.field_186573_b));
      }

      public SetMetadata func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         return new SetMetadata(p_186530_3_, (RandomValueRange)JsonUtils.func_188174_a(p_186530_1_, "data", p_186530_2_, RandomValueRange.class));
      }
   }
}
