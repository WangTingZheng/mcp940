package net.minecraft.client.model;

public class ModelBanner extends ModelBase {
   public ModelRenderer field_178690_a;
   public ModelRenderer field_178688_b;
   public ModelRenderer field_178689_c;

   public ModelBanner() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_178690_a = new ModelRenderer(this, 0, 0);
      this.field_178690_a.func_78790_a(-10.0F, 0.0F, -2.0F, 20, 40, 1, 0.0F);
      this.field_178688_b = new ModelRenderer(this, 44, 0);
      this.field_178688_b.func_78790_a(-1.0F, -30.0F, -1.0F, 2, 42, 2, 0.0F);
      this.field_178689_c = new ModelRenderer(this, 0, 42);
      this.field_178689_c.func_78790_a(-10.0F, -32.0F, -1.0F, 20, 2, 2, 0.0F);
   }

   public void func_178687_a() {
      this.field_178690_a.field_78797_d = -32.0F;
      this.field_178690_a.func_78785_a(0.0625F);
      this.field_178688_b.func_78785_a(0.0625F);
      this.field_178689_c.func_78785_a(0.0625F);
   }
}
