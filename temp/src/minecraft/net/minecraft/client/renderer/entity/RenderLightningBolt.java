package net.minecraft.client.renderer.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

public class RenderLightningBolt extends Render<EntityLightningBolt> {
   public RenderLightningBolt(RenderManager p_i46157_1_) {
      super(p_i46157_1_);
   }

   public void func_76986_a(EntityLightningBolt p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      double[] adouble = new double[8];
      double[] adouble1 = new double[8];
      double d0 = 0.0D;
      double d1 = 0.0D;
      Random random = new Random(p_76986_1_.field_70264_a);

      for(int i = 7; i >= 0; --i) {
         adouble[i] = d0;
         adouble1[i] = d1;
         d0 += (double)(random.nextInt(11) - 5);
         d1 += (double)(random.nextInt(11) - 5);
      }

      for(int k1 = 0; k1 < 4; ++k1) {
         Random random1 = new Random(p_76986_1_.field_70264_a);

         for(int j = 0; j < 3; ++j) {
            int k = 7;
            int l = 0;
            if (j > 0) {
               k = 7 - j;
            }

            if (j > 0) {
               l = k - 2;
            }

            double d2 = adouble[k] - d0;
            double d3 = adouble1[k] - d1;

            for(int i1 = k; i1 >= l; --i1) {
               double d4 = d2;
               double d5 = d3;
               if (j == 0) {
                  d2 += (double)(random1.nextInt(11) - 5);
                  d3 += (double)(random1.nextInt(11) - 5);
               } else {
                  d2 += (double)(random1.nextInt(31) - 15);
                  d3 += (double)(random1.nextInt(31) - 15);
               }

               bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               float f = 0.5F;
               float f1 = 0.45F;
               float f2 = 0.45F;
               float f3 = 0.5F;
               double d6 = 0.1D + (double)k1 * 0.2D;
               if (j == 0) {
                  d6 *= (double)i1 * 0.1D + 1.0D;
               }

               double d7 = 0.1D + (double)k1 * 0.2D;
               if (j == 0) {
                  d7 *= (double)(i1 - 1) * 0.1D + 1.0D;
               }

               for(int j1 = 0; j1 < 5; ++j1) {
                  double d8 = p_76986_2_ + 0.5D - d6;
                  double d9 = p_76986_6_ + 0.5D - d6;
                  if (j1 == 1 || j1 == 2) {
                     d8 += d6 * 2.0D;
                  }

                  if (j1 == 2 || j1 == 3) {
                     d9 += d6 * 2.0D;
                  }

                  double d10 = p_76986_2_ + 0.5D - d7;
                  double d11 = p_76986_6_ + 0.5D - d7;
                  if (j1 == 1 || j1 == 2) {
                     d10 += d7 * 2.0D;
                  }

                  if (j1 == 2 || j1 == 3) {
                     d11 += d7 * 2.0D;
                  }

                  bufferbuilder.func_181662_b(d10 + d2, p_76986_4_ + (double)(i1 * 16), d11 + d3).func_181666_a(0.45F, 0.45F, 0.5F, 0.3F).func_181675_d();
                  bufferbuilder.func_181662_b(d8 + d4, p_76986_4_ + (double)((i1 + 1) * 16), d9 + d5).func_181666_a(0.45F, 0.45F, 0.5F, 0.3F).func_181675_d();
               }

               tessellator.func_78381_a();
            }
         }
      }

      GlStateManager.func_179084_k();
      GlStateManager.func_179145_e();
      GlStateManager.func_179098_w();
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntityLightningBolt p_110775_1_) {
      return null;
   }
}
