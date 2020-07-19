package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class FindTreeStep implements ITutorialStep
{
    private static final Set<Block> TREE_BLOCKS = Sets.newHashSet(Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2);
    private static final ITextComponent TITLE = new TextComponentTranslation("tutorial.find_tree.title", new Object[0]);
    private static final ITextComponent DESCRIPTION = new TextComponentTranslation("tutorial.find_tree.description", new Object[0]);
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public FindTreeStep(Tutorial tutorial)
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
                    for (Block block : TREE_BLOCKS)
                    {
                        if (entityplayersp.inventory.hasItemStack(new ItemStack(block)))
                        {
                            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                            return;
                        }
                    }

                    if (hasPunchedTreesPreviously(entityplayersp))
                    {
                        this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                        return;
                    }
                }
            }

            if (this.timeWaiting >= 6000 && this.toast == null)
            {
                this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, false);
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
     * Handles blocks and entities hovering
     *  
     * @param worldIn The world on the client side, can be null
     * @param result The result of the ray trace
     */
    public void onMouseHover(WorldClient worldIn, RayTraceResult result)
    {
        if (result.typeOfHit == RayTraceResult.Type.BLOCK && result.getBlockPos() != null)
        {
            IBlockState iblockstate = worldIn.getBlockState(result.getBlockPos());

            if (TREE_BLOCKS.contains(iblockstate.getBlock()))
            {
                this.tutorial.setStep(TutorialSteps.PUNCH_TREE);
            }
        }
    }

    /**
     * Called when the player pick up an ItemStack
     *  
     * @param stack The ItemStack
     */
    public void handleSetSlot(ItemStack stack)
    {
        for (Block block : TREE_BLOCKS)
        {
            if (stack.getItem() == Item.getItemFromBlock(block))
            {
                this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                return;
            }
        }
    }

    public static boolean hasPunchedTreesPreviously(EntityPlayerSP p_194070_0_)
    {
        for (Block block : TREE_BLOCKS)
        {
            StatBase statbase = StatList.getBlockStats(block);

            if (statbase != null && p_194070_0_.getStatFileWriter().readStat(statbase) > 0)
            {
                return true;
            }
        }

        return false;
    }
}
