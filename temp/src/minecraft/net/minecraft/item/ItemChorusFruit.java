package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemChorusFruit extends ItemFood {
   public ItemChorusFruit(int p_i46747_1_, float p_i46747_2_) {
      super(p_i46747_1_, p_i46747_2_, false);
   }

   public ItemStack func_77654_b(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_) {
      ItemStack itemstack = super.func_77654_b(p_77654_1_, p_77654_2_, p_77654_3_);
      if (!p_77654_2_.field_72995_K) {
         double d0 = p_77654_3_.field_70165_t;
         double d1 = p_77654_3_.field_70163_u;
         double d2 = p_77654_3_.field_70161_v;

         for(int i = 0; i < 16; ++i) {
            double d3 = p_77654_3_.field_70165_t + (p_77654_3_.func_70681_au().nextDouble() - 0.5D) * 16.0D;
            double d4 = MathHelper.func_151237_a(p_77654_3_.field_70163_u + (double)(p_77654_3_.func_70681_au().nextInt(16) - 8), 0.0D, (double)(p_77654_2_.func_72940_L() - 1));
            double d5 = p_77654_3_.field_70161_v + (p_77654_3_.func_70681_au().nextDouble() - 0.5D) * 16.0D;
            if (p_77654_3_.func_184218_aH()) {
               p_77654_3_.func_184210_p();
            }

            if (p_77654_3_.func_184595_k(d3, d4, d5)) {
               p_77654_2_.func_184148_a((EntityPlayer)null, d0, d1, d2, SoundEvents.field_187544_ad, SoundCategory.PLAYERS, 1.0F, 1.0F);
               p_77654_3_.func_184185_a(SoundEvents.field_187544_ad, 1.0F, 1.0F);
               break;
            }
         }

         if (p_77654_3_ instanceof EntityPlayer) {
            ((EntityPlayer)p_77654_3_).func_184811_cZ().func_185145_a(this, 20);
         }
      }

      return itemstack;
   }
}
