package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleBubble extends Particle {
   protected ParticleBubble(World p_i1198_1_, double p_i1198_2_, double p_i1198_4_, double p_i1198_6_, double p_i1198_8_, double p_i1198_10_, double p_i1198_12_) {
      super(p_i1198_1_, p_i1198_2_, p_i1198_4_, p_i1198_6_, p_i1198_8_, p_i1198_10_, p_i1198_12_);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(32);
      this.func_187115_a(0.02F, 0.02F);
      this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.2F;
      this.field_187129_i = p_i1198_8_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.field_187130_j = p_i1198_10_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.field_187131_k = p_i1198_12_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j += 0.002D;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.8500000238418579D;
      this.field_187130_j *= 0.8500000238418579D;
      this.field_187131_k *= 0.8500000238418579D;
      if (this.field_187122_b.func_180495_p(new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h)).func_185904_a() != Material.field_151586_h) {
         this.func_187112_i();
      }

      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleBubble(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
