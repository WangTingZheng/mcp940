package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesArmorDyes implements IRecipe {
   public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      List<ItemStack> list = Lists.<ItemStack>newArrayList();

      for(int i = 0; i < p_77569_1_.func_70302_i_(); ++i) {
         ItemStack itemstack1 = p_77569_1_.func_70301_a(i);
         if (!itemstack1.func_190926_b()) {
            if (itemstack1.func_77973_b() instanceof ItemArmor) {
               ItemArmor itemarmor = (ItemArmor)itemstack1.func_77973_b();
               if (itemarmor.func_82812_d() != ItemArmor.ArmorMaterial.LEATHER || !itemstack.func_190926_b()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (itemstack1.func_77973_b() != Items.field_151100_aR) {
                  return false;
               }

               list.add(itemstack1);
            }
         }
      }

      return !itemstack.func_190926_b() && !list.isEmpty();
   }

   public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      int[] aint = new int[3];
      int i = 0;
      int j = 0;
      ItemArmor itemarmor = null;

      for(int k = 0; k < p_77572_1_.func_70302_i_(); ++k) {
         ItemStack itemstack1 = p_77572_1_.func_70301_a(k);
         if (!itemstack1.func_190926_b()) {
            if (itemstack1.func_77973_b() instanceof ItemArmor) {
               itemarmor = (ItemArmor)itemstack1.func_77973_b();
               if (itemarmor.func_82812_d() != ItemArmor.ArmorMaterial.LEATHER || !itemstack.func_190926_b()) {
                  return ItemStack.field_190927_a;
               }

               itemstack = itemstack1.func_77946_l();
               itemstack.func_190920_e(1);
               if (itemarmor.func_82816_b_(itemstack1)) {
                  int l = itemarmor.func_82814_b(itemstack);
                  float f = (float)(l >> 16 & 255) / 255.0F;
                  float f1 = (float)(l >> 8 & 255) / 255.0F;
                  float f2 = (float)(l & 255) / 255.0F;
                  i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                  aint[0] = (int)((float)aint[0] + f * 255.0F);
                  aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                  aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                  ++j;
               }
            } else {
               if (itemstack1.func_77973_b() != Items.field_151100_aR) {
                  return ItemStack.field_190927_a;
               }

               float[] afloat = EnumDyeColor.func_176766_a(itemstack1.func_77960_j()).func_193349_f();
               int l1 = (int)(afloat[0] * 255.0F);
               int i2 = (int)(afloat[1] * 255.0F);
               int j2 = (int)(afloat[2] * 255.0F);
               i += Math.max(l1, Math.max(i2, j2));
               aint[0] += l1;
               aint[1] += i2;
               aint[2] += j2;
               ++j;
            }
         }
      }

      if (itemarmor == null) {
         return ItemStack.field_190927_a;
      } else {
         int i1 = aint[0] / j;
         int j1 = aint[1] / j;
         int k1 = aint[2] / j;
         float f3 = (float)i / (float)j;
         float f4 = (float)Math.max(i1, Math.max(j1, k1));
         i1 = (int)((float)i1 * f3 / f4);
         j1 = (int)((float)j1 * f3 / f4);
         k1 = (int)((float)k1 * f3 / f4);
         int k2 = (i1 << 8) + j1;
         k2 = (k2 << 8) + k1;
         itemarmor.func_82813_b(itemstack, k2);
         return itemstack;
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
      return p_194133_1_ * p_194133_2_ >= 2;
   }
}
