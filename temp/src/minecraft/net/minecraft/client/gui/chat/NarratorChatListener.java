package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class NarratorChatListener implements IChatListener {
   public static final NarratorChatListener field_193643_a = new NarratorChatListener();
   private final Narrator field_192580_a = Narrator.getNarrator();

   public void func_192576_a(ChatType p_192576_1_, ITextComponent p_192576_2_) {
      int i = Minecraft.func_71410_x().field_71474_y.field_192571_R;
      if (i != 0 && this.field_192580_a.active()) {
         if (i == 1 || i == 2 && p_192576_1_ == ChatType.CHAT || i == 3 && p_192576_1_ == ChatType.SYSTEM) {
            if (p_192576_2_ instanceof TextComponentTranslation && "chat.type.text".equals(((TextComponentTranslation)p_192576_2_).func_150268_i())) {
               this.field_192580_a.say((new TextComponentTranslation("chat.type.text.narrate", ((TextComponentTranslation)p_192576_2_).func_150271_j())).func_150260_c());
            } else {
               this.field_192580_a.say(p_192576_2_.func_150260_c());
            }
         }

      }
   }

   public void func_193641_a(int p_193641_1_) {
      this.field_192580_a.clear();
      this.field_192580_a.say((new TextComponentTranslation("options.narrator", new Object[0])).func_150260_c() + " : " + (new TextComponentTranslation(GameSettings.field_193632_b[p_193641_1_], new Object[0])).func_150260_c());
      GuiToast guitoast = Minecraft.func_71410_x().func_193033_an();
      if (this.field_192580_a.active()) {
         if (p_193641_1_ == 0) {
            SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, new TextComponentTranslation("narrator.toast.disabled", new Object[0]), (ITextComponent)null);
         } else {
            SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, new TextComponentTranslation("narrator.toast.enabled", new Object[0]), new TextComponentTranslation(GameSettings.field_193632_b[p_193641_1_], new Object[0]));
         }
      } else {
         SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, new TextComponentTranslation("narrator.toast.disabled", new Object[0]), new TextComponentTranslation("options.narrator.notavailable", new Object[0]));
      }

   }

   public boolean func_193640_a() {
      return this.field_192580_a.active();
   }

   public void func_193642_b() {
      this.field_192580_a.clear();
   }
}
