package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;

public final class EntitySelectors {
   public static final Predicate<Entity> field_94557_a = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_.func_70089_S();
      }
   };
   public static final Predicate<Entity> field_152785_b = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_.func_70089_S() && !p_apply_1_.func_184207_aI() && !p_apply_1_.func_184218_aH();
      }
   };
   public static final Predicate<Entity> field_96566_b = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof IInventory && p_apply_1_.func_70089_S();
      }
   };
   public static final Predicate<Entity> field_188444_d = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return !(p_apply_1_ instanceof EntityPlayer) || !((EntityPlayer)p_apply_1_).func_175149_v() && !((EntityPlayer)p_apply_1_).func_184812_l_();
      }
   };
   public static final Predicate<Entity> field_180132_d = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return !(p_apply_1_ instanceof EntityPlayer) || !((EntityPlayer)p_apply_1_).func_175149_v();
      }
   };

   public static <T extends Entity> Predicate<T> func_188443_a(final double p_188443_0_, final double p_188443_2_, final double p_188443_4_, double p_188443_6_) {
      final double d0 = p_188443_6_ * p_188443_6_;
      return new Predicate<T>() {
         public boolean apply(@Nullable T p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.func_70092_e(p_188443_0_, p_188443_2_, p_188443_4_) <= d0;
         }
      };
   }

   public static <T extends Entity> Predicate<T> func_188442_a(final Entity p_188442_0_) {
      final Team team = p_188442_0_.func_96124_cp();
      final Team.CollisionRule team$collisionrule = team == null ? Team.CollisionRule.ALWAYS : team.func_186681_k();
      return team$collisionrule == Team.CollisionRule.NEVER ? Predicates.alwaysFalse() : Predicates.and(field_180132_d, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            if (!p_apply_1_.func_70104_M()) {
               return false;
            } else if (!p_188442_0_.field_70170_p.field_72995_K || p_apply_1_ instanceof EntityPlayer && ((EntityPlayer)p_apply_1_).func_175144_cb()) {
               Team team1 = p_apply_1_.func_96124_cp();
               Team.CollisionRule team$collisionrule1 = team1 == null ? Team.CollisionRule.ALWAYS : team1.func_186681_k();
               if (team$collisionrule1 == Team.CollisionRule.NEVER) {
                  return false;
               } else {
                  boolean flag = team != null && team.func_142054_a(team1);
                  if ((team$collisionrule == Team.CollisionRule.HIDE_FOR_OWN_TEAM || team$collisionrule1 == Team.CollisionRule.HIDE_FOR_OWN_TEAM) && flag) {
                     return false;
                  } else {
                     return team$collisionrule != Team.CollisionRule.HIDE_FOR_OTHER_TEAMS && team$collisionrule1 != Team.CollisionRule.HIDE_FOR_OTHER_TEAMS || flag;
                  }
               }
            } else {
               return false;
            }
         }
      });
   }

   public static Predicate<Entity> func_191324_b(final Entity p_191324_0_) {
      return new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            while(true) {
               if (p_apply_1_.func_184218_aH()) {
                  p_apply_1_ = p_apply_1_.func_184187_bx();
                  if (p_apply_1_ != p_191324_0_) {
                     continue;
                  }

                  return false;
               }

               return true;
            }
         }
      };
   }

   public static class ArmoredMob implements Predicate<Entity> {
      private final ItemStack field_96567_c;

      public ArmoredMob(ItemStack p_i1584_1_) {
         this.field_96567_c = p_i1584_1_;
      }

      public boolean apply(@Nullable Entity p_apply_1_) {
         if (!p_apply_1_.func_70089_S()) {
            return false;
         } else if (!(p_apply_1_ instanceof EntityLivingBase)) {
            return false;
         } else {
            EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
            if (!entitylivingbase.func_184582_a(EntityLiving.func_184640_d(this.field_96567_c)).func_190926_b()) {
               return false;
            } else if (entitylivingbase instanceof EntityLiving) {
               return ((EntityLiving)entitylivingbase).func_98052_bS();
            } else if (entitylivingbase instanceof EntityArmorStand) {
               return true;
            } else {
               return entitylivingbase instanceof EntityPlayer;
            }
         }
      }
   }
}
