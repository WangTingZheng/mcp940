package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEvokerFangs;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.util.ResourceLocation;

public class RenderEvokerFangs extends Render<EntityEvokerFangs> {
   private static final ResourceLocation field_191329_a = new ResourceLocation("textures/entity/illager/fangs.png");
   private final ModelEvokerFangs field_191330_f = new ModelEvokerFangs();

   public RenderEvokerFangs(RenderManager p_i47208_1_) {
      super(p_i47208_1_);
   }

   public void func_76986_a(EntityEvokerFangs p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      float f = p_76986_1_.func_190550_a(p_76986_9_);
      if (f != 0.0F) {
         float f1 = 2.0F;
         if (f > 0.9F) {
            f1 = (float)((double)f1 * ((1.0D - (double)f) / 0.10000000149011612D));
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179129_p();
         GlStateManager.func_179141_d();
         this.func_180548_c(p_76986_1_);
         GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
         GlStateManager.func_179114_b(90.0F - p_76986_1_.field_70177_z, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179152_a(-f1, -f1, f1);
         float f2 = 0.03125F;
         GlStateManager.func_179109_b(0.0F, -0.626F, 0.0F);
         this.field_191330_f.func_78088_a(p_76986_1_, f, 0.0F, 0.0F, p_76986_1_.field_70177_z, p_76986_1_.field_70125_A, 0.03125F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179089_o();
         super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      }
   }

   protected ResourceLocation func_110775_a(EntityEvokerFangs p_110775_1_) {
      return field_191329_a;
   }
}
