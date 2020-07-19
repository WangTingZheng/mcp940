package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleWaterWake extends Particle {
   protected ParticleWaterWake(World p_i45073_1_, double p_i45073_2_, double p_i45073_4_, double p_i45073_6_, double p_i45073_8_, double p_i45073_10_, double p_i45073_12_) {
      super(p_i45073_1_, p_i45073_2_, p_i45073_4_, p_i45073_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i *= 0.30000001192092896D;
      this.field_187130_j = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
      this.field_187131_k *= 0.30000001192092896D;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(19);
      this.func_187115_a(0.01F, 0.01F);
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.field_70545_g = 0.0F;
      this.field_187129_i = p_i45073_8_;
      this.field_187130_j = p_i45073_10_;
      this.field_187131_k = p_i45073_12_;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j -= (double)this.field_70545_g;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9800000190734863D;
      this.field_187130_j *= 0.9800000190734863D;
      this.field_187131_k *= 0.9800000190734863D;
      int i = 60 - this.field_70547_e;
      float f = (float)i * 0.001F;
      this.func_187115_a(f, f);
      this.func_70536_a(19 + i % 4);
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleWaterWake(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
