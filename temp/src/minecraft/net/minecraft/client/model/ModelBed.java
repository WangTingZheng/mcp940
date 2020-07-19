package net.minecraft.client.model;

public class ModelBed extends ModelBase {
   public ModelRenderer field_193772_a;
   public ModelRenderer field_193773_b;
   public ModelRenderer[] field_193774_c = new ModelRenderer[4];

   public ModelBed() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_193772_a = new ModelRenderer(this, 0, 0);
      this.field_193772_a.func_78790_a(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
      this.field_193773_b = new ModelRenderer(this, 0, 22);
      this.field_193773_b.func_78790_a(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
      this.field_193774_c[0] = new ModelRenderer(this, 50, 0);
      this.field_193774_c[1] = new ModelRenderer(this, 50, 6);
      this.field_193774_c[2] = new ModelRenderer(this, 50, 12);
      this.field_193774_c[3] = new ModelRenderer(this, 50, 18);
      this.field_193774_c[0].func_78789_a(0.0F, 6.0F, -16.0F, 3, 3, 3);
      this.field_193774_c[1].func_78789_a(0.0F, 6.0F, 0.0F, 3, 3, 3);
      this.field_193774_c[2].func_78789_a(-16.0F, 6.0F, -16.0F, 3, 3, 3);
      this.field_193774_c[3].func_78789_a(-16.0F, 6.0F, 0.0F, 3, 3, 3);
      this.field_193774_c[0].field_78795_f = 1.5707964F;
      this.field_193774_c[1].field_78795_f = 1.5707964F;
      this.field_193774_c[2].field_78795_f = 1.5707964F;
      this.field_193774_c[3].field_78795_f = 1.5707964F;
      this.field_193774_c[0].field_78808_h = 0.0F;
      this.field_193774_c[1].field_78808_h = 1.5707964F;
      this.field_193774_c[2].field_78808_h = 4.712389F;
      this.field_193774_c[3].field_78808_h = 3.1415927F;
   }

   public int func_193770_a() {
      return 51;
   }

   public void func_193771_b() {
      this.field_193772_a.func_78785_a(0.0625F);
      this.field_193773_b.func_78785_a(0.0625F);
      this.field_193774_c[0].func_78785_a(0.0625F);
      this.field_193774_c[1].func_78785_a(0.0625F);
      this.field_193774_c[2].func_78785_a(0.0625F);
      this.field_193774_c[3].func_78785_a(0.0625F);
   }

   public void func_193769_a(boolean p_193769_1_) {
      this.field_193772_a.field_78806_j = p_193769_1_;
      this.field_193773_b.field_78806_j = !p_193769_1_;
      this.field_193774_c[0].field_78806_j = !p_193769_1_;
      this.field_193774_c[1].field_78806_j = p_193769_1_;
      this.field_193774_c[2].field_78806_j = !p_193769_1_;
      this.field_193774_c[3].field_78806_j = p_193769_1_;
   }
}
