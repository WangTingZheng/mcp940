package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Quaternion;

public class GlStateManager {
   private static final FloatBuffer field_187450_a = BufferUtils.createFloatBuffer(16);
   private static final FloatBuffer field_187451_b = BufferUtils.createFloatBuffer(4);
   private static final GlStateManager.AlphaState field_179160_a = new GlStateManager.AlphaState();
   private static final GlStateManager.BooleanState field_179158_b = new GlStateManager.BooleanState(2896);
   private static final GlStateManager.BooleanState[] field_179159_c = new GlStateManager.BooleanState[8];
   private static final GlStateManager.ColorMaterialState field_179156_d;
   private static final GlStateManager.BlendState field_179157_e;
   private static final GlStateManager.DepthState field_179154_f;
   private static final GlStateManager.FogState field_179155_g;
   private static final GlStateManager.CullState field_179167_h;
   private static final GlStateManager.PolygonOffsetState field_179168_i;
   private static final GlStateManager.ColorLogicState field_179165_j;
   private static final GlStateManager.TexGenState field_179166_k;
   private static final GlStateManager.ClearState field_179163_l;
   private static final GlStateManager.StencilState field_179164_m;
   private static final GlStateManager.BooleanState field_179161_n;
   private static int field_179162_o;
   private static final GlStateManager.TextureState[] field_179174_p;
   private static int field_179173_q;
   private static final GlStateManager.BooleanState field_179172_r;
   private static final GlStateManager.ColorMask field_179171_s;
   private static final GlStateManager.Color field_179170_t;

   public static void func_179123_a() {
      GL11.glPushAttrib(8256);
   }

   public static void func_179099_b() {
      GL11.glPopAttrib();
   }

   public static void func_179118_c() {
      field_179160_a.field_179208_a.func_179198_a();
   }

   public static void func_179141_d() {
      field_179160_a.field_179208_a.func_179200_b();
   }

   public static void func_179092_a(int p_179092_0_, float p_179092_1_) {
      if (p_179092_0_ != field_179160_a.field_179206_b || p_179092_1_ != field_179160_a.field_179207_c) {
         field_179160_a.field_179206_b = p_179092_0_;
         field_179160_a.field_179207_c = p_179092_1_;
         GL11.glAlphaFunc(p_179092_0_, p_179092_1_);
      }

   }

   public static void func_179145_e() {
      field_179158_b.func_179200_b();
   }

   public static void func_179140_f() {
      field_179158_b.func_179198_a();
   }

   public static void func_179085_a(int p_179085_0_) {
      field_179159_c[p_179085_0_].func_179200_b();
   }

   public static void func_179122_b(int p_179122_0_) {
      field_179159_c[p_179122_0_].func_179198_a();
   }

   public static void func_179142_g() {
      field_179156_d.field_179191_a.func_179200_b();
   }

   public static void func_179119_h() {
      field_179156_d.field_179191_a.func_179198_a();
   }

   public static void func_179104_a(int p_179104_0_, int p_179104_1_) {
      if (p_179104_0_ != field_179156_d.field_179189_b || p_179104_1_ != field_179156_d.field_179190_c) {
         field_179156_d.field_179189_b = p_179104_0_;
         field_179156_d.field_179190_c = p_179104_1_;
         GL11.glColorMaterial(p_179104_0_, p_179104_1_);
      }

   }

   public static void func_187438_a(int p_187438_0_, int p_187438_1_, FloatBuffer p_187438_2_) {
      GL11.glLight(p_187438_0_, p_187438_1_, p_187438_2_);
   }

   public static void func_187424_a(int p_187424_0_, FloatBuffer p_187424_1_) {
      GL11.glLightModel(p_187424_0_, p_187424_1_);
   }

   public static void func_187432_a(float p_187432_0_, float p_187432_1_, float p_187432_2_) {
      GL11.glNormal3f(p_187432_0_, p_187432_1_, p_187432_2_);
   }

   public static void func_179097_i() {
      field_179154_f.field_179052_a.func_179198_a();
   }

   public static void func_179126_j() {
      field_179154_f.field_179052_a.func_179200_b();
   }

   public static void func_179143_c(int p_179143_0_) {
      if (p_179143_0_ != field_179154_f.field_179051_c) {
         field_179154_f.field_179051_c = p_179143_0_;
         GL11.glDepthFunc(p_179143_0_);
      }

   }

   public static void func_179132_a(boolean p_179132_0_) {
      if (p_179132_0_ != field_179154_f.field_179050_b) {
         field_179154_f.field_179050_b = p_179132_0_;
         GL11.glDepthMask(p_179132_0_);
      }

   }

   public static void func_179084_k() {
      field_179157_e.field_179213_a.func_179198_a();
   }

   public static void func_179147_l() {
      field_179157_e.field_179213_a.func_179200_b();
   }

   public static void func_187401_a(GlStateManager.SourceFactor p_187401_0_, GlStateManager.DestFactor p_187401_1_) {
      func_179112_b(p_187401_0_.field_187395_p, p_187401_1_.field_187345_o);
   }

   public static void func_179112_b(int p_179112_0_, int p_179112_1_) {
      if (p_179112_0_ != field_179157_e.field_179211_b || p_179112_1_ != field_179157_e.field_179212_c) {
         field_179157_e.field_179211_b = p_179112_0_;
         field_179157_e.field_179212_c = p_179112_1_;
         GL11.glBlendFunc(p_179112_0_, p_179112_1_);
      }

   }

