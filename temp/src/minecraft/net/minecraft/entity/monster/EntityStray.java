package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityStray extends AbstractSkeleton {
   public EntityStray(World p_i47281_1_) {
      super(p_i47281_1_);
   }

   public static void func_190728_b(DataFixer p_190728_0_) {
      EntityLiving.func_189752_a(p_190728_0_, EntityStray.class);
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() && this.field_70170_p.func_175678_i(new BlockPos(this));
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_189968_an;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_190032_gu;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_190034_gw;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_190033_gv;
   }

   SoundEvent func_190727_o() {
      return SoundEvents.field_190035_gx;
   }

   protected EntityArrow func_190726_a(float p_190726_1_) {
      EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
      if (entityarrow instanceof EntityTippedArrow) {
         ((EntityTippedArrow)entityarrow).func_184558_a(new PotionEffect(MobEffects.field_76421_d, 600));
      }

      return entityarrow;
   }
}
