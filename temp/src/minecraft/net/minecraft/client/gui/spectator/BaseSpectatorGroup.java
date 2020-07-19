package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class BaseSpectatorGroup implements ISpectatorMenuView {
   private final List<ISpectatorMenuObject> field_178671_a = Lists.<ISpectatorMenuObject>newArrayList();

   public BaseSpectatorGroup() {
      this.field_178671_a.add(new TeleportToPlayer());
      this.field_178671_a.add(new TeleportToTeam());
   }

   public List<ISpectatorMenuObject> func_178669_a() {
      return this.field_178671_a;
   }

   public ITextComponent func_178670_b() {
      return new TextComponentTranslation("spectatorMenu.root.prompt", new Object[0]);
   }
}
