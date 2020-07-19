package net.minecraft.client.model;

import net.minecraft.util.math.Vec3d;

public class PositionTextureVertex {
   public Vec3d field_78243_a;
   public float field_78241_b;
   public float field_78242_c;

   public PositionTextureVertex(float p_i1158_1_, float p_i1158_2_, float p_i1158_3_, float p_i1158_4_, float p_i1158_5_) {
      this(new Vec3d((double)p_i1158_1_, (double)p_i1158_2_, (double)p_i1158_3_), p_i1158_4_, p_i1158_5_);
   }

   public PositionTextureVertex func_78240_a(float p_78240_1_, float p_78240_2_) {
      return new PositionTextureVertex(this, p_78240_1_, p_78240_2_);
   }

   public PositionTextureVertex(PositionTextureVertex p_i46363_1_, float p_i46363_2_, float p_i46363_3_) {
      this.field_78243_a = p_i46363_1_.field_78243_a;
      this.field_78241_b = p_i46363_2_;
      this.field_78242_c = p_i46363_3_;
   }

   public PositionTextureVertex(Vec3d p_i47091_1_, float p_i47091_2_, float p_i47091_3_) {
      this.field_78243_a = p_i47091_1_;
      this.field_78241_b = p_i47091_2_;
      this.field_78242_c = p_i47091_3_;
   }
}
