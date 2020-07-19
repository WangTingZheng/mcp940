package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityZombie;

public class EntityAIZombieAttack extends EntityAIAttackMelee {
   private final EntityZombie field_188494_h;
   private int field_188495_i;

   public EntityAIZombieAttack(EntityZombie p_i46803_1_, double p_i46803_2_, boolean p_i46803_4_) {
      super(p_i46803_1_, p_i46803_2_, p_i46803_4_);
      this.field_188494_h = p_i46803_1_;
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.field_188495_i = 0;
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.field_188494_h.func_184733_a(false);
   }

   public void func_75246_d() {
      super.func_75246_d();
      ++this.field_188495_i;
      if (this.field_188495_i >= 5 && this.field_75439_d < 10) {
         this.field_188494_h.func_184733_a(true);
      } else {
         this.field_188494_h.func_184733_a(false);
      }

   }
}
