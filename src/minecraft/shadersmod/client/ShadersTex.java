package shadersmod.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import shadersmod.common.SMCLog;

public class ShadersTex
{
    public static final int initialBufferSize = 1048576;
    public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
    public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
    public static int[] intArray = new int[1048576];
    public static final int defBaseTexColor = 0;
    public static final int defNormTexColor = -8421377;
    public static final int defSpecTexColor = 0;
    public static Map<Integer, MultiTexID> multiTexMap = new HashMap<Integer, MultiTexID>();
    public static TextureMap updatingTextureMap = null;
    public static TextureAtlasSprite updatingSprite = null;
    public static MultiTexID updatingTex = null;
    public static MultiTexID boundTex = null;
    public static int updatingPage = 0;
    public static String iconName = null;
    public static IResourceManager resManager = null;
    static ResourceLocation resLocation = null;
    static int imageSize = 0;

    public static IntBuffer getIntBuffer(int size)
    {
        if (intBuffer.capacity() < size)
        {
            int i = roundUpPOT(size);
            byteBuffer = BufferUtils.createByteBuffer(i * 4);
            intBuffer = byteBuffer.asIntBuffer();
        }

        return intBuffer;
    }

    public static int[] getIntArray(int size)
    {
        if (intArray == null)
        {
            intArray = new int[1048576];
        }

        if (intArray.length < size)
        {
            intArray = new int[roundUpPOT(size)];
        }

        return intArray;
    }

    public static int roundUpPOT(int x)
    {
        int i = x - 1;
        i = i | i >> 1;
        i = i | i >> 2;
        i = i | i >> 4;
        i = i | i >> 8;
        i = i | i >> 16;
        return i + 1;
    }

    public static int log2(int x)
    {
        int i = 0;

        if ((x & -65536) != 0)
        {
            i += 16;
            x >>= 16;
        }

        if ((x & 65280) != 0)
        {
            i += 8;
            x >>= 8;
        }

        if ((x & 240) != 0)
        {
            i += 4;
            x >>= 4;
        }

        if ((x & 6) != 0)
        {
            i += 2;
            x >>= 2;
        }

        if ((x & 2) != 0)
        {
            ++i;
        }

        return i;
    }

    public static IntBuffer fillIntBuffer(int size, int value)
    {
        int[] aint = getIntArray(size);
        IntBuffer intbuffer = getIntBuffer(size);
        Arrays.fill(intArray, 0, size, value);
        intBuffer.put(intArray, 0, size);
        return intBuffer;
    }

