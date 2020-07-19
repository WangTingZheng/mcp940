package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

public class PotionAbsorption extends Potion {
   protected PotionAbsorption(boolean p_i46820_1_, int p_i46820_2_) {
      super(p_i46820_1_, p_i46820_2_);
   }

   public void func_111187_a(EntityLivingBase p_111187_1_, AbstractAttributeMap p_111187_2_, int p_111187_3_) {
      p_111187_1_.func_110149_m(p_111187_1_.func_110139_bj() - (float)(4 * (p_111187_3_ + 1)));
      super.func_111187_a(p_111187_1_, p_111187_2_, p_111187_3_);
   }

   public void func_111185_a(EntityLivingBase p_111185_1_, AbstractAttributeMap p_111185_2_, int p_111185_3_) {
      p_111185_1_.func_110149_m(p_111185_1_.func_110139_bj() + (float)(4 * (p_111185_3_ + 1)));
      super.func_111185_a(p_111185_1_, p_111185_2_, p_111185_3_);
   }
}
