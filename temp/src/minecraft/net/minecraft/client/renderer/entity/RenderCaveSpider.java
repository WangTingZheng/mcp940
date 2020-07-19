package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider<EntityCaveSpider> {
   private static final ResourceLocation field_110893_a = new ResourceLocation("textures/entity/spider/cave_spider.png");

   public RenderCaveSpider(RenderManager p_i46189_1_) {
      super(p_i46189_1_);
      this.field_76989_e *= 0.7F;
   }

   protected void func_77041_b(EntityCaveSpider p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(0.7F, 0.7F, 0.7F);
   }

   protected ResourceLocation func_110775_a(EntityCaveSpider p_110775_1_) {
      return field_110893_a;
   }
}
