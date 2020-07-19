package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.util.ResourceLocation;

public class RenderDragonFireball extends Render<EntityDragonFireball> {
   private static final ResourceLocation field_188314_a = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");

   public RenderDragonFireball(RenderManager p_i46553_1_) {
      super(p_i46553_1_);
   }

   public void func_76986_a(EntityDragonFireball p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      this.func_180548_c(p_76986_1_);
      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      float f = 1.0F;
      float f1 = 0.5F;
      float f2 = 0.25F;
      GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181710_j);
      bufferbuilder.func_181662_b(-0.5D, -0.25D, 0.0D).func_187315_a(0.0D, 1.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(0.5D, -0.25D, 0.0D).func_187315_a(1.0D, 1.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(0.5D, 0.75D, 0.0D).func_187315_a(1.0D, 0.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(-0.5D, 0.75D, 0.0D).func_187315_a(0.0D, 0.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      tessellator.func_78381_a();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityDragonFireball p_110775_1_) {
      return field_188314_a;
   }
}
