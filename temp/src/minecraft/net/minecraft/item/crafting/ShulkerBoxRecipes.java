package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShulkerBoxRecipes {
   public static class ShulkerBoxColoring implements IRecipe {
      public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
         int i = 0;
         int j = 0;

         for(int k = 0; k < p_77569_1_.func_70302_i_(); ++k) {
            ItemStack itemstack = p_77569_1_.func_70301_a(k);
            if (!itemstack.func_190926_b()) {
               if (Block.func_149634_a(itemstack.func_77973_b()) instanceof BlockShulkerBox) {
                  ++i;
               } else {
                  if (itemstack.func_77973_b() != Items.field_151100_aR) {
                     return false;
                  }

                  ++j;
               }

               if (j > 1 || i > 1) {
                  return false;
               }
            }
         }

         return i == 1 && j == 1;
      }

      public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
         ItemStack itemstack = ItemStack.field_190927_a;
         ItemStack itemstack1 = ItemStack.field_190927_a;

         for(int i = 0; i < p_77572_1_.func_70302_i_(); ++i) {
            ItemStack itemstack2 = p_77572_1_.func_70301_a(i);
            if (!itemstack2.func_190926_b()) {
               if (Block.func_149634_a(itemstack2.func_77973_b()) instanceof BlockShulkerBox) {
                  itemstack = itemstack2;
               } else if (itemstack2.func_77973_b() == Items.field_151100_aR) {
                  itemstack1 = itemstack2;
               }
            }
         }

         ItemStack itemstack3 = BlockShulkerBox.func_190953_b(EnumDyeColor.func_176766_a(itemstack1.func_77960_j()));
         if (itemstack.func_77942_o()) {
            itemstack3.func_77982_d(itemstack.func_77978_p().func_74737_b());
         }

         return itemstack3;
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
}
