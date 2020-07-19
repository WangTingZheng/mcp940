package net.minecraft.client.renderer.culling;

import net.minecraft.util.math.AxisAlignedBB;

public class Frustum implements ICamera {
   private final ClippingHelper field_78552_a;
   private double field_78550_b;
   private double field_78551_c;
   private double field_78549_d;

   public Frustum() {
      this(ClippingHelperImpl.func_78558_a());
   }

   public Frustum(ClippingHelper p_i46196_1_) {
      this.field_78552_a = p_i46196_1_;
   }

   public void func_78547_a(double p_78547_1_, double p_78547_3_, double p_78547_5_) {
      this.field_78550_b = p_78547_1_;
      this.field_78551_c = p_78547_3_;
      this.field_78549_d = p_78547_5_;
   }

   public boolean func_78548_b(double p_78548_1_, double p_78548_3_, double p_78548_5_, double p_78548_7_, double p_78548_9_, double p_78548_11_) {
      return this.field_78552_a.func_78553_b(p_78548_1_ - this.field_78550_b, p_78548_3_ - this.field_78551_c, p_78548_5_ - this.field_78549_d, p_78548_7_ - this.field_78550_b, p_78548_9_ - this.field_78551_c, p_78548_11_ - this.field_78549_d);
   }

   public boolean func_78546_a(AxisAlignedBB p_78546_1_) {
      return this.func_78548_b(p_78546_1_.field_72340_a, p_78546_1_.field_72338_b, p_78546_1_.field_72339_c, p_78546_1_.field_72336_d, p_78546_1_.field_72337_e, p_78546_1_.field_72334_f);
   }
}
