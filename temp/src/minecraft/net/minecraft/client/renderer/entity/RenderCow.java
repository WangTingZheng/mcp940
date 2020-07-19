package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCow;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderCow extends RenderLiving<EntityCow> {
   private static final ResourceLocation field_110833_a = new ResourceLocation("textures/entity/cow/cow.png");

   public RenderCow(RenderManager p_i47210_1_) {
      super(p_i47210_1_, new ModelCow(), 0.7F);
   }

   protected ResourceLocation func_110775_a(EntityCow p_110775_1_) {
      return field_110833_a;
   }
}
