package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase {
   private final AbstractHorse field_111180_a;
   private final double field_111178_b;
   private double field_111179_c;
   private double field_111176_d;
   private double field_111177_e;

   public EntityAIRunAroundLikeCrazy(AbstractHorse p_i1653_1_, double p_i1653_2_) {
      this.field_111180_a = p_i1653_1_;
      this.field_111178_b = p_i1653_2_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.field_111180_a.func_110248_bS() && this.field_111180_a.func_184207_aI()) {
         Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.field_111180_a, 5, 4);
         if (vec3d == null) {
            return false;
         } else {
            this.field_111179_c = vec3d.field_72450_a;
            this.field_111176_d = vec3d.field_72448_b;
            this.field_111177_e = vec3d.field_72449_c;
            return true;
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.field_111180_a.func_70661_as().func_75492_a(this.field_111179_c, this.field_111176_d, this.field_111177_e, this.field_111178_b);
   }

   public boolean func_75253_b() {
      return !this.field_111180_a.func_110248_bS() && !this.field_111180_a.func_70661_as().func_75500_f() && this.field_111180_a.func_184207_aI();
   }

   public void func_75246_d() {
      if (!this.field_111180_a.func_110248_bS() && this.field_111180_a.func_70681_au().nextInt(50) == 0) {
         Entity entity = (Entity)this.field_111180_a.func_184188_bt().get(0);
         if (entity == null) {
            return;
         }

         if (entity instanceof EntityPlayer) {
            int i = this.field_111180_a.func_110252_cg();
            int j = this.field_111180_a.func_190676_dC();
            if (j > 0 && this.field_111180_a.func_70681_au().nextInt(j) < i) {
               this.field_111180_a.func_110263_g((EntityPlayer)entity);
               return;
            }

            this.field_111180_a.func_110198_t(5);
         }

         this.field_111180_a.func_184226_ay();
         this.field_111180_a.func_190687_dF();
         this.field_111180_a.field_70170_p.func_72960_a(this.field_111180_a, (byte)6);
      }

   }
}
