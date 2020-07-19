package net.minecraft.client.gui.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class NormalChatListener implements IChatListener {
   private final Minecraft field_192581_a;

   public NormalChatListener(Minecraft p_i47393_1_) {
      this.field_192581_a = p_i47393_1_;
   }

   public void func_192576_a(ChatType p_192576_1_, ITextComponent p_192576_2_) {
      this.field_192581_a.field_71456_v.func_146158_b().func_146227_a(p_192576_2_);
   }
}
