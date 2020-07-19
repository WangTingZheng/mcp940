package net.minecraft.world.storage;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo extends WorldInfo
{
    /** Instance of WorldInfo. */
    private final WorldInfo delegate;

    public DerivedWorldInfo(WorldInfo worldInfoIn)
    {
        this.delegate = worldInfoIn;
    }

    /**
     * Creates a new NBTTagCompound for the world, with the given NBTTag as the "Player"
     */
    public NBTTagCompound cloneNBTCompound(@Nullable NBTTagCompound nbt)
    {
        return this.delegate.cloneNBTCompound(nbt);
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return this.delegate.getSeed();
    }

    /**
     * Returns the x spawn position
     */
    public int getSpawnX()
    {
        return this.delegate.getSpawnX();
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int getSpawnY()
    {
        return this.delegate.getSpawnY();
    }

    /**
     * Returns the z spawn position
     */
    public int getSpawnZ()
    {
        return this.delegate.getSpawnZ();
    }

    public long getWorldTotalTime()
    {
        return this.delegate.getWorldTotalTime();
    }

    /**
     * Get current world time
     */
    public long getWorldTime()
    {
        return this.delegate.getWorldTime();
    }

    public long getSizeOnDisk()
    {
        return this.delegate.getSizeOnDisk();
    }

    /**
     * Returns the player's NBTTagCompound to be loaded
     */
    public NBTTagCompound getPlayerNBTTagCompound()
    {
        return this.delegate.getPlayerNBTTagCompound();
    }

    /**
     * Get current world name
     */
    public String getWorldName()
    {
        return this.delegate.getWorldName();
    }

    /**
     * Returns the save version of this world
     */
    public int getSaveVersion()
    {
        return this.delegate.getSaveVersion();
    }

    /**
     * Return the last time the player was in this world.
     */
    public long getLastTimePlayed()
    {
        return this.delegate.getLastTimePlayed();
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    public boolean isThundering()
    {
        return this.delegate.isThundering();
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    public int getThunderTime()
    {
        return this.delegate.getThunderTime();
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    public boolean isRaining()
    {
        return this.delegate.isRaining();
    }

    /**
     * Return the number of ticks until rain.
     */
    public int getRainTime()
    {
        return this.delegate.getRainTime();
    }

    /**
     * Gets the GameType.
     */
    public GameType getGameType()
    {
        return this.delegate.getGameType();
    }

    /**
     * Set the x spawn position to the passed in value
     */
    public void setSpawnX(int x)
    {
    }

    /**
     * Sets the y spawn position
     */
    public void setSpawnY(int y)
    {
    }

    /**
     * Set the z spawn position to the passed in value
     */
    public void setSpawnZ(int z)
    {
    }

    public void setWorldTotalTime(long time)
    {
    }

    /**
     * Set current world time
     */
    public void setWorldTime(long time)
    {
    }

    public void setSpawn(BlockPos spawnPoint)
    {
    }

    public void setWorldName(String worldName)
    {
    }

    /**
     * Sets the save version of the world
     */
    public void setSaveVersion(int version)
    {
    }

    /**
     * Sets whether it is thundering or not.
     */
    public void setThundering(boolean thunderingIn)
    {
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    public void setThunderTime(int time)
    {
    }

    /**
     * Sets whether it is raining or not.
     */
    public void setRaining(boolean isRaining)
    {
    }

    /**
     * Sets the number of ticks until rain.
     */
    public void setRainTime(int time)
    {
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return this.delegate.isMapFeaturesEnabled();
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcoreModeEnabled()
    {
        return this.delegate.isHardcoreModeEnabled();
    }

    public WorldType getTerrainType()
    {
        return this.delegate.getTerrainType();
    }

    public void setTerrainType(WorldType type)
    {
    }

    /**
     * Returns true if commands are allowed on this World.
     */
    public boolean areCommandsAllowed()
    {
        return this.delegate.areCommandsAllowed();
    }

    public void setAllowCommands(boolean allow)
    {
    }

    /**
     * Returns true if the World is initialized.
     */
    public boolean isInitialized()
    {
        return this.delegate.isInitialized();
    }

    /**
     * Sets the initialization status of the World.
     */
    public void setServerInitialized(boolean initializedIn)
    {
    }

    /**
     * Gets the GameRules class Instance.
     */
    public GameRules getGameRulesInstance()
    {
        return this.delegate.getGameRulesInstance();
    }

    public EnumDifficulty getDifficulty()
    {
        return this.delegate.getDifficulty();
    }

    public void setDifficulty(EnumDifficulty newDifficulty)
    {
    }

    public boolean isDifficultyLocked()
    {
        return this.delegate.isDifficultyLocked();
    }

    public void setDifficultyLocked(boolean locked)
    {
    }

    public void setDimensionData(DimensionType dimensionIn, NBTTagCompound compound)
    {
        this.delegate.setDimensionData(dimensionIn, compound);
    }

    public NBTTagCompound getDimensionData(DimensionType dimensionIn)
    {
        return this.delegate.getDimensionData(dimensionIn);
    }
}
