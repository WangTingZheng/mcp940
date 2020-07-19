package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrewingStand extends BlockContainer {
   public static final PropertyBool[] field_176451_a = new PropertyBool[]{PropertyBool.func_177716_a("has_bottle_0"), PropertyBool.func_177716_a("has_bottle_1"), PropertyBool.func_177716_a("has_bottle_2")};
   protected static final AxisAlignedBB field_185555_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
   protected static final AxisAlignedBB field_185556_c = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);

   public BlockBrewingStand() {
      super(Material.field_151573_f);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176451_a[0], Boolean.valueOf(false)).func_177226_a(field_176451_a[1], Boolean.valueOf(false)).func_177226_a(field_176451_a[2], Boolean.valueOf(false)));
   }

   public String func_149732_F() {
      return I18n.func_74838_a("item.brewingStand.name");
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityBrewingStand();
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185556_c);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185555_b);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185555_b;
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityBrewingStand) {
            p_180639_4_.func_71007_a((TileEntityBrewingStand)tileentity);
            p_180639_4_.func_71029_a(StatList.field_188081_O);
         }

         return true;
      }
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)tileentity).func_145937_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      double d0 = (double)((float)p_180655_3_.func_177958_n() + 0.4F + p_180655_4_.nextFloat() * 0.2F);
      double d1 = (double)((float)p_180655_3_.func_177956_o() + 0.7F + p_180655_4_.nextFloat() * 0.3F);
      double d2 = (double)((float)p_180655_3_.func_177952_p() + 0.4F + p_180655_4_.nextFloat() * 0.2F);
      p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      TileEntity tileentity = p_180663_1_.func_175625_s(p_180663_2_);
      if (tileentity instanceof TileEntityBrewingStand) {
         InventoryHelper.func_180175_a(p_180663_1_, p_180663_2_, (TileEntityBrewingStand)tileentity);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151067_bt;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151067_bt);
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return Container.func_178144_a(p_180641_2_.func_175625_s(p_180641_3_));
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      IBlockState iblockstate = this.func_176223_P();

      for(int i = 0; i < 3; ++i) {
         iblockstate = iblockstate.func_177226_a(field_176451_a[i], Boolean.valueOf((p_176203_1_ & 1 << i) > 0));
      }

      return iblockstate;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;

      for(int j = 0; j < 3; ++j) {
         if (((Boolean)p_176201_1_.func_177229_b(field_176451_a[j])).booleanValue()) {
            i |= 1 << j;
         }
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176451_a[0], field_176451_a[1], field_176451_a[2]});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
