package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEmptyMap extends ItemMapBase {
   protected ItemEmptyMap() {
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = ItemMap.func_190906_a(p_77659_1_, p_77659_2_.field_70165_t, p_77659_2_.field_70161_v, (byte)0, true, false);
      ItemStack itemstack1 = p_77659_2_.func_184586_b(p_77659_3_);
      itemstack1.func_190918_g(1);
      if (itemstack1.func_190926_b()) {
         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      } else {
         if (!p_77659_2_.field_71071_by.func_70441_a(itemstack.func_77946_l())) {
            p_77659_2_.func_71019_a(itemstack, false);
         }

         p_77659_2_.func_71029_a(StatList.func_188057_b(this));
         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack1);
      }
   }
}
