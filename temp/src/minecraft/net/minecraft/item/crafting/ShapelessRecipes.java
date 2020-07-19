package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShapelessRecipes implements IRecipe {
   private final ItemStack field_77580_a;
   private final NonNullList<Ingredient> field_77579_b;
   private final String field_194138_c;

   public ShapelessRecipes(String p_i47500_1_, ItemStack p_i47500_2_, NonNullList<Ingredient> p_i47500_3_) {
      this.field_194138_c = p_i47500_1_;
      this.field_77580_a = p_i47500_2_;
      this.field_77579_b = p_i47500_3_;
   }

   public String func_193358_e() {
      return this.field_194138_c;
   }

   public ItemStack func_77571_b() {
      return this.field_77580_a;
   }

   public NonNullList<Ingredient> func_192400_c() {
      return this.field_77579_b;
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

   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      List<Ingredient> list = Lists.newArrayList(this.field_77579_b);

      for(int i = 0; i < p_77569_1_.func_174923_h(); ++i) {
         for(int j = 0; j < p_77569_1_.func_174922_i(); ++j) {
            ItemStack itemstack = p_77569_1_.func_70463_b(j, i);
            if (!itemstack.func_190926_b()) {
               boolean flag = false;

               for(Ingredient ingredient : list) {
                  if (ingredient.apply(itemstack)) {
                     flag = true;
                     list.remove(ingredient);
                     break;
                  }
               }

               if (!flag) {
                  return false;
               }
            }
         }
      }

      return list.isEmpty();
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      return this.field_77580_a.func_77946_l();
   }

   public static ShapelessRecipes func_193363_a(JsonObject p_193363_0_) {
      String s = JsonUtils.func_151219_a(p_193363_0_, "group", "");
      NonNullList<Ingredient> nonnulllist = func_193364_a(JsonUtils.func_151214_t(p_193363_0_, "ingredients"));
      if (nonnulllist.isEmpty()) {
         throw new JsonParseException("No ingredients for shapeless recipe");
      } else if (nonnulllist.size() > 9) {
         throw new JsonParseException("Too many ingredients for shapeless recipe");
      } else {
         ItemStack itemstack = ShapedRecipes.func_192405_a(JsonUtils.func_152754_s(p_193363_0_, "result"), true);
         return new ShapelessRecipes(s, itemstack, nonnulllist);
      }
   }

   private static NonNullList<Ingredient> func_193364_a(JsonArray p_193364_0_) {
      NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>func_191196_a();

      for(int i = 0; i < p_193364_0_.size(); ++i) {
         Ingredient ingredient = ShapedRecipes.func_193361_a(p_193364_0_.get(i));
         if (ingredient != Ingredient.field_193370_a) {
            nonnulllist.add(ingredient);
         }
      }

      return nonnulllist;
   }

   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
      return p_194133_1_ * p_194133_2_ >= this.field_77579_b.size();
   }
}
