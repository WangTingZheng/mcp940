package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockContainer {
   protected static final AxisAlignedBB field_185567_a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

   protected BlockEnchantmentTable() {
      super(Material.field_151576_e, MapColor.field_151645_D);
      this.func_149713_g(0);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185567_a;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      super.func_180655_c(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);

      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            if (i > -2 && i < 2 && j == -1) {
               j = 2;
            }

            if (p_180655_4_.nextInt(16) == 0) {
               for(int k = 0; k <= 1; ++k) {
                  BlockPos blockpos = p_180655_3_.func_177982_a(i, k, j);
                  if (p_180655_2_.func_180495_p(blockpos).func_177230_c() == Blocks.field_150342_X) {
                     if (!p_180655_2_.func_175623_d(p_180655_3_.func_177982_a(i / 2, 0, j / 2))) {
                        break;
                     }

                     p_180655_2_.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, (double)p_180655_3_.func_177958_n() + 0.5D, (double)p_180655_3_.func_177956_o() + 2.0D, (double)p_180655_3_.func_177952_p() + 0.5D, (double)((float)i + p_180655_4_.nextFloat()) - 0.5D, (double)((float)k - p_180655_4_.nextFloat() - 1.0F), (double)((float)j + p_180655_4_.nextFloat()) - 0.5D);
                  }
               }
            }
         }
      }

   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityEnchantmentTable();
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityEnchantmentTable) {
            p_180639_4_.func_180468_a((TileEntityEnchantmentTable)tileentity);
         }

         return true;
      }
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      super.func_180633_a(p_180633_1_, p_180633_2_, p_180633_3_, p_180633_4_, p_180633_5_);
      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityEnchantmentTable) {
            ((TileEntityEnchantmentTable)tileentity).func_145920_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
