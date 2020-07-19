package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

public class RenderGiantZombie extends RenderLiving<EntityGiantZombie> {
   private static final ResourceLocation field_110871_a = new ResourceLocation("textures/entity/zombie/zombie.png");
   private final float field_77073_a;

   public RenderGiantZombie(RenderManager p_i47206_1_, float p_i47206_2_) {
      super(p_i47206_1_, new ModelZombie(), 0.5F * p_i47206_2_);
      this.field_77073_a = p_i47206_2_;
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerBipedArmor(this) {
         protected void func_177177_a() {
            this.field_177189_c = (T)(new ModelZombie(0.5F, true));
            this.field_177186_d = (T)(new ModelZombie(1.0F, true));
         }
      });
   }

   public void func_82422_c() {
      GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
   }

   protected void func_77041_b(EntityGiantZombie p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(this.field_77073_a, this.field_77073_a, this.field_77073_a);
   }

   protected ResourceLocation func_110775_a(EntityGiantZombie p_110775_1_) {
      return field_110871_a;
   }
}
