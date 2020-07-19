package net.minecraft.client.renderer.debug;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class DebugRendererCollisionBox implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_191312_a;
   private EntityPlayer field_191313_b;
   private double field_191314_c;
   private double field_191315_d;
   private double field_191316_e;

   public DebugRendererCollisionBox(Minecraft p_i47215_1_) {
      this.field_191312_a = p_i47215_1_;
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      this.field_191313_b = this.field_191312_a.field_71439_g;
      this.field_191314_c = this.field_191313_b.field_70142_S + (this.field_191313_b.field_70165_t - this.field_191313_b.field_70142_S) * (double)p_190060_1_;
      this.field_191315_d = this.field_191313_b.field_70137_T + (this.field_191313_b.field_70163_u - this.field_191313_b.field_70137_T) * (double)p_190060_1_;
      this.field_191316_e = this.field_191313_b.field_70136_U + (this.field_191313_b.field_70161_v - this.field_191313_b.field_70136_U) * (double)p_190060_1_;
      World world = this.field_191312_a.field_71439_g.field_70170_p;
      List<AxisAlignedBB> list = world.func_184144_a(this.field_191313_b, this.field_191313_b.func_174813_aQ().func_186662_g(6.0D));
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);

      for(AxisAlignedBB axisalignedbb : list) {
         RenderGlobal.func_189697_a(axisalignedbb.func_186662_g(0.002D).func_72317_d(-this.field_191314_c, -this.field_191315_d, -this.field_191316_e), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
