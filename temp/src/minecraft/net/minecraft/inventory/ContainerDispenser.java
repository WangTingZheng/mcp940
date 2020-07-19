package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerDispenser extends Container {
   private final IInventory field_178146_a;

   public ContainerDispenser(IInventory p_i45799_1_, IInventory p_i45799_2_) {
      this.field_178146_a = p_i45799_2_;

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 3; ++j) {
            this.func_75146_a(new Slot(p_i45799_2_, j + i * 3, 62 + j * 18, 17 + i * 18));
         }
      }

      for(int k = 0; k < 3; ++k) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.func_75146_a(new Slot(p_i45799_1_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for(int l = 0; l < 9; ++l) {
         this.func_75146_a(new Slot(p_i45799_1_, l, 8 + l * 18, 142));
      }

   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_178146_a.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ < 9) {
            if (!this.func_75135_a(itemstack1, 9, 45, true)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 0, 9, false)) {
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
}
