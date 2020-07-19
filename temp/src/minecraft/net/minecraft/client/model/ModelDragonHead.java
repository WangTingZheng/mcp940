package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDragonHead extends ModelBase {
   private final ModelRenderer field_187070_a;
   private final ModelRenderer field_187071_b;

   public ModelDragonHead(float p_i46588_1_) {
      this.field_78090_t = 256;
      this.field_78089_u = 256;
      this.func_78085_a("body.body", 0, 0);
      this.func_78085_a("wing.skin", -56, 88);
      this.func_78085_a("wingtip.skin", -56, 144);
      this.func_78085_a("rearleg.main", 0, 0);
      this.func_78085_a("rearfoot.main", 112, 0);
      this.func_78085_a("rearlegtip.main", 196, 0);
      this.func_78085_a("head.upperhead", 112, 30);
      this.func_78085_a("wing.bone", 112, 88);
      this.func_78085_a("head.upperlip", 176, 44);
      this.func_78085_a("jaw.jaw", 176, 65);
      this.func_78085_a("frontleg.main", 112, 104);
      this.func_78085_a("wingtip.bone", 112, 136);
      this.func_78085_a("frontfoot.main", 144, 104);
      this.func_78085_a("neck.box", 192, 104);
      this.func_78085_a("frontlegtip.main", 226, 138);
      this.func_78085_a("body.scale", 220, 53);
      this.func_78085_a("head.scale", 0, 0);
      this.func_78085_a("neck.scale", 48, 0);
      this.func_78085_a("head.nostril", 112, 0);
      float f = -16.0F;
      this.field_187070_a = new ModelRenderer(this, "head");
      this.field_187070_a.func_78786_a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16);
      this.field_187070_a.func_78786_a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16);
      this.field_187070_a.field_78809_i = true;
      this.field_187070_a.func_78786_a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6);
      this.field_187070_a.func_78786_a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4);
      this.field_187070_a.field_78809_i = false;
      this.field_187070_a.func_78786_a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6);
      this.field_187070_a.func_78786_a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4);
      this.field_187071_b = new ModelRenderer(this, "jaw");
      this.field_187071_b.func_78793_a(0.0F, 4.0F, -8.0F);
      this.field_187071_b.func_78786_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.field_187070_a.func_78792_a(this.field_187071_b);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.field_187071_b.field_78795_f = (float)(Math.sin((double)(p_78088_2_ * 3.1415927F * 0.2F)) + 1.0D) * 0.2F;
      this.field_187070_a.field_78796_g = p_78088_5_ * 0.017453292F;
      this.field_187070_a.field_78795_f = p_78088_6_ * 0.017453292F;
      GlStateManager.func_179109_b(0.0F, -0.374375F, 0.0F);
      GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
      this.field_187070_a.func_78785_a(p_78088_7_);
   }
}
