package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.Vec3d;

public class EntityAIPlay extends EntityAIBase {
   private final EntityVillager field_75262_a;
   private EntityLivingBase field_75260_b;
   private final double field_75261_c;
   private int field_75259_d;

   public EntityAIPlay(EntityVillager p_i1646_1_, double p_i1646_2_) {
      this.field_75262_a = p_i1646_1_;
      this.field_75261_c = p_i1646_2_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.field_75262_a.func_70874_b() >= 0) {
         return false;
      } else if (this.field_75262_a.func_70681_au().nextInt(400) != 0) {
         return false;
      } else {
         List<EntityVillager> list = this.field_75262_a.field_70170_p.<EntityVillager>func_72872_a(EntityVillager.class, this.field_75262_a.func_174813_aQ().func_72314_b(6.0D, 3.0D, 6.0D));
         double d0 = Double.MAX_VALUE;

         for(EntityVillager entityvillager : list) {
            if (entityvillager != this.field_75262_a && !entityvillager.func_70945_p() && entityvillager.func_70874_b() < 0) {
               double d1 = entityvillager.func_70068_e(this.field_75262_a);
               if (d1 <= d0) {
                  d0 = d1;
                  this.field_75260_b = entityvillager;
               }
            }
         }

         if (this.field_75260_b == null) {
            Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.field_75262_a, 16, 3);
            if (vec3d == null) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean func_75253_b() {
      return this.field_75259_d > 0;
   }

   public void func_75249_e() {
      if (this.field_75260_b != null) {
         this.field_75262_a.func_70939_f(true);
      }

      this.field_75259_d = 1000;
   }

   public void func_75251_c() {
      this.field_75262_a.func_70939_f(false);
      this.field_75260_b = null;
   }

   public void func_75246_d() {
      --this.field_75259_d;
      if (this.field_75260_b != null) {
         if (this.field_75262_a.func_70068_e(this.field_75260_b) > 4.0D) {
            this.field_75262_a.func_70661_as().func_75497_a(this.field_75260_b, this.field_75261_c);
         }
      } else if (this.field_75262_a.func_70661_as().func_75500_f()) {
         Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.field_75262_a, 16, 3);
         if (vec3d == null) {
            return;
         }

         this.field_75262_a.func_70661_as().func_75492_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, this.field_75261_c);
      }

   }
}
