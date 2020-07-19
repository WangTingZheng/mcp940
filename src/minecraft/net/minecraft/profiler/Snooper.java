package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.HttpUtil;

public class Snooper
{
    private final Map<String, Object> snooperStats = Maps.<String, Object>newHashMap();
    private final Map<String, Object> clientStats = Maps.<String, Object>newHashMap();
    private final String uniqueID = UUID.randomUUID().toString();

    /** URL of the server to send the report to */
    private final URL serverUrl;
    private final ISnooperInfo playerStatsCollector;

    /** set to fire the snooperThread every 15 mins */
    private final Timer threadTrigger = new Timer("Snooper Timer", true);
    private final Object syncLock = new Object();
    private final long minecraftStartTimeMilis;
    private boolean isRunning;

    /** incremented on every getSelfCounterFor */
    private int selfCounter;

    public Snooper(String side, ISnooperInfo playerStatCollector, long startTime)
    {
        try
        {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + side + "?version=" + 2);
        }
        catch (MalformedURLException var6)
        {
            throw new IllegalArgumentException();
        }

        this.playerStatsCollector = playerStatCollector;
        this.minecraftStartTimeMilis = startTime;
    }

    /**
     * Note issuing start multiple times is not an error.
     */
    public void startSnooper()
    {
        if (!this.isRunning)
        {
            this.isRunning = true;
            this.addOSData();
            this.threadTrigger.schedule(new TimerTask()
            {
                public void run()
                {
                    if (Snooper.this.playerStatsCollector.isSnooperEnabled())
                    {
                        Map<String, Object> map;

                        synchronized (Snooper.this.syncLock)
                        {
                            map = Maps.<String, Object>newHashMap(Snooper.this.clientStats);

                            if (Snooper.this.selfCounter == 0)
                            {
                                map.putAll(Snooper.this.snooperStats);
                            }

                            map.put("snooper_count", Integer.valueOf(Snooper.this.selfCounter++));
                            map.put("snooper_token", Snooper.this.uniqueID);
                        }

                        MinecraftServer minecraftserver = Snooper.this.playerStatsCollector instanceof MinecraftServer ? (MinecraftServer)Snooper.this.playerStatsCollector : null;
                        HttpUtil.postMap(Snooper.this.serverUrl, map, true, minecraftserver == null ? null : minecraftserver.getServerProxy());
                    }
                }
            }, 0L, 900000L);
        }
    }

    /**
     * Add OS data into the snooper
     */
    private void addOSData()
    {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", System.getProperty("os.name"));
        this.addStatToSnooper("os_version", System.getProperty("os.version"));
        this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
        this.addStatToSnooper("java_version", System.getProperty("java.version"));
        this.addClientStat("version", "1.12");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    private void addJvmArgsToSnooper()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List<String> list = runtimemxbean.getInputArguments();
        int i = 0;

        for (String s : list)
        {
            if (s.startsWith("-X"))
            {
                this.addClientStat("jvm_arg[" + i++ + "]", s);
            }
        }

        this.addClientStat("jvm_args", Integer.valueOf(i));
    }

    public void addMemoryStatsToSnooper()
    {
        this.addStatToSnooper("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.addStatToSnooper("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.addStatToSnooper("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.addStatToSnooper("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    public void addClientStat(String statName, Object statValue)
    {
        synchronized (this.syncLock)
        {
            this.clientStats.put(statName, statValue);
        }
    }

    public void addStatToSnooper(String statName, Object statValue)
    {
        synchronized (this.syncLock)
        {
            this.snooperStats.put(statName, statValue);
        }
    }

    public Map<String, String> getCurrentStats()
    {
        Map<String, String> map = Maps.<String, String>newLinkedHashMap();

        synchronized (this.syncLock)
        {
            this.addMemoryStatsToSnooper();

            for (Entry<String, Object> entry : this.snooperStats.entrySet())
            {
                map.put(entry.getKey(), entry.getValue().toString());
            }

            for (Entry<String, Object> entry1 : this.clientStats.entrySet())
            {
                map.put(entry1.getKey(), entry1.getValue().toString());
            }

            return map;
        }
    }

    public boolean isSnooperRunning()
    {
        return this.isRunning;
    }

    public void stopSnooper()
    {
        this.threadTrigger.cancel();
    }

    public String getUniqueID()
    {
        return this.uniqueID;
    }

    /**
     * Returns the saved value of System#currentTimeMillis when the game started
     */
    public long getMinecraftStartTimeMillis()
    {
        return this.minecraftStartTimeMilis;
    }
}
