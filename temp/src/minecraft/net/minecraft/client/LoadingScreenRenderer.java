package net.minecraft.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements IProgressUpdate {
   private String field_73727_a = "";
   private final Minecraft field_73725_b;
   private String field_73726_c = "";
   private long field_73723_d = Minecraft.func_71386_F();
   private boolean field_73724_e;
   private final ScaledResolution field_146587_f;
   private final Framebuffer field_146588_g;

   public LoadingScreenRenderer(Minecraft p_i1017_1_) {
      this.field_73725_b = p_i1017_1_;
      this.field_146587_f = new ScaledResolution(p_i1017_1_);
      this.field_146588_g = new Framebuffer(p_i1017_1_.field_71443_c, p_i1017_1_.field_71440_d, false);
      this.field_146588_g.func_147607_a(9728);
   }

   public void func_73721_b(String p_73721_1_) {
      this.field_73724_e = false;
      this.func_73722_d(p_73721_1_);
   }

   public void func_73720_a(String p_73720_1_) {
      this.field_73724_e = true;
      this.func_73722_d(p_73720_1_);
   }

   private void func_73722_d(String p_73722_1_) {
      this.field_73726_c = p_73722_1_;
      if (!this.field_73725_b.field_71425_J) {
         if (!this.field_73724_e) {
            throw new MinecraftError();
         }
      } else {
         GlStateManager.func_179086_m(256);
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         if (OpenGlHelper.func_148822_b()) {
            int i = this.field_146587_f.func_78325_e();
            GlStateManager.func_179130_a(0.0D, (double)(this.field_146587_f.func_78326_a() * i), (double)(this.field_146587_f.func_78328_b() * i), 0.0D, 100.0D, 300.0D);
         } else {
            ScaledResolution scaledresolution = new ScaledResolution(this.field_73725_b);
            GlStateManager.func_179130_a(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 100.0D, 300.0D);
         }

         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179096_D();
         GlStateManager.func_179109_b(0.0F, 0.0F, -200.0F);
      }
   }

   public void func_73719_c(String p_73719_1_) {
      if (!this.field_73725_b.field_71425_J) {
         if (!this.field_73724_e) {
            throw new MinecraftError();
         }
      } else {
         this.field_73723_d = 0L;
         this.field_73727_a = p_73719_1_;
         this.func_73718_a(-1);
         this.field_73723_d = 0L;
      }
   }

   public void func_73718_a(int p_73718_1_) {
      if (!this.field_73725_b.field_71425_J) {
         if (!this.field_73724_e) {
            throw new MinecraftError();
         }
      } else {
         long i = Minecraft.func_71386_F();
         if (i - this.field_73723_d >= 100L) {
            this.field_73723_d = i;
            ScaledResolution scaledresolution = new ScaledResolution(this.field_73725_b);
            int j = scaledresolution.func_78325_e();
            int k = scaledresolution.func_78326_a();
            int l = scaledresolution.func_78328_b();
            if (OpenGlHelper.func_148822_b()) {
               this.field_146588_g.func_147614_f();
            } else {
               GlStateManager.func_179086_m(256);
            }

            this.field_146588_g.func_147610_a(false);
            GlStateManager.func_179128_n(5889);
            GlStateManager.func_179096_D();
            GlStateManager.func_179130_a(0.0D, scaledresolution.func_78327_c(), scaledresolution.func_78324_d(), 0.0D, 100.0D, 300.0D);
            GlStateManager.func_179128_n(5888);
            GlStateManager.func_179096_D();
            GlStateManager.func_179109_b(0.0F, 0.0F, -200.0F);
            if (!OpenGlHelper.func_148822_b()) {
               GlStateManager.func_179086_m(16640);
            }

            Tessellator tessellator = Tessellator.func_178181_a();
            BufferBuilder bufferbuilder = tessellator.func_178180_c();
            this.field_73725_b.func_110434_K().func_110577_a(Gui.field_110325_k);
            float f = 32.0F;
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            bufferbuilder.func_181662_b(0.0D, (double)l, 0.0D).func_187315_a(0.0D, (double)((float)l / 32.0F)).func_181669_b(64, 64, 64, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)k, (double)l, 0.0D).func_187315_a((double)((float)k / 32.0F), (double)((float)l / 32.0F)).func_181669_b(64, 64, 64, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)k, 0.0D, 0.0D).func_187315_a((double)((float)k / 32.0F), 0.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
            bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
            tessellator.func_78381_a();
            if (p_73718_1_ >= 0) {
               int i1 = 100;
               int j1 = 2;
               int k1 = k / 2 - 50;
               int l1 = l / 2 + 16;
               GlStateManager.func_179090_x();
               bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
               bufferbuilder.func_181662_b((double)k1, (double)l1, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)k1, (double)(l1 + 2), 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)(k1 + 100), (double)(l1 + 2), 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)(k1 + 100), (double)l1, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)k1, (double)l1, 0.0D).func_181669_b(128, 255, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)k1, (double)(l1 + 2), 0.0D).func_181669_b(128, 255, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)(k1 + p_73718_1_), (double)(l1 + 2), 0.0D).func_181669_b(128, 255, 128, 255).func_181675_d();
               bufferbuilder.func_181662_b((double)(k1 + p_73718_1_), (double)l1, 0.0D).func_181669_b(128, 255, 128, 255).func_181675_d();
               tessellator.func_78381_a();
               GlStateManager.func_179098_w();
            }

            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.field_73725_b.field_71466_p.func_175063_a(this.field_73726_c, (float)((k - this.field_73725_b.field_71466_p.func_78256_a(this.field_73726_c)) / 2), (float)(l / 2 - 4 - 16), 16777215);
            this.field_73725_b.field_71466_p.func_175063_a(this.field_73727_a, (float)((k - this.field_73725_b.field_71466_p.func_78256_a(this.field_73727_a)) / 2), (float)(l / 2 - 4 + 8), 16777215);
            this.field_146588_g.func_147609_e();
            if (OpenGlHelper.func_148822_b()) {
               this.field_146588_g.func_147615_c(k * j, l * j);
            }

            this.field_73725_b.func_175601_h();

            try {
               Thread.yield();
            } catch (Exception var15) {
               ;
            }

         }
      }
   }

   public void func_146586_a() {
   }
}
