package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptionTranslator
{
    private final Cipher cipher;
    private byte[] inputBuffer = new byte[0];
    private byte[] outputBuffer = new byte[0];

    protected NettyEncryptionTranslator(Cipher cipherIn)
    {
        this.cipher = cipherIn;
    }

    private byte[] bufToBytes(ByteBuf buf)
    {
        int i = buf.readableBytes();

        if (this.inputBuffer.length < i)
        {
            this.inputBuffer = new byte[i];
        }

        buf.readBytes(this.inputBuffer, 0, i);
        return this.inputBuffer;
    }

    protected ByteBuf decipher(ChannelHandlerContext ctx, ByteBuf buffer) throws ShortBufferException
    {
        int i = buffer.readableBytes();
        byte[] abyte = this.bufToBytes(buffer);
        ByteBuf bytebuf = ctx.alloc().heapBuffer(this.cipher.getOutputSize(i));
        bytebuf.writerIndex(this.cipher.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
        return bytebuf;
    }

    protected void cipher(ByteBuf in, ByteBuf out) throws ShortBufferException
    {
        int i = in.readableBytes();
        byte[] abyte = this.bufToBytes(in);
        int j = this.cipher.getOutputSize(i);

        if (this.outputBuffer.length < j)
        {
            this.outputBuffer = new byte[j];
        }

        out.writeBytes(this.outputBuffer, 0, this.cipher.update(abyte, 0, i, this.outputBuffer));
    }
}
