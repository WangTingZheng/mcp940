package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class SPacketCombatEvent implements Packet<INetHandlerPlayClient>
{
    public SPacketCombatEvent.Event eventType;
    public int playerId;
    public int entityId;
    public int duration;
    public ITextComponent deathMessage;

    public SPacketCombatEvent()
    {
    }

    public SPacketCombatEvent(CombatTracker tracker, SPacketCombatEvent.Event eventIn)
    {
        this(tracker, eventIn, true);
    }

    @SuppressWarnings("incomplete-switch")
    public SPacketCombatEvent(CombatTracker tracker, SPacketCombatEvent.Event eventIn, boolean showDeathMessage)
    {
        this.eventType = eventIn;
        EntityLivingBase entitylivingbase = tracker.getBestAttacker();

        switch (eventIn)
        {
            case END_COMBAT:
                this.duration = tracker.getCombatDuration();
                this.entityId = entitylivingbase == null ? -1 : entitylivingbase.getEntityId();
                break;

            case ENTITY_DIED:
                this.playerId = tracker.getFighter().getEntityId();
                this.entityId = entitylivingbase == null ? -1 : entitylivingbase.getEntityId();

                if (showDeathMessage)
                {
                    this.deathMessage = tracker.getDeathMessage();
                }
                else
                {
                    this.deathMessage = new TextComponentString("");
                }
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.eventType = (SPacketCombatEvent.Event)buf.readEnumValue(SPacketCombatEvent.Event.class);

        if (this.eventType == SPacketCombatEvent.Event.END_COMBAT)
        {
            this.duration = buf.readVarInt();
            this.entityId = buf.readInt();
        }
        else if (this.eventType == SPacketCombatEvent.Event.ENTITY_DIED)
        {
            this.playerId = buf.readVarInt();
            this.entityId = buf.readInt();
            this.deathMessage = buf.readTextComponent();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.eventType);

        if (this.eventType == SPacketCombatEvent.Event.END_COMBAT)
        {
            buf.writeVarInt(this.duration);
            buf.writeInt(this.entityId);
        }
        else if (this.eventType == SPacketCombatEvent.Event.ENTITY_DIED)
        {
            buf.writeVarInt(this.playerId);
            buf.writeInt(this.entityId);
            buf.writeTextComponent(this.deathMessage);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleCombatEvent(this);
    }

    public static enum Event
    {
        ENTER_COMBAT,
        END_COMBAT,
        ENTITY_DIED;
    }
}
