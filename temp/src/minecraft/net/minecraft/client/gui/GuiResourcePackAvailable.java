package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;

public class GuiResourcePackAvailable extends GuiResourcePackList {
   public GuiResourcePackAvailable(Minecraft p_i45054_1_, int p_i45054_2_, int p_i45054_3_, List<ResourcePackListEntry> p_i45054_4_) {
      super(p_i45054_1_, p_i45054_2_, p_i45054_3_, p_i45054_4_);
   }

   protected String func_148202_k() {
      return I18n.func_135052_a("resourcePack.available.title");
   }
}
