package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper {
   private final EntityLiving field_75663_a;
   protected boolean field_75662_b;

   public EntityJumpHelper(EntityLiving p_i1612_1_) {
      this.field_75663_a = p_i1612_1_;
   }

   public void func_75660_a() {
      this.field_75662_b = true;
   }

   public void func_75661_b() {
      this.field_75663_a.func_70637_d(this.field_75662_b);
      this.field_75662_b = false;
   }
}
