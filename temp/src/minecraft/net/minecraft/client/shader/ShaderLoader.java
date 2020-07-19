package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private final ShaderLoader.ShaderType field_148061_a;
   private final String field_148059_b;
   private final int field_148060_c;
   private int field_148058_d;

   private ShaderLoader(ShaderLoader.ShaderType p_i45091_1_, int p_i45091_2_, String p_i45091_3_) {
      this.field_148061_a = p_i45091_1_;
      this.field_148060_c = p_i45091_2_;
      this.field_148059_b = p_i45091_3_;
   }

   public void func_148056_a(ShaderManager p_148056_1_) {
      ++this.field_148058_d;
      OpenGlHelper.func_153178_b(p_148056_1_.func_147986_h(), this.field_148060_c);
   }

   public void func_148054_b(ShaderManager p_148054_1_) {
      --this.field_148058_d;
      if (this.field_148058_d <= 0) {
         OpenGlHelper.func_153180_a(this.field_148060_c);
         this.field_148061_a.func_148064_d().remove(this.field_148059_b);
      }

   }

   public String func_148055_a() {
      return this.field_148059_b;
   }

   public static ShaderLoader func_148057_a(IResourceManager p_148057_0_, ShaderLoader.ShaderType p_148057_1_, String p_148057_2_) throws IOException {
      ShaderLoader shaderloader = (ShaderLoader)p_148057_1_.func_148064_d().get(p_148057_2_);
      if (shaderloader == null) {
         ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + p_148057_2_ + p_148057_1_.func_148063_b());
         IResource iresource = p_148057_0_.func_110536_a(resourcelocation);

         try {
            byte[] abyte = IOUtils.toByteArray(new BufferedInputStream(iresource.func_110527_b()));
            ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
            bytebuffer.put(abyte);
            bytebuffer.position(0);
            int i = OpenGlHelper.func_153195_b(p_148057_1_.func_148065_c());
            OpenGlHelper.func_153169_a(i, bytebuffer);
            OpenGlHelper.func_153170_c(i);
            if (OpenGlHelper.func_153157_c(i, OpenGlHelper.field_153208_p) == 0) {
               String s = StringUtils.trim(OpenGlHelper.func_153158_d(i, 32768));
               JsonException jsonexception = new JsonException("Couldn't compile " + p_148057_1_.func_148062_a() + " program: " + s);
               jsonexception.func_151381_b(resourcelocation.func_110623_a());
               throw jsonexception;
            }

            shaderloader = new ShaderLoader(p_148057_1_, i, p_148057_2_);
            p_148057_1_.func_148064_d().put(p_148057_2_, shaderloader);
         } finally {
            IOUtils.closeQuietly((Closeable)iresource);
         }
      }

      return shaderloader;
   }

   public static enum ShaderType {
      VERTEX("vertex", ".vsh", OpenGlHelper.field_153209_q),
      FRAGMENT("fragment", ".fsh", OpenGlHelper.field_153210_r);

      private final String field_148072_c;
      private final String field_148069_d;
      private final int field_148070_e;
      private final Map<String, ShaderLoader> field_148067_f = Maps.<String, ShaderLoader>newHashMap();

      private ShaderType(String p_i45090_3_, String p_i45090_4_, int p_i45090_5_) {
         this.field_148072_c = p_i45090_3_;
         this.field_148069_d = p_i45090_4_;
         this.field_148070_e = p_i45090_5_;
      }

      public String func_148062_a() {
         return this.field_148072_c;
      }

      private String func_148063_b() {
         return this.field_148069_d;
      }

      private int func_148065_c() {
         return this.field_148070_e;
      }

      private Map<String, ShaderLoader> func_148064_d() {
         return this.field_148067_f;
      }
   }
}
