package net.minecraft.world.gen.layer;

public class GenLayerRiverInit extends GenLayer {
   public GenLayerRiverInit(long p_i2127_1_, GenLayer p_i2127_3_) {
      super(p_i2127_1_);
      this.field_75909_a = p_i2127_3_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint = this.field_75909_a.func_75904_a(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      int[] aint1 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i = 0; i < p_75904_4_; ++i) {
         for(int j = 0; j < p_75904_3_; ++j) {
            this.func_75903_a((long)(j + p_75904_1_), (long)(i + p_75904_2_));
            aint1[j + i * p_75904_3_] = aint[j + i * p_75904_3_] > 0 ? this.func_75902_a(299999) + 2 : 0;
         }
      }

      return aint1;
   }
}
