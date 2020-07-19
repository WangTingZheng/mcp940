package net.minecraft.tileentity;

import java.util.Arrays;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;

public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory {
   private static final int[] field_145941_a = new int[]{3};
   private static final int[] field_184277_f = new int[]{0, 1, 2, 3};
   private static final int[] field_145947_i = new int[]{0, 1, 2, 4};
   private NonNullList<ItemStack> field_145945_j = NonNullList.<ItemStack>func_191197_a(5, ItemStack.field_190927_a);
   private int field_145946_k;
   private boolean[] field_145943_l;
   private Item field_145944_m;
   private String field_145942_n;
   private int field_184278_m;

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_145942_n : "container.brewing";
   }

   public boolean func_145818_k_() {
      return this.field_145942_n != null && !this.field_145942_n.isEmpty();
   }

   public void func_145937_a(String p_145937_1_) {
      this.field_145942_n = p_145937_1_;
   }

   public int func_70302_i_() {
      return this.field_145945_j.size();
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_145945_j) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public void func_73660_a() {
      ItemStack itemstack = this.field_145945_j.get(4);
      if (this.field_184278_m <= 0 && itemstack.func_77973_b() == Items.field_151065_br) {
         this.field_184278_m = 20;
         itemstack.func_190918_g(1);
         this.func_70296_d();
      }

      boolean flag = this.func_145934_k();
      boolean flag1 = this.field_145946_k > 0;
      ItemStack itemstack1 = this.field_145945_j.get(3);
      if (flag1) {
         --this.field_145946_k;
         boolean flag2 = this.field_145946_k == 0;
         if (flag2 && flag) {
            this.func_145940_l();
            this.func_70296_d();
         } else if (!flag) {
            this.field_145946_k = 0;
            this.func_70296_d();
         } else if (this.field_145944_m != itemstack1.func_77973_b()) {
            this.field_145946_k = 0;
            this.func_70296_d();
         }
      } else if (flag && this.field_184278_m > 0) {
         --this.field_184278_m;
         this.field_145946_k = 400;
         this.field_145944_m = itemstack1.func_77973_b();
         this.func_70296_d();
      }

      if (!this.field_145850_b.field_72995_K) {
         boolean[] aboolean = this.func_174902_m();
         if (!Arrays.equals(aboolean, this.field_145943_l)) {
            this.field_145943_l = aboolean;
            IBlockState iblockstate = this.field_145850_b.func_180495_p(this.func_174877_v());
            if (!(iblockstate.func_177230_c() instanceof BlockBrewingStand)) {
               return;
            }

            for(int i = 0; i < BlockBrewingStand.field_176451_a.length; ++i) {
               iblockstate = iblockstate.func_177226_a(BlockBrewingStand.field_176451_a[i], Boolean.valueOf(aboolean[i]));
            }

            this.field_145850_b.func_180501_a(this.field_174879_c, iblockstate, 2);
         }
      }

   }

   public boolean[] func_174902_m() {
      boolean[] aboolean = new boolean[3];

      for(int i = 0; i < 3; ++i) {
         if (!((ItemStack)this.field_145945_j.get(i)).func_190926_b()) {
            aboolean[i] = true;
         }
      }

      return aboolean;
   }

   private boolean func_145934_k() {
      ItemStack itemstack = this.field_145945_j.get(3);
      if (itemstack.func_190926_b()) {
         return false;
      } else if (!PotionHelper.func_185205_a(itemstack)) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            ItemStack itemstack1 = this.field_145945_j.get(i);
            if (!itemstack1.func_190926_b() && PotionHelper.func_185208_a(itemstack1, itemstack)) {
               return true;
            }
         }

         return false;
      }
   }

   private void func_145940_l() {
      ItemStack itemstack = this.field_145945_j.get(3);

      for(int i = 0; i < 3; ++i) {
         this.field_145945_j.set(i, PotionHelper.func_185212_d(itemstack, this.field_145945_j.get(i)));
      }

      itemstack.func_190918_g(1);
      BlockPos blockpos = this.func_174877_v();
      if (itemstack.func_77973_b().func_77634_r()) {
         ItemStack itemstack1 = new ItemStack(itemstack.func_77973_b().func_77668_q());
         if (itemstack.func_190926_b()) {
            itemstack = itemstack1;
         } else {
            InventoryHelper.func_180173_a(this.field_145850_b, (double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p(), itemstack1);
         }
      }

      this.field_145945_j.set(3, itemstack);
      this.field_145850_b.func_175718_b(1035, blockpos, 0);
   }

   public static void func_189675_a(DataFixer p_189675_0_) {
      p_189675_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityBrewingStand.class, new String[]{"Items"}));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145945_j = NonNullList.<ItemStack>func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(p_145839_1_, this.field_145945_j);
      this.field_145946_k = p_145839_1_.func_74765_d("BrewTime");
      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_145942_n = p_145839_1_.func_74779_i("CustomName");
      }

      this.field_184278_m = p_145839_1_.func_74771_c("Fuel");
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74777_a("BrewTime", (short)this.field_145946_k);
      ItemStackHelper.func_191282_a(p_189515_1_, this.field_145945_j);
      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_145942_n);
      }

      p_189515_1_.func_74774_a("Fuel", (byte)this.field_184278_m);
      return p_189515_1_;
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return p_70301_1_ >= 0 && p_70301_1_ < this.field_145945_j.size() ? (ItemStack)this.field_145945_j.get(p_70301_1_) : ItemStack.field_190927_a;
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      return ItemStackHelper.func_188382_a(this.field_145945_j, p_70298_1_, p_70298_2_);
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      return ItemStackHelper.func_188383_a(this.field_145945_j, p_70304_1_);
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      if (p_70299_1_ >= 0 && p_70299_1_ < this.field_145945_j.size()) {
         this.field_145945_j.set(p_70299_1_, p_70299_2_);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return p_70300_1_.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      if (p_94041_1_ == 3) {
         return PotionHelper.func_185205_a(p_94041_2_);
      } else {
         Item item = p_94041_2_.func_77973_b();
         if (p_94041_1_ == 4) {
            return item == Items.field_151065_br;
         } else {
            return (item == Items.field_151068_bn || item == Items.field_185155_bH || item == Items.field_185156_bI || item == Items.field_151069_bo) && this.func_70301_a(p_94041_1_).func_190926_b();
         }
      }
   }

   public int[] func_180463_a(EnumFacing p_180463_1_) {
      if (p_180463_1_ == EnumFacing.UP) {
         return field_145941_a;
      } else {
         return p_180463_1_ == EnumFacing.DOWN ? field_184277_f : field_145947_i;
      }
   }

   public boolean func_180462_a(int p_180462_1_, ItemStack p_180462_2_, EnumFacing p_180462_3_) {
      return this.func_94041_b(p_180462_1_, p_180462_2_);
   }

   public boolean func_180461_b(int p_180461_1_, ItemStack p_180461_2_, EnumFacing p_180461_3_) {
      if (p_180461_1_ == 3) {
         return p_180461_2_.func_77973_b() == Items.field_151069_bo;
      } else {
         return true;
      }
   }

   public String func_174875_k() {
      return "minecraft:brewing_stand";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      return new ContainerBrewingStand(p_174876_1_, this);
   }

   public int func_174887_a_(int p_174887_1_) {
      switch(p_174887_1_) {
      case 0:
         return this.field_145946_k;
      case 1:
         return this.field_184278_m;
      default:
         return 0;
      }
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
      switch(p_174885_1_) {
      case 0:
         this.field_145946_k = p_174885_2_;
         break;
      case 1:
         this.field_184278_m = p_174885_2_;
      }

   }

   public int func_174890_g() {
      return 2;
   }

   public void func_174888_l() {
      this.field_145945_j.clear();
   }
}
