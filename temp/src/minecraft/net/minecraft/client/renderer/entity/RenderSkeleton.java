package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderSkeleton extends RenderBiped<AbstractSkeleton> {
   private static final ResourceLocation field_110862_k = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public RenderSkeleton(RenderManager p_i46143_1_) {
      super(p_i46143_1_, new ModelSkeleton(), 0.5F);
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerBipedArmor(this) {
         protected void func_177177_a() {
            this.field_177189_c = (T)(new ModelSkeleton(0.5F, true));
            this.field_177186_d = (T)(new ModelSkeleton(1.0F, true));
         }
      });
   }

   public void func_82422_c() {
      GlStateManager.func_179109_b(0.09375F, 0.1875F, 0.0F);
   }

   protected ResourceLocation func_110775_a(AbstractSkeleton p_110775_1_) {
      return field_110862_k;
   }
}
