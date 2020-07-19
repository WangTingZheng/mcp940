package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleExplosion extends Particle {
   protected ParticleExplosion(World p_i1205_1_, double p_i1205_2_, double p_i1205_4_, double p_i1205_6_, double p_i1205_8_, double p_i1205_10_, double p_i1205_12_) {
      super(p_i1205_1_, p_i1205_2_, p_i1205_4_, p_i1205_6_, p_i1205_8_, p_i1205_10_, p_i1205_12_);
      this.field_187129_i = p_i1205_8_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      this.field_187130_j = p_i1205_10_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      this.field_187131_k = p_i1205_12_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      float f = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
      this.field_70552_h = f;
      this.field_70553_i = f;
      this.field_70551_j = f;
      this.field_70544_f = this.field_187136_p.nextFloat() * this.field_187136_p.nextFloat() * 6.0F + 1.0F;
      this.field_70547_e = (int)(16.0D / ((double)this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.field_187130_j += 0.004D;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.8999999761581421D;
      this.field_187130_j *= 0.8999999761581421D;
      this.field_187131_k *= 0.8999999761581421D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleExplosion(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
