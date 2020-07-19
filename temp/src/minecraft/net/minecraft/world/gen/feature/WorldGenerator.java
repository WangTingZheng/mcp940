package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenerator {
   private final boolean field_76488_a;

   public WorldGenerator() {
      this(false);
   }

   public WorldGenerator(boolean p_i2013_1_) {
      this.field_76488_a = p_i2013_1_;
   }

   public abstract boolean func_180709_b(World var1, Random var2, BlockPos var3);

   public void func_175904_e() {
   }

   protected void func_175903_a(World p_175903_1_, BlockPos p_175903_2_, IBlockState p_175903_3_) {
      if (this.field_76488_a) {
         p_175903_1_.func_180501_a(p_175903_2_, p_175903_3_, 3);
      } else {
         p_175903_1_.func_180501_a(p_175903_2_, p_175903_3_, 2);
      }

   }
}
