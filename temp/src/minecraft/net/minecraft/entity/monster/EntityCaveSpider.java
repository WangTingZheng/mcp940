package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityCaveSpider extends EntitySpider {
   public EntityCaveSpider(World p_i1732_1_) {
      super(p_i1732_1_);
      this.func_70105_a(0.7F, 0.5F);
   }

   public static void func_189775_b(DataFixer p_189775_0_) {
      EntityLiving.func_189752_a(p_189775_0_, EntityCaveSpider.class);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      if (super.func_70652_k(p_70652_1_)) {
         if (p_70652_1_ instanceof EntityLivingBase) {
            int i = 0;
            if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
               i = 7;
            } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
               i = 15;
            }

            if (i > 0) {
               ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76436_u, i * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      return p_180482_2_;
   }

   public float func_70047_e() {
      return 0.45F;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186436_r;
   }
}
