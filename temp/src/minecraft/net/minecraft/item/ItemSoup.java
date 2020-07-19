package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemSoup extends ItemFood {
   public ItemSoup(int p_i45330_1_) {
      super(p_i45330_1_, false);
      this.func_77625_d(1);
   }

   public ItemStack func_77654_b(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_) {
      super.func_77654_b(p_77654_1_, p_77654_2_, p_77654_3_);
      return new ItemStack(Items.field_151054_z);
   }
}
