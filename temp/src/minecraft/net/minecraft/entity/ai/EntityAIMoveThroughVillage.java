package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveThroughVillage extends EntityAIBase {
   private final EntityCreature field_75420_a;
   private final double field_75418_b;
   private Path field_75419_c;
   private VillageDoorInfo field_75416_d;
   private final boolean field_75417_e;
   private final List<VillageDoorInfo> field_75415_f = Lists.<VillageDoorInfo>newArrayList();

   public EntityAIMoveThroughVillage(EntityCreature p_i1638_1_, double p_i1638_2_, boolean p_i1638_4_) {
      this.field_75420_a = p_i1638_1_;
      this.field_75418_b = p_i1638_2_;
      this.field_75417_e = p_i1638_4_;
      this.func_75248_a(1);
      if (!(p_i1638_1_.func_70661_as() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   public boolean func_75250_a() {
      this.func_75414_f();
      if (this.field_75417_e && this.field_75420_a.field_70170_p.func_72935_r()) {
         return false;
      } else {
         Village village = this.field_75420_a.field_70170_p.func_175714_ae().func_176056_a(new BlockPos(this.field_75420_a), 0);
         if (village == null) {
            return false;
         } else {
            this.field_75416_d = this.func_75412_a(village);
            if (this.field_75416_d == null) {
               return false;
            } else {
               PathNavigateGround pathnavigateground = (PathNavigateGround)this.field_75420_a.func_70661_as();
               boolean flag = pathnavigateground.func_179686_g();
               pathnavigateground.func_179688_b(false);
               this.field_75419_c = pathnavigateground.func_179680_a(this.field_75416_d.func_179852_d());
               pathnavigateground.func_179688_b(flag);
               if (this.field_75419_c != null) {
                  return true;
               } else {
                  Vec3d vec3d = RandomPositionGenerator.func_75464_a(this.field_75420_a, 10, 7, new Vec3d((double)this.field_75416_d.func_179852_d().func_177958_n(), (double)this.field_75416_d.func_179852_d().func_177956_o(), (double)this.field_75416_d.func_179852_d().func_177952_p()));
                  if (vec3d == null) {
                     return false;
                  } else {
                     pathnavigateground.func_179688_b(false);
                     this.field_75419_c = this.field_75420_a.func_70661_as().func_75488_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
                     pathnavigateground.func_179688_b(flag);
                     return this.field_75419_c != null;
                  }
               }
            }
         }
      }
   }

   public boolean func_75253_b() {
      if (this.field_75420_a.func_70661_as().func_75500_f()) {
         return false;
      } else {
         float f = this.field_75420_a.field_70130_N + 4.0F;
         return this.field_75420_a.func_174818_b(this.field_75416_d.func_179852_d()) > (double)(f * f);
      }
   }

   public void func_75249_e() {
      this.field_75420_a.func_70661_as().func_75484_a(this.field_75419_c, this.field_75418_b);
   }

   public void func_75251_c() {
      if (this.field_75420_a.func_70661_as().func_75500_f() || this.field_75420_a.func_174818_b(this.field_75416_d.func_179852_d()) < 16.0D) {
         this.field_75415_f.add(this.field_75416_d);
      }

   }

   private VillageDoorInfo func_75412_a(Village p_75412_1_) {
      VillageDoorInfo villagedoorinfo = null;
      int i = Integer.MAX_VALUE;

      for(VillageDoorInfo villagedoorinfo1 : p_75412_1_.func_75558_f()) {
         int j = villagedoorinfo1.func_75474_b(MathHelper.func_76128_c(this.field_75420_a.field_70165_t), MathHelper.func_76128_c(this.field_75420_a.field_70163_u), MathHelper.func_76128_c(this.field_75420_a.field_70161_v));
         if (j < i && !this.func_75413_a(villagedoorinfo1)) {
            villagedoorinfo = villagedoorinfo1;
            i = j;
         }
      }

      return villagedoorinfo;
   }

   private boolean func_75413_a(VillageDoorInfo p_75413_1_) {
      for(VillageDoorInfo villagedoorinfo : this.field_75415_f) {
         if (p_75413_1_.func_179852_d().equals(villagedoorinfo.func_179852_d())) {
            return true;
         }
      }

      return false;
   }

   private void func_75414_f() {
      if (this.field_75415_f.size() > 15) {
         this.field_75415_f.remove(0);
      }

   }
}
