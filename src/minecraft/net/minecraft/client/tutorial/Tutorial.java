package net.minecraft.client.tutorial;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraft.world.GameType;

public class Tutorial
{
    private final Minecraft minecraft;
    @Nullable
    private ITutorialStep tutorialStep;

    public Tutorial(Minecraft minecraft)
    {
        this.minecraft = minecraft;
    }

    public void handleMovement(MovementInput p_193293_1_)
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.handleMovement(p_193293_1_);
        }
    }

    public void handleMouse(MouseHelper p_193299_1_)
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.handleMouse(p_193299_1_);
        }
    }

    public void onMouseHover(@Nullable WorldClient worldIn, @Nullable RayTraceResult result)
    {
        if (this.tutorialStep != null && result != null && worldIn != null)
        {
            this.tutorialStep.onMouseHover(worldIn, result);
        }
    }

    public void onHitBlock(WorldClient worldIn, BlockPos pos, IBlockState state, float diggingStage)
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.onHitBlock(worldIn, pos, state, diggingStage);
        }
    }

    /**
     * Called when the player opens his inventory
     */
    public void openInventory()
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.openInventory();
        }
    }

    /**
     * Called when the player pick up an ItemStack
     *  
     * @param stack The ItemStack
     */
    public void handleSetSlot(ItemStack stack)
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.handleSetSlot(stack);
        }
    }

    public void stop()
    {
        if (this.tutorialStep != null)
        {
            this.tutorialStep.onStop();
            this.tutorialStep = null;
        }
    }

    /**
     * Reloads the tutorial step from the game settings
     */
    public void reload()
    {
        if (this.tutorialStep != null)
        {
            this.stop();
        }

        this.tutorialStep = this.minecraft.gameSettings.tutorialStep.create(this);
    }

    public void update()
    {
        if (this.tutorialStep != null)
        {
            if (this.minecraft.world != null)
            {
                this.tutorialStep.update();
            }
            else
            {
                this.stop();
            }
        }
        else if (this.minecraft.world != null)
        {
            this.reload();
        }
    }

    /**
     * Sets a new step to the tutorial
     */
    public void setStep(TutorialSteps step)
    {
        this.minecraft.gameSettings.tutorialStep = step;
        this.minecraft.gameSettings.saveOptions();

        if (this.tutorialStep != null)
        {
            this.tutorialStep.onStop();
            this.tutorialStep = step.create(this);
        }
    }

    public Minecraft getMinecraft()
    {
        return this.minecraft;
    }

    public GameType getGameType()
    {
        return this.minecraft.playerController == null ? GameType.NOT_SET : this.minecraft.playerController.getCurrentGameType();
    }

    public static ITextComponent createKeybindComponent(String keybind)
    {
        TextComponentKeybind textcomponentkeybind = new TextComponentKeybind("key." + keybind);
        textcomponentkeybind.getStyle().setBold(Boolean.valueOf(true));
        return textcomponentkeybind;
    }
}
