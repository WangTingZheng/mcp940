package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;

public class RenderWither extends RenderLiving<EntityWither> {
   private static final ResourceLocation field_110913_a = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation field_110912_f = new ResourceLocation("textures/entity/wither/wither.png");

   public RenderWither(RenderManager p_i46130_1_) {
      super(p_i46130_1_, new ModelWither(0.0F), 1.0F);
      this.func_177094_a(new LayerWitherAura(this));
   }

   protected ResourceLocation func_110775_a(EntityWither p_110775_1_) {
      int i = p_110775_1_.func_82212_n();
      return i > 0 && (i > 80 || i / 5 % 2 != 1) ? field_110913_a : field_110912_f;
   }

   protected void func_77041_b(EntityWither p_77041_1_, float p_77041_2_) {
      float f = 2.0F;
      int i = p_77041_1_.func_82212_n();
      if (i > 0) {
         f -= ((float)i - p_77041_2_) / 220.0F * 0.5F;
      }

      GlStateManager.func_179152_a(f, f, f);
   }
}
