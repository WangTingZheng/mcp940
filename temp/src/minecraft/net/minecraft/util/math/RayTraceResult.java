package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class RayTraceResult {
   private BlockPos field_178783_e;
   public RayTraceResult.Type field_72313_a;
   public EnumFacing field_178784_b;
   public Vec3d field_72307_f;
   public Entity field_72308_g;

   public RayTraceResult(Vec3d p_i47094_1_, EnumFacing p_i47094_2_, BlockPos p_i47094_3_) {
      this(RayTraceResult.Type.BLOCK, p_i47094_1_, p_i47094_2_, p_i47094_3_);
   }

   public RayTraceResult(Vec3d p_i47095_1_, EnumFacing p_i47095_2_) {
      this(RayTraceResult.Type.BLOCK, p_i47095_1_, p_i47095_2_, BlockPos.field_177992_a);
   }

   public RayTraceResult(Entity p_i2304_1_) {
      this(p_i2304_1_, new Vec3d(p_i2304_1_.field_70165_t, p_i2304_1_.field_70163_u, p_i2304_1_.field_70161_v));
   }

   public RayTraceResult(RayTraceResult.Type p_i47096_1_, Vec3d p_i47096_2_, EnumFacing p_i47096_3_, BlockPos p_i47096_4_) {
      this.field_72313_a = p_i47096_1_;
      this.field_178783_e = p_i47096_4_;
      this.field_178784_b = p_i47096_3_;
      this.field_72307_f = new Vec3d(p_i47096_2_.field_72450_a, p_i47096_2_.field_72448_b, p_i47096_2_.field_72449_c);
   }

   public RayTraceResult(Entity p_i47097_1_, Vec3d p_i47097_2_) {
      this.field_72313_a = RayTraceResult.Type.ENTITY;
      this.field_72308_g = p_i47097_1_;
      this.field_72307_f = p_i47097_2_;
   }

   public BlockPos func_178782_a() {
      return this.field_178783_e;
   }

   public String toString() {
      return "HitResult{type=" + this.field_72313_a + ", blockpos=" + this.field_178783_e + ", f=" + this.field_178784_b + ", pos=" + this.field_72307_f + ", entity=" + this.field_72308_g + '}';
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;
   }
}
