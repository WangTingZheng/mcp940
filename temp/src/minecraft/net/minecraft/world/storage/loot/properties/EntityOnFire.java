package net.minecraft.world.storage.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EntityOnFire implements EntityProperty {
   private final boolean field_186659_a;

   public EntityOnFire(boolean p_i46613_1_) {
      this.field_186659_a = p_i46613_1_;
   }

   public boolean func_186657_a(Random p_186657_1_, Entity p_186657_2_) {
      return p_186657_2_.func_70027_ad() == this.field_186659_a;
   }

   public static class Serializer extends EntityProperty.Serializer<EntityOnFire> {
      protected Serializer() {
         super(new ResourceLocation("on_fire"), EntityOnFire.class);
      }

      public JsonElement func_186650_a(EntityOnFire p_186650_1_, JsonSerializationContext p_186650_2_) {
         return new JsonPrimitive(p_186650_1_.field_186659_a);
      }

      public EntityOnFire func_186652_a(JsonElement p_186652_1_, JsonDeserializationContext p_186652_2_) {
         return new EntityOnFire(JsonUtils.func_151216_b(p_186652_1_, "on_fire"));
      }
   }
}
