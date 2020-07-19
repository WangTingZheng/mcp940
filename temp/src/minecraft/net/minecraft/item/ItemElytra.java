package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemElytra extends Item {
   public ItemElytra() {
      this.field_77777_bU = 1;
      this.func_77656_e(432);
      this.func_77637_a(CreativeTabs.field_78029_e);
      this.func_185043_a(new ResourceLocation("broken"), new IItemPropertyGetter() {
         public float func_185085_a(ItemStack p_185085_1_, @Nullable World p_185085_2_, @Nullable EntityLivingBase p_185085_3_) {
            return ItemElytra.func_185069_d(p_185085_1_) ? 0.0F : 1.0F;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(this, ItemArmor.field_96605_cw);
   }

   public static boolean func_185069_d(ItemStack p_185069_0_) {
      return p_185069_0_.func_77952_i() < p_185069_0_.func_77958_k() - 1;
   }

   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return p_82789_2_.func_77973_b() == Items.field_151116_aA;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
      ItemStack itemstack1 = p_77659_2_.func_184582_a(entityequipmentslot);
      if (itemstack1.func_190926_b()) {
         p_77659_2_.func_184201_a(entityequipmentslot, itemstack.func_77946_l());
         itemstack.func_190920_e(0);
         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      } else {
         return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
      }
   }
}
