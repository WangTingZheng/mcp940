package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemTippedArrow extends ItemArrow {
   public ItemStack func_190903_i() {
      return PotionUtils.func_185188_a(super.func_190903_i(), PotionTypes.field_185254_z);
   }

   public EntityArrow func_185052_a(World p_185052_1_, ItemStack p_185052_2_, EntityLivingBase p_185052_3_) {
      EntityTippedArrow entitytippedarrow = new EntityTippedArrow(p_185052_1_, p_185052_3_);
      entitytippedarrow.func_184555_a(p_185052_2_);
      return entitytippedarrow;
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         for(PotionType potiontype : PotionType.field_185176_a) {
            if (!potiontype.func_185170_a().isEmpty()) {
               p_150895_2_.add(PotionUtils.func_185188_a(new ItemStack(this), potiontype));
            }
         }
      }

   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      PotionUtils.func_185182_a(p_77624_1_, p_77624_3_, 0.125F);
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      return I18n.func_74838_a(PotionUtils.func_185191_c(p_77653_1_).func_185174_b("tipped_arrow.effect."));
   }
}
