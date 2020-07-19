package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
   private static final Logger field_147639_c = LogManager.getLogger();
   protected final ResourceLocation field_110568_b;

   public SimpleTexture(ResourceLocation p_i1275_1_) {
      this.field_110568_b = p_i1275_1_;
   }

   public void func_110551_a(IResourceManager p_110551_1_) throws IOException {
      this.func_147631_c();
      IResource iresource = null;

      try {
         iresource = p_110551_1_.func_110536_a(this.field_110568_b);
         BufferedImage bufferedimage = TextureUtil.func_177053_a(iresource.func_110527_b());
         boolean flag = false;
         boolean flag1 = false;
         if (iresource.func_110528_c()) {
            try {
               TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.func_110526_a("texture");
               if (texturemetadatasection != null) {
                  flag = texturemetadatasection.func_110479_a();
                  flag1 = texturemetadatasection.func_110480_b();
               }
            } catch (RuntimeException runtimeexception) {
               field_147639_c.warn("Failed reading metadata of: {}", this.field_110568_b, runtimeexception);
            }
         }

         TextureUtil.func_110989_a(this.func_110552_b(), bufferedimage, flag, flag1);
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

   }
}
