package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerDemo extends WorldServer
{
    private static final long DEMO_WORLD_SEED = (long)"North Carolina".hashCode();
    public static final WorldSettings DEMO_WORLD_SETTINGS = (new WorldSettings(DEMO_WORLD_SEED, GameType.SURVIVAL, true, false, WorldType.DEFAULT)).enableBonusChest();

    public WorldServerDemo(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo worldInfoIn, int dimensionId, Profiler profilerIn)
    {
        super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
        this.worldInfo.populateFromWorldSettings(DEMO_WORLD_SETTINGS);
    }
}
