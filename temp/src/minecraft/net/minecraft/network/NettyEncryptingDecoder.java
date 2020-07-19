package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingDecoder extends MessageToMessageDecoder<ByteBuf> {
   private final NettyEncryptionTranslator field_150509_a;

   public NettyEncryptingDecoder(Cipher p_i45141_1_) {
      this.field_150509_a = new NettyEncryptionTranslator(p_i45141_1_);
   }

   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws ShortBufferException, Exception {
      p_decode_3_.add(this.field_150509_a.func_150503_a(p_decode_1_, p_decode_2_));
   }
}
