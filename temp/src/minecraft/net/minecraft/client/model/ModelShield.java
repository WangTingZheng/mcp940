package net.minecraft.client.model;

public class ModelShield extends ModelBase {
   public ModelRenderer field_187063_a;
   public ModelRenderer field_187064_b;

   public ModelShield() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_187063_a = new ModelRenderer(this, 0, 0);
      this.field_187063_a.func_78790_a(-6.0F, -11.0F, -2.0F, 12, 22, 1, 0.0F);
      this.field_187064_b = new ModelRenderer(this, 26, 0);
      this.field_187064_b.func_78790_a(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F);
   }

   public void func_187062_a() {
      this.field_187063_a.func_78785_a(0.0625F);
      this.field_187064_b.func_78785_a(0.0625F);
   }
}
