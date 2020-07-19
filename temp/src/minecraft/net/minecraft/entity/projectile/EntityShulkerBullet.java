package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityShulkerBullet extends Entity {
   private EntityLivingBase field_184570_a;
   private Entity field_184571_b;
   @Nullable
   private EnumFacing field_184573_c;
   private int field_184575_d;
   private double field_184577_e;
   private double field_184578_f;
   private double field_184579_g;
   @Nullable
   private UUID field_184580_h;
   private BlockPos field_184572_as;
   @Nullable
   private UUID field_184574_at;
   private BlockPos field_184576_au;

   public EntityShulkerBullet(World p_i46770_1_) {
      super(p_i46770_1_);
      this.func_70105_a(0.3125F, 0.3125F);
      this.field_70145_X = true;
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   public EntityShulkerBullet(World p_i46771_1_, double p_i46771_2_, double p_i46771_4_, double p_i46771_6_, double p_i46771_8_, double p_i46771_10_, double p_i46771_12_) {
      this(p_i46771_1_);
      this.func_70012_b(p_i46771_2_, p_i46771_4_, p_i46771_6_, this.field_70177_z, this.field_70125_A);
      this.field_70159_w = p_i46771_8_;
      this.field_70181_x = p_i46771_10_;
      this.field_70179_y = p_i46771_12_;
   }

   public EntityShulkerBullet(World p_i46772_1_, EntityLivingBase p_i46772_2_, Entity p_i46772_3_, EnumFacing.Axis p_i46772_4_) {
      this(p_i46772_1_);
      this.field_184570_a = p_i46772_2_;
      BlockPos blockpos = new BlockPos(p_i46772_2_);
      double d0 = (double)blockpos.func_177958_n() + 0.5D;
      double d1 = (double)blockpos.func_177956_o() + 0.5D;
      double d2 = (double)blockpos.func_177952_p() + 0.5D;
      this.func_70012_b(d0, d1, d2, this.field_70177_z, this.field_70125_A);
      this.field_184571_b = p_i46772_3_;
      this.field_184573_c = EnumFacing.UP;
      this.func_184569_a(p_i46772_4_);
   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      if (this.field_184570_a != null) {
         BlockPos blockpos = new BlockPos(this.field_184570_a);
         NBTTagCompound nbttagcompound = NBTUtil.func_186862_a(this.field_184570_a.func_110124_au());
         nbttagcompound.func_74768_a("X", blockpos.func_177958_n());
         nbttagcompound.func_74768_a("Y", blockpos.func_177956_o());
         nbttagcompound.func_74768_a("Z", blockpos.func_177952_p());
         p_70014_1_.func_74782_a("Owner", nbttagcompound);
      }

      if (this.field_184571_b != null) {
         BlockPos blockpos1 = new BlockPos(this.field_184571_b);
         NBTTagCompound nbttagcompound1 = NBTUtil.func_186862_a(this.field_184571_b.func_110124_au());
         nbttagcompound1.func_74768_a("X", blockpos1.func_177958_n());
         nbttagcompound1.func_74768_a("Y", blockpos1.func_177956_o());
         nbttagcompound1.func_74768_a("Z", blockpos1.func_177952_p());
         p_70014_1_.func_74782_a("Target", nbttagcompound1);
      }

      if (this.field_184573_c != null) {
         p_70014_1_.func_74768_a("Dir", this.field_184573_c.func_176745_a());
      }

      p_70014_1_.func_74768_a("Steps", this.field_184575_d);
      p_70014_1_.func_74780_a("TXD", this.field_184577_e);
      p_70014_1_.func_74780_a("TYD", this.field_184578_f);
      p_70014_1_.func_74780_a("TZD", this.field_184579_g);
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_184575_d = p_70037_1_.func_74762_e("Steps");
      this.field_184577_e = p_70037_1_.func_74769_h("TXD");
      this.field_184578_f = p_70037_1_.func_74769_h("TYD");
      this.field_184579_g = p_70037_1_.func_74769_h("TZD");
      if (p_70037_1_.func_150297_b("Dir", 99)) {
         this.field_184573_c = EnumFacing.func_82600_a(p_70037_1_.func_74762_e("Dir"));
      }

      if (p_70037_1_.func_150297_b("Owner", 10)) {
         NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("Owner");
         this.field_184580_h = NBTUtil.func_186860_b(nbttagcompound);
         this.field_184572_as = new BlockPos(nbttagcompound.func_74762_e("X"), nbttagcompound.func_74762_e("Y"), nbttagcompound.func_74762_e("Z"));
      }

      if (p_70037_1_.func_150297_b("Target", 10)) {
         NBTTagCompound nbttagcompound1 = p_70037_1_.func_74775_l("Target");
         this.field_184574_at = NBTUtil.func_186860_b(nbttagcompound1);
         this.field_184576_au = new BlockPos(nbttagcompound1.func_74762_e("X"), nbttagcompound1.func_74762_e("Y"), nbttagcompound1.func_74762_e("Z"));
      }

   }

   protected void func_70088_a() {
   }

   private void func_184568_a(@Nullable EnumFacing p_184568_1_) {
      this.field_184573_c = p_184568_1_;
   }

   private void func_184569_a(@Nullable EnumFacing.Axis p_184569_1_) {
      double d0 = 0.5D;
      BlockPos blockpos;
      if (this.field_184571_b == null) {
         blockpos = (new BlockPos(this)).func_177977_b();
      } else {
         d0 = (double)this.field_184571_b.field_70131_O * 0.5D;
         blockpos = new BlockPos(this.field_184571_b.field_70165_t, this.field_184571_b.field_70163_u + d0, this.field_184571_b.field_70161_v);
      }

      double d1 = (double)blockpos.func_177958_n() + 0.5D;
      double d2 = (double)blockpos.func_177956_o() + d0;
      double d3 = (double)blockpos.func_177952_p() + 0.5D;
      EnumFacing enumfacing = null;
      if (blockpos.func_177957_d(this.field_70165_t, this.field_70163_u, this.field_70161_v) >= 4.0D) {
         BlockPos blockpos1 = new BlockPos(this);
         List<EnumFacing> list = Lists.<EnumFacing>newArrayList();
         if (p_184569_1_ != EnumFacing.Axis.X) {
            if (blockpos1.func_177958_n() < blockpos.func_177958_n() && this.field_70170_p.func_175623_d(blockpos1.func_177974_f())) {
               list.add(EnumFacing.EAST);
            } else if (blockpos1.func_177958_n() > blockpos.func_177958_n() && this.field_70170_p.func_175623_d(blockpos1.func_177976_e())) {
               list.add(EnumFacing.WEST);
            }
         }

         if (p_184569_1_ != EnumFacing.Axis.Y) {
            if (blockpos1.func_177956_o() < blockpos.func_177956_o() && this.field_70170_p.func_175623_d(blockpos1.func_177984_a())) {
               list.add(EnumFacing.UP);
            } else if (blockpos1.func_177956_o() > blockpos.func_177956_o() && this.field_70170_p.func_175623_d(blockpos1.func_177977_b())) {
               list.add(EnumFacing.DOWN);
            }
         }

         if (p_184569_1_ != EnumFacing.Axis.Z) {
            if (blockpos1.func_177952_p() < blockpos.func_177952_p() && this.field_70170_p.func_175623_d(blockpos1.func_177968_d())) {
               list.add(EnumFacing.SOUTH);
            } else if (blockpos1.func_177952_p() > blockpos.func_177952_p() && this.field_70170_p.func_175623_d(blockpos1.func_177978_c())) {
               list.add(EnumFacing.NORTH);
            }
         }

         enumfacing = EnumFacing.func_176741_a(this.field_70146_Z);
         if (list.isEmpty()) {
            for(int i = 5; !this.field_70170_p.func_175623_d(blockpos1.func_177972_a(enumfacing)) && i > 0; --i) {
               enumfacing = EnumFacing.func_176741_a(this.field_70146_Z);
            }
         } else {
            enumfacing = list.get(this.field_70146_Z.nextInt(list.size()));
         }

         d1 = this.field_70165_t + (double)enumfacing.func_82601_c();
         d2 = this.field_70163_u + (double)enumfacing.func_96559_d();
         d3 = this.field_70161_v + (double)enumfacing.func_82599_e();
      }

      this.func_184568_a(enumfacing);
      double d6 = d1 - this.field_70165_t;
      double d7 = d2 - this.field_70163_u;
      double d4 = d3 - this.field_70161_v;
      double d5 = (double)MathHelper.func_76133_a(d6 * d6 + d7 * d7 + d4 * d4);
      if (d5 == 0.0D) {
         this.field_184577_e = 0.0D;
         this.field_184578_f = 0.0D;
         this.field_184579_g = 0.0D;
      } else {
         this.field_184577_e = d6 / d5 * 0.15D;
         this.field_184578_f = d7 / d5 * 0.15D;
         this.field_184579_g = d4 / d5 * 0.15D;
      }

      this.field_70160_al = true;
      this.field_184575_d = 10 + this.field_70146_Z.nextInt(5) * 10;
   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      } else {
         super.func_70071_h_();
         if (!this.field_70170_p.field_72995_K) {
            if (this.field_184571_b == null && this.field_184574_at != null) {
               for(EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, new AxisAlignedBB(this.field_184576_au.func_177982_a(-2, -2, -2), this.field_184576_au.func_177982_a(2, 2, 2)))) {
                  if (entitylivingbase.func_110124_au().equals(this.field_184574_at)) {
                     this.field_184571_b = entitylivingbase;
                     break;
                  }
               }

               this.field_184574_at = null;
            }

            if (this.field_184570_a == null && this.field_184580_h != null) {
               for(EntityLivingBase entitylivingbase1 : this.field_70170_p.func_72872_a(EntityLivingBase.class, new AxisAlignedBB(this.field_184572_as.func_177982_a(-2, -2, -2), this.field_184572_as.func_177982_a(2, 2, 2)))) {
                  if (entitylivingbase1.func_110124_au().equals(this.field_184580_h)) {
                     this.field_184570_a = entitylivingbase1;
                     break;
                  }
               }

               this.field_184580_h = null;
            }

            if (this.field_184571_b == null || !this.field_184571_b.func_70089_S() || this.field_184571_b instanceof EntityPlayer && ((EntityPlayer)this.field_184571_b).func_175149_v()) {
               if (!this.func_189652_ae()) {
                  this.field_70181_x -= 0.04D;
               }
            } else {
               this.field_184577_e = MathHelper.func_151237_a(this.field_184577_e * 1.025D, -1.0D, 1.0D);
               this.field_184578_f = MathHelper.func_151237_a(this.field_184578_f * 1.025D, -1.0D, 1.0D);
               this.field_184579_g = MathHelper.func_151237_a(this.field_184579_g * 1.025D, -1.0D, 1.0D);
               this.field_70159_w += (this.field_184577_e - this.field_70159_w) * 0.2D;
               this.field_70181_x += (this.field_184578_f - this.field_70181_x) * 0.2D;
               this.field_70179_y += (this.field_184579_g - this.field_70179_y) * 0.2D;
            }

            RayTraceResult raytraceresult = ProjectileHelper.func_188802_a(this, true, false, this.field_184570_a);
            if (raytraceresult != null) {
               this.func_184567_a(raytraceresult);
            }
         }

         this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         ProjectileHelper.func_188803_a(this, 0.5F);
         if (this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, this.field_70165_t - this.field_70159_w, this.field_70163_u - this.field_70181_x + 0.15D, this.field_70161_v - this.field_70179_y, 0.0D, 0.0D, 0.0D);
         } else if (this.field_184571_b != null && !this.field_184571_b.field_70128_L) {
            if (this.field_184575_d > 0) {
               --this.field_184575_d;
               if (this.field_184575_d == 0) {
                  this.func_184569_a(this.field_184573_c == null ? null : this.field_184573_c.func_176740_k());
               }
            }

            if (this.field_184573_c != null) {
               BlockPos blockpos = new BlockPos(this);
               EnumFacing.Axis enumfacing$axis = this.field_184573_c.func_176740_k();
               if (this.field_70170_p.func_175677_d(blockpos.func_177972_a(this.field_184573_c), false)) {
                  this.func_184569_a(enumfacing$axis);
               } else {
                  BlockPos blockpos1 = new BlockPos(this.field_184571_b);
                  if (enumfacing$axis == EnumFacing.Axis.X && blockpos.func_177958_n() == blockpos1.func_177958_n() || enumfacing$axis == EnumFacing.Axis.Z && blockpos.func_177952_p() == blockpos1.func_177952_p() || enumfacing$axis == EnumFacing.Axis.Y && blockpos.func_177956_o() == blockpos1.func_177956_o()) {
                     this.func_184569_a(enumfacing$axis);
                  }
               }
            }
         }

      }
   }

   public boolean func_70027_ad() {
      return false;
   }

   public boolean func_70112_a(double p_70112_1_) {
      return p_70112_1_ < 16384.0D;
   }

   public float func_70013_c() {
      return 1.0F;
   }

   public int func_70070_b() {
      return 15728880;
   }

   protected void func_184567_a(RayTraceResult p_184567_1_) {
      if (p_184567_1_.field_72308_g == null) {
         ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2, 0.2D, 0.2D, 0.2D, 0.0D);
         this.func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
      } else {
         boolean flag = p_184567_1_.field_72308_g.func_70097_a(DamageSource.func_188403_a(this, this.field_184570_a).func_76349_b(), 4.0F);
         if (flag) {
            this.func_174815_a(this.field_184570_a, p_184567_1_.field_72308_g);
            if (p_184567_1_.field_72308_g instanceof EntityLivingBase) {
               ((EntityLivingBase)p_184567_1_.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_188424_y, 200));
            }
         }
      }

      this.func_70106_y();
   }

   public boolean func_70067_L() {
      return true;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (!this.field_70170_p.field_72995_K) {
         this.func_184185_a(SoundEvents.field_187777_eQ, 1.0F, 1.0F);
         ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.CRIT, this.field_70165_t, this.field_70163_u, this.field_70161_v, 15, 0.2D, 0.2D, 0.2D, 0.0D);
         this.func_70106_y();
      }

      return true;
   }
}
