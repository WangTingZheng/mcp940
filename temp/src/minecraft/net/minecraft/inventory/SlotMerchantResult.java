package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final InventoryMerchant field_75233_a;
   private final EntityPlayer field_75232_b;
   private int field_75231_g;
   private final IMerchant field_75234_h;

   public SlotMerchantResult(EntityPlayer p_i1822_1_, IMerchant p_i1822_2_, InventoryMerchant p_i1822_3_, int p_i1822_4_, int p_i1822_5_, int p_i1822_6_) {
      super(p_i1822_3_, p_i1822_4_, p_i1822_5_, p_i1822_6_);
      this.field_75232_b = p_i1822_1_;
      this.field_75234_h = p_i1822_2_;
      this.field_75233_a = p_i1822_3_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return false;
   }

   public ItemStack func_75209_a(int p_75209_1_) {
      if (this.func_75216_d()) {
         this.field_75231_g += Math.min(p_75209_1_, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(p_75209_1_);
   }

   protected void func_75210_a(ItemStack p_75210_1_, int p_75210_2_) {
      this.field_75231_g += p_75210_2_;
      this.func_75208_c(p_75210_1_);
   }

   protected void func_75208_c(ItemStack p_75208_1_) {
      p_75208_1_.func_77980_a(this.field_75232_b.field_70170_p, this.field_75232_b, this.field_75231_g);
      this.field_75231_g = 0;
   }

   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
      this.func_75208_c(p_190901_2_);
      MerchantRecipe merchantrecipe = this.field_75233_a.func_70468_h();
      if (merchantrecipe != null) {
         ItemStack itemstack = this.field_75233_a.func_70301_a(0);
         ItemStack itemstack1 = this.field_75233_a.func_70301_a(1);
         if (this.func_75230_a(merchantrecipe, itemstack, itemstack1) || this.func_75230_a(merchantrecipe, itemstack1, itemstack)) {
            this.field_75234_h.func_70933_a(merchantrecipe);
            p_190901_1_.func_71029_a(StatList.field_188075_I);
            this.field_75233_a.func_70299_a(0, itemstack);
            this.field_75233_a.func_70299_a(1, itemstack1);
         }
      }

      return p_190901_2_;
   }

   private boolean func_75230_a(MerchantRecipe p_75230_1_, ItemStack p_75230_2_, ItemStack p_75230_3_) {
      ItemStack itemstack = p_75230_1_.func_77394_a();
      ItemStack itemstack1 = p_75230_1_.func_77396_b();
      if (p_75230_2_.func_77973_b() == itemstack.func_77973_b() && p_75230_2_.func_190916_E() >= itemstack.func_190916_E()) {
         if (!itemstack1.func_190926_b() && !p_75230_3_.func_190926_b() && itemstack1.func_77973_b() == p_75230_3_.func_77973_b() && p_75230_3_.func_190916_E() >= itemstack1.func_190916_E()) {
            p_75230_2_.func_190918_g(itemstack.func_190916_E());
            p_75230_3_.func_190918_g(itemstack1.func_190916_E());
            return true;
         }

         if (itemstack1.func_190926_b() && p_75230_3_.func_190926_b()) {
            p_75230_2_.func_190918_g(itemstack.func_190916_E());
            return true;
         }
      }

      return false;
   }
}
