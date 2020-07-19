package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class TextureAnimations
{
    private static TextureAnimation[] textureAnimations = null;

    public static void reset()
    {
        textureAnimations = null;
    }

    public static void update()
    {
        textureAnimations = null;
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        textureAnimations = getTextureAnimations(airesourcepack);

        if (Config.isAnimatedTextures())
        {
            updateAnimations();
        }
    }

    public static void updateCustomAnimations()
    {
        if (textureAnimations != null)
        {
            if (Config.isAnimatedTextures())
            {
                updateAnimations();
            }
        }
    }

    public static void updateAnimations()
    {
        if (textureAnimations != null)
        {
            for (int i = 0; i < textureAnimations.length; ++i)
            {
                TextureAnimation textureanimation = textureAnimations[i];
                textureanimation.updateTexture();
            }
        }
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack[] p_getTextureAnimations_0_)
    {
        List list = new ArrayList();

        for (int i = 0; i < p_getTextureAnimations_0_.length; ++i)
        {
            IResourcePack iresourcepack = p_getTextureAnimations_0_[i];
            TextureAnimation[] atextureanimation = getTextureAnimations(iresourcepack);

            if (atextureanimation != null)
            {
                list.addAll(Arrays.asList(atextureanimation));
            }
        }

        TextureAnimation[] atextureanimation1 = (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
        return atextureanimation1;
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack p_getTextureAnimations_0_)
    {
        String[] astring = ResUtils.collectFiles(p_getTextureAnimations_0_, "mcpatcher/anim/", ".properties", (String[])null);

        if (astring.length <= 0)
        {
            return null;
        }
        else
        {
            List list = new ArrayList();

            for (int i = 0; i < astring.length; ++i)
            {
                String s = astring[i];
                Config.dbg("Texture animation: " + s);

                try
                {
                    ResourceLocation resourcelocation = new ResourceLocation(s);
                    InputStream inputstream = p_getTextureAnimations_0_.getInputStream(resourcelocation);
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    TextureAnimation textureanimation = makeTextureAnimation(properties, resourcelocation);

                    if (textureanimation != null)
                    {
                        ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());

                        if (Config.getDefiningResourcePack(resourcelocation1) != p_getTextureAnimations_0_)
                        {
                            Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
                        }
                        else
                        {
                            list.add(textureanimation);
                        }
                    }
                }
                catch (FileNotFoundException filenotfoundexception)
                {
                    Config.warn("File not found: " + filenotfoundexception.getMessage());
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }

            TextureAnimation[] atextureanimation = (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
            return atextureanimation;
        }
    }

    public static TextureAnimation makeTextureAnimation(Properties p_makeTextureAnimation_0_, ResourceLocation p_makeTextureAnimation_1_)
    {
        String s = p_makeTextureAnimation_0_.getProperty("from");
        String s1 = p_makeTextureAnimation_0_.getProperty("to");
        int i = Config.parseInt(p_makeTextureAnimation_0_.getProperty("x"), -1);
        int j = Config.parseInt(p_makeTextureAnimation_0_.getProperty("y"), -1);
        int k = Config.parseInt(p_makeTextureAnimation_0_.getProperty("w"), -1);
        int l = Config.parseInt(p_makeTextureAnimation_0_.getProperty("h"), -1);

        if (s != null && s1 != null)
        {
            if (i >= 0 && j >= 0 && k >= 0 && l >= 0)
            {
                s = s.trim();
                s1 = s1.trim();
                String s2 = TextureUtils.getBasePath(p_makeTextureAnimation_1_.getResourcePath());
                s = TextureUtils.fixResourcePath(s, s2);
                s1 = TextureUtils.fixResourcePath(s1, s2);
                byte[] abyte = getCustomTextureData(s, k);

                if (abyte == null)
                {
                    Config.warn("TextureAnimation: Source texture not found: " + s1);
                    return null;
                }
                else
                {
                    int i1 = abyte.length / 4;
                    int j1 = i1 / (k * l);
                    int k1 = j1 * k * l;

                    if (i1 != k1)
                    {
                        Config.warn("TextureAnimation: Source texture has invalid number of frames: " + s + ", frames: " + (float)i1 / (float)(k * l));
                        return null;
                    }
                    else
                    {
                        ResourceLocation resourcelocation = new ResourceLocation(s1);

                        try
                        {
                            InputStream inputstream = Config.getResourceStream(resourcelocation);

                            if (inputstream == null)
                            {
                                Config.warn("TextureAnimation: Target texture not found: " + s1);
                                return null;
                            }
                            else
                            {
                                BufferedImage bufferedimage = readTextureImage(inputstream);

                                if (i + k <= bufferedimage.getWidth() && j + l <= bufferedimage.getHeight())
                                {
                                    TextureAnimation textureanimation = new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, p_makeTextureAnimation_0_, 1);
                                    return textureanimation;
                                }
                                else
                                {
                                    Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + s1);
                                    return null;
                                }
                            }
                        }
                        catch (IOException var17)
                        {
                            Config.warn("TextureAnimation: Target texture not found: " + s1);
                            return null;
                        }
                    }
                }
            }
            else
            {
                Config.warn("TextureAnimation: Invalid coordinates");
                return null;
            }
        }
        else
        {
            Config.warn("TextureAnimation: Source or target texture not specified");
            return null;
        }
    }

    public static byte[] getCustomTextureData(String p_getCustomTextureData_0_, int p_getCustomTextureData_1_)
    {
        byte[] abyte = loadImage(p_getCustomTextureData_0_, p_getCustomTextureData_1_);

        if (abyte == null)
        {
            abyte = loadImage("/anim" + p_getCustomTextureData_0_, p_getCustomTextureData_1_);
        }

        return abyte;
    }

    private static byte[] loadImage(String p_loadImage_0_, int p_loadImage_1_)
    {
        GameSettings gamesettings = Config.getGameSettings();

        try
        {
            ResourceLocation resourcelocation = new ResourceLocation(p_loadImage_0_);
            InputStream inputstream = Config.getResourceStream(resourcelocation);

            if (inputstream == null)
            {
                return null;
            }
            else
            {
                BufferedImage bufferedimage = readTextureImage(inputstream);
                inputstream.close();

                if (bufferedimage == null)
                {
                    return null;
                }
                else
                {
                    if (p_loadImage_1_ > 0 && bufferedimage.getWidth() != p_loadImage_1_)
                    {
                        double d0 = (double)(bufferedimage.getHeight() / bufferedimage.getWidth());
                        int j = (int)((double)p_loadImage_1_ * d0);
                        bufferedimage = scaleBufferedImage(bufferedimage, p_loadImage_1_, j);
                    }

                    int k2 = bufferedimage.getWidth();
                    int i = bufferedimage.getHeight();
                    int[] aint = new int[k2 * i];
                    byte[] abyte = new byte[k2 * i * 4];
                    bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);

                    for (int k = 0; k < aint.length; ++k)
                    {
                        int l = aint[k] >> 24 & 255;
                        int i1 = aint[k] >> 16 & 255;
                        int j1 = aint[k] >> 8 & 255;
                        int k1 = aint[k] & 255;

                        if (gamesettings != null && gamesettings.anaglyph)
                        {
                            int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
                            int i2 = (i1 * 30 + j1 * 70) / 100;
                            int j2 = (i1 * 30 + k1 * 70) / 100;
                            i1 = l1;
                            j1 = i2;
                            k1 = j2;
                        }

                        abyte[k * 4 + 0] = (byte)i1;
                        abyte[k * 4 + 1] = (byte)j1;
                        abyte[k * 4 + 2] = (byte)k1;
                        abyte[k * 4 + 3] = (byte)l;
                    }

                    return abyte;
                }
            }
        }
        catch (FileNotFoundException var18)
        {
            return null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream p_readTextureImage_0_) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(p_readTextureImage_0_);
        p_readTextureImage_0_.close();
        return bufferedimage;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage p_scaleBufferedImage_0_, int p_scaleBufferedImage_1_, int p_scaleBufferedImage_2_)
    {
        BufferedImage bufferedimage = new BufferedImage(p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, 2);
        Graphics2D graphics2d = bufferedimage.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(p_scaleBufferedImage_0_, 0, 0, p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, (ImageObserver)null);
        return bufferedimage;
    }
}
