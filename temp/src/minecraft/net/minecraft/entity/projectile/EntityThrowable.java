package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityThrowable extends Entity implements IProjectile {
   private int field_145788_c;
   private int field_145786_d;
   private int field_145787_e;
   private Block field_174853_f;
   protected boolean field_174854_a;
   public int field_70191_b;
   private EntityLivingBase field_70192_c;
   private String field_85053_h;
   private int field_70194_h;
   private int field_70195_i;
   public Entity field_184539_c;
   private int field_184540_av;

   public EntityThrowable(World p_i1776_1_) {
      super(p_i1776_1_);
      this.field_145788_c = -1;
      this.field_145786_d = -1;
      this.field_145787_e = -1;
      this.func_70105_a(0.25F, 0.25F);
   }

   public EntityThrowable(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
      this(p_i1778_1_);
      this.func_70107_b(p_i1778_2_, p_i1778_4_, p_i1778_6_);
   }

   public EntityThrowable(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
      this(p_i1777_1_, p_i1777_2_.field_70165_t, p_i1777_2_.field_70163_u + (double)p_i1777_2_.func_70047_e() - 0.10000000149011612D, p_i1777_2_.field_70161_v);
      this.field_70192_c = p_i1777_2_;
   }

   protected void func_70088_a() {
   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = this.func_174813_aQ().func_72320_b() * 4.0D;
      if (Double.isNaN(d0)) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_70112_1_ < d0 * d0;
   }

   public void func_184538_a(Entity p_184538_1_, float p_184538_2_, float p_184538_3_, float p_184538_4_, float p_184538_5_, float p_184538_6_) {
      float f = -MathHelper.func_76126_a(p_184538_3_ * 0.017453292F) * MathHelper.func_76134_b(p_184538_2_ * 0.017453292F);
      float f1 = -MathHelper.func_76126_a((p_184538_2_ + p_184538_4_) * 0.017453292F);
      float f2 = MathHelper.func_76134_b(p_184538_3_ * 0.017453292F) * MathHelper.func_76134_b(p_184538_2_ * 0.017453292F);
      this.func_70186_c((double)f, (double)f1, (double)f2, p_184538_5_, p_184538_6_);
      this.field_70159_w += p_184538_1_.field_70159_w;
      this.field_70179_y += p_184538_1_.field_70179_y;
      if (!p_184538_1_.field_70122_E) {
         this.field_70181_x += p_184538_1_.field_70181_x;
      }

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
      this.field_70194_h = 0;
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         this.field_70177_z = (float)(MathHelper.func_181159_b(p_70016_1_, p_70016_5_) * 57.2957763671875D);
         this.field_70125_A = (float)(MathHelper.func_181159_b(p_70016_3_, (double)f) * 57.2957763671875D);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }

   }

   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
      if (this.field_70191_b > 0) {
         --this.field_70191_b;
      }

      if (this.field_174854_a) {
         if (this.field_70170_p.func_180495_p(new BlockPos(this.field_145788_c, this.field_145786_d, this.field_145787_e)).func_177230_c() == this.field_174853_f) {
            ++this.field_70194_h;
            if (this.field_70194_h == 1200) {
               this.func_70106_y();
            }

            return;
         }

         this.field_174854_a = false;
         this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
         this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
         this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
         this.field_70194_h = 0;
         this.field_70195_i = 0;
      } else {
         ++this.field_70195_i;
      }

      Vec3d vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult raytraceresult = this.field_70170_p.func_72933_a(vec3d, vec3d1);
      vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (raytraceresult != null) {
         vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
      }

      Entity entity = null;
      List<Entity> list = this.field_70170_p.func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0D));
      double d0 = 0.0D;
      boolean flag = false;

      for(int i = 0; i < list.size(); ++i) {
         Entity entity1 = list.get(i);
         if (entity1.func_70067_L()) {
            if (entity1 == this.field_184539_c) {
               flag = true;
            } else if (this.field_70192_c != null && this.field_70173_aa < 2 && this.field_184539_c == null) {
               this.field_184539_c = entity1;
               flag = true;
            } else {
               flag = false;
               AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
               RayTraceResult raytraceresult1 = axisalignedbb.func_72327_a(vec3d, vec3d1);
               if (raytraceresult1 != null) {
                  double d1 = vec3d.func_72436_e(raytraceresult1.field_72307_f);
                  if (d1 < d0 || d0 == 0.0D) {
                     entity = entity1;
                     d0 = d1;
                  }
               }
            }
         }
      }

      if (this.field_184539_c != null) {
         if (flag) {
            this.field_184540_av = 2;
         } else if (this.field_184540_av-- <= 0) {
            this.field_184539_c = null;
         }
      }

      if (entity != null) {
         raytraceresult = new RayTraceResult(entity);
      }

      if (raytraceresult != null) {
         if (raytraceresult.field_72313_a == RayTraceResult.Type.BLOCK && this.field_70170_p.func_180495_p(raytraceresult.func_178782_a()).func_177230_c() == Blocks.field_150427_aO) {
            this.func_181015_d(raytraceresult.func_178782_a());
         } else {
            this.func_70184_a(raytraceresult);
         }
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
      float f2 = this.func_70185_h();
      if (this.func_70090_H()) {
         for(int j = 0; j < 4; ++j) {
            float f3 = 0.25F;
            this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * 0.25D, this.field_70163_u - this.field_70181_x * 0.25D, this.field_70161_v - this.field_70179_y * 0.25D, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         }

         f1 = 0.8F;
      }

      this.field_70159_w *= (double)f1;
      this.field_70181_x *= (double)f1;
      this.field_70179_y *= (double)f1;
      if (!this.func_189652_ae()) {
         this.field_70181_x -= (double)f2;
      }

      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   protected float func_70185_h() {
      return 0.03F;
   }

   protected abstract void func_70184_a(RayTraceResult var1);

   public static void func_189661_a(DataFixer p_189661_0_, String p_189661_1_) {
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("xTile", this.field_145788_c);
      p_70014_1_.func_74768_a("yTile", this.field_145786_d);
      p_70014_1_.func_74768_a("zTile", this.field_145787_e);
      ResourceLocation resourcelocation = Block.field_149771_c.func_177774_c(this.field_174853_f);
      p_70014_1_.func_74778_a("inTile", resourcelocation == null ? "" : resourcelocation.toString());
      p_70014_1_.func_74774_a("shake", (byte)this.field_70191_b);
      p_70014_1_.func_74774_a("inGround", (byte)(this.field_174854_a ? 1 : 0));
      if ((this.field_85053_h == null || this.field_85053_h.isEmpty()) && this.field_70192_c instanceof EntityPlayer) {
         this.field_85053_h = this.field_70192_c.func_70005_c_();
      }

      p_70014_1_.func_74778_a("ownerName", this.field_85053_h == null ? "" : this.field_85053_h);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_145788_c = p_70037_1_.func_74762_e("xTile");
      this.field_145786_d = p_70037_1_.func_74762_e("yTile");
      this.field_145787_e = p_70037_1_.func_74762_e("zTile");
      if (p_70037_1_.func_150297_b("inTile", 8)) {
         this.field_174853_f = Block.func_149684_b(p_70037_1_.func_74779_i("inTile"));
      } else {
         this.field_174853_f = Block.func_149729_e(p_70037_1_.func_74771_c("inTile") & 255);
      }

      this.field_70191_b = p_70037_1_.func_74771_c("shake") & 255;
      this.field_174854_a = p_70037_1_.func_74771_c("inGround") == 1;
      this.field_70192_c = null;
      this.field_85053_h = p_70037_1_.func_74779_i("ownerName");
      if (this.field_85053_h != null && this.field_85053_h.isEmpty()) {
         this.field_85053_h = null;
      }

      this.field_70192_c = this.func_85052_h();
   }

   @Nullable
   public EntityLivingBase func_85052_h() {
      if (this.field_70192_c == null && this.field_85053_h != null && !this.field_85053_h.isEmpty()) {
         this.field_70192_c = this.field_70170_p.func_72924_a(this.field_85053_h);
         if (this.field_70192_c == null && this.field_70170_p instanceof WorldServer) {
            try {
               Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(UUID.fromString(this.field_85053_h));
               if (entity instanceof EntityLivingBase) {
                  this.field_70192_c = (EntityLivingBase)entity;
               }
            } catch (Throwable var2) {
               this.field_70192_c = null;
            }
         }
      }

      return this.field_70192_c;
   }
}
