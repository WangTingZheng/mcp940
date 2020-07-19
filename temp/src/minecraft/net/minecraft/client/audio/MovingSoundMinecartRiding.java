package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class MovingSoundMinecartRiding extends MovingSound {
   private final EntityPlayer field_147672_k;
   private final EntityMinecart field_147671_l;

   public MovingSoundMinecartRiding(EntityPlayer p_i45106_1_, EntityMinecart p_i45106_2_) {
      super(SoundEvents.field_187780_dr, SoundCategory.NEUTRAL);
      this.field_147672_k = p_i45106_1_;
      this.field_147671_l = p_i45106_2_;
      this.field_147666_i = ISound.AttenuationType.NONE;
      this.field_147659_g = true;
      this.field_147665_h = 0;
   }

   public void func_73660_a() {
      if (!this.field_147671_l.field_70128_L && this.field_147672_k.func_184218_aH() && this.field_147672_k.func_184187_bx() == this.field_147671_l) {
         float f = MathHelper.func_76133_a(this.field_147671_l.field_70159_w * this.field_147671_l.field_70159_w + this.field_147671_l.field_70179_y * this.field_147671_l.field_70179_y);
         if ((double)f >= 0.01D) {
            this.field_147662_b = 0.0F + MathHelper.func_76131_a(f, 0.0F, 1.0F) * 0.75F;
         } else {
            this.field_147662_b = 0.0F;
         }

      } else {
         this.field_147668_j = true;
      }
   }
}
