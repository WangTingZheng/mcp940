package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
   public void func_177141_a(EntityDragon p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.field_70995_bG > 0) {
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         RenderHelper.func_74518_a();
         float f = ((float)p_177141_1_.field_70995_bG + p_177141_4_) / 200.0F;
         float f1 = 0.0F;
         if (f > 0.8F) {
            f1 = (f - 0.8F) / 0.2F;
         }

         Random random = new Random(432L);
         GlStateManager.func_179090_x();
         GlStateManager.func_179103_j(7425);
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
         GlStateManager.func_179118_c();
         GlStateManager.func_179089_o();
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, -1.0F, -2.0F);

         for(int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i) {
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
            float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
            float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
            bufferbuilder.func_181668_a(6, DefaultVertexFormats.field_181706_f);
            bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - f1))).func_181675_d();
            bufferbuilder.func_181662_b(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
            bufferbuilder.func_181662_b(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
            bufferbuilder.func_181662_b(0.0D, (double)f2, (double)(1.0F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
            bufferbuilder.func_181662_b(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
            tessellator.func_78381_a();
         }

         GlStateManager.func_179121_F();
         GlStateManager.func_179132_a(true);
         GlStateManager.func_179129_p();
         GlStateManager.func_179084_k();
         GlStateManager.func_179103_j(7424);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179098_w();
         GlStateManager.func_179141_d();
         RenderHelper.func_74519_b();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
