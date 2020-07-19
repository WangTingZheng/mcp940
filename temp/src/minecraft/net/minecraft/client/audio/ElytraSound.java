package net.minecraft.client.audio;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class ElytraSound extends MovingSound {
   private final EntityPlayerSP field_189405_m;
   private int field_189406_n;

   public ElytraSound(EntityPlayerSP p_i47113_1_) {
      super(SoundEvents.field_189426_aK, SoundCategory.PLAYERS);
      this.field_189405_m = p_i47113_1_;
      this.field_147659_g = true;
      this.field_147665_h = 0;
      this.field_147662_b = 0.1F;
   }

   public void func_73660_a() {
      ++this.field_189406_n;
      if (!this.field_189405_m.field_70128_L && (this.field_189406_n <= 20 || this.field_189405_m.func_184613_cA())) {
         this.field_147660_d = (float)this.field_189405_m.field_70165_t;
         this.field_147661_e = (float)this.field_189405_m.field_70163_u;
         this.field_147658_f = (float)this.field_189405_m.field_70161_v;
         float f = MathHelper.func_76133_a(this.field_189405_m.field_70159_w * this.field_189405_m.field_70159_w + this.field_189405_m.field_70179_y * this.field_189405_m.field_70179_y + this.field_189405_m.field_70181_x * this.field_189405_m.field_70181_x);
         float f1 = f / 2.0F;
         if ((double)f >= 0.01D) {
            this.field_147662_b = MathHelper.func_76131_a(f1 * f1, 0.0F, 1.0F);
         } else {
            this.field_147662_b = 0.0F;
         }

         if (this.field_189406_n < 20) {
            this.field_147662_b = 0.0F;
         } else if (this.field_189406_n < 40) {
            this.field_147662_b = (float)((double)this.field_147662_b * ((double)(this.field_189406_n - 20) / 20.0D));
         }

         float f2 = 0.8F;
         if (this.field_147662_b > 0.8F) {
            this.field_147663_c = 1.0F + (this.field_147662_b - 0.8F);
         } else {
            this.field_147663_c = 1.0F;
         }

      } else {
         this.field_147668_j = true;
      }
   }
}
