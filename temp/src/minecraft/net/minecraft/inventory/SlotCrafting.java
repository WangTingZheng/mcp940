package net.minecraft.inventory;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

public class SlotCrafting extends Slot {
   private final InventoryCrafting field_75239_a;
   private final EntityPlayer field_75238_b;
   private int field_75237_g;

   public SlotCrafting(EntityPlayer p_i45790_1_, InventoryCrafting p_i45790_2_, IInventory p_i45790_3_, int p_i45790_4_, int p_i45790_5_, int p_i45790_6_) {
      super(p_i45790_3_, p_i45790_4_, p_i45790_5_, p_i45790_6_);
      this.field_75238_b = p_i45790_1_;
      this.field_75239_a = p_i45790_2_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return false;
   }

   public ItemStack func_75209_a(int p_75209_1_) {
      if (this.func_75216_d()) {
         this.field_75237_g += Math.min(p_75209_1_, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(p_75209_1_);
   }

   protected void func_75210_a(ItemStack p_75210_1_, int p_75210_2_) {
      this.field_75237_g += p_75210_2_;
      this.func_75208_c(p_75210_1_);
   }

   protected void func_190900_b(int p_190900_1_) {
      this.field_75237_g += p_190900_1_;
   }

   protected void func_75208_c(ItemStack p_75208_1_) {
      if (this.field_75237_g > 0) {
         p_75208_1_.func_77980_a(this.field_75238_b.field_70170_p, this.field_75238_b, this.field_75237_g);
      }

      this.field_75237_g = 0;
      InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.field_75224_c;
      IRecipe irecipe = inventorycraftresult.func_193055_i();
      if (irecipe != null && !irecipe.func_192399_d()) {
         this.field_75238_b.func_192021_a(Lists.newArrayList(irecipe));
         inventorycraftresult.func_193056_a((IRecipe)null);
      }

   }

   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
      this.func_75208_c(p_190901_2_);
      NonNullList<ItemStack> nonnulllist = CraftingManager.func_180303_b(this.field_75239_a, p_190901_1_.field_70170_p);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = this.field_75239_a.func_70301_a(i);
         ItemStack itemstack1 = nonnulllist.get(i);
         if (!itemstack.func_190926_b()) {
            this.field_75239_a.func_70298_a(i, 1);
            itemstack = this.field_75239_a.func_70301_a(i);
         }

         if (!itemstack1.func_190926_b()) {
            if (itemstack.func_190926_b()) {
               this.field_75239_a.func_70299_a(i, itemstack1);
            } else if (ItemStack.func_179545_c(itemstack, itemstack1) && ItemStack.func_77970_a(itemstack, itemstack1)) {
               itemstack1.func_190917_f(itemstack.func_190916_E());
               this.field_75239_a.func_70299_a(i, itemstack1);
            } else if (!this.field_75238_b.field_71071_by.func_70441_a(itemstack1)) {
               this.field_75238_b.func_71019_a(itemstack1, false);
            }
         }
      }

      return p_190901_2_;
   }
}
