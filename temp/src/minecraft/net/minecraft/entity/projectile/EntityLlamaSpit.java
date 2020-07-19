package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityLlamaSpit extends Entity implements IProjectile {
   public EntityLlama field_190539_a;
   private NBTTagCompound field_190540_b;

   public EntityLlamaSpit(World p_i47272_1_) {
      super(p_i47272_1_);
   }

   public EntityLlamaSpit(World p_i47273_1_, EntityLlama p_i47273_2_) {
      super(p_i47273_1_);
      this.field_190539_a = p_i47273_2_;
      this.func_70107_b(p_i47273_2_.field_70165_t - (double)(p_i47273_2_.field_70130_N + 1.0F) * 0.5D * (double)MathHelper.func_76126_a(p_i47273_2_.field_70761_aq * 0.017453292F), p_i47273_2_.field_70163_u + (double)p_i47273_2_.func_70047_e() - 0.10000000149011612D, p_i47273_2_.field_70161_v + (double)(p_i47273_2_.field_70130_N + 1.0F) * 0.5D * (double)MathHelper.func_76134_b(p_i47273_2_.field_70761_aq * 0.017453292F));
      this.func_70105_a(0.25F, 0.25F);
   }

   public EntityLlamaSpit(World p_i47274_1_, double p_i47274_2_, double p_i47274_4_, double p_i47274_6_, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
      super(p_i47274_1_);
      this.func_70107_b(p_i47274_2_, p_i47274_4_, p_i47274_6_);

      for(int i = 0; i < 7; ++i) {
         double d0 = 0.4D + 0.1D * (double)i;
         p_i47274_1_.func_175688_a(EnumParticleTypes.SPIT, p_i47274_2_, p_i47274_4_, p_i47274_6_, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
      }

      this.field_70159_w = p_i47274_8_;
      this.field_70181_x = p_i47274_10_;
      this.field_70179_y = p_i47274_12_;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_190540_b != null) {
         this.func_190537_j();
      }

      Vec3d vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult raytraceresult = this.field_70170_p.func_72933_a(vec3d, vec3d1);
      vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (raytraceresult != null) {
         vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
      }

      Entity entity = this.func_190538_a(vec3d, vec3d1);
      if (entity != null) {
         raytraceresult = new RayTraceResult(entity);
      }

      if (raytraceresult != null) {
         this.func_190536_a(raytraceresult);
      }

      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);

      for(this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f) * 57.2957763671875D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
         ;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
      this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
      float f1 = 0.99F;
      float f2 = 0.06F;
      if (!this.field_70170_p.func_72875_a(this.func_174813_aQ(), Material.field_151579_a)) {
         this.func_70106_y();
      } else if (this.func_70090_H()) {
         this.func_70106_y();
      } else {
         this.field_70159_w *= 0.9900000095367432D;
         this.field_70181_x *= 0.9900000095367432D;
         this.field_70179_y *= 0.9900000095367432D;
         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.05999999865889549D;
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         this.field_70125_A = (float)(MathHelper.func_181159_b(p_70016_3_, (double)f) * 57.2957763671875D);
         this.field_70177_z = (float)(MathHelper.func_181159_b(p_70016_1_, p_70016_5_) * 57.2957763671875D);
         this.field_70127_C = this.field_70125_A;
         this.field_70126_B = this.field_70177_z;
         this.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      }

   }

   @Nullable
   private Entity func_190538_a(Vec3d p_190538_1_, Vec3d p_190538_2_) {
      Entity entity = null;
      List<Entity> list = this.field_70170_p.func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0D));
      double d0 = 0.0D;

      for(Entity entity1 : list) {
         if (entity1 != this.field_190539_a) {
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
            RayTraceResult raytraceresult = axisalignedbb.func_72327_a(p_190538_1_, p_190538_2_);
            if (raytraceresult != null) {
               double d1 = p_190538_1_.func_72436_e(raytraceresult.field_72307_f);
               if (d1 < d0 || d0 == 0.0D) {
                  entity = entity1;
                  d0 = d1;
               }
            }
         }
      }

      return entity;
   }

   public void func_70186_c(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
      float f = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
      p_70186_1_ = p_70186_1_ / (double)f;
      p_70186_3_ = p_70186_3_ / (double)f;
      p_70186_5_ = p_70186_5_ / (double)f;
      p_70186_1_ = p_70186_1_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_3_ = p_70186_3_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_5_ = p_70186_5_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_1_ = p_70186_1_ * (double)p_70186_7_;
      p_70186_3_ = p_70186_3_ * (double)p_70186_7_;
      p_70186_5_ = p_70186_5_ * (double)p_70186_7_;
      this.field_70159_w = p_70186_1_;
      this.field_70181_x = p_70186_3_;
      this.field_70179_y = p_70186_5_;
      float f1 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
      this.field_70177_z = (float)(MathHelper.func_181159_b(p_70186_1_, p_70186_5_) * 57.2957763671875D);
      this.field_70125_A = (float)(MathHelper.func_181159_b(p_70186_3_, (double)f1) * 57.2957763671875D);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
   }

   public void func_190536_a(RayTraceResult p_190536_1_) {
      if (p_190536_1_.field_72308_g != null && this.field_190539_a != null) {
         p_190536_1_.field_72308_g.func_70097_a(DamageSource.func_188403_a(this, this.field_190539_a).func_76349_b(), 1.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70106_y();
      }

   }

   protected void func_70088_a() {
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      if (p_70037_1_.func_150297_b("Owner", 10)) {
         this.field_190540_b = p_70037_1_.func_74775_l("Owner");
      }

   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      if (this.field_190539_a != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         UUID uuid = this.field_190539_a.func_110124_au();
         nbttagcompound.func_186854_a("OwnerUUID", uuid);
         p_70014_1_.func_74782_a("Owner", nbttagcompound);
      }

   }

   private void func_190537_j() {
      if (this.field_190540_b != null && this.field_190540_b.func_186855_b("OwnerUUID")) {
         UUID uuid = this.field_190540_b.func_186857_a("OwnerUUID");

         for(EntityLlama entityllama : this.field_70170_p.func_72872_a(EntityLlama.class, this.func_174813_aQ().func_186662_g(15.0D))) {
            if (entityllama.func_110124_au().equals(uuid)) {
               this.field_190539_a = entityllama;
               break;
            }
         }
      }

      this.field_190540_b = null;
   }
}
