package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFallingDust extends Particle {
   float field_190018_a;
   final float field_190019_b;

   protected ParticleFallingDust(World p_i47135_1_, double p_i47135_2_, double p_i47135_4_, double p_i47135_6_, float p_i47135_8_, float p_i47135_9_, float p_i47135_10_) {
      super(p_i47135_1_, p_i47135_2_, p_i47135_4_, p_i47135_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i = 0.0D;
      this.field_187130_j = 0.0D;
      this.field_187131_k = 0.0D;
      this.field_70552_h = p_i47135_8_;
      this.field_70553_i = p_i47135_9_;
      this.field_70551_j = p_i47135_10_;
      float f = 0.9F;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= 0.9F;
      this.field_190018_a = this.field_70544_f;
      this.field_70547_e = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
      this.field_70547_e = (int)((float)this.field_70547_e * 0.9F);
      this.field_190019_b = ((float)Math.random() - 0.5F) * 0.1F;
      this.field_190014_F = (float)Math.random() * 6.2831855F;
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e * 32.0F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      this.field_70544_f = this.field_190018_a * f;
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.field_190015_G = this.field_190014_F;
      this.field_190014_F += 3.1415927F * this.field_190019_b * 2.0F;
      if (this.field_187132_l) {
         this.field_190015_G = this.field_190014_F = 0.0F;
      }

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187130_j -= 0.003000000026077032D;
      this.field_187130_j = Math.max(this.field_187130_j, -0.14000000059604645D);
   }

   public static class Factory implements IParticleFactory {
      @Nullable
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         IBlockState iblockstate = Block.func_176220_d(p_178902_15_[0]);
         if (iblockstate.func_177230_c() != Blocks.field_150350_a && iblockstate.func_185901_i() == EnumBlockRenderType.INVISIBLE) {
            return null;
         } else {
            int i = Minecraft.func_71410_x().func_184125_al().func_189991_a(iblockstate, p_178902_2_, new BlockPos(p_178902_3_, p_178902_5_, p_178902_7_));
            if (iblockstate.func_177230_c() instanceof BlockFalling) {
               i = ((BlockFalling)iblockstate.func_177230_c()).func_189876_x(iblockstate);
            }

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            return new ParticleFallingDust(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, f, f1, f2);
         }
      }
   }
}
