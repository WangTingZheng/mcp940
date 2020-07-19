package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class RecipesMapExtending extends ShapedRecipes {
   public RecipesMapExtending() {
      super("", 3, 3, NonNullList.func_193580_a(Ingredient.field_193370_a, Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193367_a(Items.field_151098_aY), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF), Ingredient.func_193368_a(Items.field_151121_aF)), new ItemStack(Items.field_151148_bJ));
   }

   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      if (!super.func_77569_a(p_77569_1_, p_77569_2_)) {
         return false;
      } else {
         ItemStack itemstack = ItemStack.field_190927_a;

         for(int i = 0; i < p_77569_1_.func_70302_i_() && itemstack.func_190926_b(); ++i) {
            ItemStack itemstack1 = p_77569_1_.func_70301_a(i);
            if (itemstack1.func_77973_b() == Items.field_151098_aY) {
               itemstack = itemstack1;
            }
         }

         if (itemstack.func_190926_b()) {
            return false;
         } else {
            MapData mapdata = Items.field_151098_aY.func_77873_a(itemstack, p_77569_2_);
            if (mapdata == null) {
               return false;
            } else if (this.func_190934_a(mapdata)) {
               return false;
            } else {
               return mapdata.field_76197_d < 4;
            }
         }
      }
   }

   private boolean func_190934_a(MapData p_190934_1_) {
      if (p_190934_1_.field_76203_h != null) {
         for(MapDecoration mapdecoration : p_190934_1_.field_76203_h.values()) {
            if (mapdecoration.func_191179_b() == MapDecoration.Type.MANSION || mapdecoration.func_191179_b() == MapDecoration.Type.MONUMENT) {
               return true;
            }
         }
      }

      return false;
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      ItemStack itemstack = ItemStack.field_190927_a;

      for(int i = 0; i < p_77572_1_.func_70302_i_() && itemstack.func_190926_b(); ++i) {
         ItemStack itemstack1 = p_77572_1_.func_70301_a(i);
         if (itemstack1.func_77973_b() == Items.field_151098_aY) {
            itemstack = itemstack1;
         }
      }

      itemstack = itemstack.func_77946_l();
      itemstack.func_190920_e(1);
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("map_scale_direction", 1);
      return itemstack;
   }

   public boolean func_192399_d() {
      return true;
   }
}
