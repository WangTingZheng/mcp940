package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnace extends Container {
   private final IInventory field_75158_e;
   private int field_178152_f;
   private int field_178153_g;
   private int field_178154_h;
   private int field_178155_i;

   public ContainerFurnace(InventoryPlayer p_i45794_1_, IInventory p_i45794_2_) {
      this.field_75158_e = p_i45794_2_;
      this.func_75146_a(new Slot(p_i45794_2_, 0, 56, 17));
      this.func_75146_a(new SlotFurnaceFuel(p_i45794_2_, 1, 56, 53));
      this.func_75146_a(new SlotFurnaceOutput(p_i45794_1_.field_70458_d, p_i45794_2_, 2, 116, 35));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(p_i45794_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.func_75146_a(new Slot(p_i45794_1_, k, 8 + k * 18, 142));
      }

   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      super.func_75132_a(p_75132_1_);
      p_75132_1_.func_175173_a(this, this.field_75158_e);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         IContainerListener icontainerlistener = this.field_75149_d.get(i);
         if (this.field_178152_f != this.field_75158_e.func_174887_a_(2)) {
            icontainerlistener.func_71112_a(this, 2, this.field_75158_e.func_174887_a_(2));
         }

         if (this.field_178154_h != this.field_75158_e.func_174887_a_(0)) {
            icontainerlistener.func_71112_a(this, 0, this.field_75158_e.func_174887_a_(0));
         }

         if (this.field_178155_i != this.field_75158_e.func_174887_a_(1)) {
            icontainerlistener.func_71112_a(this, 1, this.field_75158_e.func_174887_a_(1));
         }

         if (this.field_178153_g != this.field_75158_e.func_174887_a_(3)) {
            icontainerlistener.func_71112_a(this, 3, this.field_75158_e.func_174887_a_(3));
         }
      }

      this.field_178152_f = this.field_75158_e.func_174887_a_(2);
      this.field_178154_h = this.field_75158_e.func_174887_a_(0);
      this.field_178155_i = this.field_75158_e.func_174887_a_(1);
      this.field_178153_g = this.field_75158_e.func_174887_a_(3);
   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
      this.field_75158_e.func_174885_b(p_75137_1_, p_75137_2_);
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_75158_e.func_70300_a(p_75145_1_);
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
         } else if (p_82846_2_ != 1 && p_82846_2_ != 0) {
            if (!FurnaceRecipes.func_77602_a().func_151395_a(itemstack1).func_190926_b()) {
               if (!this.func_75135_a(itemstack1, 0, 1, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (TileEntityFurnace.func_145954_b(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 1, 2, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (p_82846_2_ >= 3 && p_82846_2_ < 30) {
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
}
