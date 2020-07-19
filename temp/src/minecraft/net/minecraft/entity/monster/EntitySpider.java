package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySpider extends EntityMob {
   private static final DataParameter<Byte> field_184729_a = EntityDataManager.<Byte>func_187226_a(EntitySpider.class, DataSerializers.field_187191_a);

   public EntitySpider(World p_i1743_1_) {
      super(p_i1743_1_);
      this.func_70105_a(1.4F, 0.9F);
   }

   public static void func_189774_d(DataFixer p_189774_0_) {
      EntityLiving.func_189752_a(p_189774_0_, EntitySpider.class);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(3, new EntityAILeapAtTarget(this, 0.4F));
      this.field_70714_bg.func_75776_a(4, new EntitySpider.AISpiderAttack(this));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 0.8D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntitySpider.AISpiderTarget(this, EntityPlayer.class));
      this.field_70715_bh.func_75776_a(3, new EntitySpider.AISpiderTarget(this, EntityIronGolem.class));
   }

   public double func_70042_X() {
      return (double)(this.field_70131_O * 0.5F);
   }

   protected PathNavigate func_175447_b(World p_175447_1_) {
      return new PathNavigateClimber(this, p_175447_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184729_a, Byte.valueOf((byte)0));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         this.func_70839_e(this.field_70123_F);
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187817_fK;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187821_fM;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187819_fL;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186435_q;
   }

   public boolean func_70617_f_() {
      return this.func_70841_p();
   }

   public void func_70110_aj() {
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public boolean func_70687_e(PotionEffect p_70687_1_) {
      return p_70687_1_.func_188419_a() == MobEffects.field_76436_u ? false : super.func_70687_e(p_70687_1_);
   }

   public boolean func_70841_p() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184729_a)).byteValue() & 1) != 0;
   }

   public void func_70839_e(boolean p_70839_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184729_a)).byteValue();
      if (p_70839_1_) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.field_70180_af.func_187227_b(field_184729_a, Byte.valueOf(b0));
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      if (this.field_70170_p.field_73012_v.nextInt(100) == 0) {
         EntitySkeleton entityskeleton = new EntitySkeleton(this.field_70170_p);
         entityskeleton.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         entityskeleton.func_180482_a(p_180482_1_, (IEntityLivingData)null);
         this.field_70170_p.func_72838_d(entityskeleton);
         entityskeleton.func_184220_m(this);
      }

      if (p_180482_2_ == null) {
         p_180482_2_ = new EntitySpider.GroupData();
         if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70170_p.field_73012_v.nextFloat() < 0.1F * p_180482_1_.func_180170_c()) {
            ((EntitySpider.GroupData)p_180482_2_).func_111104_a(this.field_70170_p.field_73012_v);
         }
      }

      if (p_180482_2_ instanceof EntitySpider.GroupData) {
         Potion potion = ((EntitySpider.GroupData)p_180482_2_).field_188478_a;
         if (potion != null) {
            this.func_70690_d(new PotionEffect(potion, Integer.MAX_VALUE));
         }
      }

      return p_180482_2_;
   }

   public float func_70047_e() {
      return 0.65F;
   }

   static class AISpiderAttack extends EntityAIAttackMelee {
      public AISpiderAttack(EntitySpider p_i46676_1_) {
         super(p_i46676_1_, 1.0D, true);
      }

      public boolean func_75253_b() {
         float f = this.field_75441_b.func_70013_c();
         if (f >= 0.5F && this.field_75441_b.func_70681_au().nextInt(100) == 0) {
            this.field_75441_b.func_70624_b((EntityLivingBase)null);
            return false;
         } else {
            return super.func_75253_b();
         }
      }

      protected double func_179512_a(EntityLivingBase p_179512_1_) {
         return (double)(4.0F + p_179512_1_.field_70130_N);
      }
   }

   static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
      public AISpiderTarget(EntitySpider p_i45818_1_, Class<T> p_i45818_2_) {
         super(p_i45818_1_, p_i45818_2_, true);
      }

      public boolean func_75250_a() {
         float f = this.field_75299_d.func_70013_c();
         return f >= 0.5F ? false : super.func_75250_a();
      }
   }

   public static class GroupData implements IEntityLivingData {
      public Potion field_188478_a;

      public void func_111104_a(Random p_111104_1_) {
         int i = p_111104_1_.nextInt(5);
         if (i <= 1) {
            this.field_188478_a = MobEffects.field_76424_c;
         } else if (i <= 2) {
            this.field_188478_a = MobEffects.field_76420_g;
         } else if (i <= 3) {
            this.field_188478_a = MobEffects.field_76428_l;
         } else if (i <= 4) {
            this.field_188478_a = MobEffects.field_76441_p;
         }

      }
   }
}
