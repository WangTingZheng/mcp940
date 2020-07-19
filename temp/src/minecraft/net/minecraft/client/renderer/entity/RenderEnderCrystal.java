package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
   private static final ResourceLocation field_110787_a = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
   private final ModelBase field_76995_b = new ModelEnderCrystal(0.0F, true);
   private final ModelBase field_188316_g = new ModelEnderCrystal(0.0F, false);

   public RenderEnderCrystal(RenderManager p_i46184_1_) {
      super(p_i46184_1_);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityEnderCrystal p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      float f = (float)p_76986_1_.field_70261_a + p_76986_9_;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      this.func_110776_a(field_110787_a);
      float f1 = MathHelper.func_76126_a(f * 0.2F) / 2.0F + 0.5F;
      f1 = f1 * f1 + f1;
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      if (p_76986_1_.func_184520_k()) {
         this.field_76995_b.func_78088_a(p_76986_1_, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.field_188316_g.func_78088_a(p_76986_1_, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
      }

      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      BlockPos blockpos = p_76986_1_.func_184518_j();
      if (blockpos != null) {
         this.func_110776_a(RenderDragon.field_110843_g);
         float f2 = (float)blockpos.func_177958_n() + 0.5F;
         float f3 = (float)blockpos.func_177956_o() + 0.5F;
         float f4 = (float)blockpos.func_177952_p() + 0.5F;
         double d0 = (double)f2 - p_76986_1_.field_70165_t;
         double d1 = (double)f3 - p_76986_1_.field_70163_u;
         double d2 = (double)f4 - p_76986_1_.field_70161_v;
         RenderDragon.func_188325_a(p_76986_2_ + d0, p_76986_4_ - 0.3D + (double)(f1 * 0.4F) + d1, p_76986_6_ + d2, p_76986_9_, (double)f2, (double)f3, (double)f4, p_76986_1_.field_70261_a, p_76986_1_.field_70165_t, p_76986_1_.field_70163_u, p_76986_1_.field_70161_v);
      }

      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityEnderCrystal p_110775_1_) {
      return field_110787_a;
   }

   public boolean func_177071_a(EntityEnderCrystal p_177071_1_, ICamera p_177071_2_, double p_177071_3_, double p_177071_5_, double p_177071_7_) {
      return super.func_177071_a(p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_) || p_177071_1_.func_184518_j() != null;
   }
}