   public static void func_187428_a(GlStateManager.SourceFactor p_187428_0_, GlStateManager.DestFactor p_187428_1_, GlStateManager.SourceFactor p_187428_2_, GlStateManager.DestFactor p_187428_3_) {
      func_179120_a(p_187428_0_.field_187395_p, p_187428_1_.field_187345_o, p_187428_2_.field_187395_p, p_187428_3_.field_187345_o);
   }

   public static void func_179120_a(int p_179120_0_, int p_179120_1_, int p_179120_2_, int p_179120_3_) {
      if (p_179120_0_ != field_179157_e.field_179211_b || p_179120_1_ != field_179157_e.field_179212_c || p_179120_2_ != field_179157_e.field_179209_d || p_179120_3_ != field_179157_e.field_179210_e) {
         field_179157_e.field_179211_b = p_179120_0_;
         field_179157_e.field_179212_c = p_179120_1_;
         field_179157_e.field_179209_d = p_179120_2_;
         field_179157_e.field_179210_e = p_179120_3_;
         OpenGlHelper.func_148821_a(p_179120_0_, p_179120_1_, p_179120_2_, p_179120_3_);
      }

   }

   public static void func_187398_d(int p_187398_0_) {
      GL14.glBlendEquation(p_187398_0_);
   }

   public static void func_187431_e(int p_187431_0_) {
      field_187451_b.put(0, (float)(p_187431_0_ >> 16 & 255) / 255.0F);
      field_187451_b.put(1, (float)(p_187431_0_ >> 8 & 255) / 255.0F);
      field_187451_b.put(2, (float)(p_187431_0_ >> 0 & 255) / 255.0F);
      field_187451_b.put(3, (float)(p_187431_0_ >> 24 & 255) / 255.0F);
      func_187448_b(8960, 8705, field_187451_b);
      func_187399_a(8960, 8704, 34160);
      func_187399_a(8960, 34161, 7681);
      func_187399_a(8960, 34176, 34166);
      func_187399_a(8960, 34192, 768);
      func_187399_a(8960, 34162, 7681);
      func_187399_a(8960, 34184, 5890);
      func_187399_a(8960, 34200, 770);
   }

   public static void func_187417_n() {
      func_187399_a(8960, 8704, 8448);
      func_187399_a(8960, 34161, 8448);
      func_187399_a(8960, 34162, 8448);
      func_187399_a(8960, 34176, 5890);
      func_187399_a(8960, 34184, 5890);
      func_187399_a(8960, 34192, 768);
      func_187399_a(8960, 34200, 770);
   }

   public static void func_179127_m() {
      field_179155_g.field_179049_a.func_179200_b();
   }

   public static void func_179106_n() {
      field_179155_g.field_179049_a.func_179198_a();
   }

   public static void func_187430_a(GlStateManager.FogMode p_187430_0_) {
      func_179093_d(p_187430_0_.field_187351_d);
   }

   private static void func_179093_d(int p_179093_0_) {
      if (p_179093_0_ != field_179155_g.field_179047_b) {
         field_179155_g.field_179047_b = p_179093_0_;
         GL11.glFogi(2917, p_179093_0_);
      }

   }

   public static void func_179095_a(float p_179095_0_) {
      if (p_179095_0_ != field_179155_g.field_179048_c) {
         field_179155_g.field_179048_c = p_179095_0_;
         GL11.glFogf(2914, p_179095_0_);
      }

   }

   public static void func_179102_b(float p_179102_0_) {
      if (p_179102_0_ != field_179155_g.field_179045_d) {
         field_179155_g.field_179045_d = p_179102_0_;
         GL11.glFogf(2915, p_179102_0_);
      }

   }

   public static void func_179153_c(float p_179153_0_) {
      if (p_179153_0_ != field_179155_g.field_179046_e) {
         field_179155_g.field_179046_e = p_179153_0_;
         GL11.glFogf(2916, p_179153_0_);
      }

   }

   public static void func_187402_b(int p_187402_0_, FloatBuffer p_187402_1_) {
      GL11.glFog(p_187402_0_, p_187402_1_);
   }

   public static void func_187412_c(int p_187412_0_, int p_187412_1_) {
      GL11.glFogi(p_187412_0_, p_187412_1_);
   }

   public static void func_179089_o() {
      field_179167_h.field_179054_a.func_179200_b();
   }

   public static void func_179129_p() {
      field_179167_h.field_179054_a.func_179198_a();
   }

   public static void func_187407_a(GlStateManager.CullFace p_187407_0_) {
      func_179107_e(p_187407_0_.field_187328_d);
   }

   private static void func_179107_e(int p_179107_0_) {
      if (p_179107_0_ != field_179167_h.field_179053_b) {
         field_179167_h.field_179053_b = p_179107_0_;
         GL11.glCullFace(p_179107_0_);
      }

   }

   public static void func_187409_d(int p_187409_0_, int p_187409_1_) {
      GL11.glPolygonMode(p_187409_0_, p_187409_1_);
   }

   public static void func_179088_q() {
      field_179168_i.field_179044_a.func_179200_b();
   }

   public static void func_179113_r() {
      field_179168_i.field_179044_a.func_179198_a();
   }

