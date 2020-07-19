package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSpit extends ParticleExplosion {
   protected ParticleSpit(World p_i47221_1_, double p_i47221_2_, double p_i47221_4_, double p_i47221_6_, double p_i47221_8_, double p_i47221_10_, double p_i47221_12_) {
      super(p_i47221_1_, p_i47221_2_, p_i47221_4_, p_i47221_6_, p_i47221_8_, p_i47221_10_, p_i47221_12_);
      this.field_70545_g = 0.5F;
   }

   public void func_189213_a() {
      super.func_189213_a();
      this.field_187130_j -= 0.004D + 0.04D * (double)this.field_70545_g;
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSpit(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
