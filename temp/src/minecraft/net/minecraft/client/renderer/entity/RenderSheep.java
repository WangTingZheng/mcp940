package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving<EntitySheep> {
   private static final ResourceLocation field_110884_f = new ResourceLocation("textures/entity/sheep/sheep.png");

   public RenderSheep(RenderManager p_i47195_1_) {
      super(p_i47195_1_, new ModelSheep2(), 0.7F);
      this.func_177094_a(new LayerSheepWool(this));
   }

   protected ResourceLocation func_110775_a(EntitySheep p_110775_1_) {
      return field_110884_f;
   }
}
