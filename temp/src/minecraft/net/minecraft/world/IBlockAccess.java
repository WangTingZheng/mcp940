package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public interface IBlockAccess {
   @Nullable
   TileEntity func_175625_s(BlockPos var1);

   int func_175626_b(BlockPos var1, int var2);

   IBlockState func_180495_p(BlockPos var1);

   boolean func_175623_d(BlockPos var1);

   Biome func_180494_b(BlockPos var1);

   int func_175627_a(BlockPos var1, EnumFacing var2);

   WorldType func_175624_G();
}
