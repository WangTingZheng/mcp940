package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGhast extends EntityFlying implements IMob {
   private static final DataParameter<Boolean> field_184683_a = EntityDataManager.<Boolean>func_187226_a(EntityGhast.class, DataSerializers.field_187198_h);
   private int field_92014_j = 1;

   public EntityGhast(World p_i1735_1_) {
      super(p_i1735_1_);
      this.func_70105_a(4.0F, 4.0F);
      this.field_70178_ae = true;
      this.field_70728_aV = 5;
      this.field_70765_h = new EntityGhast.GhastMoveHelper(this);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(5, new EntityGhast.AIRandomFly(this));
      this.field_70714_bg.func_75776_a(7, new EntityGhast.AILookAround(this));
      this.field_70714_bg.func_75776_a(7, new EntityGhast.AIFireballAttack(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIFindEntityNearestPlayer(this));
   }

   public boolean func_110182_bF() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184683_a)).booleanValue();
   }

   public void func_175454_a(boolean p_175454_1_) {
      this.field_70180_af.func_187227_b(field_184683_a, Boolean.valueOf(p_175454_1_));
   }

   public int func_175453_cd() {
      return this.field_92014_j;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      }

   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (p_70097_1_.func_76364_f() instanceof EntityLargeFireball && p_70097_1_.func_76346_g() instanceof EntityPlayer) {
         super.func_70097_a(p_70097_1_, 1000.0F);
         return true;
      } else {
         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184683_a, Boolean.valueOf(false));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100.0D);
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187551_bH;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187555_bJ;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187553_bI;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186380_ae;
   }

   protected float func_70599_aP() {
      return 10.0F;
   }

   public boolean func_70601_bi() {
      return this.field_70146_Z.nextInt(20) == 0 && super.func_70601_bi() && this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   public int func_70641_bl() {
      return 1;
   }

   public static void func_189756_b(DataFixer p_189756_0_) {
      EntityLiving.func_189752_a(p_189756_0_, EntityGhast.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("ExplosionPower", this.field_92014_j);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("ExplosionPower", 99)) {
         this.field_92014_j = p_70037_1_.func_74762_e("ExplosionPower");
      }

   }

   public float func_70047_e() {
      return 2.6F;
   }

   static class AIFireballAttack extends EntityAIBase {
      private final EntityGhast field_179470_b;
      public int field_179471_a;

      public AIFireballAttack(EntityGhast p_i45837_1_) {
         this.field_179470_b = p_i45837_1_;
      }

      public boolean func_75250_a() {
         return this.field_179470_b.func_70638_az() != null;
      }

      public void func_75249_e() {
         this.field_179471_a = 0;
      }

      public void func_75251_c() {
         this.field_179470_b.func_175454_a(false);
      }

      public void func_75246_d() {
         EntityLivingBase entitylivingbase = this.field_179470_b.func_70638_az();
         double d0 = 64.0D;
         if (entitylivingbase.func_70068_e(this.field_179470_b) < 4096.0D && this.field_179470_b.func_70685_l(entitylivingbase)) {
            World world = this.field_179470_b.field_70170_p;
            ++this.field_179471_a;
            if (this.field_179471_a == 10) {
               world.func_180498_a((EntityPlayer)null, 1015, new BlockPos(this.field_179470_b), 0);
            }

            if (this.field_179471_a == 20) {
               double d1 = 4.0D;
               Vec3d vec3d = this.field_179470_b.func_70676_i(1.0F);
               double d2 = entitylivingbase.field_70165_t - (this.field_179470_b.field_70165_t + vec3d.field_72450_a * 4.0D);
               double d3 = entitylivingbase.func_174813_aQ().field_72338_b + (double)(entitylivingbase.field_70131_O / 2.0F) - (0.5D + this.field_179470_b.field_70163_u + (double)(this.field_179470_b.field_70131_O / 2.0F));
               double d4 = entitylivingbase.field_70161_v - (this.field_179470_b.field_70161_v + vec3d.field_72449_c * 4.0D);
               world.func_180498_a((EntityPlayer)null, 1016, new BlockPos(this.field_179470_b), 0);
               EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.field_179470_b, d2, d3, d4);
               entitylargefireball.field_92057_e = this.field_179470_b.func_175453_cd();
               entitylargefireball.field_70165_t = this.field_179470_b.field_70165_t + vec3d.field_72450_a * 4.0D;
               entitylargefireball.field_70163_u = this.field_179470_b.field_70163_u + (double)(this.field_179470_b.field_70131_O / 2.0F) + 0.5D;
               entitylargefireball.field_70161_v = this.field_179470_b.field_70161_v + vec3d.field_72449_c * 4.0D;
               world.func_72838_d(entitylargefireball);
               this.field_179471_a = -40;
            }
         } else if (this.field_179471_a > 0) {
            --this.field_179471_a;
         }

         this.field_179470_b.func_175454_a(this.field_179471_a > 10);
      }
   }

   static class AILookAround extends EntityAIBase {
      private final EntityGhast field_179472_a;

      public AILookAround(EntityGhast p_i45839_1_) {
         this.field_179472_a = p_i45839_1_;
         this.func_75248_a(2);
      }

      public boolean func_75250_a() {
         return true;
      }

      public void func_75246_d() {
         if (this.field_179472_a.func_70638_az() == null) {
            this.field_179472_a.field_70177_z = -((float)MathHelper.func_181159_b(this.field_179472_a.field_70159_w, this.field_179472_a.field_70179_y)) * 57.295776F;
            this.field_179472_a.field_70761_aq = this.field_179472_a.field_70177_z;
         } else {
            EntityLivingBase entitylivingbase = this.field_179472_a.func_70638_az();
            double d0 = 64.0D;
            if (entitylivingbase.func_70068_e(this.field_179472_a) < 4096.0D) {
               double d1 = entitylivingbase.field_70165_t - this.field_179472_a.field_70165_t;
               double d2 = entitylivingbase.field_70161_v - this.field_179472_a.field_70161_v;
               this.field_179472_a.field_70177_z = -((float)MathHelper.func_181159_b(d1, d2)) * 57.295776F;
               this.field_179472_a.field_70761_aq = this.field_179472_a.field_70177_z;
            }
         }

      }
   }

   static class AIRandomFly extends EntityAIBase {
      private final EntityGhast field_179454_a;

      public AIRandomFly(EntityGhast p_i45836_1_) {
         this.field_179454_a = p_i45836_1_;
         this.func_75248_a(1);
      }

      public boolean func_75250_a() {
         EntityMoveHelper entitymovehelper = this.field_179454_a.func_70605_aq();
         if (!entitymovehelper.func_75640_a()) {
            return true;
         } else {
            double d0 = entitymovehelper.func_179917_d() - this.field_179454_a.field_70165_t;
            double d1 = entitymovehelper.func_179919_e() - this.field_179454_a.field_70163_u;
            double d2 = entitymovehelper.func_179918_f() - this.field_179454_a.field_70161_v;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
         }
      }

      public boolean func_75253_b() {
         return false;
      }

      public void func_75249_e() {
         Random random = this.field_179454_a.func_70681_au();
         double d0 = this.field_179454_a.field_70165_t + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d1 = this.field_179454_a.field_70163_u + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d2 = this.field_179454_a.field_70161_v + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.field_179454_a.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
      }
   }

   static class GhastMoveHelper extends EntityMoveHelper {
      private final EntityGhast field_179927_g;
      private int field_179928_h;

      public GhastMoveHelper(EntityGhast p_i45838_1_) {
         super(p_i45838_1_);
         this.field_179927_g = p_i45838_1_;
      }

      public void func_75641_c() {
         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
            double d0 = this.field_75646_b - this.field_179927_g.field_70165_t;
            double d1 = this.field_75647_c - this.field_179927_g.field_70163_u;
            double d2 = this.field_75644_d - this.field_179927_g.field_70161_v;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (this.field_179928_h-- <= 0) {
               this.field_179928_h += this.field_179927_g.func_70681_au().nextInt(5) + 2;
               d3 = (double)MathHelper.func_76133_a(d3);
               if (this.func_179926_b(this.field_75646_b, this.field_75647_c, this.field_75644_d, d3)) {
                  this.field_179927_g.field_70159_w += d0 / d3 * 0.1D;
                  this.field_179927_g.field_70181_x += d1 / d3 * 0.1D;
                  this.field_179927_g.field_70179_y += d2 / d3 * 0.1D;
               } else {
                  this.field_188491_h = EntityMoveHelper.Action.WAIT;
               }
            }

         }
      }

      private boolean func_179926_b(double p_179926_1_, double p_179926_3_, double p_179926_5_, double p_179926_7_) {
         double d0 = (p_179926_1_ - this.field_179927_g.field_70165_t) / p_179926_7_;
         double d1 = (p_179926_3_ - this.field_179927_g.field_70163_u) / p_179926_7_;
         double d2 = (p_179926_5_ - this.field_179927_g.field_70161_v) / p_179926_7_;
         AxisAlignedBB axisalignedbb = this.field_179927_g.func_174813_aQ();

         for(int i = 1; (double)i < p_179926_7_; ++i) {
            axisalignedbb = axisalignedbb.func_72317_d(d0, d1, d2);
            if (!this.field_179927_g.field_70170_p.func_184144_a(this.field_179927_g, axisalignedbb).isEmpty()) {
               return false;
            }
         }

         return true;
      }
   }
}
