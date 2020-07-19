package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RegionFileCache
{
    private static final Map<File, RegionFile> REGIONS_BY_FILE = Maps.<File, RegionFile>newHashMap();

    public static synchronized RegionFile createOrLoadRegionFile(File worldDir, int chunkX, int chunkZ)
    {
        File file1 = new File(worldDir, "region");
        File file2 = new File(file1, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
        RegionFile regionfile = REGIONS_BY_FILE.get(file2);

        if (regionfile != null)
        {
            return regionfile;
        }
        else
        {
            if (!file1.exists())
            {
                file1.mkdirs();
            }

            if (REGIONS_BY_FILE.size() >= 256)
            {
                clearRegionFileReferences();
            }

            RegionFile regionfile1 = new RegionFile(file2);
            REGIONS_BY_FILE.put(file2, regionfile1);
            return regionfile1;
        }
    }

    public static synchronized RegionFile getRegionFileIfExists(File worldDir, int chunkX, int chunkZ)
    {
        File file1 = new File(worldDir, "region");
        File file2 = new File(file1, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
        RegionFile regionfile = REGIONS_BY_FILE.get(file2);

        if (regionfile != null)
        {
            return regionfile;
        }
        else if (file1.exists() && file2.exists())
        {
            if (REGIONS_BY_FILE.size() >= 256)
            {
                clearRegionFileReferences();
            }

            RegionFile regionfile1 = new RegionFile(file2);
            REGIONS_BY_FILE.put(file2, regionfile1);
            return regionfile1;
        }
        else
        {
            return null;
        }
    }

    /**
     * clears region file references
     */
    public static synchronized void clearRegionFileReferences()
    {
        for (RegionFile regionfile : REGIONS_BY_FILE.values())
        {
            try
            {
                if (regionfile != null)
                {
                    regionfile.close();
                }
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }

        REGIONS_BY_FILE.clear();
    }

    /**
     * Gets an input stream for the chunk at the specified location.
     */
    public static DataInputStream getChunkInputStream(File worldDir, int chunkX, int chunkZ)
    {
        RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
        return regionfile.getChunkDataInputStream(chunkX & 31, chunkZ & 31);
    }

    /**
     * Gets an output stream for the specified chunk.
     */
    public static DataOutputStream getChunkOutputStream(File worldDir, int chunkX, int chunkZ)
    {
        RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
        return regionfile.getChunkDataOutputStream(chunkX & 31, chunkZ & 31);
    }

    public static boolean chunkExists(File worldDir, int chunkX, int chunkZ)
    {
        RegionFile regionfile = getRegionFileIfExists(worldDir, chunkX, chunkZ);
        return regionfile != null ? regionfile.isChunkSaved(chunkX & 31, chunkZ & 31) : false;
    }
}
