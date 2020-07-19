package net.minecraft.pathfinding;

public class PathHeap {
   private PathPoint[] field_75852_a = new PathPoint[128];
   private int field_75851_b;

   public PathPoint func_75849_a(PathPoint p_75849_1_) {
      if (p_75849_1_.field_75835_d >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.field_75851_b == this.field_75852_a.length) {
            PathPoint[] apathpoint = new PathPoint[this.field_75851_b << 1];
            System.arraycopy(this.field_75852_a, 0, apathpoint, 0, this.field_75851_b);
            this.field_75852_a = apathpoint;
         }

         this.field_75852_a[this.field_75851_b] = p_75849_1_;
         p_75849_1_.field_75835_d = this.field_75851_b;
         this.func_75847_a(this.field_75851_b++);
         return p_75849_1_;
      }
   }

   public void func_75848_a() {
      this.field_75851_b = 0;
   }

   public PathPoint func_75844_c() {
      PathPoint pathpoint = this.field_75852_a[0];
      this.field_75852_a[0] = this.field_75852_a[--this.field_75851_b];
      this.field_75852_a[this.field_75851_b] = null;
      if (this.field_75851_b > 0) {
         this.func_75846_b(0);
      }

      pathpoint.field_75835_d = -1;
      return pathpoint;
   }

   public void func_75850_a(PathPoint p_75850_1_, float p_75850_2_) {
      float f = p_75850_1_.field_75834_g;
      p_75850_1_.field_75834_g = p_75850_2_;
      if (p_75850_2_ < f) {
         this.func_75847_a(p_75850_1_.field_75835_d);
      } else {
         this.func_75846_b(p_75850_1_.field_75835_d);
      }

   }

   private void func_75847_a(int p_75847_1_) {
      PathPoint pathpoint = this.field_75852_a[p_75847_1_];

      int i;
      for(float f = pathpoint.field_75834_g; p_75847_1_ > 0; p_75847_1_ = i) {
         i = p_75847_1_ - 1 >> 1;
         PathPoint pathpoint1 = this.field_75852_a[i];
         if (f >= pathpoint1.field_75834_g) {
            break;
         }

         this.field_75852_a[p_75847_1_] = pathpoint1;
         pathpoint1.field_75835_d = p_75847_1_;
      }

      this.field_75852_a[p_75847_1_] = pathpoint;
      pathpoint.field_75835_d = p_75847_1_;
   }

   private void func_75846_b(int p_75846_1_) {
      PathPoint pathpoint = this.field_75852_a[p_75846_1_];
      float f = pathpoint.field_75834_g;

      while(true) {
         int i = 1 + (p_75846_1_ << 1);
         int j = i + 1;
         if (i >= this.field_75851_b) {
            break;
         }

         PathPoint pathpoint1 = this.field_75852_a[i];
         float f1 = pathpoint1.field_75834_g;
         PathPoint pathpoint2;
         float f2;
         if (j >= this.field_75851_b) {
            pathpoint2 = null;
            f2 = Float.POSITIVE_INFINITY;
         } else {
            pathpoint2 = this.field_75852_a[j];
            f2 = pathpoint2.field_75834_g;
         }

         if (f1 < f2) {
            if (f1 >= f) {
               break;
            }

            this.field_75852_a[p_75846_1_] = pathpoint1;
            pathpoint1.field_75835_d = p_75846_1_;
            p_75846_1_ = i;
         } else {
            if (f2 >= f) {
               break;
            }

            this.field_75852_a[p_75846_1_] = pathpoint2;
            pathpoint2.field_75835_d = p_75846_1_;
            p_75846_1_ = j;
         }
      }

      this.field_75852_a[p_75846_1_] = pathpoint;
      pathpoint.field_75835_d = p_75846_1_;
   }

   public boolean func_75845_e() {
      return this.field_75851_b == 0;
   }
}
