package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving<EntityOcelot> {
   private static final ResourceLocation field_110877_a = new ResourceLocation("textures/entity/cat/black.png");
   private static final ResourceLocation field_110875_f = new ResourceLocation("textures/entity/cat/ocelot.png");
   private static final ResourceLocation field_110876_g = new ResourceLocation("textures/entity/cat/red.png");
   private static final ResourceLocation field_110878_h = new ResourceLocation("textures/entity/cat/siamese.png");

   public RenderOcelot(RenderManager p_i47199_1_) {
      super(p_i47199_1_, new ModelOcelot(), 0.4F);
   }

   protected ResourceLocation func_110775_a(EntityOcelot p_110775_1_) {
      switch(p_110775_1_.func_70913_u()) {
      case 0:
      default:
         return field_110875_f;
      case 1:
         return field_110877_a;
      case 2:
         return field_110876_g;
      case 3:
         return field_110878_h;
      }
   }

   protected void func_77041_b(EntityOcelot p_77041_1_, float p_77041_2_) {
      super.func_77041_b(p_77041_1_, p_77041_2_);
      if (p_77041_1_.func_70909_n()) {
         GlStateManager.func_179152_a(0.8F, 0.8F, 0.8F);
      }

   }
}
