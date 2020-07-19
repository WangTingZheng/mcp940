package net.minecraft.src;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TextureAnimation
{
    private String srcTex = null;
    private String dstTex = null;
    ResourceLocation dstTexLoc = null;
    private int dstTextId = -1;
    private int dstX = 0;
    private int dstY = 0;
    private int frameWidth = 0;
    private int frameHeight = 0;
    private TextureAnimationFrame[] frames = null;
    private int activeFrame = 0;
    byte[] srcData = null;
    private ByteBuffer imageData = null;

    public TextureAnimation(String p_i94_1_, byte[] p_i94_2_, String p_i94_3_, ResourceLocation p_i94_4_, int p_i94_5_, int p_i94_6_, int p_i94_7_, int p_i94_8_, Properties p_i94_9_, int p_i94_10_)
    {
        this.srcTex = p_i94_1_;
        this.dstTex = p_i94_3_;
        this.dstTexLoc = p_i94_4_;
        this.dstX = p_i94_5_;
        this.dstY = p_i94_6_;
        this.frameWidth = p_i94_7_;
        this.frameHeight = p_i94_8_;
        int i = p_i94_7_ * p_i94_8_ * 4;

        if (p_i94_2_.length % i != 0)
        {
            Config.warn("Invalid animated texture length: " + p_i94_2_.length + ", frameWidth: " + p_i94_7_ + ", frameHeight: " + p_i94_8_);
        }

        this.srcData = p_i94_2_;
        int j = p_i94_2_.length / i;

        if (p_i94_9_.get("tile.0") != null)
        {
            for (int k = 0; p_i94_9_.get("tile." + k) != null; ++k)
            {
                j = k + 1;
            }
        }

        String s2 = (String)p_i94_9_.get("duration");
        int l = Config.parseInt(s2, p_i94_10_);
        this.frames = new TextureAnimationFrame[j];

        for (int i1 = 0; i1 < this.frames.length; ++i1)
        {
            String s = (String)p_i94_9_.get("tile." + i1);
            int j1 = Config.parseInt(s, i1);
            String s1 = (String)p_i94_9_.get("duration." + i1);
            int k1 = Config.parseInt(s1, l);
            TextureAnimationFrame textureanimationframe = new TextureAnimationFrame(j1, k1);
            this.frames[i1] = textureanimationframe;
        }
    }

    public boolean nextFrame()
    {
        if (this.frames.length <= 0)
        {
            return false;
        }
        else
        {
            if (this.activeFrame >= this.frames.length)
            {
                this.activeFrame = 0;
            }

            TextureAnimationFrame textureanimationframe = this.frames[this.activeFrame];
            ++textureanimationframe.counter;

            if (textureanimationframe.counter < textureanimationframe.duration)
            {
                return false;
            }
            else
            {
                textureanimationframe.counter = 0;
                ++this.activeFrame;

                if (this.activeFrame >= this.frames.length)
                {
                    this.activeFrame = 0;
                }

                return true;
            }
        }
    }

    public int getActiveFrameIndex()
    {
        if (this.frames.length <= 0)
        {
            return 0;
        }
        else
        {
            if (this.activeFrame >= this.frames.length)
            {
                this.activeFrame = 0;
            }

            TextureAnimationFrame textureanimationframe = this.frames[this.activeFrame];
            return textureanimationframe.index;
        }
    }

    public int getFrameCount()
    {
        return this.frames.length;
    }

    public boolean updateTexture()
    {
        if (this.dstTextId < 0)
        {
            ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);

            if (itextureobject == null)
            {
                return false;
            }

            this.dstTextId = itextureobject.getGlTextureId();
        }

        if (this.imageData == null)
        {
            this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
            this.imageData.put(this.srcData);
            this.srcData = null;
        }

        if (!this.nextFrame())
        {
            return false;
        }
        else
        {
            int k = this.frameWidth * this.frameHeight * 4;
            int i = this.getActiveFrameIndex();
            int j = k * i;

            if (j + k > this.imageData.capacity())
            {
                return false;
            }
            else
            {
                this.imageData.position(j);
                GlStateManager.bindTexture(this.dstTextId);
                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
                return true;
            }
        }
    }

    public String getSrcTex()
    {
        return this.srcTex;
    }

    public String getDstTex()
    {
        return this.dstTex;
    }

    public ResourceLocation getDstTexLoc()
    {
        return this.dstTexLoc;
    }
}
