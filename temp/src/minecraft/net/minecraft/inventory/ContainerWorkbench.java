package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {
   public InventoryCrafting field_75162_e = new InventoryCrafting(this, 3, 3);
   public InventoryCraftResult field_75160_f = new InventoryCraftResult();
   private final World field_75161_g;
   private final BlockPos field_178145_h;
   private final EntityPlayer field_192390_i;

   public ContainerWorkbench(InventoryPlayer p_i45800_1_, World p_i45800_2_, BlockPos p_i45800_3_) {
      this.field_75161_g = p_i45800_2_;
      this.field_178145_h = p_i45800_3_;
      this.field_192390_i = p_i45800_1_.field_70458_d;
      this.func_75146_a(new SlotCrafting(p_i45800_1_.field_70458_d, this.field_75162_e, this.field_75160_f, 0, 124, 35));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 3; ++j) {
            this.func_75146_a(new Slot(this.field_75162_e, j + i * 3, 30 + j * 18, 17 + i * 18));
         }
      }

      for(int k = 0; k < 3; ++k) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.func_75146_a(new Slot(p_i45800_1_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for(int l = 0; l < 9; ++l) {
         this.func_75146_a(new Slot(p_i45800_1_, l, 8 + l * 18, 142));
      }

   }

   public void func_75130_a(IInventory p_75130_1_) {
      this.func_192389_a(this.field_75161_g, this.field_192390_i, this.field_75162_e, this.field_75160_f);
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      if (!this.field_75161_g.field_72995_K) {
         this.func_193327_a(p_75134_1_, this.field_75161_g, this.field_75162_e);
      }
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      if (this.field_75161_g.func_180495_p(this.field_178145_h).func_177230_c() != Blocks.field_150462_ai) {
         return false;
      } else {
         return p_75145_1_.func_70092_e((double)this.field_178145_h.func_177958_n() + 0.5D, (double)this.field_178145_h.func_177956_o() + 0.5D, (double)this.field_178145_h.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (p_82846_2_ == 0) {
            itemstack1.func_77973_b().func_77622_d(itemstack1, this.field_75161_g, p_82846_1_);
            if (!this.func_75135_a(itemstack1, 10, 46, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (p_82846_2_ >= 10 && p_82846_2_ < 37) {
            if (!this.func_75135_a(itemstack1, 37, 46, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 37 && p_82846_2_ < 46) {
            if (!this.func_75135_a(itemstack1, 10, 37, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 10, 46, false)) {
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

         ItemStack itemstack2 = slot.func_190901_a(p_82846_1_, itemstack1);
         if (p_82846_2_ == 0) {
            p_82846_1_.func_71019_a(itemstack2, false);
         }
      }

      return itemstack;
   }

   public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
      return p_94530_2_.field_75224_c != this.field_75160_f && super.func_94530_a(p_94530_1_, p_94530_2_);
   }
}
