package net.minecraft.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

public class ContainerBrewingStand extends Container {
   private final IInventory field_75188_e;
   private final Slot field_75186_f;
   private int field_184998_g;
   private int field_184999_h;

   public ContainerBrewingStand(InventoryPlayer p_i45802_1_, IInventory p_i45802_2_) {
      this.field_75188_e = p_i45802_2_;
      this.func_75146_a(new ContainerBrewingStand.Potion(p_i45802_2_, 0, 56, 51));
      this.func_75146_a(new ContainerBrewingStand.Potion(p_i45802_2_, 1, 79, 58));
      this.func_75146_a(new ContainerBrewingStand.Potion(p_i45802_2_, 2, 102, 51));
      this.field_75186_f = this.func_75146_a(new ContainerBrewingStand.Ingredient(p_i45802_2_, 3, 79, 17));
      this.func_75146_a(new ContainerBrewingStand.Fuel(p_i45802_2_, 4, 17, 17));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(p_i45802_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.func_75146_a(new Slot(p_i45802_1_, k, 8 + k * 18, 142));
      }

   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      super.func_75132_a(p_75132_1_);
      p_75132_1_.func_175173_a(this, this.field_75188_e);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         IContainerListener icontainerlistener = this.field_75149_d.get(i);
         if (this.field_184998_g != this.field_75188_e.func_174887_a_(0)) {
            icontainerlistener.func_71112_a(this, 0, this.field_75188_e.func_174887_a_(0));
         }

         if (this.field_184999_h != this.field_75188_e.func_174887_a_(1)) {
            icontainerlistener.func_71112_a(this, 1, this.field_75188_e.func_174887_a_(1));
         }
      }

      this.field_184998_g = this.field_75188_e.func_174887_a_(0);
      this.field_184999_h = this.field_75188_e.func_174887_a_(1);
   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
      this.field_75188_e.func_174885_b(p_75137_1_, p_75137_2_);
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_75188_e.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = this.field_75151_b.get(p_82846_2_);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if ((p_82846_2_ < 0 || p_82846_2_ > 2) && p_82846_2_ != 3 && p_82846_2_ != 4) {
            if (this.field_75186_f.func_75214_a(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 3, 4, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (ContainerBrewingStand.Potion.func_75243_a_(itemstack) && itemstack.func_190916_E() == 1) {
               if (!this.func_75135_a(itemstack1, 0, 3, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (ContainerBrewingStand.Fuel.func_185004_b_(itemstack)) {
               if (!this.func_75135_a(itemstack1, 4, 5, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (p_82846_2_ >= 5 && p_82846_2_ < 32) {
               if (!this.func_75135_a(itemstack1, 32, 41, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (p_82846_2_ >= 32 && p_82846_2_ < 41) {
               if (!this.func_75135_a(itemstack1, 5, 32, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (!this.func_75135_a(itemstack1, 5, 41, false)) {
               return ItemStack.field_190927_a;
            }
         } else {
            if (!this.func_75135_a(itemstack1, 5, 41, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
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

   static class Fuel extends Slot {
      public Fuel(IInventory p_i47070_1_, int p_i47070_2_, int p_i47070_3_, int p_i47070_4_) {
         super(p_i47070_1_, p_i47070_2_, p_i47070_3_, p_i47070_4_);
      }

      public boolean func_75214_a(ItemStack p_75214_1_) {
         return func_185004_b_(p_75214_1_);
      }

      public static boolean func_185004_b_(ItemStack p_185004_0_) {
         return p_185004_0_.func_77973_b() == Items.field_151065_br;
      }

      public int func_75219_a() {
         return 64;
      }
   }

   static class Ingredient extends Slot {
      public Ingredient(IInventory p_i47069_1_, int p_i47069_2_, int p_i47069_3_, int p_i47069_4_) {
         super(p_i47069_1_, p_i47069_2_, p_i47069_3_, p_i47069_4_);
      }

      public boolean func_75214_a(ItemStack p_75214_1_) {
         return PotionHelper.func_185205_a(p_75214_1_);
      }

      public int func_75219_a() {
         return 64;
      }
   }

   static class Potion extends Slot {
      public Potion(IInventory p_i47598_1_, int p_i47598_2_, int p_i47598_3_, int p_i47598_4_) {
         super(p_i47598_1_, p_i47598_2_, p_i47598_3_, p_i47598_4_);
      }

      public boolean func_75214_a(ItemStack p_75214_1_) {
         return func_75243_a_(p_75214_1_);
      }

      public int func_75219_a() {
         return 1;
      }

      public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
         PotionType potiontype = PotionUtils.func_185191_c(p_190901_2_);
         if (p_190901_1_ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192130_j.func_192173_a((EntityPlayerMP)p_190901_1_, potiontype);
         }

         super.func_190901_a(p_190901_1_, p_190901_2_);
         return p_190901_2_;
      }

      public static boolean func_75243_a_(ItemStack p_75243_0_) {
         Item item = p_75243_0_.func_77973_b();
         return item == Items.field_151068_bn || item == Items.field_185155_bH || item == Items.field_185156_bI || item == Items.field_151069_bo;
      }
   }
}
