package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemSplashPotion extends ItemPotion {
   public String func_77653_i(ItemStack p_77653_1_) {
      return I18n.func_74838_a(PotionUtils.func_185191_c(p_77653_1_).func_185174_b("splash_potion.effect."));
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      ItemStack itemstack1 = p_77659_2_.field_71075_bZ.field_75098_d ? itemstack.func_77946_l() : itemstack.func_77979_a(1);
      p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187827_fP, SoundCategory.PLAYERS, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
      if (!p_77659_1_.field_72995_K) {
         EntityPotion entitypotion = new EntityPotion(p_77659_1_, p_77659_2_, itemstack1);
         entitypotion.func_184538_a(p_77659_2_, p_77659_2_.field_70125_A, p_77659_2_.field_70177_z, -20.0F, 0.5F, 1.0F);
         p_77659_1_.func_72838_d(entitypotion);
      }

      p_77659_2_.func_71029_a(StatList.func_188057_b(this));
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
   }
}
