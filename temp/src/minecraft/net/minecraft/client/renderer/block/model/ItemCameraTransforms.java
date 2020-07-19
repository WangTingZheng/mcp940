package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Quaternion;

public class ItemCameraTransforms {
   public static final ItemCameraTransforms field_178357_a = new ItemCameraTransforms();
   public static float field_181690_b;
   public static float field_181691_c;
   public static float field_181692_d;
   public static float field_181693_e;
   public static float field_181694_f;
   public static float field_181695_g;
   public static float field_181696_h;
   public static float field_181697_i;
   public static float field_181698_j;
   public final ItemTransformVec3f field_188036_k;
   public final ItemTransformVec3f field_188037_l;
   public final ItemTransformVec3f field_188038_m;
   public final ItemTransformVec3f field_188039_n;
   public final ItemTransformVec3f field_178353_d;
   public final ItemTransformVec3f field_178354_e;
   public final ItemTransformVec3f field_181699_o;
   public final ItemTransformVec3f field_181700_p;

   private ItemCameraTransforms() {
      this(ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a);
   }

   public ItemCameraTransforms(ItemCameraTransforms p_i46443_1_) {
      this.field_188036_k = p_i46443_1_.field_188036_k;
      this.field_188037_l = p_i46443_1_.field_188037_l;
      this.field_188038_m = p_i46443_1_.field_188038_m;
      this.field_188039_n = p_i46443_1_.field_188039_n;
      this.field_178353_d = p_i46443_1_.field_178353_d;
      this.field_178354_e = p_i46443_1_.field_178354_e;
      this.field_181699_o = p_i46443_1_.field_181699_o;
      this.field_181700_p = p_i46443_1_.field_181700_p;
   }

   public ItemCameraTransforms(ItemTransformVec3f p_i46569_1_, ItemTransformVec3f p_i46569_2_, ItemTransformVec3f p_i46569_3_, ItemTransformVec3f p_i46569_4_, ItemTransformVec3f p_i46569_5_, ItemTransformVec3f p_i46569_6_, ItemTransformVec3f p_i46569_7_, ItemTransformVec3f p_i46569_8_) {
      this.field_188036_k = p_i46569_1_;
      this.field_188037_l = p_i46569_2_;
      this.field_188038_m = p_i46569_3_;
      this.field_188039_n = p_i46569_4_;
      this.field_178353_d = p_i46569_5_;
      this.field_178354_e = p_i46569_6_;
      this.field_181699_o = p_i46569_7_;
      this.field_181700_p = p_i46569_8_;
   }

   public void func_181689_a(ItemCameraTransforms.TransformType p_181689_1_) {
      func_188034_a(this.func_181688_b(p_181689_1_), false);
   }

   public static void func_188034_a(ItemTransformVec3f p_188034_0_, boolean p_188034_1_) {
      if (p_188034_0_ != ItemTransformVec3f.field_178366_a) {
         int i = p_188034_1_ ? -1 : 1;
         GlStateManager.func_179109_b((float)i * (field_181690_b + p_188034_0_.field_178365_c.x), field_181691_c + p_188034_0_.field_178365_c.y, field_181692_d + p_188034_0_.field_178365_c.z);
         float f = field_181693_e + p_188034_0_.field_178364_b.x;
         float f1 = field_181694_f + p_188034_0_.field_178364_b.y;
         float f2 = field_181695_g + p_188034_0_.field_178364_b.z;
         if (p_188034_1_) {
            f1 = -f1;
            f2 = -f2;
         }

         GlStateManager.func_187444_a(func_188035_a(f, f1, f2));
         GlStateManager.func_179152_a(field_181696_h + p_188034_0_.field_178363_d.x, field_181697_i + p_188034_0_.field_178363_d.y, field_181698_j + p_188034_0_.field_178363_d.z);
      }
   }

