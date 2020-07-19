package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityWitherSkeleton extends AbstractSkeleton {
   public EntityWitherSkeleton(World p_i47278_1_) {
      super(p_i47278_1_);
      this.func_70105_a(0.7F, 2.4F);
      this.field_70178_ae = true;
   }

   public static void func_190729_b(DataFixer p_190729_0_) {
      EntityLiving.func_189752_a(p_190729_0_, EntityWitherSkeleton.class);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186386_ak;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_190036_ha;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_190038_hc;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_190037_hb;
   }

   SoundEvent func_190727_o() {
      return SoundEvents.field_190039_hd;
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      if (p_70645_1_.func_76346_g() instanceof EntityCreeper) {
         EntityCreeper entitycreeper = (EntityCreeper)p_70645_1_.func_76346_g();
         if (entitycreeper.func_70830_n() && entitycreeper.func_70650_aV()) {
            entitycreeper.func_175493_co();
            this.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 1), 0.0F);
         }
      }

   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151052_q));
   }

   protected void func_180483_b(DifficultyInstance p_180483_1_) {
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      IEntityLivingData ientitylivingdata = super.func_180482_a(p_180482_1_, p_180482_2_);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
      this.func_85036_m();
      return ientitylivingdata;
   }

   public float func_70047_e() {
      return 2.1F;
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      if (!super.func_70652_k(p_70652_1_)) {
         return false;
      } else {
         if (p_70652_1_ instanceof EntityLivingBase) {
            ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 200));
         }

         return true;
      }
   }

   protected EntityArrow func_190726_a(float p_190726_1_) {
      EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
      entityarrow.func_70015_d(100);
      return entityarrow;
   }
}
