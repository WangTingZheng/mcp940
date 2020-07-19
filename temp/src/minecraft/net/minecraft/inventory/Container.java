package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class Container {
   public NonNullList<ItemStack> field_75153_a = NonNullList.<ItemStack>func_191196_a();
   public List<Slot> field_75151_b = Lists.<Slot>newArrayList();
   public int field_75152_c;
   private short field_75150_e;
   private int field_94535_f = -1;
   private int field_94536_g;
   private final Set<Slot> field_94537_h = Sets.<Slot>newHashSet();
   protected List<IContainerListener> field_75149_d = Lists.<IContainerListener>newArrayList();
   private final Set<EntityPlayer> field_75148_f = Sets.<EntityPlayer>newHashSet();

   protected Slot func_75146_a(Slot p_75146_1_) {
      p_75146_1_.field_75222_d = this.field_75151_b.size();
      this.field_75151_b.add(p_75146_1_);
      this.field_75153_a.add(ItemStack.field_190927_a);
      return p_75146_1_;
   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      if (this.field_75149_d.contains(p_75132_1_)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.field_75149_d.add(p_75132_1_);
         p_75132_1_.func_71110_a(this, this.func_75138_a());
         this.func_75142_b();
      }
   }

   public void func_82847_b(IContainerListener p_82847_1_) {
      this.field_75149_d.remove(p_82847_1_);
   }

   public NonNullList<ItemStack> func_75138_a() {
      NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>func_191196_a();

      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         nonnulllist.add(((Slot)this.field_75151_b.get(i)).func_75211_c());
      }

      return nonnulllist;
   }

   public void func_75142_b() {
      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         ItemStack itemstack = ((Slot)this.field_75151_b.get(i)).func_75211_c();
         ItemStack itemstack1 = this.field_75153_a.get(i);
         if (!ItemStack.func_77989_b(itemstack1, itemstack)) {
            itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.func_77946_l();
            this.field_75153_a.set(i, itemstack1);

            for(int j = 0; j < this.field_75149_d.size(); ++j) {
               ((IContainerListener)this.field_75149_d.get(j)).func_71111_a(this, i, itemstack1);
            }
         }
      }

   }

   public boolean func_75140_a(EntityPlayer p_75140_1_, int p_75140_2_) {
      return false;
   }

   @Nullable
   public Slot func_75147_a(IInventory p_75147_1_, int p_75147_2_) {
      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         Slot slot = this.field_75151_b.get(i);
         if (slot.func_75217_a(p_75147_1_, p_75147_2_)) {
            return slot;
         }
      }

      return null;
   }

   public Slot func_75139_a(int p_75139_1_) {
      return this.field_75151_b.get(p_75139_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      Slot slot = this.field_75151_b.get(p_82846_2_);
      return slot != null ? slot.func_75211_c() : ItemStack.field_190927_a;
   }

   public ItemStack func_184996_a(int p_184996_1_, int p_184996_2_, ClickType p_184996_3_, EntityPlayer p_184996_4_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      InventoryPlayer inventoryplayer = p_184996_4_.field_71071_by;
      if (p_184996_3_ == ClickType.QUICK_CRAFT) {
         int j1 = this.field_94536_g;
         this.field_94536_g = func_94532_c(p_184996_2_);
         if ((j1 != 1 || this.field_94536_g != 2) && j1 != this.field_94536_g) {
            this.func_94533_d();
         } else if (inventoryplayer.func_70445_o().func_190926_b()) {
            this.func_94533_d();
         } else if (this.field_94536_g == 0) {
            this.field_94535_f = func_94529_b(p_184996_2_);
            if (func_180610_a(this.field_94535_f, p_184996_4_)) {
               this.field_94536_g = 1;
               this.field_94537_h.clear();
            } else {
               this.func_94533_d();
            }
         } else if (this.field_94536_g == 1) {
            Slot slot7 = this.field_75151_b.get(p_184996_1_);
            ItemStack itemstack12 = inventoryplayer.func_70445_o();
            if (slot7 != null && func_94527_a(slot7, itemstack12, true) && slot7.func_75214_a(itemstack12) && (this.field_94535_f == 2 || itemstack12.func_190916_E() > this.field_94537_h.size()) && this.func_94531_b(slot7)) {
               this.field_94537_h.add(slot7);
            }
         } else if (this.field_94536_g == 2) {
            if (!this.field_94537_h.isEmpty()) {
               ItemStack itemstack9 = inventoryplayer.func_70445_o().func_77946_l();
               int k1 = inventoryplayer.func_70445_o().func_190916_E();

               for(Slot slot8 : this.field_94537_h) {
                  ItemStack itemstack13 = inventoryplayer.func_70445_o();
                  if (slot8 != null && func_94527_a(slot8, itemstack13, true) && slot8.func_75214_a(itemstack13) && (this.field_94535_f == 2 || itemstack13.func_190916_E() >= this.field_94537_h.size()) && this.func_94531_b(slot8)) {
                     ItemStack itemstack14 = itemstack9.func_77946_l();
                     int j3 = slot8.func_75216_d() ? slot8.func_75211_c().func_190916_E() : 0;
                     func_94525_a(this.field_94537_h, this.field_94535_f, itemstack14, j3);
                     int k3 = Math.min(itemstack14.func_77976_d(), slot8.func_178170_b(itemstack14));
                     if (itemstack14.func_190916_E() > k3) {
                        itemstack14.func_190920_e(k3);
                     }

                     k1 -= itemstack14.func_190916_E() - j3;
                     slot8.func_75215_d(itemstack14);
                  }
               }

               itemstack9.func_190920_e(k1);
               inventoryplayer.func_70437_b(itemstack9);
            }

            this.func_94533_d();
         } else {
            this.func_94533_d();
         }
      } else if (this.field_94536_g != 0) {
         this.func_94533_d();
      } else if ((p_184996_3_ == ClickType.PICKUP || p_184996_3_ == ClickType.QUICK_MOVE) && (p_184996_2_ == 0 || p_184996_2_ == 1)) {
         if (p_184996_1_ == -999) {
            if (!inventoryplayer.func_70445_o().func_190926_b()) {
               if (p_184996_2_ == 0) {
                  p_184996_4_.func_71019_a(inventoryplayer.func_70445_o(), true);
                  inventoryplayer.func_70437_b(ItemStack.field_190927_a);
               }

               if (p_184996_2_ == 1) {
                  p_184996_4_.func_71019_a(inventoryplayer.func_70445_o().func_77979_a(1), true);
               }
            }
         } else if (p_184996_3_ == ClickType.QUICK_MOVE) {
            if (p_184996_1_ < 0) {
               return ItemStack.field_190927_a;
            }

            Slot slot5 = this.field_75151_b.get(p_184996_1_);
            if (slot5 == null || !slot5.func_82869_a(p_184996_4_)) {
               return ItemStack.field_190927_a;
            }

            for(ItemStack itemstack7 = this.func_82846_b(p_184996_4_, p_184996_1_); !itemstack7.func_190926_b() && ItemStack.func_179545_c(slot5.func_75211_c(), itemstack7); itemstack7 = this.func_82846_b(p_184996_4_, p_184996_1_)) {
               itemstack = itemstack7.func_77946_l();
            }
         } else {
            if (p_184996_1_ < 0) {
               return ItemStack.field_190927_a;
            }

            Slot slot6 = this.field_75151_b.get(p_184996_1_);
            if (slot6 != null) {
               ItemStack itemstack8 = slot6.func_75211_c();
               ItemStack itemstack11 = inventoryplayer.func_70445_o();
               if (!itemstack8.func_190926_b()) {
                  itemstack = itemstack8.func_77946_l();
               }

               if (itemstack8.func_190926_b()) {
                  if (!itemstack11.func_190926_b() && slot6.func_75214_a(itemstack11)) {
                     int i3 = p_184996_2_ == 0 ? itemstack11.func_190916_E() : 1;
                     if (i3 > slot6.func_178170_b(itemstack11)) {
                        i3 = slot6.func_178170_b(itemstack11);
                     }

                     slot6.func_75215_d(itemstack11.func_77979_a(i3));
                  }
               } else if (slot6.func_82869_a(p_184996_4_)) {
                  if (itemstack11.func_190926_b()) {
                     if (itemstack8.func_190926_b()) {
                        slot6.func_75215_d(ItemStack.field_190927_a);
                        inventoryplayer.func_70437_b(ItemStack.field_190927_a);
                     } else {
                        int l2 = p_184996_2_ == 0 ? itemstack8.func_190916_E() : (itemstack8.func_190916_E() + 1) / 2;
                        inventoryplayer.func_70437_b(slot6.func_75209_a(l2));
                        if (itemstack8.func_190926_b()) {
                           slot6.func_75215_d(ItemStack.field_190927_a);
                        }

                        slot6.func_190901_a(p_184996_4_, inventoryplayer.func_70445_o());
                     }
                  } else if (slot6.func_75214_a(itemstack11)) {
                     if (itemstack8.func_77973_b() == itemstack11.func_77973_b() && itemstack8.func_77960_j() == itemstack11.func_77960_j() && ItemStack.func_77970_a(itemstack8, itemstack11)) {
                        int k2 = p_184996_2_ == 0 ? itemstack11.func_190916_E() : 1;
                        if (k2 > slot6.func_178170_b(itemstack11) - itemstack8.func_190916_E()) {
                           k2 = slot6.func_178170_b(itemstack11) - itemstack8.func_190916_E();
                        }

                        if (k2 > itemstack11.func_77976_d() - itemstack8.func_190916_E()) {
                           k2 = itemstack11.func_77976_d() - itemstack8.func_190916_E();
                        }

                        itemstack11.func_190918_g(k2);
                        itemstack8.func_190917_f(k2);
                     } else if (itemstack11.func_190916_E() <= slot6.func_178170_b(itemstack11)) {
                        slot6.func_75215_d(itemstack11);
                        inventoryplayer.func_70437_b(itemstack8);
                     }
                  } else if (itemstack8.func_77973_b() == itemstack11.func_77973_b() && itemstack11.func_77976_d() > 1 && (!itemstack8.func_77981_g() || itemstack8.func_77960_j() == itemstack11.func_77960_j()) && ItemStack.func_77970_a(itemstack8, itemstack11) && !itemstack8.func_190926_b()) {
                     int j2 = itemstack8.func_190916_E();
                     if (j2 + itemstack11.func_190916_E() <= itemstack11.func_77976_d()) {
                        itemstack11.func_190917_f(j2);
                        itemstack8 = slot6.func_75209_a(j2);
                        if (itemstack8.func_190926_b()) {
                           slot6.func_75215_d(ItemStack.field_190927_a);
                        }

                        slot6.func_190901_a(p_184996_4_, inventoryplayer.func_70445_o());
                     }
                  }
               }

               slot6.func_75218_e();
            }
         }
      } else if (p_184996_3_ == ClickType.SWAP && p_184996_2_ >= 0 && p_184996_2_ < 9) {
         Slot slot4 = this.field_75151_b.get(p_184996_1_);
         ItemStack itemstack6 = inventoryplayer.func_70301_a(p_184996_2_);
         ItemStack itemstack10 = slot4.func_75211_c();
         if (!itemstack6.func_190926_b() || !itemstack10.func_190926_b()) {
            if (itemstack6.func_190926_b()) {
               if (slot4.func_82869_a(p_184996_4_)) {
                  inventoryplayer.func_70299_a(p_184996_2_, itemstack10);
                  slot4.func_190900_b(itemstack10.func_190916_E());
                  slot4.func_75215_d(ItemStack.field_190927_a);
                  slot4.func_190901_a(p_184996_4_, itemstack10);
               }
            } else if (itemstack10.func_190926_b()) {
               if (slot4.func_75214_a(itemstack6)) {
                  int l1 = slot4.func_178170_b(itemstack6);
                  if (itemstack6.func_190916_E() > l1) {
                     slot4.func_75215_d(itemstack6.func_77979_a(l1));
                  } else {
                     slot4.func_75215_d(itemstack6);
                     inventoryplayer.func_70299_a(p_184996_2_, ItemStack.field_190927_a);
                  }
               }
            } else if (slot4.func_82869_a(p_184996_4_) && slot4.func_75214_a(itemstack6)) {
               int i2 = slot4.func_178170_b(itemstack6);
               if (itemstack6.func_190916_E() > i2) {
                  slot4.func_75215_d(itemstack6.func_77979_a(i2));
                  slot4.func_190901_a(p_184996_4_, itemstack10);
                  if (!inventoryplayer.func_70441_a(itemstack10)) {
                     p_184996_4_.func_71019_a(itemstack10, true);
                  }
               } else {
                  slot4.func_75215_d(itemstack6);
                  inventoryplayer.func_70299_a(p_184996_2_, itemstack10);
                  slot4.func_190901_a(p_184996_4_, itemstack10);
               }
            }
         }
      } else if (p_184996_3_ == ClickType.CLONE && p_184996_4_.field_71075_bZ.field_75098_d && inventoryplayer.func_70445_o().func_190926_b() && p_184996_1_ >= 0) {
         Slot slot3 = this.field_75151_b.get(p_184996_1_);
         if (slot3 != null && slot3.func_75216_d()) {
            ItemStack itemstack5 = slot3.func_75211_c().func_77946_l();
            itemstack5.func_190920_e(itemstack5.func_77976_d());
            inventoryplayer.func_70437_b(itemstack5);
         }
      } else if (p_184996_3_ == ClickType.THROW && inventoryplayer.func_70445_o().func_190926_b() && p_184996_1_ >= 0) {
         Slot slot2 = this.field_75151_b.get(p_184996_1_);
         if (slot2 != null && slot2.func_75216_d() && slot2.func_82869_a(p_184996_4_)) {
            ItemStack itemstack4 = slot2.func_75209_a(p_184996_2_ == 0 ? 1 : slot2.func_75211_c().func_190916_E());
            slot2.func_190901_a(p_184996_4_, itemstack4);
            p_184996_4_.func_71019_a(itemstack4, true);
         }
      } else if (p_184996_3_ == ClickType.PICKUP_ALL && p_184996_1_ >= 0) {
         Slot slot = this.field_75151_b.get(p_184996_1_);
         ItemStack itemstack1 = inventoryplayer.func_70445_o();
         if (!itemstack1.func_190926_b() && (slot == null || !slot.func_75216_d() || !slot.func_82869_a(p_184996_4_))) {
            int i = p_184996_2_ == 0 ? 0 : this.field_75151_b.size() - 1;
            int j = p_184996_2_ == 0 ? 1 : -1;

            for(int k = 0; k < 2; ++k) {
               for(int l = i; l >= 0 && l < this.field_75151_b.size() && itemstack1.func_190916_E() < itemstack1.func_77976_d(); l += j) {
                  Slot slot1 = this.field_75151_b.get(l);
                  if (slot1.func_75216_d() && func_94527_a(slot1, itemstack1, true) && slot1.func_82869_a(p_184996_4_) && this.func_94530_a(itemstack1, slot1)) {
                     ItemStack itemstack2 = slot1.func_75211_c();
                     if (k != 0 || itemstack2.func_190916_E() != itemstack2.func_77976_d()) {
                        int i1 = Math.min(itemstack1.func_77976_d() - itemstack1.func_190916_E(), itemstack2.func_190916_E());
                        ItemStack itemstack3 = slot1.func_75209_a(i1);
                        itemstack1.func_190917_f(i1);
                        if (itemstack3.func_190926_b()) {
                           slot1.func_75215_d(ItemStack.field_190927_a);
                        }

                        slot1.func_190901_a(p_184996_4_, itemstack3);
                     }
                  }
               }
            }
         }

         this.func_75142_b();
      }

      return itemstack;
   }

   public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
      return true;
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      InventoryPlayer inventoryplayer = p_75134_1_.field_71071_by;
      if (!inventoryplayer.func_70445_o().func_190926_b()) {
         p_75134_1_.func_71019_a(inventoryplayer.func_70445_o(), false);
         inventoryplayer.func_70437_b(ItemStack.field_190927_a);
      }

   }

   protected void func_193327_a(EntityPlayer p_193327_1_, World p_193327_2_, IInventory p_193327_3_) {
      if (!p_193327_1_.func_70089_S() || p_193327_1_ instanceof EntityPlayerMP && ((EntityPlayerMP)p_193327_1_).func_193105_t()) {
         for(int j = 0; j < p_193327_3_.func_70302_i_(); ++j) {
            p_193327_1_.func_71019_a(p_193327_3_.func_70304_b(j), false);
         }

      } else {
         for(int i = 0; i < p_193327_3_.func_70302_i_(); ++i) {
            p_193327_1_.field_71071_by.func_191975_a(p_193327_2_, p_193327_3_.func_70304_b(i));
         }

      }
   }

   public void func_75130_a(IInventory p_75130_1_) {
      this.func_75142_b();
   }

   public void func_75141_a(int p_75141_1_, ItemStack p_75141_2_) {
      this.func_75139_a(p_75141_1_).func_75215_d(p_75141_2_);
   }

   public void func_190896_a(List<ItemStack> p_190896_1_) {
      for(int i = 0; i < p_190896_1_.size(); ++i) {
         this.func_75139_a(i).func_75215_d(p_190896_1_.get(i));
      }

   }

   public void func_192388_b(int p_192388_1_, ItemStack p_192388_2_) {
      ItemStack itemstack = this.func_75139_a(p_192388_1_).func_75211_c();
      if (itemstack.func_190926_b()) {
         this.func_75141_a(p_192388_1_, p_192388_2_);
      } else if (itemstack.func_77977_a().equals(p_192388_2_.func_77977_a()) && itemstack.func_190916_E() < itemstack.func_77976_d()) {
         itemstack.func_190917_f(p_192388_2_.func_190916_E());
      }

   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
   }

   public short func_75136_a(InventoryPlayer p_75136_1_) {
      ++this.field_75150_e;
      return this.field_75150_e;
   }

   public boolean func_75129_b(EntityPlayer p_75129_1_) {
      return !this.field_75148_f.contains(p_75129_1_);
   }

   public void func_75128_a(EntityPlayer p_75128_1_, boolean p_75128_2_) {
      if (p_75128_2_) {
         this.field_75148_f.remove(p_75128_1_);
      } else {
         this.field_75148_f.add(p_75128_1_);
      }

   }

   public abstract boolean func_75145_c(EntityPlayer var1);

   protected boolean func_75135_a(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
      boolean flag = false;
      int i = p_75135_2_;
      if (p_75135_4_) {
         i = p_75135_3_ - 1;
      }

      if (p_75135_1_.func_77985_e()) {
         while(!p_75135_1_.func_190926_b()) {
            if (p_75135_4_) {
               if (i < p_75135_2_) {
                  break;
               }
            } else if (i >= p_75135_3_) {
               break;
            }

            Slot slot = this.field_75151_b.get(i);
            ItemStack itemstack = slot.func_75211_c();
            if (!itemstack.func_190926_b() && itemstack.func_77973_b() == p_75135_1_.func_77973_b() && (!p_75135_1_.func_77981_g() || p_75135_1_.func_77960_j() == itemstack.func_77960_j()) && ItemStack.func_77970_a(p_75135_1_, itemstack)) {
               int j = itemstack.func_190916_E() + p_75135_1_.func_190916_E();
               if (j <= p_75135_1_.func_77976_d()) {
                  p_75135_1_.func_190920_e(0);
                  itemstack.func_190920_e(j);
                  slot.func_75218_e();
                  flag = true;
               } else if (itemstack.func_190916_E() < p_75135_1_.func_77976_d()) {
                  p_75135_1_.func_190918_g(p_75135_1_.func_77976_d() - itemstack.func_190916_E());
                  itemstack.func_190920_e(p_75135_1_.func_77976_d());
                  slot.func_75218_e();
                  flag = true;
               }
            }

            if (p_75135_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      if (!p_75135_1_.func_190926_b()) {
         if (p_75135_4_) {
            i = p_75135_3_ - 1;
         } else {
            i = p_75135_2_;
         }

         while(true) {
            if (p_75135_4_) {
               if (i < p_75135_2_) {
                  break;
               }
            } else if (i >= p_75135_3_) {
               break;
            }

            Slot slot1 = this.field_75151_b.get(i);
            ItemStack itemstack1 = slot1.func_75211_c();
            if (itemstack1.func_190926_b() && slot1.func_75214_a(p_75135_1_)) {
               if (p_75135_1_.func_190916_E() > slot1.func_75219_a()) {
                  slot1.func_75215_d(p_75135_1_.func_77979_a(slot1.func_75219_a()));
               } else {
                  slot1.func_75215_d(p_75135_1_.func_77979_a(p_75135_1_.func_190916_E()));
               }

               slot1.func_75218_e();
               flag = true;
               break;
            }

            if (p_75135_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      return flag;
   }

   public static int func_94529_b(int p_94529_0_) {
      return p_94529_0_ >> 2 & 3;
   }

   public static int func_94532_c(int p_94532_0_) {
      return p_94532_0_ & 3;
   }

   public static int func_94534_d(int p_94534_0_, int p_94534_1_) {
      return p_94534_0_ & 3 | (p_94534_1_ & 3) << 2;
   }

   public static boolean func_180610_a(int p_180610_0_, EntityPlayer p_180610_1_) {
      if (p_180610_0_ == 0) {
         return true;
      } else if (p_180610_0_ == 1) {
         return true;
      } else {
         return p_180610_0_ == 2 && p_180610_1_.field_71075_bZ.field_75098_d;
      }
   }

   protected void func_94533_d() {
      this.field_94536_g = 0;
      this.field_94537_h.clear();
   }

   public static boolean func_94527_a(@Nullable Slot p_94527_0_, ItemStack p_94527_1_, boolean p_94527_2_) {
      boolean flag = p_94527_0_ == null || !p_94527_0_.func_75216_d();
      if (!flag && p_94527_1_.func_77969_a(p_94527_0_.func_75211_c()) && ItemStack.func_77970_a(p_94527_0_.func_75211_c(), p_94527_1_)) {
         return p_94527_0_.func_75211_c().func_190916_E() + (p_94527_2_ ? 0 : p_94527_1_.func_190916_E()) <= p_94527_1_.func_77976_d();
      } else {
         return flag;
      }
   }

   public static void func_94525_a(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
      switch(p_94525_1_) {
      case 0:
         p_94525_2_.func_190920_e(MathHelper.func_76141_d((float)p_94525_2_.func_190916_E() / (float)p_94525_0_.size()));
         break;
      case 1:
         p_94525_2_.func_190920_e(1);
         break;
      case 2:
         p_94525_2_.func_190920_e(p_94525_2_.func_77973_b().func_77639_j());
      }

      p_94525_2_.func_190917_f(p_94525_3_);
   }

   public boolean func_94531_b(Slot p_94531_1_) {
      return true;
   }

   public static int func_178144_a(@Nullable TileEntity p_178144_0_) {
      return p_178144_0_ instanceof IInventory ? func_94526_b((IInventory)p_178144_0_) : 0;
   }

   public static int func_94526_b(@Nullable IInventory p_94526_0_) {
      if (p_94526_0_ == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for(int j = 0; j < p_94526_0_.func_70302_i_(); ++j) {
            ItemStack itemstack = p_94526_0_.func_70301_a(j);
            if (!itemstack.func_190926_b()) {
               f += (float)itemstack.func_190916_E() / (float)Math.min(p_94526_0_.func_70297_j_(), itemstack.func_77976_d());
               ++i;
            }
         }

         f = f / (float)p_94526_0_.func_70302_i_();
         return MathHelper.func_76141_d(f * 14.0F) + (i > 0 ? 1 : 0);
      }
   }

   protected void func_192389_a(World p_192389_1_, EntityPlayer p_192389_2_, InventoryCrafting p_192389_3_, InventoryCraftResult p_192389_4_) {
      if (!p_192389_1_.field_72995_K) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)p_192389_2_;
         ItemStack itemstack = ItemStack.field_190927_a;
         IRecipe irecipe = CraftingManager.func_192413_b(p_192389_3_, p_192389_1_);
         if (irecipe != null && (irecipe.func_192399_d() || !p_192389_1_.func_82736_K().func_82766_b("doLimitedCrafting") || entityplayermp.func_192037_E().func_193830_f(irecipe))) {
            p_192389_4_.func_193056_a(irecipe);
            itemstack = irecipe.func_77572_b(p_192389_3_);
         }

         p_192389_4_.func_70299_a(0, itemstack);
         entityplayermp.field_71135_a.func_147359_a(new SPacketSetSlot(this.field_75152_c, 0, itemstack));
      }
   }
}
