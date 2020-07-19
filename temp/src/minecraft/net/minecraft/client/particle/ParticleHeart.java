package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleHeart extends Particle {
   float field_70575_a;

   protected ParticleHeart(World p_i1211_1_, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_) {
      this(p_i1211_1_, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
   }

   protected ParticleHeart(World p_i46354_1_, double p_i46354_2_, double p_i46354_4_, double p_i46354_6_, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float p_i46354_14_) {
      super(p_i46354_1_, p_i46354_2_, p_i46354_4_, p_i46354_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i *= 0.009999999776482582D;
      this.field_187130_j *= 0.009999999776482582D;
      this.field_187131_k *= 0.009999999776482582D;
      this.field_187130_j += 0.1D;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= p_i46354_14_;
      this.field_70575_a = this.field_70544_f;
      this.field_70547_e = 16;
      this.func_70536_a(80);
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e * 32.0F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      this.field_70544_f = this.field_70575_a * f;
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_187127_g == this.field_187124_d) {
         this.field_187129_i *= 1.1D;
         this.field_187131_k *= 1.1D;
      }

      this.field_187129_i *= 0.8600000143051147D;
      this.field_187130_j *= 0.8600000143051147D;
      this.field_187131_k *= 0.8600000143051147D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class AngryVillagerFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleHeart(p_178902_2_, p_178902_3_, p_178902_5_ + 0.5D, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         particle.func_70536_a(81);
         particle.func_70538_b(1.0F, 1.0F, 1.0F);
         return particle;
      }
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleHeart(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
