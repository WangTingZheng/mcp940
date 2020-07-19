package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public interface LootCondition {
   boolean func_186618_a(Random var1, LootContext var2);

   public abstract static class Serializer<T extends LootCondition> {
      private final ResourceLocation field_186606_a;
      private final Class<T> field_186607_b;

      protected Serializer(ResourceLocation p_i47021_1_, Class<T> p_i47021_2_) {
         this.field_186606_a = p_i47021_1_;
         this.field_186607_b = p_i47021_2_;
      }

      public ResourceLocation func_186602_a() {
         return this.field_186606_a;
      }

      public Class<T> func_186604_b() {
         return this.field_186607_b;
      }

      public abstract void func_186605_a(JsonObject var1, T var2, JsonSerializationContext var3);

      public abstract T func_186603_b(JsonObject var1, JsonDeserializationContext var2);
   }
}
