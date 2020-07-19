package net.minecraft.client.renderer.texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {
   private static final Logger field_174947_f = LogManager.getLogger();
   private final ResourceLocation field_174948_g;
   private final List<String> field_174949_h;
   private final List<EnumDyeColor> field_174950_i;

   public LayeredColorMaskTexture(ResourceLocation p_i46101_1_, List<String> p_i46101_2_, List<EnumDyeColor> p_i46101_3_) {
      this.field_174948_g = p_i46101_1_;
      this.field_174949_h = p_i46101_2_;
      this.field_174950_i = p_i46101_3_;
   }

   public void func_110551_a(IResourceManager p_110551_1_) throws IOException {
      this.func_147631_c();
      IResource iresource = null;

      BufferedImage bufferedimage;
      label255: {
         try {
            iresource = p_110551_1_.func_110536_a(this.field_174948_g);
            BufferedImage bufferedimage1 = TextureUtil.func_177053_a(iresource.func_110527_b());
            int i = bufferedimage1.getType();
            if (i == 0) {
               i = 6;
            }

            bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), i);
            Graphics graphics = bufferedimage.getGraphics();
            graphics.drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
            int j = 0;

            while(true) {
               if (j >= 17 || j >= this.field_174949_h.size() || j >= this.field_174950_i.size()) {
                  break label255;
               }

               IResource iresource1 = null;

               try {
                  String s = this.field_174949_h.get(j);
                  int k = ((EnumDyeColor)this.field_174950_i.get(j)).func_193350_e();
                  if (s != null) {
                     iresource1 = p_110551_1_.func_110536_a(new ResourceLocation(s));
                     BufferedImage bufferedimage2 = TextureUtil.func_177053_a(iresource1.func_110527_b());
                     if (bufferedimage2.getWidth() == bufferedimage.getWidth() && bufferedimage2.getHeight() == bufferedimage.getHeight() && bufferedimage2.getType() == 6) {
                        for(int l = 0; l < bufferedimage2.getHeight(); ++l) {
                           for(int i1 = 0; i1 < bufferedimage2.getWidth(); ++i1) {
                              int j1 = bufferedimage2.getRGB(i1, l);
                              if ((j1 & -16777216) != 0) {
                                 int k1 = (j1 & 16711680) << 8 & -16777216;
                                 int l1 = bufferedimage1.getRGB(i1, l);
                                 int i2 = MathHelper.func_180188_d(l1, k) & 16777215;
                                 bufferedimage2.setRGB(i1, l, k1 | i2);
                              }
                           }
                        }

                        bufferedimage.getGraphics().drawImage(bufferedimage2, 0, 0, (ImageObserver)null);
                     }
                  }
               } finally {
                  IOUtils.closeQuietly((Closeable)iresource1);
               }

               ++j;
            }
         } catch (IOException ioexception) {
            field_174947_f.error("Couldn't load layered image", (Throwable)ioexception);
         } finally {
            IOUtils.closeQuietly((Closeable)iresource);
         }

         return;
      }

      TextureUtil.func_110987_a(this.func_110552_b(), bufferedimage);
   }
}