    public static int[] createAIntImage(int size)
    {
        int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, 0);
        Arrays.fill(aint, size, size * 2, -8421377);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }

    public static int[] createAIntImage(int size, int color)
    {
        int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, color);
        Arrays.fill(aint, size, size * 2, -8421377);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }

    public static MultiTexID getMultiTexID(AbstractTexture tex)
    {
        MultiTexID multitexid = tex.multiTex;

        if (multitexid == null)
        {
            int i = tex.getGlTextureId();
            multitexid = multiTexMap.get(Integer.valueOf(i));

            if (multitexid == null)
            {
                multitexid = new MultiTexID(i, GL11.glGenTextures(), GL11.glGenTextures());
                multiTexMap.put(Integer.valueOf(i), multitexid);
            }

            tex.multiTex = multitexid;
        }

        return multitexid;
    }

    public static void deleteTextures(AbstractTexture atex, int texid)
    {
        MultiTexID multitexid = atex.multiTex;

        if (multitexid != null)
        {
            atex.multiTex = null;
            multiTexMap.remove(Integer.valueOf(multitexid.base));
            GlStateManager.deleteTexture(multitexid.norm);
            GlStateManager.deleteTexture(multitexid.spec);

            if (multitexid.base != texid)
            {
                SMCLog.warning("Error : MultiTexID.base mismatch: " + multitexid.base + ", texid: " + texid);
                GlStateManager.deleteTexture(multitexid.base);
            }
        }
    }

    public static void bindNSTextures(int normTex, int specTex)
    {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984)
        {
            GlStateManager.setActiveTexture(33986);
            GlStateManager.bindTexture(normTex);
            GlStateManager.setActiveTexture(33987);
            GlStateManager.bindTexture(specTex);
            GlStateManager.setActiveTexture(33984);
        }
    }

    public static void bindNSTextures(MultiTexID multiTex)
    {
        bindNSTextures(multiTex.norm, multiTex.spec);
    }

    public static void bindTextures(int baseTex, int normTex, int specTex)
    {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984)
        {
            GlStateManager.setActiveTexture(33986);
            GlStateManager.bindTexture(normTex);
            GlStateManager.setActiveTexture(33987);
            GlStateManager.bindTexture(specTex);
            GlStateManager.setActiveTexture(33984);
        }

        GlStateManager.bindTexture(baseTex);
    }

    public static void bindTextures(MultiTexID multiTex)
    {
        boundTex = multiTex;

        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984)
        {
            if (Shaders.configNormalMap)
            {
                GlStateManager.setActiveTexture(33986);
                GlStateManager.bindTexture(multiTex.norm);
            }

            if (Shaders.configSpecularMap)
            {
                GlStateManager.setActiveTexture(33987);
                GlStateManager.bindTexture(multiTex.spec);
            }

            GlStateManager.setActiveTexture(33984);
        }

        GlStateManager.bindTexture(multiTex.base);
    }

    public static void bindTexture(ITextureObject tex)
    {
        int i = tex.getGlTextureId();

        if (tex instanceof TextureMap)
        {
            Shaders.atlasSizeX = ((TextureMap)tex).atlasWidth;
            Shaders.atlasSizeY = ((TextureMap)tex).atlasHeight;
            bindTextures(tex.getMultiTexID());
        }
        else
        {
            Shaders.atlasSizeX = 0;
            Shaders.atlasSizeY = 0;
            bindTextures(tex.getMultiTexID());
        }
    }

    public static void bindTextureMapForUpdateAndRender(TextureManager tm, ResourceLocation resLoc)
    {
        TextureMap texturemap = (TextureMap)tm.getTexture(resLoc);
        Shaders.atlasSizeX = texturemap.atlasWidth;
        Shaders.atlasSizeY = texturemap.atlasHeight;
        bindTextures(updatingTex = texturemap.getMultiTexID());
    }

    public static void bindTextures(int baseTex)
    {
        MultiTexID multitexid = multiTexMap.get(Integer.valueOf(baseTex));
        bindTextures(multitexid);
    }

    public static void initDynamicTexture(int texID, int width, int height, DynamicTexture tex)
    {
        MultiTexID multitexid = tex.getMultiTexID();
        int[] aint = tex.getTextureData();
        int i = width * height;
        Arrays.fill(aint, i, i * 2, -8421377);
        Arrays.fill(aint, i * 2, i * 3, 0);
        TextureUtil.allocateTexture(multitexid.base, width, height);
        TextureUtil.setTextureBlurMipmap(false, false);
        TextureUtil.setTextureClamped(false);
        TextureUtil.allocateTexture(multitexid.norm, width, height);
        TextureUtil.setTextureBlurMipmap(false, false);
        TextureUtil.setTextureClamped(false);
        TextureUtil.allocateTexture(multitexid.spec, width, height);
        TextureUtil.setTextureBlurMipmap(false, false);
        TextureUtil.setTextureClamped(false);
        GlStateManager.bindTexture(multitexid.base);
    }

    public static void updateDynamicTexture(int texID, int[] src, int width, int height, DynamicTexture tex)
    {
        MultiTexID multitexid = tex.getMultiTexID();
        GlStateManager.bindTexture(multitexid.base);
        updateDynTexSubImage1(src, width, height, 0, 0, 0);
        GlStateManager.bindTexture(multitexid.norm);
        updateDynTexSubImage1(src, width, height, 0, 0, 1);
        GlStateManager.bindTexture(multitexid.spec);
        updateDynTexSubImage1(src, width, height, 0, 0, 2);
        GlStateManager.bindTexture(multitexid.base);
    }

    public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page)
    {
        int i = width * height;
        IntBuffer intbuffer = getIntBuffer(i);
        intbuffer.clear();
        int j = page * i;

        if (src.length >= j + i)
        {
            intbuffer.put(src, j, i).position(0).limit(i);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
            intbuffer.clear();
        }
    }

    public static ITextureObject createDefaultTexture()
    {
        DynamicTexture dynamictexture = new DynamicTexture(1, 1);
        dynamictexture.getTextureData()[0] = -1;
        dynamictexture.updateDynamicTexture();
        return dynamictexture;
    }

    public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, Stitcher stitcher, TextureMap tex)
    {
        SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
        updatingTextureMap = tex;
        tex.atlasWidth = width;
        tex.atlasHeight = height;
        MultiTexID multitexid = getMultiTexID(tex);
        updatingTex = multitexid;
        TextureUtil.allocateTextureImpl(multitexid.base, mipmapLevels, width, height);

        if (Shaders.configNormalMap)
        {
            TextureUtil.allocateTextureImpl(multitexid.norm, mipmapLevels, width, height);
        }

        if (Shaders.configSpecularMap)
        {
            TextureUtil.allocateTextureImpl(multitexid.spec, mipmapLevels, width, height);
        }

        GlStateManager.bindTexture(texID);
    }

    public static TextureAtlasSprite setSprite(TextureAtlasSprite tas)
    {
        updatingSprite = tas;
        return tas;
    }

    public static String setIconName(String name)
    {
        iconName = name;
        return name;
    }

    public static void uploadTexSubForLoadAtlas(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp)
    {
        TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
        boolean flag = false;

        if (Shaders.configNormalMap)
        {
            int[][] aint = readImageAndMipmaps(iconName + "_n", width, height, data.length, flag, -8421377);
            GlStateManager.bindTexture(updatingTex.norm);
            TextureUtil.uploadTextureMipmap(aint, width, height, xoffset, yoffset, linear, clamp);
        }

        if (Shaders.configSpecularMap)
        {
            int[][] aint1 = readImageAndMipmaps(iconName + "_s", width, height, data.length, flag, 0);
            GlStateManager.bindTexture(updatingTex.spec);
            TextureUtil.uploadTextureMipmap(aint1, width, height, xoffset, yoffset, linear, clamp);
        }

        GlStateManager.bindTexture(updatingTex.base);
    }

    public static int[][] readImageAndMipmaps(String name, int width, int height, int numLevels, boolean border, int defColor)
    {
        int[][] aint = new int[numLevels][];
        int[] aint1;
        aint[0] = aint1 = new int[width * height];
        boolean flag = false;
        BufferedImage bufferedimage = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(name)));

        if (bufferedimage != null)
        {
            int i = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();

            if (i + (border ? 16 : 0) == width)
            {
                flag = true;
                bufferedimage.getRGB(0, 0, i, i, aint1, 0, i);
            }
        }

        if (!flag)
        {
            Arrays.fill(aint1, defColor);
        }

        GlStateManager.bindTexture(updatingTex.spec);
        aint = genMipmapsSimple(aint.length - 1, width, aint);
        return aint;
    }

    public static BufferedImage readImage(ResourceLocation resLoc)
    {
        try
        {
            if (!Config.hasResource(resLoc))
            {
                return null;
            }
            else
            {
                InputStream inputstream = Config.getResourceStream(resLoc);

                if (inputstream == null)
                {
                    return null;
                }
                else
                {
                    BufferedImage bufferedimage = ImageIO.read(inputstream);
                    inputstream.close();
                    return bufferedimage;
                }
            }
        }
        catch (IOException var3)
        {
            return null;
        }
    }

    public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data)
    {
        for (int i = 1; i <= maxLevel; ++i)
        {
            if (data[i] == null)
            {
                int j = width >> i;
                int k = j * 2;
                int[] aint = data[i - 1];
                int[] aint1 = data[i] = new int[j * j];

                for (int i1 = 0; i1 < j; ++i1)
                {
                    for (int l = 0; l < j; ++l)
                    {
                        int j1 = i1 * 2 * k + l * 2;
                        aint1[i1 * j + l] = blend4Simple(aint[j1], aint[j1 + 1], aint[j1 + k], aint[j1 + k + 1]);
                    }
                }
            }
        }

        return data;
    }

    public static void uploadTexSub(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp)
    {
        TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);

        if (Shaders.configNormalMap || Shaders.configSpecularMap)
        {
            if (Shaders.configNormalMap)
            {
                GlStateManager.bindTexture(updatingTex.norm);
                uploadTexSub1(data, width, height, xoffset, yoffset, 1);
            }

            if (Shaders.configSpecularMap)
            {
                GlStateManager.bindTexture(updatingTex.spec);
                uploadTexSub1(data, width, height, xoffset, yoffset, 2);
            }

            GlStateManager.bindTexture(updatingTex.base);
        }
    }

    public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page)
    {
        int i = width * height;
        IntBuffer intbuffer = getIntBuffer(i);
        int j = src.length;
        int k = 0;
        int l = width;
        int i1 = height;
        int j1 = posX;

        for (int k1 = posY; l > 0 && i1 > 0 && k < j; ++k)
        {
            int l1 = l * i1;
            int[] aint = src[k];
            intbuffer.clear();

            if (aint.length >= l1 * (page + 1))
            {
                intbuffer.put(aint, l1 * page, l1).position(0).limit(l1);
                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, k, j1, k1, l, i1, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
            }

            l >>= 1;
            i1 >>= 1;
            j1 >>= 1;
            k1 >>= 1;
        }

        intbuffer.clear();
    }

    public static int blend4Alpha(int c0, int c1, int c2, int c3)
    {
        int i = c0 >>> 24 & 255;
        int j = c1 >>> 24 & 255;
        int k = c2 >>> 24 & 255;
        int l = c3 >>> 24 & 255;
        int i1 = i + j + k + l;
        int j1 = (i1 + 2) / 4;
        int k1;

        if (i1 != 0)
        {
            k1 = i1;
        }
        else
        {
            k1 = 4;
            i = 1;
            j = 1;
            k = 1;
            l = 1;
        }

        int l1 = (k1 + 1) / 2;
        int i2 = j1 << 24 | ((c0 >>> 16 & 255) * i + (c1 >>> 16 & 255) * j + (c2 >>> 16 & 255) * k + (c3 >>> 16 & 255) * l + l1) / k1 << 16 | ((c0 >>> 8 & 255) * i + (c1 >>> 8 & 255) * j + (c2 >>> 8 & 255) * k + (c3 >>> 8 & 255) * l + l1) / k1 << 8 | ((c0 >>> 0 & 255) * i + (c1 >>> 0 & 255) * j + (c2 >>> 0 & 255) * k + (c3 >>> 0 & 255) * l + l1) / k1 << 0;
        return i2;
    }

    public static int blend4Simple(int c0, int c1, int c2, int c3)
    {
        int i = ((c0 >>> 24 & 255) + (c1 >>> 24 & 255) + (c2 >>> 24 & 255) + (c3 >>> 24 & 255) + 2) / 4 << 24 | ((c0 >>> 16 & 255) + (c1 >>> 16 & 255) + (c2 >>> 16 & 255) + (c3 >>> 16 & 255) + 2) / 4 << 16 | ((c0 >>> 8 & 255) + (c1 >>> 8 & 255) + (c2 >>> 8 & 255) + (c3 >>> 8 & 255) + 2) / 4 << 8 | ((c0 >>> 0 & 255) + (c1 >>> 0 & 255) + (c2 >>> 0 & 255) + (c3 >>> 0 & 255) + 2) / 4 << 0;
        return i;
    }

    public static void genMipmapAlpha(int[] aint, int offset, int width, int height)
    {
        Math.min(width, height);
        int o2 = offset;
        int w2 = width;
        int h2 = height;
        int o1 = 0;
        int w1 = 0;
        int h1 = 0;
        int i;

        for (i = 0; w2 > 1 && h2 > 1; o2 = o1)
        {
            o1 = o2 + w2 * h2;
            w1 = w2 / 2;
            h1 = h2 / 2;

            for (int l1 = 0; l1 < h1; ++l1)
            {
                int i2 = o1 + l1 * w1;
                int j2 = o2 + l1 * 2 * w2;

                for (int k2 = 0; k2 < w1; ++k2)
                {
                    aint[i2 + k2] = blend4Alpha(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
                }
            }

            ++i;
            w2 = w1;
            h2 = h1;
        }

        while (i > 0)
        {
            --i;
            w2 = width >> i;
            h2 = height >> i;
            o2 = o1 - w2 * h2;
            int l2 = o2;

            for (int i3 = 0; i3 < h2; ++i3)
            {
                for (int j3 = 0; j3 < w2; ++j3)
                {
                    if (aint[l2] == 0)
                    {
                        aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 16777215;
                    }

                    ++l2;
                }
            }

            o1 = o2;
            w1 = w2;
        }
    }

    public static void genMipmapSimple(int[] aint, int offset, int width, int height)
    {
        Math.min(width, height);
        int o2 = offset;
        int w2 = width;
        int h2 = height;
        int o1 = 0;
        int w1 = 0;
        int h1 = 0;
        int i;

        for (i = 0; w2 > 1 && h2 > 1; o2 = o1)
        {
            o1 = o2 + w2 * h2;
            w1 = w2 / 2;
            h1 = h2 / 2;

            for (int l1 = 0; l1 < h1; ++l1)
            {
                int i2 = o1 + l1 * w1;
                int j2 = o2 + l1 * 2 * w2;

                for (int k2 = 0; k2 < w1; ++k2)
                {
                    aint[i2 + k2] = blend4Simple(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
                }
            }

            ++i;
            w2 = w1;
            h2 = h1;
        }

        while (i > 0)
        {
            --i;
            w2 = width >> i;
            h2 = height >> i;
            o2 = o1 - w2 * h2;
            int l2 = o2;

            for (int i3 = 0; i3 < h2; ++i3)
            {
                for (int j3 = 0; j3 < w2; ++j3)
                {
                    if (aint[l2] == 0)
                    {
                        aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 16777215;
                    }

                    ++l2;
                }
            }

            o1 = o2;
            w1 = w2;
        }
    }

    public static boolean isSemiTransparent(int[] aint, int width, int height)
    {
        int i = width * height;

        if (aint[0] >>> 24 == 255 && aint[i - 1] == 0)
        {
            return true;
        }
        else
        {
            for (int j = 0; j < i; ++j)
            {
                int k = aint[j] >>> 24;

                if (k != 0 && k != 255)
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static void updateSubTex1(int[] src, int width, int height, int posX, int posY)
    {
        int i = 0;
        int j = width;
        int k = height;
        int l = posX;

        for (int i1 = posY; j > 0 && k > 0; i1 /= 2)
        {
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, i, l, i1, 0, 0, j, k);
            ++i;
            j /= 2;
            k /= 2;
            l /= 2;
        }
    }

    public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp)
    {
        int i = linear ? 9729 : 9728;
        int j = clamp ? 10496 : 10497;
        int k = width * height;
        IntBuffer intbuffer = getIntBuffer(k);
        intbuffer.clear();
        intbuffer.put(src, 0, k).position(0).limit(k);
        GlStateManager.bindTexture(multiTex.base);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, j);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, j);
        intbuffer.put(src, k, k).position(0).limit(k);
        GlStateManager.bindTexture(multiTex.norm);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, j);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, j);
        intbuffer.put(src, k * 2, k).position(0).limit(k);
        GlStateManager.bindTexture(multiTex.spec);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, j);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, j);
        GlStateManager.bindTexture(multiTex.base);
    }

    public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp)
    {
        int i = width * height;
        IntBuffer intbuffer = getIntBuffer(i);
        intbuffer.clear();
        intbuffer.put(src, 0, i);
        intbuffer.position(0).limit(i);
        GlStateManager.bindTexture(multiTex.base);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);

        if (src.length == i * 3)
        {
            intbuffer.clear();
            intbuffer.put(src, i, i).position(0);
            intbuffer.position(0).limit(i);
        }

        GlStateManager.bindTexture(multiTex.norm);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);

        if (src.length == i * 3)
        {
            intbuffer.clear();
            intbuffer.put(src, i * 2, i);
            intbuffer.position(0).limit(i);
        }

        GlStateManager.bindTexture(multiTex.spec);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intbuffer);
        GlStateManager.setActiveTexture(33984);
    }

    public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName)
    {
        String s = location.getResourcePath();
        String[] astring = s.split(".png");
        String s1 = astring[0];
        return new ResourceLocation(location.getResourceDomain(), s1 + "_" + mapName + ".png");
    }

    public static void loadNSMap(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint)
    {
        if (Shaders.configNormalMap)
        {
            loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
        }

        if (Shaders.configSpecularMap)
        {
            loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
        }
    }

    public static void loadNSMap1(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset, int defaultColor)
    {
        boolean flag = false;

        try
        {
            IResource iresource = manager.getResource(location);
            BufferedImage bufferedimage = ImageIO.read(iresource.getInputStream());

            if (bufferedimage != null && bufferedimage.getWidth() == width && bufferedimage.getHeight() == height)
            {
                bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
                flag = true;
            }
        }
        catch (IOException var10)
        {
            ;
        }

        if (!flag)
        {
            Arrays.fill(aint, offset, offset + width * height, defaultColor);
        }
    }

    public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, IResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex)
    {
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int k = i * j;
        int[] aint = getIntArray(k * 3);
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        loadNSMap(resourceManager, location, i, j, aint);
        setupTexture(multiTex, aint, i, j, linear, clamp);
        return textureID;
    }

    public static void mergeImage(int[] aint, int dstoff, int srcoff, int size)
    {
    }

    public static int blendColor(int color1, int color2, int factor1)
    {
        int i = 255 - factor1;
        return ((color1 >>> 24 & 255) * factor1 + (color2 >>> 24 & 255) * i) / 255 << 24 | ((color1 >>> 16 & 255) * factor1 + (color2 >>> 16 & 255) * i) / 255 << 16 | ((color1 >>> 8 & 255) * factor1 + (color2 >>> 8 & 255) * i) / 255 << 8 | ((color1 >>> 0 & 255) * factor1 + (color2 >>> 0 & 255) * i) / 255 << 0;
    }

    public static void loadLayeredTexture(LayeredTexture tex, IResourceManager manager, List list)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int[] aint = null;

        for (Object s0 : list)
        {
        	String s = (String) s0;
            if (s != null)
            {
                try
                {
                    ResourceLocation resourcelocation = new ResourceLocation(s);
                    InputStream inputstream = manager.getResource(resourcelocation).getInputStream();
                    BufferedImage bufferedimage = ImageIO.read(inputstream);

                    if (k == 0)
                    {
                        i = bufferedimage.getWidth();
                        j = bufferedimage.getHeight();
                        k = i * j;
                        aint = createAIntImage(k, 0);
                    }

                    int[] aint1 = getIntArray(k * 3);
                    bufferedimage.getRGB(0, 0, i, j, aint1, 0, i);
                    loadNSMap(manager, resourcelocation, i, j, aint1);

                    for (int l = 0; l < k; ++l)
                    {
                        int i1 = aint1[l] >>> 24 & 255;
                        aint[k * 0 + l] = blendColor(aint1[k * 0 + l], aint[k * 0 + l], i1);
                        aint[k * 1 + l] = blendColor(aint1[k * 1 + l], aint[k * 1 + l], i1);
                        aint[k * 2 + l] = blendColor(aint1[k * 2 + l], aint[k * 2 + l], i1);
                    }
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }
        }

        setupTexture(tex.getMultiTexID(), aint, i, j, false, false);
    }

    static void updateTextureMinMagFilter()
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (itextureobject != null)
        {
            MultiTexID multitexid = itextureobject.getMultiTexID();
            GlStateManager.bindTexture(multitexid.base);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GlStateManager.bindTexture(multitexid.norm);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GlStateManager.bindTexture(multitexid.spec);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GlStateManager.bindTexture(0);
        }
    }

    public static IResource loadResource(IResourceManager manager, ResourceLocation location) throws IOException
    {
        resManager = manager;
        resLocation = location;
        return manager.getResource(location);
    }

    public static int[] loadAtlasSprite(BufferedImage bufferedimage, int startX, int startY, int w, int h, int[] aint, int offset, int scansize)
    {
        imageSize = w * h;
        bufferedimage.getRGB(startX, startY, w, h, aint, offset, scansize);
        loadNSMap(resManager, resLocation, w, h, aint);
        return aint;
    }

    public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex)
    {
        int i = src.length;
        int[][] aint = new int[i][];

        for (int j = 0; j < i; ++j)
        {
            int[] aint1 = src[j];

            if (aint1 != null)
            {
                int k = (width >> j) * (height >> j);
                int[] aint2 = new int[k * 3];
                aint[j] = aint2;
                int l = aint1.length / 3;
                int i1 = k * frameIndex;
                int j1 = 0;
                System.arraycopy(aint1, i1, aint2, j1, k);
                i1 = i1 + l;
                j1 = j1 + k;
                System.arraycopy(aint1, i1, aint2, j1, k);
                i1 = i1 + l;
                j1 = j1 + k;
                System.arraycopy(aint1, i1, aint2, j1, k);
            }
        }

        return aint;
    }

    public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height)
    {
        boolean flag = true;
        return src;
    }

    public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint)
    {
    }
}
