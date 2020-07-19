package net.minecraft.tileentity;

import net.minecraft.util.EnumFacing;

public class TileEntityEndPortal extends TileEntity
{
    public boolean shouldRenderFace(EnumFacing p_184313_1_)
    {
        return p_184313_1_ == EnumFacing.UP;
    }
}
