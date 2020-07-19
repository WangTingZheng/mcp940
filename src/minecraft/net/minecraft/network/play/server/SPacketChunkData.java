package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class SPacketChunkData implements Packet<INetHandlerPlayClient>
{
    private int chunkX;
    private int chunkZ;
    private int availableSections;
    private byte[] buffer;
    private List<NBTTagCompound> tileEntityTags;
    private boolean fullChunk;

    public SPacketChunkData()
    {
    }

    public SPacketChunkData(Chunk chunkIn, int changedSectionFilter)
    {
        this.chunkX = chunkIn.x;
        this.chunkZ = chunkIn.z;
        this.fullChunk = changedSectionFilter == 65535;
        boolean flag = chunkIn.getWorld().provider.hasSkyLight();
        this.buffer = new byte[this.calculateChunkSize(chunkIn, flag, changedSectionFilter)];
        this.availableSections = this.extractChunkData(new PacketBuffer(this.getWriteBuffer()), chunkIn, flag, changedSectionFilter);
        this.tileEntityTags = Lists.<NBTTagCompound>newArrayList();

        for (Entry<BlockPos, TileEntity> entry : chunkIn.getTileEntityMap().entrySet())
        {
            BlockPos blockpos = entry.getKey();
            TileEntity tileentity = entry.getValue();
            int i = blockpos.getY() >> 4;

            if (this.isFullChunk() || (changedSectionFilter & 1 << i) != 0)
            {
                NBTTagCompound nbttagcompound = tileentity.getUpdateTag();
                this.tileEntityTags.add(nbttagcompound);
            }
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.chunkX = buf.readInt();
        this.chunkZ = buf.readInt();
        this.fullChunk = buf.readBoolean();
        this.availableSections = buf.readVarInt();
        int i = buf.readVarInt();

        if (i > 2097152)
        {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
        }
        else
        {
            this.buffer = new byte[i];
            buf.readBytes(this.buffer);
            int j = buf.readVarInt();
            this.tileEntityTags = Lists.<NBTTagCompound>newArrayList();

            for (int k = 0; k < j; ++k)
            {
                this.tileEntityTags.add(buf.readCompoundTag());
            }
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.chunkX);
        buf.writeInt(this.chunkZ);
        buf.writeBoolean(this.fullChunk);
        buf.writeVarInt(this.availableSections);
        buf.writeVarInt(this.buffer.length);
        buf.writeBytes(this.buffer);
        buf.writeVarInt(this.tileEntityTags.size());

        for (NBTTagCompound nbttagcompound : this.tileEntityTags)
        {
            buf.writeCompoundTag(nbttagcompound);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleChunkData(this);
    }

    public PacketBuffer getReadBuffer()
    {
        return new PacketBuffer(Unpooled.wrappedBuffer(this.buffer));
    }

    private ByteBuf getWriteBuffer()
    {
        ByteBuf bytebuf = Unpooled.wrappedBuffer(this.buffer);
        bytebuf.writerIndex(0);
        return bytebuf;
    }

    public int extractChunkData(PacketBuffer buf, Chunk chunkIn, boolean writeSkylight, int changedSectionFilter)
    {
        int i = 0;
        ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
        int j = 0;

        for (int k = aextendedblockstorage.length; j < k; ++j)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];

            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!this.isFullChunk() || !extendedblockstorage.isEmpty()) && (changedSectionFilter & 1 << j) != 0)
            {
                i |= 1 << j;
                extendedblockstorage.getData().write(buf);
                buf.writeBytes(extendedblockstorage.getBlockLight().getData());

                if (writeSkylight)
                {
                    buf.writeBytes(extendedblockstorage.getSkyLight().getData());
                }
            }
        }

        if (this.isFullChunk())
        {
            buf.writeBytes(chunkIn.getBiomeArray());
        }

        return i;
    }

    protected int calculateChunkSize(Chunk chunkIn, boolean p_189556_2_, int p_189556_3_)
    {
        int i = 0;
        ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
        int j = 0;

        for (int k = aextendedblockstorage.length; j < k; ++j)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];

            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!this.isFullChunk() || !extendedblockstorage.isEmpty()) && (p_189556_3_ & 1 << j) != 0)
            {
                i = i + extendedblockstorage.getData().getSerializedSize();
                i = i + extendedblockstorage.getBlockLight().getData().length;

                if (p_189556_2_)
                {
                    i += extendedblockstorage.getSkyLight().getData().length;
                }
            }
        }

        if (this.isFullChunk())
        {
            i += chunkIn.getBiomeArray().length;
        }

        return i;
    }

    public int getChunkX()
    {
        return this.chunkX;
    }

    public int getChunkZ()
    {
        return this.chunkZ;
    }

    public int getExtractedSize()
    {
        return this.availableSections;
    }

    public boolean isFullChunk()
    {
        return this.fullChunk;
    }

    public List<NBTTagCompound> getTileEntityTags()
    {
        return this.tileEntityTags;
    }
}
