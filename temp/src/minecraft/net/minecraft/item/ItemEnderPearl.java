package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemEnderPearl extends Item {
   public ItemEnderPearl() {
      this.field_77777_bU = 16;
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (!p_77659_2_.field_71075_bZ.field_75098_d) {
         itemstack.func_190918_g(1);
      }

      p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187595_bc, SoundCategory.NEUTRAL, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
      p_77659_2_.func_184811_cZ().func_185145_a(this, 20);
      if (!p_77659_1_.field_72995_K) {
         EntityEnderPearl entityenderpearl = new EntityEnderPearl(p_77659_1_, p_77659_2_);
         entityenderpearl.func_184538_a(p_77659_2_, p_77659_2_.field_70125_A, p_77659_2_.field_70177_z, 0.0F, 1.5F, 1.0F);
         p_77659_1_.func_72838_d(entityenderpearl);
      }

      p_77659_2_.func_71029_a(StatList.func_188057_b(this));
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
   }
}
