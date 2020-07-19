package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {
   public static final PropertyDirection field_176449_a = BlockHorizontal.field_185512_D;
   public static final PropertyInteger field_176448_b = PropertyInteger.func_177719_a("rotation", 0, 15);
   protected static final AxisAlignedBB field_185550_c = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

   protected BlockBanner() {
      super(Material.field_151575_d);
   }

   public String func_149732_F() {
      return I18n.func_74838_a("item.banner.white.name");
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return true;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_181623_g() {
      return true;
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityBanner();
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_179564_cE;
   }

   private ItemStack func_185549_e(World p_185549_1_, BlockPos p_185549_2_) {
      TileEntity tileentity = p_185549_1_.func_175625_s(p_185549_2_);
      return tileentity instanceof TileEntityBanner ? ((TileEntityBanner)tileentity).func_190615_l() : ItemStack.field_190927_a;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      ItemStack itemstack = this.func_185549_e(p_185473_1_, p_185473_2_);
      return itemstack.func_190926_b() ? new ItemStack(Items.field_179564_cE) : itemstack;
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      ItemStack itemstack = this.func_185549_e(p_180653_1_, p_180653_2_);
      if (itemstack.func_190926_b()) {
         super.func_180653_a(p_180653_1_, p_180653_2_, p_180653_3_, p_180653_4_, p_180653_5_);
      } else {
         func_180635_a(p_180653_1_, p_180653_2_, itemstack);
      }

   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return !this.func_181087_e(p_176196_1_, p_176196_2_) && super.func_176196_c(p_176196_1_, p_176196_2_);
   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {
      if (p_180657_5_ instanceof TileEntityBanner) {
         TileEntityBanner tileentitybanner = (TileEntityBanner)p_180657_5_;
         ItemStack itemstack = tileentitybanner.func_190615_l();
         func_180635_a(p_180657_1_, p_180657_3_, itemstack);
      } else {
         super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, (TileEntity)null, p_180657_6_);
      }

   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public static class BlockBannerHanging extends BlockBanner {
      protected static final AxisAlignedBB field_185551_d = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 0.78125D, 1.0D);
      protected static final AxisAlignedBB field_185552_e = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.78125D, 0.125D);
      protected static final AxisAlignedBB field_185553_f = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 0.78125D, 1.0D);
      protected static final AxisAlignedBB field_185554_g = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.78125D, 1.0D);

      public BlockBannerHanging() {
         this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176449_a, EnumFacing.NORTH));
      }

      public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
         return p_185499_1_.func_177226_a(field_176449_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176449_a)));
      }

      public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
         return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176449_a)));
      }

      public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
         switch((EnumFacing)p_185496_1_.func_177229_b(field_176449_a)) {
         case NORTH:
         default:
            return field_185551_d;
         case SOUTH:
            return field_185552_e;
         case WEST:
            return field_185553_f;
         case EAST:
            return field_185554_g;
         }
      }

      public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
         EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_176449_a);
         if (!p_189540_2_.func_180495_p(p_189540_3_.func_177972_a(enumfacing.func_176734_d())).func_185904_a().func_76220_a()) {
            this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
            p_189540_2_.func_175698_g(p_189540_3_);
         }

         super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
      }

      public IBlockState func_176203_a(int p_176203_1_) {
         EnumFacing enumfacing = EnumFacing.func_82600_a(p_176203_1_);
         if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
         }

         return this.func_176223_P().func_177226_a(field_176449_a, enumfacing);
      }

      public int func_176201_c(IBlockState p_176201_1_) {
         return ((EnumFacing)p_176201_1_.func_177229_b(field_176449_a)).func_176745_a();
      }

      protected BlockStateContainer func_180661_e() {
         return new BlockStateContainer(this, new IProperty[]{field_176449_a});
      }
   }

   public static class BlockBannerStanding extends BlockBanner {
      public BlockBannerStanding() {
         this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176448_b, Integer.valueOf(0)));
      }

      public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
         return field_185550_c;
      }

      public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
         return p_185499_1_.func_177226_a(field_176448_b, Integer.valueOf(p_185499_2_.func_185833_a(((Integer)p_185499_1_.func_177229_b(field_176448_b)).intValue(), 16)));
      }

      public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
         return p_185471_1_.func_177226_a(field_176448_b, Integer.valueOf(p_185471_2_.func_185802_a(((Integer)p_185471_1_.func_177229_b(field_176448_b)).intValue(), 16)));
      }

      public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
         if (!p_189540_2_.func_180495_p(p_189540_3_.func_177977_b()).func_185904_a().func_76220_a()) {
            this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
            p_189540_2_.func_175698_g(p_189540_3_);
         }

         super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
      }

      public IBlockState func_176203_a(int p_176203_1_) {
         return this.func_176223_P().func_177226_a(field_176448_b, Integer.valueOf(p_176203_1_));
      }

      public int func_176201_c(IBlockState p_176201_1_) {
         return ((Integer)p_176201_1_.func_177229_b(field_176448_b)).intValue();
      }

      protected BlockStateContainer func_180661_e() {
         return new BlockStateContainer(this, new IProperty[]{field_176448_b});
      }
   }
}
