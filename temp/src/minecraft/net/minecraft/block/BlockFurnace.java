package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final PropertyDirection field_176447_a = BlockHorizontal.field_185512_D;
   private final boolean field_149932_b;
   private static boolean field_149934_M;

   protected BlockFurnace(boolean p_i45407_1_) {
      super(Material.field_151576_e);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176447_a, EnumFacing.NORTH));
      this.field_149932_b = p_i45407_1_;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(Blocks.field_150460_al);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176445_e(p_176213_1_, p_176213_2_, p_176213_3_);
   }

   private void func_176445_e(World p_176445_1_, BlockPos p_176445_2_, IBlockState p_176445_3_) {
      if (!p_176445_1_.field_72995_K) {
         IBlockState iblockstate = p_176445_1_.func_180495_p(p_176445_2_.func_177978_c());
         IBlockState iblockstate1 = p_176445_1_.func_180495_p(p_176445_2_.func_177968_d());
         IBlockState iblockstate2 = p_176445_1_.func_180495_p(p_176445_2_.func_177976_e());
         IBlockState iblockstate3 = p_176445_1_.func_180495_p(p_176445_2_.func_177974_f());
         EnumFacing enumfacing = (EnumFacing)p_176445_3_.func_177229_b(field_176447_a);
         if (enumfacing == EnumFacing.NORTH && iblockstate.func_185913_b() && !iblockstate1.func_185913_b()) {
            enumfacing = EnumFacing.SOUTH;
         } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.func_185913_b() && !iblockstate.func_185913_b()) {
            enumfacing = EnumFacing.NORTH;
         } else if (enumfacing == EnumFacing.WEST && iblockstate2.func_185913_b() && !iblockstate3.func_185913_b()) {
            enumfacing = EnumFacing.EAST;
         } else if (enumfacing == EnumFacing.EAST && iblockstate3.func_185913_b() && !iblockstate2.func_185913_b()) {
            enumfacing = EnumFacing.WEST;
         }

         p_176445_1_.func_180501_a(p_176445_2_, p_176445_3_.func_177226_a(field_176447_a, enumfacing), 2);
      }
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (this.field_149932_b) {
         EnumFacing enumfacing = (EnumFacing)p_180655_1_.func_177229_b(field_176447_a);
         double d0 = (double)p_180655_3_.func_177958_n() + 0.5D;
         double d1 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble() * 6.0D / 16.0D;
         double d2 = (double)p_180655_3_.func_177952_p() + 0.5D;
         double d3 = 0.52D;
         double d4 = p_180655_4_.nextDouble() * 0.6D - 0.3D;
         if (p_180655_4_.nextDouble() < 0.1D) {
            p_180655_2_.func_184134_a((double)p_180655_3_.func_177958_n() + 0.5D, (double)p_180655_3_.func_177956_o(), (double)p_180655_3_.func_177952_p() + 0.5D, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         }

         switch(enumfacing) {
         case WEST:
            p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            p_180655_2_.func_175688_a(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            break;
         case EAST:
            p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            p_180655_2_.func_175688_a(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
            break;
         case NORTH:
            p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
            p_180655_2_.func_175688_a(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
            break;
         case SOUTH:
            p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            p_180655_2_.func_175688_a(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityFurnace) {
            p_180639_4_.func_71007_a((TileEntityFurnace)tileentity);
            p_180639_4_.func_71029_a(StatList.field_188061_aa);
         }

         return true;
      }
   }

   public static void func_176446_a(boolean p_176446_0_, World p_176446_1_, BlockPos p_176446_2_) {
      IBlockState iblockstate = p_176446_1_.func_180495_p(p_176446_2_);
      TileEntity tileentity = p_176446_1_.func_175625_s(p_176446_2_);
      field_149934_M = true;
      if (p_176446_0_) {
         p_176446_1_.func_180501_a(p_176446_2_, Blocks.field_150470_am.func_176223_P().func_177226_a(field_176447_a, iblockstate.func_177229_b(field_176447_a)), 3);
         p_176446_1_.func_180501_a(p_176446_2_, Blocks.field_150470_am.func_176223_P().func_177226_a(field_176447_a, iblockstate.func_177229_b(field_176447_a)), 3);
      } else {
         p_176446_1_.func_180501_a(p_176446_2_, Blocks.field_150460_al.func_176223_P().func_177226_a(field_176447_a, iblockstate.func_177229_b(field_176447_a)), 3);
         p_176446_1_.func_180501_a(p_176446_2_, Blocks.field_150460_al.func_176223_P().func_177226_a(field_176447_a, iblockstate.func_177229_b(field_176447_a)), 3);
      }

      field_149934_M = false;
      if (tileentity != null) {
         tileentity.func_145829_t();
         p_176446_1_.func_175690_a(p_176446_2_, tileentity);
      }

   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityFurnace();
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_176447_a, p_180642_8_.func_174811_aO().func_176734_d());
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_.func_177226_a(field_176447_a, p_180633_4_.func_174811_aO().func_176734_d()), 2);
      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityFurnace) {
            ((TileEntityFurnace)tileentity).func_145951_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (!field_149934_M) {
         TileEntity tileentity = p_180663_1_.func_175625_s(p_180663_2_);
         if (tileentity instanceof TileEntityFurnace) {
            InventoryHelper.func_180175_a(p_180663_1_, p_180663_2_, (TileEntityFurnace)tileentity);
            p_180663_1_.func_175666_e(p_180663_2_, this);
         }
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return Container.func_178144_a(p_180641_2_.func_175625_s(p_180641_3_));
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Blocks.field_150460_al);
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing = EnumFacing.func_82600_a(p_176203_1_);
      if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.func_176223_P().func_177226_a(field_176447_a, enumfacing);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_176447_a)).func_176745_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176447_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176447_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176447_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176447_a});
   }
}
