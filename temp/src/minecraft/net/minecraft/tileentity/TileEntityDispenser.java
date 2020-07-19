package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;

public class TileEntityDispenser extends TileEntityLockableLoot {
   private static final Random field_174913_f = new Random();
   private NonNullList<ItemStack> field_146022_i = NonNullList.<ItemStack>func_191197_a(9, ItemStack.field_190927_a);

   public int func_70302_i_() {
      return 9;
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_146022_i) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public int func_146017_i() {
      this.func_184281_d((EntityPlayer)null);
      int i = -1;
      int j = 1;

      for(int k = 0; k < this.field_146022_i.size(); ++k) {
         if (!((ItemStack)this.field_146022_i.get(k)).func_190926_b() && field_174913_f.nextInt(j++) == 0) {
            i = k;
         }
      }

      return i;
   }

   public int func_146019_a(ItemStack p_146019_1_) {
      for(int i = 0; i < this.field_146022_i.size(); ++i) {
         if (((ItemStack)this.field_146022_i.get(i)).func_190926_b()) {
            this.func_70299_a(i, p_146019_1_);
            return i;
         }
      }

      return -1;
   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_190577_o : "container.dispenser";
   }

   public static void func_189678_a(DataFixer p_189678_0_) {
      p_189678_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityDispenser.class, new String[]{"Items"}));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_146022_i = NonNullList.<ItemStack>func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(p_145839_1_)) {
         ItemStackHelper.func_191283_b(p_145839_1_, this.field_146022_i);
      }

      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_190577_o = p_145839_1_.func_74779_i("CustomName");
      }

   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      if (!this.func_184282_c(p_189515_1_)) {
         ItemStackHelper.func_191282_a(p_189515_1_, this.field_146022_i);
      }

      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_190577_o);
      }

      return p_189515_1_;
   }

   public int func_70297_j_() {
      return 64;
   }

   public String func_174875_k() {
      return "minecraft:dispenser";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      this.func_184281_d(p_174876_2_);
      return new ContainerDispenser(p_174876_1_, this);
   }

   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_146022_i;
   }
}
