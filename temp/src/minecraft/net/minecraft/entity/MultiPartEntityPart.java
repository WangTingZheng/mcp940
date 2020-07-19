package net.minecraft.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class MultiPartEntityPart extends Entity {
   public final IEntityMultiPart field_70259_a;
   public final String field_146032_b;

   public MultiPartEntityPart(IEntityMultiPart p_i1697_1_, String p_i1697_2_, float p_i1697_3_, float p_i1697_4_) {
      super(p_i1697_1_.func_82194_d());
      this.func_70105_a(p_i1697_3_, p_i1697_4_);
      this.field_70259_a = p_i1697_1_;
      this.field_146032_b = p_i1697_2_;
   }

   protected void func_70088_a() {
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
   }

   public boolean func_70067_L() {
      return true;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      return this.func_180431_b(p_70097_1_) ? false : this.field_70259_a.func_70965_a(this, p_70097_1_, p_70097_2_);
   }

   public boolean func_70028_i(Entity p_70028_1_) {
      return this == p_70028_1_ || this.field_70259_a == p_70028_1_;
   }
}
