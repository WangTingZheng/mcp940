package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSkeletonHead extends ModelBase {
   public ModelRenderer field_82896_a;

   public ModelSkeletonHead() {
      this(0, 35, 64, 64);
   }

   public ModelSkeletonHead(int p_i1155_1_, int p_i1155_2_, int p_i1155_3_, int p_i1155_4_) {
      this.field_78090_t = p_i1155_3_;
      this.field_78089_u = p_i1155_4_;
      this.field_82896_a = new ModelRenderer(this, p_i1155_1_, p_i1155_2_);
      this.field_82896_a.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.field_82896_a.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_82896_a.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_82896_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_82896_a.field_78795_f = p_78087_5_ * 0.017453292F;
   }
}