   public static void func_179136_a(float p_179136_0_, float p_179136_1_) {
      if (p_179136_0_ != field_179168_i.field_179043_c || p_179136_1_ != field_179168_i.field_179041_d) {
         field_179168_i.field_179043_c = p_179136_0_;
         field_179168_i.field_179041_d = p_179136_1_;
         GL11.glPolygonOffset(p_179136_0_, p_179136_1_);
      }

   }

   public static void func_179115_u() {
      field_179165_j.field_179197_a.func_179200_b();
   }

   public static void func_179134_v() {
      field_179165_j.field_179197_a.func_179198_a();
   }

   public static void func_187422_a(GlStateManager.LogicOp p_187422_0_) {
      func_179116_f(p_187422_0_.field_187370_q);
   }

   public static void func_179116_f(int p_179116_0_) {
      if (p_179116_0_ != field_179165_j.field_179196_b) {
         field_179165_j.field_179196_b = p_179116_0_;
         GL11.glLogicOp(p_179116_0_);
      }

   }

   public static void func_179087_a(GlStateManager.TexGen p_179087_0_) {
      func_179125_c(p_179087_0_).field_179067_a.func_179200_b();
   }

   public static void func_179100_b(GlStateManager.TexGen p_179100_0_) {
      func_179125_c(p_179100_0_).field_179067_a.func_179198_a();
   }

   public static void func_179149_a(GlStateManager.TexGen p_179149_0_, int p_179149_1_) {
      GlStateManager.TexGenCoord glstatemanager$texgencoord = func_179125_c(p_179149_0_);
      if (p_179149_1_ != glstatemanager$texgencoord.field_179066_c) {
         glstatemanager$texgencoord.field_179066_c = p_179149_1_;
         GL11.glTexGeni(glstatemanager$texgencoord.field_179065_b, 9472, p_179149_1_);
      }

   }

   public static void func_179105_a(GlStateManager.TexGen p_179105_0_, int p_179105_1_, FloatBuffer p_179105_2_) {
      GL11.glTexGen(func_179125_c(p_179105_0_).field_179065_b, p_179105_1_, p_179105_2_);
   }

   private static GlStateManager.TexGenCoord func_179125_c(GlStateManager.TexGen p_179125_0_) {
      switch(p_179125_0_) {
      case S:
         return field_179166_k.field_179064_a;
      case T:
         return field_179166_k.field_179062_b;
      case R:
         return field_179166_k.field_179063_c;
      case Q:
         return field_179166_k.field_179061_d;
      default:
         return field_179166_k.field_179064_a;
      }
   }

   public static void func_179138_g(int p_179138_0_) {
      if (field_179162_o != p_179138_0_ - OpenGlHelper.field_77478_a) {
         field_179162_o = p_179138_0_ - OpenGlHelper.field_77478_a;
         OpenGlHelper.func_77473_a(p_179138_0_);
      }

   }

   public static void func_179098_w() {
      field_179174_p[field_179162_o].field_179060_a.func_179200_b();
   }

   public static void func_179090_x() {
      field_179174_p[field_179162_o].field_179060_a.func_179198_a();
   }

   public static void func_187448_b(int p_187448_0_, int p_187448_1_, FloatBuffer p_187448_2_) {
      GL11.glTexEnv(p_187448_0_, p_187448_1_, p_187448_2_);
   }

   public static void func_187399_a(int p_187399_0_, int p_187399_1_, int p_187399_2_) {
      GL11.glTexEnvi(p_187399_0_, p_187399_1_, p_187399_2_);
   }

   public static void func_187436_a(int p_187436_0_, int p_187436_1_, float p_187436_2_) {
      GL11.glTexEnvf(p_187436_0_, p_187436_1_, p_187436_2_);
   }

   public static void func_187403_b(int p_187403_0_, int p_187403_1_, float p_187403_2_) {
      GL11.glTexParameterf(p_187403_0_, p_187403_1_, p_187403_2_);
   }

   public static void func_187421_b(int p_187421_0_, int p_187421_1_, int p_187421_2_) {
      GL11.glTexParameteri(p_187421_0_, p_187421_1_, p_187421_2_);
   }

   public static int func_187411_c(int p_187411_0_, int p_187411_1_, int p_187411_2_) {
      return GL11.glGetTexLevelParameteri(p_187411_0_, p_187411_1_, p_187411_2_);
   }

   public static int func_179146_y() {
      return GL11.glGenTextures();
   }

   public static void func_179150_h(int p_179150_0_) {
      GL11.glDeleteTextures(p_179150_0_);

      for(GlStateManager.TextureState glstatemanager$texturestate : field_179174_p) {
         if (glstatemanager$texturestate.field_179059_b == p_179150_0_) {
            glstatemanager$texturestate.field_179059_b = -1;
         }
      }

   }

   public static void func_179144_i(int p_179144_0_) {
      if (p_179144_0_ != field_179174_p[field_179162_o].field_179059_b) {
         field_179174_p[field_179162_o].field_179059_b = p_179144_0_;
         GL11.glBindTexture(3553, p_179144_0_);
      }

   }

   public static void func_187419_a(int p_187419_0_, int p_187419_1_, int p_187419_2_, int p_187419_3_, int p_187419_4_, int p_187419_5_, int p_187419_6_, int p_187419_7_, @Nullable IntBuffer p_187419_8_) {
      GL11.glTexImage2D(p_187419_0_, p_187419_1_, p_187419_2_, p_187419_3_, p_187419_4_, p_187419_5_, p_187419_6_, p_187419_7_, p_187419_8_);
   }

