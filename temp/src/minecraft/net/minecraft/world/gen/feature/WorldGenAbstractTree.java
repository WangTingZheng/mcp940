package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenAbstractTree extends WorldGenerator {
   public WorldGenAbstractTree(boolean p_i45448_1_) {
      super(p_i45448_1_);
   }

   protected boolean func_150523_a(Block p_150523_1_) {
      Material material = p_150523_1_.func_176223_P().func_185904_a();
      return material == Material.field_151579_a || material == Material.field_151584_j || p_150523_1_ == Blocks.field_150349_c || p_150523_1_ == Blocks.field_150346_d || p_150523_1_ == Blocks.field_150364_r || p_150523_1_ == Blocks.field_150363_s || p_150523_1_ == Blocks.field_150345_g || p_150523_1_ == Blocks.field_150395_bd;
   }

   public void func_180711_a(World p_180711_1_, Random p_180711_2_, BlockPos p_180711_3_) {
   }

   protected void func_175921_a(World p_175921_1_, BlockPos p_175921_2_) {
      if (p_175921_1_.func_180495_p(p_175921_2_).func_177230_c() != Blocks.field_150346_d) {
         this.func_175903_a(p_175921_1_, p_175921_2_, Blocks.field_150346_d.func_176223_P());
      }

   }
}
