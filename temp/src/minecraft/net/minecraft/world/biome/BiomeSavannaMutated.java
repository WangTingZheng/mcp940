package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeSavannaMutated extends BiomeSavanna {
   public BiomeSavannaMutated(Biome.BiomeProperties p_i46701_1_) {
      super(p_i46701_1_);
      this.field_76760_I.field_76832_z = 2;
      this.field_76760_I.field_76802_A = 2;
      this.field_76760_I.field_76803_B = 5;
   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      this.field_76752_A = Blocks.field_150349_c.func_176223_P();
      this.field_76753_B = Blocks.field_150346_d.func_176223_P();
      if (p_180622_6_ > 1.75D) {
         this.field_76752_A = Blocks.field_150348_b.func_176223_P();
         this.field_76753_B = Blocks.field_150348_b.func_176223_P();
      } else if (p_180622_6_ > -0.5D) {
         this.field_76752_A = Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.COARSE_DIRT);
      }

      this.func_180628_b(p_180622_1_, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      this.field_76760_I.func_180292_a(p_180624_1_, p_180624_2_, this, p_180624_3_);
   }
}
