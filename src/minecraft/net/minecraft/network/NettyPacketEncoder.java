package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketEncoder extends MessageToByteEncoder < Packet<? >>
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
    private final EnumPacketDirection direction;

    public NettyPacketEncoder(EnumPacketDirection direction)
    {
        this.direction = direction;
    }

    protected void encode(ChannelHandlerContext p_encode_1_, Packet<?> p_encode_2_, ByteBuf p_encode_3_) throws IOException, Exception
    {
        EnumConnectionState enumconnectionstate = (EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();

        if (enumconnectionstate == null)
        {
            throw new RuntimeException("ConnectionProtocol unknown: " + p_encode_2_.toString());
        }
        else
        {
            Integer integer = enumconnectionstate.getPacketId(this.direction, p_encode_2_);

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, p_encode_2_.getClass().getName());
            }

            if (integer == null)
            {
                throw new IOException("Can't serialize unregistered packet");
            }
            else
            {
                PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
                packetbuffer.writeVarInt(integer.intValue());

                try
                {
                    p_encode_2_.writePacketData(packetbuffer);
                }
                catch (Throwable throwable)
                {
                    LOGGER.error(throwable);
                }
            }
        }
    }
}
