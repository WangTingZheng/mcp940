package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerShulkerBox extends Container {
   private final IInventory field_190899_a;

   public ContainerShulkerBox(InventoryPlayer p_i47266_1_, IInventory p_i47266_2_, EntityPlayer p_i47266_3_) {
      this.field_190899_a = p_i47266_2_;
      p_i47266_2_.func_174889_b(p_i47266_3_);
      int i = 3;
      int j = 9;

      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 9; ++l) {
            this.func_75146_a(new SlotShulkerBox(p_i47266_2_, l + k * 9, 8 + l * 18, 18 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.func_75146_a(new Slot(p_i47266_1_, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(p_i47266_1_, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_190899_a.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ < this.field_190899_a.func_70302_i_()) {
            if (!this.func_75135_a(itemstack1, this.field_190899_a.func_70302_i_(), this.field_75151_b.size(), true)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 0, this.field_190899_a.func_70302_i_(), false)) {
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
      this.field_190899_a.func_174886_c(p_75134_1_);
   }
}
