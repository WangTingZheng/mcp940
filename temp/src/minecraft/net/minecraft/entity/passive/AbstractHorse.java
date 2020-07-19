package net.minecraft.entity.passive;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class AbstractHorse extends EntityAnimal implements IInventoryChangedListener, IJumpingMount {
   private static final Predicate<Entity> field_110276_bu = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof AbstractHorse && ((AbstractHorse)p_apply_1_).func_110205_ce();
      }
   };
   protected static final IAttribute field_110271_bv = (new RangedAttribute((IAttribute)null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).func_111117_a("Jump Strength").func_111112_a(true);
   private static final DataParameter<Byte> field_184787_bE = EntityDataManager.<Byte>func_187226_a(AbstractHorse.class, DataSerializers.field_187191_a);
   private static final DataParameter<Optional<UUID>> field_184790_bH = EntityDataManager.<Optional<UUID>>func_187226_a(AbstractHorse.class, DataSerializers.field_187203_m);
   private int field_190689_bJ;
   private int field_110290_bE;
   private int field_110295_bF;
   public int field_110278_bp;
   public int field_110279_bq;
   protected boolean field_110275_br;
   protected ContainerHorseChest field_110296_bG;
   protected int field_110274_bs;
   protected float field_110277_bt;
   private boolean field_110294_bI;
   private float field_110283_bJ;
   private float field_110284_bK;
   private float field_110281_bL;
   private float field_110282_bM;
   private float field_110287_bN;
   private float field_110288_bO;
   protected boolean field_190688_bE = true;
   protected int field_110285_bP;

   public AbstractHorse(World p_i47299_1_) {
      super(p_i47299_1_);
      this.func_70105_a(1.3964844F, 1.6F);
      this.field_70138_W = 1.0F;
      this.func_110226_cD();
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.2D));
      this.field_70714_bg.func_75776_a(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D, AbstractHorse.class));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 0.7D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184787_bE, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_184790_bH, Optional.absent());
   }

   protected boolean func_110233_w(int p_110233_1_) {
      return (((Byte)this.field_70180_af.func_187225_a(field_184787_bE)).byteValue() & p_110233_1_) != 0;
   }

   protected void func_110208_b(int p_110208_1_, boolean p_110208_2_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184787_bE)).byteValue();
      if (p_110208_2_) {
         this.field_70180_af.func_187227_b(field_184787_bE, Byte.valueOf((byte)(b0 | p_110208_1_)));
      } else {
         this.field_70180_af.func_187227_b(field_184787_bE, Byte.valueOf((byte)(b0 & ~p_110208_1_)));
      }

   }

   public boolean func_110248_bS() {
      return this.func_110233_w(2);
   }

   @Nullable
   public UUID func_184780_dh() {
      return (UUID)((Optional)this.field_70180_af.func_187225_a(field_184790_bH)).orNull();
   }

   public void func_184779_b(@Nullable UUID p_184779_1_) {
      this.field_70180_af.func_187227_b(field_184790_bH, Optional.fromNullable(p_184779_1_));
   }

   public float func_110254_bY() {
      return 0.5F;
   }

   public void func_98054_a(boolean p_98054_1_) {
      this.func_98055_j(p_98054_1_ ? this.func_110254_bY() : 1.0F);
   }

   public boolean func_110246_bZ() {
      return this.field_110275_br;
   }

   public void func_110234_j(boolean p_110234_1_) {
      this.func_110208_b(2, p_110234_1_);
   }

   public void func_110255_k(boolean p_110255_1_) {
      this.field_110275_br = p_110255_1_;
   }

   public boolean func_184652_a(EntityPlayer p_184652_1_) {
      return super.func_184652_a(p_184652_1_) && this.func_70668_bt() != EnumCreatureAttribute.UNDEAD;
   }

   protected void func_142017_o(float p_142017_1_) {
      if (p_142017_1_ > 6.0F && this.func_110204_cc()) {
         this.func_110227_p(false);
      }

   }

   public boolean func_110204_cc() {
      return this.func_110233_w(16);
   }

   public boolean func_110209_cd() {
      return this.func_110233_w(32);
   }

   public boolean func_110205_ce() {
      return this.func_110233_w(8);
   }

   public void func_110242_l(boolean p_110242_1_) {
      this.func_110208_b(8, p_110242_1_);
   }

   public void func_110251_o(boolean p_110251_1_) {
      this.func_110208_b(4, p_110251_1_);
   }

   public int func_110252_cg() {
      return this.field_110274_bs;
   }

   public void func_110238_s(int p_110238_1_) {
      this.field_110274_bs = p_110238_1_;
   }

   public int func_110198_t(int p_110198_1_) {
      int i = MathHelper.func_76125_a(this.func_110252_cg() + p_110198_1_, 0, this.func_190676_dC());
      this.func_110238_s(i);
      return i;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      Entity entity = p_70097_1_.func_76346_g();
      return this.func_184207_aI() && entity != null && this.func_184215_y(entity) ? false : super.func_70097_a(p_70097_1_, p_70097_2_);
   }

   public boolean func_70104_M() {
      return !this.func_184207_aI();
   }

   private void func_110266_cB() {
      this.func_110249_cI();
      if (!this.func_174814_R()) {
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187711_cp, this.func_184176_by(), 1.0F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
      }

   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      if (p_180430_1_ > 1.0F) {
         this.func_184185_a(SoundEvents.field_187723_ct, 0.4F, 1.0F);
      }

      int i = MathHelper.func_76123_f((p_180430_1_ * 0.5F - 3.0F) * p_180430_2_);
      if (i > 0) {
         this.func_70097_a(DamageSource.field_76379_h, (float)i);
         if (this.func_184207_aI()) {
            for(Entity entity : this.func_184182_bu()) {
               entity.func_70097_a(DamageSource.field_76379_h, (float)i);
            }
         }

         IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.2D - (double)this.field_70126_B, this.field_70161_v));
         Block block = iblockstate.func_177230_c();
         if (iblockstate.func_185904_a() != Material.field_151579_a && !this.func_174814_R()) {
            SoundType soundtype = block.func_185467_w();
            this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, soundtype.func_185844_d(), this.func_184176_by(), soundtype.func_185843_a() * 0.5F, soundtype.func_185847_b() * 0.75F);
         }

      }
   }

   protected int func_190686_di() {
      return 2;
   }

   protected void func_110226_cD() {
      ContainerHorseChest containerhorsechest = this.field_110296_bG;
      this.field_110296_bG = new ContainerHorseChest("HorseChest", this.func_190686_di());
      this.field_110296_bG.func_110133_a(this.func_70005_c_());
      if (containerhorsechest != null) {
         containerhorsechest.func_110132_b(this);
         int i = Math.min(containerhorsechest.func_70302_i_(), this.field_110296_bG.func_70302_i_());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = containerhorsechest.func_70301_a(j);
            if (!itemstack.func_190926_b()) {
               this.field_110296_bG.func_70299_a(j, itemstack.func_77946_l());
            }
         }
      }

      this.field_110296_bG.func_110134_a(this);
      this.func_110232_cE();
   }

   protected void func_110232_cE() {
      if (!this.field_70170_p.field_72995_K) {
         this.func_110251_o(!this.field_110296_bG.func_70301_a(0).func_190926_b() && this.func_190685_dA());
      }
   }

   public void func_76316_a(IInventory p_76316_1_) {
      boolean flag = this.func_110257_ck();
      this.func_110232_cE();
      if (this.field_70173_aa > 20 && !flag && this.func_110257_ck()) {
         this.func_184185_a(SoundEvents.field_187726_cu, 0.5F, 1.0F);
      }

   }

   @Nullable
   protected AbstractHorse func_110250_a(Entity p_110250_1_, double p_110250_2_) {
      double d0 = Double.MAX_VALUE;
      Entity entity = null;

      for(Entity entity1 : this.field_70170_p.func_175674_a(p_110250_1_, p_110250_1_.func_174813_aQ().func_72321_a(p_110250_2_, p_110250_2_, p_110250_2_), field_110276_bu)) {
         double d1 = entity1.func_70092_e(p_110250_1_.field_70165_t, p_110250_1_.field_70163_u, p_110250_1_.field_70161_v);
         if (d1 < d0) {
            entity = entity1;
            d0 = d1;
         }
      }

      return (AbstractHorse)entity;
   }

   public double func_110215_cj() {
      return this.func_110148_a(field_110271_bv).func_111126_e();
   }

   @Nullable
   protected SoundEvent func_184615_bR() {
      this.func_110249_cI();
      return null;
   }

   @Nullable
   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      this.func_110249_cI();
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_110220_cK();
      }

      return null;
   }

   @Nullable
   protected SoundEvent func_184639_G() {
      this.func_110249_cI();
      if (this.field_70146_Z.nextInt(10) == 0 && !this.func_70610_aX()) {
         this.func_110220_cK();
      }

      return null;
   }

   public boolean func_190685_dA() {
      return true;
   }

   public boolean func_110257_ck() {
      return this.func_110233_w(4);
   }

   @Nullable
   protected SoundEvent func_184785_dv() {
      this.func_110249_cI();
      this.func_110220_cK();
      return null;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      if (!p_180429_2_.func_176223_P().func_185904_a().func_76224_d()) {
         SoundType soundtype = p_180429_2_.func_185467_w();
         if (this.field_70170_p.func_180495_p(p_180429_1_.func_177984_a()).func_177230_c() == Blocks.field_150431_aC) {
            soundtype = Blocks.field_150431_aC.func_185467_w();
         }

         if (this.func_184207_aI() && this.field_190688_bE) {
            ++this.field_110285_bP;
            if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0) {
               this.func_190680_a(soundtype);
            } else if (this.field_110285_bP <= 5) {
               this.func_184185_a(SoundEvents.field_187732_cw, soundtype.func_185843_a() * 0.15F, soundtype.func_185847_b());
            }
         } else if (soundtype == SoundType.field_185848_a) {
            this.func_184185_a(SoundEvents.field_187732_cw, soundtype.func_185843_a() * 0.15F, soundtype.func_185847_b());
         } else {
            this.func_184185_a(SoundEvents.field_187729_cv, soundtype.func_185843_a() * 0.15F, soundtype.func_185847_b());
         }

      }
   }

   protected void func_190680_a(SoundType p_190680_1_) {
      this.func_184185_a(SoundEvents.field_187714_cq, p_190680_1_.func_185843_a() * 0.15F, p_190680_1_.func_185847_b());
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(field_110271_bv);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(53.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.22499999403953552D);
   }

   public int func_70641_bl() {
      return 6;
   }

   public int func_190676_dC() {
      return 100;
   }

   protected float func_70599_aP() {
      return 0.8F;
   }

   public int func_70627_aG() {
      return 400;
   }

   public void func_110199_f(EntityPlayer p_110199_1_) {
      if (!this.field_70170_p.field_72995_K && (!this.func_184207_aI() || this.func_184196_w(p_110199_1_)) && this.func_110248_bS()) {
         this.field_110296_bG.func_110133_a(this.func_70005_c_());
         p_110199_1_.func_184826_a(this, this.field_110296_bG);
      }

   }

   protected boolean func_190678_b(EntityPlayer p_190678_1_, ItemStack p_190678_2_) {
      boolean flag = false;
      float f = 0.0F;
      int i = 0;
      int j = 0;
      Item item = p_190678_2_.func_77973_b();
      if (item == Items.field_151015_O) {
         f = 2.0F;
         i = 20;
         j = 3;
      } else if (item == Items.field_151102_aT) {
         f = 1.0F;
         i = 30;
         j = 3;
      } else if (item == Item.func_150898_a(Blocks.field_150407_cf)) {
         f = 20.0F;
         i = 180;
      } else if (item == Items.field_151034_e) {
         f = 3.0F;
         i = 60;
         j = 3;
      } else if (item == Items.field_151150_bK) {
         f = 4.0F;
         i = 60;
         j = 5;
         if (this.func_110248_bS() && this.func_70874_b() == 0 && !this.func_70880_s()) {
            flag = true;
            this.func_146082_f(p_190678_1_);
         }
      } else if (item == Items.field_151153_ao) {
         f = 10.0F;
         i = 240;
         j = 10;
         if (this.func_110248_bS() && this.func_70874_b() == 0 && !this.func_70880_s()) {
            flag = true;
            this.func_146082_f(p_190678_1_);
         }
      }

      if (this.func_110143_aJ() < this.func_110138_aP() && f > 0.0F) {
         this.func_70691_i(f);
         flag = true;
      }

      if (this.func_70631_g_() && i > 0) {
         this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_HAPPY, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         if (!this.field_70170_p.field_72995_K) {
            this.func_110195_a(i);
         }

         flag = true;
      }

      if (j > 0 && (flag || !this.func_110248_bS()) && this.func_110252_cg() < this.func_190676_dC()) {
         flag = true;
         if (!this.field_70170_p.field_72995_K) {
            this.func_110198_t(j);
         }
      }

      if (flag) {
         this.func_110266_cB();
      }

      return flag;
   }

   protected void func_110237_h(EntityPlayer p_110237_1_) {
      p_110237_1_.field_70177_z = this.field_70177_z;
      p_110237_1_.field_70125_A = this.field_70125_A;
      this.func_110227_p(false);
      this.func_110219_q(false);
      if (!this.field_70170_p.field_72995_K) {
         p_110237_1_.func_184220_m(this);
      }

   }

   protected boolean func_70610_aX() {
      return super.func_70610_aX() && this.func_184207_aI() && this.func_110257_ck() || this.func_110204_cc() || this.func_110209_cd();
   }

   public boolean func_70877_b(ItemStack p_70877_1_) {
      return false;
   }

   private void func_110210_cH() {
      this.field_110278_bp = 1;
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      if (!this.field_70170_p.field_72995_K && this.field_110296_bG != null) {
         for(int i = 0; i < this.field_110296_bG.func_70302_i_(); ++i) {
            ItemStack itemstack = this.field_110296_bG.func_70301_a(i);
            if (!itemstack.func_190926_b()) {
               this.func_70099_a(itemstack, 0.0F);
            }
         }

      }
   }

   public void func_70636_d() {
      if (this.field_70146_Z.nextInt(200) == 0) {
         this.func_110210_cH();
      }

      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70146_Z.nextInt(900) == 0 && this.field_70725_aQ == 0) {
            this.func_70691_i(1.0F);
         }

         if (this.func_190684_dE()) {
            if (!this.func_110204_cc() && !this.func_184207_aI() && this.field_70146_Z.nextInt(300) == 0 && this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c() == Blocks.field_150349_c) {
               this.func_110227_p(true);
            }

            if (this.func_110204_cc() && ++this.field_190689_bJ > 50) {
               this.field_190689_bJ = 0;
               this.func_110227_p(false);
            }
         }

         this.func_190679_dD();
      }
   }

   protected void func_190679_dD() {
      if (this.func_110205_ce() && this.func_70631_g_() && !this.func_110204_cc()) {
         AbstractHorse abstracthorse = this.func_110250_a(this, 16.0D);
         if (abstracthorse != null && this.func_70068_e(abstracthorse) > 4.0D) {
            this.field_70699_by.func_75494_a(abstracthorse);
         }
      }

   }

   public boolean func_190684_dE() {
      return true;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_110290_bE > 0 && ++this.field_110290_bE > 30) {
         this.field_110290_bE = 0;
         this.func_110208_b(64, false);
      }

      if (this.func_184186_bw() && this.field_110295_bF > 0 && ++this.field_110295_bF > 20) {
         this.field_110295_bF = 0;
         this.func_110219_q(false);
      }

      if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
         this.field_110278_bp = 0;
      }

      if (this.field_110279_bq > 0) {
         ++this.field_110279_bq;
         if (this.field_110279_bq > 300) {
            this.field_110279_bq = 0;
         }
      }

      this.field_110284_bK = this.field_110283_bJ;
      if (this.func_110204_cc()) {
         this.field_110283_bJ += (1.0F - this.field_110283_bJ) * 0.4F + 0.05F;
         if (this.field_110283_bJ > 1.0F) {
            this.field_110283_bJ = 1.0F;
         }
      } else {
         this.field_110283_bJ += (0.0F - this.field_110283_bJ) * 0.4F - 0.05F;
         if (this.field_110283_bJ < 0.0F) {
            this.field_110283_bJ = 0.0F;
         }
      }

      this.field_110282_bM = this.field_110281_bL;
      if (this.func_110209_cd()) {
         this.field_110283_bJ = 0.0F;
         this.field_110284_bK = this.field_110283_bJ;
         this.field_110281_bL += (1.0F - this.field_110281_bL) * 0.4F + 0.05F;
         if (this.field_110281_bL > 1.0F) {
            this.field_110281_bL = 1.0F;
         }
      } else {
         this.field_110294_bI = false;
         this.field_110281_bL += (0.8F * this.field_110281_bL * this.field_110281_bL * this.field_110281_bL - this.field_110281_bL) * 0.6F - 0.05F;
         if (this.field_110281_bL < 0.0F) {
            this.field_110281_bL = 0.0F;
         }
      }

      this.field_110288_bO = this.field_110287_bN;
      if (this.func_110233_w(64)) {
         this.field_110287_bN += (1.0F - this.field_110287_bN) * 0.7F + 0.05F;
         if (this.field_110287_bN > 1.0F) {
            this.field_110287_bN = 1.0F;
         }
      } else {
         this.field_110287_bN += (0.0F - this.field_110287_bN) * 0.7F - 0.05F;
         if (this.field_110287_bN < 0.0F) {
            this.field_110287_bN = 0.0F;
         }
      }

   }

   private void func_110249_cI() {
      if (!this.field_70170_p.field_72995_K) {
         this.field_110290_bE = 1;
         this.func_110208_b(64, true);
      }

   }

   public void func_110227_p(boolean p_110227_1_) {
      this.func_110208_b(16, p_110227_1_);
   }

   public void func_110219_q(boolean p_110219_1_) {
      if (p_110219_1_) {
         this.func_110227_p(false);
      }

      this.func_110208_b(32, p_110219_1_);
   }

   private void func_110220_cK() {
      if (this.func_184186_bw()) {
         this.field_110295_bF = 1;
         this.func_110219_q(true);
      }

   }

   public void func_190687_dF() {
      this.func_110220_cK();
      SoundEvent soundevent = this.func_184785_dv();
      if (soundevent != null) {
         this.func_184185_a(soundevent, this.func_70599_aP(), this.func_70647_i());
      }

   }

   public boolean func_110263_g(EntityPlayer p_110263_1_) {
      this.func_184779_b(p_110263_1_.func_110124_au());
      this.func_110234_j(true);
      if (p_110263_1_ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_193136_w.func_193178_a((EntityPlayerMP)p_110263_1_, this);
      }

      this.field_70170_p.func_72960_a(this, (byte)7);
      return true;
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      if (this.func_184207_aI() && this.func_82171_bF() && this.func_110257_ck()) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)this.func_184179_bs();
         this.field_70177_z = entitylivingbase.field_70177_z;
         this.field_70126_B = this.field_70177_z;
         this.field_70125_A = entitylivingbase.field_70125_A * 0.5F;
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         this.field_70761_aq = this.field_70177_z;
         this.field_70759_as = this.field_70761_aq;
         p_191986_1_ = entitylivingbase.field_70702_br * 0.5F;
         p_191986_3_ = entitylivingbase.field_191988_bg;
         if (p_191986_3_ <= 0.0F) {
            p_191986_3_ *= 0.25F;
            this.field_110285_bP = 0;
         }

         if (this.field_70122_E && this.field_110277_bt == 0.0F && this.func_110209_cd() && !this.field_110294_bI) {
            p_191986_1_ = 0.0F;
            p_191986_3_ = 0.0F;
         }

         if (this.field_110277_bt > 0.0F && !this.func_110246_bZ() && this.field_70122_E) {
            this.field_70181_x = this.func_110215_cj() * (double)this.field_110277_bt;
            if (this.func_70644_a(MobEffects.field_76430_j)) {
               this.field_70181_x += (double)((float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
            }

            this.func_110255_k(true);
            this.field_70160_al = true;
            if (p_191986_3_ > 0.0F) {
               float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
               float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
               this.field_70159_w += (double)(-0.4F * f * this.field_110277_bt);
               this.field_70179_y += (double)(0.4F * f1 * this.field_110277_bt);
               this.func_184185_a(SoundEvents.field_187720_cs, 0.4F, 1.0F);
            }

            this.field_110277_bt = 0.0F;
         }

         this.field_70747_aH = this.func_70689_ay() * 0.1F;
         if (this.func_184186_bw()) {
            this.func_70659_e((float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
         } else if (entitylivingbase instanceof EntityPlayer) {
            this.field_70159_w = 0.0D;
            this.field_70181_x = 0.0D;
            this.field_70179_y = 0.0D;
         }

         if (this.field_70122_E) {
            this.field_110277_bt = 0.0F;
            this.func_110255_k(false);
         }

         this.field_184618_aE = this.field_70721_aZ;
         double d1 = this.field_70165_t - this.field_70169_q;
         double d0 = this.field_70161_v - this.field_70166_s;
         float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
         if (f2 > 1.0F) {
            f2 = 1.0F;
         }

         this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.4F;
         this.field_184619_aG += this.field_70721_aZ;
      } else {
         this.field_70747_aH = 0.02F;
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
      }
   }

   public static void func_190683_c(DataFixer p_190683_0_, Class<?> p_190683_1_) {
      EntityLiving.func_189752_a(p_190683_0_, p_190683_1_);
      p_190683_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData(p_190683_1_, new String[]{"SaddleItem"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("EatingHaystack", this.func_110204_cc());
      p_70014_1_.func_74757_a("Bred", this.func_110205_ce());
      p_70014_1_.func_74768_a("Temper", this.func_110252_cg());
      p_70014_1_.func_74757_a("Tame", this.func_110248_bS());
      if (this.func_184780_dh() != null) {
         p_70014_1_.func_74778_a("OwnerUUID", this.func_184780_dh().toString());
      }

      if (!this.field_110296_bG.func_70301_a(0).func_190926_b()) {
         p_70014_1_.func_74782_a("SaddleItem", this.field_110296_bG.func_70301_a(0).func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_110227_p(p_70037_1_.func_74767_n("EatingHaystack"));
      this.func_110242_l(p_70037_1_.func_74767_n("Bred"));
      this.func_110238_s(p_70037_1_.func_74762_e("Temper"));
      this.func_110234_j(p_70037_1_.func_74767_n("Tame"));
      String s;
      if (p_70037_1_.func_150297_b("OwnerUUID", 8)) {
         s = p_70037_1_.func_74779_i("OwnerUUID");
      } else {
         String s1 = p_70037_1_.func_74779_i("Owner");
         s = PreYggdrasilConverter.func_187473_a(this.func_184102_h(), s1);
      }

      if (!s.isEmpty()) {
         this.func_184779_b(UUID.fromString(s));
      }

      IAttributeInstance iattributeinstance = this.func_110140_aT().func_111152_a("Speed");
      if (iattributeinstance != null) {
         this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(iattributeinstance.func_111125_b() * 0.25D);
      }

      if (p_70037_1_.func_150297_b("SaddleItem", 10)) {
         ItemStack itemstack = new ItemStack(p_70037_1_.func_74775_l("SaddleItem"));
         if (itemstack.func_77973_b() == Items.field_151141_av) {
            this.field_110296_bG.func_70299_a(0, itemstack);
         }
      }

      this.func_110232_cE();
   }

   public boolean func_70878_b(EntityAnimal p_70878_1_) {
      return false;
   }

   protected boolean func_110200_cJ() {
      return !this.func_184207_aI() && !this.func_184218_aH() && this.func_110248_bS() && !this.func_70631_g_() && this.func_110143_aJ() >= this.func_110138_aP() && this.func_70880_s();
   }

   @Nullable
   public EntityAgeable func_90011_a(EntityAgeable p_90011_1_) {
      return null;
   }

   protected void func_190681_a(EntityAgeable p_190681_1_, AbstractHorse p_190681_2_) {
      double d0 = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + p_190681_1_.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + (double)this.func_110267_cL();
      p_190681_2_.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(d0 / 3.0D);
      double d1 = this.func_110148_a(field_110271_bv).func_111125_b() + p_190681_1_.func_110148_a(field_110271_bv).func_111125_b() + this.func_110245_cM();
      p_190681_2_.func_110148_a(field_110271_bv).func_111128_a(d1 / 3.0D);
      double d2 = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + p_190681_1_.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + this.func_110203_cN();
      p_190681_2_.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(d2 / 3.0D);
   }

   public boolean func_82171_bF() {
      return this.func_184179_bs() instanceof EntityLivingBase;
   }

   public float func_110258_o(float p_110258_1_) {
      return this.field_110284_bK + (this.field_110283_bJ - this.field_110284_bK) * p_110258_1_;
   }

   public float func_110223_p(float p_110223_1_) {
      return this.field_110282_bM + (this.field_110281_bL - this.field_110282_bM) * p_110223_1_;
   }

   public float func_110201_q(float p_110201_1_) {
      return this.field_110288_bO + (this.field_110287_bN - this.field_110288_bO) * p_110201_1_;
   }

   public void func_110206_u(int p_110206_1_) {
      if (this.func_110257_ck()) {
         if (p_110206_1_ < 0) {
            p_110206_1_ = 0;
         } else {
            this.field_110294_bI = true;
            this.func_110220_cK();
         }

         if (p_110206_1_ >= 90) {
            this.field_110277_bt = 1.0F;
         } else {
            this.field_110277_bt = 0.4F + 0.4F * (float)p_110206_1_ / 90.0F;
         }

      }
   }

   public boolean func_184776_b() {
      return this.func_110257_ck();
   }

   public void func_184775_b(int p_184775_1_) {
      this.field_110294_bI = true;
      this.func_110220_cK();
   }

   public void func_184777_r_() {
   }

   protected void func_110216_r(boolean p_110216_1_) {
      EnumParticleTypes enumparticletypes = p_110216_1_ ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;

      for(int i = 0; i < 7; ++i) {
         double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
         double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
         double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
         this.field_70170_p.func_175688_a(enumparticletypes, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d0, d1, d2);
      }

   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 7) {
         this.func_110216_r(true);
      } else if (p_70103_1_ == 6) {
         this.func_110216_r(false);
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public void func_184232_k(Entity p_184232_1_) {
      super.func_184232_k(p_184232_1_);
      if (p_184232_1_ instanceof EntityLiving) {
         EntityLiving entityliving = (EntityLiving)p_184232_1_;
         this.field_70761_aq = entityliving.field_70761_aq;
      }

      if (this.field_110282_bM > 0.0F) {
         float f3 = MathHelper.func_76126_a(this.field_70761_aq * 0.017453292F);
         float f = MathHelper.func_76134_b(this.field_70761_aq * 0.017453292F);
         float f1 = 0.7F * this.field_110282_bM;
         float f2 = 0.15F * this.field_110282_bM;
         p_184232_1_.func_70107_b(this.field_70165_t + (double)(f1 * f3), this.field_70163_u + this.func_70042_X() + p_184232_1_.func_70033_W() + (double)f2, this.field_70161_v - (double)(f1 * f));
         if (p_184232_1_ instanceof EntityLivingBase) {
            ((EntityLivingBase)p_184232_1_).field_70761_aq = this.field_70761_aq;
         }
      }

   }

   protected float func_110267_cL() {
      return 15.0F + (float)this.field_70146_Z.nextInt(8) + (float)this.field_70146_Z.nextInt(9);
   }

   protected double func_110245_cM() {
      return 0.4000000059604645D + this.field_70146_Z.nextDouble() * 0.2D + this.field_70146_Z.nextDouble() * 0.2D + this.field_70146_Z.nextDouble() * 0.2D;
   }

   protected double func_110203_cN() {
      return (0.44999998807907104D + this.field_70146_Z.nextDouble() * 0.3D + this.field_70146_Z.nextDouble() * 0.3D + this.field_70146_Z.nextDouble() * 0.3D) * 0.25D;
   }

   public boolean func_70617_f_() {
      return false;
   }

   public float func_70047_e() {
      return this.field_70131_O;
   }

   public boolean func_190677_dK() {
      return false;
   }

   public boolean func_190682_f(ItemStack p_190682_1_) {
      return false;
   }

   public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
      int i = p_174820_1_ - 400;
      if (i >= 0 && i < 2 && i < this.field_110296_bG.func_70302_i_()) {
         if (i == 0 && p_174820_2_.func_77973_b() != Items.field_151141_av) {
            return false;
         } else if (i != 1 || this.func_190677_dK() && this.func_190682_f(p_174820_2_)) {
            this.field_110296_bG.func_70299_a(i, p_174820_2_);
            this.func_110232_cE();
            return true;
         } else {
            return false;
         }
      } else {
         int j = p_174820_1_ - 500 + 2;
         if (j >= 2 && j < this.field_110296_bG.func_70302_i_()) {
            this.field_110296_bG.func_70299_a(j, p_174820_2_);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   public Entity func_184179_bs() {
      return this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70873_a(-24000);
      }

      return p_180482_2_;
   }
}
