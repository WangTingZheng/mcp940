package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIFollowGolem extends EntityAIBase {
   private final EntityVillager field_75294_a;
   private EntityIronGolem field_75292_b;
   private int field_75293_c;
   private boolean field_75291_d;

   public EntityAIFollowGolem(EntityVillager p_i1656_1_) {
      this.field_75294_a = p_i1656_1_;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.field_75294_a.func_70874_b() >= 0) {
         return false;
      } else if (!this.field_75294_a.field_70170_p.func_72935_r()) {
         return false;
      } else {
         List<EntityIronGolem> list = this.field_75294_a.field_70170_p.<EntityIronGolem>func_72872_a(EntityIronGolem.class, this.field_75294_a.func_174813_aQ().func_72314_b(6.0D, 2.0D, 6.0D));
         if (list.isEmpty()) {
            return false;
         } else {
            for(EntityIronGolem entityirongolem : list) {
               if (entityirongolem.func_70853_p() > 0) {
                  this.field_75292_b = entityirongolem;
                  break;
               }
            }

            return this.field_75292_b != null;
         }
      }
   }

   public boolean func_75253_b() {
      return this.field_75292_b.func_70853_p() > 0;
   }

   public void func_75249_e() {
      this.field_75293_c = this.field_75294_a.func_70681_au().nextInt(320);
      this.field_75291_d = false;
      this.field_75292_b.func_70661_as().func_75499_g();
   }

   public void func_75251_c() {
      this.field_75292_b = null;
      this.field_75294_a.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      this.field_75294_a.func_70671_ap().func_75651_a(this.field_75292_b, 30.0F, 30.0F);
      if (this.field_75292_b.func_70853_p() == this.field_75293_c) {
         this.field_75294_a.func_70661_as().func_75497_a(this.field_75292_b, 0.5D);
         this.field_75291_d = true;
      }

      if (this.field_75291_d && this.field_75294_a.func_70068_e(this.field_75292_b) < 4.0D) {
         this.field_75292_b.func_70851_e(false);
         this.field_75294_a.func_70661_as().func_75499_g();
      }

   }
}
