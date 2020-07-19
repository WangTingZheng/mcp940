package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerPlayer extends Container {
   private static final EntityEquipmentSlot[] field_185003_h = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
   public InventoryCrafting field_75181_e = new InventoryCrafting(this, 2, 2);
   public InventoryCraftResult field_75179_f = new InventoryCraftResult();
   public boolean field_75180_g;
   private final EntityPlayer field_82862_h;

   public ContainerPlayer(InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer p_i1819_3_) {
      this.field_75180_g = p_i1819_2_;
      this.field_82862_h = p_i1819_3_;
      this.func_75146_a(new SlotCrafting(p_i1819_1_.field_70458_d, this.field_75181_e, this.field_75179_f, 0, 154, 28));

      for(int i = 0; i < 2; ++i) {
         for(int j = 0; j < 2; ++j) {
            this.func_75146_a(new Slot(this.field_75181_e, j + i * 2, 98 + j * 18, 18 + i * 18));
         }
      }

      for(int k = 0; k < 4; ++k) {
         final EntityEquipmentSlot entityequipmentslot = field_185003_h[k];
         this.func_75146_a(new Slot(p_i1819_1_, 36 + (3 - k), 8, 8 + k * 18) {
            public int func_75219_a() {
               return 1;
            }

            public boolean func_75214_a(ItemStack p_75214_1_) {
               return entityequipmentslot == EntityLiving.func_184640_d(p_75214_1_);
            }

            public boolean func_82869_a(EntityPlayer p_82869_1_) {
               ItemStack itemstack = this.func_75211_c();
               return !itemstack.func_190926_b() && !p_82869_1_.func_184812_l_() && EnchantmentHelper.func_190938_b(itemstack) ? false : super.func_82869_a(p_82869_1_);
            }

            @Nullable
            public String func_178171_c() {
               return ItemArmor.field_94603_a[entityequipmentslot.func_188454_b()];
            }
         });
      }

      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.func_75146_a(new Slot(p_i1819_1_, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.func_75146_a(new Slot(p_i1819_1_, i1, 8 + i1 * 18, 142));
      }

      this.func_75146_a(new Slot(p_i1819_1_, 40, 77, 62) {
         @Nullable
         public String func_178171_c() {
            return "minecraft:items/empty_armor_slot_shield";
         }
      });
   }

   public void func_75130_a(IInventory p_75130_1_) {
      this.func_192389_a(this.field_82862_h.field_70170_p, this.field_82862_h, this.field_75181_e, this.field_75179_f);
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      this.field_75179_f.func_174888_l();
      if (!p_75134_1_.field_70170_p.field_72995_K) {
         this.func_193327_a(p_75134_1_, p_75134_1_.field_70170_p, this.field_75181_e);
      }
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return true;
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
         if (p_82846_2_ == 0) {
            if (!this.func_75135_a(itemstack1, 9, 45, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (p_82846_2_ >= 1 && p_82846_2_ < 5) {
            if (!this.func_75135_a(itemstack1, 9, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 5 && p_82846_2_ < 9) {
            if (!this.func_75135_a(itemstack1, 9, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR && !((Slot)this.field_75151_b.get(8 - entityequipmentslot.func_188454_b())).func_75216_d()) {
            int i = 8 - entityequipmentslot.func_188454_b();
            if (!this.func_75135_a(itemstack1, i, i + 1, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND && !((Slot)this.field_75151_b.get(45)).func_75216_d()) {
            if (!this.func_75135_a(itemstack1, 45, 46, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 9 && p_82846_2_ < 36) {
            if (!this.func_75135_a(itemstack1, 36, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (p_82846_2_ >= 36 && p_82846_2_ < 45) {
            if (!this.func_75135_a(itemstack1, 9, 36, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(itemstack1, 9, 45, false)) {
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
      return p_94530_2_.field_75224_c != this.field_75179_f && super.func_94530_a(p_94530_1_, p_94530_2_);
   }
}
