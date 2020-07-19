package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.Snooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.src.Reflector;
import net.minecraft.src.WorldServerOF;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Minecraft instance. */
    private final Minecraft mc;
    private final WorldSettings worldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;

    public IntegratedServer(Minecraft clientIn, String folderNameIn, String worldNameIn, WorldSettings worldSettingsIn, YggdrasilAuthenticationService authServiceIn, MinecraftSessionService sessionServiceIn, GameProfileRepository profileRepoIn, PlayerProfileCache profileCacheIn)
    {
        super(new File(clientIn.mcDataDir, "saves"), clientIn.getProxy(), clientIn.getDataFixer(), authServiceIn, sessionServiceIn, profileRepoIn, profileCacheIn);
        this.setServerOwner(clientIn.getSession().getUsername());
        this.setFolderName(folderNameIn);
        this.setWorldName(worldNameIn);
        this.setDemo(clientIn.isDemo());
        this.canCreateBonusChest(worldSettingsIn.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setPlayerList(new IntegratedPlayerList(this));
        this.mc = clientIn;
        this.worldSettings = this.isDemo() ? WorldServerDemo.DEMO_WORLD_SETTINGS : worldSettingsIn;
    }

    public ServerCommandManager createCommandManager()
    {
        return new IntegratedServerCommandManager(this);
    }

    public void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions)
    {
        this.convertMapIfNeeded(saveName);
        ISaveHandler isavehandler = this.getActiveAnvilConverter().getSaveLoader(saveName, true);
        this.setResourcePackFromWorld(this.getFolderName(), isavehandler);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();

        if (Reflector.DimensionManager.exists())
        {
            WorldServer worldserver = this.isDemo() ? (WorldServer)((WorldServer)(new WorldServerDemo(this, isavehandler, worldinfo, 0, this.profiler)).init()) : (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, 0, this.profiler)).init();
            worldserver.initialize(this.worldSettings);
            Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs);
            Integer[] ainteger1 = ainteger;
            int i1 = ainteger.length;

            for (int j1 = 0; j1 < i1; ++j1)
            {
                int k = ainteger1[j1].intValue();
                WorldServer worldserver1 = k == 0 ? worldserver : (WorldServer)((WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.profiler)).init());
                worldserver1.addEventListener(new ServerWorldEventHandler(this, worldserver1));

                if (!this.isSinglePlayer())
                {
                    worldserver1.getWorldInfo().setGameType(this.getGameType());
                }

                if (Reflector.EventBus.exists())
                {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, worldserver1);
                }
            }

            this.getPlayerList().setPlayerManager(new WorldServer[] {worldserver});

            if (worldserver.getWorldInfo().getDifficulty() == null)
            {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        else
        {
            this.worlds = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worlds.length][100];
            this.setResourcePackFromWorld(this.getFolderName(), isavehandler);

            if (worldinfo == null)
            {
                worldinfo = new WorldInfo(this.worldSettings, worldNameIn);
            }
            else
            {
                worldinfo.setWorldName(worldNameIn);
            }

            for (int l = 0; l < this.worlds.length; ++l)
            {
                int i1 = 0;

                if (l == 1)
                {
                    i1 = -1;
                }

                if (l == 2)
                {
                    i1 = 1;
                }

                if (l == 0)
                {
                    if (this.isDemo())
                    {
                        this.worlds[l] = (WorldServer)(new WorldServerDemo(this, isavehandler, worldinfo, i1, this.profiler)).init();
                    }
                    else
                    {
                        this.worlds[l] = (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, i1, this.profiler)).init();
                    }

                    this.worlds[l].initialize(this.worldSettings);
                }
                else
                {
                    this.worlds[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, i1, this.worlds[0], this.profiler)).init();
                }

                this.worlds[l].addEventListener(new ServerWorldEventHandler(this, this.worlds[l]));
            }

            this.getPlayerList().setPlayerManager(this.worlds);

            if (this.worlds[0].getWorldInfo().getDifficulty() == null)
            {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }

        this.initialWorldChunkLoad();
    }

    /**
     * Initialises the server and starts it.
     */
    public boolean init() throws IOException
    {
        LOGGER.info("Starting integrated minecraft server version 1.12");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        LOGGER.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());

        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists())
        {
            Object object = Reflector.call(Reflector.FMLCommonHandler_instance);

            if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, this))
            {
                return false;
            }
        }

        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.worldSettings.getSeed(), this.worldSettings.getTerrainType(), this.worldSettings.getGeneratorOptions());
        this.setMOTD(this.getServerOwner() + " - " + this.worlds[0].getWorldInfo().getWorldName());

        if (Reflector.FMLCommonHandler_handleServerStarting.exists())
        {
            Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance);

            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE)
            {
                return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, this);
            }

            Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, this);
        }

        return true;
    }

    /**
     * Main function called by run() every loop.
     */
    public void tick()
    {
        boolean flag = this.isGamePaused;
        this.isGamePaused = Minecraft.getMinecraft().getConnection() != null && Minecraft.getMinecraft().isGamePaused();

        if (!flag && this.isGamePaused)
        {
            LOGGER.info("Saving and pausing game...");
            this.getPlayerList().saveAllPlayerData();
            this.saveAllWorlds(false);
        }

        if (this.isGamePaused)
        {
            synchronized (this.futureTaskQueue)
            {
                while (!this.futureTaskQueue.isEmpty())
                {
                    Util.runTask(this.futureTaskQueue.poll(), LOGGER);
                }
            }
        }
        else
        {
            super.tick();

            if (this.mc.gameSettings.renderDistanceChunks != this.getPlayerList().getViewDistance())
            {
                LOGGER.info("Changing view distance to {}, from {}", Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(this.getPlayerList().getViewDistance()));
                this.getPlayerList().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
            }

            if (this.mc.world != null)
            {
                WorldInfo worldinfo1 = this.worlds[0].getWorldInfo();
                WorldInfo worldinfo = this.mc.world.getWorldInfo();

                if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty())
                {
                    LOGGER.info("Changing difficulty to {}, from {}", worldinfo.getDifficulty(), worldinfo1.getDifficulty());
                    this.setDifficultyForAllWorlds(worldinfo.getDifficulty());
                }
                else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked())
                {
                    LOGGER.info("Locking difficulty to {}", (Object)worldinfo.getDifficulty());

                    for (WorldServer worldserver : this.worlds)
                    {
                        if (worldserver != null)
                        {
                            worldserver.getWorldInfo().setDifficultyLocked(true);
                        }
                    }
                }
            }
        }
    }

    public boolean canStructuresSpawn()
    {
        return false;
    }

    public GameType getGameType()
    {
        return this.worldSettings.getGameType();
    }

    /**
     * Get the server's difficulty
     */
    public EnumDifficulty getDifficulty()
    {
        return this.mc.world == null ? this.mc.gameSettings.difficulty : this.mc.world.getWorldInfo().getDifficulty();
    }

    /**
     * Defaults to false.
     */
    public boolean isHardcore()
    {
        return this.worldSettings.getHardcoreEnabled();
    }

    /**
     * Get if RCON command events should be broadcast to ops
     */
    public boolean shouldBroadcastRconToOps()
    {
        return true;
    }

    /**
     * Get if console command events should be broadcast to ops
     */
    public boolean shouldBroadcastConsoleToOps()
    {
        return true;
    }

    /**
     * par1 indicates if a log message should be output.
     */
    public void saveAllWorlds(boolean isSilent)
    {
        super.saveAllWorlds(isSilent);
    }

    public File getDataDirectory()
    {
        return this.mc.mcDataDir;
    }

    public boolean isDedicatedServer()
    {
        return false;
    }

    /**
     * Get if native transport should be used. Native transport means linux server performance improvements and
     * optimized packet sending/receiving on linux
     */
    public boolean shouldUseNativeTransport()
    {
        return false;
    }

    /**
     * Called on exit from the main run() loop.
     */
    public void finalTick(CrashReport report)
    {
        this.mc.crashed(report);
    }

    /**
     * Adds the server info, including from theWorldServer, to the crash report.
     */
    public CrashReport addServerInfoToCrashReport(CrashReport report)
    {
        report = super.addServerInfoToCrashReport(report);
        report.getCategory().addDetail("Type", new ICrashReportDetail<String>()
        {
            public String call() throws Exception
            {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.getCategory().addDetail("Is Modded", new ICrashReportDetail<String>()
        {
            public String call() throws Exception
            {
                String s = ClientBrandRetriever.getClientModName();

                if (!s.equals("vanilla"))
                {
                    return "Definitely; Client brand changed to '" + s + "'";
                }
                else
                {
                    s = IntegratedServer.this.getServerModName();

                    if (!"vanilla".equals(s))
                    {
                        return "Definitely; Server brand changed to '" + s + "'";
                    }
                    else
                    {
                        return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.";
                    }
                }
            }
        });
        return report;
    }

    public void setDifficultyForAllWorlds(EnumDifficulty difficulty)
    {
        super.setDifficultyForAllWorlds(difficulty);

        if (this.mc.world != null)
        {
            this.mc.world.getWorldInfo().setDifficulty(difficulty);
        }
    }

    public void addServerStatsToSnooper(Snooper playerSnooper)
    {
        super.addServerStatsToSnooper(playerSnooper);
        playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    public boolean isSnooperEnabled()
    {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }

    /**
     * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
     */
    public String shareToLAN(GameType type, boolean allowCheats)
    {
        try
        {
            int i = -1;

            try
            {
                i = HttpUtil.getSuitableLanPort();
            }
            catch (IOException var5)
            {
                ;
            }

            if (i <= 0)
            {
                i = 25564;
            }

            this.getNetworkSystem().addLanEndpoint((InetAddress)null, i);
            LOGGER.info("Started on {}", (int)i);
            this.isPublic = true;
            this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), i + "");
            this.lanServerPing.start();
            this.getPlayerList().setGameType(type);
            this.getPlayerList().setCommandsAllowedForAll(allowCheats);
            this.mc.player.setPermissionLevel(allowCheats ? 4 : 0);
            return i + "";
        }
        catch (IOException var61)
        {
            return null;
        }
    }

    /**
     * Saves all necessary data as preparation for stopping the server.
     */
    public void stopServer()
    {
        super.stopServer();

        if (this.lanServerPing != null)
        {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    /**
     * Sets the serverRunning variable to false, in order to get the server to shut down.
     */
    public void initiateShutdown()
    {
        if (!Reflector.MinecraftForge.exists() || this.isServerRunning())
        {
            Futures.getUnchecked(this.addScheduledTask(new Runnable()
            {
                public void run()
                {
                    for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getPlayerList().getPlayers()))
                    {
                        if (!entityplayermp.getUniqueID().equals(IntegratedServer.this.mc.player.getUniqueID()))
                        {
                            IntegratedServer.this.getPlayerList().playerLoggedOut(entityplayermp);
                        }
                    }
                }
            }));
        }

        super.initiateShutdown();

        if (this.lanServerPing != null)
        {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    /**
     * Returns true if this integrated server is open to LAN
     */
    public boolean getPublic()
    {
        return this.isPublic;
    }

    /**
     * Sets the game type for all worlds.
     */
    public void setGameType(GameType gameMode)
    {
        super.setGameType(gameMode);
        this.getPlayerList().setGameType(gameMode);
    }

    /**
     * Return whether command blocks are enabled.
     */
    public boolean isCommandBlockEnabled()
    {
        return true;
    }

    public int getOpPermissionLevel()
    {
        return 4;
    }
}
