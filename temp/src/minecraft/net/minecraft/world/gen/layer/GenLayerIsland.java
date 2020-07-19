package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer {
   public GenLayerIsland(long p_i2124_1_) {
      super(p_i2124_1_);
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i = 0; i < p_75904_4_; ++i) {
         for(int j = 0; j < p_75904_3_; ++j) {
            this.func_75903_a((long)(p_75904_1_ + j), (long)(p_75904_2_ + i));
            aint[j + i * p_75904_3_] = this.func_75902_a(10) == 0 ? 1 : 0;
         }
      }

      if (p_75904_1_ > -p_75904_3_ && p_75904_1_ <= 0 && p_75904_2_ > -p_75904_4_ && p_75904_2_ <= 0) {
         aint[-p_75904_1_ + -p_75904_2_ * p_75904_3_] = 1;
      }

      return aint;
   }
}
