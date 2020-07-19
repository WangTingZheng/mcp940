package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk() {
      this.func_77625_d(1);
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public ItemStack func_77654_b(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_) {
      if (p_77654_3_ instanceof EntityPlayerMP) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)p_77654_3_;
         CriteriaTriggers.field_193138_y.func_193148_a(entityplayermp, p_77654_1_);
         entityplayermp.func_71029_a(StatList.func_188057_b(this));
      }

      if (p_77654_3_ instanceof EntityPlayer && !((EntityPlayer)p_77654_3_).field_71075_bZ.field_75098_d) {
         p_77654_1_.func_190918_g(1);
      }

      if (!p_77654_2_.field_72995_K) {
         p_77654_3_.func_70674_bp();
      }

      return p_77654_1_.func_190926_b() ? new ItemStack(Items.field_151133_ar) : p_77654_1_;
   }

   public int func_77626_a(ItemStack p_77626_1_) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack p_77661_1_) {
      return EnumAction.DRINK;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      p_77659_2_.func_184598_c(p_77659_3_);
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, p_77659_2_.func_184586_b(p_77659_3_));
   }
}
