package net.minecraft.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BiomeColorHelper {
   private static final BiomeColorHelper.ColorResolver field_180291_a = new BiomeColorHelper.ColorResolver() {
      public int func_180283_a(Biome p_180283_1_, BlockPos p_180283_2_) {
         return p_180283_1_.func_180627_b(p_180283_2_);
      }
   };
   private static final BiomeColorHelper.ColorResolver field_180289_b = new BiomeColorHelper.ColorResolver() {
      public int func_180283_a(Biome p_180283_1_, BlockPos p_180283_2_) {
         return p_180283_1_.func_180625_c(p_180283_2_);
      }
   };
   private static final BiomeColorHelper.ColorResolver field_180290_c = new BiomeColorHelper.ColorResolver() {
      public int func_180283_a(Biome p_180283_1_, BlockPos p_180283_2_) {
         return p_180283_1_.func_185361_o();
      }
   };

   private static int func_180285_a(IBlockAccess p_180285_0_, BlockPos p_180285_1_, BiomeColorHelper.ColorResolver p_180285_2_) {
      int i = 0;
      int j = 0;
      int k = 0;

      for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(p_180285_1_.func_177982_a(-1, 0, -1), p_180285_1_.func_177982_a(1, 0, 1))) {
         int l = p_180285_2_.func_180283_a(p_180285_0_.func_180494_b(blockpos$mutableblockpos), blockpos$mutableblockpos);
         i += (l & 16711680) >> 16;
         j += (l & '\uff00') >> 8;
         k += l & 255;
      }

      return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
   }

   public static int func_180286_a(IBlockAccess p_180286_0_, BlockPos p_180286_1_) {
      return func_180285_a(p_180286_0_, p_180286_1_, field_180291_a);
   }

   public static int func_180287_b(IBlockAccess p_180287_0_, BlockPos p_180287_1_) {
      return func_180285_a(p_180287_0_, p_180287_1_, field_180289_b);
   }

   public static int func_180288_c(IBlockAccess p_180288_0_, BlockPos p_180288_1_) {
      return func_180285_a(p_180288_0_, p_180288_1_, field_180290_c);
   }

   interface ColorResolver {
      int func_180283_a(Biome var1, BlockPos var2);
   }
}
