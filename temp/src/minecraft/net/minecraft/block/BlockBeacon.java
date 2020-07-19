package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {
   public BlockBeacon() {
      super(Material.field_151592_s, MapColor.field_151648_G);
      this.func_149711_c(3.0F);
      this.func_149647_a(CreativeTabs.field_78026_f);
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityBeacon();
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityBeacon) {
            p_180639_4_.func_71007_a((TileEntityBeacon)tileentity);
            p_180639_4_.func_71029_a(StatList.field_188082_P);
         }

         return true;
      }
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      super.func_180633_a(p_180633_1_, p_180633_2_, p_180633_3_, p_180633_4_, p_180633_5_);
      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityBeacon) {
            ((TileEntityBeacon)tileentity).func_145999_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      TileEntity tileentity = p_189540_2_.func_175625_s(p_189540_3_);
      if (tileentity instanceof TileEntityBeacon) {
         ((TileEntityBeacon)tileentity).func_174908_m();
         p_189540_2_.func_175641_c(p_189540_3_, this, 1, 0);
      }

   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public static void func_176450_d(final World p_176450_0_, final BlockPos p_176450_1_) {
      HttpUtil.field_180193_a.submit(new Runnable() {
         public void run() {
            Chunk chunk = p_176450_0_.func_175726_f(p_176450_1_);

            for(int i = p_176450_1_.func_177956_o() - 1; i >= 0; --i) {
               final BlockPos blockpos = new BlockPos(p_176450_1_.func_177958_n(), i, p_176450_1_.func_177952_p());
               if (!chunk.func_177444_d(blockpos)) {
                  break;
               }

               IBlockState iblockstate = p_176450_0_.func_180495_p(blockpos);
               if (iblockstate.func_177230_c() == Blocks.field_150461_bJ) {
                  ((WorldServer)p_176450_0_).func_152344_a(new Runnable() {
                     public void run() {
                        TileEntity tileentity = p_176450_0_.func_175625_s(blockpos);
                        if (tileentity instanceof TileEntityBeacon) {
                           ((TileEntityBeacon)tileentity).func_174908_m();
                           p_176450_0_.func_175641_c(blockpos, Blocks.field_150461_bJ, 1, 0);
                        }

                     }
                  });
               }
            }

         }
      });
   }
}
