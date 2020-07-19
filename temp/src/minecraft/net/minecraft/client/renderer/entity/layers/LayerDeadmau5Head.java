package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class LayerDeadmau5Head implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer field_177208_a;

   public LayerDeadmau5Head(RenderPlayer p_i46119_1_) {
      this.field_177208_a = p_i46119_1_;
   }

   public void func_177141_a(AbstractClientPlayer p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if ("deadmau5".equals(p_177141_1_.func_70005_c_()) && p_177141_1_.func_152123_o() && !p_177141_1_.func_82150_aj()) {
         this.field_177208_a.func_110776_a(p_177141_1_.func_110306_p());

         for(int i = 0; i < 2; ++i) {
            float f = p_177141_1_.field_70126_B + (p_177141_1_.field_70177_z - p_177141_1_.field_70126_B) * p_177141_4_ - (p_177141_1_.field_70760_ar + (p_177141_1_.field_70761_aq - p_177141_1_.field_70760_ar) * p_177141_4_);
            float f1 = p_177141_1_.field_70127_C + (p_177141_1_.field_70125_A - p_177141_1_.field_70127_C) * p_177141_4_;
            GlStateManager.func_179094_E();
            GlStateManager.func_179114_b(f, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -0.375F, 0.0F);
            GlStateManager.func_179114_b(-f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-f, 0.0F, 1.0F, 0.0F);
            float f2 = 1.3333334F;
            GlStateManager.func_179152_a(1.3333334F, 1.3333334F, 1.3333334F);
            this.field_177208_a.func_177087_b().func_178727_b(0.0625F);
            GlStateManager.func_179121_F();
         }

      }
   }

   public boolean func_177142_b() {
      return true;
   }
}
