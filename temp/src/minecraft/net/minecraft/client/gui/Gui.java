package net.minecraft.client.gui;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui {
   public static final ResourceLocation field_110325_k = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation field_110323_l = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation field_110324_m = new ResourceLocation("textures/gui/icons.png");
   protected float field_73735_i;

   protected void func_73730_a(int p_73730_1_, int p_73730_2_, int p_73730_3_, int p_73730_4_) {
      if (p_73730_2_ < p_73730_1_) {
         int i = p_73730_1_;
         p_73730_1_ = p_73730_2_;
         p_73730_2_ = i;
      }

      func_73734_a(p_73730_1_, p_73730_3_, p_73730_2_ + 1, p_73730_3_ + 1, p_73730_4_);
   }

   protected void func_73728_b(int p_73728_1_, int p_73728_2_, int p_73728_3_, int p_73728_4_) {
      if (p_73728_3_ < p_73728_2_) {
         int i = p_73728_2_;
         p_73728_2_ = p_73728_3_;
         p_73728_3_ = i;
      }

      func_73734_a(p_73728_1_, p_73728_2_ + 1, p_73728_1_ + 1, p_73728_3_, p_73728_4_);
   }

   public static void func_73734_a(int p_73734_0_, int p_73734_1_, int p_73734_2_, int p_73734_3_, int p_73734_4_) {
      if (p_73734_0_ < p_73734_2_) {
         int i = p_73734_0_;
         p_73734_0_ = p_73734_2_;
         p_73734_2_ = i;
      }

      if (p_73734_1_ < p_73734_3_) {
         int j = p_73734_1_;
         p_73734_1_ = p_73734_3_;
         p_73734_3_ = j;
      }

      float f3 = (float)(p_73734_4_ >> 24 & 255) / 255.0F;
      float f = (float)(p_73734_4_ >> 16 & 255) / 255.0F;
      float f1 = (float)(p_73734_4_ >> 8 & 255) / 255.0F;
      float f2 = (float)(p_73734_4_ & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      bufferbuilder.func_181662_b((double)p_73734_0_, (double)p_73734_3_, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73734_2_, (double)p_73734_3_, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73734_2_, (double)p_73734_1_, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73734_0_, (double)p_73734_1_, 0.0D).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   protected void func_73733_a(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, int p_73733_5_, int p_73733_6_) {
      float f = (float)(p_73733_5_ >> 24 & 255) / 255.0F;
      float f1 = (float)(p_73733_5_ >> 16 & 255) / 255.0F;
      float f2 = (float)(p_73733_5_ >> 8 & 255) / 255.0F;
      float f3 = (float)(p_73733_5_ & 255) / 255.0F;
      float f4 = (float)(p_73733_6_ >> 24 & 255) / 255.0F;
      float f5 = (float)(p_73733_6_ >> 16 & 255) / 255.0F;
      float f6 = (float)(p_73733_6_ >> 8 & 255) / 255.0F;
      float f7 = (float)(p_73733_6_ & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b((double)p_73733_3_, (double)p_73733_2_, (double)this.field_73735_i).func_181666_a(f1, f2, f3, f).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73733_1_, (double)p_73733_2_, (double)this.field_73735_i).func_181666_a(f1, f2, f3, f).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73733_1_, (double)p_73733_4_, (double)this.field_73735_i).func_181666_a(f5, f6, f7, f4).func_181675_d();
      bufferbuilder.func_181662_b((double)p_73733_3_, (double)p_73733_4_, (double)this.field_73735_i).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   public void func_73732_a(FontRenderer p_73732_1_, String p_73732_2_, int p_73732_3_, int p_73732_4_, int p_73732_5_) {
      p_73732_1_.func_175063_a(p_73732_2_, (float)(p_73732_3_ - p_73732_1_.func_78256_a(p_73732_2_) / 2), (float)p_73732_4_, p_73732_5_);
   }

   public void func_73731_b(FontRenderer p_73731_1_, String p_73731_2_, int p_73731_3_, int p_73731_4_, int p_73731_5_) {
      p_73731_1_.func_175063_a(p_73731_2_, (float)p_73731_3_, (float)p_73731_4_, p_73731_5_);
   }

   public void func_73729_b(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)this.field_73735_i).func_187315_a((double)((float)(p_73729_3_ + 0) * 0.00390625F), (double)((float)(p_73729_4_ + p_73729_6_) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.field_73735_i).func_187315_a((double)((float)(p_73729_3_ + p_73729_5_) * 0.00390625F), (double)((float)(p_73729_4_ + p_73729_6_) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)this.field_73735_i).func_187315_a((double)((float)(p_73729_3_ + p_73729_5_) * 0.00390625F), (double)((float)(p_73729_4_ + 0) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)this.field_73735_i).func_187315_a((double)((float)(p_73729_3_ + 0) * 0.00390625F), (double)((float)(p_73729_4_ + 0) * 0.00390625F)).func_181675_d();
      tessellator.func_78381_a();
   }

   public void func_175174_a(float p_175174_1_, float p_175174_2_, int p_175174_3_, int p_175174_4_, int p_175174_5_, int p_175174_6_) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b((double)(p_175174_1_ + 0.0F), (double)(p_175174_2_ + (float)p_175174_6_), (double)this.field_73735_i).func_187315_a((double)((float)(p_175174_3_ + 0) * 0.00390625F), (double)((float)(p_175174_4_ + p_175174_6_) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175174_1_ + (float)p_175174_5_), (double)(p_175174_2_ + (float)p_175174_6_), (double)this.field_73735_i).func_187315_a((double)((float)(p_175174_3_ + p_175174_5_) * 0.00390625F), (double)((float)(p_175174_4_ + p_175174_6_) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175174_1_ + (float)p_175174_5_), (double)(p_175174_2_ + 0.0F), (double)this.field_73735_i).func_187315_a((double)((float)(p_175174_3_ + p_175174_5_) * 0.00390625F), (double)((float)(p_175174_4_ + 0) * 0.00390625F)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175174_1_ + 0.0F), (double)(p_175174_2_ + 0.0F), (double)this.field_73735_i).func_187315_a((double)((float)(p_175174_3_ + 0) * 0.00390625F), (double)((float)(p_175174_4_ + 0) * 0.00390625F)).func_181675_d();
      tessellator.func_78381_a();
   }

   public void func_175175_a(int p_175175_1_, int p_175175_2_, TextureAtlasSprite p_175175_3_, int p_175175_4_, int p_175175_5_) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b((double)(p_175175_1_ + 0), (double)(p_175175_2_ + p_175175_5_), (double)this.field_73735_i).func_187315_a((double)p_175175_3_.func_94209_e(), (double)p_175175_3_.func_94210_h()).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175175_1_ + p_175175_4_), (double)(p_175175_2_ + p_175175_5_), (double)this.field_73735_i).func_187315_a((double)p_175175_3_.func_94212_f(), (double)p_175175_3_.func_94210_h()).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175175_1_ + p_175175_4_), (double)(p_175175_2_ + 0), (double)this.field_73735_i).func_187315_a((double)p_175175_3_.func_94212_f(), (double)p_175175_3_.func_94206_g()).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_175175_1_ + 0), (double)(p_175175_2_ + 0), (double)this.field_73735_i).func_187315_a((double)p_175175_3_.func_94209_e(), (double)p_175175_3_.func_94206_g()).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void func_146110_a(int p_146110_0_, int p_146110_1_, float p_146110_2_, float p_146110_3_, int p_146110_4_, int p_146110_5_, float p_146110_6_, float p_146110_7_) {
      float f = 1.0F / p_146110_6_;
      float f1 = 1.0F / p_146110_7_;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b((double)p_146110_0_, (double)(p_146110_1_ + p_146110_5_), 0.0D).func_187315_a((double)(p_146110_2_ * f), (double)((p_146110_3_ + (float)p_146110_5_) * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_146110_0_ + p_146110_4_), (double)(p_146110_1_ + p_146110_5_), 0.0D).func_187315_a((double)((p_146110_2_ + (float)p_146110_4_) * f), (double)((p_146110_3_ + (float)p_146110_5_) * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_146110_0_ + p_146110_4_), (double)p_146110_1_, 0.0D).func_187315_a((double)((p_146110_2_ + (float)p_146110_4_) * f), (double)(p_146110_3_ * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)p_146110_0_, (double)p_146110_1_, 0.0D).func_187315_a((double)(p_146110_2_ * f), (double)(p_146110_3_ * f1)).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void func_152125_a(int p_152125_0_, int p_152125_1_, float p_152125_2_, float p_152125_3_, int p_152125_4_, int p_152125_5_, int p_152125_6_, int p_152125_7_, float p_152125_8_, float p_152125_9_) {
      float f = 1.0F / p_152125_8_;
      float f1 = 1.0F / p_152125_9_;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b((double)p_152125_0_, (double)(p_152125_1_ + p_152125_7_), 0.0D).func_187315_a((double)(p_152125_2_ * f), (double)((p_152125_3_ + (float)p_152125_5_) * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_152125_0_ + p_152125_6_), (double)(p_152125_1_ + p_152125_7_), 0.0D).func_187315_a((double)((p_152125_2_ + (float)p_152125_4_) * f), (double)((p_152125_3_ + (float)p_152125_5_) * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)(p_152125_0_ + p_152125_6_), (double)p_152125_1_, 0.0D).func_187315_a((double)((p_152125_2_ + (float)p_152125_4_) * f), (double)(p_152125_3_ * f1)).func_181675_d();
      bufferbuilder.func_181662_b((double)p_152125_0_, (double)p_152125_1_, 0.0D).func_187315_a((double)(p_152125_2_ * f), (double)(p_152125_3_ * f1)).func_181675_d();
      tessellator.func_78381_a();
   }
}
