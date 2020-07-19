package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class Path {
   private final PathPoint[] field_75884_a;
   private PathPoint[] field_186312_b = new PathPoint[0];
   private PathPoint[] field_186313_c = new PathPoint[0];
   private PathPoint field_186314_d;
   private int field_75882_b;
   private int field_75883_c;

   public Path(PathPoint[] p_i2136_1_) {
      this.field_75884_a = p_i2136_1_;
      this.field_75883_c = p_i2136_1_.length;
   }

   public void func_75875_a() {
      ++this.field_75882_b;
   }

   public boolean func_75879_b() {
      return this.field_75882_b >= this.field_75883_c;
   }

   @Nullable
   public PathPoint func_75870_c() {
      return this.field_75883_c > 0 ? this.field_75884_a[this.field_75883_c - 1] : null;
   }

   public PathPoint func_75877_a(int p_75877_1_) {
      return this.field_75884_a[p_75877_1_];
   }

   public void func_186309_a(int p_186309_1_, PathPoint p_186309_2_) {
      this.field_75884_a[p_186309_1_] = p_186309_2_;
   }

   public int func_75874_d() {
      return this.field_75883_c;
   }

   public void func_75871_b(int p_75871_1_) {
      this.field_75883_c = p_75871_1_;
   }

   public int func_75873_e() {
      return this.field_75882_b;
   }

   public void func_75872_c(int p_75872_1_) {
      this.field_75882_b = p_75872_1_;
   }

   public Vec3d func_75881_a(Entity p_75881_1_, int p_75881_2_) {
      double d0 = (double)this.field_75884_a[p_75881_2_].field_75839_a + (double)((int)(p_75881_1_.field_70130_N + 1.0F)) * 0.5D;
      double d1 = (double)this.field_75884_a[p_75881_2_].field_75837_b;
      double d2 = (double)this.field_75884_a[p_75881_2_].field_75838_c + (double)((int)(p_75881_1_.field_70130_N + 1.0F)) * 0.5D;
      return new Vec3d(d0, d1, d2);
   }

   public Vec3d func_75878_a(Entity p_75878_1_) {
      return this.func_75881_a(p_75878_1_, this.field_75882_b);
   }

   public Vec3d func_186310_f() {
      PathPoint pathpoint = this.field_75884_a[this.field_75882_b];
      return new Vec3d((double)pathpoint.field_75839_a, (double)pathpoint.field_75837_b, (double)pathpoint.field_75838_c);
   }

   public boolean func_75876_a(Path p_75876_1_) {
      if (p_75876_1_ == null) {
         return false;
      } else if (p_75876_1_.field_75884_a.length != this.field_75884_a.length) {
         return false;
      } else {
         for(int i = 0; i < this.field_75884_a.length; ++i) {
            if (this.field_75884_a[i].field_75839_a != p_75876_1_.field_75884_a[i].field_75839_a || this.field_75884_a[i].field_75837_b != p_75876_1_.field_75884_a[i].field_75837_b || this.field_75884_a[i].field_75838_c != p_75876_1_.field_75884_a[i].field_75838_c) {
               return false;
            }
         }

         return true;
      }
   }

   public PathPoint[] func_189966_g() {
      return this.field_186312_b;
   }

   public PathPoint[] func_189965_h() {
      return this.field_186313_c;
   }

   public PathPoint func_189964_i() {
      return this.field_186314_d;
   }

   public static Path func_186311_b(PacketBuffer p_186311_0_) {
      int i = p_186311_0_.readInt();
      PathPoint pathpoint = PathPoint.func_186282_b(p_186311_0_);
      PathPoint[] apathpoint = new PathPoint[p_186311_0_.readInt()];

      for(int j = 0; j < apathpoint.length; ++j) {
         apathpoint[j] = PathPoint.func_186282_b(p_186311_0_);
      }

      PathPoint[] apathpoint1 = new PathPoint[p_186311_0_.readInt()];

      for(int k = 0; k < apathpoint1.length; ++k) {
         apathpoint1[k] = PathPoint.func_186282_b(p_186311_0_);
      }

      PathPoint[] apathpoint2 = new PathPoint[p_186311_0_.readInt()];

      for(int l = 0; l < apathpoint2.length; ++l) {
         apathpoint2[l] = PathPoint.func_186282_b(p_186311_0_);
      }

      Path path = new Path(apathpoint);
      path.field_186312_b = apathpoint1;
      path.field_186313_c = apathpoint2;
      path.field_186314_d = pathpoint;
      path.field_75882_b = i;
      return path;
   }
}
