package shadersmod.client;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class HFNoiseTexture
{
    public int texID = GL11.glGenTextures();
    public int textureUnit = 15;

    public HFNoiseTexture(int width, int height)
    {
        byte[] abyte = this.genHFNoiseImage(width, height);
        ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
        bytebuffer.put(abyte);
        bytebuffer.flip();
        GlStateManager.bindTexture(this.texID);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, bytebuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GlStateManager.bindTexture(0);
    }

    public int getID()
    {
        return this.texID;
    }

    public void destroy()
    {
        GlStateManager.deleteTexture(this.texID);
        this.texID = 0;
    }

    private int random(int seed)
    {
        seed = seed ^ seed << 13;
        seed = seed ^ seed >> 17;
        seed = seed ^ seed << 5;
        return seed;
    }

    private byte random(int x, int y, int z)
    {
        int i = (this.random(x) + this.random(y * 19)) * this.random(z * 23) - z;
        return (byte)(this.random(i) % 128);
    }

    private byte[] genHFNoiseImage(int width, int height)
    {
        byte[] abyte = new byte[width * height * 3];
        int i = 0;

        for (int j = 0; j < height; ++j)
        {
            for (int k = 0; k < width; ++k)
            {
                for (int l = 1; l < 4; ++l)
                {
                    abyte[i++] = this.random(k, j, l);
                }
            }
        }

        return abyte;
    }
}
