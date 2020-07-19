package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   EntityAnimal field_75348_a;
   EntityAnimal field_75346_b;
   double field_75347_c;
   private int field_75345_d;

   public EntityAIFollowParent(EntityAnimal p_i1626_1_, double p_i1626_2_) {
      this.field_75348_a = p_i1626_1_;
      this.field_75347_c = p_i1626_2_;
   }

   public boolean func_75250_a() {
      if (this.field_75348_a.func_70874_b() >= 0) {
         return false;
      } else {
         List<EntityAnimal> list = this.field_75348_a.field_70170_p.<EntityAnimal>func_72872_a(this.field_75348_a.getClass(), this.field_75348_a.func_174813_aQ().func_72314_b(8.0D, 4.0D, 8.0D));
         EntityAnimal entityanimal = null;
         double d0 = Double.MAX_VALUE;

         for(EntityAnimal entityanimal1 : list) {
            if (entityanimal1.func_70874_b() >= 0) {
               double d1 = this.field_75348_a.func_70068_e(entityanimal1);
               if (d1 <= d0) {
                  d0 = d1;
                  entityanimal = entityanimal1;
               }
            }
         }

         if (entityanimal == null) {
            return false;
         } else if (d0 < 9.0D) {
            return false;
         } else {
            this.field_75346_b = entityanimal;
            return true;
         }
      }
   }

   public boolean func_75253_b() {
      if (this.field_75348_a.func_70874_b() >= 0) {
         return false;
      } else if (!this.field_75346_b.func_70089_S()) {
         return false;
      } else {
         double d0 = this.field_75348_a.func_70068_e(this.field_75346_b);
         return d0 >= 9.0D && d0 <= 256.0D;
      }
   }

   public void func_75249_e() {
      this.field_75345_d = 0;
   }

   public void func_75251_c() {
      this.field_75346_b = null;
   }

   public void func_75246_d() {
      if (--this.field_75345_d <= 0) {
         this.field_75345_d = 10;
         this.field_75348_a.func_70661_as().func_75497_a(this.field_75346_b, this.field_75347_c);
      }
   }
}
