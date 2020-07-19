package net.minecraft.item.crafting;

import javax.annotation.Nullable;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesBanners {
   public static class RecipeAddPattern implements IRecipe {
      public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
         boolean flag = false;

         for(int i = 0; i < p_77569_1_.func_70302_i_(); ++i) {
            ItemStack itemstack = p_77569_1_.func_70301_a(i);
            if (itemstack.func_77973_b() == Items.field_179564_cE) {
               if (flag) {
                  return false;
               }

               if (TileEntityBanner.func_175113_c(itemstack) >= 6) {
                  return false;
               }

               flag = true;
            }
         }

         if (!flag) {
            return false;
         } else {
            return this.func_190933_c(p_77569_1_) != null;
         }
      }

      public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
         ItemStack itemstack = ItemStack.field_190927_a;

         for(int i = 0; i < p_77572_1_.func_70302_i_(); ++i) {
            ItemStack itemstack1 = p_77572_1_.func_70301_a(i);
            if (!itemstack1.func_190926_b() && itemstack1.func_77973_b() == Items.field_179564_cE) {
               itemstack = itemstack1.func_77946_l();
               itemstack.func_190920_e(1);
               break;
            }
         }

         BannerPattern bannerpattern = this.func_190933_c(p_77572_1_);
         if (bannerpattern != null) {
            int k = 0;

            for(int j = 0; j < p_77572_1_.func_70302_i_(); ++j) {
               ItemStack itemstack2 = p_77572_1_.func_70301_a(j);
               if (itemstack2.func_77973_b() == Items.field_151100_aR) {
                  k = itemstack2.func_77960_j();
                  break;
               }
            }

            NBTTagCompound nbttagcompound1 = itemstack.func_190925_c("BlockEntityTag");
            NBTTagList nbttaglist;
            if (nbttagcompound1.func_150297_b("Patterns", 9)) {
               nbttaglist = nbttagcompound1.func_150295_c("Patterns", 10);
            } else {
               nbttaglist = new NBTTagList();
               nbttagcompound1.func_74782_a("Patterns", nbttaglist);
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74778_a("Pattern", bannerpattern.func_190993_b());
            nbttagcompound.func_74768_a("Color", k);
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return itemstack;
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

      @Nullable
      private BannerPattern func_190933_c(InventoryCrafting p_190933_1_) {
         for(BannerPattern bannerpattern : BannerPattern.values()) {
            if (bannerpattern.func_191000_d()) {
               boolean flag = true;
               if (bannerpattern.func_190999_e()) {
                  boolean flag1 = false;
                  boolean flag2 = false;

                  for(int i = 0; i < p_190933_1_.func_70302_i_() && flag; ++i) {
                     ItemStack itemstack = p_190933_1_.func_70301_a(i);
                     if (!itemstack.func_190926_b() && itemstack.func_77973_b() != Items.field_179564_cE) {
                        if (itemstack.func_77973_b() == Items.field_151100_aR) {
                           if (flag2) {
                              flag = false;
                              break;
                           }

                           flag2 = true;
                        } else {
                           if (flag1 || !itemstack.func_77969_a(bannerpattern.func_190998_f())) {
                              flag = false;
                              break;
                           }

                           flag1 = true;
                        }
                     }
                  }

                  if (!flag1 || !flag2) {
                     flag = false;
                  }
               } else if (p_190933_1_.func_70302_i_() == bannerpattern.func_190996_c().length * bannerpattern.func_190996_c()[0].length()) {
                  int j = -1;

                  for(int k = 0; k < p_190933_1_.func_70302_i_() && flag; ++k) {
                     int l = k / 3;
                     int i1 = k % 3;
                     ItemStack itemstack1 = p_190933_1_.func_70301_a(k);
                     if (!itemstack1.func_190926_b() && itemstack1.func_77973_b() != Items.field_179564_cE) {
                        if (itemstack1.func_77973_b() != Items.field_151100_aR) {
                           flag = false;
                           break;
                        }

                        if (j != -1 && j != itemstack1.func_77960_j()) {
                           flag = false;
                           break;
                        }

                        if (bannerpattern.func_190996_c()[l].charAt(i1) == ' ') {
                           flag = false;
                           break;
                        }

                        j = itemstack1.func_77960_j();
                     } else if (bannerpattern.func_190996_c()[l].charAt(i1) != ' ') {
                        flag = false;
                        break;
                     }
                  }
               } else {
                  flag = false;
               }

               if (flag) {
                  return bannerpattern;
               }
            }
         }

         return null;
      }

      public boolean func_192399_d() {
         return true;
      }

      public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
         return p_194133_1_ >= 3 && p_194133_2_ >= 3;
      }
   }

   public static class RecipeDuplicatePattern implements IRecipe {
      public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_) {
         ItemStack itemstack = ItemStack.field_190927_a;
         ItemStack itemstack1 = ItemStack.field_190927_a;

         for(int i = 0; i < p_77569_1_.func_70302_i_(); ++i) {
            ItemStack itemstack2 = p_77569_1_.func_70301_a(i);
            if (!itemstack2.func_190926_b()) {
               if (itemstack2.func_77973_b() != Items.field_179564_cE) {
                  return false;
               }

               if (!itemstack.func_190926_b() && !itemstack1.func_190926_b()) {
                  return false;
               }

               EnumDyeColor enumdyecolor = ItemBanner.func_179225_h(itemstack2);
               boolean flag = TileEntityBanner.func_175113_c(itemstack2) > 0;
               if (!itemstack.func_190926_b()) {
                  if (flag) {
                     return false;
                  }

                  if (enumdyecolor != ItemBanner.func_179225_h(itemstack)) {
                     return false;
                  }

                  itemstack1 = itemstack2;
               } else if (!itemstack1.func_190926_b()) {
                  if (!flag) {
                     return false;
                  }

                  if (enumdyecolor != ItemBanner.func_179225_h(itemstack1)) {
                     return false;
                  }

                  itemstack = itemstack2;
               } else if (flag) {
                  itemstack = itemstack2;
               } else {
                  itemstack1 = itemstack2;
               }
            }
         }

         return !itemstack.func_190926_b() && !itemstack1.func_190926_b();
      }

      public ItemStack func_77572_b(InventoryCrafting p_77572_1_) {
         for(int i = 0; i < p_77572_1_.func_70302_i_(); ++i) {
            ItemStack itemstack = p_77572_1_.func_70301_a(i);
            if (!itemstack.func_190926_b() && TileEntityBanner.func_175113_c(itemstack) > 0) {
               ItemStack itemstack1 = itemstack.func_77946_l();
               itemstack1.func_190920_e(1);
               return itemstack1;
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
            if (!itemstack.func_190926_b()) {
               if (itemstack.func_77973_b().func_77634_r()) {
                  nonnulllist.set(i, new ItemStack(itemstack.func_77973_b().func_77668_q()));
               } else if (itemstack.func_77942_o() && TileEntityBanner.func_175113_c(itemstack) > 0) {
                  ItemStack itemstack1 = itemstack.func_77946_l();
                  itemstack1.func_190920_e(1);
                  nonnulllist.set(i, itemstack1);
               }
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
