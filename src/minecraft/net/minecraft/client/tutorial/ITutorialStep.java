package net.minecraft.client.tutorial;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public interface ITutorialStep
{
default void onStop()
    {
    }

default void update()
    {
    }

default void handleMovement(MovementInput input)
    {
    }

default void handleMouse(MouseHelper mouseHelperIn)
    {
    }

default void onMouseHover(WorldClient worldIn, RayTraceResult result)
    {
    }

default void onHitBlock(WorldClient worldIn, BlockPos pos, IBlockState state, float diggingStage)
    {
    }

default void openInventory()
    {
    }

default void handleSetSlot(ItemStack stack)
    {
    }
}
