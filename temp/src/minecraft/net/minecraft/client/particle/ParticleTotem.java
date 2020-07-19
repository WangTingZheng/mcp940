package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleTotem extends ParticleSimpleAnimated {
   public ParticleTotem(World p_i47220_1_, double p_i47220_2_, double p_i47220_4_, double p_i47220_6_, double p_i47220_8_, double p_i47220_10_, double p_i47220_12_) {
      super(p_i47220_1_, p_i47220_2_, p_i47220_4_, p_i47220_6_, 176, 8, -0.05F);
      this.field_187129_i = p_i47220_8_;
      this.field_187130_j = p_i47220_10_;
      this.field_187131_k = p_i47220_12_;
      this.field_70544_f *= 0.75F;
      this.field_70547_e = 60 + this.field_187136_p.nextInt(12);
      if (this.field_187136_p.nextInt(4) == 0) {
         this.func_70538_b(0.6F + this.field_187136_p.nextFloat() * 0.2F, 0.6F + this.field_187136_p.nextFloat() * 0.3F, this.field_187136_p.nextFloat() * 0.2F);
      } else {
         this.func_70538_b(0.1F + this.field_187136_p.nextFloat() * 0.2F, 0.4F + this.field_187136_p.nextFloat() * 0.3F, this.field_187136_p.nextFloat() * 0.2F);
      }

      this.func_191238_f(0.6F);
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleTotem(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
