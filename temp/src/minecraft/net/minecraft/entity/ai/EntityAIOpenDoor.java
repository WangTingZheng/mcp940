package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   boolean field_75361_i;
   int field_75360_j;

   public EntityAIOpenDoor(EntityLiving p_i1644_1_, boolean p_i1644_2_) {
      super(p_i1644_1_);
      this.field_75356_a = p_i1644_1_;
      this.field_75361_i = p_i1644_2_;
   }

   public boolean func_75253_b() {
      return this.field_75361_i && this.field_75360_j > 0 && super.func_75253_b();
   }

   public void func_75249_e() {
      this.field_75360_j = 20;
      this.field_151504_e.func_176512_a(this.field_75356_a.field_70170_p, this.field_179507_b, true);
   }

   public void func_75251_c() {
      if (this.field_75361_i) {
         this.field_151504_e.func_176512_a(this.field_75356_a.field_70170_p, this.field_179507_b, false);
      }

   }

   public void func_75246_d() {
      --this.field_75360_j;
      super.func_75246_d();
   }
}
