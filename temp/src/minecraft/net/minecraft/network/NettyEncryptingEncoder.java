package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptingEncoder extends MessageToByteEncoder<ByteBuf> {
   private final NettyEncryptionTranslator field_150750_a;

   public NettyEncryptingEncoder(Cipher p_i45142_1_) {
      this.field_150750_a = new NettyEncryptionTranslator(p_i45142_1_);
   }

   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws ShortBufferException, Exception {
      this.field_150750_a.func_150504_a(p_encode_2_, p_encode_3_);
   }
}
