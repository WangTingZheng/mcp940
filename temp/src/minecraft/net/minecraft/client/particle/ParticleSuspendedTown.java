package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSuspendedTown extends Particle {
   protected ParticleSuspendedTown(World p_i1232_1_, double p_i1232_2_, double p_i1232_4_, double p_i1232_6_, double p_i1232_8_, double p_i1232_10_, double p_i1232_12_) {
      super(p_i1232_1_, p_i1232_2_, p_i1232_4_, p_i1232_6_, p_i1232_8_, p_i1232_10_, p_i1232_12_);
      float f = this.field_187136_p.nextFloat() * 0.1F + 0.2F;
      this.field_70552_h = f;
      this.field_70553_i = f;
      this.field_70551_j = f;
      this.func_70536_a(0);
      this.func_187115_a(0.02F, 0.02F);
      this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.5F;
      this.field_187129_i *= 0.019999999552965164D;
      this.field_187130_j *= 0.019999999552965164D;
      this.field_187131_k *= 0.019999999552965164D;
      this.field_70547_e = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
      this.func_187108_a(this.func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
      this.func_187118_j();
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.99D;
      this.field_187130_j *= 0.99D;
      this.field_187131_k *= 0.99D;
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSuspendedTown(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }

   public static class HappyVillagerFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleSuspendedTown(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         particle.func_70536_a(82);
         particle.func_70538_b(1.0F, 1.0F, 1.0F);
         return particle;
      }
   }
}
