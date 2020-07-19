package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class BlockPart {
   public final Vector3f field_178241_a;
   public final Vector3f field_178239_b;
   public final Map<EnumFacing, BlockPartFace> field_178240_c;
   public final BlockPartRotation field_178237_d;
   public final boolean field_178238_e;

   public BlockPart(Vector3f p_i46231_1_, Vector3f p_i46231_2_, Map<EnumFacing, BlockPartFace> p_i46231_3_, @Nullable BlockPartRotation p_i46231_4_, boolean p_i46231_5_) {
      this.field_178241_a = p_i46231_1_;
      this.field_178239_b = p_i46231_2_;
      this.field_178240_c = p_i46231_3_;
      this.field_178237_d = p_i46231_4_;
      this.field_178238_e = p_i46231_5_;
      this.func_178235_a();
   }

   private void func_178235_a() {
      for(Entry<EnumFacing, BlockPartFace> entry : this.field_178240_c.entrySet()) {
         float[] afloat = this.func_178236_a(entry.getKey());
         (entry.getValue()).field_178243_e.func_178349_a(afloat);
      }

   }

   private float[] func_178236_a(EnumFacing p_178236_1_) {
      switch(p_178236_1_) {
      case DOWN:
         return new float[]{this.field_178241_a.x, 16.0F - this.field_178239_b.z, this.field_178239_b.x, 16.0F - this.field_178241_a.z};
      case UP:
         return new float[]{this.field_178241_a.x, this.field_178241_a.z, this.field_178239_b.x, this.field_178239_b.z};
      case NORTH:
      default:
         return new float[]{16.0F - this.field_178239_b.x, 16.0F - this.field_178239_b.y, 16.0F - this.field_178241_a.x, 16.0F - this.field_178241_a.y};
      case SOUTH:
         return new float[]{this.field_178241_a.x, 16.0F - this.field_178239_b.y, this.field_178239_b.x, 16.0F - this.field_178241_a.y};
      case WEST:
         return new float[]{this.field_178241_a.z, 16.0F - this.field_178239_b.y, this.field_178239_b.z, 16.0F - this.field_178241_a.y};
      case EAST:
         return new float[]{16.0F - this.field_178239_b.z, 16.0F - this.field_178239_b.y, 16.0F - this.field_178241_a.z, 16.0F - this.field_178241_a.y};
      }
   }

   static class Deserializer implements JsonDeserializer<BlockPart> {
      public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         Vector3f vector3f = this.func_178249_e(jsonobject);
         Vector3f vector3f1 = this.func_178247_d(jsonobject);
         BlockPartRotation blockpartrotation = this.func_178256_a(jsonobject);
         Map<EnumFacing, BlockPartFace> map = this.func_178250_a(p_deserialize_3_, jsonobject);
         if (jsonobject.has("shade") && !JsonUtils.func_180199_c(jsonobject, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean flag = JsonUtils.func_151209_a(jsonobject, "shade", true);
            return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
         }
      }

      @Nullable
      private BlockPartRotation func_178256_a(JsonObject p_178256_1_) {
         BlockPartRotation blockpartrotation = null;
         if (p_178256_1_.has("rotation")) {
            JsonObject jsonobject = JsonUtils.func_152754_s(p_178256_1_, "rotation");
            Vector3f vector3f = this.func_178251_a(jsonobject, "origin");
            vector3f.scale(0.0625F);
            EnumFacing.Axis enumfacing$axis = this.func_178252_c(jsonobject);
            float f = this.func_178255_b(jsonobject);
            boolean flag = JsonUtils.func_151209_a(jsonobject, "rescale", false);
            blockpartrotation = new BlockPartRotation(vector3f, enumfacing$axis, f, flag);
         }

         return blockpartrotation;
      }

      private float func_178255_b(JsonObject p_178255_1_) {
         float f = JsonUtils.func_151217_k(p_178255_1_, "angle");
         if (f != 0.0F && MathHelper.func_76135_e(f) != 22.5F && MathHelper.func_76135_e(f) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return f;
         }
      }

      private EnumFacing.Axis func_178252_c(JsonObject p_178252_1_) {
         String s = JsonUtils.func_151200_h(p_178252_1_, "axis");
         EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.func_176717_a(s.toLowerCase(Locale.ROOT));
         if (enumfacing$axis == null) {
            throw new JsonParseException("Invalid rotation axis: " + s);
         } else {
            return enumfacing$axis;
         }
      }

      private Map<EnumFacing, BlockPartFace> func_178250_a(JsonDeserializationContext p_178250_1_, JsonObject p_178250_2_) {
         Map<EnumFacing, BlockPartFace> map = this.func_178253_b(p_178250_1_, p_178250_2_);
         if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return map;
         }
      }

      private Map<EnumFacing, BlockPartFace> func_178253_b(JsonDeserializationContext p_178253_1_, JsonObject p_178253_2_) {
         Map<EnumFacing, BlockPartFace> map = Maps.newEnumMap(EnumFacing.class);
         JsonObject jsonobject = JsonUtils.func_152754_s(p_178253_2_, "faces");

         for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            EnumFacing enumfacing = this.func_178248_a(entry.getKey());
            map.put(enumfacing, (BlockPartFace)p_178253_1_.deserialize(entry.getValue(), BlockPartFace.class));
         }

         return map;
      }

      private EnumFacing func_178248_a(String p_178248_1_) {
         EnumFacing enumfacing = EnumFacing.func_176739_a(p_178248_1_);
         if (enumfacing == null) {
            throw new JsonParseException("Unknown facing: " + p_178248_1_);
         } else {
            return enumfacing;
         }
      }

      private Vector3f func_178247_d(JsonObject p_178247_1_) {
         Vector3f vector3f = this.func_178251_a(p_178247_1_, "to");
         if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F) {
            return vector3f;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
         }
      }

      private Vector3f func_178249_e(JsonObject p_178249_1_) {
         Vector3f vector3f = this.func_178251_a(p_178249_1_, "from");
         if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F) {
            return vector3f;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
         }
      }

      private Vector3f func_178251_a(JsonObject p_178251_1_, String p_178251_2_) {
         JsonArray jsonarray = JsonUtils.func_151214_t(p_178251_1_, p_178251_2_);
         if (jsonarray.size() != 3) {
            throw new JsonParseException("Expected 3 " + p_178251_2_ + " values, found: " + jsonarray.size());
         } else {
            float[] afloat = new float[3];

            for(int i = 0; i < afloat.length; ++i) {
               afloat[i] = JsonUtils.func_151220_d(jsonarray.get(i), p_178251_2_ + "[" + i + "]");
            }

            return new Vector3f(afloat[0], afloat[1], afloat[2]);
         }
      }
   }
}
