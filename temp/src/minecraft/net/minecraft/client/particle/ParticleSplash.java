package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSplash extends ParticleRain {
   protected ParticleSplash(World p_i1230_1_, double p_i1230_2_, double p_i1230_4_, double p_i1230_6_, double p_i1230_8_, double p_i1230_10_, double p_i1230_12_) {
      super(p_i1230_1_, p_i1230_2_, p_i1230_4_, p_i1230_6_);
      this.field_70545_g = 0.04F;
      this.func_94053_h();
      if (p_i1230_10_ == 0.0D && (p_i1230_8_ != 0.0D || p_i1230_12_ != 0.0D)) {
         this.field_187129_i = p_i1230_8_;
         this.field_187130_j = p_i1230_10_ + 0.1D;
         this.field_187131_k = p_i1230_12_;
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSplash(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
