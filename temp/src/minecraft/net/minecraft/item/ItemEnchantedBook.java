package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemEnchantedBook extends Item {
   public boolean func_77636_d(ItemStack p_77636_1_) {
      return true;
   }

   public boolean func_77616_k(ItemStack p_77616_1_) {
      return false;
   }

   public EnumRarity func_77613_e(ItemStack p_77613_1_) {
      return func_92110_g(p_77613_1_).func_82582_d() ? super.func_77613_e(p_77613_1_) : EnumRarity.UNCOMMON;
   }

   public static NBTTagList func_92110_g(ItemStack p_92110_0_) {
      NBTTagCompound nbttagcompound = p_92110_0_.func_77978_p();
      return nbttagcompound != null ? nbttagcompound.func_150295_c("StoredEnchantments", 10) : new NBTTagList();
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      super.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
      NBTTagList nbttaglist = func_92110_g(p_77624_1_);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         int j = nbttagcompound.func_74765_d("id");
         Enchantment enchantment = Enchantment.func_185262_c(j);
         if (enchantment != null) {
            p_77624_3_.add(enchantment.func_77316_c(nbttagcompound.func_74765_d("lvl")));
         }
      }

   }

   public static void func_92115_a(ItemStack p_92115_0_, EnchantmentData p_92115_1_) {
      NBTTagList nbttaglist = func_92110_g(p_92115_0_);
      boolean flag = true;

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         if (Enchantment.func_185262_c(nbttagcompound.func_74765_d("id")) == p_92115_1_.field_76302_b) {
            if (nbttagcompound.func_74765_d("lvl") < p_92115_1_.field_76303_c) {
               nbttagcompound.func_74777_a("lvl", (short)p_92115_1_.field_76303_c);
            }

            flag = false;
            break;
         }
      }

      if (flag) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.func_74777_a("id", (short)Enchantment.func_185258_b(p_92115_1_.field_76302_b));
         nbttagcompound1.func_74777_a("lvl", (short)p_92115_1_.field_76303_c);
         nbttaglist.func_74742_a(nbttagcompound1);
      }

      if (!p_92115_0_.func_77942_o()) {
         p_92115_0_.func_77982_d(new NBTTagCompound());
      }

      p_92115_0_.func_77978_p().func_74782_a("StoredEnchantments", nbttaglist);
   }

   public static ItemStack func_92111_a(EnchantmentData p_92111_0_) {
      ItemStack itemstack = new ItemStack(Items.field_151134_bR);
      func_92115_a(itemstack, p_92111_0_);
      return itemstack;
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (p_150895_1_ == CreativeTabs.field_78027_g) {
         for(Enchantment enchantment : Enchantment.field_185264_b) {
            if (enchantment.field_77351_y != null) {
               for(int i = enchantment.func_77319_d(); i <= enchantment.func_77325_b(); ++i) {
                  p_150895_2_.add(func_92111_a(new EnchantmentData(enchantment, i)));
               }
            }
         }
      } else if (p_150895_1_.func_111225_m().length != 0) {
         for(Enchantment enchantment1 : Enchantment.field_185264_b) {
            if (p_150895_1_.func_111226_a(enchantment1.field_77351_y)) {
               p_150895_2_.add(func_92111_a(new EnchantmentData(enchantment1, enchantment1.func_77325_b())));
            }
         }
      }

   }
}
