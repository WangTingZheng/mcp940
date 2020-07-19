package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   public static final PropertyDirection field_176430_a = PropertyDirection.func_177712_a("facing", new Predicate<EnumFacing>() {
      public boolean apply(@Nullable EnumFacing p_apply_1_) {
         return p_apply_1_ != EnumFacing.UP;
      }
   });
   public static final PropertyBool field_176429_b = PropertyBool.func_177716_a("enabled");
   protected static final AxisAlignedBB field_185571_c = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_185572_d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
   protected static final AxisAlignedBB field_185573_e = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185574_f = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185575_g = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

   public BlockHopper() {
      super(Material.field_151573_f, MapColor.field_151665_m);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176430_a, EnumFacing.DOWN).func_177226_a(field_176429_b, Boolean.valueOf(true)));
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185505_j;
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185571_c);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185575_g);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185574_f);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185572_d);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185573_e);
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      EnumFacing enumfacing = p_180642_3_.func_176734_d();
      if (enumfacing == EnumFacing.UP) {
         enumfacing = EnumFacing.DOWN;
      }

      return this.func_176223_P().func_177226_a(field_176430_a, enumfacing).func_177226_a(field_176429_b, Boolean.valueOf(true));
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityHopper();
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      super.func_180633_a(p_180633_1_, p_180633_2_, p_180633_3_, p_180633_4_, p_180633_5_);
      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityHopper) {
            ((TileEntityHopper)tileentity).func_190575_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public boolean func_185481_k(IBlockState p_185481_1_) {
      return true;
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176427_e(p_176213_1_, p_176213_2_, p_176213_3_);
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityHopper) {
            p_180639_4_.func_71007_a((TileEntityHopper)tileentity);
            p_180639_4_.func_71029_a(StatList.field_188084_R);
         }

         return true;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      this.func_176427_e(p_189540_2_, p_189540_3_, p_189540_1_);
   }

   private void func_176427_e(World p_176427_1_, BlockPos p_176427_2_, IBlockState p_176427_3_) {
      boolean flag = !p_176427_1_.func_175640_z(p_176427_2_);
      if (flag != ((Boolean)p_176427_3_.func_177229_b(field_176429_b)).booleanValue()) {
         p_176427_1_.func_180501_a(p_176427_2_, p_176427_3_.func_177226_a(field_176429_b, Boolean.valueOf(flag)), 4);
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      TileEntity tileentity = p_180663_1_.func_175625_s(p_180663_2_);
      if (tileentity instanceof TileEntityHopper) {
         InventoryHelper.func_180175_a(p_180663_1_, p_180663_2_, (TileEntityHopper)tileentity);
         p_180663_1_.func_175666_e(p_180663_2_, this);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return true;
   }

   public static EnumFacing func_176428_b(int p_176428_0_) {
      return EnumFacing.func_82600_a(p_176428_0_ & 7);
   }

   public static boolean func_149917_c(int p_149917_0_) {
      return (p_149917_0_ & 8) != 8;
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return Container.func_178144_a(p_180641_2_.func_175625_s(p_180641_3_));
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176430_a, func_176428_b(p_176203_1_)).func_177226_a(field_176429_b, Boolean.valueOf(func_149917_c(p_176203_1_)));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176430_a)).func_176745_a();
      if (!((Boolean)p_176201_1_.func_177229_b(field_176429_b)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176430_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176430_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176430_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176430_a, field_176429_b});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.UP ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
   }
}
