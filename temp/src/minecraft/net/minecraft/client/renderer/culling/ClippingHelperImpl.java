package net.minecraft.client.renderer.culling;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class ClippingHelperImpl extends ClippingHelper {
   private static final ClippingHelperImpl field_78563_e = new ClippingHelperImpl();
   private final FloatBuffer field_78561_f = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_78562_g = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_78564_h = GLAllocation.func_74529_h(16);

   public static ClippingHelper func_78558_a() {
      field_78563_e.func_78560_b();
      return field_78563_e;
   }

   private void func_180547_a(float[] p_180547_1_) {
      float f = MathHelper.func_76129_c(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
      p_180547_1_[0] /= f;
      p_180547_1_[1] /= f;
      p_180547_1_[2] /= f;
      p_180547_1_[3] /= f;
   }

   public void func_78560_b() {
      this.field_78561_f.clear();
      this.field_78562_g.clear();
      this.field_78564_h.clear();
      GlStateManager.func_179111_a(2983, this.field_78561_f);
      GlStateManager.func_179111_a(2982, this.field_78562_g);
      float[] afloat = this.field_178625_b;
      float[] afloat1 = this.field_178626_c;
      this.field_78561_f.flip().limit(16);
      this.field_78561_f.get(afloat);
      this.field_78562_g.flip().limit(16);
      this.field_78562_g.get(afloat1);
      this.field_78554_d[0] = afloat1[0] * afloat[0] + afloat1[1] * afloat[4] + afloat1[2] * afloat[8] + afloat1[3] * afloat[12];
      this.field_78554_d[1] = afloat1[0] * afloat[1] + afloat1[1] * afloat[5] + afloat1[2] * afloat[9] + afloat1[3] * afloat[13];
      this.field_78554_d[2] = afloat1[0] * afloat[2] + afloat1[1] * afloat[6] + afloat1[2] * afloat[10] + afloat1[3] * afloat[14];
      this.field_78554_d[3] = afloat1[0] * afloat[3] + afloat1[1] * afloat[7] + afloat1[2] * afloat[11] + afloat1[3] * afloat[15];
      this.field_78554_d[4] = afloat1[4] * afloat[0] + afloat1[5] * afloat[4] + afloat1[6] * afloat[8] + afloat1[7] * afloat[12];
      this.field_78554_d[5] = afloat1[4] * afloat[1] + afloat1[5] * afloat[5] + afloat1[6] * afloat[9] + afloat1[7] * afloat[13];
      this.field_78554_d[6] = afloat1[4] * afloat[2] + afloat1[5] * afloat[6] + afloat1[6] * afloat[10] + afloat1[7] * afloat[14];
      this.field_78554_d[7] = afloat1[4] * afloat[3] + afloat1[5] * afloat[7] + afloat1[6] * afloat[11] + afloat1[7] * afloat[15];
      this.field_78554_d[8] = afloat1[8] * afloat[0] + afloat1[9] * afloat[4] + afloat1[10] * afloat[8] + afloat1[11] * afloat[12];
      this.field_78554_d[9] = afloat1[8] * afloat[1] + afloat1[9] * afloat[5] + afloat1[10] * afloat[9] + afloat1[11] * afloat[13];
      this.field_78554_d[10] = afloat1[8] * afloat[2] + afloat1[9] * afloat[6] + afloat1[10] * afloat[10] + afloat1[11] * afloat[14];
      this.field_78554_d[11] = afloat1[8] * afloat[3] + afloat1[9] * afloat[7] + afloat1[10] * afloat[11] + afloat1[11] * afloat[15];
      this.field_78554_d[12] = afloat1[12] * afloat[0] + afloat1[13] * afloat[4] + afloat1[14] * afloat[8] + afloat1[15] * afloat[12];
      this.field_78554_d[13] = afloat1[12] * afloat[1] + afloat1[13] * afloat[5] + afloat1[14] * afloat[9] + afloat1[15] * afloat[13];
      this.field_78554_d[14] = afloat1[12] * afloat[2] + afloat1[13] * afloat[6] + afloat1[14] * afloat[10] + afloat1[15] * afloat[14];
      this.field_78554_d[15] = afloat1[12] * afloat[3] + afloat1[13] * afloat[7] + afloat1[14] * afloat[11] + afloat1[15] * afloat[15];
      float[] afloat2 = this.field_78557_a[0];
      afloat2[0] = this.field_78554_d[3] - this.field_78554_d[0];
      afloat2[1] = this.field_78554_d[7] - this.field_78554_d[4];
      afloat2[2] = this.field_78554_d[11] - this.field_78554_d[8];
      afloat2[3] = this.field_78554_d[15] - this.field_78554_d[12];
      this.func_180547_a(afloat2);
      float[] afloat3 = this.field_78557_a[1];
      afloat3[0] = this.field_78554_d[3] + this.field_78554_d[0];
      afloat3[1] = this.field_78554_d[7] + this.field_78554_d[4];
      afloat3[2] = this.field_78554_d[11] + this.field_78554_d[8];
      afloat3[3] = this.field_78554_d[15] + this.field_78554_d[12];
      this.func_180547_a(afloat3);
      float[] afloat4 = this.field_78557_a[2];
      afloat4[0] = this.field_78554_d[3] + this.field_78554_d[1];
      afloat4[1] = this.field_78554_d[7] + this.field_78554_d[5];
      afloat4[2] = this.field_78554_d[11] + this.field_78554_d[9];
      afloat4[3] = this.field_78554_d[15] + this.field_78554_d[13];
      this.func_180547_a(afloat4);
      float[] afloat5 = this.field_78557_a[3];
      afloat5[0] = this.field_78554_d[3] - this.field_78554_d[1];
      afloat5[1] = this.field_78554_d[7] - this.field_78554_d[5];
      afloat5[2] = this.field_78554_d[11] - this.field_78554_d[9];
      afloat5[3] = this.field_78554_d[15] - this.field_78554_d[13];
      this.func_180547_a(afloat5);
      float[] afloat6 = this.field_78557_a[4];
      afloat6[0] = this.field_78554_d[3] - this.field_78554_d[2];
      afloat6[1] = this.field_78554_d[7] - this.field_78554_d[6];
      afloat6[2] = this.field_78554_d[11] - this.field_78554_d[10];
      afloat6[3] = this.field_78554_d[15] - this.field_78554_d[14];
      this.func_180547_a(afloat6);
      float[] afloat7 = this.field_78557_a[5];
      afloat7[0] = this.field_78554_d[3] + this.field_78554_d[2];
      afloat7[1] = this.field_78554_d[7] + this.field_78554_d[6];
      afloat7[2] = this.field_78554_d[11] + this.field_78554_d[10];
      afloat7[3] = this.field_78554_d[15] + this.field_78554_d[14];
      this.func_180547_a(afloat7);
   }
}
