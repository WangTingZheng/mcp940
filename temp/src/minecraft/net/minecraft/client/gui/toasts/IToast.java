package net.minecraft.client.gui.toasts;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public interface IToast {
   ResourceLocation field_193654_a = new ResourceLocation("textures/gui/toasts.png");
   Object field_193655_b = new Object();

   IToast.Visibility func_193653_a(GuiToast var1, long var2);

   default Object func_193652_b() {
      return field_193655_b;
   }

   public static enum Visibility {
      SHOW(SoundEvents.field_194226_id),
      HIDE(SoundEvents.field_194227_ie);

      private final SoundEvent field_194170_c;

      private Visibility(SoundEvent p_i47607_3_) {
         this.field_194170_c = p_i47607_3_;
      }

      public void func_194169_a(SoundHandler p_194169_1_) {
         p_194169_1_.func_147682_a(PositionedSoundRecord.func_194007_a(this.field_194170_c, 1.0F, 1.0F));
      }
   }
}
