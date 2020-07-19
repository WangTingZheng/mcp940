package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import net.minecraft.util.math.Vec3d;

public class RenderHelper {
   private static final FloatBuffer field_74522_a = GLAllocation.func_74529_h(4);
   private static final Vec3d field_82884_b = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).func_72432_b();
   private static final Vec3d field_82885_c = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).func_72432_b();

   public static void func_74518_a() {
      GlStateManager.func_179140_f();
      GlStateManager.func_179122_b(0);
      GlStateManager.func_179122_b(1);
      GlStateManager.func_179119_h();
   }

   public static void func_74519_b() {
      GlStateManager.func_179145_e();
      GlStateManager.func_179085_a(0);
      GlStateManager.func_179085_a(1);
      GlStateManager.func_179142_g();
      GlStateManager.func_179104_a(1032, 5634);
      GlStateManager.func_187438_a(16384, 4611, func_74517_a(field_82884_b.field_72450_a, field_82884_b.field_72448_b, field_82884_b.field_72449_c, 0.0D));
      float f = 0.6F;
      GlStateManager.func_187438_a(16384, 4609, func_74521_a(0.6F, 0.6F, 0.6F, 1.0F));
      GlStateManager.func_187438_a(16384, 4608, func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
      GlStateManager.func_187438_a(16384, 4610, func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
      GlStateManager.func_187438_a(16385, 4611, func_74517_a(field_82885_c.field_72450_a, field_82885_c.field_72448_b, field_82885_c.field_72449_c, 0.0D));
      GlStateManager.func_187438_a(16385, 4609, func_74521_a(0.6F, 0.6F, 0.6F, 1.0F));
      GlStateManager.func_187438_a(16385, 4608, func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
      GlStateManager.func_187438_a(16385, 4610, func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
      GlStateManager.func_179103_j(7424);
      float f1 = 0.4F;
      GlStateManager.func_187424_a(2899, func_74521_a(0.4F, 0.4F, 0.4F, 1.0F));
   }

   private static FloatBuffer func_74517_a(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
      return func_74521_a((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
   }

   public static FloatBuffer func_74521_a(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
      field_74522_a.clear();
      field_74522_a.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
      field_74522_a.flip();
      return field_74522_a;
   }

   public static void func_74520_c() {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(-30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(165.0F, 1.0F, 0.0F, 0.0F);
      func_74519_b();
      GlStateManager.func_179121_F();
   }
}
