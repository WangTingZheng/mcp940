package net.minecraft.client.tutorial;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class CraftPlanksStep implements ITutorialStep
{
    private static final ITextComponent TITLE = new TextComponentTranslation("tutorial.craft_planks.title", new Object[0]);
    private static final ITextComponent DESCRIPTION = new TextComponentTranslation("tutorial.craft_planks.description", new Object[0]);
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public CraftPlanksStep(Tutorial tutorial)
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
            if (this.timeWaiting == 1)
            {
                EntityPlayerSP entityplayersp = this.tutorial.getMinecraft().player;

                if (entityplayersp != null)
                {
                    if (entityplayersp.inventory.hasItemStack(new ItemStack(Blocks.PLANKS)))
                    {
                        this.tutorial.setStep(TutorialSteps.NONE);
                        return;
                    }

                    if (didPlayerCraftedPlanks(entityplayersp))
                    {
                        this.tutorial.setStep(TutorialSteps.NONE);
                        return;
                    }
                }
            }

            if (this.timeWaiting >= 1200 && this.toast == null)
            {
                this.toast = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, TITLE, DESCRIPTION, false);
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
     * Called when the player pick up an ItemStack
     *  
     * @param stack The ItemStack
     */
    public void handleSetSlot(ItemStack stack)
    {
        if (stack.getItem() == Item.getItemFromBlock(Blocks.PLANKS))
        {
            this.tutorial.setStep(TutorialSteps.NONE);
        }
    }

    /**
     * Indicates if the players crafted at least one time planks.
     *  
     * @param player The player
     */
    public static boolean didPlayerCraftedPlanks(EntityPlayerSP player)
    {
        StatBase statbase = StatList.getCraftStats(Item.getItemFromBlock(Blocks.PLANKS));
        return statbase != null && player.getStatFileWriter().readStat(statbase) > 0;
    }
}
