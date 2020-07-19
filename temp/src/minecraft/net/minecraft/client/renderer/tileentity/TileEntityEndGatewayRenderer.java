package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEndGatewayRenderer extends TileEntityEndPortalRenderer {
   private static final ResourceLocation field_188199_f = new ResourceLocation("textures/entity/end_gateway_beam.png");

   public void func_192841_a(TileEntityEndPortal p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      GlStateManager.func_179106_n();
      TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)p_192841_1_;
      if (tileentityendgateway.func_184309_b() || tileentityendgateway.func_184310_d()) {
         GlStateManager.func_179092_a(516, 0.1F);
         this.func_147499_a(field_188199_f);
         float f = tileentityendgateway.func_184309_b() ? tileentityendgateway.func_184302_e(p_192841_8_) : tileentityendgateway.func_184305_g(p_192841_8_);
         double d0 = tileentityendgateway.func_184309_b() ? 256.0D - p_192841_4_ : 50.0D;
         f = MathHelper.func_76126_a(f * 3.1415927F);
         int i = MathHelper.func_76128_c((double)f * d0);
         float[] afloat = tileentityendgateway.func_184309_b() ? EnumDyeColor.MAGENTA.func_193349_f() : EnumDyeColor.PURPLE.func_193349_f();
         TileEntityBeaconRenderer.func_188205_a(p_192841_2_, p_192841_4_, p_192841_6_, (double)p_192841_8_, (double)f, (double)tileentityendgateway.func_145831_w().func_82737_E(), 0, i, afloat, 0.15D, 0.175D);
         TileEntityBeaconRenderer.func_188205_a(p_192841_2_, p_192841_4_, p_192841_6_, (double)p_192841_8_, (double)f, (double)tileentityendgateway.func_145831_w().func_82737_E(), 0, -i, afloat, 0.15D, 0.175D);
      }

      super.func_192841_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, p_192841_10_);
      GlStateManager.func_179127_m();
   }

   protected int func_191286_a(double p_191286_1_) {
      return super.func_191286_a(p_191286_1_) + 1;
   }

   protected float func_191287_c() {
      return 1.0F;
   }
}
