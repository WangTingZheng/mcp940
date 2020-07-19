package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPolarBear extends EntityAnimal {
   private static final DataParameter<Boolean> field_189798_bx = EntityDataManager.<Boolean>func_187226_a(EntityPolarBear.class, DataSerializers.field_187198_h);
   private float field_189799_by;
   private float field_189800_bz;
   private int field_189797_bB;

   public EntityPolarBear(World p_i47154_1_) {
      super(p_i47154_1_);
      this.func_70105_a(1.3F, 1.4F);
   }

   public EntityAgeable func_90011_a(EntityAgeable p_90011_1_) {
      return new EntityPolarBear(this.field_70170_p);
   }

   public boolean func_70877_b(ItemStack p_70877_1_) {
      return false;
   }

   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityPolarBear.AIMeleeAttack());
      this.field_70714_bg.func_75776_a(1, new EntityPolarBear.AIPanic());
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.25D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityPolarBear.AIHurtByTarget());
      this.field_70715_bh.func_75776_a(2, new EntityPolarBear.AIAttackPlayer());
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
   }

   protected SoundEvent func_184639_G() {
      return this.func_70631_g_() ? SoundEvents.field_190027_es : SoundEvents.field_190026_er;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_190029_eu;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_190028_et;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_190030_ev, 0.15F, 1.0F);
   }

   protected void func_189796_de() {
      if (this.field_189797_bB <= 0) {
         this.func_184185_a(SoundEvents.field_190031_ew, 1.0F, 1.0F);
         this.field_189797_bB = 40;
      }

   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_189969_E;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_189798_bx, Boolean.valueOf(false));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         this.field_189799_by = this.field_189800_bz;
         if (this.func_189793_df()) {
            this.field_189800_bz = MathHelper.func_76131_a(this.field_189800_bz + 1.0F, 0.0F, 6.0F);
         } else {
            this.field_189800_bz = MathHelper.func_76131_a(this.field_189800_bz - 1.0F, 0.0F, 6.0F);
         }
      }

      if (this.field_189797_bB > 0) {
         --this.field_189797_bB;
      }

   }

   public boolean func_70652_k(Entity p_70652_1_) {
      boolean flag = p_70652_1_.func_70097_a(DamageSource.func_76358_a(this), (float)((int)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e()));
      if (flag) {
         this.func_174815_a(this, p_70652_1_);
      }

      return flag;
   }

   public boolean func_189793_df() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_189798_bx)).booleanValue();
   }

   public void func_189794_p(boolean p_189794_1_) {
      this.field_70180_af.func_187227_b(field_189798_bx, Boolean.valueOf(p_189794_1_));
   }

   public float func_189795_r(float p_189795_1_) {
      return (this.field_189799_by + (this.field_189800_bz - this.field_189799_by) * p_189795_1_) / 6.0F;
   }

   protected float func_189749_co() {
      return 0.98F;
   }

   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
      if (p_180482_2_ instanceof EntityPolarBear.GroupData) {
         if (((EntityPolarBear.GroupData)p_180482_2_).field_190101_a) {
            this.func_70873_a(-24000);
         }
      } else {
         EntityPolarBear.GroupData entitypolarbear$groupdata = new EntityPolarBear.GroupData();
         entitypolarbear$groupdata.field_190101_a = true;
         p_180482_2_ = entitypolarbear$groupdata;
      }

      return p_180482_2_;
   }

   class AIAttackPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
      public AIAttackPlayer() {
         super(EntityPolarBear.this, EntityPlayer.class, 20, true, true, (Predicate)null);
      }

      public boolean func_75250_a() {
         if (EntityPolarBear.this.func_70631_g_()) {
            return false;
         } else {
            if (super.func_75250_a()) {
               for(EntityPolarBear entitypolarbear : EntityPolarBear.this.field_70170_p.func_72872_a(EntityPolarBear.class, EntityPolarBear.this.func_174813_aQ().func_72314_b(8.0D, 4.0D, 8.0D))) {
                  if (entitypolarbear.func_70631_g_()) {
                     return true;
                  }
               }
            }

            EntityPolarBear.this.func_70624_b((EntityLivingBase)null);
            return false;
         }
      }

      protected double func_111175_f() {
         return super.func_111175_f() * 0.5D;
      }
   }

   class AIHurtByTarget extends EntityAIHurtByTarget {
      public AIHurtByTarget() {
         super(EntityPolarBear.this, false);
      }

      public void func_75249_e() {
         super.func_75249_e();
         if (EntityPolarBear.this.func_70631_g_()) {
            this.func_190105_f();
            this.func_75251_c();
         }

      }

      protected void func_179446_a(EntityCreature p_179446_1_, EntityLivingBase p_179446_2_) {
         if (p_179446_1_ instanceof EntityPolarBear && !p_179446_1_.func_70631_g_()) {
            super.func_179446_a(p_179446_1_, p_179446_2_);
         }

      }
   }

   class AIMeleeAttack extends EntityAIAttackMelee {
      public AIMeleeAttack() {
         super(EntityPolarBear.this, 1.25D, true);
      }

      protected void func_190102_a(EntityLivingBase p_190102_1_, double p_190102_2_) {
         double d0 = this.func_179512_a(p_190102_1_);
         if (p_190102_2_ <= d0 && this.field_75439_d <= 0) {
            this.field_75439_d = 20;
            this.field_75441_b.func_70652_k(p_190102_1_);
            EntityPolarBear.this.func_189794_p(false);
         } else if (p_190102_2_ <= d0 * 2.0D) {
            if (this.field_75439_d <= 0) {
               EntityPolarBear.this.func_189794_p(false);
               this.field_75439_d = 20;
            }

            if (this.field_75439_d <= 10) {
               EntityPolarBear.this.func_189794_p(true);
               EntityPolarBear.this.func_189796_de();
            }
         } else {
            this.field_75439_d = 20;
            EntityPolarBear.this.func_189794_p(false);
         }

      }

      public void func_75251_c() {
         EntityPolarBear.this.func_189794_p(false);
         super.func_75251_c();
      }

      protected double func_179512_a(EntityLivingBase p_179512_1_) {
         return (double)(4.0F + p_179512_1_.field_70130_N);
      }
   }

   class AIPanic extends EntityAIPanic {
      public AIPanic() {
         super(EntityPolarBear.this, 2.0D);
      }

      public boolean func_75250_a() {
         return !EntityPolarBear.this.func_70631_g_() && !EntityPolarBear.this.func_70027_ad() ? false : super.func_75250_a();
      }
   }

   static class GroupData implements IEntityLivingData {
      public boolean field_190101_a;

      private GroupData() {
      }
   }
}
