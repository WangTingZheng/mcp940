package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneLight extends Block {
   private final boolean field_150171_a;

   public BlockRedstoneLight(boolean p_i45421_1_) {
      super(Material.field_151591_t);
      this.field_150171_a = p_i45421_1_;
      if (p_i45421_1_) {
         this.func_149715_a(1.0F);
      }

   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!p_176213_1_.field_72995_K) {
         if (this.field_150171_a && !p_176213_1_.func_175640_z(p_176213_2_)) {
            p_176213_1_.func_180501_a(p_176213_2_, Blocks.field_150379_bu.func_176223_P(), 2);
         } else if (!this.field_150171_a && p_176213_1_.func_175640_z(p_176213_2_)) {
            p_176213_1_.func_180501_a(p_176213_2_, Blocks.field_150374_bv.func_176223_P(), 2);
         }

      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.field_72995_K) {
         if (this.field_150171_a && !p_189540_2_.func_175640_z(p_189540_3_)) {
            p_189540_2_.func_175684_a(p_189540_3_, this, 4);
         } else if (!this.field_150171_a && p_189540_2_.func_175640_z(p_189540_3_)) {
            p_189540_2_.func_180501_a(p_189540_3_, Blocks.field_150374_bv.func_176223_P(), 2);
         }

      }
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         if (this.field_150171_a && !p_180650_1_.func_175640_z(p_180650_2_)) {
            p_180650_1_.func_180501_a(p_180650_2_, Blocks.field_150379_bu.func_176223_P(), 2);
         }

      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(Blocks.field_150379_bu);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Blocks.field_150379_bu);
   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      return new ItemStack(Blocks.field_150379_bu);
   }
}
