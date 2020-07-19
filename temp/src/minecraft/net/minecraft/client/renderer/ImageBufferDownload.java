package net.minecraft.client.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import javax.annotation.Nullable;

public class ImageBufferDownload implements IImageBuffer {
   private int[] field_78438_a;
   private int field_78436_b;
   private int field_78437_c;

   @Nullable
   public BufferedImage func_78432_a(BufferedImage p_78432_1_) {
      if (p_78432_1_ == null) {
         return null;
      } else {
         this.field_78436_b = 64;
         this.field_78437_c = 64;
         BufferedImage bufferedimage = new BufferedImage(this.field_78436_b, this.field_78437_c, 2);
         Graphics graphics = bufferedimage.getGraphics();
         graphics.drawImage(p_78432_1_, 0, 0, (ImageObserver)null);
         boolean flag = p_78432_1_.getHeight() == 32;
         if (flag) {
            graphics.setColor(new Color(0, 0, 0, 0));
            graphics.fillRect(0, 32, 64, 32);
            graphics.drawImage(bufferedimage, 24, 48, 20, 52, 4, 16, 8, 20, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 28, 48, 24, 52, 8, 16, 12, 20, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 20, 52, 16, 64, 8, 20, 12, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 24, 52, 20, 64, 4, 20, 8, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 28, 52, 24, 64, 0, 20, 4, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 32, 52, 28, 64, 12, 20, 16, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 40, 48, 36, 52, 44, 16, 48, 20, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 44, 48, 40, 52, 48, 16, 52, 20, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 36, 52, 32, 64, 48, 20, 52, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 40, 52, 36, 64, 44, 20, 48, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 44, 52, 40, 64, 40, 20, 44, 32, (ImageObserver)null);
            graphics.drawImage(bufferedimage, 48, 52, 44, 64, 52, 20, 56, 32, (ImageObserver)null);
         }

         graphics.dispose();
         this.field_78438_a = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
         this.func_78433_b(0, 0, 32, 16);
         if (flag) {
            this.func_189559_a(32, 0, 64, 32);
         }

         this.func_78433_b(0, 16, 64, 32);
         this.func_78433_b(16, 48, 48, 64);
         return bufferedimage;
      }
   }

   public void func_152634_a() {
   }

   private void func_189559_a(int p_189559_1_, int p_189559_2_, int p_189559_3_, int p_189559_4_) {
      for(int i = p_189559_1_; i < p_189559_3_; ++i) {
         for(int j = p_189559_2_; j < p_189559_4_; ++j) {
            int k = this.field_78438_a[i + j * this.field_78436_b];
            if ((k >> 24 & 255) < 128) {
               return;
            }
         }
      }

      for(int l = p_189559_1_; l < p_189559_3_; ++l) {
         for(int i1 = p_189559_2_; i1 < p_189559_4_; ++i1) {
            this.field_78438_a[l + i1 * this.field_78436_b] &= 16777215;
         }
      }

   }

   private void func_78433_b(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
      for(int i = p_78433_1_; i < p_78433_3_; ++i) {
         for(int j = p_78433_2_; j < p_78433_4_; ++j) {
            this.field_78438_a[i + j * this.field_78436_b] |= -16777216;
         }
      }

   }
}
