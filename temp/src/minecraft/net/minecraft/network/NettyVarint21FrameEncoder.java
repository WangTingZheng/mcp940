package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class NettyVarint21FrameEncoder extends MessageToByteEncoder<ByteBuf> {
   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws Exception {
      int i = p_encode_2_.readableBytes();
      int j = PacketBuffer.func_150790_a(i);
      if (j > 3) {
         throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
      } else {
         PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
         packetbuffer.ensureWritable(j + i);
         packetbuffer.func_150787_b(i);
         packetbuffer.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), i);
      }
   }
}
