package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
   private final EntityCreature field_75431_a;
   private EntityLivingBase field_75429_b;
   private double field_75430_c;
   private double field_75427_d;
   private double field_75428_e;
   private final double field_75425_f;
   private final float field_75426_g;

   public EntityAIMoveTowardsTarget(EntityCreature p_i1640_1_, double p_i1640_2_, float p_i1640_4_) {
      this.field_75431_a = p_i1640_1_;
      this.field_75425_f = p_i1640_2_;
      this.field_75426_g = p_i1640_4_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      this.field_75429_b = this.field_75431_a.func_70638_az();
      if (this.field_75429_b == null) {
         return false;
      } else if (this.field_75429_b.func_70068_e(this.field_75431_a) > (double)(this.field_75426_g * this.field_75426_g)) {
         return false;
      } else {
         Vec3d vec3d = RandomPositionGenerator.func_75464_a(this.field_75431_a, 16, 7, new Vec3d(this.field_75429_b.field_70165_t, this.field_75429_b.field_70163_u, this.field_75429_b.field_70161_v));
         if (vec3d == null) {
            return false;
         } else {
            this.field_75430_c = vec3d.field_72450_a;
            this.field_75427_d = vec3d.field_72448_b;
            this.field_75428_e = vec3d.field_72449_c;
            return true;
         }
      }
   }

   public boolean func_75253_b() {
      return !this.field_75431_a.func_70661_as().func_75500_f() && this.field_75429_b.func_70089_S() && this.field_75429_b.func_70068_e(this.field_75431_a) < (double)(this.field_75426_g * this.field_75426_g);
   }

   public void func_75251_c() {
      this.field_75429_b = null;
   }

   public void func_75249_e() {
      this.field_75431_a.func_70661_as().func_75492_a(this.field_75430_c, this.field_75427_d, this.field_75428_e, this.field_75425_f);
   }
}
