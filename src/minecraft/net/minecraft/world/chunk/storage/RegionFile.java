package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class RegionFile
{
    private static final byte[] EMPTY_SECTOR = new byte[4096];
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int[] offsets = new int[1024];
    private final int[] chunkTimestamps = new int[1024];
    private List<Boolean> sectorFree;

    /** McRegion sizeDelta */
    private int sizeDelta;
    private long lastModified;

    public RegionFile(File fileNameIn)
    {
        this.fileName = fileNameIn;
        this.sizeDelta = 0;

        try
        {
            if (fileNameIn.exists())
            {
                this.lastModified = fileNameIn.lastModified();
            }

            this.dataFile = new RandomAccessFile(fileNameIn, "rw");

            if (this.dataFile.length() < 4096L)
            {
                this.dataFile.write(EMPTY_SECTOR);
                this.dataFile.write(EMPTY_SECTOR);
                this.sizeDelta += 8192;
            }

            if ((this.dataFile.length() & 4095L) != 0L)
            {
                for (int i = 0; (long)i < (this.dataFile.length() & 4095L); ++i)
                {
                    this.dataFile.write(0);
                }
            }

            int i1 = (int)this.dataFile.length() / 4096;
            this.sectorFree = Lists.<Boolean>newArrayListWithCapacity(i1);

            for (int j = 0; j < i1; ++j)
            {
                this.sectorFree.add(Boolean.valueOf(true));
            }

            this.sectorFree.set(0, Boolean.valueOf(false));
            this.sectorFree.set(1, Boolean.valueOf(false));
            this.dataFile.seek(0L);

            for (int j1 = 0; j1 < 1024; ++j1)
            {
                int k = this.dataFile.readInt();
                this.offsets[j1] = k;

                if (k != 0 && (k >> 8) + (k & 255) <= this.sectorFree.size())
                {
                    for (int l = 0; l < (k & 255); ++l)
                    {
                        this.sectorFree.set((k >> 8) + l, Boolean.valueOf(false));
                    }
                }
            }

            for (int k1 = 0; k1 < 1024; ++k1)
            {
                int l1 = this.dataFile.readInt();
                this.chunkTimestamps[k1] = l1;
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    @Nullable

    /**
     * Returns an uncompressed chunk stream from the region file.
     */
    public synchronized DataInputStream getChunkDataInputStream(int x, int z)
    {
        if (this.outOfBounds(x, z))
        {
            return null;
        }
        else
        {
            try
            {
                int i = this.getOffset(x, z);

                if (i == 0)
                {
                    return null;
                }
                else
                {
                    int j = i >> 8;
                    int k = i & 255;

                    if (j + k > this.sectorFree.size())
                    {
                        return null;
                    }
                    else
                    {
                        this.dataFile.seek((long)(j * 4096));
                        int l = this.dataFile.readInt();

                        if (l > 4096 * k)
                        {
                            return null;
                        }
                        else if (l <= 0)
                        {
                            return null;
                        }
                        else
                        {
                            byte b0 = this.dataFile.readByte();

                            if (b0 == 1)
                            {
                                byte[] abyte1 = new byte[l - 1];
                                this.dataFile.read(abyte1);
                                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
                            }
                            else if (b0 == 2)
                            {
                                byte[] abyte = new byte[l - 1];
                                this.dataFile.read(abyte);
                                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
                            }
                            else
                            {
                                return null;
                            }
                        }
                    }
                }
            }
            catch (IOException var9)
            {
                return null;
            }
        }
    }

    @Nullable

    /**
     * Returns an output stream used to write chunk data. Data is on disk when the returned stream is closed.
     */
    public DataOutputStream getChunkDataOutputStream(int x, int z)
    {
        return this.outOfBounds(x, z) ? null : new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(x, z))));
    }

    /**
     * Writes the specified chunk to disk.
     */
    protected synchronized void write(int x, int z, byte[] data, int length)
    {
        try
        {
            int i = this.getOffset(x, z);
            int j = i >> 8;
            int k = i & 255;
            int l = (length + 5) / 4096 + 1;

            if (l >= 256)
            {
                return;
            }

            if (j != 0 && k == l)
            {
                this.write(j, data, length);
            }
            else
            {
                for (int i1 = 0; i1 < k; ++i1)
                {
                    this.sectorFree.set(j + i1, Boolean.valueOf(true));
                }

                int l1 = this.sectorFree.indexOf(Boolean.valueOf(true));
                int j1 = 0;

                if (l1 != -1)
                {
                    for (int k1 = l1; k1 < this.sectorFree.size(); ++k1)
                    {
                        if (j1 != 0)
                        {
                            if (((Boolean)this.sectorFree.get(k1)).booleanValue())
                            {
                                ++j1;
                            }
                            else
                            {
                                j1 = 0;
                            }
                        }
                        else if (((Boolean)this.sectorFree.get(k1)).booleanValue())
                        {
                            l1 = k1;
                            j1 = 1;
                        }

                        if (j1 >= l)
                        {
                            break;
                        }
                    }
                }

                if (j1 >= l)
                {
                    j = l1;
                    this.setOffset(x, z, l1 << 8 | l);

                    for (int j2 = 0; j2 < l; ++j2)
                    {
                        this.sectorFree.set(j + j2, Boolean.valueOf(false));
                    }

                    this.write(j, data, length);
                }
                else
                {
                    this.dataFile.seek(this.dataFile.length());
                    j = this.sectorFree.size();

                    for (int i2 = 0; i2 < l; ++i2)
                    {
                        this.dataFile.write(EMPTY_SECTOR);
                        this.sectorFree.add(Boolean.valueOf(false));
                    }

                    this.sizeDelta += 4096 * l;
                    this.write(j, data, length);
                    this.setOffset(x, z, j << 8 | l);
                }
            }

            this.setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    /**
     * Writes the chunk data to this RegionFile.
     */
    private void write(int sectorNumber, byte[] data, int length) throws IOException
    {
        this.dataFile.seek((long)(sectorNumber * 4096));
        this.dataFile.writeInt(length + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(data, 0, length);
    }

    /**
     * Checks if region is out of bounds.
     */
    private boolean outOfBounds(int x, int z)
    {
        return x < 0 || x >= 32 || z < 0 || z >= 32;
    }

    /**
     * Gets a chunk's offset in region file.
     */
    private int getOffset(int x, int z)
    {
        return this.offsets[x + z * 32];
    }

    /**
     * Checks if a chunk has been saved.
     */
    public boolean isChunkSaved(int x, int z)
    {
        return this.getOffset(x, z) != 0;
    }

    /**
     * Sets the chunk's offset in the region file.
     */
    private void setOffset(int x, int z, int offset) throws IOException
    {
        this.offsets[x + z * 32] = offset;
        this.dataFile.seek((long)((x + z * 32) * 4));
        this.dataFile.writeInt(offset);
    }

    /**
     * Updates the specified chunk's write timestamp.
     */
    private void setChunkTimestamp(int x, int z, int timestamp) throws IOException
    {
        this.chunkTimestamps[x + z * 32] = timestamp;
        this.dataFile.seek((long)(4096 + (x + z * 32) * 4));
        this.dataFile.writeInt(timestamp);
    }

    /**
     * close this RegionFile and prevent further writes
     */
    public void close() throws IOException
    {
        if (this.dataFile != null)
        {
            this.dataFile.close();
        }
    }

    class ChunkBuffer extends ByteArrayOutputStream
    {
        private final int chunkX;
        private final int chunkZ;

        public ChunkBuffer(int x, int z)
        {
            super(8096);
            this.chunkX = x;
            this.chunkZ = z;
        }

        public void close() throws IOException
        {
            RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
        }
    }
}
