package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleSuspend extends Particle {
   protected ParticleSuspend(World p_i1231_1_, double p_i1231_2_, double p_i1231_4_, double p_i1231_6_, double p_i1231_8_, double p_i1231_10_, double p_i1231_12_) {
      super(p_i1231_1_, p_i1231_2_, p_i1231_4_ - 0.125D, p_i1231_6_, p_i1231_8_, p_i1231_10_, p_i1231_12_);
      this.field_70552_h = 0.4F;
      this.field_70553_i = 0.4F;
      this.field_70551_j = 0.7F;
      this.func_70536_a(0);
      this.func_187115_a(0.01F, 0.01F);
      this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.2F;
      this.field_187129_i = p_i1231_8_ * 0.0D;
      this.field_187130_j = p_i1231_10_ * 0.0D;
      this.field_187131_k = p_i1231_12_ * 0.0D;
      this.field_70547_e = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_187122_b.func_180495_p(new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h)).func_185904_a() != Material.field_151586_h) {
         this.func_187112_i();
      }

      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSuspend(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
