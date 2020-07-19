package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticlePortal extends Particle {
   private final float field_70571_a;
   private final double field_70574_aq;
   private final double field_70573_ar;
   private final double field_70572_as;

   protected ParticlePortal(World p_i46351_1_, double p_i46351_2_, double p_i46351_4_, double p_i46351_6_, double p_i46351_8_, double p_i46351_10_, double p_i46351_12_) {
      super(p_i46351_1_, p_i46351_2_, p_i46351_4_, p_i46351_6_, p_i46351_8_, p_i46351_10_, p_i46351_12_);
      this.field_187129_i = p_i46351_8_;
      this.field_187130_j = p_i46351_10_;
      this.field_187131_k = p_i46351_12_;
      this.field_187126_f = p_i46351_2_;
      this.field_187127_g = p_i46351_4_;
      this.field_187128_h = p_i46351_6_;
      this.field_70574_aq = this.field_187126_f;
      this.field_70573_ar = this.field_187127_g;
      this.field_70572_as = this.field_187128_h;
      float f = this.field_187136_p.nextFloat() * 0.6F + 0.4F;
      this.field_70544_f = this.field_187136_p.nextFloat() * 0.2F + 0.5F;
      this.field_70571_a = this.field_70544_f;
      this.field_70552_h = f * 0.9F;
      this.field_70553_i = f * 0.3F;
      this.field_70551_j = f;
      this.field_70547_e = (int)(Math.random() * 10.0D) + 40;
      this.func_70536_a((int)(Math.random() * 8.0D));
   }

   public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
      this.func_187108_a(this.func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
      this.func_187118_j();
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e;
      f = 1.0F - f;
      f = f * f;
      f = 1.0F - f;
      this.field_70544_f = this.field_70571_a * f;
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public int func_189214_a(float p_189214_1_) {
      int i = super.func_189214_a(p_189214_1_);
      float f = (float)this.field_70546_d / (float)this.field_70547_e;
      f = f * f;
      f = f * f;
      int j = i & 255;
      int k = i >> 16 & 255;
      k = k + (int)(f * 15.0F * 16.0F);
      if (k > 240) {
         k = 240;
      }

      return j | k << 16;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      float f = (float)this.field_70546_d / (float)this.field_70547_e;
      float f1 = -f + f * f * 2.0F;
      float f2 = 1.0F - f1;
      this.field_187126_f = this.field_70574_aq + this.field_187129_i * (double)f2;
      this.field_187127_g = this.field_70573_ar + this.field_187130_j * (double)f2 + (double)(1.0F - f);
      this.field_187128_h = this.field_70572_as + this.field_187131_k * (double)f2;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticlePortal(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
