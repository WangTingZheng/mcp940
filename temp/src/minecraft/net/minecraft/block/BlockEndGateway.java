package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndGateway extends BlockContainer {
   protected BlockEndGateway(Material p_i46687_1_) {
      super(p_i46687_1_);
      this.func_149715_a(1.0F);
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityEndGateway();
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      IBlockState iblockstate = p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_));
      Block block = iblockstate.func_177230_c();
      return !iblockstate.func_185914_p() && block != Blocks.field_185775_db;
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      TileEntity tileentity = p_180655_2_.func_175625_s(p_180655_3_);
      if (tileentity instanceof TileEntityEndGateway) {
         int i = ((TileEntityEndGateway)tileentity).func_184304_i();

         for(int j = 0; j < i; ++j) {
            double d0 = (double)((float)p_180655_3_.func_177958_n() + p_180655_4_.nextFloat());
            double d1 = (double)((float)p_180655_3_.func_177956_o() + p_180655_4_.nextFloat());
            double d2 = (double)((float)p_180655_3_.func_177952_p() + p_180655_4_.nextFloat());
            double d3 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            int k = p_180655_4_.nextInt(2) * 2 - 1;
            if (p_180655_4_.nextBoolean()) {
               d2 = (double)p_180655_3_.func_177952_p() + 0.5D + 0.25D * (double)k;
               d5 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)k);
            } else {
               d0 = (double)p_180655_3_.func_177958_n() + 0.5D + 0.25D * (double)k;
               d3 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)k);
            }

            p_180655_2_.func_175688_a(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
         }

      }
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return ItemStack.field_190927_a;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151646_E;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
