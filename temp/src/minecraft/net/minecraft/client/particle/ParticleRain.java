package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleRain extends Particle {
   protected ParticleRain(World p_i1235_1_, double p_i1235_2_, double p_i1235_4_, double p_i1235_6_) {
      super(p_i1235_1_, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i *= 0.30000001192092896D;
      this.field_187130_j = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
      this.field_187131_k *= 0.30000001192092896D;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(19 + this.field_187136_p.nextInt(4));
      this.func_187115_a(0.01F, 0.01F);
      this.field_70545_g = 0.06F;
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
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
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

      if (this.field_187132_l) {
         if (Math.random() < 0.5D) {
            this.func_187112_i();
         }

         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

      BlockPos blockpos = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      IBlockState iblockstate = this.field_187122_b.func_180495_p(blockpos);
      Material material = iblockstate.func_185904_a();
      if (material.func_76224_d() || material.func_76220_a()) {
         double d0;
         if (iblockstate.func_177230_c() instanceof BlockLiquid) {
            d0 = (double)(1.0F - BlockLiquid.func_149801_b(((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue()));
         } else {
            d0 = iblockstate.func_185900_c(this.field_187122_b, blockpos).field_72337_e;
         }

         double d1 = (double)MathHelper.func_76128_c(this.field_187127_g) + d0;
         if (this.field_187127_g < d1) {
            this.func_187112_i();
         }
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleRain(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_);
      }
   }
}
