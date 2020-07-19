package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
   private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
   private final ModelSign field_147514_c = new ModelSign();

   public void func_192841_a(TileEntitySign p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      Block block = p_192841_1_.func_145838_q();
      GlStateManager.func_179094_E();
      float f = 0.6666667F;
      if (block == Blocks.field_150472_an) {
         GlStateManager.func_179109_b((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
         float f1 = (float)(p_192841_1_.func_145832_p() * 360) / 16.0F;
         GlStateManager.func_179114_b(-f1, 0.0F, 1.0F, 0.0F);
         this.field_147514_c.field_78165_b.field_78806_j = true;
      } else {
         int k = p_192841_1_.func_145832_p();
         float f2 = 0.0F;
         if (k == 2) {
            f2 = 180.0F;
         }

         if (k == 4) {
            f2 = 90.0F;
         }

         if (k == 5) {
            f2 = -90.0F;
         }

         GlStateManager.func_179109_b((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
         GlStateManager.func_179114_b(-f2, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.0F, -0.3125F, -0.4375F);
         this.field_147514_c.field_78165_b.field_78806_j = false;
      }

      if (p_192841_9_ >= 0) {
         this.func_147499_a(field_178460_a[p_192841_9_]);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(4.0F, 2.0F, 1.0F);
         GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.func_179128_n(5888);
      } else {
         this.func_147499_a(field_147513_b);
      }

      GlStateManager.func_179091_B();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.6666667F, -0.6666667F, -0.6666667F);
      this.field_147514_c.func_78164_a();
      GlStateManager.func_179121_F();
      FontRenderer fontrenderer = this.func_147498_b();
      float f3 = 0.010416667F;
      GlStateManager.func_179109_b(0.0F, 0.33333334F, 0.046666667F);
      GlStateManager.func_179152_a(0.010416667F, -0.010416667F, 0.010416667F);
      GlStateManager.func_187432_a(0.0F, 0.0F, -0.010416667F);
      GlStateManager.func_179132_a(false);
      int i = 0;
      if (p_192841_9_ < 0) {
         for(int j = 0; j < p_192841_1_.field_145915_a.length; ++j) {
            if (p_192841_1_.field_145915_a[j] != null) {
               ITextComponent itextcomponent = p_192841_1_.field_145915_a[j];
               List<ITextComponent> list = GuiUtilRenderComponents.func_178908_a(itextcomponent, 90, fontrenderer, false, true);
               String s = list != null && !list.isEmpty() ? ((ITextComponent)list.get(0)).func_150254_d() : "";
               if (j == p_192841_1_.field_145918_i) {
                  s = "> " + s + " <";
                  fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, j * 10 - p_192841_1_.field_145915_a.length * 5, 0);
               } else {
                  fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, j * 10 - p_192841_1_.field_145915_a.length * 5, 0);
               }
            }
         }
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
      if (p_192841_9_ >= 0) {
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
      }

   }
}
