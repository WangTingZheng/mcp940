package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.util.text.ITextComponent;

public interface ISpectatorMenuView
{
    List<ISpectatorMenuObject> getItems();

    ITextComponent getPrompt();
}
