package net.minecraft.client.tutorial;

import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class OpenInventoryStep implements ITutorialStep
{
    private static final ITextComponent TITLE = new TextComponentTranslation("tutorial.open_inventory.title", new Object[0]);
    private static final ITextComponent DESCRIPTION = new TextComponentTranslation("tutorial.open_inventory.description", new Object[] {Tutorial.createKeybindComponent("inventory")});
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public OpenInventoryStep(Tutorial tutorial)
    {
        this.tutorial = tutorial;
    }

    public void update()
    {
        ++this.timeWaiting;

        if (this.tutorial.getGameType() != GameType.SURVIVAL)
        {
            this.tutorial.setStep(TutorialSteps.NONE);
        }
        else
        {
            if (this.timeWaiting >= 600 && this.toast == null)
            {
                this.toast = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, TITLE, DESCRIPTION, false);
                this.tutorial.getMinecraft().getToastGui().add(this.toast);
            }
        }
    }

    public void onStop()
    {
        if (this.toast != null)
        {
            this.toast.hide();
            this.toast = null;
        }
    }

    /**
     * Called when the player opens his inventory
     */
    public void openInventory()
    {
        this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
    }
}