   public static void func_187414_b(int p_187414_0_, int p_187414_1_, int p_187414_2_, int p_187414_3_, int p_187414_4_, int p_187414_5_, int p_187414_6_, int p_187414_7_, IntBuffer p_187414_8_) {
      GL11.glTexSubImage2D(p_187414_0_, p_187414_1_, p_187414_2_, p_187414_3_, p_187414_4_, p_187414_5_, p_187414_6_, p_187414_7_, p_187414_8_);
   }

   public static void func_187443_a(int p_187443_0_, int p_187443_1_, int p_187443_2_, int p_187443_3_, int p_187443_4_, int p_187443_5_, int p_187443_6_, int p_187443_7_) {
      GL11.glCopyTexSubImage2D(p_187443_0_, p_187443_1_, p_187443_2_, p_187443_3_, p_187443_4_, p_187443_5_, p_187443_6_, p_187443_7_);
   }

   public static void func_187433_a(int p_187433_0_, int p_187433_1_, int p_187433_2_, int p_187433_3_, IntBuffer p_187433_4_) {
      GL11.glGetTexImage(p_187433_0_, p_187433_1_, p_187433_2_, p_187433_3_, p_187433_4_);
   }

   public static void func_179108_z() {
      field_179161_n.func_179200_b();
   }

   public static void func_179133_A() {
      field_179161_n.func_179198_a();
   }

   public static void func_179103_j(int p_179103_0_) {
      if (p_179103_0_ != field_179173_q) {
         field_179173_q = p_179103_0_;
         GL11.glShadeModel(p_179103_0_);
      }

   }

   public static void func_179091_B() {
      field_179172_r.func_179200_b();
   }

   public static void func_179101_C() {
      field_179172_r.func_179198_a();
   }

   public static void func_179083_b(int p_179083_0_, int p_179083_1_, int p_179083_2_, int p_179083_3_) {
      GL11.glViewport(p_179083_0_, p_179083_1_, p_179083_2_, p_179083_3_);
   }

   public static void func_179135_a(boolean p_179135_0_, boolean p_179135_1_, boolean p_179135_2_, boolean p_179135_3_) {
      if (p_179135_0_ != field_179171_s.field_179188_a || p_179135_1_ != field_179171_s.field_179186_b || p_179135_2_ != field_179171_s.field_179187_c || p_179135_3_ != field_179171_s.field_179185_d) {
         field_179171_s.field_179188_a = p_179135_0_;
         field_179171_s.field_179186_b = p_179135_1_;
         field_179171_s.field_179187_c = p_179135_2_;
         field_179171_s.field_179185_d = p_179135_3_;
         GL11.glColorMask(p_179135_0_, p_179135_1_, p_179135_2_, p_179135_3_);
      }

   }

   public static void func_179151_a(double p_179151_0_) {
      if (p_179151_0_ != field_179163_l.field_179205_a) {
         field_179163_l.field_179205_a = p_179151_0_;
         GL11.glClearDepth(p_179151_0_);
      }

   }

   public static void func_179082_a(float p_179082_0_, float p_179082_1_, float p_179082_2_, float p_179082_3_) {
      if (p_179082_0_ != field_179163_l.field_179203_b.field_179195_a || p_179082_1_ != field_179163_l.field_179203_b.field_179193_b || p_179082_2_ != field_179163_l.field_179203_b.field_179194_c || p_179082_3_ != field_179163_l.field_179203_b.field_179192_d) {
         field_179163_l.field_179203_b.field_179195_a = p_179082_0_;
         field_179163_l.field_179203_b.field_179193_b = p_179082_1_;
         field_179163_l.field_179203_b.field_179194_c = p_179082_2_;
         field_179163_l.field_179203_b.field_179192_d = p_179082_3_;
         GL11.glClearColor(p_179082_0_, p_179082_1_, p_179082_2_, p_179082_3_);
      }

   }

   public static void func_179086_m(int p_179086_0_) {
      GL11.glClear(p_179086_0_);
   }

   public static void func_179128_n(int p_179128_0_) {
      GL11.glMatrixMode(p_179128_0_);
   }

   public static void func_179096_D() {
      GL11.glLoadIdentity();
   }

   public static void func_179094_E() {
      GL11.glPushMatrix();
   }

   public static void func_179121_F() {
      GL11.glPopMatrix();
   }

   public static void func_179111_a(int p_179111_0_, FloatBuffer p_179111_1_) {
      GL11.glGetFloat(p_179111_0_, p_179111_1_);
   }

   public static void func_179130_a(double p_179130_0_, double p_179130_2_, double p_179130_4_, double p_179130_6_, double p_179130_8_, double p_179130_10_) {
      GL11.glOrtho(p_179130_0_, p_179130_2_, p_179130_4_, p_179130_6_, p_179130_8_, p_179130_10_);
   }

   public static void func_179114_b(float p_179114_0_, float p_179114_1_, float p_179114_2_, float p_179114_3_) {
      GL11.glRotatef(p_179114_0_, p_179114_1_, p_179114_2_, p_179114_3_);
   }

   public static void func_179152_a(float p_179152_0_, float p_179152_1_, float p_179152_2_) {
      GL11.glScalef(p_179152_0_, p_179152_1_, p_179152_2_);
   }

   public static void func_179139_a(double p_179139_0_, double p_179139_2_, double p_179139_4_) {
      GL11.glScaled(p_179139_0_, p_179139_2_, p_179139_4_);
   }

