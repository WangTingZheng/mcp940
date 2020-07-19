package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public enum ModelRotation {
   X0_Y0(0, 0),
   X0_Y90(0, 90),
   X0_Y180(0, 180),
   X0_Y270(0, 270),
   X90_Y0(90, 0),
   X90_Y90(90, 90),
   X90_Y180(90, 180),
   X90_Y270(90, 270),
   X180_Y0(180, 0),
   X180_Y90(180, 90),
   X180_Y180(180, 180),
   X180_Y270(180, 270),
   X270_Y0(270, 0),
   X270_Y90(270, 90),
   X270_Y180(270, 180),
   X270_Y270(270, 270);

   private static final Map<Integer, ModelRotation> field_177546_q = Maps.<Integer, ModelRotation>newHashMap();
   private final int field_177545_r;
   private final Matrix4f field_177544_s;
   private final int field_177543_t;
   private final int field_177542_u;

   private static int func_177521_b(int p_177521_0_, int p_177521_1_) {
      return p_177521_0_ * 360 + p_177521_1_;
   }

   private ModelRotation(int p_i46087_3_, int p_i46087_4_) {
      this.field_177545_r = func_177521_b(p_i46087_3_, p_i46087_4_);
      this.field_177544_s = new Matrix4f();
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.setIdentity();
      Matrix4f.rotate((float)(-p_i46087_3_) * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), matrix4f, matrix4f);
      this.field_177543_t = MathHelper.func_76130_a(p_i46087_3_ / 90);
      Matrix4f matrix4f1 = new Matrix4f();
      matrix4f1.setIdentity();
      Matrix4f.rotate((float)(-p_i46087_4_) * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), matrix4f1, matrix4f1);
      this.field_177542_u = MathHelper.func_76130_a(p_i46087_4_ / 90);
      Matrix4f.mul(matrix4f1, matrix4f, this.field_177544_s);
   }

   public Matrix4f func_177525_a() {
      return this.field_177544_s;
   }

   public EnumFacing func_177523_a(EnumFacing p_177523_1_) {
      EnumFacing enumfacing = p_177523_1_;

      for(int i = 0; i < this.field_177543_t; ++i) {
         enumfacing = enumfacing.func_176732_a(EnumFacing.Axis.X);
      }

      if (enumfacing.func_176740_k() != EnumFacing.Axis.Y) {
         for(int j = 0; j < this.field_177542_u; ++j) {
            enumfacing = enumfacing.func_176732_a(EnumFacing.Axis.Y);
         }
      }

      return enumfacing;
   }

   public int func_177520_a(EnumFacing p_177520_1_, int p_177520_2_) {
      int i = p_177520_2_;
      if (p_177520_1_.func_176740_k() == EnumFacing.Axis.X) {
         i = (p_177520_2_ + this.field_177543_t) % 4;
      }

      EnumFacing enumfacing = p_177520_1_;

      for(int j = 0; j < this.field_177543_t; ++j) {
         enumfacing = enumfacing.func_176732_a(EnumFacing.Axis.X);
      }

      if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
         i = (i + this.field_177542_u) % 4;
      }

      return i;
   }

   public static ModelRotation func_177524_a(int p_177524_0_, int p_177524_1_) {
      return field_177546_q.get(Integer.valueOf(func_177521_b(MathHelper.func_180184_b(p_177524_0_, 360), MathHelper.func_180184_b(p_177524_1_, 360))));
   }

   static {
      for(ModelRotation modelrotation : values()) {
         field_177546_q.put(Integer.valueOf(modelrotation.field_177545_r), modelrotation);
      }

   }
}
