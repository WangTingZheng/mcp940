package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;

public class BlockStatePaletteRegistry implements IBlockStatePalette {
   public int func_186041_a(IBlockState p_186041_1_) {
      int i = Block.field_176229_d.func_148747_b(p_186041_1_);
      return i == -1 ? 0 : i;
   }

   public IBlockState func_186039_a(int p_186039_1_) {
      IBlockState iblockstate = Block.field_176229_d.func_148745_a(p_186039_1_);
      return iblockstate == null ? Blocks.field_150350_a.func_176223_P() : iblockstate;
   }

   public void func_186038_a(PacketBuffer p_186038_1_) {
      p_186038_1_.func_150792_a();
   }

   public void func_186037_b(PacketBuffer p_186037_1_) {
      p_186037_1_.func_150787_b(0);
   }

   public int func_186040_a() {
      return PacketBuffer.func_150790_a(0);
   }
}
