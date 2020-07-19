package net.minecraft.client.renderer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper {
   private static final Logger field_188787_T = LogManager.getLogger();
   public static boolean field_153197_d;
   public static boolean field_181063_b;
   public static int field_153198_e;
   public static int field_153199_f;
   public static int field_153200_g;
   public static int field_153201_h;
   public static int field_153202_i;
   public static int field_153203_j;
   public static int field_153204_k;
   public static int field_153205_l;
   public static int field_153206_m;
   private static OpenGlHelper.FboMode field_153212_w;
   public static boolean field_148823_f;
   private static boolean field_153213_x;
   private static boolean field_153214_y;
   public static int field_153207_o;
   public static int field_153208_p;
   public static int field_153209_q;
   public static int field_153210_r;
   private static boolean field_153215_z;
   public static int field_77478_a;
   public static int field_77476_b;
   public static int field_176096_r;
   private static boolean field_176088_V;
   public static int field_176095_s;
   public static int field_176094_t;
   public static int field_176093_u;
   public static int field_176092_v;
   public static int field_176091_w;
   public static int field_176099_x;
   public static int field_176098_y;
   public static int field_176097_z;
   public static int field_176080_A;
   public static int field_176081_B;
   public static int field_176082_C;
   public static int field_176076_D;
   public static int field_176077_E;
   public static int field_176078_F;
   public static int field_176079_G;
   public static int field_176084_H;
   public static int field_176085_I;
   public static int field_176086_J;
   public static int field_176087_K;
   private static boolean field_148828_i;
   public static boolean field_153211_u;
   public static boolean field_148827_a;
   public static boolean field_148824_g;
   private static String field_153196_B = "";
   private static String field_183030_aa;
   public static boolean field_176083_O;
   public static boolean field_181062_Q;
   private static boolean field_176090_Y;
   public static int field_176089_P;
   public static int field_148826_e;

   public static void func_77474_a() {
      ContextCapabilities contextcapabilities = GLContext.getCapabilities();
      field_153215_z = contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13;
      field_176088_V = contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13;
      if (field_153215_z) {
         field_153196_B = field_153196_B + "Using ARB_multitexture.\n";
         field_77478_a = 33984;
         field_77476_b = 33985;
         field_176096_r = 33986;
      } else {
         field_153196_B = field_153196_B + "Using GL 1.3 multitexturing.\n";
         field_77478_a = 33984;
         field_77476_b = 33985;
         field_176096_r = 33986;
      }

      if (field_176088_V) {
         field_153196_B = field_153196_B + "Using ARB_texture_env_combine.\n";
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      } else {
         field_153196_B = field_153196_B + "Using GL 1.3 texture combiners.\n";
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      }

      field_153211_u = contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14;
      field_148828_i = contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate;
      field_148823_f = field_148828_i && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30);
      if (field_148823_f) {
         field_153196_B = field_153196_B + "Using framebuffer objects because ";
         if (contextcapabilities.OpenGL30) {
            field_153196_B = field_153196_B + "OpenGL 3.0 is supported and separate blending is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.BASE;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153203_j = 36054;
            field_153204_k = 36055;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (contextcapabilities.GL_ARB_framebuffer_object) {
            field_153196_B = field_153196_B + "ARB_framebuffer_object is supported and separate blending is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.ARB;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (contextcapabilities.GL_EXT_framebuffer_object) {
            field_153196_B = field_153196_B + "EXT_framebuffer_object is supported.\n";
            field_153212_w = OpenGlHelper.FboMode.EXT;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         }
      } else {
         field_153196_B = field_153196_B + "Not using framebuffer objects because ";
         field_153196_B = field_153196_B + "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
         field_153196_B = field_153196_B + "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
      }

      field_148827_a = contextcapabilities.OpenGL21;
      field_153213_x = field_148827_a || contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects;
      field_153196_B = field_153196_B + "Shaders are " + (field_153213_x ? "" : "not ") + "available because ";
      if (field_153213_x) {
         if (contextcapabilities.OpenGL21) {
            field_153196_B = field_153196_B + "OpenGL 2.1 is supported.\n";
            field_153214_y = false;
            field_153207_o = 35714;
            field_153208_p = 35713;
            field_153209_q = 35633;
            field_153210_r = 35632;
         } else {
            field_153196_B = field_153196_B + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
            field_153214_y = true;
            field_153207_o = 35714;
            field_153208_p = 35713;
            field_153209_q = 35633;
            field_153210_r = 35632;
         }
      } else {
         field_153196_B = field_153196_B + "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
         field_153196_B = field_153196_B + "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
         field_153196_B = field_153196_B + "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
      }

      field_148824_g = field_148823_f && field_153213_x;
      String s = GL11.glGetString(7936).toLowerCase(Locale.ROOT);
      field_153197_d = s.contains("nvidia");
      field_176090_Y = !contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object;
      field_176083_O = contextcapabilities.OpenGL15 || field_176090_Y;
      field_153196_B = field_153196_B + "VBOs are " + (field_176083_O ? "" : "not ") + "available because ";
      if (field_176083_O) {
         if (field_176090_Y) {
            field_153196_B = field_153196_B + "ARB_vertex_buffer_object is supported.\n";
            field_148826_e = 35044;
            field_176089_P = 34962;
         } else {
            field_153196_B = field_153196_B + "OpenGL 1.5 is supported.\n";
            field_148826_e = 35044;
            field_176089_P = 34962;
         }
      }

      field_181063_b = s.contains("ati");
      if (field_181063_b) {
         if (field_176083_O) {
            field_181062_Q = true;
         } else {
            GameSettings.Options.RENDER_DISTANCE.func_148263_a(16.0F);
         }
      }

      try {
         Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
         field_183030_aa = String.format("%dx %s", aprocessor.length, aprocessor[0]).replaceAll("\\s+", " ");
      } catch (Throwable var3) {
         ;
      }

   }

   public static boolean func_153193_b() {
      return field_148824_g;
   }

   public static String func_153172_c() {
      return field_153196_B;
   }

   public static int func_153175_a(int p_153175_0_, int p_153175_1_) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(p_153175_0_, p_153175_1_) : GL20.glGetProgrami(p_153175_0_, p_153175_1_);
   }

   public static void func_153178_b(int p_153178_0_, int p_153178_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glAttachObjectARB(p_153178_0_, p_153178_1_);
      } else {
         GL20.glAttachShader(p_153178_0_, p_153178_1_);
      }

   }

   public static void func_153180_a(int p_153180_0_) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(p_153180_0_);
      } else {
         GL20.glDeleteShader(p_153180_0_);
      }

   }

   public static int func_153195_b(int p_153195_0_) {
      return field_153214_y ? ARBShaderObjects.glCreateShaderObjectARB(p_153195_0_) : GL20.glCreateShader(p_153195_0_);
   }

   public static void func_153169_a(int p_153169_0_, ByteBuffer p_153169_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glShaderSourceARB(p_153169_0_, p_153169_1_);
      } else {
         GL20.glShaderSource(p_153169_0_, p_153169_1_);
      }

   }

   public static void func_153170_c(int p_153170_0_) {
      if (field_153214_y) {
         ARBShaderObjects.glCompileShaderARB(p_153170_0_);
      } else {
         GL20.glCompileShader(p_153170_0_);
      }

   }

   public static int func_153157_c(int p_153157_0_, int p_153157_1_) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(p_153157_0_, p_153157_1_) : GL20.glGetShaderi(p_153157_0_, p_153157_1_);
   }

   public static String func_153158_d(int p_153158_0_, int p_153158_1_) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(p_153158_0_, p_153158_1_) : GL20.glGetShaderInfoLog(p_153158_0_, p_153158_1_);
   }

   public static String func_153166_e(int p_153166_0_, int p_153166_1_) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(p_153166_0_, p_153166_1_) : GL20.glGetProgramInfoLog(p_153166_0_, p_153166_1_);
   }

   public static void func_153161_d(int p_153161_0_) {
      if (field_153214_y) {
         ARBShaderObjects.glUseProgramObjectARB(p_153161_0_);
      } else {
         GL20.glUseProgram(p_153161_0_);
      }

   }

   public static int func_153183_d() {
      return field_153214_y ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
   }

   public static void func_153187_e(int p_153187_0_) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(p_153187_0_);
      } else {
         GL20.glDeleteProgram(p_153187_0_);
      }

   }

   public static void func_153179_f(int p_153179_0_) {
      if (field_153214_y) {
         ARBShaderObjects.glLinkProgramARB(p_153179_0_);
      } else {
         GL20.glLinkProgram(p_153179_0_);
      }

   }

   public static int func_153194_a(int p_153194_0_, CharSequence p_153194_1_) {
      return field_153214_y ? ARBShaderObjects.glGetUniformLocationARB(p_153194_0_, p_153194_1_) : GL20.glGetUniformLocation(p_153194_0_, p_153194_1_);
   }

   public static void func_153181_a(int p_153181_0_, IntBuffer p_153181_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1ARB(p_153181_0_, p_153181_1_);
      } else {
         GL20.glUniform1(p_153181_0_, p_153181_1_);
      }

   }

   public static void func_153163_f(int p_153163_0_, int p_153163_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1iARB(p_153163_0_, p_153163_1_);
      } else {
         GL20.glUniform1i(p_153163_0_, p_153163_1_);
      }

   }

   public static void func_153168_a(int p_153168_0_, FloatBuffer p_153168_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1ARB(p_153168_0_, p_153168_1_);
      } else {
         GL20.glUniform1(p_153168_0_, p_153168_1_);
      }

   }

   public static void func_153182_b(int p_153182_0_, IntBuffer p_153182_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2ARB(p_153182_0_, p_153182_1_);
      } else {
         GL20.glUniform2(p_153182_0_, p_153182_1_);
      }

   }

   public static void func_153177_b(int p_153177_0_, FloatBuffer p_153177_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2ARB(p_153177_0_, p_153177_1_);
      } else {
         GL20.glUniform2(p_153177_0_, p_153177_1_);
      }

   }

   public static void func_153192_c(int p_153192_0_, IntBuffer p_153192_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3ARB(p_153192_0_, p_153192_1_);
      } else {
         GL20.glUniform3(p_153192_0_, p_153192_1_);
      }

   }

   public static void func_153191_c(int p_153191_0_, FloatBuffer p_153191_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3ARB(p_153191_0_, p_153191_1_);
      } else {
         GL20.glUniform3(p_153191_0_, p_153191_1_);
      }

   }

   public static void func_153162_d(int p_153162_0_, IntBuffer p_153162_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4ARB(p_153162_0_, p_153162_1_);
      } else {
         GL20.glUniform4(p_153162_0_, p_153162_1_);
      }

   }

   public static void func_153159_d(int p_153159_0_, FloatBuffer p_153159_1_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4ARB(p_153159_0_, p_153159_1_);
      } else {
         GL20.glUniform4(p_153159_0_, p_153159_1_);
      }

   }

   public static void func_153173_a(int p_153173_0_, boolean p_153173_1_, FloatBuffer p_153173_2_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix2ARB(p_153173_0_, p_153173_1_, p_153173_2_);
      } else {
         GL20.glUniformMatrix2(p_153173_0_, p_153173_1_, p_153173_2_);
      }

   }

   public static void func_153189_b(int p_153189_0_, boolean p_153189_1_, FloatBuffer p_153189_2_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix3ARB(p_153189_0_, p_153189_1_, p_153189_2_);
      } else {
         GL20.glUniformMatrix3(p_153189_0_, p_153189_1_, p_153189_2_);
      }

   }

   public static void func_153160_c(int p_153160_0_, boolean p_153160_1_, FloatBuffer p_153160_2_) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix4ARB(p_153160_0_, p_153160_1_, p_153160_2_);
      } else {
         GL20.glUniformMatrix4(p_153160_0_, p_153160_1_, p_153160_2_);
      }

   }

   public static int func_153164_b(int p_153164_0_, CharSequence p_153164_1_) {
      return field_153214_y ? ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) : GL20.glGetAttribLocation(p_153164_0_, p_153164_1_);
   }

   public static int func_176073_e() {
      return field_176090_Y ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
   }

   public static void func_176072_g(int p_176072_0_, int p_176072_1_) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBindBufferARB(p_176072_0_, p_176072_1_);
      } else {
         GL15.glBindBuffer(p_176072_0_, p_176072_1_);
      }

   }

   public static void func_176071_a(int p_176071_0_, ByteBuffer p_176071_1_, int p_176071_2_) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBufferDataARB(p_176071_0_, p_176071_1_, p_176071_2_);
      } else {
         GL15.glBufferData(p_176071_0_, p_176071_1_, p_176071_2_);
      }

   }

   public static void func_176074_g(int p_176074_0_) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glDeleteBuffersARB(p_176074_0_);
      } else {
         GL15.glDeleteBuffers(p_176074_0_);
      }

   }

   public static boolean func_176075_f() {
      return field_176083_O && Minecraft.func_71410_x().field_71474_y.field_178881_t;
   }

   public static void func_153171_g(int p_153171_0_, int p_153171_1_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glBindFramebuffer(p_153171_0_, p_153171_1_);
            break;
         case ARB:
            ARBFramebufferObject.glBindFramebuffer(p_153171_0_, p_153171_1_);
            break;
         case EXT:
            EXTFramebufferObject.glBindFramebufferEXT(p_153171_0_, p_153171_1_);
         }

      }
   }

   public static void func_153176_h(int p_153176_0_, int p_153176_1_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glBindRenderbuffer(p_153176_0_, p_153176_1_);
            break;
         case ARB:
            ARBFramebufferObject.glBindRenderbuffer(p_153176_0_, p_153176_1_);
            break;
         case EXT:
            EXTFramebufferObject.glBindRenderbufferEXT(p_153176_0_, p_153176_1_);
         }

      }
   }

   public static void func_153184_g(int p_153184_0_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glDeleteRenderbuffers(p_153184_0_);
            break;
         case ARB:
            ARBFramebufferObject.glDeleteRenderbuffers(p_153184_0_);
            break;
         case EXT:
            EXTFramebufferObject.glDeleteRenderbuffersEXT(p_153184_0_);
         }

      }
   }

   public static void func_153174_h(int p_153174_0_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glDeleteFramebuffers(p_153174_0_);
            break;
         case ARB:
            ARBFramebufferObject.glDeleteFramebuffers(p_153174_0_);
            break;
         case EXT:
            EXTFramebufferObject.glDeleteFramebuffersEXT(p_153174_0_);
         }

      }
   }

   public static int func_153165_e() {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
         case BASE:
            return GL30.glGenFramebuffers();
         case ARB:
            return ARBFramebufferObject.glGenFramebuffers();
         case EXT:
            return EXTFramebufferObject.glGenFramebuffersEXT();
         default:
            return -1;
         }
      }
   }

   public static int func_153185_f() {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
         case BASE:
            return GL30.glGenRenderbuffers();
         case ARB:
            return ARBFramebufferObject.glGenRenderbuffers();
         case EXT:
            return EXTFramebufferObject.glGenRenderbuffersEXT();
         default:
            return -1;
         }
      }
   }

   public static void func_153186_a(int p_153186_0_, int p_153186_1_, int p_153186_2_, int p_153186_3_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
            break;
         case ARB:
            ARBFramebufferObject.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
            break;
         case EXT:
            EXTFramebufferObject.glRenderbufferStorageEXT(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
         }

      }
   }

   public static void func_153190_b(int p_153190_0_, int p_153190_1_, int p_153190_2_, int p_153190_3_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
            break;
         case ARB:
            ARBFramebufferObject.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
            break;
         case EXT:
            EXTFramebufferObject.glFramebufferRenderbufferEXT(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
         }

      }
   }

   public static int func_153167_i(int p_153167_0_) {
      if (!field_148823_f) {
         return -1;
      } else {
         switch(field_153212_w) {
         case BASE:
            return GL30.glCheckFramebufferStatus(p_153167_0_);
         case ARB:
            return ARBFramebufferObject.glCheckFramebufferStatus(p_153167_0_);
         case EXT:
            return EXTFramebufferObject.glCheckFramebufferStatusEXT(p_153167_0_);
         default:
            return -1;
         }
      }
   }

   public static void func_153188_a(int p_153188_0_, int p_153188_1_, int p_153188_2_, int p_153188_3_, int p_153188_4_) {
      if (field_148823_f) {
         switch(field_153212_w) {
         case BASE:
            GL30.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
            break;
         case ARB:
            ARBFramebufferObject.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
            break;
         case EXT:
            EXTFramebufferObject.glFramebufferTexture2DEXT(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
         }

      }
   }

   public static void func_77473_a(int p_77473_0_) {
      if (field_153215_z) {
         ARBMultitexture.glActiveTextureARB(p_77473_0_);
      } else {
         GL13.glActiveTexture(p_77473_0_);
      }

   }

   public static void func_77472_b(int p_77472_0_) {
      if (field_153215_z) {
         ARBMultitexture.glClientActiveTextureARB(p_77472_0_);
      } else {
         GL13.glClientActiveTexture(p_77472_0_);
      }

   }

   public static void func_77475_a(int p_77475_0_, float p_77475_1_, float p_77475_2_) {
      if (field_153215_z) {
         ARBMultitexture.glMultiTexCoord2fARB(p_77475_0_, p_77475_1_, p_77475_2_);
      } else {
         GL13.glMultiTexCoord2f(p_77475_0_, p_77475_1_, p_77475_2_);
      }

   }

   public static void func_148821_a(int p_148821_0_, int p_148821_1_, int p_148821_2_, int p_148821_3_) {
      if (field_148828_i) {
         if (field_153211_u) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
         } else {
            GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
         }
      } else {
         GL11.glBlendFunc(p_148821_0_, p_148821_1_);
      }

   }

   public static boolean func_148822_b() {
      return field_148823_f && Minecraft.func_71410_x().field_71474_y.field_151448_g;
   }

   public static String func_183029_j() {
      return field_183030_aa == null ? "<unknown>" : field_183030_aa;
   }

   public static void func_188785_m(int p_188785_0_) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GL11.glLineWidth(4.0F);
      bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)p_188785_0_, 0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, (double)p_188785_0_, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, (double)p_188785_0_).func_181669_b(0, 0, 0, 255).func_181675_d();
      tessellator.func_78381_a();
      GL11.glLineWidth(2.0F);
      bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)p_188785_0_, 0.0D, 0.0D).func_181669_b(255, 0, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(0, 255, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, (double)p_188785_0_, 0.0D).func_181669_b(0, 255, 0, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(127, 127, 255, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, (double)p_188785_0_).func_181669_b(127, 127, 255, 255).func_181675_d();
      tessellator.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
   }

   public static void func_188786_a(File p_188786_0_) {
      String s = p_188786_0_.getAbsolutePath();
      if (Util.func_110647_a() == Util.EnumOS.OSX) {
         try {
            field_188787_T.info(s);
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
            return;
         } catch (IOException ioexception1) {
            field_188787_T.error("Couldn't open file", (Throwable)ioexception1);
         }
      } else if (Util.func_110647_a() == Util.EnumOS.WINDOWS) {
         String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);

         try {
            Runtime.getRuntime().exec(s1);
            return;
         } catch (IOException ioexception) {
            field_188787_T.error("Couldn't open file", (Throwable)ioexception);
         }
      }

      boolean flag = false;

      try {
         Class<?> oclass = Class.forName("java.awt.Desktop");
         Object object = oclass.getMethod("getDesktop").invoke((Object)null);
         oclass.getMethod("browse", URI.class).invoke(object, p_188786_0_.toURI());
      } catch (Throwable throwable) {
         field_188787_T.error("Couldn't open link", throwable);
         flag = true;
      }

      if (flag) {
         field_188787_T.info("Opening via system class!");
         Sys.openURL("file://" + s);
      }

   }

   static enum FboMode {
      BASE,
      ARB,
      EXT;
   }
}
