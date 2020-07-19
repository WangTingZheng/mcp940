package net.minecraft.entity.ai;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;

public class EntityAIWander extends EntityAIBase {
   protected final EntityCreature field_75457_a;
   protected double field_75455_b;
   protected double field_75456_c;
   protected double field_75453_d;
   protected final double field_75454_e;
   protected int field_179481_f;
   protected boolean field_179482_g;

   public EntityAIWander(EntityCreature p_i1648_1_, double p_i1648_2_) {
      this(p_i1648_1_, p_i1648_2_, 120);
   }

   public EntityAIWander(EntityCreature p_i45887_1_, double p_i45887_2_, int p_i45887_4_) {
      this.field_75457_a = p_i45887_1_;
      this.field_75454_e = p_i45887_2_;
      this.field_179481_f = p_i45887_4_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.field_179482_g) {
         if (this.field_75457_a.func_70654_ax() >= 100) {
            return false;
         }

         if (this.field_75457_a.func_70681_au().nextInt(this.field_179481_f) != 0) {
            return false;
         }
      }

      Vec3d vec3d = this.func_190864_f();
      if (vec3d == null) {
         return false;
      } else {
         this.field_75455_b = vec3d.field_72450_a;
         this.field_75456_c = vec3d.field_72448_b;
         this.field_75453_d = vec3d.field_72449_c;
         this.field_179482_g = false;
         return true;
      }
   }

   @Nullable
   protected Vec3d func_190864_f() {
      return RandomPositionGenerator.func_75463_a(this.field_75457_a, 10, 7);
   }

   public boolean func_75253_b() {
      return !this.field_75457_a.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.field_75457_a.func_70661_as().func_75492_a(this.field_75455_b, this.field_75456_c, this.field_75453_d, this.field_75454_e);
   }

   public void func_179480_f() {
      this.field_179482_g = true;
   }

   public void func_179479_b(int p_179479_1_) {
      this.field_179481_f = p_179479_1_;
   }
}
