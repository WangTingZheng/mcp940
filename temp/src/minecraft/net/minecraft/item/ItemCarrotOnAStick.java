package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item {
   public ItemCarrotOnAStick() {
      this.func_77637_a(CreativeTabs.field_78029_e);
      this.func_77625_d(1);
      this.func_77656_e(25);
   }

   public boolean func_77662_d() {
      return true;
   }

   public boolean func_77629_n_() {
      return true;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (p_77659_1_.field_72995_K) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         if (p_77659_2_.func_184218_aH() && p_77659_2_.func_184187_bx() instanceof EntityPig) {
            EntityPig entitypig = (EntityPig)p_77659_2_.func_184187_bx();
            if (itemstack.func_77958_k() - itemstack.func_77960_j() >= 7 && entitypig.func_184762_da()) {
               itemstack.func_77972_a(7, p_77659_2_);
               if (itemstack.func_190926_b()) {
                  ItemStack itemstack1 = new ItemStack(Items.field_151112_aM);
                  itemstack1.func_77982_d(itemstack.func_77978_p());
                  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack1);
               }

               return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
         }

         p_77659_2_.func_71029_a(StatList.func_188057_b(this));
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      }
   }
}
