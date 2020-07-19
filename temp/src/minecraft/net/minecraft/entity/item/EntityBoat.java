package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBoat extends Entity {
   private static final DataParameter<Integer> field_184460_a = EntityDataManager.<Integer>func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184462_b = EntityDataManager.<Integer>func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Float> field_184464_c = EntityDataManager.<Float>func_187226_a(EntityBoat.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184466_d = EntityDataManager.<Integer>func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean>[] field_184468_e = new DataParameter[]{EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187198_h), EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187198_h)};
   private final float[] field_184470_f;
   private float field_184472_g;
   private float field_184474_h;
   private float field_184475_as;
   private int field_184476_at;
   private double field_70281_h;
   private double field_184477_av;
   private double field_184478_aw;
   private double field_70273_g;
   private double field_184479_ay;
   private boolean field_184480_az;
   private boolean field_184459_aA;
   private boolean field_184461_aB;
   private boolean field_184463_aC;
   private double field_184465_aD;
   private float field_184467_aE;
   private EntityBoat.Status field_184469_aF;
   private EntityBoat.Status field_184471_aG;
   private double field_184473_aH;

   public EntityBoat(World p_i1704_1_) {
      super(p_i1704_1_);
      this.field_184470_f = new float[2];
      this.field_70156_m = true;
      this.func_70105_a(1.375F, 0.5625F);
   }

   public EntityBoat(World p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
      this(p_i1705_1_);
      this.func_70107_b(p_i1705_2_, p_i1705_4_, p_i1705_6_);
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70169_q = p_i1705_2_;
      this.field_70167_r = p_i1705_4_;
      this.field_70166_s = p_i1705_6_;
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184460_a, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184462_b, Integer.valueOf(1));
      this.field_70180_af.func_187214_a(field_184464_c, Float.valueOf(0.0F));
      this.field_70180_af.func_187214_a(field_184466_d, Integer.valueOf(EntityBoat.Type.OAK.ordinal()));

      for(DataParameter<Boolean> dataparameter : field_184468_e) {
         this.field_70180_af.func_187214_a(dataparameter, Boolean.valueOf(false));
      }

   }

   @Nullable
   public AxisAlignedBB func_70114_g(Entity p_70114_1_) {
      return p_70114_1_.func_70104_M() ? p_70114_1_.func_174813_aQ() : null;
   }

   @Nullable
   public AxisAlignedBB func_70046_E() {
      return this.func_174813_aQ();
   }

   public boolean func_70104_M() {
      return true;
   }

   public double func_70042_X() {
      return -0.1D;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         if (p_70097_1_ instanceof EntityDamageSourceIndirect && p_70097_1_.func_76346_g() != null && this.func_184196_w(p_70097_1_.func_76346_g())) {
            return false;
         } else {
            this.func_70269_c(-this.func_70267_i());
            this.func_70265_b(10);
            this.func_70266_a(this.func_70271_g() + p_70097_2_ * 10.0F);
            this.func_70018_K();
            boolean flag = p_70097_1_.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75098_d;
            if (flag || this.func_70271_g() > 40.0F) {
               if (!flag && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
                  this.func_145778_a(this.func_184455_j(), 1, 0.0F);
               }

               this.func_70106_y();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public void func_70108_f(Entity p_70108_1_) {
      if (p_70108_1_ instanceof EntityBoat) {
         if (p_70108_1_.func_174813_aQ().field_72338_b < this.func_174813_aQ().field_72337_e) {
            super.func_70108_f(p_70108_1_);
         }
      } else if (p_70108_1_.func_174813_aQ().field_72338_b <= this.func_174813_aQ().field_72338_b) {
         super.func_70108_f(p_70108_1_);
      }

   }

   public Item func_184455_j() {
      switch(this.func_184453_r()) {
      case OAK:
      default:
         return Items.field_151124_az;
      case SPRUCE:
         return Items.field_185150_aH;
      case BIRCH:
         return Items.field_185151_aI;
      case JUNGLE:
         return Items.field_185152_aJ;
      case ACACIA:
         return Items.field_185153_aK;
      case DARK_OAK:
         return Items.field_185154_aL;
      }
   }

   public void func_70057_ab() {
      this.func_70269_c(-this.func_70267_i());
      this.func_70265_b(10);
      this.func_70266_a(this.func_70271_g() * 11.0F);
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.field_70281_h = p_180426_1_;
      this.field_184477_av = p_180426_3_;
      this.field_184478_aw = p_180426_5_;
      this.field_70273_g = (double)p_180426_7_;
      this.field_184479_ay = (double)p_180426_8_;
      this.field_184476_at = 10;
   }

   public EnumFacing func_184172_bi() {
      return this.func_174811_aO().func_176746_e();
   }

   public void func_70071_h_() {
      this.field_184471_aG = this.field_184469_aF;
      this.field_184469_aF = this.func_184449_t();
      if (this.field_184469_aF != EntityBoat.Status.UNDER_WATER && this.field_184469_aF != EntityBoat.Status.UNDER_FLOWING_WATER) {
         this.field_184474_h = 0.0F;
      } else {
         ++this.field_184474_h;
      }

      if (!this.field_70170_p.field_72995_K && this.field_184474_h >= 60.0F) {
         this.func_184226_ay();
      }

      if (this.func_70268_h() > 0) {
         this.func_70265_b(this.func_70268_h() - 1);
      }

      if (this.func_70271_g() > 0.0F) {
         this.func_70266_a(this.func_70271_g() - 1.0F);
      }

      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      super.func_70071_h_();
      this.func_184447_s();
      if (this.func_184186_bw()) {
         if (this.func_184188_bt().isEmpty() || !(this.func_184188_bt().get(0) instanceof EntityPlayer)) {
            this.func_184445_a(false, false);
         }

         this.func_184450_w();
         if (this.field_70170_p.field_72995_K) {
            this.func_184443_x();
            this.field_70170_p.func_184135_a(new CPacketSteerBoat(this.func_184457_a(0), this.func_184457_a(1)));
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      } else {
         this.field_70159_w = 0.0D;
         this.field_70181_x = 0.0D;
         this.field_70179_y = 0.0D;
      }

      for(int i = 0; i <= 1; ++i) {
         if (this.func_184457_a(i)) {
            if (!this.func_174814_R() && (double)(this.field_184470_f[i] % 6.2831855F) <= 0.7853981852531433D && ((double)this.field_184470_f[i] + 0.39269909262657166D) % 6.2831854820251465D >= 0.7853981852531433D) {
               SoundEvent soundevent = this.func_193047_k();
               if (soundevent != null) {
                  Vec3d vec3d = this.func_70676_i(1.0F);
                  double d0 = i == 1 ? -vec3d.field_72449_c : vec3d.field_72449_c;
                  double d1 = i == 1 ? vec3d.field_72450_a : -vec3d.field_72450_a;
                  this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t + d0, this.field_70163_u, this.field_70161_v + d1, soundevent, this.func_184176_by(), 1.0F, 0.8F + 0.4F * this.field_70146_Z.nextFloat());
               }
            }

            this.field_184470_f[i] = (float)((double)this.field_184470_f[i] + 0.39269909262657166D);
         } else {
            this.field_184470_f[i] = 0.0F;
         }
      }

      this.func_145775_I();
      List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72314_b(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.func_188442_a(this));
      if (!list.isEmpty()) {
         boolean flag = !this.field_70170_p.field_72995_K && !(this.func_184179_bs() instanceof EntityPlayer);

         for(int j = 0; j < list.size(); ++j) {
            Entity entity = list.get(j);
            if (!entity.func_184196_w(this)) {
               if (flag && this.func_184188_bt().size() < 2 && !entity.func_184218_aH() && entity.field_70130_N < this.field_70130_N && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
                  entity.func_184220_m(this);
               } else {
                  this.func_70108_f(entity);
               }
            }
         }
      }

   }

   @Nullable
   protected SoundEvent func_193047_k() {
      switch(this.func_184449_t()) {
      case IN_WATER:
      case UNDER_WATER:
      case UNDER_FLOWING_WATER:
         return SoundEvents.field_193779_I;
      case ON_LAND:
         return SoundEvents.field_193778_H;
      case IN_AIR:
      default:
         return null;
      }
   }

   private void func_184447_s() {
      if (this.field_184476_at > 0 && !this.func_184186_bw()) {
         double d0 = this.field_70165_t + (this.field_70281_h - this.field_70165_t) / (double)this.field_184476_at;
         double d1 = this.field_70163_u + (this.field_184477_av - this.field_70163_u) / (double)this.field_184476_at;
         double d2 = this.field_70161_v + (this.field_184478_aw - this.field_70161_v) / (double)this.field_184476_at;
         double d3 = MathHelper.func_76138_g(this.field_70273_g - (double)this.field_70177_z);
         this.field_70177_z = (float)((double)this.field_70177_z + d3 / (double)this.field_184476_at);
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_184479_ay - (double)this.field_70125_A) / (double)this.field_184476_at);
         --this.field_184476_at;
         this.func_70107_b(d0, d1, d2);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      }
   }

   public void func_184445_a(boolean p_184445_1_, boolean p_184445_2_) {
      this.field_70180_af.func_187227_b(field_184468_e[0], Boolean.valueOf(p_184445_1_));
      this.field_70180_af.func_187227_b(field_184468_e[1], Boolean.valueOf(p_184445_2_));
   }

   public float func_184448_a(int p_184448_1_, float p_184448_2_) {
      return this.func_184457_a(p_184448_1_) ? (float)MathHelper.func_151238_b((double)this.field_184470_f[p_184448_1_] - 0.39269909262657166D, (double)this.field_184470_f[p_184448_1_], (double)p_184448_2_) : 0.0F;
   }

   private EntityBoat.Status func_184449_t() {
      EntityBoat.Status entityboat$status = this.func_184444_v();
      if (entityboat$status != null) {
         this.field_184465_aD = this.func_174813_aQ().field_72337_e;
         return entityboat$status;
      } else if (this.func_184446_u()) {
         return EntityBoat.Status.IN_WATER;
      } else {
         float f = this.func_184441_l();
         if (f > 0.0F) {
            this.field_184467_aE = f;
            return EntityBoat.Status.ON_LAND;
         } else {
            return EntityBoat.Status.IN_AIR;
         }
      }
   }

   public float func_184451_k() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      int i = MathHelper.func_76128_c(axisalignedbb.field_72340_a);
      int j = MathHelper.func_76143_f(axisalignedbb.field_72336_d);
      int k = MathHelper.func_76128_c(axisalignedbb.field_72337_e);
      int l = MathHelper.func_76143_f(axisalignedbb.field_72337_e - this.field_184473_aH);
      int i1 = MathHelper.func_76128_c(axisalignedbb.field_72339_c);
      int j1 = MathHelper.func_76143_f(axisalignedbb.field_72334_f);
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

      try {
         label108:
         for(int k1 = k; k1 < l; ++k1) {
            float f = 0.0F;
            int l1 = i;

            while(true) {
               if (l1 >= j) {
                  if (f < 1.0F) {
                     float f2 = (float)blockpos$pooledmutableblockpos.func_177956_o() + f;
                     return f2;
                  }
                  break;
               }

               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.func_181079_c(l1, k1, i2);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos);
                  if (iblockstate.func_185904_a() == Material.field_151586_h) {
                     f = Math.max(f, BlockLiquid.func_190973_f(iblockstate, this.field_70170_p, blockpos$pooledmutableblockpos));
                  }

                  if (f >= 1.0F) {
                     continue label108;
                  }
               }

               ++l1;
            }
         }

         float f1 = (float)(l + 1);
         return f1;
      } finally {
         blockpos$pooledmutableblockpos.func_185344_t();
      }
   }

   public float func_184441_l() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b - 0.001D, axisalignedbb.field_72339_c, axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f);
      int i = MathHelper.func_76128_c(axisalignedbb1.field_72340_a) - 1;
      int j = MathHelper.func_76143_f(axisalignedbb1.field_72336_d) + 1;
      int k = MathHelper.func_76128_c(axisalignedbb1.field_72338_b) - 1;
      int l = MathHelper.func_76143_f(axisalignedbb1.field_72337_e) + 1;
      int i1 = MathHelper.func_76128_c(axisalignedbb1.field_72339_c) - 1;
      int j1 = MathHelper.func_76143_f(axisalignedbb1.field_72334_f) + 1;
      List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
      float f = 0.0F;
      int k1 = 0;
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

      try {
         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
               if (j2 != 2) {
                  for(int k2 = k; k2 < l; ++k2) {
                     if (j2 <= 0 || k2 != k && k2 != l - 1) {
                        blockpos$pooledmutableblockpos.func_181079_c(l1, k2, i2);
                        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos);
                        iblockstate.func_185908_a(this.field_70170_p, blockpos$pooledmutableblockpos, axisalignedbb1, list, this, false);
                        if (!list.isEmpty()) {
                           f += iblockstate.func_177230_c().field_149765_K;
                           ++k1;
                        }

                        list.clear();
                     }
                  }
               }
            }
         }
      } finally {
         blockpos$pooledmutableblockpos.func_185344_t();
      }

      return f / (float)k1;
   }

   private boolean func_184446_u() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      int i = MathHelper.func_76128_c(axisalignedbb.field_72340_a);
      int j = MathHelper.func_76143_f(axisalignedbb.field_72336_d);
      int k = MathHelper.func_76128_c(axisalignedbb.field_72338_b);
      int l = MathHelper.func_76143_f(axisalignedbb.field_72338_b + 0.001D);
      int i1 = MathHelper.func_76128_c(axisalignedbb.field_72339_c);
      int j1 = MathHelper.func_76143_f(axisalignedbb.field_72334_f);
      boolean flag = false;
      this.field_184465_aD = Double.MIN_VALUE;
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

      try {
         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.func_181079_c(k1, l1, i2);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos);
                  if (iblockstate.func_185904_a() == Material.field_151586_h) {
                     float f = BlockLiquid.func_190972_g(iblockstate, this.field_70170_p, blockpos$pooledmutableblockpos);
                     this.field_184465_aD = Math.max((double)f, this.field_184465_aD);
                     flag |= axisalignedbb.field_72338_b < (double)f;
                  }
               }
            }
         }
      } finally {
         blockpos$pooledmutableblockpos.func_185344_t();
      }

      return flag;
   }

   @Nullable
   private EntityBoat.Status func_184444_v() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      double d0 = axisalignedbb.field_72337_e + 0.001D;
      int i = MathHelper.func_76128_c(axisalignedbb.field_72340_a);
      int j = MathHelper.func_76143_f(axisalignedbb.field_72336_d);
      int k = MathHelper.func_76128_c(axisalignedbb.field_72337_e);
      int l = MathHelper.func_76143_f(d0);
      int i1 = MathHelper.func_76128_c(axisalignedbb.field_72339_c);
      int j1 = MathHelper.func_76143_f(axisalignedbb.field_72334_f);
      boolean flag = false;
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

      try {
         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.func_181079_c(k1, l1, i2);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos);
                  if (iblockstate.func_185904_a() == Material.field_151586_h && d0 < (double)BlockLiquid.func_190972_g(iblockstate, this.field_70170_p, blockpos$pooledmutableblockpos)) {
                     if (((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() != 0) {
                        EntityBoat.Status entityboat$status = EntityBoat.Status.UNDER_FLOWING_WATER;
                        return entityboat$status;
                     }

                     flag = true;
                  }
               }
            }
         }
      } finally {
         blockpos$pooledmutableblockpos.func_185344_t();
      }

      return flag ? EntityBoat.Status.UNDER_WATER : null;
   }

   private void func_184450_w() {
      double d0 = -0.03999999910593033D;
      double d1 = this.func_189652_ae() ? 0.0D : -0.03999999910593033D;
      double d2 = 0.0D;
      this.field_184472_g = 0.05F;
      if (this.field_184471_aG == EntityBoat.Status.IN_AIR && this.field_184469_aF != EntityBoat.Status.IN_AIR && this.field_184469_aF != EntityBoat.Status.ON_LAND) {
         this.field_184465_aD = this.func_174813_aQ().field_72338_b + (double)this.field_70131_O;
         this.func_70107_b(this.field_70165_t, (double)(this.func_184451_k() - this.field_70131_O) + 0.101D, this.field_70161_v);
         this.field_70181_x = 0.0D;
         this.field_184473_aH = 0.0D;
         this.field_184469_aF = EntityBoat.Status.IN_WATER;
      } else {
         if (this.field_184469_aF == EntityBoat.Status.IN_WATER) {
            d2 = (this.field_184465_aD - this.func_174813_aQ().field_72338_b) / (double)this.field_70131_O;
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.UNDER_FLOWING_WATER) {
            d1 = -7.0E-4D;
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.UNDER_WATER) {
            d2 = 0.009999999776482582D;
            this.field_184472_g = 0.45F;
         } else if (this.field_184469_aF == EntityBoat.Status.IN_AIR) {
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.ON_LAND) {
            this.field_184472_g = this.field_184467_aE;
            if (this.func_184179_bs() instanceof EntityPlayer) {
               this.field_184467_aE /= 2.0F;
            }
         }

         this.field_70159_w *= (double)this.field_184472_g;
         this.field_70179_y *= (double)this.field_184472_g;
         this.field_184475_as *= this.field_184472_g;
         this.field_70181_x += d1;
         if (d2 > 0.0D) {
            double d3 = 0.65D;
            this.field_70181_x += d2 * 0.06153846016296973D;
            double d4 = 0.75D;
            this.field_70181_x *= 0.75D;
         }
      }

   }

   private void func_184443_x() {
      if (this.func_184207_aI()) {
         float f = 0.0F;
         if (this.field_184480_az) {
            this.field_184475_as += -1.0F;
         }

         if (this.field_184459_aA) {
            ++this.field_184475_as;
         }

         if (this.field_184459_aA != this.field_184480_az && !this.field_184461_aB && !this.field_184463_aC) {
            f += 0.005F;
         }

         this.field_70177_z += this.field_184475_as;
         if (this.field_184461_aB) {
            f += 0.04F;
         }

         if (this.field_184463_aC) {
            f -= 0.005F;
         }

         this.field_70159_w += (double)(MathHelper.func_76126_a(-this.field_70177_z * 0.017453292F) * f);
         this.field_70179_y += (double)(MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * f);
         this.func_184445_a(this.field_184459_aA && !this.field_184480_az || this.field_184461_aB, this.field_184480_az && !this.field_184459_aA || this.field_184461_aB);
      }
   }

   public void func_184232_k(Entity p_184232_1_) {
      if (this.func_184196_w(p_184232_1_)) {
         float f = 0.0F;
         float f1 = (float)((this.field_70128_L ? 0.009999999776482582D : this.func_70042_X()) + p_184232_1_.func_70033_W());
         if (this.func_184188_bt().size() > 1) {
            int i = this.func_184188_bt().indexOf(p_184232_1_);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (p_184232_1_ instanceof EntityAnimal) {
               f = (float)((double)f + 0.2D);
            }
         }

         Vec3d vec3d = (new Vec3d((double)f, 0.0D, 0.0D)).func_178785_b(-this.field_70177_z * 0.017453292F - 1.5707964F);
         p_184232_1_.func_70107_b(this.field_70165_t + vec3d.field_72450_a, this.field_70163_u + (double)f1, this.field_70161_v + vec3d.field_72449_c);
         p_184232_1_.field_70177_z += this.field_184475_as;
         p_184232_1_.func_70034_d(p_184232_1_.func_70079_am() + this.field_184475_as);
         this.func_184454_a(p_184232_1_);
         if (p_184232_1_ instanceof EntityAnimal && this.func_184188_bt().size() > 1) {
            int j = p_184232_1_.func_145782_y() % 2 == 0 ? 90 : 270;
            p_184232_1_.func_181013_g(((EntityAnimal)p_184232_1_).field_70761_aq + (float)j);
            p_184232_1_.func_70034_d(p_184232_1_.func_70079_am() + (float)j);
         }

      }
   }

   protected void func_184454_a(Entity p_184454_1_) {
      p_184454_1_.func_181013_g(this.field_70177_z);
      float f = MathHelper.func_76142_g(p_184454_1_.field_70177_z - this.field_70177_z);
      float f1 = MathHelper.func_76131_a(f, -105.0F, 105.0F);
      p_184454_1_.field_70126_B += f1 - f;
      p_184454_1_.field_70177_z += f1 - f;
      p_184454_1_.func_70034_d(p_184454_1_.field_70177_z);
   }

   public void func_184190_l(Entity p_184190_1_) {
      this.func_184454_a(p_184190_1_);
   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74778_a("Type", this.func_184453_r().func_184980_a());
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      if (p_70037_1_.func_150297_b("Type", 8)) {
         this.func_184458_a(EntityBoat.Type.func_184981_a(p_70037_1_.func_74779_i("Type")));
      }

   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      if (p_184230_1_.func_70093_af()) {
         return false;
      } else {
         if (!this.field_70170_p.field_72995_K && this.field_184474_h < 60.0F) {
            p_184230_1_.func_184220_m(this);
         }

         return true;
      }
   }

   protected void func_184231_a(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
      this.field_184473_aH = this.field_70181_x;
      if (!this.func_184218_aH()) {
         if (p_184231_3_) {
            if (this.field_70143_R > 3.0F) {
               if (this.field_184469_aF != EntityBoat.Status.ON_LAND) {
                  this.field_70143_R = 0.0F;
                  return;
               }

               this.func_180430_e(this.field_70143_R, 1.0F);
               if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
                  this.func_70106_y();
                  if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
                     for(int i = 0; i < 3; ++i) {
                        this.func_70099_a(new ItemStack(Item.func_150898_a(Blocks.field_150344_f), 1, this.func_184453_r().func_184982_b()), 0.0F);
                     }

                     for(int j = 0; j < 2; ++j) {
                        this.func_145778_a(Items.field_151055_y, 1, 0.0F);
                     }
                  }
               }
            }

            this.field_70143_R = 0.0F;
         } else if (this.field_70170_p.func_180495_p((new BlockPos(this)).func_177977_b()).func_185904_a() != Material.field_151586_h && p_184231_1_ < 0.0D) {
            this.field_70143_R = (float)((double)this.field_70143_R - p_184231_1_);
         }

      }
   }

   public boolean func_184457_a(int p_184457_1_) {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184468_e[p_184457_1_])).booleanValue() && this.func_184179_bs() != null;
   }

   public void func_70266_a(float p_70266_1_) {
      this.field_70180_af.func_187227_b(field_184464_c, Float.valueOf(p_70266_1_));
   }

   public float func_70271_g() {
      return ((Float)this.field_70180_af.func_187225_a(field_184464_c)).floatValue();
   }

   public void func_70265_b(int p_70265_1_) {
      this.field_70180_af.func_187227_b(field_184460_a, Integer.valueOf(p_70265_1_));
   }

   public int func_70268_h() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184460_a)).intValue();
   }

   public void func_70269_c(int p_70269_1_) {
      this.field_70180_af.func_187227_b(field_184462_b, Integer.valueOf(p_70269_1_));
   }

   public int func_70267_i() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184462_b)).intValue();
   }

   public void func_184458_a(EntityBoat.Type p_184458_1_) {
      this.field_70180_af.func_187227_b(field_184466_d, Integer.valueOf(p_184458_1_.ordinal()));
   }

   public EntityBoat.Type func_184453_r() {
      return EntityBoat.Type.func_184979_a(((Integer)this.field_70180_af.func_187225_a(field_184466_d)).intValue());
   }

   protected boolean func_184219_q(Entity p_184219_1_) {
      return this.func_184188_bt().size() < 2;
   }

   @Nullable
   public Entity func_184179_bs() {
      List<Entity> list = this.func_184188_bt();
      return list.isEmpty() ? null : (Entity)list.get(0);
   }

   public void func_184442_a(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_) {
      this.field_184480_az = p_184442_1_;
      this.field_184459_aA = p_184442_2_;
      this.field_184461_aB = p_184442_3_;
      this.field_184463_aC = p_184442_4_;
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }

   public static enum Type {
      OAK(BlockPlanks.EnumType.OAK.func_176839_a(), "oak"),
      SPRUCE(BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce"),
      BIRCH(BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch"),
      JUNGLE(BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle"),
      ACACIA(BlockPlanks.EnumType.ACACIA.func_176839_a(), "acacia"),
      DARK_OAK(BlockPlanks.EnumType.DARK_OAK.func_176839_a(), "dark_oak");

      private final String field_184990_g;
      private final int field_184991_h;

      private Type(int p_i47028_3_, String p_i47028_4_) {
         this.field_184990_g = p_i47028_4_;
         this.field_184991_h = p_i47028_3_;
      }

      public String func_184980_a() {
         return this.field_184990_g;
      }

      public int func_184982_b() {
         return this.field_184991_h;
      }

      public String toString() {
         return this.field_184990_g;
      }

      public static EntityBoat.Type func_184979_a(int p_184979_0_) {
         if (p_184979_0_ < 0 || p_184979_0_ >= values().length) {
            p_184979_0_ = 0;
         }

         return values()[p_184979_0_];
      }

      public static EntityBoat.Type func_184981_a(String p_184981_0_) {
         for(int i = 0; i < values().length; ++i) {
            if (values()[i].func_184980_a().equals(p_184981_0_)) {
               return values()[i];
            }
         }

         return values()[0];
      }
   }
}
