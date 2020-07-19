package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
   private final ShaderManager field_148051_c;
   public final Framebuffer field_148052_a;
   public final Framebuffer field_148050_b;
   private final List<Object> field_148048_d = Lists.<Object>newArrayList();
   private final List<String> field_148049_e = Lists.<String>newArrayList();
   private final List<Integer> field_148046_f = Lists.<Integer>newArrayList();
   private final List<Integer> field_148047_g = Lists.<Integer>newArrayList();
   private Matrix4f field_148053_h;

   public Shader(IResourceManager p_i45089_1_, String p_i45089_2_, Framebuffer p_i45089_3_, Framebuffer p_i45089_4_) throws JsonException, IOException {
      this.field_148051_c = new ShaderManager(p_i45089_1_, p_i45089_2_);
      this.field_148052_a = p_i45089_3_;
      this.field_148050_b = p_i45089_4_;
   }

   public void func_148044_b() {
      this.field_148051_c.func_147988_a();
   }

   public void func_148041_a(String p_148041_1_, Object p_148041_2_, int p_148041_3_, int p_148041_4_) {
      this.field_148049_e.add(this.field_148049_e.size(), p_148041_1_);
      this.field_148048_d.add(this.field_148048_d.size(), p_148041_2_);
      this.field_148046_f.add(this.field_148046_f.size(), Integer.valueOf(p_148041_3_));
      this.field_148047_g.add(this.field_148047_g.size(), Integer.valueOf(p_148041_4_));
   }

   private void func_148040_d() {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179097_i();
      GlStateManager.func_179118_c();
      GlStateManager.func_179106_n();
      GlStateManager.func_179140_f();
      GlStateManager.func_179119_h();
      GlStateManager.func_179098_w();
      GlStateManager.func_179144_i(0);
   }

   public void func_148045_a(Matrix4f p_148045_1_) {
      this.field_148053_h = p_148045_1_;
   }

   public void func_148042_a(float p_148042_1_) {
      this.func_148040_d();
      this.field_148052_a.func_147609_e();
      float f = (float)this.field_148050_b.field_147622_a;
      float f1 = (float)this.field_148050_b.field_147620_b;
      GlStateManager.func_179083_b(0, 0, (int)f, (int)f1);
      this.field_148051_c.func_147992_a("DiffuseSampler", this.field_148052_a);

      for(int i = 0; i < this.field_148048_d.size(); ++i) {
         this.field_148051_c.func_147992_a(this.field_148049_e.get(i), this.field_148048_d.get(i));
         this.field_148051_c.func_147984_b("AuxSize" + i).func_148087_a((float)((Integer)this.field_148046_f.get(i)).intValue(), (float)((Integer)this.field_148047_g.get(i)).intValue());
      }

      this.field_148051_c.func_147984_b("ProjMat").func_148088_a(this.field_148053_h);
      this.field_148051_c.func_147984_b("InSize").func_148087_a((float)this.field_148052_a.field_147622_a, (float)this.field_148052_a.field_147620_b);
      this.field_148051_c.func_147984_b("OutSize").func_148087_a(f, f1);
      this.field_148051_c.func_147984_b("Time").func_148090_a(p_148042_1_);
      Minecraft minecraft = Minecraft.func_71410_x();
      this.field_148051_c.func_147984_b("ScreenSize").func_148087_a((float)minecraft.field_71443_c, (float)minecraft.field_71440_d);
      this.field_148051_c.func_147995_c();
      this.field_148050_b.func_147614_f();
      this.field_148050_b.func_147610_a(false);
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179135_a(true, true, true, true);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(0.0D, (double)f1, 500.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)f, (double)f1, 500.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)f, 0.0D, 500.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 500.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179135_a(true, true, true, true);
      this.field_148051_c.func_147993_b();
      this.field_148050_b.func_147609_e();
      this.field_148052_a.func_147606_d();

      for(Object object : this.field_148048_d) {
         if (object instanceof Framebuffer) {
            ((Framebuffer)object).func_147606_d();
         }
      }

   }

   public ShaderManager func_148043_c() {
      return this.field_148051_c;
   }
}
