package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeTippedArrow implements IRecipe {
   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      if (p_77569_1_.func_174922_i() == 3 && p_77569_1_.func_174923_h() == 3) {
         for(int i = 0; i < p_77569_1_.func_174922_i(); ++i) {
            for(int j = 0; j < p_77569_1_.func_174923_h(); ++j) {
               ItemStack itemstack = p_77569_1_.func_70463_b(i, j);
               if (itemstack.func_190926_b()) {
                  return false;
               }

               Item item = itemstack.func_77973_b();
               if (i == 1 && j == 1) {
                  if (item != Items.field_185156_bI) {
                     return false;
                  }
               } else if (item != Items.field_151032_g) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      ItemStack itemstack = p_77572_1_.func_70463_b(1, 1);
      if (itemstack.func_77973_b() != Items.field_185156_bI) {
         return ItemStack.field_190927_a;
      } else {
         ItemStack itemstack1 = new ItemStack(Items.field_185167_i, 8);
         PotionUtils.func_185188_a(itemstack1, PotionUtils.func_185191_c(itemstack));
         PotionUtils.func_185184_a(itemstack1, PotionUtils.func_185190_b(itemstack));
         return itemstack1;
      }
   }

   public ItemStack func_77571_b() {
      return ItemStack.field_190927_a;
   }

   public NonNullList<ItemStack> func_179532_b(InventoryCrafting p_179532_1_) {
      return NonNullList.<ItemStack>func_191197_a(p_179532_1_.func_70302_i_(), ItemStack.field_190927_a);
   }

   public boolean func_192399_d() {
      return true;
   }

   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
      return p_194133_1_ >= 2 && p_194133_2_ >= 2;
   }
}
