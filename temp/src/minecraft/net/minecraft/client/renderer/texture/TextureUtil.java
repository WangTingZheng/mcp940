package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureUtil {
   private static final Logger field_147959_c = LogManager.getLogger();
   private static final IntBuffer field_111000_c = GLAllocation.func_74527_f(4194304);
   public static final DynamicTexture field_111001_a = new DynamicTexture(16, 16);
   public static final int[] field_110999_b = field_111001_a.func_110565_c();
   private static final float[] field_188544_e;
   private static final int[] field_147957_g;

   private static float func_188543_d(int p_188543_0_) {
      return field_188544_e[p_188543_0_ & 255];
   }

   public static int func_110996_a() {
      return GlStateManager.func_179146_y();
   }

   public static void func_147942_a(int p_147942_0_) {
      GlStateManager.func_179150_h(p_147942_0_);
   }

   public static int func_110987_a(int p_110987_0_, BufferedImage p_110987_1_) {
      return func_110989_a(p_110987_0_, p_110987_1_, false, false);
   }

   public static void func_110988_a(int p_110988_0_, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
      func_94277_a(p_110988_0_);
      func_147947_a(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
   }

   public static int[][] func_147949_a(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
      int[][] aint = new int[p_147949_0_ + 1][];
      aint[0] = p_147949_2_[0];
      if (p_147949_0_ > 0) {
         boolean flag = false;

         for(int i = 0; i < p_147949_2_.length; ++i) {
            if (p_147949_2_[0][i] >> 24 == 0) {
               flag = true;
               break;
            }
         }

         for(int l1 = 1; l1 <= p_147949_0_; ++l1) {
            if (p_147949_2_[l1] != null) {
               aint[l1] = p_147949_2_[l1];
            } else {
               int[] aint1 = aint[l1 - 1];
               int[] aint2 = new int[aint1.length >> 2];
               int j = p_147949_1_ >> l1;
               int k = aint2.length / j;
               int l = j << 1;

               for(int i1 = 0; i1 < j; ++i1) {
                  for(int j1 = 0; j1 < k; ++j1) {
                     int k1 = 2 * (i1 + j1 * l);
                     aint2[i1 + j1 * j] = func_147943_a(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
                  }
               }

               aint[l1] = aint2;
            }
         }
      }

      return aint;
   }

   private static int func_147943_a(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_) {
      if (p_147943_4_) {
         field_147957_g[0] = p_147943_0_;
         field_147957_g[1] = p_147943_1_;
         field_147957_g[2] = p_147943_2_;
         field_147957_g[3] = p_147943_3_;
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;

         for(int i1 = 0; i1 < 4; ++i1) {
            if (field_147957_g[i1] >> 24 != 0) {
               f += func_188543_d(field_147957_g[i1] >> 24);
               f1 += func_188543_d(field_147957_g[i1] >> 16);
               f2 += func_188543_d(field_147957_g[i1] >> 8);
               f3 += func_188543_d(field_147957_g[i1] >> 0);
            }
         }

         f = f / 4.0F;
         f1 = f1 / 4.0F;
         f2 = f2 / 4.0F;
         f3 = f3 / 4.0F;
         int i2 = (int)(Math.pow((double)f, 0.45454545454545453D) * 255.0D);
         int j1 = (int)(Math.pow((double)f1, 0.45454545454545453D) * 255.0D);
         int k1 = (int)(Math.pow((double)f2, 0.45454545454545453D) * 255.0D);
         int l1 = (int)(Math.pow((double)f3, 0.45454545454545453D) * 255.0D);
         if (i2 < 96) {
            i2 = 0;
         }

         return i2 << 24 | j1 << 16 | k1 << 8 | l1;
      } else {
         int i = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 24);
         int j = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 16);
         int k = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 8);
         int l = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 0);
         return i << 24 | j << 16 | k << 8 | l;
      }
   }

   private static int func_147944_a(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
      float f = func_188543_d(p_147944_0_ >> p_147944_4_);
      float f1 = func_188543_d(p_147944_1_ >> p_147944_4_);
      float f2 = func_188543_d(p_147944_2_ >> p_147944_4_);
      float f3 = func_188543_d(p_147944_3_ >> p_147944_4_);
      float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D)));
      return (int)((double)f4 * 255.0D);
   }

   public static void func_147955_a(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
      for(int i = 0; i < p_147955_0_.length; ++i) {
         int[] aint = p_147955_0_[i];
         func_147947_a(i, aint, p_147955_1_ >> i, p_147955_2_ >> i, p_147955_3_ >> i, p_147955_4_ >> i, p_147955_5_, p_147955_6_, p_147955_0_.length > 1);
      }

   }

   private static void func_147947_a(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
      int i = 4194304 / p_147947_2_;
      func_147954_b(p_147947_6_, p_147947_8_);
      func_110997_a(p_147947_7_);

      int l;
      for(int j = 0; j < p_147947_2_ * p_147947_3_; j += p_147947_2_ * l) {
         int k = j / p_147947_2_;
         l = Math.min(i, p_147947_3_ - k);
         int i1 = p_147947_2_ * l;
         func_110994_a(p_147947_1_, j, i1);
         GlStateManager.func_187414_b(3553, p_147947_0_, p_147947_4_, p_147947_5_ + k, p_147947_2_, l, 32993, 33639, field_111000_c);
      }

   }

   public static int func_110989_a(int p_110989_0_, BufferedImage p_110989_1_, boolean p_110989_2_, boolean p_110989_3_) {
      func_110991_a(p_110989_0_, p_110989_1_.getWidth(), p_110989_1_.getHeight());
      return func_110995_a(p_110989_0_, p_110989_1_, 0, 0, p_110989_2_, p_110989_3_);
   }

   public static void func_110991_a(int p_110991_0_, int p_110991_1_, int p_110991_2_) {
      func_180600_a(p_110991_0_, 0, p_110991_1_, p_110991_2_);
   }

   public static void func_180600_a(int p_180600_0_, int p_180600_1_, int p_180600_2_, int p_180600_3_) {
      func_147942_a(p_180600_0_);
      func_94277_a(p_180600_0_);
      if (p_180600_1_ >= 0) {
         GlStateManager.func_187421_b(3553, 33085, p_180600_1_);
         GlStateManager.func_187421_b(3553, 33082, 0);
         GlStateManager.func_187421_b(3553, 33083, p_180600_1_);
         GlStateManager.func_187403_b(3553, 34049, 0.0F);
      }

      for(int i = 0; i <= p_180600_1_; ++i) {
         GlStateManager.func_187419_a(3553, i, 6408, p_180600_2_ >> i, p_180600_3_ >> i, 0, 32993, 33639, (IntBuffer)null);
      }

   }

   public static int func_110995_a(int p_110995_0_, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
      func_94277_a(p_110995_0_);
      func_110993_a(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
      return p_110995_0_;
   }

   private static void func_110993_a(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
      int i = p_110993_0_.getWidth();
      int j = p_110993_0_.getHeight();
      int k = 4194304 / i;
      int[] aint = new int[k * i];
      func_147951_b(p_110993_3_);
      func_110997_a(p_110993_4_);

      for(int l = 0; l < i * j; l += i * k) {
         int i1 = l / i;
         int j1 = Math.min(k, j - i1);
         int k1 = i * j1;
         p_110993_0_.getRGB(0, i1, i, j1, aint, 0, i);
         func_110990_a(aint, k1);
         GlStateManager.func_187414_b(3553, 0, p_110993_1_, p_110993_2_ + i1, i, j1, 32993, 33639, field_111000_c);
      }

   }

   private static void func_110997_a(boolean p_110997_0_) {
      if (p_110997_0_) {
         GlStateManager.func_187421_b(3553, 10242, 10496);
         GlStateManager.func_187421_b(3553, 10243, 10496);
      } else {
         GlStateManager.func_187421_b(3553, 10242, 10497);
         GlStateManager.func_187421_b(3553, 10243, 10497);
      }

   }

   private static void func_147951_b(boolean p_147951_0_) {
      func_147954_b(p_147951_0_, false);
   }

   private static void func_147954_b(boolean p_147954_0_, boolean p_147954_1_) {
      if (p_147954_0_) {
         GlStateManager.func_187421_b(3553, 10241, p_147954_1_ ? 9987 : 9729);
         GlStateManager.func_187421_b(3553, 10240, 9729);
      } else {
         GlStateManager.func_187421_b(3553, 10241, p_147954_1_ ? 9986 : 9728);
         GlStateManager.func_187421_b(3553, 10240, 9728);
      }

   }

   private static void func_110990_a(int[] p_110990_0_, int p_110990_1_) {
      func_110994_a(p_110990_0_, 0, p_110990_1_);
   }

   private static void func_110994_a(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
      int[] aint = p_110994_0_;
      if (Minecraft.func_71410_x().field_71474_y.field_74337_g) {
         aint = func_110985_a(p_110994_0_);
      }

      field_111000_c.clear();
      field_111000_c.put(aint, p_110994_1_, p_110994_2_);
      field_111000_c.position(0).limit(p_110994_2_);
   }

   static void func_94277_a(int p_94277_0_) {
      GlStateManager.func_179144_i(p_94277_0_);
   }

   public static int[] func_110986_a(IResourceManager p_110986_0_, ResourceLocation p_110986_1_) throws IOException {
      IResource iresource = null;

      int[] aint1;
      try {
         iresource = p_110986_0_.func_110536_a(p_110986_1_);
         BufferedImage bufferedimage = func_177053_a(iresource.func_110527_b());
         int i = bufferedimage.getWidth();
         int j = bufferedimage.getHeight();
         int[] aint = new int[i * j];
         bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
         aint1 = aint;
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

      return aint1;
   }

   public static BufferedImage func_177053_a(InputStream p_177053_0_) throws IOException {
      BufferedImage bufferedimage;
      try {
         bufferedimage = ImageIO.read(p_177053_0_);
      } finally {
         IOUtils.closeQuietly(p_177053_0_);
      }

      return bufferedimage;
   }

   public static int[] func_110985_a(int[] p_110985_0_) {
      int[] aint = new int[p_110985_0_.length];

      for(int i = 0; i < p_110985_0_.length; ++i) {
         aint[i] = func_177054_c(p_110985_0_[i]);
      }

      return aint;
   }

   public static int func_177054_c(int p_177054_0_) {
      int i = p_177054_0_ >> 24 & 255;
      int j = p_177054_0_ >> 16 & 255;
      int k = p_177054_0_ >> 8 & 255;
      int l = p_177054_0_ & 255;
      int i1 = (j * 30 + k * 59 + l * 11) / 100;
      int j1 = (j * 30 + k * 70) / 100;
      int k1 = (j * 30 + l * 70) / 100;
      return i << 24 | i1 << 16 | j1 << 8 | k1;
   }

   public static void func_147953_a(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
      int[] aint = new int[p_147953_1_];
      int i = p_147953_2_ / 2;

      for(int j = 0; j < i; ++j) {
         System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
         System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
         System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
      }

   }

   static {
      int i = -16777216;
      int j = -524040;
      int[] aint = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] aint1 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int k = aint.length;

      for(int l = 0; l < 16; ++l) {
         System.arraycopy(l < k ? aint : aint1, 0, field_110999_b, 16 * l, k);
         System.arraycopy(l < k ? aint1 : aint, 0, field_110999_b, 16 * l + k, k);
      }

      field_111001_a.func_110564_a();
      field_188544_e = new float[256];

      for(int i1 = 0; i1 < field_188544_e.length; ++i1) {
         field_188544_e[i1] = (float)Math.pow((double)((float)i1 / 255.0F), 2.2D);
      }

      field_147957_g = new int[4];
   }
}
