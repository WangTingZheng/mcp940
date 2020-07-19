package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;

@Immutable
public class DifficultyInstance {
   private final EnumDifficulty field_180172_a;
   private final float field_180171_b;

   public DifficultyInstance(EnumDifficulty p_i45904_1_, long p_i45904_2_, long p_i45904_4_, float p_i45904_6_) {
      this.field_180172_a = p_i45904_1_;
      this.field_180171_b = this.func_180169_a(p_i45904_1_, p_i45904_2_, p_i45904_4_, p_i45904_6_);
   }

   public float func_180168_b() {
      return this.field_180171_b;
   }

   public boolean func_193845_a(float p_193845_1_) {
      return this.field_180171_b > p_193845_1_;
   }

   public float func_180170_c() {
      if (this.field_180171_b < 2.0F) {
         return 0.0F;
      } else {
         return this.field_180171_b > 4.0F ? 1.0F : (this.field_180171_b - 2.0F) / 2.0F;
      }
   }

   private float func_180169_a(EnumDifficulty p_180169_1_, long p_180169_2_, long p_180169_4_, float p_180169_6_) {
      if (p_180169_1_ == EnumDifficulty.PEACEFUL) {
         return 0.0F;
      } else {
         boolean flag = p_180169_1_ == EnumDifficulty.HARD;
         float f = 0.75F;
         float f1 = MathHelper.func_76131_a(((float)p_180169_2_ + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         f = f + f1;
         float f2 = 0.0F;
         f2 = f2 + MathHelper.func_76131_a((float)p_180169_4_ / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
         f2 = f2 + MathHelper.func_76131_a(p_180169_6_ * 0.25F, 0.0F, f1);
         if (p_180169_1_ == EnumDifficulty.EASY) {
            f2 *= 0.5F;
         }

         f = f + f2;
         return (float)p_180169_1_.func_151525_a() * f;
      }
   }
}
