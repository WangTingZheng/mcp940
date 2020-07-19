package net.minecraft.block;

import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndPortalFrame extends Block {
   public static final PropertyDirection field_176508_a = BlockHorizontal.field_185512_D;
   public static final PropertyBool field_176507_b = PropertyBool.func_177716_a("eye");
   protected static final AxisAlignedBB field_185662_c = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
   protected static final AxisAlignedBB field_185663_d = new AxisAlignedBB(0.3125D, 0.8125D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
   private static BlockPattern field_185664_e;

   public BlockEndPortalFrame() {
      super(Material.field_151576_e, MapColor.field_151651_C);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176508_a, EnumFacing.NORTH).func_177226_a(field_176507_b, Boolean.valueOf(false)));
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185662_c;
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185662_c);
      if (((Boolean)p_185477_2_.func_180495_p(p_185477_3_).func_177229_b(field_176507_b)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185663_d);
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_176508_a, p_180642_8_.func_174811_aO().func_176734_d()).func_177226_a(field_176507_b, Boolean.valueOf(false));
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return ((Boolean)p_180641_1_.func_177229_b(field_176507_b)).booleanValue() ? 15 : 0;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176507_b, Boolean.valueOf((p_176203_1_ & 4) != 0)).func_177226_a(field_176508_a, EnumFacing.func_176731_b(p_176203_1_ & 3));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176508_a)).func_176736_b();
      if (((Boolean)p_176201_1_.func_177229_b(field_176507_b)).booleanValue()) {
         i |= 4;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176508_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176508_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176508_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176508_a, field_176507_b});
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public static BlockPattern func_185661_e() {
      if (field_185664_e == null) {
         field_185664_e = FactoryBlockPattern.func_177660_a().func_177659_a("?vvv?", ">???<", ">???<", ">???<", "?^^^?").func_177662_a('?', BlockWorldState.func_177510_a(BlockStateMatcher.field_185928_a)).func_177662_a('^', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150378_br).func_177637_a(field_176507_b, Predicates.equalTo(Boolean.valueOf(true))).func_177637_a(field_176508_a, Predicates.equalTo(EnumFacing.SOUTH)))).func_177662_a('>', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150378_br).func_177637_a(field_176507_b, Predicates.equalTo(Boolean.valueOf(true))).func_177637_a(field_176508_a, Predicates.equalTo(EnumFacing.WEST)))).func_177662_a('v', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150378_br).func_177637_a(field_176507_b, Predicates.equalTo(Boolean.valueOf(true))).func_177637_a(field_176508_a, Predicates.equalTo(EnumFacing.NORTH)))).func_177662_a('<', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150378_br).func_177637_a(field_176507_b, Predicates.equalTo(Boolean.valueOf(true))).func_177637_a(field_176508_a, Predicates.equalTo(EnumFacing.EAST)))).func_177661_b();
      }

      return field_185664_e;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