   public static void func_179109_b(float p_179109_0_, float p_179109_1_, float p_179109_2_) {
      GL11.glTranslatef(p_179109_0_, p_179109_1_, p_179109_2_);
   }

   public static void func_179137_b(double p_179137_0_, double p_179137_2_, double p_179137_4_) {
      GL11.glTranslated(p_179137_0_, p_179137_2_, p_179137_4_);
   }

   public static void func_179110_a(FloatBuffer p_179110_0_) {
      GL11.glMultMatrix(p_179110_0_);
   }

   public static void func_187444_a(Quaternion p_187444_0_) {
      func_179110_a(func_187418_a(field_187450_a, p_187444_0_));
   }

   public static FloatBuffer func_187418_a(FloatBuffer p_187418_0_, Quaternion p_187418_1_) {
      p_187418_0_.clear();
      float f = p_187418_1_.x * p_187418_1_.x;
      float f1 = p_187418_1_.x * p_187418_1_.y;
      float f2 = p_187418_1_.x * p_187418_1_.z;
      float f3 = p_187418_1_.x * p_187418_1_.w;
      float f4 = p_187418_1_.y * p_187418_1_.y;
      float f5 = p_187418_1_.y * p_187418_1_.z;
      float f6 = p_187418_1_.y * p_187418_1_.w;
      float f7 = p_187418_1_.z * p_187418_1_.z;
      float f8 = p_187418_1_.z * p_187418_1_.w;
      p_187418_0_.put(1.0F - 2.0F * (f4 + f7));
      p_187418_0_.put(2.0F * (f1 + f8));
      p_187418_0_.put(2.0F * (f2 - f6));
      p_187418_0_.put(0.0F);
      p_187418_0_.put(2.0F * (f1 - f8));
      p_187418_0_.put(1.0F - 2.0F * (f + f7));
      p_187418_0_.put(2.0F * (f5 + f3));
      p_187418_0_.put(0.0F);
      p_187418_0_.put(2.0F * (f2 + f6));
      p_187418_0_.put(2.0F * (f5 - f3));
      p_187418_0_.put(1.0F - 2.0F * (f + f4));
      p_187418_0_.put(0.0F);
      p_187418_0_.put(0.0F);
      p_187418_0_.put(0.0F);
      p_187418_0_.put(0.0F);
      p_187418_0_.put(1.0F);
      p_187418_0_.rewind();
      return p_187418_0_;
   }

   public static void func_179131_c(float p_179131_0_, float p_179131_1_, float p_179131_2_, float p_179131_3_) {
      if (p_179131_0_ != field_179170_t.field_179195_a || p_179131_1_ != field_179170_t.field_179193_b || p_179131_2_ != field_179170_t.field_179194_c || p_179131_3_ != field_179170_t.field_179192_d) {
         field_179170_t.field_179195_a = p_179131_0_;
         field_179170_t.field_179193_b = p_179131_1_;
         field_179170_t.field_179194_c = p_179131_2_;
         field_179170_t.field_179192_d = p_179131_3_;
         GL11.glColor4f(p_179131_0_, p_179131_1_, p_179131_2_, p_179131_3_);
      }

   }

   public static void func_179124_c(float p_179124_0_, float p_179124_1_, float p_179124_2_) {
      func_179131_c(p_179124_0_, p_179124_1_, p_179124_2_, 1.0F);
   }

   public static void func_187426_b(float p_187426_0_, float p_187426_1_) {
      GL11.glTexCoord2f(p_187426_0_, p_187426_1_);
   }

   public static void func_187435_e(float p_187435_0_, float p_187435_1_, float p_187435_2_) {
      GL11.glVertex3f(p_187435_0_, p_187435_1_, p_187435_2_);
   }

   public static void func_179117_G() {
      field_179170_t.field_179195_a = -1.0F;
      field_179170_t.field_179193_b = -1.0F;
      field_179170_t.field_179194_c = -1.0F;
      field_179170_t.field_179192_d = -1.0F;
   }

   public static void func_187446_a(int p_187446_0_, int p_187446_1_, ByteBuffer p_187446_2_) {
      GL11.glNormalPointer(p_187446_0_, p_187446_1_, p_187446_2_);
   }

   public static void func_187405_c(int p_187405_0_, int p_187405_1_, int p_187405_2_, int p_187405_3_) {
      GL11.glTexCoordPointer(p_187405_0_, p_187405_1_, p_187405_2_, (long)p_187405_3_);
   }

   public static void func_187404_a(int p_187404_0_, int p_187404_1_, int p_187404_2_, ByteBuffer p_187404_3_) {
      GL11.glTexCoordPointer(p_187404_0_, p_187404_1_, p_187404_2_, p_187404_3_);
   }

   public static void func_187420_d(int p_187420_0_, int p_187420_1_, int p_187420_2_, int p_187420_3_) {
      GL11.glVertexPointer(p_187420_0_, p_187420_1_, p_187420_2_, (long)p_187420_3_);
   }

   public static void func_187427_b(int p_187427_0_, int p_187427_1_, int p_187427_2_, ByteBuffer p_187427_3_) {
      GL11.glVertexPointer(p_187427_0_, p_187427_1_, p_187427_2_, p_187427_3_);
   }

