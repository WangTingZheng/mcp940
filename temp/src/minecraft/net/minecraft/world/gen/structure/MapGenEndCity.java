package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;

public class MapGenEndCity extends MapGenStructure {
   private final int field_186131_a = 20;
   private final int field_186132_b = 11;
   private final ChunkGeneratorEnd field_186133_d;

   public MapGenEndCity(ChunkGeneratorEnd p_i46665_1_) {
      this.field_186133_d = p_i46665_1_;
   }

   public String func_143025_a() {
      return "EndCity";
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int i = p_75047_1_;
      int j = p_75047_2_;
      if (p_75047_1_ < 0) {
         p_75047_1_ -= 19;
      }

      if (p_75047_2_ < 0) {
         p_75047_2_ -= 19;
      }

      int k = p_75047_1_ / 20;
      int l = p_75047_2_ / 20;
      Random random = this.field_75039_c.func_72843_D(k, l, 10387313);
      k = k * 20;
      l = l * 20;
      k = k + (random.nextInt(9) + random.nextInt(9)) / 2;
      l = l + (random.nextInt(9) + random.nextInt(9)) / 2;
      if (i == k && j == l && this.field_186133_d.func_185961_c(i, j)) {
         int i1 = func_191070_b(i, j, this.field_186133_d);
         return i1 >= 60;
      } else {
         return false;
      }
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new MapGenEndCity.Start(this.field_75039_c, this.field_186133_d, this.field_75038_b, p_75049_1_, p_75049_2_);
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      this.field_75039_c = p_180706_1_;
      return func_191069_a(p_180706_1_, this, p_180706_2_, 20, 11, 10387313, true, 100, p_180706_3_);
   }

   private static int func_191070_b(int p_191070_0_, int p_191070_1_, ChunkGeneratorEnd p_191070_2_) {
      Random random = new Random((long)(p_191070_0_ + p_191070_1_ * 10387313));
      Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
      ChunkPrimer chunkprimer = new ChunkPrimer();
      p_191070_2_.func_180518_a(p_191070_0_, p_191070_1_, chunkprimer);
      int i = 5;
      int j = 5;
      if (rotation == Rotation.CLOCKWISE_90) {
         i = -5;
      } else if (rotation == Rotation.CLOCKWISE_180) {
         i = -5;
         j = -5;
      } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
         j = -5;
      }

      int k = chunkprimer.func_186138_a(7, 7);
      int l = chunkprimer.func_186138_a(7, 7 + j);
      int i1 = chunkprimer.func_186138_a(7 + i, 7);
      int j1 = chunkprimer.func_186138_a(7 + i, 7 + j);
      int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
      return k1;
   }

   public static class Start extends StructureStart {
      private boolean field_186163_c;

      public Start() {
      }

      public Start(World p_i46760_1_, ChunkGeneratorEnd p_i46760_2_, Random p_i46760_3_, int p_i46760_4_, int p_i46760_5_) {
         super(p_i46760_4_, p_i46760_5_);
         this.func_186162_a(p_i46760_1_, p_i46760_2_, p_i46760_3_, p_i46760_4_, p_i46760_5_);
      }

      private void func_186162_a(World p_186162_1_, ChunkGeneratorEnd p_186162_2_, Random p_186162_3_, int p_186162_4_, int p_186162_5_) {
         Random random = new Random((long)(p_186162_4_ + p_186162_5_ * 10387313));
         Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
         int i = MapGenEndCity.func_191070_b(p_186162_4_, p_186162_5_, p_186162_2_);
         if (i < 60) {
            this.field_186163_c = false;
         } else {
            BlockPos blockpos = new BlockPos(p_186162_4_ * 16 + 8, i, p_186162_5_ * 16 + 8);
            StructureEndCityPieces.func_191087_a(p_186162_1_.func_72860_G().func_186340_h(), blockpos, rotation, this.field_75075_a, p_186162_3_);
            this.func_75072_c();
            this.field_186163_c = true;
         }
      }

      public boolean func_75069_d() {
         return this.field_186163_c;
      }
   }
}
