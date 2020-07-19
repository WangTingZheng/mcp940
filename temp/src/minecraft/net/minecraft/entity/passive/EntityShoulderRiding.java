package net.minecraft.entity.passive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityShoulderRiding extends EntityTameable {
   private int field_191996_bB;

   public EntityShoulderRiding(World p_i47410_1_) {
      super(p_i47410_1_);
   }

   public boolean func_191994_f(EntityPlayer p_191994_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74778_a("id", this.func_70022_Q());
      this.func_189511_e(nbttagcompound);
      if (p_191994_1_.func_192027_g(nbttagcompound)) {
         this.field_70170_p.func_72900_e(this);
         return true;
      } else {
         return false;
      }
   }

   public void func_70071_h_() {
      ++this.field_191996_bB;
      super.func_70071_h_();
   }

   public boolean func_191995_du() {
      return this.field_191996_bB > 100;
   }
}
