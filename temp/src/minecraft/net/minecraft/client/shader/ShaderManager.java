package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderManager {
   private static final Logger field_148003_a = LogManager.getLogger();
   private static final ShaderDefault field_148001_b = new ShaderDefault();
   private static ShaderManager field_148002_c;
   private static int field_147999_d = -1;
   private static boolean field_148000_e = true;
   private final Map<String, Object> field_147997_f = Maps.<String, Object>newHashMap();
   private final List<String> field_147998_g = Lists.<String>newArrayList();
   private final List<Integer> field_148010_h = Lists.<Integer>newArrayList();
   private final List<ShaderUniform> field_148011_i = Lists.<ShaderUniform>newArrayList();
   private final List<Integer> field_148008_j = Lists.<Integer>newArrayList();
   private final Map<String, ShaderUniform> field_148009_k = Maps.<String, ShaderUniform>newHashMap();
   private final int field_148006_l;
   private final String field_148007_m;
   private final boolean field_148004_n;
   private boolean field_148005_o;
   private final JsonBlendingMode field_148016_p;
   private final List<Integer> field_148015_q;
   private final List<String> field_148014_r;
   private final ShaderLoader field_148013_s;
   private final ShaderLoader field_148012_t;

   public ShaderManager(IResourceManager p_i45087_1_, String p_i45087_2_) throws JsonException, IOException {
      JsonParser jsonparser = new JsonParser();
      ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + p_i45087_2_ + ".json");
      this.field_148007_m = p_i45087_2_;
      IResource iresource = null;

      try {
         iresource = p_i45087_1_.func_110536_a(resourcelocation);
         JsonObject jsonobject = jsonparser.parse(IOUtils.toString(iresource.func_110527_b(), StandardCharsets.UTF_8)).getAsJsonObject();
         String s = JsonUtils.func_151200_h(jsonobject, "vertex");
         String s1 = JsonUtils.func_151200_h(jsonobject, "fragment");
         JsonArray jsonarray = JsonUtils.func_151213_a(jsonobject, "samplers", (JsonArray)null);
         if (jsonarray != null) {
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  this.func_147996_a(jsonelement);
               } catch (Exception exception2) {
                  JsonException jsonexception1 = JsonException.func_151379_a(exception2);
                  jsonexception1.func_151380_a("samplers[" + i + "]");
                  throw jsonexception1;
               }

               ++i;
            }
         }

         JsonArray jsonarray1 = JsonUtils.func_151213_a(jsonobject, "attributes", (JsonArray)null);
         if (jsonarray1 != null) {
            int j = 0;
            this.field_148015_q = Lists.<Integer>newArrayListWithCapacity(jsonarray1.size());
            this.field_148014_r = Lists.<String>newArrayListWithCapacity(jsonarray1.size());

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.field_148014_r.add(JsonUtils.func_151206_a(jsonelement1, "attribute"));
               } catch (Exception exception1) {
                  JsonException jsonexception2 = JsonException.func_151379_a(exception1);
                  jsonexception2.func_151380_a("attributes[" + j + "]");
                  throw jsonexception2;
               }

               ++j;
            }
         } else {
            this.field_148015_q = null;
            this.field_148014_r = null;
         }

         JsonArray jsonarray2 = JsonUtils.func_151213_a(jsonobject, "uniforms", (JsonArray)null);
         if (jsonarray2 != null) {
            int k = 0;

            for(JsonElement jsonelement2 : jsonarray2) {
               try {
                  this.func_147987_b(jsonelement2);
               } catch (Exception exception) {
                  JsonException jsonexception3 = JsonException.func_151379_a(exception);
                  jsonexception3.func_151380_a("uniforms[" + k + "]");
                  throw jsonexception3;
               }

               ++k;
            }
         }

         this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.func_151218_a(jsonobject, "blend", (JsonObject)null));
         this.field_148004_n = JsonUtils.func_151209_a(jsonobject, "cull", true);
         this.field_148013_s = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.VERTEX, s);
         this.field_148012_t = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.FRAGMENT, s1);
         this.field_148006_l = ShaderLinkHelper.func_148074_b().func_148078_c();
         ShaderLinkHelper.func_148074_b().func_148075_b(this);
         this.func_147990_i();
         if (this.field_148014_r != null) {
            for(String s2 : this.field_148014_r) {
               int l = OpenGlHelper.func_153164_b(this.field_148006_l, s2);
               this.field_148015_q.add(Integer.valueOf(l));
            }
         }
      } catch (Exception exception3) {
         JsonException jsonexception = JsonException.func_151379_a(exception3);
         jsonexception.func_151381_b(resourcelocation.func_110623_a());
         throw jsonexception;
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

      this.func_147985_d();
   }

   public void func_147988_a() {
      ShaderLinkHelper.func_148074_b().func_148077_a(this);
   }

   public void func_147993_b() {
      OpenGlHelper.func_153161_d(0);
      field_147999_d = -1;
      field_148002_c = null;
      field_148000_e = true;

      for(int i = 0; i < this.field_148010_h.size(); ++i) {
         if (this.field_147997_f.get(this.field_147998_g.get(i)) != null) {
            GlStateManager.func_179138_g(OpenGlHelper.field_77478_a + i);
            GlStateManager.func_179144_i(0);
         }
      }

   }

   public void func_147995_c() {
      this.field_148005_o = false;
      field_148002_c = this;
      this.field_148016_p.func_148109_a();
      if (this.field_148006_l != field_147999_d) {
         OpenGlHelper.func_153161_d(this.field_148006_l);
         field_147999_d = this.field_148006_l;
      }

      if (this.field_148004_n) {
         GlStateManager.func_179089_o();
      } else {
         GlStateManager.func_179129_p();
      }

      for(int i = 0; i < this.field_148010_h.size(); ++i) {
         if (this.field_147997_f.get(this.field_147998_g.get(i)) != null) {
            GlStateManager.func_179138_g(OpenGlHelper.field_77478_a + i);
            GlStateManager.func_179098_w();
            Object object = this.field_147997_f.get(this.field_147998_g.get(i));
            int j = -1;
            if (object instanceof Framebuffer) {
               j = ((Framebuffer)object).field_147617_g;
            } else if (object instanceof ITextureObject) {
               j = ((ITextureObject)object).func_110552_b();
            } else if (object instanceof Integer) {
               j = ((Integer)object).intValue();
            }

            if (j != -1) {
               GlStateManager.func_179144_i(j);
               OpenGlHelper.func_153163_f(OpenGlHelper.func_153194_a(this.field_148006_l, this.field_147998_g.get(i)), i);
            }
         }
      }

      for(ShaderUniform shaderuniform : this.field_148011_i) {
         shaderuniform.func_148093_b();
      }

   }

   public void func_147985_d() {
      this.field_148005_o = true;
   }

   @Nullable
   public ShaderUniform func_147991_a(String p_147991_1_) {
      return this.field_148009_k.get(p_147991_1_);
   }

   public ShaderUniform func_147984_b(String p_147984_1_) {
      ShaderUniform shaderuniform = this.func_147991_a(p_147984_1_);
      return (ShaderUniform)(shaderuniform == null ? field_148001_b : shaderuniform);
   }

   private void func_147990_i() {
      int i = 0;

      for(int j = 0; i < this.field_147998_g.size(); ++j) {
         String s = this.field_147998_g.get(i);
         int k = OpenGlHelper.func_153194_a(this.field_148006_l, s);
         if (k == -1) {
            field_148003_a.warn("Shader {}could not find sampler named {} in the specified shader program.", this.field_148007_m, s);
            this.field_147997_f.remove(s);
            this.field_147998_g.remove(j);
            --j;
         } else {
            this.field_148010_h.add(Integer.valueOf(k));
         }

         ++i;
      }

      for(ShaderUniform shaderuniform : this.field_148011_i) {
         String s1 = shaderuniform.func_148086_a();
         int l = OpenGlHelper.func_153194_a(this.field_148006_l, s1);
         if (l == -1) {
            field_148003_a.warn("Could not find uniform named {} in the specified shader program.", (Object)s1);
         } else {
            this.field_148008_j.add(Integer.valueOf(l));
            shaderuniform.func_148084_b(l);
            this.field_148009_k.put(s1, shaderuniform);
         }
      }

   }

   private void func_147996_a(JsonElement p_147996_1_) throws JsonException {
      JsonObject jsonobject = JsonUtils.func_151210_l(p_147996_1_, "sampler");
      String s = JsonUtils.func_151200_h(jsonobject, "name");
      if (!JsonUtils.func_151205_a(jsonobject, "file")) {
         this.field_147997_f.put(s, (Object)null);
         this.field_147998_g.add(s);
      } else {
         this.field_147998_g.add(s);
      }
   }

   public void func_147992_a(String p_147992_1_, Object p_147992_2_) {
      if (this.field_147997_f.containsKey(p_147992_1_)) {
         this.field_147997_f.remove(p_147992_1_);
      }

      this.field_147997_f.put(p_147992_1_, p_147992_2_);
      this.func_147985_d();
   }

   private void func_147987_b(JsonElement p_147987_1_) throws JsonException {
      JsonObject jsonobject = JsonUtils.func_151210_l(p_147987_1_, "uniform");
      String s = JsonUtils.func_151200_h(jsonobject, "name");
      int i = ShaderUniform.func_148085_a(JsonUtils.func_151200_h(jsonobject, "type"));
      int j = JsonUtils.func_151203_m(jsonobject, "count");
      float[] afloat = new float[Math.max(j, 16)];
      JsonArray jsonarray = JsonUtils.func_151214_t(jsonobject, "values");
      if (jsonarray.size() != j && jsonarray.size() > 1) {
         throw new JsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
      } else {
         int k = 0;

         for(JsonElement jsonelement : jsonarray) {
            try {
               afloat[k] = JsonUtils.func_151220_d(jsonelement, "value");
            } catch (Exception exception) {
               JsonException jsonexception = JsonException.func_151379_a(exception);
               jsonexception.func_151380_a("values[" + k + "]");
               throw jsonexception;
            }

            ++k;
         }

         if (j > 1 && jsonarray.size() == 1) {
            while(k < j) {
               afloat[k] = afloat[0];
               ++k;
            }
         }

         int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
         ShaderUniform shaderuniform = new ShaderUniform(s, i + l, j, this);
         if (i <= 3) {
            shaderuniform.func_148083_a((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
         } else if (i <= 7) {
            shaderuniform.func_148092_b(afloat[0], afloat[1], afloat[2], afloat[3]);
         } else {
            shaderuniform.func_148097_a(afloat);
         }

         this.field_148011_i.add(shaderuniform);
      }
   }

   public ShaderLoader func_147989_e() {
      return this.field_148013_s;
   }

   public ShaderLoader func_147994_f() {
      return this.field_148012_t;
   }

   public int func_147986_h() {
      return this.field_148006_l;
   }
}
