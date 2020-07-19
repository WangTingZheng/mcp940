package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class MusicTicker implements ITickable {
   private final Random field_147679_a = new Random();
   private final Minecraft field_147677_b;
   private ISound field_147678_c;
   private int field_147676_d = 100;

   public MusicTicker(Minecraft p_i45112_1_) {
      this.field_147677_b = p_i45112_1_;
   }

   public void func_73660_a() {
      MusicTicker.MusicType musicticker$musictype = this.field_147677_b.func_147109_W();
      if (this.field_147678_c != null) {
         if (!musicticker$musictype.func_188768_a().func_187503_a().equals(this.field_147678_c.func_147650_b())) {
            this.field_147677_b.func_147118_V().func_147683_b(this.field_147678_c);
            this.field_147676_d = MathHelper.func_76136_a(this.field_147679_a, 0, musicticker$musictype.func_148634_b() / 2);
         }

         if (!this.field_147677_b.func_147118_V().func_147692_c(this.field_147678_c)) {
            this.field_147678_c = null;
            this.field_147676_d = Math.min(MathHelper.func_76136_a(this.field_147679_a, musicticker$musictype.func_148634_b(), musicticker$musictype.func_148633_c()), this.field_147676_d);
         }
      }

      this.field_147676_d = Math.min(this.field_147676_d, musicticker$musictype.func_148633_c());
      if (this.field_147678_c == null && this.field_147676_d-- <= 0) {
         this.func_181558_a(musicticker$musictype);
      }

   }

   public void func_181558_a(MusicTicker.MusicType p_181558_1_) {
      this.field_147678_c = PositionedSoundRecord.func_184370_a(p_181558_1_.func_188768_a());
      this.field_147677_b.func_147118_V().func_147682_a(this.field_147678_c);
      this.field_147676_d = Integer.MAX_VALUE;
   }

   public static enum MusicType {
      MENU(SoundEvents.field_187671_dC, 20, 600),
      GAME(SoundEvents.field_187669_dB, 12000, 24000),
      CREATIVE(SoundEvents.field_187792_dx, 1200, 3600),
      CREDITS(SoundEvents.field_187794_dy, 0, 0),
      NETHER(SoundEvents.field_187673_dD, 1200, 3600),
      END_BOSS(SoundEvents.field_187796_dz, 0, 0),
      END(SoundEvents.field_187667_dA, 6000, 24000);

      private final SoundEvent field_148645_h;
      private final int field_148646_i;
      private final int field_148643_j;

      private MusicType(SoundEvent p_i47050_3_, int p_i47050_4_, int p_i47050_5_) {
         this.field_148645_h = p_i47050_3_;
         this.field_148646_i = p_i47050_4_;
         this.field_148643_j = p_i47050_5_;
      }

      public SoundEvent func_188768_a() {
         return this.field_148645_h;
      }

      public int func_148634_b() {
         return this.field_148646_i;
      }

      public int func_148633_c() {
         return this.field_148643_j;
      }
   }
}
