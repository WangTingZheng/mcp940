package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class SPacketRespawn implements Packet<INetHandlerPlayClient>
{
    private int dimensionID;
    private EnumDifficulty difficulty;
    private GameType gameType;
    private WorldType worldType;

    public SPacketRespawn()
    {
    }

    public SPacketRespawn(int dimensionIdIn, EnumDifficulty difficultyIn, WorldType worldTypeIn, GameType gameModeIn)
    {
        this.dimensionID = dimensionIdIn;
        this.difficulty = difficultyIn;
        this.gameType = gameModeIn;
        this.worldType = worldTypeIn;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleRespawn(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.dimensionID = buf.readInt();
        this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
        this.gameType = GameType.getByID(buf.readUnsignedByte());
        this.worldType = WorldType.parseWorldType(buf.readString(16));

        if (this.worldType == null)
        {
            this.worldType = WorldType.DEFAULT;
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.dimensionID);
        buf.writeByte(this.difficulty.getDifficultyId());
        buf.writeByte(this.gameType.getID());
        buf.writeString(this.worldType.getName());
    }

    public int getDimensionID()
    {
        return this.dimensionID;
    }

    public EnumDifficulty getDifficulty()
    {
        return this.difficulty;
    }

    public GameType getGameType()
    {
        return this.gameType;
    }

    public WorldType getWorldType()
    {
        return this.worldType;
    }
}
