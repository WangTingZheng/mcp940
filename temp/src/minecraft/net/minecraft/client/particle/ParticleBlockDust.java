package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class ParticleBlockDust extends ParticleDigging {
   protected ParticleBlockDust(World p_i46281_1_, double p_i46281_2_, double p_i46281_4_, double p_i46281_6_, double p_i46281_8_, double p_i46281_10_, double p_i46281_12_, IBlockState p_i46281_14_) {
      super(p_i46281_1_, p_i46281_2_, p_i46281_4_, p_i46281_6_, p_i46281_8_, p_i46281_10_, p_i46281_12_, p_i46281_14_);
      this.field_187129_i = p_i46281_8_;
      this.field_187130_j = p_i46281_10_;
      this.field_187131_k = p_i46281_12_;
   }

   public static class Factory implements IParticleFactory {
      @Nullable
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         IBlockState iblockstate = Block.func_176220_d(p_178902_15_[0]);
         return iblockstate.func_185901_i() == EnumBlockRenderType.INVISIBLE ? null : (new ParticleBlockDust(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, iblockstate)).func_174845_l();
      }
   }
}
