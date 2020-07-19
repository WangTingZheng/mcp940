package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer field_177167_a;

   public LayerCape(RenderPlayer p_i46123_1_) {
      this.field_177167_a = p_i46123_1_;
   }

   public void func_177141_a(AbstractClientPlayer p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.func_152122_n() && !p_177141_1_.func_82150_aj() && p_177141_1_.func_175148_a(EnumPlayerModelParts.CAPE) && p_177141_1_.func_110303_q() != null) {
         ItemStack itemstack = p_177141_1_.func_184582_a(EntityEquipmentSlot.CHEST);
         if (itemstack.func_77973_b() != Items.field_185160_cR) {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_177167_a.func_110776_a(p_177141_1_.func_110303_q());
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
            double d0 = p_177141_1_.field_71091_bM + (p_177141_1_.field_71094_bP - p_177141_1_.field_71091_bM) * (double)p_177141_4_ - (p_177141_1_.field_70169_q + (p_177141_1_.field_70165_t - p_177141_1_.field_70169_q) * (double)p_177141_4_);
            double d1 = p_177141_1_.field_71096_bN + (p_177141_1_.field_71095_bQ - p_177141_1_.field_71096_bN) * (double)p_177141_4_ - (p_177141_1_.field_70167_r + (p_177141_1_.field_70163_u - p_177141_1_.field_70167_r) * (double)p_177141_4_);
            double d2 = p_177141_1_.field_71097_bO + (p_177141_1_.field_71085_bR - p_177141_1_.field_71097_bO) * (double)p_177141_4_ - (p_177141_1_.field_70166_s + (p_177141_1_.field_70161_v - p_177141_1_.field_70166_s) * (double)p_177141_4_);
            float f = p_177141_1_.field_70760_ar + (p_177141_1_.field_70761_aq - p_177141_1_.field_70760_ar) * p_177141_4_;
            double d3 = (double)MathHelper.func_76126_a(f * 0.017453292F);
            double d4 = (double)(-MathHelper.func_76134_b(f * 0.017453292F));
            float f1 = (float)d1 * 10.0F;
            f1 = MathHelper.func_76131_a(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
            if (f2 < 0.0F) {
               f2 = 0.0F;
            }

            float f4 = p_177141_1_.field_71107_bF + (p_177141_1_.field_71109_bG - p_177141_1_.field_71107_bF) * p_177141_4_;
            f1 = f1 + MathHelper.func_76126_a((p_177141_1_.field_70141_P + (p_177141_1_.field_70140_Q - p_177141_1_.field_70141_P) * p_177141_4_) * 6.0F) * 32.0F * f4;
            if (p_177141_1_.func_70093_af()) {
               f1 += 25.0F;
            }

            GlStateManager.func_179114_b(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            this.field_177167_a.func_177087_b().func_178728_c(0.0625F);
            GlStateManager.func_179121_F();
         }
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
