package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

public class TextureAtlasSprite {
   private final String field_110984_i;
   protected List<int[][]> field_110976_a = Lists.<int[][]>newArrayList();
   protected int[][] field_176605_b;
   private AnimationMetadataSection field_110982_k;
   protected boolean field_130222_e;
   protected int field_110975_c;
   protected int field_110974_d;
   protected int field_130223_c;
   protected int field_130224_d;
   private float field_110979_l;
   private float field_110980_m;
   private float field_110977_n;
   private float field_110978_o;
   protected int field_110973_g;
   protected int field_110983_h;

   protected TextureAtlasSprite(String p_i1282_1_) {
      this.field_110984_i = p_i1282_1_;
   }

   protected static TextureAtlasSprite func_176604_a(ResourceLocation p_176604_0_) {
      return new TextureAtlasSprite(p_176604_0_.toString());
   }

   public void func_110971_a(int p_110971_1_, int p_110971_2_, int p_110971_3_, int p_110971_4_, boolean p_110971_5_) {
      this.field_110975_c = p_110971_3_;
      this.field_110974_d = p_110971_4_;
      this.field_130222_e = p_110971_5_;
      float f = (float)(0.009999999776482582D / (double)p_110971_1_);
      float f1 = (float)(0.009999999776482582D / (double)p_110971_2_);
      this.field_110979_l = (float)p_110971_3_ / (float)((double)p_110971_1_) + f;
      this.field_110980_m = (float)(p_110971_3_ + this.field_130223_c) / (float)((double)p_110971_1_) - f;
      this.field_110977_n = (float)p_110971_4_ / (float)p_110971_2_ + f1;
      this.field_110978_o = (float)(p_110971_4_ + this.field_130224_d) / (float)p_110971_2_ - f1;
   }

   public void func_94217_a(TextureAtlasSprite p_94217_1_) {
      this.field_110975_c = p_94217_1_.field_110975_c;
      this.field_110974_d = p_94217_1_.field_110974_d;
      this.field_130223_c = p_94217_1_.field_130223_c;
      this.field_130224_d = p_94217_1_.field_130224_d;
      this.field_130222_e = p_94217_1_.field_130222_e;
      this.field_110979_l = p_94217_1_.field_110979_l;
      this.field_110980_m = p_94217_1_.field_110980_m;
      this.field_110977_n = p_94217_1_.field_110977_n;
      this.field_110978_o = p_94217_1_.field_110978_o;
   }

   public int func_130010_a() {
      return this.field_110975_c;
   }

   public int func_110967_i() {
      return this.field_110974_d;
   }

   public int func_94211_a() {
      return this.field_130223_c;
   }

   public int func_94216_b() {
      return this.field_130224_d;
   }

   public float func_94209_e() {
      return this.field_110979_l;
   }

   public float func_94212_f() {
      return this.field_110980_m;
   }

   public float func_94214_a(double p_94214_1_) {
      float f = this.field_110980_m - this.field_110979_l;
      return this.field_110979_l + f * (float)p_94214_1_ / 16.0F;
   }

   public float func_188537_a(float p_188537_1_) {
      float f = this.field_110980_m - this.field_110979_l;
      return (p_188537_1_ - this.field_110979_l) / f * 16.0F;
   }

   public float func_94206_g() {
      return this.field_110977_n;
   }

   public float func_94210_h() {
      return this.field_110978_o;
   }

   public float func_94207_b(double p_94207_1_) {
      float f = this.field_110978_o - this.field_110977_n;
      return this.field_110977_n + f * (float)p_94207_1_ / 16.0F;
   }

   public float func_188536_b(float p_188536_1_) {
      float f = this.field_110978_o - this.field_110977_n;
      return (p_188536_1_ - this.field_110977_n) / f * 16.0F;
   }

   public String func_94215_i() {
      return this.field_110984_i;
   }

