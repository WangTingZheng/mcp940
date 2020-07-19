package net.minecraft.world.storage;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData
{
    /**
     * Writes the player data to disk from the specified PlayerEntityMP.
     */
    void writePlayerData(EntityPlayer player);

    @Nullable

    /**
     * Reads the player data from disk into the specified PlayerEntityMP.
     */
    NBTTagCompound readPlayerData(EntityPlayer player);

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    String[] getAvailablePlayerDat();
}
