package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity implements ILockableContainer {
   private LockCode field_174901_a = LockCode.field_180162_a;

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_174901_a = LockCode.func_180158_b(p_145839_1_);
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      if (this.field_174901_a != null) {
         this.field_174901_a.func_180157_a(p_189515_1_);
      }

      return p_189515_1_;
   }

   public boolean func_174893_q_() {
      return this.field_174901_a != null && !this.field_174901_a.func_180160_a();
   }

   public LockCode func_174891_i() {
      return this.field_174901_a;
   }

   public void func_174892_a(LockCode p_174892_1_) {
      this.field_174901_a = p_174892_1_;
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }
}
