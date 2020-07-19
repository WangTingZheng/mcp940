package net.minecraft.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends Block {
   public static final PropertyBool field_176256_a = PropertyBool.func_177716_a("up");
   public static final PropertyBool field_176254_b = PropertyBool.func_177716_a("north");
   public static final PropertyBool field_176257_M = PropertyBool.func_177716_a("east");
   public static final PropertyBool field_176258_N = PropertyBool.func_177716_a("south");
   public static final PropertyBool field_176259_O = PropertyBool.func_177716_a("west");
   public static final PropertyEnum<BlockWall.EnumType> field_176255_P = PropertyEnum.<BlockWall.EnumType>func_177709_a("variant", BlockWall.EnumType.class);
   protected static final AxisAlignedBB[] field_185751_g = new AxisAlignedBB[]{new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
   protected static final AxisAlignedBB[] field_185750_B = new AxisAlignedBB[]{field_185751_g[0].func_186666_e(1.5D), field_185751_g[1].func_186666_e(1.5D), field_185751_g[2].func_186666_e(1.5D), field_185751_g[3].func_186666_e(1.5D), field_185751_g[4].func_186666_e(1.5D), field_185751_g[5].func_186666_e(1.5D), field_185751_g[6].func_186666_e(1.5D), field_185751_g[7].func_186666_e(1.5D), field_185751_g[8].func_186666_e(1.5D), field_185751_g[9].func_186666_e(1.5D), field_185751_g[10].func_186666_e(1.5D), field_185751_g[11].func_186666_e(1.5D), field_185751_g[12].func_186666_e(1.5D), field_185751_g[13].func_186666_e(1.5D), field_185751_g[14].func_186666_e(1.5D), field_185751_g[15].func_186666_e(1.5D)};

   public BlockWall(Block p_i45435_1_) {
      super(p_i45435_1_.field_149764_J);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176256_a, Boolean.valueOf(false)).func_177226_a(field_176254_b, Boolean.valueOf(false)).func_177226_a(field_176257_M, Boolean.valueOf(false)).func_177226_a(field_176258_N, Boolean.valueOf(false)).func_177226_a(field_176259_O, Boolean.valueOf(false)).func_177226_a(field_176255_P, BlockWall.EnumType.NORMAL));
      this.func_149711_c(p_i45435_1_.field_149782_v);
      this.func_149752_b(p_i45435_1_.field_149781_w / 3.0F);
      this.func_149672_a(p_i45435_1_.field_149762_H);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      p_185496_1_ = this.func_176221_a(p_185496_1_, p_185496_2_, p_185496_3_);
      return field_185751_g[func_185749_i(p_185496_1_)];
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      if (!p_185477_7_) {
         p_185477_1_ = this.func_176221_a(p_185477_1_, p_185477_2_, p_185477_3_);
      }

      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185750_B[func_185749_i(p_185477_1_)]);
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      p_180646_1_ = this.func_176221_a(p_180646_1_, p_180646_2_, p_180646_3_);
      return field_185750_B[func_185749_i(p_180646_1_)];
   }

   private static int func_185749_i(IBlockState p_185749_0_) {
      int i = 0;
      if (((Boolean)p_185749_0_.func_177229_b(field_176254_b)).booleanValue()) {
         i |= 1 << EnumFacing.NORTH.func_176736_b();
      }

      if (((Boolean)p_185749_0_.func_177229_b(field_176257_M)).booleanValue()) {
         i |= 1 << EnumFacing.EAST.func_176736_b();
      }

      if (((Boolean)p_185749_0_.func_177229_b(field_176258_N)).booleanValue()) {
         i |= 1 << EnumFacing.SOUTH.func_176736_b();
      }

      if (((Boolean)p_185749_0_.func_177229_b(field_176259_O)).booleanValue()) {
         i |= 1 << EnumFacing.WEST.func_176736_b();
      }

      return i;
   }

   public String func_149732_F() {
      return I18n.func_74838_a(this.func_149739_a() + "." + BlockWall.EnumType.NORMAL.func_176659_c() + ".name");
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   private boolean func_176253_e(IBlockAccess p_176253_1_, BlockPos p_176253_2_, EnumFacing p_176253_3_) {
      IBlockState iblockstate = p_176253_1_.func_180495_p(p_176253_2_);
      Block block = iblockstate.func_177230_c();
      BlockFaceShape blockfaceshape = iblockstate.func_193401_d(p_176253_1_, p_176253_2_, p_176253_3_);
      boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE_THICK || blockfaceshape == BlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate;
      return !func_194143_e(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
   }

   protected static boolean func_194143_e(Block p_194143_0_) {
      return Block.func_193382_c(p_194143_0_) || p_194143_0_ == Blocks.field_180401_cv || p_194143_0_ == Blocks.field_150440_ba || p_194143_0_ == Blocks.field_150423_aK || p_194143_0_ == Blocks.field_150428_aP;
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockWall.EnumType blockwall$enumtype : BlockWall.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockwall$enumtype.func_176657_a()));
      }

   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockWall.EnumType)p_180651_1_.func_177229_b(field_176255_P)).func_176657_a();
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return p_176225_4_ == EnumFacing.DOWN ? super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_) : true;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176255_P, BlockWall.EnumType.func_176660_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockWall.EnumType)p_176201_1_.func_177229_b(field_176255_P)).func_176657_a();
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      boolean flag = this.func_176253_e(p_176221_2_, p_176221_3_.func_177978_c(), EnumFacing.SOUTH);
      boolean flag1 = this.func_176253_e(p_176221_2_, p_176221_3_.func_177974_f(), EnumFacing.WEST);
      boolean flag2 = this.func_176253_e(p_176221_2_, p_176221_3_.func_177968_d(), EnumFacing.NORTH);
      boolean flag3 = this.func_176253_e(p_176221_2_, p_176221_3_.func_177976_e(), EnumFacing.EAST);
      boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
      return p_176221_1_.func_177226_a(field_176256_a, Boolean.valueOf(!flag4 || !p_176221_2_.func_175623_d(p_176221_3_.func_177984_a()))).func_177226_a(field_176254_b, Boolean.valueOf(flag)).func_177226_a(field_176257_M, Boolean.valueOf(flag1)).func_177226_a(field_176258_N, Boolean.valueOf(flag2)).func_177226_a(field_176259_O, Boolean.valueOf(flag3));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176256_a, field_176254_b, field_176257_M, field_176259_O, field_176258_N, field_176255_P});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
   }

   public static enum EnumType implements IStringSerializable {
      NORMAL(0, "cobblestone", "normal"),
      MOSSY(1, "mossy_cobblestone", "mossy");

      private static final BlockWall.EnumType[] field_176666_c = new BlockWall.EnumType[values().length];
      private final int field_176663_d;
      private final String field_176664_e;
      private final String field_176661_f;

      private EnumType(int p_i45673_3_, String p_i45673_4_, String p_i45673_5_) {
         this.field_176663_d = p_i45673_3_;
         this.field_176664_e = p_i45673_4_;
         this.field_176661_f = p_i45673_5_;
      }

      public int func_176657_a() {
         return this.field_176663_d;
      }

      public String toString() {
         return this.field_176664_e;
      }

      public static BlockWall.EnumType func_176660_a(int p_176660_0_) {
         if (p_176660_0_ < 0 || p_176660_0_ >= field_176666_c.length) {
            p_176660_0_ = 0;
         }

         return field_176666_c[p_176660_0_];
      }

      public String func_176610_l() {
         return this.field_176664_e;
      }

      public String func_176659_c() {
         return this.field_176661_f;
      }

      static {
         for(BlockWall.EnumType blockwall$enumtype : values()) {
            field_176666_c[blockwall$enumtype.func_176657_a()] = blockwall$enumtype;
         }

      }
   }
}
