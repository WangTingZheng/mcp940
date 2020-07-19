package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCrit extends Particle {
   float field_174839_a;

   protected ParticleCrit(World p_i46284_1_, double p_i46284_2_, double p_i46284_4_, double p_i46284_6_, double p_i46284_8_, double p_i46284_10_, double p_i46284_12_) {
      this(p_i46284_1_, p_i46284_2_, p_i46284_4_, p_i46284_6_, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0F);
   }

   protected ParticleCrit(World p_i46285_1_, double p_i46285_2_, double p_i46285_4_, double p_i46285_6_, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_) {
      super(p_i46285_1_, p_i46285_2_, p_i46285_4_, p_i46285_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i *= 0.10000000149011612D;
      this.field_187130_j *= 0.10000000149011612D;
      this.field_187131_k *= 0.10000000149011612D;
      this.field_187129_i += p_i46285_8_ * 0.4D;
      this.field_187130_j += p_i46285_10_ * 0.4D;
      this.field_187131_k += p_i46285_12_ * 0.4D;
      float f = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
      this.field_70552_h = f;
      this.field_70553_i = f;
      this.field_70551_j = f;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= p_i46285_14_;
      this.field_174839_a = this.field_70544_f;
      this.field_70547_e = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
      this.field_70547_e = (int)((float)this.field_70547_e * p_i46285_14_);
      this.func_70536_a(65);
      this.func_189213_a();
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e * 32.0F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      this.field_70544_f = this.field_174839_a * f;
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
      this.field_70553_i = (float)((double)this.field_70553_i * 0.96D);
      this.field_70551_j = (float)((double)this.field_70551_j * 0.9D);
      this.field_187129_i *= 0.699999988079071D;
      this.field_187130_j *= 0.699999988079071D;
      this.field_187131_k *= 0.699999988079071D;
      this.field_187130_j -= 0.019999999552965164D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class DamageIndicatorFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleCrit(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_ + 1.0D, p_178902_13_, 1.0F);
         particle.func_187114_a(20);
         particle.func_70536_a(67);
         return particle;
      }
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleCrit(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }

   public static class MagicFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleCrit(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         particle.func_70538_b(particle.func_70534_d() * 0.3F, particle.func_70542_f() * 0.8F, particle.func_70535_g());
         particle.func_94053_h();
         return particle;
      }
   }
}
