package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger field_179436_a = LogManager.getLogger();
   private final EntityLiving field_179434_b;
   private final Predicate<Entity> field_179435_c;
   private final EntityAINearestAttackableTarget.Sorter field_179432_d;
   private EntityLivingBase field_179433_e;

   public EntityAIFindEntityNearestPlayer(EntityLiving p_i45882_1_) {
      this.field_179434_b = p_i45882_1_;
      if (p_i45882_1_ instanceof EntityCreature) {
         field_179436_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179435_c = new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            if (!(p_apply_1_ instanceof EntityPlayer)) {
               return false;
            } else if (((EntityPlayer)p_apply_1_).field_71075_bZ.field_75102_a) {
               return false;
            } else {
               double d0 = EntityAIFindEntityNearestPlayer.this.func_179431_f();
               if (p_apply_1_.func_70093_af()) {
                  d0 *= 0.800000011920929D;
               }

               if (p_apply_1_.func_82150_aj()) {
                  float f = ((EntityPlayer)p_apply_1_).func_82243_bO();
                  if (f < 0.1F) {
                     f = 0.1F;
                  }

                  d0 *= (double)(0.7F * f);
               }

               return (double)p_apply_1_.func_70032_d(EntityAIFindEntityNearestPlayer.this.field_179434_b) > d0 ? false : EntityAITarget.func_179445_a(EntityAIFindEntityNearestPlayer.this.field_179434_b, (EntityLivingBase)p_apply_1_, false, true);
            }
         }
      };
      this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(p_i45882_1_);
   }

   public boolean func_75250_a() {
      double d0 = this.func_179431_f();
      List<EntityPlayer> list = this.field_179434_b.field_70170_p.<EntityPlayer>func_175647_a(EntityPlayer.class, this.field_179434_b.func_174813_aQ().func_72314_b(d0, 4.0D, d0), this.field_179435_c);
      Collections.sort(list, this.field_179432_d);
      if (list.isEmpty()) {
         return false;
      } else {
         this.field_179433_e = list.get(0);
         return true;
      }
   }

   public boolean func_75253_b() {
      EntityLivingBase entitylivingbase = this.field_179434_b.func_70638_az();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.func_70089_S()) {
         return false;
      } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a) {
         return false;
      } else {
         Team team = this.field_179434_b.func_96124_cp();
         Team team1 = entitylivingbase.func_96124_cp();
         if (team != null && team1 == team) {
            return false;
         } else {
            double d0 = this.func_179431_f();
            if (this.field_179434_b.func_70068_e(entitylivingbase) > d0 * d0) {
               return false;
            } else {
               return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).field_71134_c.func_73083_d();
            }
         }
      }
   }

   public void func_75249_e() {
      this.field_179434_b.func_70624_b(this.field_179433_e);
      super.func_75249_e();
   }

   public void func_75251_c() {
      this.field_179434_b.func_70624_b((EntityLivingBase)null);
      super.func_75249_e();
   }

   protected double func_179431_f() {
      IAttributeInstance iattributeinstance = this.field_179434_b.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return iattributeinstance == null ? 16.0D : iattributeinstance.func_111126_e();
   }
}
