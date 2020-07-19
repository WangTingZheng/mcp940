package net.minecraft.src;

import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerOF extends WorldServer
{
    private MinecraftServer mcServer;

    public WorldServerOF(MinecraftServer p_i97_1_, ISaveHandler p_i97_2_, WorldInfo p_i97_3_, int p_i97_4_, Profiler p_i97_5_)
    {
        super(p_i97_1_, p_i97_2_, p_i97_3_, p_i97_4_, p_i97_5_);
        this.mcServer = p_i97_1_;
    }

    /**
     * Runs a single tick for the world
     */
    public void tick()
    {
        super.tick();

        if (!Config.isTimeDefault())
        {
            this.fixWorldTime();
        }

        if (Config.waterOpacityChanged)
        {
            Config.waterOpacityChanged = false;
            ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
        }
    }

    /**
     * Updates all weather states.
     */
    protected void updateWeather()
    {
        if (!Config.isWeatherEnabled())
        {
            this.fixWorldWeather();
        }

        super.updateWeather();
    }

    private void fixWorldWeather()
    {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering())
        {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0F);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.setThunderStrength(0.0F);
            this.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketChangeGameState(2, 0.0F));
            this.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketChangeGameState(7, 0.0F));
            this.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketChangeGameState(8, 0.0F));
        }
    }

    private void fixWorldTime()
    {
        if (this.worldInfo.getGameType().getID() == 1)
        {
            long i = this.getWorldTime();
            long j = i % 24000L;

            if (Config.isTimeDayOnly())
            {
                if (j <= 1000L)
                {
                    this.setWorldTime(i - j + 1001L);
                }

                if (j >= 11000L)
                {
                    this.setWorldTime(i - j + 24001L);
                }
            }

            if (Config.isTimeNightOnly())
            {
                if (j <= 14000L)
                {
                    this.setWorldTime(i - j + 14001L);
                }

                if (j >= 22000L)
                {
                    this.setWorldTime(i - j + 24000L + 14001L);
                }
            }
        }
    }
}
