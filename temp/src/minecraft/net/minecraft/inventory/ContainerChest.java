package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {
   private final IInventory field_75155_e;
   private final int field_75154_f;

   public ContainerChest(IInventory p_i45801_1_, IInventory p_i45801_2_, EntityPlayer p_i45801_3_) {
      this.field_75155_e = p_i45801_2_;
      this.field_75154_f = p_i45801_2_.func_70302_i_() / 9;
      p_i45801_2_.func_174889_b(p_i45801_3_);
      int i = (this.field_75154_f - 4) * 18;

      for(int j = 0; j < this.field_75154_f; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.func_75146_a(new Slot(p_i45801_2_, k + j * 9, 8 + k * 18, 18 + j * 18));
         }
      }

      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.func_75146_a(new Slot(p_i45801_1_, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.func_75146_a(new Slot(p_i45801_1_, i1, 8 + i1 * 18, 161 + i));
      }

   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_75155_e.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ < this.field_75154_f * 9) {
            if (!this.func_75135_a(itemstack1, this.field_75154_f * 9, this.field_75151_b.size(), true)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 0, this.field_75154_f * 9, false)) {
            return ItemStack.field_190927_a;
         }

         if (itemstack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
         } else {
            slot.func_75218_e();
         }
      }

      return itemstack;
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      this.field_75155_e.func_174886_c(p_75134_1_);
   }

   public IInventory func_85151_d() {
      return this.field_75155_e;
   }
}
