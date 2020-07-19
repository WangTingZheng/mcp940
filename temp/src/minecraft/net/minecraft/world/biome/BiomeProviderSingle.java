package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class BiomeProviderSingle extends BiomeProvider {
   private final Biome field_76947_d;

   public BiomeProviderSingle(Biome p_i46709_1_) {
      this.field_76947_d = p_i46709_1_;
   }

   public Biome func_180631_a(BlockPos p_180631_1_) {
      return this.field_76947_d;
   }

   public Biome[] func_76937_a(Biome[] p_76937_1_, int p_76937_2_, int p_76937_3_, int p_76937_4_, int p_76937_5_) {
      if (p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_) {
         p_76937_1_ = new Biome[p_76937_4_ * p_76937_5_];
      }

      Arrays.fill(p_76937_1_, 0, p_76937_4_ * p_76937_5_, this.field_76947_d);
      return p_76937_1_;
   }

   public Biome[] func_76933_b(@Nullable Biome[] p_76933_1_, int p_76933_2_, int p_76933_3_, int p_76933_4_, int p_76933_5_) {
      if (p_76933_1_ == null || p_76933_1_.length < p_76933_4_ * p_76933_5_) {
         p_76933_1_ = new Biome[p_76933_4_ * p_76933_5_];
      }

      Arrays.fill(p_76933_1_, 0, p_76933_4_ * p_76933_5_, this.field_76947_d);
      return p_76933_1_;
   }

   public Biome[] func_76931_a(@Nullable Biome[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_) {
      return this.func_76933_b(p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
   }

   @Nullable
   public BlockPos func_180630_a(int p_180630_1_, int p_180630_2_, int p_180630_3_, List<Biome> p_180630_4_, Random p_180630_5_) {
      return p_180630_4_.contains(this.field_76947_d) ? new BlockPos(p_180630_1_ - p_180630_3_ + p_180630_5_.nextInt(p_180630_3_ * 2 + 1), 0, p_180630_2_ - p_180630_3_ + p_180630_5_.nextInt(p_180630_3_ * 2 + 1)) : null;
   }

   public boolean func_76940_a(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<Biome> p_76940_4_) {
      return p_76940_4_.contains(this.field_76947_d);
   }

   public boolean func_190944_c() {
      return true;
   }

   public Biome func_190943_d() {
      return this.field_76947_d;
   }
}
