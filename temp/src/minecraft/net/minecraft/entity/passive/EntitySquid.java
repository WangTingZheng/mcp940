package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySquid extends EntityWaterMob {
   public float field_70861_d;
   public float field_70862_e;
   public float field_70859_f;
   public float field_70860_g;
   public float field_70867_h;
   public float field_70868_i;
   public float field_70866_j;
   public float field_70865_by;
   private float field_70863_bz;
   private float field_70864_bA;
   private float field_70871_bB;
   private float field_70872_bC;
   private float field_70869_bD;
   private float field_70870_bE;

   public EntitySquid(World p_i1693_1_) {
      super(p_i1693_1_);
      this.func_70105_a(0.8F, 0.8F);
      this.field_70146_Z.setSeed((long)(1 + this.func_145782_y()));
      this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
   }

   public static void func_189804_b(DataFixer p_189804_0_) {
      EntityLiving.func_189752_a(p_189804_0_, EntitySquid.class);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntitySquid.AIMoveRandom(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
   }

   public float func_70047_e() {
      return this.field_70131_O * 0.5F;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187829_fQ;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187833_fS;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187831_fR;
   }

   protected float func_70599_aP() {
      return 0.4F;
   }

   protected boolean func_70041_e_() {
      return false;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186381_af;
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.field_70862_e = this.field_70861_d;
      this.field_70860_g = this.field_70859_f;
      this.field_70868_i = this.field_70867_h;
      this.field_70865_by = this.field_70866_j;
      this.field_70867_h += this.field_70864_bA;
      if ((double)this.field_70867_h > 6.283185307179586D) {
         if (this.field_70170_p.field_72995_K) {
            this.field_70867_h = 6.2831855F;
         } else {
            this.field_70867_h = (float)((double)this.field_70867_h - 6.283185307179586D);
            if (this.field_70146_Z.nextInt(10) == 0) {
               this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
            }

            this.field_70170_p.func_72960_a(this, (byte)19);
         }
      }

      if (this.field_70171_ac) {
         if (this.field_70867_h < 3.1415927F) {
            float f = this.field_70867_h / 3.1415927F;
            this.field_70866_j = MathHelper.func_76126_a(f * f * 3.1415927F) * 3.1415927F * 0.25F;
            if ((double)f > 0.75D) {
               this.field_70863_bz = 1.0F;
               this.field_70871_bB = 1.0F;
            } else {
               this.field_70871_bB *= 0.8F;
            }
         } else {
            this.field_70866_j = 0.0F;
            this.field_70863_bz *= 0.9F;
            this.field_70871_bB *= 0.99F;
         }

         if (!this.field_70170_p.field_72995_K) {
            this.field_70159_w = (double)(this.field_70872_bC * this.field_70863_bz);
            this.field_70181_x = (double)(this.field_70869_bD * this.field_70863_bz);
            this.field_70179_y = (double)(this.field_70870_bE * this.field_70863_bz);
         }

         float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70761_aq += (-((float)MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y)) * 57.295776F - this.field_70761_aq) * 0.1F;
         this.field_70177_z = this.field_70761_aq;
         this.field_70859_f = (float)((double)this.field_70859_f + 3.141592653589793D * (double)this.field_70871_bB * 1.5D);
         this.field_70861_d += (-((float)MathHelper.func_181159_b((double)f1, this.field_70181_x)) * 57.295776F - this.field_70861_d) * 0.1F;
      } else {
         this.field_70866_j = MathHelper.func_76135_e(MathHelper.func_76126_a(this.field_70867_h)) * 3.1415927F * 0.25F;
         if (!this.field_70170_p.field_72995_K) {
            this.field_70159_w = 0.0D;
            this.field_70179_y = 0.0D;
            if (this.func_70644_a(MobEffects.field_188424_y)) {
               this.field_70181_x += 0.05D * (double)(this.func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x;
            } else if (!this.func_189652_ae()) {
               this.field_70181_x -= 0.08D;
            }

            this.field_70181_x *= 0.9800000190734863D;
         }

         this.field_70861_d = (float)((double)this.field_70861_d + (double)(-90.0F - this.field_70861_d) * 0.02D);
      }

   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
   }

   public boolean func_70601_bi() {
      return this.field_70163_u > 45.0D && this.field_70163_u < (double)this.field_70170_p.func_181545_F() && super.func_70601_bi();
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 19) {
         this.field_70867_h = 0.0F;
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public void func_175568_b(float p_175568_1_, float p_175568_2_, float p_175568_3_) {
      this.field_70872_bC = p_175568_1_;
      this.field_70869_bD = p_175568_2_;
      this.field_70870_bE = p_175568_3_;
   }

   public boolean func_175567_n() {
      return this.field_70872_bC != 0.0F || this.field_70869_bD != 0.0F || this.field_70870_bE != 0.0F;
   }

   static class AIMoveRandom extends EntityAIBase {
      private final EntitySquid field_179476_a;

      public AIMoveRandom(EntitySquid p_i45859_1_) {
         this.field_179476_a = p_i45859_1_;
      }

      public boolean func_75250_a() {
         return true;
      }

      public void func_75246_d() {
         int i = this.field_179476_a.func_70654_ax();
         if (i > 100) {
            this.field_179476_a.func_175568_b(0.0F, 0.0F, 0.0F);
         } else if (this.field_179476_a.func_70681_au().nextInt(50) == 0 || !this.field_179476_a.field_70171_ac || !this.field_179476_a.func_175567_n()) {
            float f = this.field_179476_a.func_70681_au().nextFloat() * 6.2831855F;
            float f1 = MathHelper.func_76134_b(f) * 0.2F;
            float f2 = -0.1F + this.field_179476_a.func_70681_au().nextFloat() * 0.2F;
            float f3 = MathHelper.func_76126_a(f) * 0.2F;
            this.field_179476_a.func_175568_b(f1, f2, f3);
         }

      }
   }
}
