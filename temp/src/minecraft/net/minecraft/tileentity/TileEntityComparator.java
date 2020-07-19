package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {
   private int field_145997_a;

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74768_a("OutputSignal", this.field_145997_a);
      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145997_a = p_145839_1_.func_74762_e("OutputSignal");
   }

   public int func_145996_a() {
      return this.field_145997_a;
   }

   public void func_145995_a(int p_145995_1_) {
      this.field_145997_a = p_145995_1_;
   }
}
