package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWither extends EntityMob implements IRangedAttackMob {
   private static final DataParameter<Integer> field_184741_a = EntityDataManager.<Integer>func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184742_b = EntityDataManager.<Integer>func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184743_c = EntityDataManager.<Integer>func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer>[] field_184745_bv = new DataParameter[]{field_184741_a, field_184742_b, field_184743_c};
   private static final DataParameter<Integer> field_184746_bw = EntityDataManager.<Integer>func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private final float[] field_82220_d = new float[2];
   private final float[] field_82221_e = new float[2];
   private final float[] field_82217_f = new float[2];
   private final float[] field_82218_g = new float[2];
   private final int[] field_82223_h = new int[2];
   private final int[] field_82224_i = new int[2];
   private int field_82222_j;
   private final BossInfoServer field_184744_bE = (BossInfoServer)(new BossInfoServer(this.func_145748_c_(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).func_186741_a(true);
   private static final Predicate<Entity> field_82219_bJ = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).func_70668_bt() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).func_190631_cK();
      }
   };

   public EntityWither(World p_i1701_1_) {
      super(p_i1701_1_);
      this.func_70606_j(this.func_110138_aP());
      this.func_70105_a(0.9F, 3.5F);
      this.field_70178_ae = true;
      ((PathNavigateGround)this.func_70661_as()).func_179693_d(true);
      this.field_70728_aV = 50;
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityWither.AIDoNothing());
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIAttackRanged(this, 1.0D, 40, 20.0F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, field_82219_bJ));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184741_a, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184742_b, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184743_c, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184746_bw, Integer.valueOf(0));
   }

   public static void func_189782_b(DataFixer p_189782_0_) {
      EntityLiving.func_189752_a(p_189782_0_, EntityWither.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Invul", this.func_82212_n());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_82215_s(p_70037_1_.func_74762_e("Invul"));
      if (this.func_145818_k_()) {
         this.field_184744_bE.func_186739_a(this.func_145748_c_());
      }

   }

   public void func_96094_a(String p_96094_1_) {
      super.func_96094_a(p_96094_1_);
      this.field_184744_bE.func_186739_a(this.func_145748_c_());
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187925_gy;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187851_gB;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187849_gA;
   }

   public void func_70636_d() {
      this.field_70181_x *= 0.6000000238418579D;
      if (!this.field_70170_p.field_72995_K && this.func_82203_t(0) > 0) {
         Entity entity = this.field_70170_p.func_73045_a(this.func_82203_t(0));
         if (entity != null) {
            if (this.field_70163_u < entity.field_70163_u || !this.func_82205_o() && this.field_70163_u < entity.field_70163_u + 5.0D) {
               if (this.field_70181_x < 0.0D) {
                  this.field_70181_x = 0.0D;
               }

               this.field_70181_x += (0.5D - this.field_70181_x) * 0.6000000238418579D;
            }

            double d0 = entity.field_70165_t - this.field_70165_t;
            double d1 = entity.field_70161_v - this.field_70161_v;
            double d3 = d0 * d0 + d1 * d1;
            if (d3 > 9.0D) {
               double d5 = (double)MathHelper.func_76133_a(d3);
               this.field_70159_w += (d0 / d5 * 0.5D - this.field_70159_w) * 0.6000000238418579D;
               this.field_70179_y += (d1 / d5 * 0.5D - this.field_70179_y) * 0.6000000238418579D;
            }
         }
      }

      if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.05000000074505806D) {
         this.field_70177_z = (float)MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F;
      }

      super.func_70636_d();

      for(int i = 0; i < 2; ++i) {
         this.field_82218_g[i] = this.field_82221_e[i];
         this.field_82217_f[i] = this.field_82220_d[i];
      }

      for(int j = 0; j < 2; ++j) {
         int k = this.func_82203_t(j + 1);
         Entity entity1 = null;
         if (k > 0) {
            entity1 = this.field_70170_p.func_73045_a(k);
         }

         if (entity1 != null) {
            double d11 = this.func_82214_u(j + 1);
            double d12 = this.func_82208_v(j + 1);
            double d13 = this.func_82213_w(j + 1);
            double d6 = entity1.field_70165_t - d11;
            double d7 = entity1.field_70163_u + (double)entity1.func_70047_e() - d12;
            double d8 = entity1.field_70161_v - d13;
            double d9 = (double)MathHelper.func_76133_a(d6 * d6 + d8 * d8);
            float f = (float)(MathHelper.func_181159_b(d8, d6) * 57.2957763671875D) - 90.0F;
            float f1 = (float)(-(MathHelper.func_181159_b(d7, d9) * 57.2957763671875D));
            this.field_82220_d[j] = this.func_82204_b(this.field_82220_d[j], f1, 40.0F);
            this.field_82221_e[j] = this.func_82204_b(this.field_82221_e[j], f, 10.0F);
         } else {
            this.field_82221_e[j] = this.func_82204_b(this.field_82221_e[j], this.field_70761_aq, 10.0F);
         }
      }

      boolean flag = this.func_82205_o();

      for(int l = 0; l < 3; ++l) {
         double d10 = this.func_82214_u(l);
         double d2 = this.func_82208_v(l);
         double d4 = this.func_82213_w(l);
         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d10 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d2 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d4 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
         if (flag && this.field_70170_p.field_73012_v.nextInt(4) == 0) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, d10 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d2 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d4 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
         }
      }

      if (this.func_82212_n() > 0) {
         for(int i1 = 0; i1 < 3; ++i1) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + this.field_70146_Z.nextGaussian(), this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * 3.3F), this.field_70161_v + this.field_70146_Z.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
         }
      }

   }

   protected void func_70619_bc() {
      if (this.func_82212_n() > 0) {
         int j1 = this.func_82212_n() - 1;
         if (j1 <= 0) {
            this.field_70170_p.func_72885_a(this, this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v, 7.0F, false, this.field_70170_p.func_82736_K().func_82766_b("mobGriefing"));
            this.field_70170_p.func_175669_a(1023, new BlockPos(this), 0);
         }

         this.func_82215_s(j1);
         if (this.field_70173_aa % 10 == 0) {
            this.func_70691_i(10.0F);
         }

      } else {
         super.func_70619_bc();

         for(int i = 1; i < 3; ++i) {
            if (this.field_70173_aa >= this.field_82223_h[i - 1]) {
               this.field_82223_h[i - 1] = this.field_70173_aa + 10 + this.field_70146_Z.nextInt(10);
               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                  int j3 = i - 1;
                  int k3 = this.field_82224_i[i - 1];
                  this.field_82224_i[j3] = this.field_82224_i[i - 1] + 1;
                  if (k3 > 15) {
                     float f = 10.0F;
                     float f1 = 5.0F;
                     double d0 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70165_t - 10.0D, this.field_70165_t + 10.0D);
                     double d1 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70163_u - 5.0D, this.field_70163_u + 5.0D);
                     double d2 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70161_v - 10.0D, this.field_70161_v + 10.0D);
                     this.func_82209_a(i + 1, d0, d1, d2, true);
                     this.field_82224_i[i - 1] = 0;
                  }
               }

               int k1 = this.func_82203_t(i);
               if (k1 > 0) {
                  Entity entity = this.field_70170_p.func_73045_a(k1);
                  if (entity != null && entity.func_70089_S() && this.func_70068_e(entity) <= 900.0D && this.func_70685_l(entity)) {
                     if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75102_a) {
                        this.func_82211_c(i, 0);
                     } else {
                        this.func_82216_a(i + 1, (EntityLivingBase)entity);
                        this.field_82223_h[i - 1] = this.field_70173_aa + 40 + this.field_70146_Z.nextInt(20);
                        this.field_82224_i[i - 1] = 0;
                     }
                  } else {
                     this.func_82211_c(i, 0);
                  }
               } else {
                  List<EntityLivingBase> list = this.field_70170_p.<EntityLivingBase>func_175647_a(EntityLivingBase.class, this.func_174813_aQ().func_72314_b(20.0D, 8.0D, 20.0D), Predicates.and(field_82219_bJ, EntitySelectors.field_180132_d));

                  for(int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2) {
                     EntityLivingBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
                     if (entitylivingbase != this && entitylivingbase.func_70089_S() && this.func_70685_l(entitylivingbase)) {
                        if (entitylivingbase instanceof EntityPlayer) {
                           if (!((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a) {
                              this.func_82211_c(i, entitylivingbase.func_145782_y());
                           }
                        } else {
                           this.func_82211_c(i, entitylivingbase.func_145782_y());
                        }
                        break;
                     }

                     list.remove(entitylivingbase);
                  }
               }
            }
         }

         if (this.func_70638_az() != null) {
            this.func_82211_c(0, this.func_70638_az().func_145782_y());
         } else {
            this.func_82211_c(0, 0);
         }

         if (this.field_82222_j > 0) {
            --this.field_82222_j;
            if (this.field_82222_j == 0 && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
               int i1 = MathHelper.func_76128_c(this.field_70163_u);
               int l1 = MathHelper.func_76128_c(this.field_70165_t);
               int i2 = MathHelper.func_76128_c(this.field_70161_v);
               boolean flag = false;

               for(int k2 = -1; k2 <= 1; ++k2) {
                  for(int l2 = -1; l2 <= 1; ++l2) {
                     for(int j = 0; j <= 3; ++j) {
                        int i3 = l1 + k2;
                        int k = i1 + j;
                        int l = i2 + l2;
                        BlockPos blockpos = new BlockPos(i3, k, l);
                        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                        Block block = iblockstate.func_177230_c();
                        if (iblockstate.func_185904_a() != Material.field_151579_a && func_181033_a(block)) {
                           flag = this.field_70170_p.func_175655_b(blockpos, true) || flag;
                        }
                     }
                  }
               }

               if (flag) {
                  this.field_70170_p.func_180498_a((EntityPlayer)null, 1022, new BlockPos(this), 0);
               }
            }
         }

         if (this.field_70173_aa % 20 == 0) {
            this.func_70691_i(1.0F);
         }

         this.field_184744_bE.func_186735_a(this.func_110143_aJ() / this.func_110138_aP());
      }
   }

   public static boolean func_181033_a(Block p_181033_0_) {
      return p_181033_0_ != Blocks.field_150357_h && p_181033_0_ != Blocks.field_150384_bq && p_181033_0_ != Blocks.field_150378_br && p_181033_0_ != Blocks.field_150483_bI && p_181033_0_ != Blocks.field_185776_dc && p_181033_0_ != Blocks.field_185777_dd && p_181033_0_ != Blocks.field_180401_cv && p_181033_0_ != Blocks.field_185779_df && p_181033_0_ != Blocks.field_189881_dj && p_181033_0_ != Blocks.field_180384_M && p_181033_0_ != Blocks.field_185775_db;
   }

   public void func_82206_m() {
      this.func_82215_s(220);
      this.func_70606_j(this.func_110138_aP() / 3.0F);
   }

   public void func_70110_aj() {
   }

   public void func_184178_b(EntityPlayerMP p_184178_1_) {
      super.func_184178_b(p_184178_1_);
      this.field_184744_bE.func_186760_a(p_184178_1_);
   }

   public void func_184203_c(EntityPlayerMP p_184203_1_) {
      super.func_184203_c(p_184203_1_);
      this.field_184744_bE.func_186761_b(p_184203_1_);
   }

   private double func_82214_u(int p_82214_1_) {
      if (p_82214_1_ <= 0) {
         return this.field_70165_t;
      } else {
         float f = (this.field_70761_aq + (float)(180 * (p_82214_1_ - 1))) * 0.017453292F;
         float f1 = MathHelper.func_76134_b(f);
         return this.field_70165_t + (double)f1 * 1.3D;
      }
   }

   private double func_82208_v(int p_82208_1_) {
      return p_82208_1_ <= 0 ? this.field_70163_u + 3.0D : this.field_70163_u + 2.2D;
   }

   private double func_82213_w(int p_82213_1_) {
      if (p_82213_1_ <= 0) {
         return this.field_70161_v;
      } else {
         float f = (this.field_70761_aq + (float)(180 * (p_82213_1_ - 1))) * 0.017453292F;
         float f1 = MathHelper.func_76126_a(f);
         return this.field_70161_v + (double)f1 * 1.3D;
      }
   }

   private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
      float f = MathHelper.func_76142_g(p_82204_2_ - p_82204_1_);
      if (f > p_82204_3_) {
         f = p_82204_3_;
      }

      if (f < -p_82204_3_) {
         f = -p_82204_3_;
      }

      return p_82204_1_ + f;
   }

   private void func_82216_a(int p_82216_1_, EntityLivingBase p_82216_2_) {
      this.func_82209_a(p_82216_1_, p_82216_2_.field_70165_t, p_82216_2_.field_70163_u + (double)p_82216_2_.func_70047_e() * 0.5D, p_82216_2_.field_70161_v, p_82216_1_ == 0 && this.field_70146_Z.nextFloat() < 0.001F);
   }

   private void func_82209_a(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_) {
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1024, new BlockPos(this), 0);
      double d0 = this.func_82214_u(p_82209_1_);
      double d1 = this.func_82208_v(p_82209_1_);
      double d2 = this.func_82213_w(p_82209_1_);
      double d3 = p_82209_2_ - d0;
      double d4 = p_82209_4_ - d1;
      double d5 = p_82209_6_ - d2;
      EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.field_70170_p, this, d3, d4, d5);
      if (p_82209_8_) {
         entitywitherskull.func_82343_e(true);
      }

      entitywitherskull.field_70163_u = d1;
      entitywitherskull.field_70165_t = d0;
      entitywitherskull.field_70161_v = d2;
      this.field_70170_p.func_72838_d(entitywitherskull);
   }

   public void func_82196_d(EntityLivingBase p_82196_1_, float p_82196_2_) {
      this.func_82216_a(0, p_82196_1_);
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (p_70097_1_ != DamageSource.field_76369_e && !(p_70097_1_.func_76346_g() instanceof EntityWither)) {
         if (this.func_82212_n() > 0 && p_70097_1_ != DamageSource.field_76380_i) {
            return false;
         } else {
            if (this.func_82205_o()) {
               Entity entity = p_70097_1_.func_76364_f();
               if (entity instanceof EntityArrow) {
                  return false;
               }
            }

            Entity entity1 = p_70097_1_.func_76346_g();
            if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase)entity1).func_70668_bt() == this.func_70668_bt()) {
               return false;
            } else {
               if (this.field_82222_j <= 0) {
                  this.field_82222_j = 20;
               }

               for(int i = 0; i < this.field_82224_i.length; ++i) {
                  this.field_82224_i[i] += 3;
               }

               return super.func_70097_a(p_70097_1_, p_70097_2_);
            }
         }
      } else {
         return false;
      }
   }

   protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
      EntityItem entityitem = this.func_145779_a(Items.field_151156_bN, 1);
      if (entityitem != null) {
         entityitem.func_174873_u();
      }

   }

   protected void func_70623_bb() {
      this.field_70708_bq = 0;
   }

   public int func_70070_b() {
      return 15728880;
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
   }

   public void func_70690_d(PotionEffect p_70690_1_) {
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.6000000238418579D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(4.0D);
   }

   public float func_82207_a(int p_82207_1_) {
      return this.field_82221_e[p_82207_1_];
   }

   public float func_82210_r(int p_82210_1_) {
      return this.field_82220_d[p_82210_1_];
   }

   public int func_82212_n() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184746_bw)).intValue();
   }

   public void func_82215_s(int p_82215_1_) {
      this.field_70180_af.func_187227_b(field_184746_bw, Integer.valueOf(p_82215_1_));
   }

   public int func_82203_t(int p_82203_1_) {
      return ((Integer)this.field_70180_af.func_187225_a(field_184745_bv[p_82203_1_])).intValue();
   }

   public void func_82211_c(int p_82211_1_, int p_82211_2_) {
      this.field_70180_af.func_187227_b(field_184745_bv[p_82211_1_], Integer.valueOf(p_82211_2_));
   }

   public boolean func_82205_o() {
      return this.func_110143_aJ() <= this.func_110138_aP() / 2.0F;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected boolean func_184228_n(Entity p_184228_1_) {
      return false;
   }

   public boolean func_184222_aU() {
      return false;
   }

   public void func_184724_a(boolean p_184724_1_) {
   }

   class AIDoNothing extends EntityAIBase {
      public AIDoNothing() {
         this.func_75248_a(7);
      }

      public boolean func_75250_a() {
         return EntityWither.this.func_82212_n() > 0;
      }
   }
}
