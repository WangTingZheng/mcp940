package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper extends IInventory
{
    /**
     * Returns the worldObj for this tileEntity.
     */
    World getWorld();

    /**
     * Gets the world X position for this hopper entity.
     */
    double getXPos();

    /**
     * Gets the world Y position for this hopper entity.
     */
    double getYPos();

    /**
     * Gets the world Z position for this hopper entity.
     */
    double getZPos();
}
