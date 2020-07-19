package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class ItemNameTag extends Item {
   public ItemNameTag() {
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   public boolean func_111207_a(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_, EnumHand p_111207_4_) {
      if (p_111207_1_.func_82837_s() && !(p_111207_3_ instanceof EntityPlayer)) {
         p_111207_3_.func_96094_a(p_111207_1_.func_82833_r());
         if (p_111207_3_ instanceof EntityLiving) {
            ((EntityLiving)p_111207_3_).func_110163_bv();
         }

         p_111207_1_.func_190918_g(1);
         return true;
      } else {
         return false;
      }
   }
}
