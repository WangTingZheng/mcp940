package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {
   private TileEntityEnderChest field_70488_a;

   public InventoryEnderChest() {
      super("container.enderchest", false, 27);
   }

   public void func_146031_a(TileEntityEnderChest p_146031_1_) {
      this.field_70488_a = p_146031_1_;
   }

   public void func_70486_a(NBTTagList p_70486_1_) {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         this.func_70299_a(i, ItemStack.field_190927_a);
      }

      for(int k = 0; k < p_70486_1_.func_74745_c(); ++k) {
         NBTTagCompound nbttagcompound = p_70486_1_.func_150305_b(k);
         int j = nbttagcompound.func_74771_c("Slot") & 255;
         if (j >= 0 && j < this.func_70302_i_()) {
            this.func_70299_a(j, new ItemStack(nbttagcompound));
         }
      }

   }

   public NBTTagList func_70487_g() {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Slot", (byte)i);
            itemstack.func_77955_b(nbttagcompound);
            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      return this.field_70488_a != null && !this.field_70488_a.func_145971_a(p_70300_1_) ? false : super.func_70300_a(p_70300_1_);
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
      if (this.field_70488_a != null) {
         this.field_70488_a.func_145969_a();
      }

      super.func_174889_b(p_174889_1_);
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
      if (this.field_70488_a != null) {
         this.field_70488_a.func_145970_b();
      }

      super.func_174886_c(p_174886_1_);
      this.field_70488_a = null;
   }
}
