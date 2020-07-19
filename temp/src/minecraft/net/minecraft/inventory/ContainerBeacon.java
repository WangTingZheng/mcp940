package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerBeacon extends Container {
   private final IInventory field_82866_e;
   private final ContainerBeacon.BeaconSlot field_82864_f;

   public ContainerBeacon(IInventory p_i45804_1_, IInventory p_i45804_2_) {
      this.field_82866_e = p_i45804_2_;
      this.field_82864_f = new ContainerBeacon.BeaconSlot(p_i45804_2_, 0, 136, 110);
      this.func_75146_a(this.field_82864_f);
      int i = 36;
      int j = 137;

      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 9; ++l) {
            this.func_75146_a(new Slot(p_i45804_1_, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.func_75146_a(new Slot(p_i45804_1_, i1, 36 + i1 * 18, 195));
      }

   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      super.func_75132_a(p_75132_1_);
      p_75132_1_.func_175173_a(this, this.field_82866_e);
   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
      this.field_82866_e.func_174885_b(p_75137_1_, p_75137_2_);
   }

   public IInventory func_180611_e() {
      return this.field_82866_e;
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      if (!p_75134_1_.field_70170_p.field_72995_K) {
         ItemStack itemstack = this.field_82864_f.func_75209_a(this.field_82864_f.func_75219_a());
         if (!itemstack.func_190926_b()) {
            p_75134_1_.func_71019_a(itemstack, false);
         }

      }
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_82866_e.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ == 0) {
            if (!this.func_75135_a(itemstack1, 1, 37, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (!this.field_82864_f.func_75216_d() && this.field_82864_f.func_75214_a(itemstack1) && itemstack1.func_190916_E() == 1) {
            if (!this.func_75135_a(itemstack1, 0, 1, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 1 && p_82846_2_ < 28) {
            if (!this.func_75135_a(itemstack1, 28, 37, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 28 && p_82846_2_ < 37) {
            if (!this.func_75135_a(itemstack1, 1, 28, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 1, 37, false)) {
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

   class BeaconSlot extends Slot {
      public BeaconSlot(IInventory p_i1801_2_, int p_i1801_3_, int p_i1801_4_, int p_i1801_5_) {
         super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
      }

      public boolean func_75214_a(ItemStack p_75214_1_) {
         Item item = p_75214_1_.func_77973_b();
         return item == Items.field_151166_bC || item == Items.field_151045_i || item == Items.field_151043_k || item == Items.field_151042_j;
      }

      public int func_75219_a() {
         return 1;
      }
   }
}
