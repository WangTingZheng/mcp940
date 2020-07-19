package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant extends Container {
   private final IMerchant field_75178_e;
   private final InventoryMerchant field_75176_f;
   private final World field_75177_g;

   public ContainerMerchant(InventoryPlayer p_i1821_1_, IMerchant p_i1821_2_, World p_i1821_3_) {
      this.field_75178_e = p_i1821_2_;
      this.field_75177_g = p_i1821_3_;
      this.field_75176_f = new InventoryMerchant(p_i1821_1_.field_70458_d, p_i1821_2_);
      this.func_75146_a(new Slot(this.field_75176_f, 0, 36, 53));
      this.func_75146_a(new Slot(this.field_75176_f, 1, 62, 53));
      this.func_75146_a(new SlotMerchantResult(p_i1821_1_.field_70458_d, p_i1821_2_, this.field_75176_f, 2, 120, 53));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(p_i1821_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.func_75146_a(new Slot(p_i1821_1_, k, 8 + k * 18, 142));
      }

   }

   public InventoryMerchant func_75174_d() {
      return this.field_75176_f;
   }

   public void func_75130_a(IInventory p_75130_1_) {
      this.field_75176_f.func_70470_g();
      super.func_75130_a(p_75130_1_);
   }

   public void func_75175_c(int p_75175_1_) {
      this.field_75176_f.func_70471_c(p_75175_1_);
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_75178_e.func_70931_l_() == p_75145_1_;
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
            if (p_82846_2_ >= 3 && p_82846_2_ < 30) {
               if (!this.func_75135_a(itemstack1, 30, 39, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.func_75135_a(itemstack1, 3, 30, false)) {
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

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      this.field_75178_e.func_70932_a_((EntityPlayer)null);
      super.func_75134_a(p_75134_1_);
      if (!this.field_75177_g.field_72995_K) {
         ItemStack itemstack = this.field_75176_f.func_70304_b(0);
         if (!itemstack.func_190926_b()) {
            p_75134_1_.func_71019_a(itemstack, false);
         }

         itemstack = this.field_75176_f.func_70304_b(1);
         if (!itemstack.func_190926_b()) {
            p_75134_1_.func_71019_a(itemstack, false);
         }

      }
   }
}
