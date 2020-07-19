package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.math.Vec3d;

public class EntityAILlamaFollowCaravan extends EntityAIBase {
   public EntityLlama field_190859_a;
   private double field_190860_b;
   private int field_190861_c;

   public EntityAILlamaFollowCaravan(EntityLlama p_i47305_1_, double p_i47305_2_) {
      this.field_190859_a = p_i47305_1_;
      this.field_190860_b = p_i47305_2_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.field_190859_a.func_110167_bD() && !this.field_190859_a.func_190718_dR()) {
         List<EntityLlama> list = this.field_190859_a.field_70170_p.<EntityLlama>func_72872_a(this.field_190859_a.getClass(), this.field_190859_a.func_174813_aQ().func_72314_b(9.0D, 4.0D, 9.0D));
         EntityLlama entityllama = null;
         double d0 = Double.MAX_VALUE;

         for(EntityLlama entityllama1 : list) {
            if (entityllama1.func_190718_dR() && !entityllama1.func_190712_dQ()) {
               double d1 = this.field_190859_a.func_70068_e(entityllama1);
               if (d1 <= d0) {
                  d0 = d1;
                  entityllama = entityllama1;
               }
            }
         }

         if (entityllama == null) {
            for(EntityLlama entityllama2 : list) {
               if (entityllama2.func_110167_bD() && !entityllama2.func_190712_dQ()) {
                  double d2 = this.field_190859_a.func_70068_e(entityllama2);
                  if (d2 <= d0) {
                     d0 = d2;
                     entityllama = entityllama2;
                  }
               }
            }
         }

         if (entityllama == null) {
            return false;
         } else if (d0 < 4.0D) {
            return false;
         } else if (!entityllama.func_110167_bD() && !this.func_190858_a(entityllama, 1)) {
            return false;
         } else {
            this.field_190859_a.func_190715_a(entityllama);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (this.field_190859_a.func_190718_dR() && this.field_190859_a.func_190716_dS().func_70089_S() && this.func_190858_a(this.field_190859_a, 0)) {
         double d0 = this.field_190859_a.func_70068_e(this.field_190859_a.func_190716_dS());
         if (d0 > 676.0D) {
            if (this.field_190860_b <= 3.0D) {
               this.field_190860_b *= 1.2D;
               this.field_190861_c = 40;
               return true;
            }

            if (this.field_190861_c == 0) {
               return false;
            }
         }

         if (this.field_190861_c > 0) {
            --this.field_190861_c;
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_75251_c() {
      this.field_190859_a.func_190709_dP();
      this.field_190860_b = 2.1D;
   }

   public void func_75246_d() {
      if (this.field_190859_a.func_190718_dR()) {
         EntityLlama entityllama = this.field_190859_a.func_190716_dS();
         double d0 = (double)this.field_190859_a.func_70032_d(entityllama);
         float f = 2.0F;
         Vec3d vec3d = (new Vec3d(entityllama.field_70165_t - this.field_190859_a.field_70165_t, entityllama.field_70163_u - this.field_190859_a.field_70163_u, entityllama.field_70161_v - this.field_190859_a.field_70161_v)).func_72432_b().func_186678_a(Math.max(d0 - 2.0D, 0.0D));
         this.field_190859_a.func_70661_as().func_75492_a(this.field_190859_a.field_70165_t + vec3d.field_72450_a, this.field_190859_a.field_70163_u + vec3d.field_72448_b, this.field_190859_a.field_70161_v + vec3d.field_72449_c, this.field_190860_b);
      }
   }

   private boolean func_190858_a(EntityLlama p_190858_1_, int p_190858_2_) {
      if (p_190858_2_ > 8) {
         return false;
      } else if (p_190858_1_.func_190718_dR()) {
         if (p_190858_1_.func_190716_dS().func_110167_bD()) {
            return true;
         } else {
            EntityLlama entityllama = p_190858_1_.func_190716_dS();
            ++p_190858_2_;
            return this.func_190858_a(entityllama, p_190858_2_);
         }
      } else {
         return false;
      }
   }
}
