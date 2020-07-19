package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
   public int field_92057_e = 1;

   public EntityLargeFireball(World p_i1767_1_) {
      super(p_i1767_1_);
   }

   public EntityLargeFireball(World p_i1768_1_, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
      super(p_i1768_1_, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
   }

   public EntityLargeFireball(World p_i1769_1_, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_) {
      super(p_i1769_1_, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
   }

   protected void func_70227_a(RayTraceResult p_70227_1_) {
      if (!this.field_70170_p.field_72995_K) {
         if (p_70227_1_.field_72308_g != null) {
            p_70227_1_.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 6.0F);
            this.func_174815_a(this.field_70235_a, p_70227_1_.field_72308_g);
         }

         boolean flag = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
         this.field_70170_p.func_72885_a((Entity)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)this.field_92057_e, flag, flag);
         this.func_70106_y();
      }

   }

   public static void func_189744_a(DataFixer p_189744_0_) {
      EntityFireball.func_189743_a(p_189744_0_, "Fireball");
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("ExplosionPower", this.field_92057_e);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("ExplosionPower", 99)) {
         this.field_92057_e = p_70037_1_.func_74762_e("ExplosionPower");
      }

   }
}
