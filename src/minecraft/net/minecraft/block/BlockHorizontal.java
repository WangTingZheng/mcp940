package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public abstract class BlockHorizontal extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected BlockHorizontal(Material materialIn)
    {
        super(materialIn);
    }

    protected BlockHorizontal(Material materialIn, MapColor colorIn)
    {
        super(materialIn, colorIn);
    }
}