   public static void func_187406_e(int p_187406_0_, int p_187406_1_, int p_187406_2_, int p_187406_3_) {
      GL11.glColorPointer(p_187406_0_, p_187406_1_, p_187406_2_, (long)p_187406_3_);
   }

   public static void func_187400_c(int p_187400_0_, int p_187400_1_, int p_187400_2_, ByteBuffer p_187400_3_) {
      GL11.glColorPointer(p_187400_0_, p_187400_1_, p_187400_2_, p_187400_3_);
   }

   public static void func_187429_p(int p_187429_0_) {
      GL11.glDisableClientState(p_187429_0_);
   }

   public static void func_187410_q(int p_187410_0_) {
      GL11.glEnableClientState(p_187410_0_);
   }

   public static void func_187447_r(int p_187447_0_) {
      GL11.glBegin(p_187447_0_);
   }

   public static void func_187437_J() {
      GL11.glEnd();
   }

   public static void func_187439_f(int p_187439_0_, int p_187439_1_, int p_187439_2_) {
      GL11.glDrawArrays(p_187439_0_, p_187439_1_, p_187439_2_);
   }

   public static void func_187441_d(float p_187441_0_) {
      GL11.glLineWidth(p_187441_0_);
   }

   public static void func_179148_o(int p_179148_0_) {
      GL11.glCallList(p_179148_0_);
   }

   public static void func_187449_e(int p_187449_0_, int p_187449_1_) {
      GL11.glDeleteLists(p_187449_0_, p_187449_1_);
   }

   public static void func_187423_f(int p_187423_0_, int p_187423_1_) {
      GL11.glNewList(p_187423_0_, p_187423_1_);
   }

   public static void func_187415_K() {
      GL11.glEndList();
   }

   public static int func_187442_t(int p_187442_0_) {
      return GL11.glGenLists(p_187442_0_);
   }

   public static void func_187425_g(int p_187425_0_, int p_187425_1_) {
      GL11.glPixelStorei(p_187425_0_, p_187425_1_);
   }

   public static void func_187413_a(int p_187413_0_, int p_187413_1_, int p_187413_2_, int p_187413_3_, int p_187413_4_, int p_187413_5_, IntBuffer p_187413_6_) {
      GL11.glReadPixels(p_187413_0_, p_187413_1_, p_187413_2_, p_187413_3_, p_187413_4_, p_187413_5_, p_187413_6_);
   }

   public static int func_187434_L() {
      return GL11.glGetError();
   }

   public static String func_187416_u(int p_187416_0_) {
      return GL11.glGetString(p_187416_0_);
   }

   public static void func_187445_a(int p_187445_0_, IntBuffer p_187445_1_) {
      GL11.glGetInteger(p_187445_0_, p_187445_1_);
   }

   public static int func_187397_v(int p_187397_0_) {
      return GL11.glGetInteger(p_187397_0_);
   }

   public static void func_187408_a(GlStateManager.Profile p_187408_0_) {
      p_187408_0_.func_187373_a();
   }

   public static void func_187440_b(GlStateManager.Profile p_187440_0_) {
      p_187440_0_.func_187374_b();
   }

   static {
      for(int i = 0; i < 8; ++i) {
         field_179159_c[i] = new GlStateManager.BooleanState(16384 + i);
      }

      field_179156_d = new GlStateManager.ColorMaterialState();
      field_179157_e = new GlStateManager.BlendState();
      field_179154_f = new GlStateManager.DepthState();
      field_179155_g = new GlStateManager.FogState();
      field_179167_h = new GlStateManager.CullState();
      field_179168_i = new GlStateManager.PolygonOffsetState();
      field_179165_j = new GlStateManager.ColorLogicState();
      field_179166_k = new GlStateManager.TexGenState();
      field_179163_l = new GlStateManager.ClearState();
      field_179164_m = new GlStateManager.StencilState();
      field_179161_n = new GlStateManager.BooleanState(2977);
      field_179174_p = new GlStateManager.TextureState[8];

      for(int j = 0; j < 8; ++j) {
         field_179174_p[j] = new GlStateManager.TextureState();
      }

      field_179173_q = 7425;
      field_179172_r = new GlStateManager.BooleanState(32826);
      field_179171_s = new GlStateManager.ColorMask();
      field_179170_t = new GlStateManager.Color();
   }

   static class AlphaState {
      public GlStateManager.BooleanState field_179208_a;
      public int field_179206_b;
      public float field_179207_c;

      private AlphaState() {
         this.field_179208_a = new GlStateManager.BooleanState(3008);
         this.field_179206_b = 519;
         this.field_179207_c = -1.0F;
      }
   }

   static class BlendState {
      public GlStateManager.BooleanState field_179213_a;
      public int field_179211_b;
      public int field_179212_c;
      public int field_179209_d;
      public int field_179210_e;

      private BlendState() {
         this.field_179213_a = new GlStateManager.BooleanState(3042);
         this.field_179211_b = 1;
         this.field_179212_c = 0;
         this.field_179209_d = 1;
         this.field_179210_e = 0;
      }
   }

   static class BooleanState {
      private final int field_179202_a;
      private boolean field_179201_b;

      public BooleanState(int p_i46267_1_) {
         this.field_179202_a = p_i46267_1_;
      }

      public void func_179198_a() {
         this.func_179199_a(false);
      }

      public void func_179200_b() {
         this.func_179199_a(true);
      }

