package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton
{
    private boolean value;

    /** The localization string used by this control. */
    private final String localizationStr;

    /** The GuiResponder Object reference. */
    private final GuiPageButtonList.GuiResponder guiResponder;

    public GuiListButton(GuiPageButtonList.GuiResponder responder, int buttonId, int x, int y, String localizationStrIn, boolean valueIn)
    {
        super(buttonId, x, y, 150, 20, "");
        this.localizationStr = localizationStrIn;
        this.value = valueIn;
        this.displayString = this.buildDisplayString();
        this.guiResponder = responder;
    }

    /**
     * Builds the localized display string for this GuiListButton
     */
    private String buildDisplayString()
    {
        return I18n.format(this.localizationStr) + ": " + I18n.format(this.value ? "gui.yes" : "gui.no");
    }

    public void setValue(boolean valueIn)
    {
        this.value = valueIn;
        this.displayString = this.buildDisplayString();
        this.guiResponder.setEntryValue(this.id, valueIn);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.value = !this.value;
            this.displayString = this.buildDisplayString();
            this.guiResponder.setEntryValue(this.id, this.value);
            return true;
        }
        else
        {
            return false;
        }
    }
}
