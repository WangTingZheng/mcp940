package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {
   public int field_70533_a;
   public int field_70531_b;
   public int field_70532_c;
   private int field_70529_d = 5;
   private int field_70530_e;
   private EntityPlayer field_80001_f;
   private int field_80002_g;

   public EntityXPOrb(World p_i1585_1_, double p_i1585_2_, double p_i1585_4_, double p_i1585_6_, int p_i1585_8_) {
      super(p_i1585_1_);
      this.func_70105_a(0.5F, 0.5F);
      this.func_70107_b(p_i1585_2_, p_i1585_4_, p_i1585_6_);
      this.field_70177_z = (float)(Math.random() * 360.0D);
      this.field_70159_w = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
      this.field_70181_x = (double)((float)(Math.random() * 0.2D) * 2.0F);
      this.field_70179_y = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
      this.field_70530_e = p_i1585_8_;
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public EntityXPOrb(World p_i1586_1_) {
      super(p_i1586_1_);
      this.func_70105_a(0.25F, 0.25F);
   }

   protected void func_70088_a() {
   }

   public int func_70070_b() {
      float f = 0.5F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      int i = super.func_70070_b();
      int j = i & 255;
      int k = i >> 16 & 255;
      j = j + (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70532_c > 0) {
         --this.field_70532_c;
      }

      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      if (!this.func_189652_ae()) {
         this.field_70181_x -= 0.029999999329447746D;
      }

      if (this.field_70170_p.func_180495_p(new BlockPos(this)).func_185904_a() == Material.field_151587_i) {
         this.field_70181_x = 0.20000000298023224D;
         this.field_70159_w = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
         this.field_70179_y = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
         this.func_184185_a(SoundEvents.field_187658_bx, 0.4F, 2.0F + this.field_70146_Z.nextFloat() * 0.4F);
      }

      this.func_145771_j(this.field_70165_t, (this.func_174813_aQ().field_72338_b + this.func_174813_aQ().field_72337_e) / 2.0D, this.field_70161_v);
      double d0 = 8.0D;
      if (this.field_80002_g < this.field_70533_a - 20 + this.func_145782_y() % 100) {
         if (this.field_80001_f == null || this.field_80001_f.func_70068_e(this) > 64.0D) {
            this.field_80001_f = this.field_70170_p.func_72890_a(this, 8.0D);
         }

         this.field_80002_g = this.field_70533_a;
      }

      if (this.field_80001_f != null && this.field_80001_f.func_175149_v()) {
         this.field_80001_f = null;
      }

      if (this.field_80001_f != null) {
         double d1 = (this.field_80001_f.field_70165_t - this.field_70165_t) / 8.0D;
         double d2 = (this.field_80001_f.field_70163_u + (double)this.field_80001_f.func_70047_e() / 2.0D - this.field_70163_u) / 8.0D;
         double d3 = (this.field_80001_f.field_70161_v - this.field_70161_v) / 8.0D;
         double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         double d5 = 1.0D - d4;
         if (d5 > 0.0D) {
            d5 = d5 * d5;
            this.field_70159_w += d1 / d4 * d5 * 0.1D;
            this.field_70181_x += d2 / d4 * d5 * 0.1D;
            this.field_70179_y += d3 / d4 * d5 * 0.1D;
         }
      }

      this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      float f = 0.98F;
      if (this.field_70122_E) {
         f = this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c().field_149765_K * 0.98F;
      }

      this.field_70159_w *= (double)f;
      this.field_70181_x *= 0.9800000190734863D;
      this.field_70179_y *= (double)f;
      if (this.field_70122_E) {
         this.field_70181_x *= -0.8999999761581421D;
      }

      ++this.field_70533_a;
      ++this.field_70531_b;
      if (this.field_70531_b >= 6000) {
         this.func_70106_y();
      }

   }

   public boolean func_70072_I() {
      return this.field_70170_p.func_72918_a(this.func_174813_aQ(), Material.field_151586_h, this);
   }

   protected void func_70081_e(int p_70081_1_) {
      this.func_70097_a(DamageSource.field_76372_a, (float)p_70081_1_);
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         this.func_70018_K();
         this.field_70529_d = (int)((float)this.field_70529_d - p_70097_2_);
         if (this.field_70529_d <= 0) {
            this.func_70106_y();
         }

         return false;
      }
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74777_a("Health", (short)this.field_70529_d);
      p_70014_1_.func_74777_a("Age", (short)this.field_70531_b);
      p_70014_1_.func_74777_a("Value", (short)this.field_70530_e);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_70529_d = p_70037_1_.func_74765_d("Health");
      this.field_70531_b = p_70037_1_.func_74765_d("Age");
      this.field_70530_e = p_70037_1_.func_74765_d("Value");
   }

   public void func_70100_b_(EntityPlayer p_70100_1_) {
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70532_c == 0 && p_70100_1_.field_71090_bL == 0) {
            p_70100_1_.field_71090_bL = 2;
            p_70100_1_.func_71001_a(this, 1);
            ItemStack itemstack = EnchantmentHelper.func_92099_a(Enchantments.field_185296_A, p_70100_1_);
            if (!itemstack.func_190926_b() && itemstack.func_77951_h()) {
               int i = Math.min(this.func_184514_c(this.field_70530_e), itemstack.func_77952_i());
               this.field_70530_e -= this.func_184515_b(i);
               itemstack.func_77964_b(itemstack.func_77952_i() - i);
            }

            if (this.field_70530_e > 0) {
               p_70100_1_.func_71023_q(this.field_70530_e);
            }

            this.func_70106_y();
         }

      }
   }

   private int func_184515_b(int p_184515_1_) {
      return p_184515_1_ / 2;
   }

   private int func_184514_c(int p_184514_1_) {
      return p_184514_1_ * 2;
   }

   public int func_70526_d() {
      return this.field_70530_e;
   }

   public int func_70528_g() {
      if (this.field_70530_e >= 2477) {
         return 10;
      } else if (this.field_70530_e >= 1237) {
         return 9;
      } else if (this.field_70530_e >= 617) {
         return 8;
      } else if (this.field_70530_e >= 307) {
         return 7;
      } else if (this.field_70530_e >= 149) {
         return 6;
      } else if (this.field_70530_e >= 73) {
         return 5;
      } else if (this.field_70530_e >= 37) {
         return 4;
      } else if (this.field_70530_e >= 17) {
         return 3;
      } else if (this.field_70530_e >= 7) {
         return 2;
      } else {
         return this.field_70530_e >= 3 ? 1 : 0;
      }
   }

   public static int func_70527_a(int p_70527_0_) {
      if (p_70527_0_ >= 2477) {
         return 2477;
      } else if (p_70527_0_ >= 1237) {
         return 1237;
      } else if (p_70527_0_ >= 617) {
         return 617;
      } else if (p_70527_0_ >= 307) {
         return 307;
      } else if (p_70527_0_ >= 149) {
         return 149;
      } else if (p_70527_0_ >= 73) {
         return 73;
      } else if (p_70527_0_ >= 37) {
         return 37;
      } else if (p_70527_0_ >= 17) {
         return 17;
      } else if (p_70527_0_ >= 7) {
         return 7;
      } else {
         return p_70527_0_ >= 3 ? 3 : 1;
      }
   }

   public boolean func_70075_an() {
      return false;
   }
}
