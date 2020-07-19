package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class ProjectileHelper {
   public static RayTraceResult func_188802_a(Entity p_188802_0_, boolean p_188802_1_, boolean p_188802_2_, Entity p_188802_3_) {
      double d0 = p_188802_0_.field_70165_t;
      double d1 = p_188802_0_.field_70163_u;
      double d2 = p_188802_0_.field_70161_v;
      double d3 = p_188802_0_.field_70159_w;
      double d4 = p_188802_0_.field_70181_x;
      double d5 = p_188802_0_.field_70179_y;
      World world = p_188802_0_.field_70170_p;
      Vec3d vec3d = new Vec3d(d0, d1, d2);
      Vec3d vec3d1 = new Vec3d(d0 + d3, d1 + d4, d2 + d5);
      RayTraceResult raytraceresult = world.func_147447_a(vec3d, vec3d1, false, true, false);
      if (p_188802_1_) {
         if (raytraceresult != null) {
            vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
         }

         Entity entity = null;
         List<Entity> list = world.func_72839_b(p_188802_0_, p_188802_0_.func_174813_aQ().func_72321_a(d3, d4, d5).func_186662_g(1.0D));
         double d6 = 0.0D;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = list.get(i);
            if (entity1.func_70067_L() && (p_188802_2_ || !entity1.func_70028_i(p_188802_3_)) && !entity1.field_70145_X) {
               AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
               RayTraceResult raytraceresult1 = axisalignedbb.func_72327_a(vec3d, vec3d1);
               if (raytraceresult1 != null) {
                  double d7 = vec3d.func_72436_e(raytraceresult1.field_72307_f);
                  if (d7 < d6 || d6 == 0.0D) {
                     entity = entity1;
                     d6 = d7;
                  }
               }
            }
         }

         if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
         }
      }

      return raytraceresult;
   }

   public static final void func_188803_a(Entity p_188803_0_, float p_188803_1_) {
      double d0 = p_188803_0_.field_70159_w;
      double d1 = p_188803_0_.field_70181_x;
      double d2 = p_188803_0_.field_70179_y;
      float f = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
      p_188803_0_.field_70177_z = (float)(MathHelper.func_181159_b(d2, d0) * 57.2957763671875D) + 90.0F;

      for(p_188803_0_.field_70125_A = (float)(MathHelper.func_181159_b((double)f, d1) * 57.2957763671875D) - 90.0F; p_188803_0_.field_70125_A - p_188803_0_.field_70127_C < -180.0F; p_188803_0_.field_70127_C -= 360.0F) {
         ;
      }

      while(p_188803_0_.field_70125_A - p_188803_0_.field_70127_C >= 180.0F) {
         p_188803_0_.field_70127_C += 360.0F;
      }

      while(p_188803_0_.field_70177_z - p_188803_0_.field_70126_B < -180.0F) {
         p_188803_0_.field_70126_B -= 360.0F;
      }

      while(p_188803_0_.field_70177_z - p_188803_0_.field_70126_B >= 180.0F) {
         p_188803_0_.field_70126_B += 360.0F;
      }

      p_188803_0_.field_70125_A = p_188803_0_.field_70127_C + (p_188803_0_.field_70125_A - p_188803_0_.field_70127_C) * p_188803_1_;
      p_188803_0_.field_70177_z = p_188803_0_.field_70126_B + (p_188803_0_.field_70177_z - p_188803_0_.field_70126_B) * p_188803_1_;
   }
}
