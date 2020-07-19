package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGuardian extends EntityMob {
   private static final DataParameter<Boolean> field_190766_bz = EntityDataManager.<Boolean>func_187226_a(EntityGuardian.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184723_b = EntityDataManager.<Integer>func_187226_a(EntityGuardian.class, DataSerializers.field_187192_b);
   protected float field_175482_b;
   protected float field_175484_c;
   protected float field_175483_bk;
   protected float field_175485_bl;
   protected float field_175486_bm;
   private EntityLivingBase field_175478_bn;
   private int field_175479_bo;
   private boolean field_175480_bp;
   protected EntityAIWander field_175481_bq;

   public EntityGuardian(World p_i45835_1_) {
      super(p_i45835_1_);
      this.field_70728_aV = 10;
      this.func_70105_a(0.85F, 0.85F);
      this.field_70765_h = new EntityGuardian.GuardianMoveHelper(this);
      this.field_175482_b = this.field_70146_Z.nextFloat();
      this.field_175484_c = this.field_175482_b;
   }

   protected void func_184651_r() {
      EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
      this.field_175481_bq = new EntityAIWander(this, 1.0D, 80);
      this.field_70714_bg.func_75776_a(4, new EntityGuardian.AIGuardianAttack(this));
      this.field_70714_bg.func_75776_a(5, entityaimovetowardsrestriction);
      this.field_70714_bg.func_75776_a(7, this.field_175481_bq);
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.field_175481_bq.func_75248_a(3);
      entityaimovetowardsrestriction.func_75248_a(3);
      this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new EntityGuardian.GuardianTargetSelector(this)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
   }

   public static void func_189766_b(DataFixer p_189766_0_) {
      EntityLiving.func_189752_a(p_189766_0_, EntityGuardian.class);
   }

   protected PathNavigate func_175447_b(World p_175447_1_) {
      return new PathNavigateSwimmer(this, p_175447_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_190766_bz, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_184723_b, Integer.valueOf(0));
   }

   public boolean func_175472_n() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_190766_bz)).booleanValue();
   }

   private void func_175476_l(boolean p_175476_1_) {
      this.field_70180_af.func_187227_b(field_190766_bz, Boolean.valueOf(p_175476_1_));
   }

   public int func_175464_ck() {
      return 80;
   }

   private void func_175463_b(int p_175463_1_) {
      this.field_70180_af.func_187227_b(field_184723_b, Integer.valueOf(p_175463_1_));
   }

   public boolean func_175474_cn() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184723_b)).intValue() != 0;
   }

   @Nullable
   public EntityLivingBase func_175466_co() {
      if (!this.func_175474_cn()) {
         return null;
      } else if (this.field_70170_p.field_72995_K) {
         if (this.field_175478_bn != null) {
            return this.field_175478_bn;
         } else {
            Entity entity = this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(field_184723_b)).intValue());
            if (entity instanceof EntityLivingBase) {
               this.field_175478_bn = (EntityLivingBase)entity;
               return this.field_175478_bn;
            } else {
               return null;
            }
         }
      } else {
         return this.func_70638_az();
      }
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      super.func_184206_a(p_184206_1_);
      if (field_184723_b.equals(p_184206_1_)) {
         this.field_175479_bo = 0;
         this.field_175478_bn = null;
      }

   }

   public int func_70627_aG() {
      return 160;
   }

   protected SoundEvent func_184639_G() {
      return this.func_70090_H() ? SoundEvents.field_187670_cb : SoundEvents.field_187672_cc;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return this.func_70090_H() ? SoundEvents.field_187687_ch : SoundEvents.field_187690_ci;
   }

   protected SoundEvent func_184615_bR() {
      return this.func_70090_H() ? SoundEvents.field_187678_ce : SoundEvents.field_187681_cf;
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public float func_70047_e() {
      return this.field_70131_O * 0.5F;
   }

   public float func_180484_a(BlockPos p_180484_1_) {
      return this.field_70170_p.func_180495_p(p_180484_1_).func_185904_a() == Material.field_151586_h ? 10.0F + this.field_70170_p.func_175724_o(p_180484_1_) - 0.5F : super.func_180484_a(p_180484_1_);
   }

   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.field_175484_c = this.field_175482_b;
         if (!this.func_70090_H()) {
            this.field_175483_bk = 2.0F;
            if (this.field_70181_x > 0.0D && this.field_175480_bp && !this.func_174814_R()) {
               this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.func_190765_dj(), this.func_184176_by(), 1.0F, 1.0F, false);
            }

            this.field_175480_bp = this.field_70181_x < 0.0D && this.field_70170_p.func_175677_d((new BlockPos(this)).func_177977_b(), false);
         } else if (this.func_175472_n()) {
            if (this.field_175483_bk < 0.5F) {
               this.field_175483_bk = 4.0F;
            } else {
               this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
            }
         } else {
            this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
         }

         this.field_175482_b += this.field_175483_bk;
         this.field_175486_bm = this.field_175485_bl;
         if (!this.func_70090_H()) {
            this.field_175485_bl = this.field_70146_Z.nextFloat();
         } else if (this.func_175472_n()) {
            this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
         } else {
            this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
         }

         if (this.func_175472_n() && this.func_70090_H()) {
            Vec3d vec3d = this.func_70676_i(0.0F);

            for(int i = 0; i < 2; ++i) {
               this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N - vec3d.field_72450_a * 1.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - vec3d.field_72448_b * 1.5D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N - vec3d.field_72449_c * 1.5D, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.func_175474_cn()) {
            if (this.field_175479_bo < this.func_175464_ck()) {
               ++this.field_175479_bo;
            }

            EntityLivingBase entitylivingbase = this.func_175466_co();
            if (entitylivingbase != null) {
               this.func_70671_ap().func_75651_a(entitylivingbase, 90.0F, 90.0F);
               this.func_70671_ap().func_75649_a();
               double d5 = (double)this.func_175477_p(0.0F);
               double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
               double d1 = entitylivingbase.field_70163_u + (double)(entitylivingbase.field_70131_O * 0.5F) - (this.field_70163_u + (double)this.func_70047_e());
               double d2 = entitylivingbase.field_70161_v - this.field_70161_v;
               double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
               d0 = d0 / d3;
               d1 = d1 / d3;
               d2 = d2 / d3;
               double d4 = this.field_70146_Z.nextDouble();

               while(d4 < d3) {
                  d4 += 1.8D - d5 + this.field_70146_Z.nextDouble() * (1.7D - d5);
                  this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + d0 * d4, this.field_70163_u + d1 * d4 + (double)this.func_70047_e(), this.field_70161_v + d2 * d4, 0.0D, 0.0D, 0.0D);
               }
            }
         }
      }

      if (this.field_70171_ac) {
         this.func_70050_g(300);
      } else if (this.field_70122_E) {
         this.field_70181_x += 0.5D;
         this.field_70159_w += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.field_70179_y += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
         this.field_70122_E = false;
         this.field_70160_al = true;
      }

      if (this.func_175474_cn()) {
         this.field_70177_z = this.field_70759_as;
      }

      super.func_70636_d();
   }

   protected SoundEvent func_190765_dj() {
      return SoundEvents.field_187684_cg;
   }

   public float func_175471_a(float p_175471_1_) {
      return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * p_175471_1_;
   }

   public float func_175469_o(float p_175469_1_) {
      return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * p_175469_1_;
   }

   public float func_175477_p(float p_175477_1_) {
      return ((float)this.field_175479_bo + p_175477_1_) / (float)this.func_175464_ck();
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186440_v;
   }

   protected boolean func_70814_o() {
      return true;
   }

   public boolean func_70058_J() {
      return this.field_70170_p.func_72917_a(this.func_174813_aQ(), this) && this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty();
   }

   public boolean func_70601_bi() {
      return (this.field_70146_Z.nextInt(20) == 0 || !this.field_70170_p.func_175710_j(new BlockPos(this))) && super.func_70601_bi();
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (!this.func_175472_n() && !p_70097_1_.func_82725_o() && p_70097_1_.func_76364_f() instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)p_70097_1_.func_76364_f();
         if (!p_70097_1_.func_94541_c()) {
            entitylivingbase.func_70097_a(DamageSource.func_92087_a(this), 2.0F);
         }
      }

      if (this.field_175481_bq != null) {
         this.field_175481_bq.func_179480_f();
      }

      return super.func_70097_a(p_70097_1_, p_70097_2_);
   }

   public int func_70646_bf() {
      return 180;
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      if (this.func_70613_aW() && this.func_70090_H()) {
         this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.1F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.8999999761581421D;
         this.field_70181_x *= 0.8999999761581421D;
         this.field_70179_y *= 0.8999999761581421D;
         if (!this.func_175472_n() && this.func_70638_az() == null) {
            this.field_70181_x -= 0.005D;
         }
      } else {
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
      }

   }

   static class AIGuardianAttack extends EntityAIBase {
      private final EntityGuardian field_179456_a;
      private int field_179455_b;
      private final boolean field_190881_c;

      public AIGuardianAttack(EntityGuardian p_i45833_1_) {
         this.field_179456_a = p_i45833_1_;
         this.field_190881_c = p_i45833_1_ instanceof EntityElderGuardian;
         this.func_75248_a(3);
      }

      public boolean func_75250_a() {
         EntityLivingBase entitylivingbase = this.field_179456_a.func_70638_az();
         return entitylivingbase != null && entitylivingbase.func_70089_S();
      }

      public boolean func_75253_b() {
         return super.func_75253_b() && (this.field_190881_c || this.field_179456_a.func_70068_e(this.field_179456_a.func_70638_az()) > 9.0D);
      }

      public void func_75249_e() {
         this.field_179455_b = -10;
         this.field_179456_a.func_70661_as().func_75499_g();
         this.field_179456_a.func_70671_ap().func_75651_a(this.field_179456_a.func_70638_az(), 90.0F, 90.0F);
         this.field_179456_a.field_70160_al = true;
      }

      public void func_75251_c() {
         this.field_179456_a.func_175463_b(0);
         this.field_179456_a.func_70624_b((EntityLivingBase)null);
         this.field_179456_a.field_175481_bq.func_179480_f();
      }

      public void func_75246_d() {
         EntityLivingBase entitylivingbase = this.field_179456_a.func_70638_az();
         this.field_179456_a.func_70661_as().func_75499_g();
         this.field_179456_a.func_70671_ap().func_75651_a(entitylivingbase, 90.0F, 90.0F);
         if (!this.field_179456_a.func_70685_l(entitylivingbase)) {
            this.field_179456_a.func_70624_b((EntityLivingBase)null);
         } else {
            ++this.field_179455_b;
            if (this.field_179455_b == 0) {
               this.field_179456_a.func_175463_b(this.field_179456_a.func_70638_az().func_145782_y());
               this.field_179456_a.field_70170_p.func_72960_a(this.field_179456_a, (byte)21);
            } else if (this.field_179455_b >= this.field_179456_a.func_175464_ck()) {
               float f = 1.0F;
               if (this.field_179456_a.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                  f += 2.0F;
               }

               if (this.field_190881_c) {
                  f += 2.0F;
               }

               entitylivingbase.func_70097_a(DamageSource.func_76354_b(this.field_179456_a, this.field_179456_a), f);
               entitylivingbase.func_70097_a(DamageSource.func_76358_a(this.field_179456_a), (float)this.field_179456_a.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e());
               this.field_179456_a.func_70624_b((EntityLivingBase)null);
            }

            super.func_75246_d();
         }
      }
   }

   static class GuardianMoveHelper extends EntityMoveHelper {
      private final EntityGuardian field_179930_g;

      public GuardianMoveHelper(EntityGuardian p_i45831_1_) {
         super(p_i45831_1_);
         this.field_179930_g = p_i45831_1_;
      }

      public void func_75641_c() {
         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.field_179930_g.func_70661_as().func_75500_f()) {
            double d0 = this.field_75646_b - this.field_179930_g.field_70165_t;
            double d1 = this.field_75647_c - this.field_179930_g.field_70163_u;
            double d2 = this.field_75644_d - this.field_179930_g.field_70161_v;
            double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = d1 / d3;
            float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.2957763671875D) - 90.0F;
            this.field_179930_g.field_70177_z = this.func_75639_a(this.field_179930_g.field_70177_z, f, 90.0F);
            this.field_179930_g.field_70761_aq = this.field_179930_g.field_70177_z;
            float f1 = (float)(this.field_75645_e * this.field_179930_g.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            this.field_179930_g.func_70659_e(this.field_179930_g.func_70689_ay() + (f1 - this.field_179930_g.func_70689_ay()) * 0.125F);
            double d4 = Math.sin((double)(this.field_179930_g.field_70173_aa + this.field_179930_g.func_145782_y()) * 0.5D) * 0.05D;
            double d5 = Math.cos((double)(this.field_179930_g.field_70177_z * 0.017453292F));
            double d6 = Math.sin((double)(this.field_179930_g.field_70177_z * 0.017453292F));
            this.field_179930_g.field_70159_w += d4 * d5;
            this.field_179930_g.field_70179_y += d4 * d6;
            d4 = Math.sin((double)(this.field_179930_g.field_70173_aa + this.field_179930_g.func_145782_y()) * 0.75D) * 0.05D;
            this.field_179930_g.field_70181_x += d4 * (d6 + d5) * 0.25D;
            this.field_179930_g.field_70181_x += (double)this.field_179930_g.func_70689_ay() * d1 * 0.1D;
            EntityLookHelper entitylookhelper = this.field_179930_g.func_70671_ap();
            double d7 = this.field_179930_g.field_70165_t + d0 / d3 * 2.0D;
            double d8 = (double)this.field_179930_g.func_70047_e() + this.field_179930_g.field_70163_u + d1 / d3;
            double d9 = this.field_179930_g.field_70161_v + d2 / d3 * 2.0D;
            double d10 = entitylookhelper.func_180423_e();
            double d11 = entitylookhelper.func_180422_f();
            double d12 = entitylookhelper.func_180421_g();
            if (!entitylookhelper.func_180424_b()) {
               d10 = d7;
               d11 = d8;
               d12 = d9;
            }

            this.field_179930_g.func_70671_ap().func_75650_a(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
            this.field_179930_g.func_175476_l(true);
         } else {
            this.field_179930_g.func_70659_e(0.0F);
            this.field_179930_g.func_175476_l(false);
         }
      }
   }

   static class GuardianTargetSelector implements Predicate<EntityLivingBase> {
      private final EntityGuardian field_179916_a;

      public GuardianTargetSelector(EntityGuardian p_i45832_1_) {
         this.field_179916_a = p_i45832_1_;
      }

      public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
         return (p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof EntitySquid) && p_apply_1_.func_70068_e(this.field_179916_a) > 9.0D;
      }
   }
}
