package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie extends RenderBiped<EntityZombie> {
   private static final ResourceLocation field_110865_p = new ResourceLocation("textures/entity/zombie/zombie.png");

   public RenderZombie(RenderManager p_i46127_1_) {
      super(p_i46127_1_, new ModelZombie(), 0.5F);
      LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
         protected void func_177177_a() {
            this.field_177189_c = (T)(new ModelZombie(0.5F, true));
            this.field_177186_d = (T)(new ModelZombie(1.0F, true));
         }
      };
      this.func_177094_a(layerbipedarmor);
   }

   protected ResourceLocation func_110775_a(EntityZombie p_110775_1_) {
      return field_110865_p;
   }
}