      public void func_179199_a(boolean p_179199_1_) {
         if (p_179199_1_ != this.field_179201_b) {
            this.field_179201_b = p_179199_1_;
            if (p_179199_1_) {
               GL11.glEnable(this.field_179202_a);
            } else {
               GL11.glDisable(this.field_179202_a);
            }
         }

      }
   }

   static class ClearState {
      public double field_179205_a;
      public GlStateManager.Color field_179203_b;

      private ClearState() {
         this.field_179205_a = 1.0D;
         this.field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }

   static class Color {
      public float field_179195_a;
      public float field_179193_b;
      public float field_179194_c;
      public float field_179192_d;

      public Color() {
         this(1.0F, 1.0F, 1.0F, 1.0F);
      }

      public Color(float p_i46265_1_, float p_i46265_2_, float p_i46265_3_, float p_i46265_4_) {
         this.field_179195_a = 1.0F;
         this.field_179193_b = 1.0F;
         this.field_179194_c = 1.0F;
         this.field_179192_d = 1.0F;
         this.field_179195_a = p_i46265_1_;
         this.field_179193_b = p_i46265_2_;
         this.field_179194_c = p_i46265_3_;
         this.field_179192_d = p_i46265_4_;
      }
   }

   static class ColorLogicState {
      public GlStateManager.BooleanState field_179197_a;
      public int field_179196_b;

      private ColorLogicState() {
         this.field_179197_a = new GlStateManager.BooleanState(3058);
         this.field_179196_b = 5379;
      }
   }

   static class ColorMask {
      public boolean field_179188_a;
      public boolean field_179186_b;
      public boolean field_179187_c;
      public boolean field_179185_d;

      private ColorMask() {
         this.field_179188_a = true;
         this.field_179186_b = true;
         this.field_179187_c = true;
         this.field_179185_d = true;
      }
   }

   static class ColorMaterialState {
      public GlStateManager.BooleanState field_179191_a;
      public int field_179189_b;
      public int field_179190_c;

      private ColorMaterialState() {
         this.field_179191_a = new GlStateManager.BooleanState(2903);
         this.field_179189_b = 1032;
         this.field_179190_c = 5634;
      }
   }

   public static enum CullFace {
      FRONT(1028),
      BACK(1029),
      FRONT_AND_BACK(1032);

      public final int field_187328_d;

      private CullFace(int p_i46520_3_) {
         this.field_187328_d = p_i46520_3_;
      }
   }

   static class CullState {
      public GlStateManager.BooleanState field_179054_a;
      public int field_179053_b;

      private CullState() {
         this.field_179054_a = new GlStateManager.BooleanState(2884);
         this.field_179053_b = 1029;
      }
   }

   static class DepthState {
      public GlStateManager.BooleanState field_179052_a;
      public boolean field_179050_b;
      public int field_179051_c;

      private DepthState() {
         this.field_179052_a = new GlStateManager.BooleanState(2929);
         this.field_179050_b = true;
         this.field_179051_c = 513;
      }
   }

   public static enum DestFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_187345_o;

      private DestFactor(int p_i46519_3_) {
         this.field_187345_o = p_i46519_3_;
      }
   }

   public static enum FogMode {
      LINEAR(9729),
      EXP(2048),
      EXP2(2049);

      public final int field_187351_d;

      private FogMode(int p_i46518_3_) {
         this.field_187351_d = p_i46518_3_;
      }
   }

   static class FogState {
      public GlStateManager.BooleanState field_179049_a;
      public int field_179047_b;
      public float field_179048_c;
      public float field_179045_d;
      public float field_179046_e;

      private FogState() {
         this.field_179049_a = new GlStateManager.BooleanState(2912);
         this.field_179047_b = 2048;
         this.field_179048_c = 1.0F;
         this.field_179046_e = 1.0F;
      }
   }

   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public final int field_187370_q;

