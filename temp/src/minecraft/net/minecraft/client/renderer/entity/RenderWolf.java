package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving<EntityWolf> {
   private static final ResourceLocation field_110917_a = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation field_110915_f = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation field_110916_g = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public RenderWolf(RenderManager p_i47187_1_) {
      super(p_i47187_1_, new ModelWolf(), 0.5F);
      this.func_177094_a(new LayerWolfCollar(this));
   }

   protected float func_77044_a(EntityWolf p_77044_1_, float p_77044_2_) {
      return p_77044_1_.func_70920_v();
   }

   public void func_76986_a(EntityWolf p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      if (p_76986_1_.func_70921_u()) {
         float f = p_76986_1_.func_70013_c() * p_76986_1_.func_70915_j(p_76986_9_);
         GlStateManager.func_179124_c(f, f, f);
      }

      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityWolf p_110775_1_) {
      if (p_110775_1_.func_70909_n()) {
         return field_110915_f;
      } else {
         return p_110775_1_.func_70919_bu() ? field_110916_g : field_110917_a;
      }
   }
}
