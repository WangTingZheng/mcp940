package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderPainting extends Render<EntityPainting> {
   private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

   public RenderPainting(RenderManager p_i46150_1_) {
      super(p_i46150_1_);
   }

   public void func_76986_a(EntityPainting p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(p_76986_2_, p_76986_4_, p_76986_6_);
      GlStateManager.func_179114_b(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179091_B();
      this.func_180548_c(p_76986_1_);
      EntityPainting.EnumArt entitypainting$enumart = p_76986_1_.field_70522_e;
      float f = 0.0625F;
      GlStateManager.func_179152_a(0.0625F, 0.0625F, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      this.func_77010_a(p_76986_1_, entitypainting$enumart.field_75703_B, entitypainting$enumart.field_75704_C, entitypainting$enumart.field_75699_D, entitypainting$enumart.field_75700_E);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityPainting p_110775_1_) {
      return field_110807_a;
   }

   private void func_77010_a(EntityPainting p_77010_1_, int p_77010_2_, int p_77010_3_, int p_77010_4_, int p_77010_5_) {
      float f = (float)(-p_77010_2_) / 2.0F;
      float f1 = (float)(-p_77010_3_) / 2.0F;
      float f2 = 0.5F;
      float f3 = 0.75F;
      float f4 = 0.8125F;
      float f5 = 0.0F;
      float f6 = 0.0625F;
      float f7 = 0.75F;
      float f8 = 0.8125F;
      float f9 = 0.001953125F;
      float f10 = 0.001953125F;
      float f11 = 0.7519531F;
      float f12 = 0.7519531F;
      float f13 = 0.0F;
      float f14 = 0.0625F;

      for(int i = 0; i < p_77010_2_ / 16; ++i) {
         for(int j = 0; j < p_77010_3_ / 16; ++j) {
            float f15 = f + (float)((i + 1) * 16);
            float f16 = f + (float)(i * 16);
            float f17 = f1 + (float)((j + 1) * 16);
            float f18 = f1 + (float)(j * 16);
            this.func_77008_a(p_77010_1_, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
            float f19 = (float)(p_77010_4_ + p_77010_2_ - i * 16) / 256.0F;
            float f20 = (float)(p_77010_4_ + p_77010_2_ - (i + 1) * 16) / 256.0F;
            float f21 = (float)(p_77010_5_ + p_77010_3_ - j * 16) / 256.0F;
            float f22 = (float)(p_77010_5_ + p_77010_3_ - (j + 1) * 16) / 256.0F;
            Tessellator tessellator = Tessellator.func_178181_a();
            BufferBuilder bufferbuilder = tessellator.func_178180_c();
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181710_j);
            bufferbuilder.func_181662_b((double)f15, (double)f18, -0.5D).func_187315_a((double)f20, (double)f21).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, -0.5D).func_187315_a((double)f19, (double)f21).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, -0.5D).func_187315_a((double)f19, (double)f22).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, -0.5D).func_187315_a((double)f20, (double)f22).func_181663_c(0.0F, 0.0F, -1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, 0.5D).func_187315_a(0.75D, 0.0D).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, 0.5D).func_187315_a(0.8125D, 0.0D).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, 0.5D).func_187315_a(0.8125D, 0.0625D).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f18, 0.5D).func_187315_a(0.75D, 0.0625D).func_181663_c(0.0F, 0.0F, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, -0.5D).func_187315_a(0.75D, 0.001953125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, -0.5D).func_187315_a(0.8125D, 0.001953125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, 0.5D).func_187315_a(0.8125D, 0.001953125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, 0.5D).func_187315_a(0.75D, 0.001953125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f18, 0.5D).func_187315_a(0.75D, 0.001953125D).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, 0.5D).func_187315_a(0.8125D, 0.001953125D).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, -0.5D).func_187315_a(0.8125D, 0.001953125D).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f18, -0.5D).func_187315_a(0.75D, 0.001953125D).func_181663_c(0.0F, -1.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, 0.5D).func_187315_a(0.751953125D, 0.0D).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f18, 0.5D).func_187315_a(0.751953125D, 0.0625D).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f18, -0.5D).func_187315_a(0.751953125D, 0.0625D).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f15, (double)f17, -0.5D).func_187315_a(0.751953125D, 0.0D).func_181663_c(-1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, -0.5D).func_187315_a(0.751953125D, 0.0D).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, -0.5D).func_187315_a(0.751953125D, 0.0625D).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f18, 0.5D).func_187315_a(0.751953125D, 0.0625D).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
            bufferbuilder.func_181662_b((double)f16, (double)f17, 0.5D).func_187315_a(0.751953125D, 0.0D).func_181663_c(1.0F, 0.0F, 0.0F).func_181675_d();
            tessellator.func_78381_a();
         }
      }

   }

   private void func_77008_a(EntityPainting p_77008_1_, float p_77008_2_, float p_77008_3_) {
      int i = MathHelper.func_76128_c(p_77008_1_.field_70165_t);
      int j = MathHelper.func_76128_c(p_77008_1_.field_70163_u + (double)(p_77008_3_ / 16.0F));
      int k = MathHelper.func_76128_c(p_77008_1_.field_70161_v);
      EnumFacing enumfacing = p_77008_1_.field_174860_b;
      if (enumfacing == EnumFacing.NORTH) {
         i = MathHelper.func_76128_c(p_77008_1_.field_70165_t + (double)(p_77008_2_ / 16.0F));
      }

      if (enumfacing == EnumFacing.WEST) {
         k = MathHelper.func_76128_c(p_77008_1_.field_70161_v - (double)(p_77008_2_ / 16.0F));
      }

      if (enumfacing == EnumFacing.SOUTH) {
         i = MathHelper.func_76128_c(p_77008_1_.field_70165_t - (double)(p_77008_2_ / 16.0F));
      }

      if (enumfacing == EnumFacing.EAST) {
         k = MathHelper.func_76128_c(p_77008_1_.field_70161_v + (double)(p_77008_2_ / 16.0F));
      }

      int l = this.field_76990_c.field_78722_g.func_175626_b(new BlockPos(i, j, k), 0);
      int i1 = l % 65536;
      int j1 = l / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)i1, (float)j1);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
   }
}
