package net.minecraft.client.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class RecipeBookClient extends RecipeBook {
   public static final Map<CreativeTabs, List<RecipeList>> field_194086_e = Maps.<CreativeTabs, List<RecipeList>>newHashMap();
   public static final List<RecipeList> field_194087_f = Lists.<RecipeList>newArrayList();

   private static RecipeList func_194082_a(CreativeTabs p_194082_0_) {
      RecipeList recipelist = new RecipeList();
      field_194087_f.add(recipelist);
      (field_194086_e.computeIfAbsent(p_194082_0_, (p_194085_0_) -> {
         return new ArrayList();
      })).add(recipelist);
      (field_194086_e.computeIfAbsent(CreativeTabs.field_78027_g, (p_194083_0_) -> {
         return new ArrayList();
      })).add(recipelist);
      return recipelist;
   }

   private static CreativeTabs func_194084_a(ItemStack p_194084_0_) {
      CreativeTabs creativetabs = p_194084_0_.func_77973_b().func_77640_w();
      if (creativetabs != CreativeTabs.field_78030_b && creativetabs != CreativeTabs.field_78040_i && creativetabs != CreativeTabs.field_78028_d) {
         return creativetabs == CreativeTabs.field_78037_j ? CreativeTabs.field_78040_i : CreativeTabs.field_78026_f;
      } else {
         return creativetabs;
      }
   }

   static {
      Table<CreativeTabs, String, RecipeList> table = HashBasedTable.<CreativeTabs, String, RecipeList>create();

      for(IRecipe irecipe : CraftingManager.field_193380_a) {
         if (!irecipe.func_192399_d()) {
            CreativeTabs creativetabs = func_194084_a(irecipe.func_77571_b());
            String s = irecipe.func_193358_e();
            RecipeList recipelist1;
            if (s.isEmpty()) {
               recipelist1 = func_194082_a(creativetabs);
            } else {
               recipelist1 = table.get(creativetabs, s);
               if (recipelist1 == null) {
                  recipelist1 = func_194082_a(creativetabs);
                  table.put(creativetabs, s, recipelist1);
               }
            }

            recipelist1.func_192709_a(irecipe);
         }
      }

   }
}
