package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World p_i1737_1_) {
      super(p_i1737_1_);
      this.field_70178_ae = true;
   }

   public static void func_189759_b(DataFixer p_189759_0_) {
      EntityLiving.func_189752_a(p_189759_0_, EntityMagmaCube.class);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
   }

   public boolean func_70601_bi() {
      return this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   public boolean func_70058_J() {
      return this.field_70170_p.func_72917_a(this.func_174813_aQ(), this) && this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(this.func_174813_aQ());
   }

   protected void func_70799_a(int p_70799_1_, boolean p_70799_2_) {
      super.func_70799_a(p_70799_1_, p_70799_2_);
      this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a((double)(p_70799_1_ * 3));
   }

   public int func_70070_b() {
      return 15728880;
   }

   public float func_70013_c() {
      return 1.0F;
   }

   protected EnumParticleTypes func_180487_n() {
      return EnumParticleTypes.FLAME;
   }

   protected EntitySlime func_70802_j() {
      return new EntityMagmaCube(this.field_70170_p);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return this.func_189101_db() ? LootTableList.field_186419_a : LootTableList.field_186379_ad;
   }

   public boolean func_70027_ad() {
      return false;
   }

   protected int func_70806_k() {
      return super.func_70806_k() * 4;
   }

   protected void func_70808_l() {
      this.field_70813_a *= 0.9F;
   }

   protected void func_70664_aZ() {
      this.field_70181_x = (double)(0.42F + (float)this.func_70809_q() * 0.1F);
      this.field_70160_al = true;
   }

   protected void func_180466_bG() {
      this.field_70181_x = (double)(0.22F + (float)this.func_70809_q() * 0.05F);
      this.field_70160_al = true;
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
   }

   protected boolean func_70800_m() {
      return true;
   }

   protected int func_70805_n() {
      return super.func_70805_n() + 2;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return this.func_189101_db() ? SoundEvents.field_187892_fv : SoundEvents.field_187760_dh;
   }

   protected SoundEvent func_184615_bR() {
      return this.func_189101_db() ? SoundEvents.field_187890_fu : SoundEvents.field_187758_dg;
   }

   protected SoundEvent func_184709_cY() {
      return this.func_189101_db() ? SoundEvents.field_187894_fw : SoundEvents.field_187764_dj;
   }

   protected SoundEvent func_184710_cZ() {
      return SoundEvents.field_187762_di;
   }
}
