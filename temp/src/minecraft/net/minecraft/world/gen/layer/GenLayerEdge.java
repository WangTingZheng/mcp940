package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer {
   private final GenLayerEdge.Mode field_151627_c;

   public GenLayerEdge(long p_i45474_1_, GenLayer p_i45474_3_, GenLayerEdge.Mode p_i45474_4_) {
      super(p_i45474_1_);
      this.field_75909_a = p_i45474_3_;
      this.field_151627_c = p_i45474_4_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      switch(this.field_151627_c) {
      case COOL_WARM:
      default:
         return this.func_151626_c(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      case HEAT_ICE:
         return this.func_151624_d(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      case SPECIAL:
         return this.func_151625_e(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      }
   }

   private int[] func_151626_c(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_) {
      int i = p_151626_1_ - 1;
      int j = p_151626_2_ - 1;
      int k = 1 + p_151626_3_ + 1;
      int l = 1 + p_151626_4_ + 1;
      int[] aint = this.field_75909_a.func_75904_a(i, j, k, l);
      int[] aint1 = IntCache.func_76445_a(p_151626_3_ * p_151626_4_);

      for(int i1 = 0; i1 < p_151626_4_; ++i1) {
         for(int j1 = 0; j1 < p_151626_3_; ++j1) {
            this.func_75903_a((long)(j1 + p_151626_1_), (long)(i1 + p_151626_2_));
            int k1 = aint[j1 + 1 + (i1 + 1) * k];
            if (k1 == 1) {
               int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
               int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
               int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
               int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
               boolean flag = l1 == 3 || i2 == 3 || j2 == 3 || k2 == 3;
               boolean flag1 = l1 == 4 || i2 == 4 || j2 == 4 || k2 == 4;
               if (flag || flag1) {
                  k1 = 2;
               }
            }

            aint1[j1 + i1 * p_151626_3_] = k1;
         }
      }

      return aint1;
   }

   private int[] func_151624_d(int p_151624_1_, int p_151624_2_, int p_151624_3_, int p_151624_4_) {
      int i = p_151624_1_ - 1;
      int j = p_151624_2_ - 1;
      int k = 1 + p_151624_3_ + 1;
      int l = 1 + p_151624_4_ + 1;
      int[] aint = this.field_75909_a.func_75904_a(i, j, k, l);
      int[] aint1 = IntCache.func_76445_a(p_151624_3_ * p_151624_4_);

      for(int i1 = 0; i1 < p_151624_4_; ++i1) {
         for(int j1 = 0; j1 < p_151624_3_; ++j1) {
            int k1 = aint[j1 + 1 + (i1 + 1) * k];
            if (k1 == 4) {
               int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
               int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
               int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
               int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
               boolean flag = l1 == 2 || i2 == 2 || j2 == 2 || k2 == 2;
               boolean flag1 = l1 == 1 || i2 == 1 || j2 == 1 || k2 == 1;
               if (flag1 || flag) {
                  k1 = 3;
               }
            }

            aint1[j1 + i1 * p_151624_3_] = k1;
         }
      }

      return aint1;
   }

   private int[] func_151625_e(int p_151625_1_, int p_151625_2_, int p_151625_3_, int p_151625_4_) {
      int[] aint = this.field_75909_a.func_75904_a(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
      int[] aint1 = IntCache.func_76445_a(p_151625_3_ * p_151625_4_);

      for(int i = 0; i < p_151625_4_; ++i) {
         for(int j = 0; j < p_151625_3_; ++j) {
            this.func_75903_a((long)(j + p_151625_1_), (long)(i + p_151625_2_));
            int k = aint[j + i * p_151625_3_];
            if (k != 0 && this.func_75902_a(13) == 0) {
               k |= 1 + this.func_75902_a(15) << 8 & 3840;
            }

            aint1[j + i * p_151625_3_] = k;
         }
      }

      return aint1;
   }

   public static enum Mode {
      COOL_WARM,
      HEAT_ICE,
      SPECIAL;
   }
}
