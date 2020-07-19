package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {
   private final Framebuffer field_148035_a;
   private final IResourceManager field_148033_b;
   private final String field_148034_c;
   private final List<Shader> field_148031_d = Lists.<Shader>newArrayList();
   private final Map<String, Framebuffer> field_148032_e = Maps.<String, Framebuffer>newHashMap();
   private final List<Framebuffer> field_148029_f = Lists.<Framebuffer>newArrayList();
   private Matrix4f field_148030_g;
   private int field_148038_h;
   private int field_148039_i;
   private float field_148036_j;
   private float field_148037_k;

   public ShaderGroup(TextureManager p_i1050_1_, IResourceManager p_i1050_2_, Framebuffer p_i1050_3_, ResourceLocation p_i1050_4_) throws JsonException, IOException, JsonSyntaxException {
      this.field_148033_b = p_i1050_2_;
      this.field_148035_a = p_i1050_3_;
      this.field_148036_j = 0.0F;
      this.field_148037_k = 0.0F;
      this.field_148038_h = p_i1050_3_.field_147621_c;
      this.field_148039_i = p_i1050_3_.field_147618_d;
      this.field_148034_c = p_i1050_4_.toString();
      this.func_148024_c();
      this.func_152765_a(p_i1050_1_, p_i1050_4_);
   }

   public void func_152765_a(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws JsonException, IOException, JsonSyntaxException {
      JsonParser jsonparser = new JsonParser();
      IResource iresource = null;

      try {
         iresource = this.field_148033_b.func_110536_a(p_152765_2_);
         JsonObject jsonobject = jsonparser.parse(IOUtils.toString(iresource.func_110527_b(), StandardCharsets.UTF_8)).getAsJsonObject();
         if (JsonUtils.func_151202_d(jsonobject, "targets")) {
            JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  this.func_148027_a(jsonelement);
               } catch (Exception exception1) {
                  JsonException jsonexception1 = JsonException.func_151379_a(exception1);
                  jsonexception1.func_151380_a("targets[" + i + "]");
                  throw jsonexception1;
               }

               ++i;
            }
         }

         if (JsonUtils.func_151202_d(jsonobject, "passes")) {
            JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
            int j = 0;

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.func_152764_a(p_152765_1_, jsonelement1);
               } catch (Exception exception) {
                  JsonException jsonexception2 = JsonException.func_151379_a(exception);
                  jsonexception2.func_151380_a("passes[" + j + "]");
                  throw jsonexception2;
               }

               ++j;
            }
         }
      } catch (Exception exception2) {
         JsonException jsonexception = JsonException.func_151379_a(exception2);
         jsonexception.func_151381_b(p_152765_2_.func_110623_a());
         throw jsonexception;
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

   }

   private void func_148027_a(JsonElement p_148027_1_) throws JsonException {
      if (JsonUtils.func_151211_a(p_148027_1_)) {
         this.func_148020_a(p_148027_1_.getAsString(), this.field_148038_h, this.field_148039_i);
      } else {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_148027_1_, "target");
         String s = JsonUtils.func_151200_h(jsonobject, "name");
         int i = JsonUtils.func_151208_a(jsonobject, "width", this.field_148038_h);
         int j = JsonUtils.func_151208_a(jsonobject, "height", this.field_148039_i);
         if (this.field_148032_e.containsKey(s)) {
            throw new JsonException(s + " is already defined");
         }

         this.func_148020_a(s, i, j);
      }

   }

   private void func_152764_a(TextureManager p_152764_1_, JsonElement p_152764_2_) throws JsonException, IOException {
      JsonObject jsonobject = JsonUtils.func_151210_l(p_152764_2_, "pass");
      String s = JsonUtils.func_151200_h(jsonobject, "name");
      String s1 = JsonUtils.func_151200_h(jsonobject, "intarget");
      String s2 = JsonUtils.func_151200_h(jsonobject, "outtarget");
      Framebuffer framebuffer = this.func_148017_a(s1);
      Framebuffer framebuffer1 = this.func_148017_a(s2);
      if (framebuffer == null) {
         throw new JsonException("Input target '" + s1 + "' does not exist");
      } else if (framebuffer1 == null) {
         throw new JsonException("Output target '" + s2 + "' does not exist");
      } else {
         Shader shader = this.func_148023_a(s, framebuffer, framebuffer1);
         JsonArray jsonarray = JsonUtils.func_151213_a(jsonobject, "auxtargets", (JsonArray)null);
         if (jsonarray != null) {
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  JsonObject jsonobject1 = JsonUtils.func_151210_l(jsonelement, "auxtarget");
                  String s4 = JsonUtils.func_151200_h(jsonobject1, "name");
                  String s3 = JsonUtils.func_151200_h(jsonobject1, "id");
                  Framebuffer framebuffer2 = this.func_148017_a(s3);
                  if (framebuffer2 == null) {
                     ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
                     IResource iresource = null;

                     try {
                        iresource = this.field_148033_b.func_110536_a(resourcelocation);
                     } catch (FileNotFoundException var29) {
                        throw new JsonException("Render target or texture '" + s3 + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly((Closeable)iresource);
                     }

                     p_152764_1_.func_110577_a(resourcelocation);
                     ITextureObject lvt_20_2_ = p_152764_1_.func_110581_b(resourcelocation);
                     int lvt_21_1_ = JsonUtils.func_151203_m(jsonobject1, "width");
                     int lvt_22_1_ = JsonUtils.func_151203_m(jsonobject1, "height");
                     boolean lvt_23_1_ = JsonUtils.func_151212_i(jsonobject1, "bilinear");
                     if (lvt_23_1_) {
                        GlStateManager.func_187421_b(3553, 10241, 9729);
                        GlStateManager.func_187421_b(3553, 10240, 9729);
                     } else {
                        GlStateManager.func_187421_b(3553, 10241, 9728);
                        GlStateManager.func_187421_b(3553, 10240, 9728);
                     }

                     shader.func_148041_a(s4, Integer.valueOf(lvt_20_2_.func_110552_b()), lvt_21_1_, lvt_22_1_);
                  } else {
                     shader.func_148041_a(s4, framebuffer2, framebuffer2.field_147622_a, framebuffer2.field_147620_b);
                  }
               } catch (Exception exception1) {
                  JsonException jsonexception = JsonException.func_151379_a(exception1);
                  jsonexception.func_151380_a("auxtargets[" + i + "]");
                  throw jsonexception;
               }

               ++i;
            }
         }

         JsonArray jsonarray1 = JsonUtils.func_151213_a(jsonobject, "uniforms", (JsonArray)null);
         if (jsonarray1 != null) {
            int l = 0;

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.func_148028_c(jsonelement1);
               } catch (Exception exception) {
                  JsonException jsonexception1 = JsonException.func_151379_a(exception);
                  jsonexception1.func_151380_a("uniforms[" + l + "]");
                  throw jsonexception1;
               }

               ++l;
            }
         }

      }
   }

   private void func_148028_c(JsonElement p_148028_1_) throws JsonException {
      JsonObject jsonobject = JsonUtils.func_151210_l(p_148028_1_, "uniform");
      String s = JsonUtils.func_151200_h(jsonobject, "name");
      ShaderUniform shaderuniform = ((Shader)this.field_148031_d.get(this.field_148031_d.size() - 1)).func_148043_c().func_147991_a(s);
      if (shaderuniform == null) {
         throw new JsonException("Uniform '" + s + "' does not exist");
      } else {
         float[] afloat = new float[4];
         int i = 0;

         for(JsonElement jsonelement : JsonUtils.func_151214_t(jsonobject, "values")) {
            try {
               afloat[i] = JsonUtils.func_151220_d(jsonelement, "value");
            } catch (Exception exception) {
               JsonException jsonexception = JsonException.func_151379_a(exception);
               jsonexception.func_151380_a("values[" + i + "]");
               throw jsonexception;
            }

            ++i;
         }

         switch(i) {
         case 0:
         default:
            break;
         case 1:
            shaderuniform.func_148090_a(afloat[0]);
            break;
         case 2:
            shaderuniform.func_148087_a(afloat[0], afloat[1]);
            break;
         case 3:
            shaderuniform.func_148095_a(afloat[0], afloat[1], afloat[2]);
            break;
         case 4:
            shaderuniform.func_148081_a(afloat[0], afloat[1], afloat[2], afloat[3]);
         }

      }
   }

   public Framebuffer func_177066_a(String p_177066_1_) {
      return this.field_148032_e.get(p_177066_1_);
   }

   public void func_148020_a(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
      Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
      framebuffer.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
      this.field_148032_e.put(p_148020_1_, framebuffer);
      if (p_148020_2_ == this.field_148038_h && p_148020_3_ == this.field_148039_i) {
         this.field_148029_f.add(framebuffer);
      }

   }

   public void func_148021_a() {
      for(Framebuffer framebuffer : this.field_148032_e.values()) {
         framebuffer.func_147608_a();
      }

      for(Shader shader : this.field_148031_d) {
         shader.func_148044_b();
      }

      this.field_148031_d.clear();
   }

   public Shader func_148023_a(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException, IOException {
      Shader shader = new Shader(this.field_148033_b, p_148023_1_, p_148023_2_, p_148023_3_);
      this.field_148031_d.add(this.field_148031_d.size(), shader);
      return shader;
   }

   private void func_148024_c() {
      this.field_148030_g = new Matrix4f();
      this.field_148030_g.setIdentity();
      this.field_148030_g.m00 = 2.0F / (float)this.field_148035_a.field_147622_a;
      this.field_148030_g.m11 = 2.0F / (float)(-this.field_148035_a.field_147620_b);
      this.field_148030_g.m22 = -0.0020001999F;
      this.field_148030_g.m33 = 1.0F;
      this.field_148030_g.m03 = -1.0F;
      this.field_148030_g.m13 = 1.0F;
      this.field_148030_g.m23 = -1.0001999F;
   }

   public void func_148026_a(int p_148026_1_, int p_148026_2_) {
      this.field_148038_h = this.field_148035_a.field_147622_a;
      this.field_148039_i = this.field_148035_a.field_147620_b;
      this.func_148024_c();

      for(Shader shader : this.field_148031_d) {
         shader.func_148045_a(this.field_148030_g);
      }

      for(Framebuffer framebuffer : this.field_148029_f) {
         framebuffer.func_147613_a(p_148026_1_, p_148026_2_);
      }

   }

   public void func_148018_a(float p_148018_1_) {
      if (p_148018_1_ < this.field_148037_k) {
         this.field_148036_j += 1.0F - this.field_148037_k;
         this.field_148036_j += p_148018_1_;
      } else {
         this.field_148036_j += p_148018_1_ - this.field_148037_k;
      }

      for(this.field_148037_k = p_148018_1_; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F) {
         ;
      }

      for(Shader shader : this.field_148031_d) {
         shader.func_148042_a(this.field_148036_j / 20.0F);
      }

   }

   public final String func_148022_b() {
      return this.field_148034_c;
   }

   private Framebuffer func_148017_a(String p_148017_1_) {
      if (p_148017_1_ == null) {
         return null;
      } else {
         return p_148017_1_.equals("minecraft:main") ? this.field_148035_a : (Framebuffer)this.field_148032_e.get(p_148017_1_);
      }
   }
}
