package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;

public class FoodStats {
   private int field_75127_a = 20;
   private float field_75125_b = 5.0F;
   private float field_75126_c;
   private int field_75123_d;
   private int field_75124_e = 20;

   public void func_75122_a(int p_75122_1_, float p_75122_2_) {
      this.field_75127_a = Math.min(p_75122_1_ + this.field_75127_a, 20);
      this.field_75125_b = Math.min(this.field_75125_b + (float)p_75122_1_ * p_75122_2_ * 2.0F, (float)this.field_75127_a);
   }

   public void func_151686_a(ItemFood p_151686_1_, ItemStack p_151686_2_) {
      this.func_75122_a(p_151686_1_.func_150905_g(p_151686_2_), p_151686_1_.func_150906_h(p_151686_2_));
   }

   public void func_75118_a(EntityPlayer p_75118_1_) {
      EnumDifficulty enumdifficulty = p_75118_1_.field_70170_p.func_175659_aa();
      this.field_75124_e = this.field_75127_a;
      if (this.field_75126_c > 4.0F) {
         this.field_75126_c -= 4.0F;
         if (this.field_75125_b > 0.0F) {
            this.field_75125_b = Math.max(this.field_75125_b - 1.0F, 0.0F);
         } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
            this.field_75127_a = Math.max(this.field_75127_a - 1, 0);
         }
      }

      boolean flag = p_75118_1_.field_70170_p.func_82736_K().func_82766_b("naturalRegeneration");
      if (flag && this.field_75125_b > 0.0F && p_75118_1_.func_70996_bM() && this.field_75127_a >= 20) {
         ++this.field_75123_d;
         if (this.field_75123_d >= 10) {
            float f = Math.min(this.field_75125_b, 6.0F);
            p_75118_1_.func_70691_i(f / 6.0F);
            this.func_75113_a(f);
            this.field_75123_d = 0;
         }
      } else if (flag && this.field_75127_a >= 18 && p_75118_1_.func_70996_bM()) {
         ++this.field_75123_d;
         if (this.field_75123_d >= 80) {
            p_75118_1_.func_70691_i(1.0F);
            this.func_75113_a(6.0F);
            this.field_75123_d = 0;
         }
      } else if (this.field_75127_a <= 0) {
         ++this.field_75123_d;
         if (this.field_75123_d >= 80) {
            if (p_75118_1_.func_110143_aJ() > 10.0F || enumdifficulty == EnumDifficulty.HARD || p_75118_1_.func_110143_aJ() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL) {
               p_75118_1_.func_70097_a(DamageSource.field_76366_f, 1.0F);
            }

            this.field_75123_d = 0;
         }
      } else {
         this.field_75123_d = 0;
      }

   }

   public void func_75112_a(NBTTagCompound p_75112_1_) {
      if (p_75112_1_.func_150297_b("foodLevel", 99)) {
         this.field_75127_a = p_75112_1_.func_74762_e("foodLevel");
         this.field_75123_d = p_75112_1_.func_74762_e("foodTickTimer");
         this.field_75125_b = p_75112_1_.func_74760_g("foodSaturationLevel");
         this.field_75126_c = p_75112_1_.func_74760_g("foodExhaustionLevel");
      }

   }

   public void func_75117_b(NBTTagCompound p_75117_1_) {
      p_75117_1_.func_74768_a("foodLevel", this.field_75127_a);
      p_75117_1_.func_74768_a("foodTickTimer", this.field_75123_d);
      p_75117_1_.func_74776_a("foodSaturationLevel", this.field_75125_b);
      p_75117_1_.func_74776_a("foodExhaustionLevel", this.field_75126_c);
   }

   public int func_75116_a() {
      return this.field_75127_a;
   }

   public boolean func_75121_c() {
      return this.field_75127_a < 20;
   }

   public void func_75113_a(float p_75113_1_) {
      this.field_75126_c = Math.min(this.field_75126_c + p_75113_1_, 40.0F);
   }

   public float func_75115_e() {
      return this.field_75125_b;
   }

   public void func_75114_a(int p_75114_1_) {
      this.field_75127_a = p_75114_1_;
   }

   public void func_75119_b(float p_75119_1_) {
      this.field_75125_b = p_75119_1_;
   }
}
