package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

public class ItemSaddle extends Item {
   public ItemSaddle() {
      this.field_77777_bU = 1;
      this.func_77637_a(CreativeTabs.field_78029_e);
   }

   public boolean func_111207_a(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_, EnumHand p_111207_4_) {
      if (p_111207_3_ instanceof EntityPig) {
         EntityPig entitypig = (EntityPig)p_111207_3_;
         if (!entitypig.func_70901_n() && !entitypig.func_70631_g_()) {
            entitypig.func_70900_e(true);
            entitypig.field_70170_p.func_184148_a(p_111207_2_, entitypig.field_70165_t, entitypig.field_70163_u, entitypig.field_70161_v, SoundEvents.field_187706_dO, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            p_111207_1_.func_190918_g(1);
         }

         return true;
      } else {
         return false;
      }
   }
}
