package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack {
   public static final Set<String> field_110608_a = ImmutableSet.<String>of("minecraft", "realms");
   private final ResourceIndex field_188549_b;

   public DefaultResourcePack(ResourceIndex p_i46541_1_) {
      this.field_188549_b = p_i46541_1_;
   }

   public InputStream func_110590_a(ResourceLocation p_110590_1_) throws IOException {
      InputStream inputstream = this.func_110605_c(p_110590_1_);
      if (inputstream != null) {
         return inputstream;
      } else {
         InputStream inputstream1 = this.func_152780_c(p_110590_1_);
         if (inputstream1 != null) {
            return inputstream1;
         } else {
            throw new FileNotFoundException(p_110590_1_.func_110623_a());
         }
      }
   }

   @Nullable
   public InputStream func_152780_c(ResourceLocation p_152780_1_) throws IOException, FileNotFoundException {
      File file1 = this.field_188549_b.func_188547_a(p_152780_1_);
      return file1 != null && file1.isFile() ? new FileInputStream(file1) : null;
   }

   @Nullable
   private InputStream func_110605_c(ResourceLocation p_110605_1_) {
      String s = "/assets/" + p_110605_1_.func_110624_b() + "/" + p_110605_1_.func_110623_a();

      try {
         URL url = DefaultResourcePack.class.getResource(s);
         return url != null && FolderResourcePack.func_191384_a(new File(url.getFile()), s) ? DefaultResourcePack.class.getResourceAsStream(s) : null;
      } catch (IOException var4) {
         return DefaultResourcePack.class.getResourceAsStream(s);
      }
   }

   public boolean func_110589_b(ResourceLocation p_110589_1_) {
      return this.func_110605_c(p_110589_1_) != null || this.field_188549_b.func_188545_b(p_110589_1_);
   }

   public Set<String> func_110587_b() {
      return field_110608_a;
   }

   @Nullable
   public <T extends IMetadataSection> T func_135058_a(MetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
      try {
         InputStream inputstream = new FileInputStream(this.field_188549_b.func_188546_a());
         return (T)AbstractResourcePack.func_110596_a(p_135058_1_, inputstream, p_135058_2_);
      } catch (RuntimeException var4) {
         return (T)null;
      } catch (FileNotFoundException var5) {
         return (T)null;
      }
   }

   public BufferedImage func_110586_a() throws IOException {
      return TextureUtil.func_177053_a(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).func_110623_a()));
   }

   public String func_130077_b() {
      return "Default";
   }
}
