package net.minecraft.entity.boss;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IMob {
   private static final Logger field_184675_bH = LogManager.getLogger();
   public static final DataParameter<Integer> field_184674_a = EntityDataManager.<Integer>func_187226_a(EntityDragon.class, DataSerializers.field_187192_b);
   public double[][] field_70979_e = new double[64][3];
   public int field_70976_f = -1;
   public MultiPartEntityPart[] field_70977_g;
   public MultiPartEntityPart field_70986_h = new MultiPartEntityPart(this, "head", 6.0F, 6.0F);
   public MultiPartEntityPart field_184673_bv = new MultiPartEntityPart(this, "neck", 6.0F, 6.0F);
   public MultiPartEntityPart field_70987_i = new MultiPartEntityPart(this, "body", 8.0F, 8.0F);
   public MultiPartEntityPart field_70985_j = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
   public MultiPartEntityPart field_70984_by = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
   public MultiPartEntityPart field_70982_bz = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
   public MultiPartEntityPart field_70983_bA = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
   public MultiPartEntityPart field_70990_bB = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
   public float field_70991_bC;
   public float field_70988_bD;
   public boolean field_70994_bF;
   public int field_70995_bG;
   public EntityEnderCrystal field_70992_bH;
   private final DragonFightManager field_184676_bI;
   private final PhaseManager field_184677_bJ;
   private int field_184678_bK = 200;
   private int field_184679_bL;
   private final PathPoint[] field_184680_bM = new PathPoint[24];
   private final int[] field_184681_bN = new int[24];
   private final PathHeap field_184682_bO = new PathHeap();

   public EntityDragon(World p_i1700_1_) {
      super(p_i1700_1_);
      this.field_70977_g = new MultiPartEntityPart[]{this.field_70986_h, this.field_184673_bv, this.field_70987_i, this.field_70985_j, this.field_70984_by, this.field_70982_bz, this.field_70983_bA, this.field_70990_bB};
      this.func_70606_j(this.func_110138_aP());
      this.func_70105_a(16.0F, 8.0F);
      this.field_70145_X = true;
      this.field_70178_ae = true;
      this.field_184678_bK = 100;
      this.field_70158_ak = true;
      if (!p_i1700_1_.field_72995_K && p_i1700_1_.field_73011_w instanceof WorldProviderEnd) {
         this.field_184676_bI = ((WorldProviderEnd)p_i1700_1_.field_73011_w).func_186063_s();
      } else {
         this.field_184676_bI = null;
      }

      this.field_184677_bJ = new PhaseManager(this);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.func_184212_Q().func_187214_a(field_184674_a, Integer.valueOf(PhaseList.field_188751_k.func_188740_b()));
   }

   public double[] func_70974_a(int p_70974_1_, float p_70974_2_) {
      if (this.func_110143_aJ() <= 0.0F) {
         p_70974_2_ = 0.0F;
      }

      p_70974_2_ = 1.0F - p_70974_2_;
      int i = this.field_70976_f - p_70974_1_ & 63;
      int j = this.field_70976_f - p_70974_1_ - 1 & 63;
      double[] adouble = new double[3];
      double d0 = this.field_70979_e[i][0];
      double d1 = MathHelper.func_76138_g(this.field_70979_e[j][0] - d0);
      adouble[0] = d0 + d1 * (double)p_70974_2_;
      d0 = this.field_70979_e[i][1];
      d1 = this.field_70979_e[j][1] - d0;
      adouble[1] = d0 + d1 * (double)p_70974_2_;
      adouble[2] = this.field_70979_e[i][2] + (this.field_70979_e[j][2] - this.field_70979_e[i][2]) * (double)p_70974_2_;
      return adouble;
   }

   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.func_70606_j(this.func_110143_aJ());
         if (!this.func_174814_R()) {
            float f = MathHelper.func_76134_b(this.field_70988_bD * 6.2831855F);
            float f1 = MathHelper.func_76134_b(this.field_70991_bC * 6.2831855F);
            if (f1 <= -0.3F && f >= -0.3F) {
               this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, this.func_184176_by(), 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
            }

            if (!this.field_184677_bJ.func_188756_a().func_188654_a() && --this.field_184678_bK < 0) {
               this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187525_aO, this.func_184176_by(), 2.5F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
               this.field_184678_bK = 200 + this.field_70146_Z.nextInt(200);
            }
         }
      }

      this.field_70991_bC = this.field_70988_bD;
      if (this.func_110143_aJ() <= 0.0F) {
         float f12 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
         float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + (double)f12, this.field_70163_u + 2.0D + (double)f13, this.field_70161_v + (double)f15, 0.0D, 0.0D, 0.0D);
      } else {
         this.func_70969_j();
         float f11 = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
         f11 = f11 * (float)Math.pow(2.0D, this.field_70181_x);
         if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
            this.field_70988_bD += 0.1F;
         } else if (this.field_70994_bF) {
            this.field_70988_bD += f11 * 0.5F;
         } else {
            this.field_70988_bD += f11;
         }

         this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
         if (this.func_175446_cd()) {
            this.field_70988_bD = 0.5F;
         } else {
            if (this.field_70976_f < 0) {
               for(int i = 0; i < this.field_70979_e.length; ++i) {
                  this.field_70979_e[i][0] = (double)this.field_70177_z;
                  this.field_70979_e[i][1] = this.field_70163_u;
               }
            }

            if (++this.field_70976_f == this.field_70979_e.length) {
               this.field_70976_f = 0;
            }

            this.field_70979_e[this.field_70976_f][0] = (double)this.field_70177_z;
            this.field_70979_e[this.field_70976_f][1] = this.field_70163_u;
            if (this.field_70170_p.field_72995_K) {
               if (this.field_70716_bi > 0) {
                  double d5 = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / (double)this.field_70716_bi;
                  double d0 = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / (double)this.field_70716_bi;
                  double d1 = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / (double)this.field_70716_bi;
                  double d2 = MathHelper.func_76138_g(this.field_184626_bk - (double)this.field_70177_z);
                  this.field_70177_z = (float)((double)this.field_70177_z + d2 / (double)this.field_70716_bi);
                  this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70709_bj - (double)this.field_70125_A) / (double)this.field_70716_bi);
                  --this.field_70716_bi;
                  this.func_70107_b(d5, d0, d1);
                  this.func_70101_b(this.field_70177_z, this.field_70125_A);
               }

               this.field_184677_bJ.func_188756_a().func_188657_b();
            } else {
               IPhase iphase = this.field_184677_bJ.func_188756_a();
               iphase.func_188659_c();
               if (this.field_184677_bJ.func_188756_a() != iphase) {
                  iphase = this.field_184677_bJ.func_188756_a();
                  iphase.func_188659_c();
               }

               Vec3d vec3d = iphase.func_188650_g();
               if (vec3d != null) {
                  double d6 = vec3d.field_72450_a - this.field_70165_t;
                  double d7 = vec3d.field_72448_b - this.field_70163_u;
                  double d8 = vec3d.field_72449_c - this.field_70161_v;
                  double d3 = d6 * d6 + d7 * d7 + d8 * d8;
                  float f5 = iphase.func_188651_f();
                  d7 = MathHelper.func_151237_a(d7 / (double)MathHelper.func_76133_a(d6 * d6 + d8 * d8), (double)(-f5), (double)f5);
                  this.field_70181_x += d7 * 0.10000000149011612D;
                  this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
                  double d4 = MathHelper.func_151237_a(MathHelper.func_76138_g(180.0D - MathHelper.func_181159_b(d6, d8) * 57.2957763671875D - (double)this.field_70177_z), -50.0D, 50.0D);
                  Vec3d vec3d1 = (new Vec3d(vec3d.field_72450_a - this.field_70165_t, vec3d.field_72448_b - this.field_70163_u, vec3d.field_72449_c - this.field_70161_v)).func_72432_b();
                  Vec3d vec3d2 = (new Vec3d((double)MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), this.field_70181_x, (double)(-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F)))).func_72432_b();
                  float f7 = Math.max(((float)vec3d2.func_72430_b(vec3d1) + 0.5F) / 1.5F, 0.0F);
                  this.field_70704_bt *= 0.8F;
                  this.field_70704_bt = (float)((double)this.field_70704_bt + d4 * (double)iphase.func_188653_h());
                  this.field_70177_z += this.field_70704_bt * 0.1F;
                  float f8 = (float)(2.0D / (d3 + 1.0D));
                  float f9 = 0.06F;
                  this.func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + (1.0F - f8)));
                  if (this.field_70994_bF) {
                     this.func_70091_d(MoverType.SELF, this.field_70159_w * 0.800000011920929D, this.field_70181_x * 0.800000011920929D, this.field_70179_y * 0.800000011920929D);
                  } else {
                     this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  }

                  Vec3d vec3d3 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
                  float f10 = ((float)vec3d3.func_72430_b(vec3d2) + 1.0F) / 2.0F;
                  f10 = 0.8F + 0.15F * f10;
                  this.field_70159_w *= (double)f10;
                  this.field_70179_y *= (double)f10;
                  this.field_70181_x *= 0.9100000262260437D;
               }
            }

            this.field_70761_aq = this.field_70177_z;
            this.field_70986_h.field_70130_N = 1.0F;
            this.field_70986_h.field_70131_O = 1.0F;
            this.field_184673_bv.field_70130_N = 3.0F;
            this.field_184673_bv.field_70131_O = 3.0F;
            this.field_70985_j.field_70130_N = 2.0F;
            this.field_70985_j.field_70131_O = 2.0F;
            this.field_70984_by.field_70130_N = 2.0F;
            this.field_70984_by.field_70131_O = 2.0F;
            this.field_70982_bz.field_70130_N = 2.0F;
            this.field_70982_bz.field_70131_O = 2.0F;
            this.field_70987_i.field_70131_O = 3.0F;
            this.field_70987_i.field_70130_N = 5.0F;
            this.field_70983_bA.field_70131_O = 2.0F;
            this.field_70983_bA.field_70130_N = 4.0F;
            this.field_70990_bB.field_70131_O = 3.0F;
            this.field_70990_bB.field_70130_N = 4.0F;
            Vec3d[] avec3d = new Vec3d[this.field_70977_g.length];

            for(int j = 0; j < this.field_70977_g.length; ++j) {
               avec3d[j] = new Vec3d(this.field_70977_g[j].field_70165_t, this.field_70977_g[j].field_70163_u, this.field_70977_g[j].field_70161_v);
            }

            float f14 = (float)(this.func_70974_a(5, 1.0F)[1] - this.func_70974_a(10, 1.0F)[1]) * 10.0F * 0.017453292F;
            float f16 = MathHelper.func_76134_b(f14);
            float f2 = MathHelper.func_76126_a(f14);
            float f17 = this.field_70177_z * 0.017453292F;
            float f3 = MathHelper.func_76126_a(f17);
            float f18 = MathHelper.func_76134_b(f17);
            this.field_70987_i.func_70071_h_();
            this.field_70987_i.func_70012_b(this.field_70165_t + (double)(f3 * 0.5F), this.field_70163_u, this.field_70161_v - (double)(f18 * 0.5F), 0.0F, 0.0F);
            this.field_70983_bA.func_70071_h_();
            this.field_70983_bA.func_70012_b(this.field_70165_t + (double)(f18 * 4.5F), this.field_70163_u + 2.0D, this.field_70161_v + (double)(f3 * 4.5F), 0.0F, 0.0F);
            this.field_70990_bB.func_70071_h_();
            this.field_70990_bB.func_70012_b(this.field_70165_t - (double)(f18 * 4.5F), this.field_70163_u + 2.0D, this.field_70161_v - (double)(f3 * 4.5F), 0.0F, 0.0F);
            if (!this.field_70170_p.field_72995_K && this.field_70737_aN == 0) {
               this.func_70970_a(this.field_70170_p.func_72839_b(this, this.field_70983_bA.func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
               this.func_70970_a(this.field_70170_p.func_72839_b(this, this.field_70990_bB.func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
               this.func_70971_b(this.field_70170_p.func_72839_b(this, this.field_70986_h.func_174813_aQ().func_186662_g(1.0D)));
               this.func_70971_b(this.field_70170_p.func_72839_b(this, this.field_184673_bv.func_174813_aQ().func_186662_g(1.0D)));
            }

            double[] adouble = this.func_70974_a(5, 1.0F);
            float f19 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F - this.field_70704_bt * 0.01F);
            float f4 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F - this.field_70704_bt * 0.01F);
            this.field_70986_h.func_70071_h_();
            this.field_184673_bv.func_70071_h_();
            float f20 = this.func_184662_q(1.0F);
            this.field_70986_h.func_70012_b(this.field_70165_t + (double)(f19 * 6.5F * f16), this.field_70163_u + (double)f20 + (double)(f2 * 6.5F), this.field_70161_v - (double)(f4 * 6.5F * f16), 0.0F, 0.0F);
            this.field_184673_bv.func_70012_b(this.field_70165_t + (double)(f19 * 5.5F * f16), this.field_70163_u + (double)f20 + (double)(f2 * 5.5F), this.field_70161_v - (double)(f4 * 5.5F * f16), 0.0F, 0.0F);

            for(int k = 0; k < 3; ++k) {
               MultiPartEntityPart multipartentitypart = null;
               if (k == 0) {
                  multipartentitypart = this.field_70985_j;
               }

               if (k == 1) {
                  multipartentitypart = this.field_70984_by;
               }

               if (k == 2) {
                  multipartentitypart = this.field_70982_bz;
               }

               double[] adouble1 = this.func_70974_a(12 + k * 2, 1.0F);
               float f21 = this.field_70177_z * 0.017453292F + this.func_70973_b(adouble1[0] - adouble[0]) * 0.017453292F;
               float f6 = MathHelper.func_76126_a(f21);
               float f22 = MathHelper.func_76134_b(f21);
               float f23 = 1.5F;
               float f24 = (float)(k + 1) * 2.0F;
               multipartentitypart.func_70071_h_();
               multipartentitypart.func_70012_b(this.field_70165_t - (double)((f3 * 1.5F + f6 * f24) * f16), this.field_70163_u + (adouble1[1] - adouble[1]) - (double)((f24 + 1.5F) * f2) + 1.5D, this.field_70161_v + (double)((f18 * 1.5F + f22 * f24) * f16), 0.0F, 0.0F);
            }

            if (!this.field_70170_p.field_72995_K) {
               this.field_70994_bF = this.func_70972_a(this.field_70986_h.func_174813_aQ()) | this.func_70972_a(this.field_184673_bv.func_174813_aQ()) | this.func_70972_a(this.field_70987_i.func_174813_aQ());
               if (this.field_184676_bI != null) {
                  this.field_184676_bI.func_186099_b(this);
               }
            }

            for(int l = 0; l < this.field_70977_g.length; ++l) {
               this.field_70977_g[l].field_70169_q = avec3d[l].field_72450_a;
               this.field_70977_g[l].field_70167_r = avec3d[l].field_72448_b;
               this.field_70977_g[l].field_70166_s = avec3d[l].field_72449_c;
            }

         }
      }
   }

   private float func_184662_q(float p_184662_1_) {
      double d0;
      if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
         d0 = -1.0D;
      } else {
         double[] adouble = this.func_70974_a(5, 1.0F);
         double[] adouble1 = this.func_70974_a(0, 1.0F);
         d0 = adouble[1] - adouble1[1];
      }

      return (float)d0;
   }

   private void func_70969_j() {
      if (this.field_70992_bH != null) {
         if (this.field_70992_bH.field_70128_L) {
            this.field_70992_bH = null;
         } else if (this.field_70173_aa % 10 == 0 && this.func_110143_aJ() < this.func_110138_aP()) {
            this.func_70606_j(this.func_110143_aJ() + 1.0F);
         }
      }

      if (this.field_70146_Z.nextInt(10) == 0) {
         List<EntityEnderCrystal> list = this.field_70170_p.<EntityEnderCrystal>func_72872_a(EntityEnderCrystal.class, this.func_174813_aQ().func_186662_g(32.0D));
         EntityEnderCrystal entityendercrystal = null;
         double d0 = Double.MAX_VALUE;

         for(EntityEnderCrystal entityendercrystal1 : list) {
            double d1 = entityendercrystal1.func_70068_e(this);
            if (d1 < d0) {
               d0 = d1;
               entityendercrystal = entityendercrystal1;
            }
         }

         this.field_70992_bH = entityendercrystal;
      }

   }

   private void func_70970_a(List<Entity> p_70970_1_) {
      double d0 = (this.field_70987_i.func_174813_aQ().field_72340_a + this.field_70987_i.func_174813_aQ().field_72336_d) / 2.0D;
      double d1 = (this.field_70987_i.func_174813_aQ().field_72339_c + this.field_70987_i.func_174813_aQ().field_72334_f) / 2.0D;

      for(Entity entity : p_70970_1_) {
         if (entity instanceof EntityLivingBase) {
            double d2 = entity.field_70165_t - d0;
            double d3 = entity.field_70161_v - d1;
            double d4 = d2 * d2 + d3 * d3;
            entity.func_70024_g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
            if (!this.field_184677_bJ.func_188756_a().func_188654_a() && ((EntityLivingBase)entity).func_142015_aE() < entity.field_70173_aa - 2) {
               entity.func_70097_a(DamageSource.func_76358_a(this), 5.0F);
               this.func_174815_a(this, entity);
            }
         }
      }

   }

   private void func_70971_b(List<Entity> p_70971_1_) {
      for(int i = 0; i < p_70971_1_.size(); ++i) {
         Entity entity = p_70971_1_.get(i);
         if (entity instanceof EntityLivingBase) {
            entity.func_70097_a(DamageSource.func_76358_a(this), 10.0F);
            this.func_174815_a(this, entity);
         }
      }

   }

   private float func_70973_b(double p_70973_1_) {
      return (float)MathHelper.func_76138_g(p_70973_1_);
   }

   private boolean func_70972_a(AxisAlignedBB p_70972_1_) {
      int i = MathHelper.func_76128_c(p_70972_1_.field_72340_a);
      int j = MathHelper.func_76128_c(p_70972_1_.field_72338_b);
      int k = MathHelper.func_76128_c(p_70972_1_.field_72339_c);
      int l = MathHelper.func_76128_c(p_70972_1_.field_72336_d);
      int i1 = MathHelper.func_76128_c(p_70972_1_.field_72337_e);
      int j1 = MathHelper.func_76128_c(p_70972_1_.field_72334_f);
      boolean flag = false;
      boolean flag1 = false;

      for(int k1 = i; k1 <= l; ++k1) {
         for(int l1 = j; l1 <= i1; ++l1) {
            for(int i2 = k; i2 <= j1; ++i2) {
               BlockPos blockpos = new BlockPos(k1, l1, i2);
               IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
               Block block = iblockstate.func_177230_c();
               if (iblockstate.func_185904_a() != Material.field_151579_a && iblockstate.func_185904_a() != Material.field_151581_o) {
                  if (!this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
                     flag = true;
                  } else if (block != Blocks.field_180401_cv && block != Blocks.field_150343_Z && block != Blocks.field_150377_bs && block != Blocks.field_150357_h && block != Blocks.field_150384_bq && block != Blocks.field_150378_br) {
                     if (block != Blocks.field_150483_bI && block != Blocks.field_185776_dc && block != Blocks.field_185777_dd && block != Blocks.field_150411_aY && block != Blocks.field_185775_db) {
                        flag1 = this.field_70170_p.func_175698_g(blockpos) || flag1;
                     } else {
                        flag = true;
                     }
                  } else {
                     flag = true;
                  }
               }
            }
         }
      }

      if (flag1) {
         double d0 = p_70972_1_.field_72340_a + (p_70972_1_.field_72336_d - p_70972_1_.field_72340_a) * (double)this.field_70146_Z.nextFloat();
         double d1 = p_70972_1_.field_72338_b + (p_70972_1_.field_72337_e - p_70972_1_.field_72338_b) * (double)this.field_70146_Z.nextFloat();
         double d2 = p_70972_1_.field_72339_c + (p_70972_1_.field_72334_f - p_70972_1_.field_72339_c) * (double)this.field_70146_Z.nextFloat();
         this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }

      return flag;
   }

   public boolean func_70965_a(MultiPartEntityPart p_70965_1_, DamageSource p_70965_2_, float p_70965_3_) {
      p_70965_3_ = this.field_184677_bJ.func_188756_a().func_188656_a(p_70965_1_, p_70965_2_, p_70965_3_);
      if (p_70965_1_ != this.field_70986_h) {
         p_70965_3_ = p_70965_3_ / 4.0F + Math.min(p_70965_3_, 1.0F);
      }

      if (p_70965_3_ < 0.01F) {
         return false;
      } else {
         if (p_70965_2_.func_76346_g() instanceof EntityPlayer || p_70965_2_.func_94541_c()) {
            float f = this.func_110143_aJ();
            this.func_82195_e(p_70965_2_, p_70965_3_);
            if (this.func_110143_aJ() <= 0.0F && !this.field_184677_bJ.func_188756_a().func_188654_a()) {
               this.func_70606_j(1.0F);
               this.field_184677_bJ.func_188758_a(PhaseList.field_188750_j);
            }

            if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
               this.field_184679_bL = (int)((float)this.field_184679_bL + (f - this.func_110143_aJ()));
               if ((float)this.field_184679_bL > 0.25F * this.func_110138_aP()) {
                  this.field_184679_bL = 0;
                  this.field_184677_bJ.func_188758_a(PhaseList.field_188745_e);
               }
            }
         }

         return true;
      }
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (p_70097_1_ instanceof EntityDamageSource && ((EntityDamageSource)p_70097_1_).func_180139_w()) {
         this.func_70965_a(this.field_70987_i, p_70097_1_, p_70097_2_);
      }

      return false;
   }

   protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_) {
      return super.func_70097_a(p_82195_1_, p_82195_2_);
   }

   public void func_174812_G() {
      this.func_70106_y();
      if (this.field_184676_bI != null) {
         this.field_184676_bI.func_186099_b(this);
         this.field_184676_bI.func_186096_a(this);
      }

   }

   protected void func_70609_aI() {
      if (this.field_184676_bI != null) {
         this.field_184676_bI.func_186099_b(this);
      }

      ++this.field_70995_bG;
      if (this.field_70995_bG >= 180 && this.field_70995_bG <= 200) {
         float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + (double)f, this.field_70163_u + 2.0D + (double)f1, this.field_70161_v + (double)f2, 0.0D, 0.0D, 0.0D);
      }

      boolean flag = this.field_70170_p.func_82736_K().func_82766_b("doMobLoot");
      int i = 500;
      if (this.field_184676_bI != null && !this.field_184676_bI.func_186102_d()) {
         i = 12000;
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70995_bG > 150 && this.field_70995_bG % 5 == 0 && flag) {
            this.func_184668_a(MathHelper.func_76141_d((float)i * 0.08F));
         }

         if (this.field_70995_bG == 1) {
            this.field_70170_p.func_175669_a(1028, new BlockPos(this), 0);
         }
      }

      this.func_70091_d(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
      this.field_70177_z += 20.0F;
      this.field_70761_aq = this.field_70177_z;
      if (this.field_70995_bG == 200 && !this.field_70170_p.field_72995_K) {
         if (flag) {
            this.func_184668_a(MathHelper.func_76141_d((float)i * 0.2F));
         }

         if (this.field_184676_bI != null) {
            this.field_184676_bI.func_186096_a(this);
         }

         this.func_70106_y();
      }

   }

   private void func_184668_a(int p_184668_1_) {
      while(p_184668_1_ > 0) {
         int i = EntityXPOrb.func_70527_a(p_184668_1_);
         p_184668_1_ -= i;
         this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, i));
      }

   }

   public int func_184671_o() {
      if (this.field_184680_bM[0] == null) {
         for(int i = 0; i < 24; ++i) {
            int j = 5;
            int l;
            int i1;
            if (i < 12) {
               l = (int)(60.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.2617994F * (float)i)));
               i1 = (int)(60.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.2617994F * (float)i)));
            } else if (i < 20) {
               int lvt_3_1_ = i - 12;
               l = (int)(40.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.3926991F * (float)lvt_3_1_)));
               i1 = (int)(40.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.3926991F * (float)lvt_3_1_)));
               j += 10;
            } else {
               int k1 = i - 20;
               l = (int)(20.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.7853982F * (float)k1)));
               i1 = (int)(20.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.7853982F * (float)k1)));
            }

            int j1 = Math.max(this.field_70170_p.func_181545_F() + 10, this.field_70170_p.func_175672_r(new BlockPos(l, 0, i1)).func_177956_o() + j);
            this.field_184680_bM[i] = new PathPoint(l, j1, i1);
         }

         this.field_184681_bN[0] = 6146;
         this.field_184681_bN[1] = 8197;
         this.field_184681_bN[2] = 8202;
         this.field_184681_bN[3] = 16404;
         this.field_184681_bN[4] = 32808;
         this.field_184681_bN[5] = 32848;
         this.field_184681_bN[6] = 65696;
         this.field_184681_bN[7] = 131392;
         this.field_184681_bN[8] = 131712;
         this.field_184681_bN[9] = 263424;
         this.field_184681_bN[10] = 526848;
         this.field_184681_bN[11] = 525313;
         this.field_184681_bN[12] = 1581057;
         this.field_184681_bN[13] = 3166214;
         this.field_184681_bN[14] = 2138120;
         this.field_184681_bN[15] = 6373424;
         this.field_184681_bN[16] = 4358208;
         this.field_184681_bN[17] = 12910976;
         this.field_184681_bN[18] = 9044480;
         this.field_184681_bN[19] = 9706496;
         this.field_184681_bN[20] = 15216640;
         this.field_184681_bN[21] = 13688832;
         this.field_184681_bN[22] = 11763712;
         this.field_184681_bN[23] = 8257536;
      }

      return this.func_184663_l(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public int func_184663_l(double p_184663_1_, double p_184663_3_, double p_184663_5_) {
      float f = 10000.0F;
      int i = 0;
      PathPoint pathpoint = new PathPoint(MathHelper.func_76128_c(p_184663_1_), MathHelper.func_76128_c(p_184663_3_), MathHelper.func_76128_c(p_184663_5_));
      int j = 0;
      if (this.field_184676_bI == null || this.field_184676_bI.func_186092_c() == 0) {
         j = 12;
      }

      for(int k = j; k < 24; ++k) {
         if (this.field_184680_bM[k] != null) {
            float f1 = this.field_184680_bM[k].func_75832_b(pathpoint);
            if (f1 < f) {
               f = f1;
               i = k;
            }
         }
      }

      return i;
   }

   @Nullable
   public Path func_184666_a(int p_184666_1_, int p_184666_2_, @Nullable PathPoint p_184666_3_) {
      for(int i = 0; i < 24; ++i) {
         PathPoint pathpoint = this.field_184680_bM[i];
         pathpoint.field_75842_i = false;
         pathpoint.field_75834_g = 0.0F;
         pathpoint.field_75836_e = 0.0F;
         pathpoint.field_75833_f = 0.0F;
         pathpoint.field_75841_h = null;
         pathpoint.field_75835_d = -1;
      }

      PathPoint pathpoint4 = this.field_184680_bM[p_184666_1_];
      PathPoint pathpoint5 = this.field_184680_bM[p_184666_2_];
      pathpoint4.field_75836_e = 0.0F;
      pathpoint4.field_75833_f = pathpoint4.func_75829_a(pathpoint5);
      pathpoint4.field_75834_g = pathpoint4.field_75833_f;
      this.field_184682_bO.func_75848_a();
      this.field_184682_bO.func_75849_a(pathpoint4);
      PathPoint pathpoint1 = pathpoint4;
      int j = 0;
      if (this.field_184676_bI == null || this.field_184676_bI.func_186092_c() == 0) {
         j = 12;
      }

      while(!this.field_184682_bO.func_75845_e()) {
         PathPoint pathpoint2 = this.field_184682_bO.func_75844_c();
         if (pathpoint2.equals(pathpoint5)) {
            if (p_184666_3_ != null) {
               p_184666_3_.field_75841_h = pathpoint5;
               pathpoint5 = p_184666_3_;
            }

            return this.func_184669_a(pathpoint4, pathpoint5);
         }

         if (pathpoint2.func_75829_a(pathpoint5) < pathpoint1.func_75829_a(pathpoint5)) {
            pathpoint1 = pathpoint2;
         }

         pathpoint2.field_75842_i = true;
         int k = 0;

         for(int l = 0; l < 24; ++l) {
            if (this.field_184680_bM[l] == pathpoint2) {
               k = l;
               break;
            }
         }

         for(int i1 = j; i1 < 24; ++i1) {
            if ((this.field_184681_bN[k] & 1 << i1) > 0) {
               PathPoint pathpoint3 = this.field_184680_bM[i1];
               if (!pathpoint3.field_75842_i) {
                  float f = pathpoint2.field_75836_e + pathpoint2.func_75829_a(pathpoint3);
                  if (!pathpoint3.func_75831_a() || f < pathpoint3.field_75836_e) {
                     pathpoint3.field_75841_h = pathpoint2;
                     pathpoint3.field_75836_e = f;
                     pathpoint3.field_75833_f = pathpoint3.func_75829_a(pathpoint5);
                     if (pathpoint3.func_75831_a()) {
                        this.field_184682_bO.func_75850_a(pathpoint3, pathpoint3.field_75836_e + pathpoint3.field_75833_f);
                     } else {
                        pathpoint3.field_75834_g = pathpoint3.field_75836_e + pathpoint3.field_75833_f;
                        this.field_184682_bO.func_75849_a(pathpoint3);
                     }
                  }
               }
            }
         }
      }

      if (pathpoint1 == pathpoint4) {
         return null;
      } else {
         field_184675_bH.debug("Failed to find path from {} to {}", Integer.valueOf(p_184666_1_), Integer.valueOf(p_184666_2_));
         if (p_184666_3_ != null) {
            p_184666_3_.field_75841_h = pathpoint1;
            pathpoint1 = p_184666_3_;
         }

         return this.func_184669_a(pathpoint4, pathpoint1);
      }
   }

   private Path func_184669_a(PathPoint p_184669_1_, PathPoint p_184669_2_) {
      int i = 1;

      for(PathPoint pathpoint = p_184669_2_; pathpoint.field_75841_h != null; pathpoint = pathpoint.field_75841_h) {
         ++i;
      }

      PathPoint[] apathpoint = new PathPoint[i];
      PathPoint pathpoint1 = p_184669_2_;
      --i;

      for(apathpoint[i] = p_184669_2_; pathpoint1.field_75841_h != null; apathpoint[i] = pathpoint1) {
         pathpoint1 = pathpoint1.field_75841_h;
         --i;
      }

      return new Path(apathpoint);
   }

   public static void func_189755_b(DataFixer p_189755_0_) {
      EntityLiving.func_189752_a(p_189755_0_, EntityDragon.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("DragonPhase", this.field_184677_bJ.func_188756_a().func_188652_i().func_188740_b());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_74764_b("DragonPhase")) {
         this.field_184677_bJ.func_188758_a(PhaseList.func_188738_a(p_70037_1_.func_74762_e("DragonPhase")));
      }

   }

   protected void func_70623_bb() {
   }

   public Entity[] func_70021_al() {
      return this.field_70977_g;
   }

   public boolean func_70067_L() {
      return false;
   }

   public World func_82194_d() {
      return this.field_70170_p;
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187521_aK;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187526_aP;
   }

   protected float func_70599_aP() {
      return 5.0F;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191189_ay;
   }

   public float func_184667_a(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
      IPhase iphase = this.field_184677_bJ.func_188756_a();
      PhaseList<? extends IPhase> phaselist = iphase.func_188652_i();
      double d0;
      if (phaselist != PhaseList.field_188744_d && phaselist != PhaseList.field_188745_e) {
         if (iphase.func_188654_a()) {
            d0 = (double)p_184667_1_;
         } else if (p_184667_1_ == 6) {
            d0 = 0.0D;
         } else {
            d0 = p_184667_3_[1] - p_184667_2_[1];
         }
      } else {
         BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
         float f = Math.max(MathHelper.func_76133_a(this.func_174831_c(blockpos)) / 4.0F, 1.0F);
         d0 = (double)((float)p_184667_1_ / f);
      }

      return (float)d0;
   }

   public Vec3d func_184665_a(float p_184665_1_) {
      IPhase iphase = this.field_184677_bJ.func_188756_a();
      PhaseList<? extends IPhase> phaselist = iphase.func_188652_i();
      Vec3d vec3d;
      if (phaselist != PhaseList.field_188744_d && phaselist != PhaseList.field_188745_e) {
         if (iphase.func_188654_a()) {
            float f4 = this.field_70125_A;
            float f5 = 1.5F;
            this.field_70125_A = -45.0F;
            vec3d = this.func_70676_i(p_184665_1_);
            this.field_70125_A = f4;
         } else {
            vec3d = this.func_70676_i(p_184665_1_);
         }
      } else {
         BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
         float f = Math.max(MathHelper.func_76133_a(this.func_174831_c(blockpos)) / 4.0F, 1.0F);
         float f1 = 6.0F / f;
         float f2 = this.field_70125_A;
         float f3 = 1.5F;
         this.field_70125_A = -f1 * 1.5F * 5.0F;
         vec3d = this.func_70676_i(p_184665_1_);
         this.field_70125_A = f2;
      }

      return vec3d;
   }

   public void func_184672_a(EntityEnderCrystal p_184672_1_, BlockPos p_184672_2_, DamageSource p_184672_3_) {
      EntityPlayer entityplayer;
      if (p_184672_3_.func_76346_g() instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)p_184672_3_.func_76346_g();
      } else {
         entityplayer = this.field_70170_p.func_184139_a(p_184672_2_, 64.0D, 64.0D);
      }

      if (p_184672_1_ == this.field_70992_bH) {
         this.func_70965_a(this.field_70986_h, DamageSource.func_188405_b(entityplayer), 10.0F);
      }

      this.field_184677_bJ.func_188756_a().func_188655_a(p_184672_1_, p_184672_2_, p_184672_3_, entityplayer);
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184674_a.equals(p_184206_1_) && this.field_70170_p.field_72995_K) {
         this.field_184677_bJ.func_188758_a(PhaseList.func_188738_a(((Integer)this.func_184212_Q().func_187225_a(field_184674_a)).intValue()));
      }

      super.func_184206_a(p_184206_1_);
   }

   public PhaseManager func_184670_cT() {
      return this.field_184677_bJ;
   }

   @Nullable
   public DragonFightManager func_184664_cU() {
      return this.field_184676_bI;
   }

   public void func_70690_d(PotionEffect p_70690_1_) {
   }

   protected boolean func_184228_n(Entity p_184228_1_) {
      return false;
   }

   public boolean func_184222_aU() {
      return false;
   }
}
