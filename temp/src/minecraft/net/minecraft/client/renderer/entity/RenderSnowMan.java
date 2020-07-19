package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;

public class RenderSnowMan extends RenderLiving<EntitySnowman> {
   private static final ResourceLocation field_110895_a = new ResourceLocation("textures/entity/snowman.png");

   public RenderSnowMan(RenderManager p_i46140_1_) {
      super(p_i46140_1_, new ModelSnowMan(), 0.5F);
      this.func_177094_a(new LayerSnowmanHead(this));
   }

   protected ResourceLocation func_110775_a(EntitySnowman p_110775_1_) {
      return field_110895_a;
   }

   public ModelSnowMan func_177087_b() {
      return (ModelSnowMan)super.func_177087_b();
   }
}
