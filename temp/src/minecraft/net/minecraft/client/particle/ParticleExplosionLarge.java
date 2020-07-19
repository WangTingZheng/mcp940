package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleExplosionLarge extends Particle {
   private static final ResourceLocation field_110127_a = new ResourceLocation("textures/entity/explosion.png");
   private static final VertexFormat field_181549_az = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m).func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n).func_181721_a(DefaultVertexFormats.field_181716_p).func_181721_a(DefaultVertexFormats.field_181717_q).func_181721_a(DefaultVertexFormats.field_181718_r);
   private int field_70581_a;
   private final int field_70584_aq;
   private final TextureManager field_70583_ar;
   private final float field_70582_as;

   protected ParticleExplosionLarge(TextureManager p_i1213_1_, World p_i1213_2_, double p_i1213_3_, double p_i1213_5_, double p_i1213_7_, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_) {
      super(p_i1213_2_, p_i1213_3_, p_i1213_5_, p_i1213_7_, 0.0D, 0.0D, 0.0D);
      this.field_70583_ar = p_i1213_1_;
      this.field_70584_aq = 6 + this.field_187136_p.nextInt(4);
      float f = this.field_187136_p.nextFloat() * 0.6F + 0.4F;
      this.field_70552_h = f;
      this.field_70553_i = f;
      this.field_70551_j = f;
      this.field_70582_as = 1.0F - (float)p_i1213_9_ * 0.5F;
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      int i = (int)(((float)this.field_70581_a + p_180434_3_) * 15.0F / (float)this.field_70584_aq);
      if (i <= 15) {
         this.field_70583_ar.func_110577_a(field_110127_a);
         float f = (float)(i % 4) / 4.0F;
         float f1 = f + 0.24975F;
         float f2 = (float)(i / 4) / 4.0F;
         float f3 = f2 + 0.24975F;
         float f4 = 2.0F * this.field_70582_as;
         float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)p_180434_3_ - field_70556_an);
         float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)p_180434_3_ - field_70554_ao);
         float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)p_180434_3_ - field_70555_ap);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179140_f();
         RenderHelper.func_74518_a();
         p_180434_1_.func_181668_a(7, field_181549_az);
         p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a((double)f1, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a((double)f1, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a((double)f, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         Tessellator.func_178181_a().func_78381_a();
         GlStateManager.func_179145_e();
      }
   }

   public int func_189214_a(float p_189214_1_) {
      return 61680;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_70581_a;
      if (this.field_70581_a == this.field_70584_aq) {
         this.func_187112_i();
      }

   }

   public int func_70537_b() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleExplosionLarge(Minecraft.func_71410_x().func_110434_K(), p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
