package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderSlime extends RenderLiving<EntitySlime> {
   private static final ResourceLocation field_110897_a = new ResourceLocation("textures/entity/slime/slime.png");

   public RenderSlime(RenderManager p_i47193_1_) {
      super(p_i47193_1_, new ModelSlime(16), 0.25F);
      this.func_177094_a(new LayerSlimeGel(this));
   }

   public void func_76986_a(EntitySlime p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.field_76989_e = 0.25F * (float)p_76986_1_.func_70809_q();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected void func_77041_b(EntitySlime p_77041_1_, float p_77041_2_) {
      float f = 0.999F;
      GlStateManager.func_179152_a(0.999F, 0.999F, 0.999F);
      float f1 = (float)p_77041_1_.func_70809_q();
      float f2 = (p_77041_1_.field_70812_c + (p_77041_1_.field_70811_b - p_77041_1_.field_70812_c) * p_77041_2_) / (f1 * 0.5F + 1.0F);
      float f3 = 1.0F / (f2 + 1.0F);
      GlStateManager.func_179152_a(f3 * f1, 1.0F / f3 * f1, f3 * f1);
   }

   protected ResourceLocation func_110775_a(EntitySlime p_110775_1_) {
      return field_110897_a;
   }
}
