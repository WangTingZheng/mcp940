package net.minecraft.inventory;

import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private static final Logger field_148326_f = LogManager.getLogger();
   private final IInventory field_82852_f;
   private final IInventory field_82853_g;
   private final World field_82860_h;
   private final BlockPos field_178156_j;
   public int field_82854_e;
   private int field_82856_l;
   private String field_82857_m;
   private final EntityPlayer field_82855_n;

   public ContainerRepair(InventoryPlayer p_i45806_1_, World p_i45806_2_, EntityPlayer p_i45806_3_) {
      this(p_i45806_1_, p_i45806_2_, BlockPos.field_177992_a, p_i45806_3_);
   }

   public ContainerRepair(InventoryPlayer p_i45807_1_, final World p_i45807_2_, final BlockPos p_i45807_3_, EntityPlayer p_i45807_4_) {
      this.field_82852_f = new InventoryCraftResult();
      this.field_82853_g = new InventoryBasic("Repair", true, 2) {
         public void func_70296_d() {
            super.func_70296_d();
            ContainerRepair.this.func_75130_a(this);
         }
      };
      this.field_178156_j = p_i45807_3_;
      this.field_82860_h = p_i45807_2_;
      this.field_82855_n = p_i45807_4_;
      this.func_75146_a(new Slot(this.field_82853_g, 0, 27, 47));
      this.func_75146_a(new Slot(this.field_82853_g, 1, 76, 47));
      this.func_75146_a(new Slot(this.field_82852_f, 2, 134, 47) {
         public boolean func_75214_a(ItemStack p_75214_1_) {
            return false;
         }

         public boolean func_82869_a(EntityPlayer p_82869_1_) {
            return (p_82869_1_.field_71075_bZ.field_75098_d || p_82869_1_.field_71068_ca >= ContainerRepair.this.field_82854_e) && ContainerRepair.this.field_82854_e > 0 && this.func_75216_d();
         }

         public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
            if (!p_190901_1_.field_71075_bZ.field_75098_d) {
               p_190901_1_.func_82242_a(-ContainerRepair.this.field_82854_e);
            }

            ContainerRepair.this.field_82853_g.func_70299_a(0, ItemStack.field_190927_a);
            if (ContainerRepair.this.field_82856_l > 0) {
               ItemStack itemstack = ContainerRepair.this.field_82853_g.func_70301_a(1);
               if (!itemstack.func_190926_b() && itemstack.func_190916_E() > ContainerRepair.this.field_82856_l) {
                  itemstack.func_190918_g(ContainerRepair.this.field_82856_l);
                  ContainerRepair.this.field_82853_g.func_70299_a(1, itemstack);
               } else {
                  ContainerRepair.this.field_82853_g.func_70299_a(1, ItemStack.field_190927_a);
               }
            } else {
               ContainerRepair.this.field_82853_g.func_70299_a(1, ItemStack.field_190927_a);
            }

            ContainerRepair.this.field_82854_e = 0;
            IBlockState iblockstate = p_i45807_2_.func_180495_p(p_i45807_3_);
            if (!p_190901_1_.field_71075_bZ.field_75098_d && !p_i45807_2_.field_72995_K && iblockstate.func_177230_c() == Blocks.field_150467_bQ && p_190901_1_.func_70681_au().nextFloat() < 0.12F) {
               int l = ((Integer)iblockstate.func_177229_b(BlockAnvil.field_176505_b)).intValue();
               ++l;
               if (l > 2) {
                  p_i45807_2_.func_175698_g(p_i45807_3_);
                  p_i45807_2_.func_175718_b(1029, p_i45807_3_, 0);
               } else {
                  p_i45807_2_.func_180501_a(p_i45807_3_, iblockstate.func_177226_a(BlockAnvil.field_176505_b, Integer.valueOf(l)), 2);
                  p_i45807_2_.func_175718_b(1030, p_i45807_3_, 0);
               }
            } else if (!p_i45807_2_.field_72995_K) {
               p_i45807_2_.func_175718_b(1030, p_i45807_3_, 0);
            }

            return p_190901_2_;
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(p_i45807_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.func_75146_a(new Slot(p_i45807_1_, k, 8 + k * 18, 142));
      }

   }

   public void func_75130_a(IInventory p_75130_1_) {
      super.func_75130_a(p_75130_1_);
      if (p_75130_1_ == this.field_82853_g) {
         this.func_82848_d();
      }

   }

   public void func_82848_d() {
      ItemStack itemstack = this.field_82853_g.func_70301_a(0);
      this.field_82854_e = 1;
      int i = 0;
      int j = 0;
      int k = 0;
      if (itemstack.func_190926_b()) {
         this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
         this.field_82854_e = 0;
      } else {
         ItemStack itemstack1 = itemstack.func_77946_l();
         ItemStack itemstack2 = this.field_82853_g.func_70301_a(1);
         Map<Enchantment, Integer> map = EnchantmentHelper.func_82781_a(itemstack1);
         j = j + itemstack.func_82838_A() + (itemstack2.func_190926_b() ? 0 : itemstack2.func_82838_A());
         this.field_82856_l = 0;
         if (!itemstack2.func_190926_b()) {
            boolean flag = itemstack2.func_77973_b() == Items.field_151134_bR && !ItemEnchantedBook.func_92110_g(itemstack2).func_82582_d();
            if (itemstack1.func_77984_f() && itemstack1.func_77973_b().func_82789_a(itemstack, itemstack2)) {
               int l2 = Math.min(itemstack1.func_77952_i(), itemstack1.func_77958_k() / 4);
               if (l2 <= 0) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }

               int i3;
               for(i3 = 0; l2 > 0 && i3 < itemstack2.func_190916_E(); ++i3) {
                  int j3 = itemstack1.func_77952_i() - l2;
                  itemstack1.func_77964_b(j3);
                  ++i;
                  l2 = Math.min(itemstack1.func_77952_i(), itemstack1.func_77958_k() / 4);
               }

               this.field_82856_l = i3;
            } else {
               if (!flag && (itemstack1.func_77973_b() != itemstack2.func_77973_b() || !itemstack1.func_77984_f())) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }

               if (itemstack1.func_77984_f() && !flag) {
                  int l = itemstack.func_77958_k() - itemstack.func_77952_i();
                  int i1 = itemstack2.func_77958_k() - itemstack2.func_77952_i();
                  int j1 = i1 + itemstack1.func_77958_k() * 12 / 100;
                  int k1 = l + j1;
                  int l1 = itemstack1.func_77958_k() - k1;
                  if (l1 < 0) {
                     l1 = 0;
                  }

                  if (l1 < itemstack1.func_77960_j()) {
                     itemstack1.func_77964_b(l1);
                     i += 2;
                  }
               }

               Map<Enchantment, Integer> map1 = EnchantmentHelper.func_82781_a(itemstack2);
               boolean flag2 = false;
               boolean flag3 = false;

               for(Enchantment enchantment1 : map1.keySet()) {
                  if (enchantment1 != null) {
                     int i2 = map.containsKey(enchantment1) ? ((Integer)map.get(enchantment1)).intValue() : 0;
                     int j2 = ((Integer)map1.get(enchantment1)).intValue();
                     j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                     boolean flag1 = enchantment1.func_92089_a(itemstack);
                     if (this.field_82855_n.field_71075_bZ.field_75098_d || itemstack.func_77973_b() == Items.field_151134_bR) {
                        flag1 = true;
                     }

                     for(Enchantment enchantment : map.keySet()) {
                        if (enchantment != enchantment1 && !enchantment1.func_191560_c(enchantment)) {
                           flag1 = false;
                           ++i;
                        }
                     }

                     if (!flag1) {
                        flag3 = true;
                     } else {
                        flag2 = true;
                        if (j2 > enchantment1.func_77325_b()) {
                           j2 = enchantment1.func_77325_b();
                        }

                        map.put(enchantment1, Integer.valueOf(j2));
                        int k3 = 0;
                        switch(enchantment1.func_77324_c()) {
                        case COMMON:
                           k3 = 1;
                           break;
                        case UNCOMMON:
                           k3 = 2;
                           break;
                        case RARE:
                           k3 = 4;
                           break;
                        case VERY_RARE:
                           k3 = 8;
                        }

                        if (flag) {
                           k3 = Math.max(1, k3 / 2);
                        }

                        i += k3 * j2;
                        if (itemstack.func_190916_E() > 1) {
                           i = 40;
                        }
                     }
                  }
               }

               if (flag3 && !flag2) {
                  this.field_82852_f.func_70299_a(0, ItemStack.field_190927_a);
                  this.field_82854_e = 0;
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.field_82857_m)) {
            if (itemstack.func_82837_s()) {
               k = 1;
               i += k;
               itemstack1.func_135074_t();
            }
         } else if (!this.field_82857_m.equals(itemstack.func_82833_r())) {
            k = 1;
            i += k;
            itemstack1.func_151001_c(this.field_82857_m);
         }

         this.field_82854_e = j + i;
         if (i <= 0) {
            itemstack1 = ItemStack.field_190927_a;
         }

         if (k == i && k > 0 && this.field_82854_e >= 40) {
            this.field_82854_e = 39;
         }

         if (this.field_82854_e >= 40 && !this.field_82855_n.field_71075_bZ.field_75098_d) {
            itemstack1 = ItemStack.field_190927_a;
         }

         if (!itemstack1.func_190926_b()) {
            int k2 = itemstack1.func_82838_A();
            if (!itemstack2.func_190926_b() && k2 < itemstack2.func_82838_A()) {
               k2 = itemstack2.func_82838_A();
            }

            if (k != i || k == 0) {
               k2 = k2 * 2 + 1;
            }

            itemstack1.func_82841_c(k2);
            EnchantmentHelper.func_82782_a(map, itemstack1);
         }

         this.field_82852_f.func_70299_a(0, itemstack1);
         this.func_75142_b();
      }
   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      super.func_75132_a(p_75132_1_);
      p_75132_1_.func_71112_a(this, 0, this.field_82854_e);
   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
      if (p_75137_1_ == 0) {
         this.field_82854_e = p_75137_2_;
      }

   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      if (!this.field_82860_h.field_72995_K) {
         this.func_193327_a(p_75134_1_, this.field_82860_h, this.field_82853_g);
      }
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      if (this.field_82860_h.func_180495_p(this.field_178156_j).func_177230_c() != Blocks.field_150467_bQ) {
         return false;
      } else {
         return p_75145_1_.func_70092_e((double)this.field_178156_j.func_177958_n() + 0.5D, (double)this.field_178156_j.func_177956_o() + 0.5D, (double)this.field_178156_j.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ == 2) {
            if (!this.func_75135_a(itemstack1, 3, 39, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (p_82846_2_ != 0 && p_82846_2_ != 1) {
            if (p_82846_2_ >= 3 && p_82846_2_ < 39 && !this.func_75135_a(itemstack1, 0, 2, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 3, 39, false)) {
            return ItemStack.field_190927_a;
         }

         if (itemstack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         slot.func_190901_a(p_82846_1_, itemstack1);
      }

      return itemstack;
   }

   public void func_82850_a(String p_82850_1_) {
      this.field_82857_m = p_82850_1_;
      if (this.func_75139_a(2).func_75216_d()) {
         ItemStack itemstack = this.func_75139_a(2).func_75211_c();
         if (StringUtils.isBlank(p_82850_1_)) {
            itemstack.func_135074_t();
         } else {
            itemstack.func_151001_c(this.field_82857_m);
         }
      }

      this.func_82848_d();
   }
}
