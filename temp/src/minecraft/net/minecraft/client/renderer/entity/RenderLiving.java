package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;

public abstract class RenderLiving<T extends EntityLiving> extends RenderLivingBase<T> {
   public RenderLiving(RenderManager p_i46153_1_, ModelBase p_i46153_2_, float p_i46153_3_) {
      super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
   }

   protected boolean func_177070_b(T p_177070_1_) {
      return super.func_177070_b(p_177070_1_) && (p_177070_1_.func_94059_bO() || p_177070_1_.func_145818_k_() && p_177070_1_ == this.field_76990_c.field_147941_i);
   }

   public boolean func_177071_a(T p_177071_1_, ICamera p_177071_2_, double p_177071_3_, double p_177071_5_, double p_177071_7_) {
      if (super.func_177071_a(p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_)) {
         return true;
      } else if (p_177071_1_.func_110167_bD() && p_177071_1_.func_110166_bE() != null) {
         Entity entity = p_177071_1_.func_110166_bE();
         return p_177071_2_.func_78546_a(entity.func_184177_bl());
      } else {
         return false;
      }
   }

   public void func_76986_a(T p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      if (!this.field_188301_f) {
         this.func_110827_b(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      }

   }

   public void func_177105_a(T p_177105_1_) {
      int i = p_177105_1_.func_70070_b();
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
   }

   private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_) {
      return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
   }

   protected void func_110827_b(T p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_, float p_110827_8_, float p_110827_9_) {
      Entity entity = p_110827_1_.func_110166_bE();
      if (entity != null) {
         p_110827_4_ = p_110827_4_ - (1.6D - (double)p_110827_1_.field_70131_O) * 0.5D;
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         double d0 = this.func_110828_a((double)entity.field_70126_B, (double)entity.field_70177_z, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double d1 = this.func_110828_a((double)entity.field_70127_C, (double)entity.field_70125_A, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double d2 = Math.cos(d0);
         double d3 = Math.sin(d0);
         double d4 = Math.sin(d1);
         if (entity instanceof EntityHanging) {
            d2 = 0.0D;
            d3 = 0.0D;
            d4 = -1.0D;
         }

         double d5 = Math.cos(d1);
         double d6 = this.func_110828_a(entity.field_70169_q, entity.field_70165_t, (double)p_110827_9_) - d2 * 0.7D - d3 * 0.5D * d5;
         double d7 = this.func_110828_a(entity.field_70167_r + (double)entity.func_70047_e() * 0.7D, entity.field_70163_u + (double)entity.func_70047_e() * 0.7D, (double)p_110827_9_) - d4 * 0.5D - 0.25D;
         double d8 = this.func_110828_a(entity.field_70166_s, entity.field_70161_v, (double)p_110827_9_) - d3 * 0.7D + d2 * 0.5D * d5;
         double d9 = this.func_110828_a((double)p_110827_1_.field_70760_ar, (double)p_110827_1_.field_70761_aq, (double)p_110827_9_) * 0.01745329238474369D + 1.5707963267948966D;
         d2 = Math.cos(d9) * (double)p_110827_1_.field_70130_N * 0.4D;
         d3 = Math.sin(d9) * (double)p_110827_1_.field_70130_N * 0.4D;
         double d10 = this.func_110828_a(p_110827_1_.field_70169_q, p_110827_1_.field_70165_t, (double)p_110827_9_) + d2;
         double d11 = this.func_110828_a(p_110827_1_.field_70167_r, p_110827_1_.field_70163_u, (double)p_110827_9_);
         double d12 = this.func_110828_a(p_110827_1_.field_70166_s, p_110827_1_.field_70161_v, (double)p_110827_9_) + d3;
         p_110827_2_ = p_110827_2_ + d2;
         p_110827_6_ = p_110827_6_ + d3;
         double d13 = (double)((float)(d6 - d10));
         double d14 = (double)((float)(d7 - d11));
         double d15 = (double)((float)(d8 - d12));
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179129_p();
         int i = 24;
         double d16 = 0.025D;
         bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);

         for(int j = 0; j <= 24; ++j) {
            float f = 0.5F;
            float f1 = 0.4F;
            float f2 = 0.3F;
            if (j % 2 == 0) {
               f *= 0.7F;
               f1 *= 0.7F;
               f2 *= 0.7F;
            }

            float f3 = (float)j / 24.0F;
            bufferbuilder.func_181662_b(p_110827_2_ + d13 * (double)f3 + 0.0D, p_110827_4_ + d14 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F), p_110827_6_ + d15 * (double)f3).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b(p_110827_2_ + d13 * (double)f3 + 0.025D, p_110827_4_ + d14 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + d15 * (double)f3).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
         }

         tessellator.func_78381_a();
         bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);

         for(int k = 0; k <= 24; ++k) {
            float f4 = 0.5F;
            float f5 = 0.4F;
            float f6 = 0.3F;
            if (k % 2 == 0) {
               f4 *= 0.7F;
               f5 *= 0.7F;
               f6 *= 0.7F;
            }

            float f7 = (float)k / 24.0F;
            bufferbuilder.func_181662_b(p_110827_2_ + d13 * (double)f7 + 0.0D, p_110827_4_ + d14 * (double)(f7 * f7 + f7) * 0.5D + (double)((24.0F - (float)k) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + d15 * (double)f7).func_181666_a(f4, f5, f6, 1.0F).func_181675_d();
            bufferbuilder.func_181662_b(p_110827_2_ + d13 * (double)f7 + 0.025D, p_110827_4_ + d14 * (double)(f7 * f7 + f7) * 0.5D + (double)((24.0F - (float)k) / 18.0F + 0.125F), p_110827_6_ + d15 * (double)f7 + 0.025D).func_181666_a(f4, f5, f6, 1.0F).func_181675_d();
         }

         tessellator.func_78381_a();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
         GlStateManager.func_179089_o();
      }
   }
}
