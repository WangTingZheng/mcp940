package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelLlamaSpit extends ModelBase {
   private final ModelRenderer field_191225_a;

   public ModelLlamaSpit() {
      this(0.0F);
   }

   public ModelLlamaSpit(float p_i47225_1_) {
      this.field_191225_a = new ModelRenderer(this);
      int i = 2;
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(-4.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(0.0F, -4.0F, 0.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(0.0F, 0.0F, -4.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(2.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(0.0F, 2.0F, 0.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78784_a(0, 0).func_78790_a(0.0F, 0.0F, 2.0F, 2, 2, 2, p_i47225_1_);
      this.field_191225_a.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_191225_a.func_78785_a(p_78088_7_);
   }
}
