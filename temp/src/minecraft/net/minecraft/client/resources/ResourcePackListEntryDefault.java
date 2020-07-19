package net.minecraft.client.resources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryDefault extends ResourcePackListEntryServer {
   public ResourcePackListEntryDefault(GuiScreenResourcePacks p_i45052_1_) {
      super(p_i45052_1_, Minecraft.func_71410_x().func_110438_M().field_110620_b);
   }

   protected String func_148312_b() {
      return "Default";
   }

   public boolean func_186768_j() {
      return false;
   }
}
