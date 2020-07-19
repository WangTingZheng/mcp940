package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball<EntityPotion> {
   public RenderPotion(RenderManager p_i46136_1_, RenderItem p_i46136_2_) {
      super(p_i46136_1_, Items.field_151068_bn, p_i46136_2_);
   }

   public ItemStack func_177082_d(EntityPotion p_177082_1_) {
      return p_177082_1_.func_184543_l();
   }
}
