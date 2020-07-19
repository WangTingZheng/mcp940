package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityProperties implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private final List<SPacketEntityProperties.Snapshot> snapshots = Lists.<SPacketEntityProperties.Snapshot>newArrayList();

    public SPacketEntityProperties()
    {
    }

    public SPacketEntityProperties(int entityIdIn, Collection<IAttributeInstance> instances)
    {
        this.entityId = entityIdIn;

        for (IAttributeInstance iattributeinstance : instances)
        {
            this.snapshots.add(new SPacketEntityProperties.Snapshot(iattributeinstance.getAttribute().getName(), iattributeinstance.getBaseValue(), iattributeinstance.getModifiers()));
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
        int i = buf.readInt();

        for (int j = 0; j < i; ++j)
        {
            String s = buf.readString(64);
            double d0 = buf.readDouble();
            List<AttributeModifier> list = Lists.<AttributeModifier>newArrayList();
            int k = buf.readVarInt();

            for (int l = 0; l < k; ++l)
            {
                UUID uuid = buf.readUniqueId();
                list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
            }

            this.snapshots.add(new SPacketEntityProperties.Snapshot(s, d0, list));
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
        buf.writeInt(this.snapshots.size());

        for (SPacketEntityProperties.Snapshot spacketentityproperties$snapshot : this.snapshots)
        {
            buf.writeString(spacketentityproperties$snapshot.getName());
            buf.writeDouble(spacketentityproperties$snapshot.getBaseValue());
            buf.writeVarInt(spacketentityproperties$snapshot.getModifiers().size());

            for (AttributeModifier attributemodifier : spacketentityproperties$snapshot.getModifiers())
            {
                buf.writeUniqueId(attributemodifier.getID());
                buf.writeDouble(attributemodifier.getAmount());
                buf.writeByte(attributemodifier.getOperation());
            }
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityProperties(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public List<SPacketEntityProperties.Snapshot> getSnapshots()
    {
        return this.snapshots;
    }

    public class Snapshot
    {
        private final String name;
        private final double baseValue;
        private final Collection<AttributeModifier> modifiers;

        public Snapshot(String nameIn, double baseValueIn, Collection<AttributeModifier> modifiersIn)
        {
            this.name = nameIn;
            this.baseValue = baseValueIn;
            this.modifiers = modifiersIn;
        }

        public String getName()
        {
            return this.name;
        }

        public double getBaseValue()
        {
            return this.baseValue;
        }

        public Collection<AttributeModifier> getModifiers()
        {
            return this.modifiers;
        }
    }
}
