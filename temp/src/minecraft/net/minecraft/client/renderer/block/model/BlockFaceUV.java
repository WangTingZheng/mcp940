package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;

public class BlockFaceUV {
   public float[] field_178351_a;
   public final int field_178350_b;

   public BlockFaceUV(@Nullable float[] p_i46228_1_, int p_i46228_2_) {
      this.field_178351_a = p_i46228_1_;
      this.field_178350_b = p_i46228_2_;
   }

   public float func_178348_a(int p_178348_1_) {
      if (this.field_178351_a == null) {
         throw new NullPointerException("uvs");
      } else {
         int i = this.func_178347_d(p_178348_1_);
         return i != 0 && i != 1 ? this.field_178351_a[2] : this.field_178351_a[0];
      }
   }

   public float func_178346_b(int p_178346_1_) {
      if (this.field_178351_a == null) {
         throw new NullPointerException("uvs");
      } else {
         int i = this.func_178347_d(p_178346_1_);
         return i != 0 && i != 3 ? this.field_178351_a[3] : this.field_178351_a[1];
      }
   }

   private int func_178347_d(int p_178347_1_) {
      return (p_178347_1_ + this.field_178350_b / 90) % 4;
   }

   public int func_178345_c(int p_178345_1_) {
      return (p_178345_1_ + (4 - this.field_178350_b / 90)) % 4;
   }

   public void func_178349_a(float[] p_178349_1_) {
      if (this.field_178351_a == null) {
         this.field_178351_a = p_178349_1_;
      }

   }

   static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         float[] afloat = this.func_178292_b(jsonobject);
         int i = this.func_178291_a(jsonobject);
         return new BlockFaceUV(afloat, i);
      }

      protected int func_178291_a(JsonObject p_178291_1_) {
         int i = JsonUtils.func_151208_a(p_178291_1_, "rotation", 0);
         if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
            return i;
         } else {
            throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] func_178292_b(JsonObject p_178292_1_) {
         if (!p_178292_1_.has("uv")) {
            return null;
         } else {
            JsonArray jsonarray = JsonUtils.func_151214_t(p_178292_1_, "uv");
            if (jsonarray.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
            } else {
               float[] afloat = new float[4];

               for(int i = 0; i < afloat.length; ++i) {
                  afloat[i] = JsonUtils.func_151220_d(jsonarray.get(i), "uv[" + i + "]");
               }

               return afloat;
            }
         }
      }
   }
}
