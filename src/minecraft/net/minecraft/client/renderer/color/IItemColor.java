package net.minecraft.client.renderer.color;

import net.minecraft.item.ItemStack;

public interface IItemColor
{
    int getColorFromItemstack(ItemStack stack, int tintIndex);
}
