package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

public class PotionHealthBoost extends Potion {
   public PotionHealthBoost(boolean p_i46817_1_, int p_i46817_2_) {
      super(p_i46817_1_, p_i46817_2_);
   }

   public void func_111187_a(EntityLivingBase p_111187_1_, AbstractAttributeMap p_111187_2_, int p_111187_3_) {
      super.func_111187_a(p_111187_1_, p_111187_2_, p_111187_3_);
      if (p_111187_1_.func_110143_aJ() > p_111187_1_.func_110138_aP()) {
         p_111187_1_.func_70606_j(p_111187_1_.func_110138_aP());
      }

   }
}
