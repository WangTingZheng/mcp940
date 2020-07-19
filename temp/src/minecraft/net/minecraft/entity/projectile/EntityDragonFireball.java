package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDragonFireball extends EntityFireball {
   public EntityDragonFireball(World p_i46774_1_) {
      super(p_i46774_1_);
      this.func_70105_a(1.0F, 1.0F);
   }

   public EntityDragonFireball(World p_i46775_1_, double p_i46775_2_, double p_i46775_4_, double p_i46775_6_, double p_i46775_8_, double p_i46775_10_, double p_i46775_12_) {
      super(p_i46775_1_, p_i46775_2_, p_i46775_4_, p_i46775_6_, p_i46775_8_, p_i46775_10_, p_i46775_12_);
      this.func_70105_a(1.0F, 1.0F);
   }

   public EntityDragonFireball(World p_i46776_1_, EntityLivingBase p_i46776_2_, double p_i46776_3_, double p_i46776_5_, double p_i46776_7_) {
      super(p_i46776_1_, p_i46776_2_, p_i46776_3_, p_i46776_5_, p_i46776_7_);
      this.func_70105_a(1.0F, 1.0F);
   }

   public static void func_189747_a(DataFixer p_189747_0_) {
      EntityFireball.func_189743_a(p_189747_0_, "DragonFireball");
   }

   protected void func_70227_a(RayTraceResult p_70227_1_) {
      if (p_70227_1_.field_72308_g == null || !p_70227_1_.field_72308_g.func_70028_i(this.field_70235_a)) {
         if (!this.field_70170_p.field_72995_K) {
            List<EntityLivingBase> list = this.field_70170_p.<EntityLivingBase>func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D));
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
            entityareaeffectcloud.func_184481_a(this.field_70235_a);
            entityareaeffectcloud.func_184491_a(EnumParticleTypes.DRAGON_BREATH);
            entityareaeffectcloud.func_184483_a(3.0F);
            entityareaeffectcloud.func_184486_b(600);
            entityareaeffectcloud.func_184487_c((7.0F - entityareaeffectcloud.func_184490_j()) / (float)entityareaeffectcloud.func_184489_o());
            entityareaeffectcloud.func_184496_a(new PotionEffect(MobEffects.field_76433_i, 1, 1));
            if (!list.isEmpty()) {
               for(EntityLivingBase entitylivingbase : list) {
                  double d0 = this.func_70068_e(entitylivingbase);
                  if (d0 < 16.0D) {
                     entityareaeffectcloud.func_70107_b(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v);
                     break;
                  }
               }
            }

            this.field_70170_p.func_175718_b(2006, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
            this.field_70170_p.func_72838_d(entityareaeffectcloud);
            this.func_70106_y();
         }

      }
   }

   public boolean func_70067_L() {
      return false;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      return false;
   }

   protected EnumParticleTypes func_184563_j() {
      return EnumParticleTypes.DRAGON_BREATH;
   }

   protected boolean func_184564_k() {
      return false;
   }
}