      private LogicOp(int p_i46517_3_) {
         this.field_187370_q = p_i46517_3_;
      }
   }

   static class PolygonOffsetState {
      public GlStateManager.BooleanState field_179044_a;
      public GlStateManager.BooleanState field_179042_b;
      public float field_179043_c;
      public float field_179041_d;

      private PolygonOffsetState() {
         this.field_179044_a = new GlStateManager.BooleanState(32823);
         this.field_179042_b = new GlStateManager.BooleanState(10754);
      }
   }

   public static enum Profile {
      DEFAULT {
         public void func_187373_a() {
            GlStateManager.func_179118_c();
            GlStateManager.func_179092_a(519, 0.0F);
            GlStateManager.func_179140_f();
            GL11.glLightModel(2899, RenderHelper.func_74521_a(0.2F, 0.2F, 0.2F, 1.0F));

            for(int i = 0; i < 8; ++i) {
               GlStateManager.func_179122_b(i);
               GL11.glLight(16384 + i, 4608, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
               GL11.glLight(16384 + i, 4611, RenderHelper.func_74521_a(0.0F, 0.0F, 1.0F, 0.0F));
               if (i == 0) {
                  GL11.glLight(16384 + i, 4609, RenderHelper.func_74521_a(1.0F, 1.0F, 1.0F, 1.0F));
                  GL11.glLight(16384 + i, 4610, RenderHelper.func_74521_a(1.0F, 1.0F, 1.0F, 1.0F));
               } else {
                  GL11.glLight(16384 + i, 4609, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
                  GL11.glLight(16384 + i, 4610, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
               }
            }

            GlStateManager.func_179119_h();
            GlStateManager.func_179104_a(1032, 5634);
            GlStateManager.func_179097_i();
            GlStateManager.func_179143_c(513);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179084_k();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL14.glBlendEquation(32774);
            GlStateManager.func_179106_n();
            GL11.glFogi(2917, 2048);
            GlStateManager.func_179095_a(1.0F);
            GlStateManager.func_179102_b(0.0F);
            GlStateManager.func_179153_c(1.0F);
            GL11.glFog(2918, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
               GL11.glFogi(2917, 34140);
            }

            GlStateManager.func_179136_a(0.0F, 0.0F);
            GlStateManager.func_179134_v();
            GlStateManager.func_179116_f(5379);
            GlStateManager.func_179100_b(GlStateManager.TexGen.S);
            GlStateManager.func_179149_a(GlStateManager.TexGen.S, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9474, RenderHelper.func_74521_a(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9217, RenderHelper.func_74521_a(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.T);
            GlStateManager.func_179149_a(GlStateManager.TexGen.T, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9474, RenderHelper.func_74521_a(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9217, RenderHelper.func_74521_a(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.R);
            GlStateManager.func_179149_a(GlStateManager.TexGen.R, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9474, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9217, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.Q);
            GlStateManager.func_179149_a(GlStateManager.TexGen.Q, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9217, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179138_g(0);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9986);
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 33085, 1000);
            GL11.glTexParameteri(3553, 33083, 1000);
            GL11.glTexParameteri(3553, 33082, -1000);
            GL11.glTexParameterf(3553, 34049, 0.0F);
            GL11.glTexEnvi(8960, 8704, 8448);
            GL11.glTexEnv(8960, 8705, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GL11.glTexEnvi(8960, 34161, 8448);
            GL11.glTexEnvi(8960, 34162, 8448);
            GL11.glTexEnvi(8960, 34176, 5890);
            GL11.glTexEnvi(8960, 34177, 34168);
            GL11.glTexEnvi(8960, 34178, 34166);
            GL11.glTexEnvi(8960, 34184, 5890);
            GL11.glTexEnvi(8960, 34185, 34168);
            GL11.glTexEnvi(8960, 34186, 34166);
            GL11.glTexEnvi(8960, 34192, 768);
            GL11.glTexEnvi(8960, 34193, 768);
            GL11.glTexEnvi(8960, 34194, 770);
            GL11.glTexEnvi(8960, 34200, 770);
            GL11.glTexEnvi(8960, 34201, 770);
            GL11.glTexEnvi(8960, 34202, 770);
            GL11.glTexEnvf(8960, 34163, 1.0F);
            GL11.glTexEnvf(8960, 3356, 1.0F);
            GlStateManager.func_179133_A();
            GlStateManager.func_179103_j(7425);
            GlStateManager.func_179101_C();
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179151_a(1.0D);
            GL11.glLineWidth(1.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            GL11.glPolygonMode(1028, 6914);
            GL11.glPolygonMode(1029, 6914);
         }

         public void func_187374_b() {
         }
      },
      PLAYER_SKIN {
         public void func_187373_a() {
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
         }

         public void func_187374_b() {
            GlStateManager.func_179084_k();
         }
      },
      TRANSPARENT_MODEL {
         public void func_187373_a() {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179147_l();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.func_179092_a(516, 0.003921569F);
         }

         public void func_187374_b() {
            GlStateManager.func_179084_k();
            GlStateManager.func_179092_a(516, 0.1F);
            GlStateManager.func_179132_a(true);
         }
      };

      private Profile() {
      }

      public abstract void func_187373_a();

      public abstract void func_187374_b();
   }

   public static enum SourceFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_187395_p;

      private SourceFactor(int p_i46514_3_) {
         this.field_187395_p = p_i46514_3_;
      }
   }

   static class StencilFunc {
      public int field_179081_a;
      public int field_179080_c;

      private StencilFunc() {
         this.field_179081_a = 519;
         this.field_179080_c = -1;
      }
   }

   static class StencilState {
      public GlStateManager.StencilFunc field_179078_a;
      public int field_179076_b;
      public int field_179077_c;
      public int field_179074_d;
      public int field_179075_e;

      private StencilState() {
         this.field_179078_a = new GlStateManager.StencilFunc();
         this.field_179076_b = -1;
         this.field_179077_c = 7680;
         this.field_179074_d = 7680;
         this.field_179075_e = 7680;
      }
   }

   public static enum TexGen {
      S,
      T,
      R,
      Q;
   }

   static class TexGenCoord {
      public GlStateManager.BooleanState field_179067_a;
      public int field_179065_b;
      public int field_179066_c = -1;

      public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
         this.field_179065_b = p_i46254_1_;
         this.field_179067_a = new GlStateManager.BooleanState(p_i46254_2_);
      }
   }

   static class TexGenState {
      public GlStateManager.TexGenCoord field_179064_a;
      public GlStateManager.TexGenCoord field_179062_b;
      public GlStateManager.TexGenCoord field_179063_c;
      public GlStateManager.TexGenCoord field_179061_d;

      private TexGenState() {
         this.field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
         this.field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
         this.field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
         this.field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);
      }
   }

   static class TextureState {
      public GlStateManager.BooleanState field_179060_a;
      public int field_179059_b;

      private TextureState() {
         this.field_179060_a = new GlStateManager.BooleanState(3553);
      }
   }
}
