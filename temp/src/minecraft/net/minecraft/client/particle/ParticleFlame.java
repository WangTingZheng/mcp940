package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFlame extends Particle {
   private final float field_70562_a;

   protected ParticleFlame(World p_i1209_1_, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_, double p_i1209_10_, double p_i1209_12_) {
      super(p_i1209_1_, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
      this.field_187129_i = this.field_187129_i * 0.009999999776482582D + p_i1209_8_;
      this.field_187130_j = this.field_187130_j * 0.009999999776482582D + p_i1209_10_;
      this.field_187131_k = this.field_187131_k * 0.009999999776482582D + p_i1209_12_;
      this.field_187126_f += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_187127_g += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_187128_h += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_70562_a = this.field_70544_f;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
      this.func_70536_a(48);
   }

   public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
      this.func_187108_a(this.func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
      this.func_187118_j();
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e;
      this.field_70544_f = this.field_70562_a * (1.0F - f * f * 0.5F);
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public int func_189214_a(float p_189214_1_) {
      float f = ((float)this.field_70546_d + p_189214_1_) / (float)this.field_70547_e;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      int i = super.func_189214_a(p_189214_1_);
      int j = i & 255;
      int k = i >> 16 & 255;
      j = j + (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9599999785423279D;
      this.field_187130_j *= 0.9599999785423279D;
      this.field_187131_k *= 0.9599999785423279D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleFlame(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