   private static Quaternion func_188035_a(float p_188035_0_, float p_188035_1_, float p_188035_2_) {
      float f = p_188035_0_ * 0.017453292F;
      float f1 = p_188035_1_ * 0.017453292F;
      float f2 = p_188035_2_ * 0.017453292F;
      float f3 = MathHelper.func_76126_a(0.5F * f);
      float f4 = MathHelper.func_76134_b(0.5F * f);
      float f5 = MathHelper.func_76126_a(0.5F * f1);
      float f6 = MathHelper.func_76134_b(0.5F * f1);
      float f7 = MathHelper.func_76126_a(0.5F * f2);
      float f8 = MathHelper.func_76134_b(0.5F * f2);
      return new Quaternion(f3 * f6 * f8 + f4 * f5 * f7, f4 * f5 * f8 - f3 * f6 * f7, f3 * f5 * f8 + f4 * f6 * f7, f4 * f6 * f8 - f3 * f5 * f7);
   }

   public ItemTransformVec3f func_181688_b(ItemCameraTransforms.TransformType p_181688_1_) {
      switch(p_181688_1_) {
      case THIRD_PERSON_LEFT_HAND:
         return this.field_188036_k;
      case THIRD_PERSON_RIGHT_HAND:
         return this.field_188037_l;
      case FIRST_PERSON_LEFT_HAND:
         return this.field_188038_m;
      case FIRST_PERSON_RIGHT_HAND:
         return this.field_188039_n;
      case HEAD:
         return this.field_178353_d;
      case GUI:
         return this.field_178354_e;
      case GROUND:
         return this.field_181699_o;
      case FIXED:
         return this.field_181700_p;
      default:
         return ItemTransformVec3f.field_178366_a;
      }
   }

   public boolean func_181687_c(ItemCameraTransforms.TransformType p_181687_1_) {
      return this.func_181688_b(p_181687_1_) != ItemTransformVec3f.field_178366_a;
   }

   static class Deserializer implements JsonDeserializer<ItemCameraTransforms> {
      public ItemCameraTransforms deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         ItemTransformVec3f itemtransformvec3f = this.func_181683_a(p_deserialize_3_, jsonobject, "thirdperson_righthand");
         ItemTransformVec3f itemtransformvec3f1 = this.func_181683_a(p_deserialize_3_, jsonobject, "thirdperson_lefthand");
         if (itemtransformvec3f1 == ItemTransformVec3f.field_178366_a) {
            itemtransformvec3f1 = itemtransformvec3f;
         }

         ItemTransformVec3f itemtransformvec3f2 = this.func_181683_a(p_deserialize_3_, jsonobject, "firstperson_righthand");
         ItemTransformVec3f itemtransformvec3f3 = this.func_181683_a(p_deserialize_3_, jsonobject, "firstperson_lefthand");
         if (itemtransformvec3f3 == ItemTransformVec3f.field_178366_a) {
            itemtransformvec3f3 = itemtransformvec3f2;
         }

         ItemTransformVec3f itemtransformvec3f4 = this.func_181683_a(p_deserialize_3_, jsonobject, "head");
         ItemTransformVec3f itemtransformvec3f5 = this.func_181683_a(p_deserialize_3_, jsonobject, "gui");
         ItemTransformVec3f itemtransformvec3f6 = this.func_181683_a(p_deserialize_3_, jsonobject, "ground");
         ItemTransformVec3f itemtransformvec3f7 = this.func_181683_a(p_deserialize_3_, jsonobject, "fixed");
         return new ItemCameraTransforms(itemtransformvec3f1, itemtransformvec3f, itemtransformvec3f3, itemtransformvec3f2, itemtransformvec3f4, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7);
      }

      private ItemTransformVec3f func_181683_a(JsonDeserializationContext p_181683_1_, JsonObject p_181683_2_, String p_181683_3_) {
         return p_181683_2_.has(p_181683_3_) ? (ItemTransformVec3f)p_181683_1_.deserialize(p_181683_2_.get(p_181683_3_), ItemTransformVec3f.class) : ItemTransformVec3f.field_178366_a;
      }
   }

   public static enum TransformType {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;
   }
}