   public void func_94219_l() {
      ++this.field_110983_h;
      if (this.field_110983_h >= this.field_110982_k.func_110472_a(this.field_110973_g)) {
         int i = this.field_110982_k.func_110468_c(this.field_110973_g);
         int j = this.field_110982_k.func_110473_c() == 0 ? this.field_110976_a.size() : this.field_110982_k.func_110473_c();
         this.field_110973_g = (this.field_110973_g + 1) % j;
         this.field_110983_h = 0;
         int k = this.field_110982_k.func_110468_c(this.field_110973_g);
         if (i != k && k >= 0 && k < this.field_110976_a.size()) {
            TextureUtil.func_147955_a(this.field_110976_a.get(k), this.field_130223_c, this.field_130224_d, this.field_110975_c, this.field_110974_d, false, false);
         }
      } else if (this.field_110982_k.func_177219_e()) {
         this.func_180599_n();
      }

   }

   private void func_180599_n() {
      double d0 = 1.0D - (double)this.field_110983_h / (double)this.field_110982_k.func_110472_a(this.field_110973_g);
      int i = this.field_110982_k.func_110468_c(this.field_110973_g);
      int j = this.field_110982_k.func_110473_c() == 0 ? this.field_110976_a.size() : this.field_110982_k.func_110473_c();
      int k = this.field_110982_k.func_110468_c((this.field_110973_g + 1) % j);
      if (i != k && k >= 0 && k < this.field_110976_a.size()) {
         int[][] aint = this.field_110976_a.get(i);
         int[][] aint1 = this.field_110976_a.get(k);
         if (this.field_176605_b == null || this.field_176605_b.length != aint.length) {
            this.field_176605_b = new int[aint.length][];
         }

         for(int l = 0; l < aint.length; ++l) {
            if (this.field_176605_b[l] == null) {
               this.field_176605_b[l] = new int[aint[l].length];
            }

            if (l < aint1.length && aint1[l].length == aint[l].length) {
               for(int i1 = 0; i1 < aint[l].length; ++i1) {
                  int j1 = aint[l][i1];
                  int k1 = aint1[l][i1];
                  int l1 = this.func_188535_a(d0, j1 >> 16 & 255, k1 >> 16 & 255);
                  int i2 = this.func_188535_a(d0, j1 >> 8 & 255, k1 >> 8 & 255);
                  int j2 = this.func_188535_a(d0, j1 & 255, k1 & 255);
                  this.field_176605_b[l][i1] = j1 & -16777216 | l1 << 16 | i2 << 8 | j2;
               }
            }
         }

         TextureUtil.func_147955_a(this.field_176605_b, this.field_130223_c, this.field_130224_d, this.field_110975_c, this.field_110974_d, false, false);
      }

   }

   private int func_188535_a(double p_188535_1_, int p_188535_3_, int p_188535_4_) {
      return (int)(p_188535_1_ * (double)p_188535_3_ + (1.0D - p_188535_1_) * (double)p_188535_4_);
   }

   public int[][] func_147965_a(int p_147965_1_) {
      return this.field_110976_a.get(p_147965_1_);
   }

   public int func_110970_k() {
      return this.field_110976_a.size();
   }

   public void func_110966_b(int p_110966_1_) {
      this.field_130223_c = p_110966_1_;
   }

   public void func_110969_c(int p_110969_1_) {
      this.field_130224_d = p_110969_1_;
   }

   public void func_188538_a(PngSizeInfo p_188538_1_, boolean p_188538_2_) throws IOException {
      this.func_130102_n();
      this.field_130223_c = p_188538_1_.field_188533_a;
      this.field_130224_d = p_188538_1_.field_188534_b;
      if (p_188538_2_) {
         this.field_130224_d = this.field_130223_c;
      } else if (p_188538_1_.field_188534_b != p_188538_1_.field_188533_a) {
         throw new RuntimeException("broken aspect ratio and not an animation");
      }

   }

