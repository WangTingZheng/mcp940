package net.minecraft.client.gui.spectator;

import net.minecraft.util.text.ITextComponent;

public interface ISpectatorMenuObject
{
    void selectItem(SpectatorMenu menu);

    ITextComponent getSpectatorName();

    void renderIcon(float brightness, int alpha);

    boolean isEnabled();
}
