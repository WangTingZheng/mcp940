package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketDecoder extends ByteToMessageDecoder {
   private static final Logger field_150800_a = LogManager.getLogger();
   private static final Marker field_150799_b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.field_150738_b);
   private final EnumPacketDirection field_152499_c;

   public NettyPacketDecoder(EnumPacketDirection p_i45999_1_) {
      this.field_152499_c = p_i45999_1_;
   }

   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception {
      if (p_decode_2_.readableBytes() != 0) {
         PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
         int i = packetbuffer.func_150792_a();
         Packet<?> packet = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.field_150739_c).get()).func_179244_a(this.field_152499_c, i);
         if (packet == null) {
            throw new IOException("Bad packet id " + i);
         } else {
            packet.func_148837_a(packetbuffer);
            if (packetbuffer.readableBytes() > 0) {
               throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.field_150739_c).get()).func_150759_c() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + i);
            } else {
               p_decode_3_.add(packet);
               if (field_150800_a.isDebugEnabled()) {
                  field_150800_a.debug(field_150799_b, " IN: [{}:{}] {}", p_decode_1_.channel().attr(NetworkManager.field_150739_c).get(), Integer.valueOf(i), packet.getClass().getName());
               }

            }
         }
      }
   }
}