   public void func_188539_a(IResource p_188539_1_, int p_188539_2_) throws IOException {
      BufferedImage bufferedimage = TextureUtil.func_177053_a(p_188539_1_.func_110527_b());
      AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)p_188539_1_.func_110526_a("animation");
      int[][] aint = new int[p_188539_2_][];
      aint[0] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
      bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[0], 0, bufferedimage.getWidth());
      if (animationmetadatasection == null) {
         this.field_110976_a.add(aint);
      } else {
         int i = bufferedimage.getHeight() / this.field_130223_c;
         if (animationmetadatasection.func_110473_c() > 0) {
            Iterator lvt_7_1_ = animationmetadatasection.func_130073_e().iterator();

            while(lvt_7_1_.hasNext()) {
               int j = ((Integer)lvt_7_1_.next()).intValue();
               if (j >= i) {
                  throw new RuntimeException("invalid frameindex " + j);
               }

               this.func_130099_d(j);
               this.field_110976_a.set(j, func_147962_a(aint, this.field_130223_c, this.field_130223_c, j));
            }

            this.field_110982_k = animationmetadatasection;
         } else {
            List<AnimationFrame> list = Lists.<AnimationFrame>newArrayList();

            for(int k = 0; k < i; ++k) {
               this.field_110976_a.add(func_147962_a(aint, this.field_130223_c, this.field_130223_c, k));
               list.add(new AnimationFrame(k, -1));
            }

            this.field_110982_k = new AnimationMetadataSection(list, this.field_130223_c, this.field_130224_d, animationmetadatasection.func_110469_d(), animationmetadatasection.func_177219_e());
         }
      }

   }

   public void func_147963_d(int p_147963_1_) {
      List<int[][]> list = Lists.<int[][]>newArrayList();

      for(int i = 0; i < this.field_110976_a.size(); ++i) {
         final int[][] aint = this.field_110976_a.get(i);
         if (aint != null) {
            try {
               list.add(TextureUtil.func_147949_a(p_147963_1_, this.field_130223_c, aint));
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.func_85055_a(throwable, "Generating mipmaps for frame");
               CrashReportCategory crashreportcategory = crashreport.func_85058_a("Frame being iterated");
               crashreportcategory.func_71507_a("Frame index", Integer.valueOf(i));
               crashreportcategory.func_189529_a("Frame sizes", new ICrashReportDetail<String>() {
                  public String call() throws Exception {
                     StringBuilder stringbuilder = new StringBuilder();

                     for(int[] aint1 : aint) {
                        if (stringbuilder.length() > 0) {
                           stringbuilder.append(", ");
                        }

                        stringbuilder.append(aint1 == null ? "null" : aint1.length);
                     }

                     return stringbuilder.toString();
                  }
               });
               throw new ReportedException(crashreport);
            }
         }
      }

      this.func_110968_a(list);
   }

   private void func_130099_d(int p_130099_1_) {
      if (this.field_110976_a.size() <= p_130099_1_) {
         for(int i = this.field_110976_a.size(); i <= p_130099_1_; ++i) {
            this.field_110976_a.add((Object)null);
         }

      }
   }

   private static int[][] func_147962_a(int[][] p_147962_0_, int p_147962_1_, int p_147962_2_, int p_147962_3_) {
      int[][] aint = new int[p_147962_0_.length][];

      for(int i = 0; i < p_147962_0_.length; ++i) {
         int[] aint1 = p_147962_0_[i];
         if (aint1 != null) {
            aint[i] = new int[(p_147962_1_ >> i) * (p_147962_2_ >> i)];
            System.arraycopy(aint1, p_147962_3_ * aint[i].length, aint[i], 0, aint[i].length);
         }
      }

      return aint;
   }

   public void func_130103_l() {
      this.field_110976_a.clear();
   }

   public boolean func_130098_m() {
      return this.field_110982_k != null;
   }

   public void func_110968_a(List<int[][]> p_110968_1_) {
      this.field_110976_a = p_110968_1_;
   }

   private void func_130102_n() {
      this.field_110982_k = null;
      this.func_110968_a(Lists.newArrayList());
      this.field_110973_g = 0;
      this.field_110983_h = 0;
   }

   public String toString() {
      return "TextureAtlasSprite{name='" + this.field_110984_i + '\'' + ", frameCount=" + this.field_110976_a.size() + ", rotated=" + this.field_130222_e + ", x=" + this.field_110975_c + ", y=" + this.field_110974_d + ", height=" + this.field_130224_d + ", width=" + this.field_130223_c + ", u0=" + this.field_110979_l + ", u1=" + this.field_110980_m + ", v0=" + this.field_110977_n + ", v1=" + this.field_110978_o + '}';
   }
}
