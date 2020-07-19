package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer {
   private static final IBlockState field_177859_b = Blocks.field_150350_a.func_176223_P();
   private final char[] field_177860_a = new char[65536];

   public IBlockState func_177856_a(int p_177856_1_, int p_177856_2_, int p_177856_3_) {
      IBlockState iblockstate = Block.field_176229_d.func_148745_a(this.field_177860_a[func_186137_b(p_177856_1_, p_177856_2_, p_177856_3_)]);
      return iblockstate == null ? field_177859_b : iblockstate;
   }

   public void func_177855_a(int p_177855_1_, int p_177855_2_, int p_177855_3_, IBlockState p_177855_4_) {
      this.field_177860_a[func_186137_b(p_177855_1_, p_177855_2_, p_177855_3_)] = (char)Block.field_176229_d.func_148747_b(p_177855_4_);
   }

   private static int func_186137_b(int p_186137_0_, int p_186137_1_, int p_186137_2_) {
      return p_186137_0_ << 12 | p_186137_2_ << 8 | p_186137_1_;
   }

   public int func_186138_a(int p_186138_1_, int p_186138_2_) {
      int i = (p_186138_1_ << 12 | p_186138_2_ << 8) + 256 - 1;

      for(int j = 255; j >= 0; --j) {
         IBlockState iblockstate = Block.field_176229_d.func_148745_a(this.field_177860_a[i + j]);
         if (iblockstate != null && iblockstate != field_177859_b) {
            return j;
         }
      }

      return 0;
   }
}
