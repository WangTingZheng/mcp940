package net.minecraft.src;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ConnectedTexturesCompact
{
    private static final int COMPACT_NONE = 0;
    private static final int COMPACT_ALL = 1;
    private static final int COMPACT_V = 2;
    private static final int COMPACT_H = 3;
    private static final int COMPACT_HV = 4;

    public static BakedQuad[] getConnectedTextureCtmCompact(int p_getConnectedTextureCtmCompact_0_, ConnectedProperties p_getConnectedTextureCtmCompact_1_, int p_getConnectedTextureCtmCompact_2_, BakedQuad p_getConnectedTextureCtmCompact_3_, RenderEnv p_getConnectedTextureCtmCompact_4_)
    {
        if (p_getConnectedTextureCtmCompact_1_.ctmTileIndexes != null && p_getConnectedTextureCtmCompact_0_ >= 0 && p_getConnectedTextureCtmCompact_0_ < p_getConnectedTextureCtmCompact_1_.ctmTileIndexes.length)
        {
            int i = p_getConnectedTextureCtmCompact_1_.ctmTileIndexes[p_getConnectedTextureCtmCompact_0_];

            if (i >= 0 && i <= p_getConnectedTextureCtmCompact_1_.tileIcons.length)
            {
                return getQuadsCompact(i, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
            }
        }

        switch (p_getConnectedTextureCtmCompact_0_)
        {
            case 1:
                return getQuadsCompactH(0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 2:
                return getQuadsCompact(3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 3:
                return getQuadsCompactH(3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 4:
                return getQuadsCompact4(0, 3, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 5:
                return getQuadsCompact4(3, 0, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 6:
                return getQuadsCompact4(2, 4, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 7:
                return getQuadsCompact4(3, 3, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 8:
                return getQuadsCompact4(4, 1, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 9:
                return getQuadsCompact4(4, 4, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 10:
                return getQuadsCompact4(1, 4, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 11:
                return getQuadsCompact4(1, 1, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 12:
                return getQuadsCompactV(0, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 13:
                return getQuadsCompact4(0, 3, 2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 14:
                return getQuadsCompactV(3, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 15:
                return getQuadsCompact4(3, 0, 1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 16:
                return getQuadsCompact4(2, 4, 0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 17:
                return getQuadsCompact4(4, 2, 3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 18:
                return getQuadsCompact4(4, 4, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 19:
                return getQuadsCompact4(4, 2, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 20:
                return getQuadsCompact4(1, 4, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 21:
                return getQuadsCompact4(4, 4, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 22:
                return getQuadsCompact4(4, 4, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 23:
                return getQuadsCompact4(4, 1, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 24:
                return getQuadsCompact(2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 25:
                return getQuadsCompactH(2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 26:
                return getQuadsCompact(1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 27:
                return getQuadsCompactH(1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 28:
                return getQuadsCompact4(2, 4, 2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 29:
                return getQuadsCompact4(3, 3, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 30:
                return getQuadsCompact4(2, 1, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 31:
                return getQuadsCompact4(3, 3, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 32:
                return getQuadsCompact4(1, 1, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 33:
                return getQuadsCompact4(1, 1, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 34:
                return getQuadsCompact4(4, 1, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 35:
                return getQuadsCompact4(1, 4, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 36:
                return getQuadsCompactV(2, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 37:
                return getQuadsCompact4(2, 1, 0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 38:
                return getQuadsCompactV(1, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 39:
                return getQuadsCompact4(1, 2, 3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 40:
                return getQuadsCompact4(4, 1, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 41:
                return getQuadsCompact4(1, 2, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 42:
                return getQuadsCompact4(1, 4, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 43:
                return getQuadsCompact4(4, 2, 1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 44:
                return getQuadsCompact4(1, 4, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 45:
                return getQuadsCompact4(4, 1, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            case 46:
                return getQuadsCompact(4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);

            default:
                return getQuadsCompact(0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
        }
    }

    private static BakedQuad[] getQuadsCompactH(int p_getQuadsCompactH_0_, int p_getQuadsCompactH_1_, TextureAtlasSprite[] p_getQuadsCompactH_2_, int p_getQuadsCompactH_3_, BakedQuad p_getQuadsCompactH_4_, RenderEnv p_getQuadsCompactH_5_)
    {
        return getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, p_getQuadsCompactH_0_, ConnectedTexturesCompact.Dir.RIGHT, p_getQuadsCompactH_1_, p_getQuadsCompactH_2_, p_getQuadsCompactH_3_, p_getQuadsCompactH_4_, p_getQuadsCompactH_5_);
    }

    private static BakedQuad[] getQuadsCompactV(int p_getQuadsCompactV_0_, int p_getQuadsCompactV_1_, TextureAtlasSprite[] p_getQuadsCompactV_2_, int p_getQuadsCompactV_3_, BakedQuad p_getQuadsCompactV_4_, RenderEnv p_getQuadsCompactV_5_)
    {
        return getQuadsCompact(ConnectedTexturesCompact.Dir.UP, p_getQuadsCompactV_0_, ConnectedTexturesCompact.Dir.DOWN, p_getQuadsCompactV_1_, p_getQuadsCompactV_2_, p_getQuadsCompactV_3_, p_getQuadsCompactV_4_, p_getQuadsCompactV_5_);
    }

    private static BakedQuad[] getQuadsCompact4(int p_getQuadsCompact4_0_, int p_getQuadsCompact4_1_, int p_getQuadsCompact4_2_, int p_getQuadsCompact4_3_, TextureAtlasSprite[] p_getQuadsCompact4_4_, int p_getQuadsCompact4_5_, BakedQuad p_getQuadsCompact4_6_, RenderEnv p_getQuadsCompact4_7_)
    {
        if (p_getQuadsCompact4_0_ == p_getQuadsCompact4_1_)
        {
            return p_getQuadsCompact4_2_ == p_getQuadsCompact4_3_ ? getQuadsCompact(ConnectedTexturesCompact.Dir.UP, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.DOWN, p_getQuadsCompact4_2_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(ConnectedTexturesCompact.Dir.UP, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.DOWN_LEFT, p_getQuadsCompact4_2_, ConnectedTexturesCompact.Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
        }
        else if (p_getQuadsCompact4_2_ == p_getQuadsCompact4_3_)
        {
            return getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.UP_RIGHT, p_getQuadsCompact4_1_, ConnectedTexturesCompact.Dir.DOWN, p_getQuadsCompact4_2_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
        }
        else if (p_getQuadsCompact4_0_ == p_getQuadsCompact4_2_)
        {
            return p_getQuadsCompact4_1_ == p_getQuadsCompact4_3_ ? getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.RIGHT, p_getQuadsCompact4_1_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.UP_RIGHT, p_getQuadsCompact4_1_, ConnectedTexturesCompact.Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
        }
        else
        {
            return p_getQuadsCompact4_1_ == p_getQuadsCompact4_3_ ? getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.DOWN_LEFT, p_getQuadsCompact4_2_, ConnectedTexturesCompact.Dir.RIGHT, p_getQuadsCompact4_1_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, p_getQuadsCompact4_0_, ConnectedTexturesCompact.Dir.UP_RIGHT, p_getQuadsCompact4_1_, ConnectedTexturesCompact.Dir.DOWN_LEFT, p_getQuadsCompact4_2_, ConnectedTexturesCompact.Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
        }
    }

    private static BakedQuad[] getQuadsCompact(int p_getQuadsCompact_0_, TextureAtlasSprite[] p_getQuadsCompact_1_, BakedQuad p_getQuadsCompact_2_, RenderEnv p_getQuadsCompact_3_)
    {
        TextureAtlasSprite textureatlassprite = p_getQuadsCompact_1_[p_getQuadsCompact_0_];
        return ConnectedTextures.getQuads(textureatlassprite, p_getQuadsCompact_2_, p_getQuadsCompact_3_);
    }

    private static BakedQuad[] getQuadsCompact(ConnectedTexturesCompact.Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, ConnectedTexturesCompact.Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, TextureAtlasSprite[] p_getQuadsCompact_4_, int p_getQuadsCompact_5_, BakedQuad p_getQuadsCompact_6_, RenderEnv p_getQuadsCompact_7_)
    {
        BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_4_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_5_, p_getQuadsCompact_6_, p_getQuadsCompact_7_);
        BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_4_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_5_, p_getQuadsCompact_6_, p_getQuadsCompact_7_);
        return p_getQuadsCompact_7_.getArrayQuadsCtm(bakedquad, bakedquad1);
    }

    private static BakedQuad[] getQuadsCompact(ConnectedTexturesCompact.Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, ConnectedTexturesCompact.Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, ConnectedTexturesCompact.Dir p_getQuadsCompact_4_, int p_getQuadsCompact_5_, TextureAtlasSprite[] p_getQuadsCompact_6_, int p_getQuadsCompact_7_, BakedQuad p_getQuadsCompact_8_, RenderEnv p_getQuadsCompact_9_)
    {
        BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
        BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
        BakedQuad bakedquad2 = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_5_], p_getQuadsCompact_4_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
        return p_getQuadsCompact_9_.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2);
    }

    private static BakedQuad[] getQuadsCompact(ConnectedTexturesCompact.Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, ConnectedTexturesCompact.Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, ConnectedTexturesCompact.Dir p_getQuadsCompact_4_, int p_getQuadsCompact_5_, ConnectedTexturesCompact.Dir p_getQuadsCompact_6_, int p_getQuadsCompact_7_, TextureAtlasSprite[] p_getQuadsCompact_8_, int p_getQuadsCompact_9_, BakedQuad p_getQuadsCompact_10_, RenderEnv p_getQuadsCompact_11_)
    {
        BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
        BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
        BakedQuad bakedquad2 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_5_], p_getQuadsCompact_4_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
        BakedQuad bakedquad3 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_7_], p_getQuadsCompact_6_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
        return p_getQuadsCompact_11_.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2, bakedquad3);
    }

    private static BakedQuad getQuadCompact(TextureAtlasSprite p_getQuadCompact_0_, ConnectedTexturesCompact.Dir p_getQuadCompact_1_, int p_getQuadCompact_2_, BakedQuad p_getQuadCompact_3_, RenderEnv p_getQuadCompact_4_)
    {
        switch (p_getQuadCompact_1_)
        {
            case UP:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 16, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case UP_RIGHT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 0, 16, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case RIGHT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 0, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case DOWN_RIGHT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 8, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case DOWN:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 8, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case DOWN_LEFT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 8, 8, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case LEFT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 8, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            case UP_LEFT:
                return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 8, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);

            default:
                return p_getQuadCompact_3_;
        }
    }

    private static BakedQuad getQuadCompact(TextureAtlasSprite p_getQuadCompact_0_, ConnectedTexturesCompact.Dir p_getQuadCompact_1_, int p_getQuadCompact_2_, int p_getQuadCompact_3_, int p_getQuadCompact_4_, int p_getQuadCompact_5_, int p_getQuadCompact_6_, BakedQuad p_getQuadCompact_7_, RenderEnv p_getQuadCompact_8_)
    {
        Map[][] amap = ConnectedTextures.getSpriteQuadCompactMaps();

        if (amap == null)
        {
            return p_getQuadCompact_7_;
        }
        else
        {
            int i = p_getQuadCompact_0_.getIndexInMap();

            if (i >= 0 && i < amap.length)
            {
                Map[] amap1 = amap[i];

                if (amap1 == null)
                {
                    amap1 = new Map[ConnectedTexturesCompact.Dir.VALUES.length];
                    amap[i] = amap1;
                }

                Map<BakedQuad, BakedQuad> map = amap1[p_getQuadCompact_1_.ordinal()];

                if (map == null)
                {
                    map = new IdentityHashMap<BakedQuad, BakedQuad>(1);
                    amap1[p_getQuadCompact_1_.ordinal()] = map;
                }

                BakedQuad bakedquad = map.get(p_getQuadCompact_7_);

                if (bakedquad == null)
                {
                    bakedquad = makeSpriteQuadCompact(p_getQuadCompact_7_, p_getQuadCompact_0_, p_getQuadCompact_6_, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_, p_getQuadCompact_5_);
                    map.put(p_getQuadCompact_7_, bakedquad);
                }

                return bakedquad;
            }
            else
            {
                return p_getQuadCompact_7_;
            }
        }
    }

    private static BakedQuad makeSpriteQuadCompact(BakedQuad p_makeSpriteQuadCompact_0_, TextureAtlasSprite p_makeSpriteQuadCompact_1_, int p_makeSpriteQuadCompact_2_, int p_makeSpriteQuadCompact_3_, int p_makeSpriteQuadCompact_4_, int p_makeSpriteQuadCompact_5_, int p_makeSpriteQuadCompact_6_)
    {
        int[] aint = (int[])p_makeSpriteQuadCompact_0_.getVertexData().clone();
        TextureAtlasSprite textureatlassprite = p_makeSpriteQuadCompact_0_.getSprite();

        for (int i = 0; i < 4; ++i)
        {
            fixVertexCompact(aint, i, textureatlassprite, p_makeSpriteQuadCompact_1_, p_makeSpriteQuadCompact_2_, p_makeSpriteQuadCompact_3_, p_makeSpriteQuadCompact_4_, p_makeSpriteQuadCompact_5_, p_makeSpriteQuadCompact_6_);
        }

        BakedQuad bakedquad = new BakedQuad(aint, p_makeSpriteQuadCompact_0_.getTintIndex(), p_makeSpriteQuadCompact_0_.getFace(), p_makeSpriteQuadCompact_1_);
        return bakedquad;
    }

    private static void fixVertexCompact(int[] p_fixVertexCompact_0_, int p_fixVertexCompact_1_, TextureAtlasSprite p_fixVertexCompact_2_, TextureAtlasSprite p_fixVertexCompact_3_, int p_fixVertexCompact_4_, int p_fixVertexCompact_5_, int p_fixVertexCompact_6_, int p_fixVertexCompact_7_, int p_fixVertexCompact_8_)
    {
        int i = p_fixVertexCompact_0_.length / 4;
        int j = i * p_fixVertexCompact_1_;
        float f = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 4]);
        float f1 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 4 + 1]);
        double d0 = p_fixVertexCompact_2_.getSpriteU16(f);
        double d1 = p_fixVertexCompact_2_.getSpriteV16(f1);
        float f2 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 0]);
        float f3 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 1]);
        float f4 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 2]);
        float f5;
        float f6;

        switch (p_fixVertexCompact_4_)
        {
            case 0:
                f5 = f2;
                f6 = 1.0F - f4;
                break;

            case 1:
                f5 = f2;
                f6 = f4;
                break;

            case 2:
                f5 = 1.0F - f2;
                f6 = 1.0F - f3;
                break;

            case 3:
                f5 = f2;
                f6 = 1.0F - f3;
                break;

            case 4:
                f5 = f4;
                f6 = 1.0F - f3;
                break;

            case 5:
                f5 = 1.0F - f4;
                f6 = 1.0F - f3;
                break;

            default:
                return;
        }

        float f7 = 15.968F;
        float f8 = 15.968F;

        if (d0 < (double)p_fixVertexCompact_5_)
        {
            f5 = (float)((double)f5 + ((double)p_fixVertexCompact_5_ - d0) / (double)f7);
            d0 = (double)p_fixVertexCompact_5_;
        }

        if (d0 > (double)p_fixVertexCompact_7_)
        {
            f5 = (float)((double)f5 - (d0 - (double)p_fixVertexCompact_7_) / (double)f7);
            d0 = (double)p_fixVertexCompact_7_;
        }

        if (d1 < (double)p_fixVertexCompact_6_)
        {
            f6 = (float)((double)f6 + ((double)p_fixVertexCompact_6_ - d1) / (double)f8);
            d1 = (double)p_fixVertexCompact_6_;
        }

        if (d1 > (double)p_fixVertexCompact_8_)
        {
            f6 = (float)((double)f6 - (d1 - (double)p_fixVertexCompact_8_) / (double)f8);
            d1 = (double)p_fixVertexCompact_8_;
        }

        switch (p_fixVertexCompact_4_)
        {
            case 0:
                f2 = f5;
                f4 = 1.0F - f6;
                break;

            case 1:
                f2 = f5;
                f4 = f6;
                break;

            case 2:
                f2 = 1.0F - f5;
                f3 = 1.0F - f6;
                break;

            case 3:
                f2 = f5;
                f3 = 1.0F - f6;
                break;

            case 4:
                f4 = f5;
                f3 = 1.0F - f6;
                break;

            case 5:
                f4 = 1.0F - f5;
                f3 = 1.0F - f6;
                break;

            default:
                return;
        }

        p_fixVertexCompact_0_[j + 4] = Float.floatToRawIntBits(p_fixVertexCompact_3_.getInterpolatedU(d0));
        p_fixVertexCompact_0_[j + 4 + 1] = Float.floatToRawIntBits(p_fixVertexCompact_3_.getInterpolatedV(d1));
        p_fixVertexCompact_0_[j + 0] = Float.floatToRawIntBits(f2);
        p_fixVertexCompact_0_[j + 1] = Float.floatToRawIntBits(f3);
        p_fixVertexCompact_0_[j + 2] = Float.floatToRawIntBits(f4);
    }

    private static enum Dir
    {
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT,
        UP_LEFT;

        public static final ConnectedTexturesCompact.Dir[] VALUES = values();
    }
}
