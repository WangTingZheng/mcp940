package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase {
   private final EntityCreature field_75273_a;

   public EntityAIRestrictSun(EntityCreature p_i1652_1_) {
      this.field_75273_a = p_i1652_1_;
   }

   public boolean func_75250_a() {
      return this.field_75273_a.field_70170_p.func_72935_r() && this.field_75273_a.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b();
   }

   public void func_75249_e() {
      ((PathNavigateGround)this.field_75273_a.func_70661_as()).func_179685_e(true);
   }

   public void func_75251_c() {
      ((PathNavigateGround)this.field_75273_a.func_70661_as()).func_179685_e(false);
   }
}
