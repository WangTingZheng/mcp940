package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAILookAtVillager extends EntityAIBase {
   private final EntityIronGolem field_75397_a;
   private EntityVillager field_75395_b;
   private int field_75396_c;

   public EntityAILookAtVillager(EntityIronGolem p_i1643_1_) {
      this.field_75397_a = p_i1643_1_;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.field_75397_a.field_70170_p.func_72935_r()) {
         return false;
      } else if (this.field_75397_a.func_70681_au().nextInt(8000) != 0) {
         return false;
      } else {
         this.field_75395_b = (EntityVillager)this.field_75397_a.field_70170_p.func_72857_a(EntityVillager.class, this.field_75397_a.func_174813_aQ().func_72314_b(6.0D, 2.0D, 6.0D), this.field_75397_a);
         return this.field_75395_b != null;
      }
   }

   public boolean func_75253_b() {
      return this.field_75396_c > 0;
   }

   public void func_75249_e() {
      this.field_75396_c = 400;
      this.field_75397_a.func_70851_e(true);
   }

   public void func_75251_c() {
      this.field_75397_a.func_70851_e(false);
      this.field_75395_b = null;
   }

   public void func_75246_d() {
      this.field_75397_a.func_70671_ap().func_75651_a(this.field_75395_b, 30.0F, 30.0F);
      --this.field_75396_c;
   }
}
