package net.minecraft.client.renderer;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public interface ItemMeshDefinition
{
    ModelResourceLocation getModelLocation(ItemStack stack);
}
