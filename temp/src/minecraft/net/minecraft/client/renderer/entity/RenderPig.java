package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig extends RenderLiving<EntityPig> {
   private static final ResourceLocation field_110887_f = new ResourceLocation("textures/entity/pig/pig.png");

   public RenderPig(RenderManager p_i47198_1_) {
      super(p_i47198_1_, new ModelPig(), 0.7F);
      this.func_177094_a(new LayerSaddle(this));
   }

   protected ResourceLocation func_110775_a(EntityPig p_110775_1_) {
      return field_110887_f;
   }
}
