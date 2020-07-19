package net.minecraft.client.model;

public class ModelCow extends ModelQuadruped {
   public ModelCow() {
      super(12, 0.0F);
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
      this.field_78150_a.func_78793_a(0.0F, 4.0F, -8.0F);
      this.field_78150_a.func_78784_a(22, 0).func_78790_a(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      this.field_78150_a.func_78784_a(22, 0).func_78790_a(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      this.field_78148_b = new ModelRenderer(this, 18, 4);
      this.field_78148_b.func_78790_a(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
      this.field_78148_b.func_78793_a(0.0F, 5.0F, 2.0F);
      this.field_78148_b.func_78784_a(52, 0).func_78789_a(-2.0F, 2.0F, -8.0F, 4, 6, 1);
      --this.field_78149_c.field_78800_c;
      ++this.field_78146_d.field_78800_c;
      this.field_78149_c.field_78798_e += 0.0F;
      this.field_78146_d.field_78798_e += 0.0F;
      --this.field_78147_e.field_78800_c;
      ++this.field_78144_f.field_78800_c;
      --this.field_78147_e.field_78798_e;
      --this.field_78144_f.field_78798_e;
      this.field_78151_h += 2.0F;
   }
}
