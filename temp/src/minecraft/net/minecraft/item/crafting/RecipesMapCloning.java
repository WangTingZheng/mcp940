package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesMapCloning implements IRecipe {
   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      int i = 0;
      ItemStack itemstack = ItemStack.field_190927_a;

      for(int j = 0; j < p_77569_1_.func_70302_i_(); ++j) {
         ItemStack itemstack1 = p_77569_1_.func_70301_a(j);
         if (!itemstack1.func_190926_b()) {
            if (itemstack1.func_77973_b() == Items.field_151098_aY) {
               if (!itemstack.func_190926_b()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (itemstack1.func_77973_b() != Items.field_151148_bJ) {
                  return false;
               }

               ++i;
            }
         }
      }

      return !itemstack.func_190926_b() && i > 0;
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      int i = 0;
      ItemStack itemstack = ItemStack.field_190927_a;

      for(int j = 0; j < p_77572_1_.func_70302_i_(); ++j) {
         ItemStack itemstack1 = p_77572_1_.func_70301_a(j);
         if (!itemstack1.func_190926_b()) {
            if (itemstack1.func_77973_b() == Items.field_151098_aY) {
               if (!itemstack.func_190926_b()) {
                  return ItemStack.field_190927_a;
               }

               itemstack = itemstack1;
            } else {
               if (itemstack1.func_77973_b() != Items.field_151148_bJ) {
                  return ItemStack.field_190927_a;
               }

               ++i;
            }
         }
      }

      if (!itemstack.func_190926_b() && i >= 1) {
         ItemStack itemstack2 = new ItemStack(Items.field_151098_aY, i + 1, itemstack.func_77960_j());
         if (itemstack.func_82837_s()) {
            itemstack2.func_151001_c(itemstack.func_82833_r());
         }

         if (itemstack.func_77942_o()) {
            itemstack2.func_77982_d(itemstack.func_77978_p());
         }

         return itemstack2;
      } else {
         return ItemStack.field_190927_a;
      }
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
      return p_194133_1_ >= 3 && p_194133_2_ >= 3;
   }
}
