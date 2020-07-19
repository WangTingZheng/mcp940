package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
   private static final ResourceLocation field_110873_a = new ResourceLocation("textures/entity/slime/magmacube.png");

   public RenderMagmaCube(RenderManager p_i46159_1_) {
      super(p_i46159_1_, new ModelMagmaCube(), 0.25F);
   }

   protected ResourceLocation func_110775_a(EntityMagmaCube p_110775_1_) {
      return field_110873_a;
   }

   protected void func_77041_b(EntityMagmaCube p_77041_1_, float p_77041_2_) {
      int i = p_77041_1_.func_70809_q();
      float f = (p_77041_1_.field_70812_c + (p_77041_1_.field_70811_b - p_77041_1_.field_70812_c) * p_77041_2_) / ((float)i * 0.5F + 1.0F);
      float f1 = 1.0F / (f + 1.0F);
      GlStateManager.func_179152_a(f1 * (float)i, 1.0F / f1 * (float)i, f1 * (float)i);
   }
}
