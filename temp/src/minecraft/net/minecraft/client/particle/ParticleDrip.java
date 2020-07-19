package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleDrip extends Particle {
   private final Material field_70563_a;
   private int field_70564_aq;

   protected ParticleDrip(World p_i1203_1_, double p_i1203_2_, double p_i1203_4_, double p_i1203_6_, Material p_i1203_8_) {
      super(p_i1203_1_, p_i1203_2_, p_i1203_4_, p_i1203_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i = 0.0D;
      this.field_187130_j = 0.0D;
      this.field_187131_k = 0.0D;
      if (p_i1203_8_ == Material.field_151586_h) {
         this.field_70552_h = 0.0F;
         this.field_70553_i = 0.0F;
         this.field_70551_j = 1.0F;
      } else {
         this.field_70552_h = 1.0F;
         this.field_70553_i = 0.0F;
         this.field_70551_j = 0.0F;
      }

      this.func_70536_a(113);
      this.func_187115_a(0.01F, 0.01F);
      this.field_70545_g = 0.06F;
      this.field_70563_a = p_i1203_8_;
      this.field_70564_aq = 40;
      this.field_70547_e = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
      this.field_187129_i = 0.0D;
      this.field_187130_j = 0.0D;
      this.field_187131_k = 0.0D;
   }

   public int func_189214_a(float p_189214_1_) {
      return this.field_70563_a == Material.field_151586_h ? super.func_189214_a(p_189214_1_) : 257;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70563_a == Material.field_151586_h) {
         this.field_70552_h = 0.2F;
         this.field_70553_i = 0.3F;
         this.field_70551_j = 1.0F;
      } else {
         this.field_70552_h = 1.0F;
         this.field_70553_i = 16.0F / (float)(40 - this.field_70564_aq + 16);
         this.field_70551_j = 4.0F / (float)(40 - this.field_70564_aq + 8);
      }

      this.field_187130_j -= (double)this.field_70545_g;
      if (this.field_70564_aq-- > 0) {
         this.field_187129_i *= 0.02D;
         this.field_187130_j *= 0.02D;
         this.field_187131_k *= 0.02D;
         this.func_70536_a(113);
      } else {
         this.func_70536_a(112);
      }

      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9800000190734863D;
      this.field_187130_j *= 0.9800000190734863D;
      this.field_187131_k *= 0.9800000190734863D;
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }

      if (this.field_187132_l) {
         if (this.field_70563_a == Material.field_151586_h) {
            this.func_187112_i();
            this.field_187122_b.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_187126_f, this.field_187127_g, this.field_187128_h, 0.0D, 0.0D, 0.0D);
         } else {
            this.func_70536_a(114);
         }

         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

      BlockPos blockpos = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      IBlockState iblockstate = this.field_187122_b.func_180495_p(blockpos);
      Material material = iblockstate.func_185904_a();
      if (material.func_76224_d() || material.func_76220_a()) {
         double d0 = 0.0D;
         if (iblockstate.func_177230_c() instanceof BlockLiquid) {
            d0 = (double)BlockLiquid.func_149801_b(((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue());
         }

         double d1 = (double)(MathHelper.func_76128_c(this.field_187127_g) + 1) - d0;
         if (this.field_187127_g < d1) {
            this.func_187112_i();
         }
      }

   }

   public static class LavaFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleDrip(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, Material.field_151587_i);
      }
   }

   public static class WaterFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleDrip(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, Material.field_151586_h);
      }
   }
}
