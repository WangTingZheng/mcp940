package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeRepairItem implements IRecipe {
   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      List<ItemStack> list = Lists.<ItemStack>newArrayList();

      for(int i = 0; i < p_77569_1_.func_70302_i_(); ++i) {
         ItemStack itemstack = p_77569_1_.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            list.add(itemstack);
            if (list.size() > 1) {
               ItemStack itemstack1 = list.get(0);
               if (itemstack.func_77973_b() != itemstack1.func_77973_b() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.func_77973_b().func_77645_m()) {
                  return false;
               }
            }
         }
      }

      return list.size() == 2;
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      List<ItemStack> list = Lists.<ItemStack>newArrayList();

      for(int i = 0; i < p_77572_1_.func_70302_i_(); ++i) {
         ItemStack itemstack = p_77572_1_.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            list.add(itemstack);
            if (list.size() > 1) {
               ItemStack itemstack1 = list.get(0);
               if (itemstack.func_77973_b() != itemstack1.func_77973_b() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.func_77973_b().func_77645_m()) {
                  return ItemStack.field_190927_a;
               }
            }
         }
      }

      if (list.size() == 2) {
         ItemStack itemstack2 = list.get(0);
         ItemStack itemstack3 = list.get(1);
         if (itemstack2.func_77973_b() == itemstack3.func_77973_b() && itemstack2.func_190916_E() == 1 && itemstack3.func_190916_E() == 1 && itemstack2.func_77973_b().func_77645_m()) {
            Item item = itemstack2.func_77973_b();
            int j = item.func_77612_l() - itemstack2.func_77952_i();
            int k = item.func_77612_l() - itemstack3.func_77952_i();
            int l = j + k + item.func_77612_l() * 5 / 100;
            int i1 = item.func_77612_l() - l;
            if (i1 < 0) {
               i1 = 0;
            }

            return new ItemStack(itemstack2.func_77973_b(), 1, i1);
         }
      }

      return ItemStack.field_190927_a;
   }

   public ItemStack func_77571_b() {
      return ItemStack.field_190927_a;
   }

   public NonNullList<ItemStack> func_179532_b(InventoryCrafting p_179532_1_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>func_191197_a(p_179532_1_.func_70302_i_(), ItemStack.field_190927_a);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = p_179532_1_.func_70301_a(i);
         if (itemstack.func_77973_b().func_77634_r()) {
            nonnulllist.set(i, new ItemStack(itemstack.func_77973_b().func_77668_q()));
         }
      }

      return nonnulllist;
   }

   public boolean func_192399_d() {
      return true;
   }

   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
      return p_194133_1_ * p_194133_2_ >= 2;
   }
}
