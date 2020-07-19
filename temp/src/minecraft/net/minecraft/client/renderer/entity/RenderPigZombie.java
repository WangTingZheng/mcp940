package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped<EntityPigZombie> {
   private static final ResourceLocation field_177120_j = new ResourceLocation("textures/entity/zombie_pigman.png");

   public RenderPigZombie(RenderManager p_i46148_1_) {
      super(p_i46148_1_, new ModelZombie(), 0.5F);
      this.func_177094_a(new LayerBipedArmor(this) {
         protected void func_177177_a() {
            this.field_177189_c = (T)(new ModelZombie(0.5F, true));
            this.field_177186_d = (T)(new ModelZombie(1.0F, true));
         }
      });
   }

   protected ResourceLocation func_110775_a(EntityPigZombie p_110775_1_) {
      return field_177120_j;
   }
}
