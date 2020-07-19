package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockBehaviors {
   boolean func_189547_a(World var1, BlockPos var2, int var3, int var4);

   void func_189546_a(World var1, BlockPos var2, Block var3, BlockPos var4);
}
