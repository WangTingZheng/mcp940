package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.layers.LayerStrayClothing;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderStray extends RenderSkeleton {
   private static final ResourceLocation field_190084_m = new ResourceLocation("textures/entity/skeleton/stray.png");

   public RenderStray(RenderManager p_i47191_1_) {
      super(p_i47191_1_);
      this.func_177094_a(new LayerStrayClothing(this));
   }

   protected ResourceLocation func_110775_a(AbstractSkeleton p_110775_1_) {
      return field_190084_m;
   }
}
