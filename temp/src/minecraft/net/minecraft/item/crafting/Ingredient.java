package net.minecraft.item.crafting;

import com.google.common.base.Predicate;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import javax.annotation.Nullable;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient implements Predicate<ItemStack> {
   public static final Ingredient field_193370_a = new Ingredient(new ItemStack[0]) {
      public boolean apply(@Nullable ItemStack p_apply_1_) {
         return p_apply_1_.func_190926_b();
      }
   };
   private final ItemStack[] field_193371_b;
   private IntList field_194140_c;

   private Ingredient(ItemStack... p_i47503_1_) {
      this.field_193371_b = p_i47503_1_;
   }

   public ItemStack[] func_193365_a() {
      return this.field_193371_b;
   }

   public boolean apply(@Nullable ItemStack p_apply_1_) {
      if (p_apply_1_ == null) {
         return false;
      } else {
         for(ItemStack itemstack : this.field_193371_b) {
            if (itemstack.func_77973_b() == p_apply_1_.func_77973_b()) {
               int i = itemstack.func_77960_j();
               if (i == 32767 || i == p_apply_1_.func_77960_j()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public IntList func_194139_b() {
      if (this.field_194140_c == null) {
         this.field_194140_c = new IntArrayList(this.field_193371_b.length);

         for(ItemStack itemstack : this.field_193371_b) {
            this.field_194140_c.add(RecipeItemHelper.func_194113_b(itemstack));
         }

         this.field_194140_c.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.field_194140_c;
   }

   public static Ingredient func_193367_a(Item p_193367_0_) {
      return func_193369_a(new ItemStack(p_193367_0_, 1, 32767));
   }

   public static Ingredient func_193368_a(Item... p_193368_0_) {
      ItemStack[] aitemstack = new ItemStack[p_193368_0_.length];

      for(int i = 0; i < p_193368_0_.length; ++i) {
         aitemstack[i] = new ItemStack(p_193368_0_[i]);
      }

      return func_193369_a(aitemstack);
   }

   public static Ingredient func_193369_a(ItemStack... p_193369_0_) {
      if (p_193369_0_.length > 0) {
         for(ItemStack itemstack : p_193369_0_) {
            if (!itemstack.func_190926_b()) {
               return new Ingredient(p_193369_0_);
            }
         }
      }

      return field_193370_a;
   }
}
