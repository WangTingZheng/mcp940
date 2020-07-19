package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.util.ResourceLocation;

public class LayerStrayClothing implements LayerRenderer<EntityStray> {
   private static final ResourceLocation field_190092_a = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
   private final RenderLivingBase<?> field_190093_b;
   private final ModelSkeleton field_190094_c = new ModelSkeleton(0.25F, true);

   public LayerStrayClothing(RenderLivingBase<?> p_i47183_1_) {
      this.field_190093_b = p_i47183_1_;
   }

   public void func_177141_a(EntityStray p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      this.field_190094_c.func_178686_a(this.field_190093_b.func_177087_b());
      this.field_190094_c.func_78086_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_190093_b.func_110776_a(field_190092_a);
      this.field_190094_c.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
   }

   public boolean func_177142_b() {
      return true;
   }
}
