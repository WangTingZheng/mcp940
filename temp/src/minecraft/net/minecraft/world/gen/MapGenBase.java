package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenBase {
   protected int field_75040_a = 8;
   protected Random field_75038_b = new Random();
   protected World field_75039_c;

   public void func_186125_a(World p_186125_1_, int p_186125_2_, int p_186125_3_, ChunkPrimer p_186125_4_) {
      int i = this.field_75040_a;
      this.field_75039_c = p_186125_1_;
      this.field_75038_b.setSeed(p_186125_1_.func_72905_C());
      long j = this.field_75038_b.nextLong();
      long k = this.field_75038_b.nextLong();

      for(int l = p_186125_2_ - i; l <= p_186125_2_ + i; ++l) {
         for(int i1 = p_186125_3_ - i; i1 <= p_186125_3_ + i; ++i1) {
            long j1 = (long)l * j;
            long k1 = (long)i1 * k;
            this.field_75038_b.setSeed(j1 ^ k1 ^ p_186125_1_.func_72905_C());
            this.func_180701_a(p_186125_1_, l, i1, p_186125_2_, p_186125_3_, p_186125_4_);
         }
      }

   }

   public static void func_191068_a(long p_191068_0_, Random p_191068_2_, int p_191068_3_, int p_191068_4_) {
      p_191068_2_.setSeed(p_191068_0_);
      long i = p_191068_2_.nextLong();
      long j = p_191068_2_.nextLong();
      long k = (long)p_191068_3_ * i;
      long l = (long)p_191068_4_ * j;
      p_191068_2_.setSeed(k ^ l ^ p_191068_0_);
   }

   protected void func_180701_a(World p_180701_1_, int p_180701_2_, int p_180701_3_, int p_180701_4_, int p_180701_5_, ChunkPrimer p_180701_6_) {
   }
}
