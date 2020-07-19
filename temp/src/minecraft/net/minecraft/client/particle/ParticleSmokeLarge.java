package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSmokeLarge extends ParticleSmokeNormal {
   protected ParticleSmokeLarge(World p_i1201_1_, double p_i1201_2_, double p_i1201_4_, double p_i1201_6_, double p_i1201_8_, double p_i1201_10_, double p_i1201_12_) {
      super(p_i1201_1_, p_i1201_2_, p_i1201_4_, p_i1201_6_, p_i1201_8_, p_i1201_10_, p_i1201_12_, 2.5F);
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSmokeLarge(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
