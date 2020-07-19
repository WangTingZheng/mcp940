package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBeetroot extends BlockCrops {
   public static final PropertyInteger field_185531_a = PropertyInteger.func_177719_a("age", 0, 3);
   private static final AxisAlignedBB[] field_185532_d = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)};

   protected PropertyInteger func_185524_e() {
      return field_185531_a;
   }

   public int func_185526_g() {
      return 3;
   }

   protected Item func_149866_i() {
      return Items.field_185163_cU;
   }

   protected Item func_149865_P() {
      return Items.field_185164_cV;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (p_180650_4_.nextInt(3) == 0) {
         this.func_176475_e(p_180650_1_, p_180650_2_, p_180650_3_);
      } else {
         super.func_180650_b(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_);
      }

   }

   protected int func_185529_b(World p_185529_1_) {
      return super.func_185529_b(p_185529_1_) / 3;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185531_a});
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185532_d[((Integer)p_185496_1_.func_177229_b(this.func_185524_e())).intValue()];
   }
}
