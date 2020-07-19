package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveIndoors extends EntityAIBase {
   private final EntityCreature field_75424_a;
   private VillageDoorInfo field_75422_b;
   private int field_75423_c = -1;
   private int field_75421_d = -1;

   public EntityAIMoveIndoors(EntityCreature p_i1637_1_) {
      this.field_75424_a = p_i1637_1_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      BlockPos blockpos = new BlockPos(this.field_75424_a);
      if ((!this.field_75424_a.field_70170_p.func_72935_r() || this.field_75424_a.field_70170_p.func_72896_J() && !this.field_75424_a.field_70170_p.func_180494_b(blockpos).func_76738_d()) && this.field_75424_a.field_70170_p.field_73011_w.func_191066_m()) {
         if (this.field_75424_a.func_70681_au().nextInt(50) != 0) {
            return false;
         } else if (this.field_75423_c != -1 && this.field_75424_a.func_70092_e((double)this.field_75423_c, this.field_75424_a.field_70163_u, (double)this.field_75421_d) < 4.0D) {
            return false;
         } else {
            Village village = this.field_75424_a.field_70170_p.func_175714_ae().func_176056_a(blockpos, 14);
            if (village == null) {
               return false;
            } else {
               this.field_75422_b = village.func_179863_c(blockpos);
               return this.field_75422_b != null;
            }
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return !this.field_75424_a.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.field_75423_c = -1;
      BlockPos blockpos = this.field_75422_b.func_179856_e();
      int i = blockpos.func_177958_n();
      int j = blockpos.func_177956_o();
      int k = blockpos.func_177952_p();
      if (this.field_75424_a.func_174818_b(blockpos) > 256.0D) {
         Vec3d vec3d = RandomPositionGenerator.func_75464_a(this.field_75424_a, 14, 3, new Vec3d((double)i + 0.5D, (double)j, (double)k + 0.5D));
         if (vec3d != null) {
            this.field_75424_a.func_70661_as().func_75492_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
         }
      } else {
         this.field_75424_a.func_70661_as().func_75492_a((double)i + 0.5D, (double)j, (double)k + 0.5D, 1.0D);
      }

   }

   public void func_75251_c() {
      this.field_75423_c = this.field_75422_b.func_179856_e().func_177958_n();
      this.field_75421_d = this.field_75422_b.func_179856_e().func_177952_p();
      this.field_75422_b = null;
   }
}
