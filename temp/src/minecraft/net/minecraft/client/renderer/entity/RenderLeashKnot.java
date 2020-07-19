package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render<EntityLeashKnot> {
   private static final ResourceLocation field_110802_a = new ResourceLocation("textures/entity/lead_knot.png");
   private final ModelLeashKnot field_110801_f = new ModelLeashKnot();

   public RenderLeashKnot(RenderManager p_i46158_1_) {
      super(p_i46158_1_);
   }

   public void func_76986_a(EntityLeashKnot p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179129_p();
      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      float f = 0.0625F;
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      GlStateManager.func_179141_d();
      this.func_180548_c(p_76986_1_);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      this.field_110801_f.func_78088_a(p_76986_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityLeashKnot p_110775_1_) {
      return field_110802_a;
   }
}
