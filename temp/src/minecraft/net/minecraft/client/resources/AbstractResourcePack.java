package net.minecraft.client.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final Logger field_110598_a = LogManager.getLogger();
   protected final File field_110597_b;

   public AbstractResourcePack(File p_i1287_1_) {
      this.field_110597_b = p_i1287_1_;
   }

   private static String func_110592_c(ResourceLocation p_110592_0_) {
      return String.format("%s/%s/%s", "assets", p_110592_0_.func_110624_b(), p_110592_0_.func_110623_a());
   }

   protected static String func_110595_a(File p_110595_0_, File p_110595_1_) {
      return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
   }

   public InputStream func_110590_a(ResourceLocation p_110590_1_) throws IOException {
      return this.func_110591_a(func_110592_c(p_110590_1_));
   }

   public boolean func_110589_b(ResourceLocation p_110589_1_) {
      return this.func_110593_b(func_110592_c(p_110589_1_));
   }

   protected abstract InputStream func_110591_a(String var1) throws IOException;

   protected abstract boolean func_110593_b(String var1);

   protected void func_110594_c(String p_110594_1_) {
      field_110598_a.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", p_110594_1_, this.field_110597_b);
   }

   public <T extends IMetadataSection> T func_135058_a(MetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
      return (T)func_110596_a(p_135058_1_, this.func_110591_a("pack.mcmeta"), p_135058_2_);
   }

   static <T extends IMetadataSection> T func_110596_a(MetadataSerializer p_110596_0_, InputStream p_110596_1_, String p_110596_2_) {
      JsonObject jsonobject = null;
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, StandardCharsets.UTF_8));
         jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
      } catch (RuntimeException runtimeexception) {
         throw new JsonParseException(runtimeexception);
      } finally {
         IOUtils.closeQuietly((Reader)bufferedreader);
      }

      return (T)p_110596_0_.func_110503_a(p_110596_2_, jsonobject);
   }

   public BufferedImage func_110586_a() throws IOException {
      return TextureUtil.func_177053_a(this.func_110591_a("pack.png"));
   }

   public String func_130077_b() {
      return this.field_110597_b.getName();
   }
}
