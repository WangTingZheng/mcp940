package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleEndRod extends ParticleSimpleAnimated {
   public ParticleEndRod(World p_i46580_1_, double p_i46580_2_, double p_i46580_4_, double p_i46580_6_, double p_i46580_8_, double p_i46580_10_, double p_i46580_12_) {
      super(p_i46580_1_, p_i46580_2_, p_i46580_4_, p_i46580_6_, 176, 8, -5.0E-4F);
      this.field_187129_i = p_i46580_8_;
      this.field_187130_j = p_i46580_10_;
      this.field_187131_k = p_i46580_12_;
      this.field_70544_f *= 0.75F;
      this.field_70547_e = 60 + this.field_187136_p.nextInt(12);
      this.func_187145_d(15916745);
   }

   public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
      this.func_187108_a(this.func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
      this.func_187118_j();
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleEndRod(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
