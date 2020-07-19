package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
   public EntitySnowball(World p_i1773_1_) {
      super(p_i1773_1_);
   }

   public EntitySnowball(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
      super(p_i1774_1_, p_i1774_2_);
   }

   public EntitySnowball(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
      super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
   }

   public static void func_189662_a(DataFixer p_189662_0_) {
      EntityThrowable.func_189661_a(p_189662_0_, "Snowball");
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 3) {
         for(int i = 0; i < 8; ++i) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.SNOWBALL, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected void func_70184_a(RayTraceResult p_70184_1_) {
      if (p_70184_1_.field_72308_g != null) {
         int i = 0;
         if (p_70184_1_.field_72308_g instanceof EntityBlaze) {
            i = 3;
         }

         p_70184_1_.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), (float)i);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)3);
         this.func_70106_y();
      }

   }
}
