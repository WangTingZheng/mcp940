package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityHusk extends EntityZombie {
   public EntityHusk(World p_i47286_1_) {
      super(p_i47286_1_);
   }

   public static void func_190740_b(DataFixer p_190740_0_) {
      EntityLiving.func_189752_a(p_190740_0_, EntityHusk.class);
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() && this.field_70170_p.func_175678_i(new BlockPos(this));
   }

   protected boolean func_190730_o() {
      return false;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_190022_cI;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_190024_cK;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_190023_cJ;
   }

   protected SoundEvent func_190731_di() {
      return SoundEvents.field_190025_cL;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191182_ar;
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      boolean flag = super.func_70652_k(p_70652_1_);
      if (flag && this.func_184614_ca().func_190926_b() && p_70652_1_ instanceof EntityLivingBase) {
         float f = this.field_70170_p.func_175649_E(new BlockPos(this)).func_180168_b();
         ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76438_s, 140 * (int)f));
      }

      return flag;
   }

   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }
}
