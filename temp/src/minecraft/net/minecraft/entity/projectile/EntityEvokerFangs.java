package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityEvokerFangs extends Entity {
   private int field_190553_a;
   private boolean field_190554_b;
   private int field_190555_c;
   private boolean field_190556_d;
   private EntityLivingBase field_190557_e;
   private UUID field_190558_f;

   public EntityEvokerFangs(World p_i47275_1_) {
      super(p_i47275_1_);
      this.field_190555_c = 22;
      this.func_70105_a(0.5F, 0.8F);
   }

   public EntityEvokerFangs(World p_i47276_1_, double p_i47276_2_, double p_i47276_4_, double p_i47276_6_, float p_i47276_8_, int p_i47276_9_, EntityLivingBase p_i47276_10_) {
      this(p_i47276_1_);
      this.field_190553_a = p_i47276_9_;
      this.func_190549_a(p_i47276_10_);
      this.field_70177_z = p_i47276_8_ * 57.295776F;
      this.func_70107_b(p_i47276_2_, p_i47276_4_, p_i47276_6_);
   }

   protected void func_70088_a() {
   }

   public void func_190549_a(@Nullable EntityLivingBase p_190549_1_) {
      this.field_190557_e = p_190549_1_;
      this.field_190558_f = p_190549_1_ == null ? null : p_190549_1_.func_110124_au();
   }

   @Nullable
   public EntityLivingBase func_190552_j() {
      if (this.field_190557_e == null && this.field_190558_f != null && this.field_70170_p instanceof WorldServer) {
         Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.field_190558_f);
         if (entity instanceof EntityLivingBase) {
            this.field_190557_e = (EntityLivingBase)entity;
         }
      }

      return this.field_190557_e;
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_190553_a = p_70037_1_.func_74762_e("Warmup");
      this.field_190558_f = p_70037_1_.func_186857_a("OwnerUUID");
   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("Warmup", this.field_190553_a);
      if (this.field_190558_f != null) {
         p_70014_1_.func_186854_a("OwnerUUID", this.field_190558_f);
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_190556_d) {
            --this.field_190555_c;
            if (this.field_190555_c == 14) {
               for(int i = 0; i < 12; ++i) {
                  double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * (double)this.field_70130_N * 0.5D;
                  double d1 = this.field_70163_u + 0.05D + this.field_70146_Z.nextDouble() * 1.0D;
                  double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * (double)this.field_70130_N * 0.5D;
                  double d3 = (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * 0.3D;
                  double d4 = 0.3D + this.field_70146_Z.nextDouble() * 0.3D;
                  double d5 = (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * 0.3D;
                  this.field_70170_p.func_175688_a(EnumParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
               }
            }
         }
      } else if (--this.field_190553_a < 0) {
         if (this.field_190553_a == -8) {
            for(EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D))) {
               this.func_190551_c(entitylivingbase);
            }
         }

         if (!this.field_190554_b) {
            this.field_70170_p.func_72960_a(this, (byte)4);
            this.field_190554_b = true;
         }

         if (--this.field_190555_c < 0) {
            this.func_70106_y();
         }
      }

   }

   private void func_190551_c(EntityLivingBase p_190551_1_) {
      EntityLivingBase entitylivingbase = this.func_190552_j();
      if (p_190551_1_.func_70089_S() && !p_190551_1_.func_190530_aW() && p_190551_1_ != entitylivingbase) {
         if (entitylivingbase == null) {
            p_190551_1_.func_70097_a(DamageSource.field_76376_m, 6.0F);
         } else {
            if (entitylivingbase.func_184191_r(p_190551_1_)) {
               return;
            }

            p_190551_1_.func_70097_a(DamageSource.func_76354_b(this, entitylivingbase), 6.0F);
         }

      }
   }

   public void func_70103_a(byte p_70103_1_) {
      super.func_70103_a(p_70103_1_);
      if (p_70103_1_ == 4) {
         this.field_190556_d = true;
         if (!this.func_174814_R()) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_191242_bl, this.func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.2F + 0.85F, false);
         }
      }

   }

   public float func_190550_a(float p_190550_1_) {
      if (!this.field_190556_d) {
         return 0.0F;
      } else {
         int i = this.field_190555_c - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - p_190550_1_) / 20.0F;
      }
   }
}
