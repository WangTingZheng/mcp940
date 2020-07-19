package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Variant {
   private final ResourceLocation field_188050_a;
   private final ModelRotation field_188051_b;
   private final boolean field_188052_c;
   private final int field_188053_d;

   public Variant(ResourceLocation p_i46567_1_, ModelRotation p_i46567_2_, boolean p_i46567_3_, int p_i46567_4_) {
      this.field_188050_a = p_i46567_1_;
      this.field_188051_b = p_i46567_2_;
      this.field_188052_c = p_i46567_3_;
      this.field_188053_d = p_i46567_4_;
   }

   public ResourceLocation func_188046_a() {
      return this.field_188050_a;
   }

   public ModelRotation func_188048_b() {
      return this.field_188051_b;
   }

   public boolean func_188049_c() {
      return this.field_188052_c;
   }

   public int func_188047_d() {
      return this.field_188053_d;
   }

   public String toString() {
      return "Variant{modelLocation=" + this.field_188050_a + ", rotation=" + this.field_188051_b + ", uvLock=" + this.field_188052_c + ", weight=" + this.field_188053_d + '}';
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Variant)) {
         return false;
      } else {
         Variant variant = (Variant)p_equals_1_;
         return this.field_188050_a.equals(variant.field_188050_a) && this.field_188051_b == variant.field_188051_b && this.field_188052_c == variant.field_188052_c && this.field_188053_d == variant.field_188053_d;
      }
   }

   public int hashCode() {
      int i = this.field_188050_a.hashCode();
      i = 31 * i + this.field_188051_b.hashCode();
      i = 31 * i + this.field_188052_c.hashCode();
      i = 31 * i + this.field_188053_d;
      return i;
   }

   public static class Deserializer implements JsonDeserializer<Variant> {
      public Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         String s = this.func_188043_b(jsonobject);
         ModelRotation modelrotation = this.func_188042_a(jsonobject);
         boolean flag = this.func_188044_d(jsonobject);
         int i = this.func_188045_c(jsonobject);
         return new Variant(this.func_188041_a(s), modelrotation, flag, i);
      }

      private ResourceLocation func_188041_a(String p_188041_1_) {
         ResourceLocation resourcelocation = new ResourceLocation(p_188041_1_);
         resourcelocation = new ResourceLocation(resourcelocation.func_110624_b(), "block/" + resourcelocation.func_110623_a());
         return resourcelocation;
      }

      private boolean func_188044_d(JsonObject p_188044_1_) {
         return JsonUtils.func_151209_a(p_188044_1_, "uvlock", false);
      }

      protected ModelRotation func_188042_a(JsonObject p_188042_1_) {
         int i = JsonUtils.func_151208_a(p_188042_1_, "x", 0);
         int j = JsonUtils.func_151208_a(p_188042_1_, "y", 0);
         ModelRotation modelrotation = ModelRotation.func_177524_a(i, j);
         if (modelrotation == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
         } else {
            return modelrotation;
         }
      }

      protected String func_188043_b(JsonObject p_188043_1_) {
         return JsonUtils.func_151200_h(p_188043_1_, "model");
      }

      protected int func_188045_c(JsonObject p_188045_1_) {
         int i = JsonUtils.func_151208_a(p_188045_1_, "weight", 1);
         if (i < 1) {
            throw new JsonParseException("Invalid weight " + i + " found, expected integer >= 1");
         } else {
            return i;
         }
      }
   }
}
