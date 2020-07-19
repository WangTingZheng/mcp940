package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

public class RenderBlaze extends RenderLiving<EntityBlaze> {
   private static final ResourceLocation field_110837_a = new ResourceLocation("textures/entity/blaze.png");

   public RenderBlaze(RenderManager p_i46191_1_) {
      super(p_i46191_1_, new ModelBlaze(), 0.5F);
   }

   protected ResourceLocation func_110775_a(EntityBlaze p_110775_1_) {
      return field_110837_a;
   }
}
