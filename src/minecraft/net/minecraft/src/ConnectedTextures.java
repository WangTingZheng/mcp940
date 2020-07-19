package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;

public class ConnectedTextures
{
    private static Map[] spriteQuadMaps = null;
    private static Map[] spriteQuadFullMaps = null;
    private static Map[][] spriteQuadCompactMaps = (Map[][])null;
    private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
    private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
    private static boolean multipass = false;
    protected static final int UNKNOWN = -1;
    protected static final int Y_NEG_DOWN = 0;
    protected static final int Y_POS_UP = 1;
    protected static final int Z_NEG_NORTH = 2;
    protected static final int Z_POS_SOUTH = 3;
    protected static final int X_NEG_WEST = 4;
    protected static final int X_POS_EAST = 5;
    private static final int Y_AXIS = 0;
    private static final int Z_AXIS = 1;
    private static final int X_AXIS = 2;
    public static final IBlockState AIR_DEFAULT_STATE = Blocks.AIR.getDefaultState();
    private static TextureAtlasSprite emptySprite = null;
    private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] {BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH};
    private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] {BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH};
    private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] {BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP};
    private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] {BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP};
    private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] {BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP};
    private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] {BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP};
    private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] {BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN};
    private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[] {BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN};
    private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] {BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST};
    private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] {BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST};
    private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] {BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST};
    private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] {BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST};
    private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] {BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH};
    private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] {BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH};
    private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] {BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST};
    private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[] {BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH};

    public static synchronized BakedQuad[] getConnectedTexture(IBlockAccess p_getConnectedTexture_0_, IBlockState p_getConnectedTexture_1_, BlockPos p_getConnectedTexture_2_, BakedQuad p_getConnectedTexture_3_, RenderEnv p_getConnectedTexture_4_)
    {
        TextureAtlasSprite textureatlassprite = p_getConnectedTexture_3_.getSprite();

        if (textureatlassprite == null)
        {
            return p_getConnectedTexture_4_.getArrayQuadsCtm(p_getConnectedTexture_3_);
        }
        else
        {
            Block block = p_getConnectedTexture_1_.getBlock();

            if (skipConnectedTexture(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, p_getConnectedTexture_4_))
            {
                p_getConnectedTexture_3_ = getQuad(emptySprite, p_getConnectedTexture_3_);
                return p_getConnectedTexture_4_.getArrayQuadsCtm(p_getConnectedTexture_3_);
            }
            else
            {
                EnumFacing enumfacing = p_getConnectedTexture_3_.getFace();
                BakedQuad[] abakedquad = getConnectedTextureMultiPass(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, enumfacing, p_getConnectedTexture_3_, p_getConnectedTexture_4_);
                return abakedquad;
            }
        }
    }

    private static boolean skipConnectedTexture(IBlockAccess p_skipConnectedTexture_0_, IBlockState p_skipConnectedTexture_1_, BlockPos p_skipConnectedTexture_2_, BakedQuad p_skipConnectedTexture_3_, RenderEnv p_skipConnectedTexture_4_)
    {
        Block block = p_skipConnectedTexture_1_.getBlock();

        if (block instanceof BlockPane)
        {
            EnumFacing enumfacing = p_skipConnectedTexture_3_.getFace();

            if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN)
            {
                return false;
            }

            if (!p_skipConnectedTexture_3_.isFaceQuad())
            {
                return false;
            }

            BlockPos blockpos = p_skipConnectedTexture_2_.offset(p_skipConnectedTexture_3_.getFace());
            IBlockState iblockstate = p_skipConnectedTexture_0_.getBlockState(blockpos);

            if (iblockstate.getBlock() != block)
            {
                return false;
            }

            if (block == Blocks.STAINED_GLASS_PANE && iblockstate.getValue(BlockStainedGlassPane.COLOR) != p_skipConnectedTexture_1_.getValue(BlockStainedGlassPane.COLOR))
            {
                return false;
            }

            iblockstate = iblockstate.getActualState(p_skipConnectedTexture_0_, blockpos);
            double d0 = (double)p_skipConnectedTexture_3_.getMidX();

            if (d0 < 0.4D)
            {
                if (((Boolean)iblockstate.getValue(BlockPane.WEST)).booleanValue())
                {
                    return true;
                }
            }
            else if (d0 > 0.6D)
            {
                if (((Boolean)iblockstate.getValue(BlockPane.EAST)).booleanValue())
                {
                    return true;
                }
            }
            else
            {
                double d1 = p_skipConnectedTexture_3_.getMidZ();

                if (d1 < 0.4D)
                {
                    if (((Boolean)iblockstate.getValue(BlockPane.NORTH)).booleanValue())
                    {
                        return true;
                    }
                }
                else
                {
                    if (d1 <= 0.6D)
                    {
                        return true;
                    }

                    if (((Boolean)iblockstate.getValue(BlockPane.SOUTH)).booleanValue())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected static BakedQuad[] getQuads(TextureAtlasSprite p_getQuads_0_, BakedQuad p_getQuads_1_, RenderEnv p_getQuads_2_)
    {
        if (p_getQuads_0_ == null)
        {
            return null;
        }
        else
        {
            BakedQuad bakedquad = getQuad(p_getQuads_0_, p_getQuads_1_);
            BakedQuad[] abakedquad = p_getQuads_2_.getArrayQuadsCtm(bakedquad);
            return abakedquad;
        }
    }

    private static BakedQuad getQuad(TextureAtlasSprite p_getQuad_0_, BakedQuad p_getQuad_1_)
    {
        if (spriteQuadMaps == null)
        {
            return p_getQuad_1_;
        }
        else
        {
            int i = p_getQuad_0_.getIndexInMap();

            if (i >= 0 && i < spriteQuadMaps.length)
            {
                Map map = spriteQuadMaps[i];

                if (map == null)
                {
                    map = new IdentityHashMap(1);
                    spriteQuadMaps[i] = map;
                }

                BakedQuad bakedquad = (BakedQuad)map.get(p_getQuad_1_);

                if (bakedquad == null)
                {
                    bakedquad = makeSpriteQuad(p_getQuad_1_, p_getQuad_0_);
                    map.put(p_getQuad_1_, bakedquad);
                }

                return bakedquad;
            }
            else
            {
                return p_getQuad_1_;
            }
        }
    }

    private static BakedQuad getQuadFull(TextureAtlasSprite p_getQuadFull_0_, BakedQuad p_getQuadFull_1_, int p_getQuadFull_2_)
    {
        if (spriteQuadFullMaps == null)
        {
            return p_getQuadFull_1_;
        }
        else
        {
            int i = p_getQuadFull_0_.getIndexInMap();

            if (i >= 0 && i < spriteQuadFullMaps.length)
            {
                Map map = spriteQuadFullMaps[i];

                if (map == null)
                {
                    map = new EnumMap(EnumFacing.class);
                    spriteQuadFullMaps[i] = map;
                }

                EnumFacing enumfacing = p_getQuadFull_1_.getFace();
                BakedQuad bakedquad = (BakedQuad)map.get(enumfacing);

                if (bakedquad == null)
                {
                    bakedquad = BlockModelUtils.makeBakedQuad(enumfacing, p_getQuadFull_0_, p_getQuadFull_2_);
                    map.put(enumfacing, bakedquad);
                }

                return bakedquad;
            }
            else
            {
                return p_getQuadFull_1_;
            }
        }
    }

    private static BakedQuad makeSpriteQuad(BakedQuad p_makeSpriteQuad_0_, TextureAtlasSprite p_makeSpriteQuad_1_)
    {
        int[] aint = (int[])p_makeSpriteQuad_0_.getVertexData().clone();
        TextureAtlasSprite textureatlassprite = p_makeSpriteQuad_0_.getSprite();

        for (int i = 0; i < 4; ++i)
        {
            fixVertex(aint, i, textureatlassprite, p_makeSpriteQuad_1_);
        }

        BakedQuad bakedquad = new BakedQuad(aint, p_makeSpriteQuad_0_.getTintIndex(), p_makeSpriteQuad_0_.getFace(), p_makeSpriteQuad_1_);
        return bakedquad;
    }

    private static void fixVertex(int[] p_fixVertex_0_, int p_fixVertex_1_, TextureAtlasSprite p_fixVertex_2_, TextureAtlasSprite p_fixVertex_3_)
    {
        int i = p_fixVertex_0_.length / 4;
        int j = i * p_fixVertex_1_;
        float f = Float.intBitsToFloat(p_fixVertex_0_[j + 4]);
        float f1 = Float.intBitsToFloat(p_fixVertex_0_[j + 4 + 1]);
        double d0 = p_fixVertex_2_.getSpriteU16(f);
        double d1 = p_fixVertex_2_.getSpriteV16(f1);
        p_fixVertex_0_[j + 4] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedU(d0));
        p_fixVertex_0_[j + 4 + 1] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedV(d1));
    }

    private static BakedQuad[] getConnectedTextureMultiPass(IBlockAccess p_getConnectedTextureMultiPass_0_, IBlockState p_getConnectedTextureMultiPass_1_, BlockPos p_getConnectedTextureMultiPass_2_, EnumFacing p_getConnectedTextureMultiPass_3_, BakedQuad p_getConnectedTextureMultiPass_4_, RenderEnv p_getConnectedTextureMultiPass_5_)
    {
        BakedQuad[] abakedquad = getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, p_getConnectedTextureMultiPass_4_, true, 0, p_getConnectedTextureMultiPass_5_);

        if (!multipass)
        {
            return abakedquad;
        }
        else if (abakedquad.length == 1 && abakedquad[0] == p_getConnectedTextureMultiPass_4_)
        {
            return abakedquad;
        }
        else
        {
            List<BakedQuad> list = p_getConnectedTextureMultiPass_5_.getListQuadsCtmMultipass(abakedquad);

            for (int i = 0; i < list.size(); ++i)
            {
                BakedQuad bakedquad = list.get(i);
                BakedQuad bakedquad1 = bakedquad;

                for (int j = 0; j < 3; ++j)
                {
                    BakedQuad[] abakedquad1 = getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, bakedquad1, false, j + 1, p_getConnectedTextureMultiPass_5_);

                    if (abakedquad1.length != 1 || abakedquad1[0] == bakedquad1)
                    {
                        break;
                    }

                    bakedquad1 = abakedquad1[0];
                }

                list.set(i, bakedquad1);
            }

            for (int k = 0; k < abakedquad.length; ++k)
            {
                abakedquad[k] = list.get(k);
            }

            return abakedquad;
        }
    }

    public static BakedQuad[] getConnectedTextureSingle(IBlockAccess p_getConnectedTextureSingle_0_, IBlockState p_getConnectedTextureSingle_1_, BlockPos p_getConnectedTextureSingle_2_, EnumFacing p_getConnectedTextureSingle_3_, BakedQuad p_getConnectedTextureSingle_4_, boolean p_getConnectedTextureSingle_5_, int p_getConnectedTextureSingle_6_, RenderEnv p_getConnectedTextureSingle_7_)
    {
        Block block = p_getConnectedTextureSingle_1_.getBlock();

        if (!(p_getConnectedTextureSingle_1_ instanceof BlockStateBase))
        {
            return p_getConnectedTextureSingle_7_.getArrayQuadsCtm(p_getConnectedTextureSingle_4_);
        }
        else
        {
            BlockStateBase blockstatebase = (BlockStateBase)p_getConnectedTextureSingle_1_;
            TextureAtlasSprite textureatlassprite = p_getConnectedTextureSingle_4_.getSprite();

            if (tileProperties != null)
            {
                int i = textureatlassprite.getIndexInMap();

                if (i >= 0 && i < tileProperties.length)
                {
                    ConnectedProperties[] aconnectedproperties = tileProperties[i];

                    if (aconnectedproperties != null)
                    {
                        int j = getSide(p_getConnectedTextureSingle_3_);

                        for (int k = 0; k < aconnectedproperties.length; ++k)
                        {
                            ConnectedProperties connectedproperties = aconnectedproperties[k];

                            if (connectedproperties != null && connectedproperties.matchesBlockId(blockstatebase.getBlockId()))
                            {
                                BakedQuad[] abakedquad = getConnectedTexture(connectedproperties, p_getConnectedTextureSingle_0_, blockstatebase, p_getConnectedTextureSingle_2_, j, p_getConnectedTextureSingle_4_, p_getConnectedTextureSingle_6_, p_getConnectedTextureSingle_7_);

                                if (abakedquad != null)
                                {
                                    return abakedquad;
                                }
                            }
                        }
                    }
                }
            }

            if (blockProperties != null && p_getConnectedTextureSingle_5_)
            {
                int l = p_getConnectedTextureSingle_7_.getBlockId();

                if (l >= 0 && l < blockProperties.length)
                {
                    ConnectedProperties[] aconnectedproperties1 = blockProperties[l];

                    if (aconnectedproperties1 != null)
                    {
                        int i1 = getSide(p_getConnectedTextureSingle_3_);

                        for (int j1 = 0; j1 < aconnectedproperties1.length; ++j1)
                        {
                            ConnectedProperties connectedproperties1 = aconnectedproperties1[j1];

                            if (connectedproperties1 != null && connectedproperties1.matchesIcon(textureatlassprite))
                            {
                                BakedQuad[] abakedquad1 = getConnectedTexture(connectedproperties1, p_getConnectedTextureSingle_0_, blockstatebase, p_getConnectedTextureSingle_2_, i1, p_getConnectedTextureSingle_4_, p_getConnectedTextureSingle_6_, p_getConnectedTextureSingle_7_);

                                if (abakedquad1 != null)
                                {
                                    return abakedquad1;
                                }
                            }
                        }
                    }
                }
            }

            return p_getConnectedTextureSingle_7_.getArrayQuadsCtm(p_getConnectedTextureSingle_4_);
        }
    }

    public static int getSide(EnumFacing p_getSide_0_)
    {
        if (p_getSide_0_ == null)
        {
            return -1;
        }
        else
        {
            switch (p_getSide_0_)
            {
                case DOWN:
                    return 0;

                case UP:
                    return 1;

                case EAST:
                    return 5;

                case WEST:
                    return 4;

                case NORTH:
                    return 2;

                case SOUTH:
                    return 3;

                default:
                    return -1;
            }
        }
    }

    private static EnumFacing getFacing(int p_getFacing_0_)
    {
        switch (p_getFacing_0_)
        {
            case 0:
                return EnumFacing.DOWN;

            case 1:
                return EnumFacing.UP;

            case 2:
                return EnumFacing.NORTH;

            case 3:
                return EnumFacing.SOUTH;

            case 4:
                return EnumFacing.WEST;

            case 5:
                return EnumFacing.EAST;

            default:
                return EnumFacing.UP;
        }
    }

    private static BakedQuad[] getConnectedTexture(ConnectedProperties p_getConnectedTexture_0_, IBlockAccess p_getConnectedTexture_1_, BlockStateBase p_getConnectedTexture_2_, BlockPos p_getConnectedTexture_3_, int p_getConnectedTexture_4_, BakedQuad p_getConnectedTexture_5_, int p_getConnectedTexture_6_, RenderEnv p_getConnectedTexture_7_)
    {
        int i = 0;
        int j = p_getConnectedTexture_2_.getMetadata();
        int k = j;
        Block block = p_getConnectedTexture_2_.getBlock();

        if (block instanceof BlockRotatedPillar)
        {
            i = getWoodAxis(p_getConnectedTexture_4_, j);

            if (p_getConnectedTexture_0_.getMetadataMax() <= 3)
            {
                k = j & 3;
            }
        }

        if (block instanceof BlockQuartz)
        {
            i = getQuartzAxis(p_getConnectedTexture_4_, j);

            if (p_getConnectedTexture_0_.getMetadataMax() <= 2 && k > 2)
            {
                k = 2;
            }
        }

        if (!p_getConnectedTexture_0_.matchesBlock(p_getConnectedTexture_2_.getBlockId(), k))
        {
            return null;
        }
        else
        {
            if (p_getConnectedTexture_4_ >= 0 && p_getConnectedTexture_0_.faces != 63)
            {
                int l = p_getConnectedTexture_4_;

                if (i != 0)
                {
                    l = fixSideByAxis(p_getConnectedTexture_4_, i);
                }

                if ((1 << l & p_getConnectedTexture_0_.faces) == 0)
                {
                    return null;
                }
            }

            int i1 = p_getConnectedTexture_3_.getY();

            if (i1 >= p_getConnectedTexture_0_.minHeight && i1 <= p_getConnectedTexture_0_.maxHeight)
            {
                if (p_getConnectedTexture_0_.biomes != null)
                {
                    Biome biome = p_getConnectedTexture_1_.getBiome(p_getConnectedTexture_3_);

                    if (!p_getConnectedTexture_0_.matchesBiome(biome))
                    {
                        return null;
                    }
                }

                TextureAtlasSprite textureatlassprite = p_getConnectedTexture_5_.getSprite();

                switch (p_getConnectedTexture_0_.method)
                {
                    case 1:
                        return getQuads(getConnectedTextureCtm(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j, p_getConnectedTexture_7_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 2:
                        return getQuads(getConnectedTextureHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 3:
                        return getQuads(getConnectedTextureTop(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 4:
                        return getQuads(getConnectedTextureRandom(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 5:
                        return getQuads(getConnectedTextureRepeat(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 6:
                        return getQuads(getConnectedTextureVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 7:
                        return getQuads(getConnectedTextureFixed(p_getConnectedTexture_0_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 8:
                        return getQuads(getConnectedTextureHorizontalVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 9:
                        return getQuads(getConnectedTextureVerticalHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);

                    case 10:
                        if (p_getConnectedTexture_6_ == 0)
                        {
                            return getConnectedTextureCtmCompact(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, p_getConnectedTexture_5_, j, p_getConnectedTexture_7_);
                        }

                    default:
                        return null;

                    case 11:
                        return getConnectedTextureOverlay(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, p_getConnectedTexture_5_, j, p_getConnectedTexture_7_);
                }
            }
            else
            {
                return null;
            }
        }
    }

    private static int fixSideByAxis(int p_fixSideByAxis_0_, int p_fixSideByAxis_1_)
    {
        switch (p_fixSideByAxis_1_)
        {
            case 0:
                return p_fixSideByAxis_0_;

            case 1:
                switch (p_fixSideByAxis_0_)
                {
                    case 0:
                        return 2;

                    case 1:
                        return 3;

                    case 2:
                        return 1;

                    case 3:
                        return 0;

                    default:
                        return p_fixSideByAxis_0_;
                }

            case 2:
                switch (p_fixSideByAxis_0_)
                {
                    case 0:
                        return 4;

                    case 1:
                        return 5;

                    case 2:
                    case 3:
                    default:
                        return p_fixSideByAxis_0_;

                    case 4:
                        return 1;

                    case 5:
                        return 0;
                }

            default:
                return p_fixSideByAxis_0_;
        }
    }

    private static int getWoodAxis(int p_getWoodAxis_0_, int p_getWoodAxis_1_)
    {
        int i = (p_getWoodAxis_1_ & 12) >> 2;

        switch (i)
        {
            case 1:
                return 2;

            case 2:
                return 1;

            default:
                return 0;
        }
    }

    private static int getQuartzAxis(int p_getQuartzAxis_0_, int p_getQuartzAxis_1_)
    {
        switch (p_getQuartzAxis_1_)
        {
            case 3:
                return 2;

            case 4:
                return 1;

            default:
                return 0;
        }
    }

    private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties p_getConnectedTextureRandom_0_, BlockPos p_getConnectedTextureRandom_1_, int p_getConnectedTextureRandom_2_)
    {
        if (p_getConnectedTextureRandom_0_.tileIcons.length == 1)
        {
            return p_getConnectedTextureRandom_0_.tileIcons[0];
        }
        else
        {
            int i = p_getConnectedTextureRandom_2_ / p_getConnectedTextureRandom_0_.symmetry * p_getConnectedTextureRandom_0_.symmetry;
            int j = Config.getRandom(p_getConnectedTextureRandom_1_, i) & Integer.MAX_VALUE;
            int k = 0;

            if (p_getConnectedTextureRandom_0_.weights == null)
            {
                k = j % p_getConnectedTextureRandom_0_.tileIcons.length;
            }
            else
            {
                int l = j % p_getConnectedTextureRandom_0_.sumAllWeights;
                int[] aint = p_getConnectedTextureRandom_0_.sumWeights;

                for (int i1 = 0; i1 < aint.length; ++i1)
                {
                    if (l < aint[i1])
                    {
                        k = i1;
                        break;
                    }
                }
            }

            return p_getConnectedTextureRandom_0_.tileIcons[k];
        }
    }

    private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties p_getConnectedTextureFixed_0_)
    {
        return p_getConnectedTextureFixed_0_.tileIcons[0];
    }

    private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties p_getConnectedTextureRepeat_0_, BlockPos p_getConnectedTextureRepeat_1_, int p_getConnectedTextureRepeat_2_)
    {
        if (p_getConnectedTextureRepeat_0_.tileIcons.length == 1)
        {
            return p_getConnectedTextureRepeat_0_.tileIcons[0];
        }
        else
        {
            int i = p_getConnectedTextureRepeat_1_.getX();
            int j = p_getConnectedTextureRepeat_1_.getY();
            int k = p_getConnectedTextureRepeat_1_.getZ();
            int l = 0;
            int i1 = 0;

            switch (p_getConnectedTextureRepeat_2_)
            {
                case 0:
                    l = i;
                    i1 = k;
                    break;

                case 1:
                    l = i;
                    i1 = k;
                    break;

                case 2:
                    l = -i - 1;
                    i1 = -j;
                    break;

                case 3:
                    l = i;
                    i1 = -j;
                    break;

                case 4:
                    l = k;
                    i1 = -j;
                    break;

                case 5:
                    l = -k - 1;
                    i1 = -j;
            }

            l = l % p_getConnectedTextureRepeat_0_.width;
            i1 = i1 % p_getConnectedTextureRepeat_0_.height;

            if (l < 0)
            {
                l += p_getConnectedTextureRepeat_0_.width;
            }

            if (i1 < 0)
            {
                i1 += p_getConnectedTextureRepeat_0_.height;
            }

            int j1 = i1 * p_getConnectedTextureRepeat_0_.width + l;
            return p_getConnectedTextureRepeat_0_.tileIcons[j1];
        }
    }

    private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties p_getConnectedTextureCtm_0_, IBlockAccess p_getConnectedTextureCtm_1_, IBlockState p_getConnectedTextureCtm_2_, BlockPos p_getConnectedTextureCtm_3_, int p_getConnectedTextureCtm_4_, int p_getConnectedTextureCtm_5_, TextureAtlasSprite p_getConnectedTextureCtm_6_, int p_getConnectedTextureCtm_7_, RenderEnv p_getConnectedTextureCtm_8_)
    {
        int i = getConnectedTextureCtmIndex(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_, p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_, p_getConnectedTextureCtm_7_, p_getConnectedTextureCtm_8_);
        return p_getConnectedTextureCtm_0_.tileIcons[i];
    }

    private static BakedQuad[] getConnectedTextureCtmCompact(ConnectedProperties p_getConnectedTextureCtmCompact_0_, IBlockAccess p_getConnectedTextureCtmCompact_1_, IBlockState p_getConnectedTextureCtmCompact_2_, BlockPos p_getConnectedTextureCtmCompact_3_, int p_getConnectedTextureCtmCompact_4_, int p_getConnectedTextureCtmCompact_5_, BakedQuad p_getConnectedTextureCtmCompact_6_, int p_getConnectedTextureCtmCompact_7_, RenderEnv p_getConnectedTextureCtmCompact_8_)
    {
        TextureAtlasSprite textureatlassprite = p_getConnectedTextureCtmCompact_6_.getSprite();
        int i = getConnectedTextureCtmIndex(p_getConnectedTextureCtmCompact_0_, p_getConnectedTextureCtmCompact_1_, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_, p_getConnectedTextureCtmCompact_5_, textureatlassprite, p_getConnectedTextureCtmCompact_7_, p_getConnectedTextureCtmCompact_8_);
        return ConnectedTexturesCompact.getConnectedTextureCtmCompact(i, p_getConnectedTextureCtmCompact_0_, p_getConnectedTextureCtmCompact_5_, p_getConnectedTextureCtmCompact_6_, p_getConnectedTextureCtmCompact_8_);
    }

    private static BakedQuad[] getConnectedTextureOverlay(ConnectedProperties p_getConnectedTextureOverlay_0_, IBlockAccess p_getConnectedTextureOverlay_1_, IBlockState p_getConnectedTextureOverlay_2_, BlockPos p_getConnectedTextureOverlay_3_, int p_getConnectedTextureOverlay_4_, int p_getConnectedTextureOverlay_5_, BakedQuad p_getConnectedTextureOverlay_6_, int p_getConnectedTextureOverlay_7_, RenderEnv p_getConnectedTextureOverlay_8_)
    {
        if (!p_getConnectedTextureOverlay_6_.isFullQuad())
        {
            return null;
        }
        else
        {
            TextureAtlasSprite textureatlassprite = p_getConnectedTextureOverlay_6_.getSprite();
            BlockDir[] ablockdir = getSideDirections(p_getConnectedTextureOverlay_5_, p_getConnectedTextureOverlay_4_);
            boolean[] aboolean = p_getConnectedTextureOverlay_8_.getBorderFlags();

            for (int i = 0; i < 4; ++i)
            {
                aboolean[i] = isNeighbourOverlay(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir[i].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
            }

            ListQuadsOverlay listquadsoverlay = p_getConnectedTextureOverlay_8_.getListQuadsOverlay(p_getConnectedTextureOverlay_0_.layer);
            Object dirEdges;

            try
            {
                if (!aboolean[0] || !aboolean[1] || !aboolean[2] || !aboolean[3])
                {
                    if (aboolean[0] && aboolean[1] && aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[5], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        dirEdges = null;
                        return (BakedQuad[])dirEdges;
                    }

                    if (aboolean[0] && aboolean[2] && aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[6], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        dirEdges = null;
                        return (BakedQuad[])dirEdges;
                    }

                    if (aboolean[1] && aboolean[2] && aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[12], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        dirEdges = null;
                        return (BakedQuad[])dirEdges;
                    }

                    if (aboolean[0] && aboolean[1] && aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[13], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        dirEdges = null;
                        return (BakedQuad[])dirEdges;
                    }

                    BlockDir[] ablockdir1 = getEdgeDirections(p_getConnectedTextureOverlay_5_, p_getConnectedTextureOverlay_4_);
                    boolean[] aboolean1 = p_getConnectedTextureOverlay_8_.getBorderFlags2();

                    for (int j = 0; j < 4; ++j)
                    {
                        aboolean1[j] = isNeighbourOverlay(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir1[j].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
                    }

                    if (aboolean[1] && aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[3], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);

                        if (aboolean1[3])
                        {
                            listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[16], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        }

                        Object object4 = null;
                        return (BakedQuad[])object4;
                    }

                    if (aboolean[0] && aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[4], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);

                        if (aboolean1[2])
                        {
                            listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[14], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        }

                        Object object3 = null;
                        return (BakedQuad[])object3;
                    }

                    if (aboolean[1] && aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[10], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);

                        if (aboolean1[1])
                        {
                            listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[2], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        }

                        Object object2 = null;
                        return (BakedQuad[])object2;
                    }

                    if (aboolean[0] && aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[11], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);

                        if (aboolean1[0])
                        {
                            listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[0], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                        }

                        Object object1 = null;
                        return (BakedQuad[])object1;
                    }

                    boolean[] aboolean2 = p_getConnectedTextureOverlay_8_.getBorderFlags3();

                    for (int k = 0; k < 4; ++k)
                    {
                        aboolean2[k] = isNeighbourMatching(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir[k].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
                    }

                    if (aboolean[0])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[9], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean[1])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[7], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[1], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[15], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean1[0] && (aboolean2[1] || aboolean2[2]) && !aboolean[1] && !aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[0], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean1[1] && (aboolean2[0] || aboolean2[2]) && !aboolean[0] && !aboolean[2])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[2], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean1[2] && (aboolean2[1] || aboolean2[3]) && !aboolean[1] && !aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[14], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    if (aboolean1[3] && (aboolean2[0] || aboolean2[3]) && !aboolean[0] && !aboolean[3])
                    {
                        listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[16], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                    }

                    Object object5 = null;
                    return (BakedQuad[])object5;
                }

                listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[8], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
                dirEdges = null;
            }
            finally
            {
                if (listquadsoverlay.size() > 0)
                {
                    p_getConnectedTextureOverlay_8_.setOverlaysRendered(true);
                }
            }

            return (BakedQuad[])dirEdges;
        }
    }

    private static BlockDir[] getSideDirections(int p_getSideDirections_0_, int p_getSideDirections_1_)
    {
        switch (p_getSideDirections_0_)
        {
            case 0:
                return SIDES_Y_NEG_DOWN;

            case 1:
                return SIDES_Y_POS_UP;

            case 2:
                if (p_getSideDirections_1_ == 1)
                {
                    return SIDES_Z_NEG_NORTH_Z_AXIS;
                }

                return SIDES_Z_NEG_NORTH;

            case 3:
                return SIDES_Z_POS_SOUTH;

            case 4:
                return SIDES_X_NEG_WEST;

            case 5:
                if (p_getSideDirections_1_ == 2)
                {
                    return SIDES_X_POS_EAST_X_AXIS;
                }

                return SIDES_X_POS_EAST;

            default:
                throw new IllegalArgumentException("Unknown side: " + p_getSideDirections_0_);
        }
    }

    private static BlockDir[] getEdgeDirections(int p_getEdgeDirections_0_, int p_getEdgeDirections_1_)
    {
        switch (p_getEdgeDirections_0_)
        {
            case 0:
                return EDGES_Y_NEG_DOWN;

            case 1:
                return EDGES_Y_POS_UP;

            case 2:
                if (p_getEdgeDirections_1_ == 1)
                {
                    return EDGES_Z_NEG_NORTH_Z_AXIS;
                }

                return EDGES_Z_NEG_NORTH;

            case 3:
                return EDGES_Z_POS_SOUTH;

            case 4:
                return EDGES_X_NEG_WEST;

            case 5:
                if (p_getEdgeDirections_1_ == 2)
                {
                    return EDGES_X_POS_EAST_X_AXIS;
                }

                return EDGES_X_POS_EAST;

            default:
                throw new IllegalArgumentException("Unknown side: " + p_getEdgeDirections_0_);
        }
    }

    protected static Map[][] getSpriteQuadCompactMaps()
    {
        return spriteQuadCompactMaps;
    }

    private static int getConnectedTextureCtmIndex(ConnectedProperties p_getConnectedTextureCtmIndex_0_, IBlockAccess p_getConnectedTextureCtmIndex_1_, IBlockState p_getConnectedTextureCtmIndex_2_, BlockPos p_getConnectedTextureCtmIndex_3_, int p_getConnectedTextureCtmIndex_4_, int p_getConnectedTextureCtmIndex_5_, TextureAtlasSprite p_getConnectedTextureCtmIndex_6_, int p_getConnectedTextureCtmIndex_7_, RenderEnv p_getConnectedTextureCtmIndex_8_)
    {
        boolean[] aboolean = p_getConnectedTextureCtmIndex_8_.getBorderFlags();

        switch (p_getConnectedTextureCtmIndex_5_)
        {
            case 0:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                break;

            case 1:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                break;

            case 2:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);

                if (p_getConnectedTextureCtmIndex_4_ == 1)
                {
                    switchValues(0, 1, aboolean);
                    switchValues(2, 3, aboolean);
                }

                break;

            case 3:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                break;

            case 4:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                break;

            case 5:
                aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);

                if (p_getConnectedTextureCtmIndex_4_ == 2)
                {
                    switchValues(0, 1, aboolean);
                    switchValues(2, 3, aboolean);
                }
        }

        int i = 0;

        if (aboolean[0] & !aboolean[1] & !aboolean[2] & !aboolean[3])
        {
            i = 3;
        }
        else if (!aboolean[0] & aboolean[1] & !aboolean[2] & !aboolean[3])
        {
            i = 1;
        }
        else if (!aboolean[0] & !aboolean[1] & aboolean[2] & !aboolean[3])
        {
            i = 12;
        }
        else if (!aboolean[0] & !aboolean[1] & !aboolean[2] & aboolean[3])
        {
            i = 36;
        }
        else if (aboolean[0] & aboolean[1] & !aboolean[2] & !aboolean[3])
        {
            i = 2;
        }
        else if (!aboolean[0] & !aboolean[1] & aboolean[2] & aboolean[3])
        {
            i = 24;
        }
        else if (aboolean[0] & !aboolean[1] & aboolean[2] & !aboolean[3])
        {
            i = 15;
        }
        else if (aboolean[0] & !aboolean[1] & !aboolean[2] & aboolean[3])
        {
            i = 39;
        }
        else if (!aboolean[0] & aboolean[1] & aboolean[2] & !aboolean[3])
        {
            i = 13;
        }
        else if (!aboolean[0] & aboolean[1] & !aboolean[2] & aboolean[3])
        {
            i = 37;
        }
        else if (!aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3])
        {
            i = 25;
        }
        else if (aboolean[0] & !aboolean[1] & aboolean[2] & aboolean[3])
        {
            i = 27;
        }
        else if (aboolean[0] & aboolean[1] & !aboolean[2] & aboolean[3])
        {
            i = 38;
        }
        else if (aboolean[0] & aboolean[1] & aboolean[2] & !aboolean[3])
        {
            i = 14;
        }
        else if (aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3])
        {
            i = 26;
        }

        if (i == 0)
        {
            return i;
        }
        else if (!Config.isConnectedTexturesFancy())
        {
            return i;
        }
        else
        {
            switch (p_getConnectedTextureCtmIndex_5_)
            {
                case 0:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    break;

                case 1:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    break;

                case 2:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);

                    if (p_getConnectedTextureCtmIndex_4_ == 1)
                    {
                        switchValues(0, 3, aboolean);
                        switchValues(1, 2, aboolean);
                    }

                    break;

                case 3:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    break;

                case 4:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    break;

                case 5:
                    aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
                    aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);

                    if (p_getConnectedTextureCtmIndex_4_ == 2)
                    {
                        switchValues(0, 3, aboolean);
                        switchValues(1, 2, aboolean);
                    }
            }

            if (i == 13 && aboolean[0])
            {
                i = 4;
            }
            else if (i == 15 && aboolean[1])
            {
                i = 5;
            }
            else if (i == 37 && aboolean[2])
            {
                i = 16;
            }
            else if (i == 39 && aboolean[3])
            {
                i = 17;
            }
            else if (i == 14 && aboolean[0] && aboolean[1])
            {
                i = 7;
            }
            else if (i == 25 && aboolean[0] && aboolean[2])
            {
                i = 6;
            }
            else if (i == 27 && aboolean[3] && aboolean[1])
            {
                i = 19;
            }
            else if (i == 38 && aboolean[3] && aboolean[2])
            {
                i = 18;
            }
            else if (i == 14 && !aboolean[0] && aboolean[1])
            {
                i = 31;
            }
            else if (i == 25 && aboolean[0] && !aboolean[2])
            {
                i = 30;
            }
            else if (i == 27 && !aboolean[3] && aboolean[1])
            {
                i = 41;
            }
            else if (i == 38 && aboolean[3] && !aboolean[2])
            {
                i = 40;
            }
            else if (i == 14 && aboolean[0] && !aboolean[1])
            {
                i = 29;
            }
            else if (i == 25 && !aboolean[0] && aboolean[2])
            {
                i = 28;
            }
            else if (i == 27 && aboolean[3] && !aboolean[1])
            {
                i = 43;
            }
            else if (i == 38 && !aboolean[3] && aboolean[2])
            {
                i = 42;
            }
            else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3])
            {
                i = 46;
            }
            else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3])
            {
                i = 9;
            }
            else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3])
            {
                i = 21;
            }
            else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3])
            {
                i = 8;
            }
            else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3])
            {
                i = 20;
            }
            else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3])
            {
                i = 11;
            }
            else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3])
            {
                i = 22;
            }
            else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3])
            {
                i = 23;
            }
            else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3])
            {
                i = 10;
            }
            else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3])
            {
                i = 34;
            }
            else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3])
            {
                i = 35;
            }
            else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3])
            {
                i = 32;
            }
            else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3])
            {
                i = 33;
            }
            else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3])
            {
                i = 44;
            }
            else if (i == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3])
            {
                i = 45;
            }

            return i;
        }
    }

    private static void switchValues(int p_switchValues_0_, int p_switchValues_1_, boolean[] p_switchValues_2_)
    {
        boolean flag = p_switchValues_2_[p_switchValues_0_];
        p_switchValues_2_[p_switchValues_0_] = p_switchValues_2_[p_switchValues_1_];
        p_switchValues_2_[p_switchValues_1_] = flag;
    }

    private static boolean isNeighbourOverlay(ConnectedProperties p_isNeighbourOverlay_0_, IBlockAccess p_isNeighbourOverlay_1_, IBlockState p_isNeighbourOverlay_2_, BlockPos p_isNeighbourOverlay_3_, int p_isNeighbourOverlay_4_, TextureAtlasSprite p_isNeighbourOverlay_5_, int p_isNeighbourOverlay_6_)
    {
        IBlockState iblockstate = p_isNeighbourOverlay_1_.getBlockState(p_isNeighbourOverlay_3_);

        if (!isFullCubeModel(iblockstate))
        {
            return false;
        }
        else
        {
            if (p_isNeighbourOverlay_0_.connectBlocks != null)
            {
                BlockStateBase blockstatebase = (BlockStateBase)iblockstate;

                if (!Matches.block(blockstatebase.getBlockId(), blockstatebase.getMetadata(), p_isNeighbourOverlay_0_.connectBlocks))
                {
                    return false;
                }
            }

            if (p_isNeighbourOverlay_0_.connectTileIcons != null)
            {
                TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbourOverlay_1_, p_isNeighbourOverlay_2_, p_isNeighbourOverlay_3_, iblockstate, p_isNeighbourOverlay_4_);

                if (!Config.isSameOne(textureatlassprite, p_isNeighbourOverlay_0_.connectTileIcons))
                {
                    return false;
                }
            }

            IBlockState iblockstate1 = p_isNeighbourOverlay_1_.getBlockState(p_isNeighbourOverlay_3_.offset(getFacing(p_isNeighbourOverlay_4_)));

            if (iblockstate1.isOpaqueCube())
            {
                return false;
            }
            else if (p_isNeighbourOverlay_4_ == 1 && iblockstate1.getBlock() == Blocks.SNOW_LAYER)
            {
                return false;
            }
            else
            {
                return !isNeighbour(p_isNeighbourOverlay_0_, p_isNeighbourOverlay_1_, p_isNeighbourOverlay_2_, p_isNeighbourOverlay_3_, iblockstate, p_isNeighbourOverlay_4_, p_isNeighbourOverlay_5_, p_isNeighbourOverlay_6_);
            }
        }
    }

    private static boolean isFullCubeModel(IBlockState p_isFullCubeModel_0_)
    {
        if (p_isFullCubeModel_0_.isFullCube())
        {
            return true;
        }
        else
        {
            Block block = p_isFullCubeModel_0_.getBlock();

            if (block instanceof BlockGlass)
            {
                return true;
            }
            else
            {
                return block instanceof BlockStainedGlass;
            }
        }
    }

    private static boolean isNeighbourMatching(ConnectedProperties p_isNeighbourMatching_0_, IBlockAccess p_isNeighbourMatching_1_, IBlockState p_isNeighbourMatching_2_, BlockPos p_isNeighbourMatching_3_, int p_isNeighbourMatching_4_, TextureAtlasSprite p_isNeighbourMatching_5_, int p_isNeighbourMatching_6_)
    {
        IBlockState iblockstate = p_isNeighbourMatching_1_.getBlockState(p_isNeighbourMatching_3_);

        if (iblockstate == AIR_DEFAULT_STATE)
        {
            return false;
        }
        else
        {
            if (p_isNeighbourMatching_0_.matchBlocks != null && iblockstate instanceof BlockStateBase)
            {
                BlockStateBase blockstatebase = (BlockStateBase)iblockstate;

                if (!p_isNeighbourMatching_0_.matchesBlock(blockstatebase.getBlockId(), blockstatebase.getMetadata()))
                {
                    return false;
                }
            }

            if (p_isNeighbourMatching_0_.matchTileIcons != null)
            {
                TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbourMatching_1_, p_isNeighbourMatching_2_, p_isNeighbourMatching_3_, iblockstate, p_isNeighbourMatching_4_);

                if (textureatlassprite != p_isNeighbourMatching_5_)
                {
                    return false;
                }
            }

            IBlockState iblockstate1 = p_isNeighbourMatching_1_.getBlockState(p_isNeighbourMatching_3_.offset(getFacing(p_isNeighbourMatching_4_)));

            if (iblockstate1.isOpaqueCube())
            {
                return false;
            }
            else
            {
                return p_isNeighbourMatching_4_ != 1 || iblockstate1.getBlock() != Blocks.SNOW_LAYER;
            }
        }
    }

    private static boolean isNeighbour(ConnectedProperties p_isNeighbour_0_, IBlockAccess p_isNeighbour_1_, IBlockState p_isNeighbour_2_, BlockPos p_isNeighbour_3_, int p_isNeighbour_4_, TextureAtlasSprite p_isNeighbour_5_, int p_isNeighbour_6_)
    {
        IBlockState iblockstate = p_isNeighbour_1_.getBlockState(p_isNeighbour_3_);
        return isNeighbour(p_isNeighbour_0_, p_isNeighbour_1_, p_isNeighbour_2_, p_isNeighbour_3_, iblockstate, p_isNeighbour_4_, p_isNeighbour_5_, p_isNeighbour_6_);
    }

    private static boolean isNeighbour(ConnectedProperties p_isNeighbour_0_, IBlockAccess p_isNeighbour_1_, IBlockState p_isNeighbour_2_, BlockPos p_isNeighbour_3_, IBlockState p_isNeighbour_4_, int p_isNeighbour_5_, TextureAtlasSprite p_isNeighbour_6_, int p_isNeighbour_7_)
    {
        if (p_isNeighbour_2_ == p_isNeighbour_4_)
        {
            return true;
        }
        else if (p_isNeighbour_0_.connect == 2)
        {
            if (p_isNeighbour_4_ == null)
            {
                return false;
            }
            else if (p_isNeighbour_4_ == AIR_DEFAULT_STATE)
            {
                return false;
            }
            else
            {
                TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbour_1_, p_isNeighbour_2_, p_isNeighbour_3_, p_isNeighbour_4_, p_isNeighbour_5_);
                return textureatlassprite == p_isNeighbour_6_;
            }
        }
        else if (p_isNeighbour_0_.connect == 3)
        {
            if (p_isNeighbour_4_ == null)
            {
                return false;
            }
            else if (p_isNeighbour_4_ == AIR_DEFAULT_STATE)
            {
                return false;
            }
            else
            {
                return p_isNeighbour_4_.getMaterial() == p_isNeighbour_2_.getMaterial();
            }
        }
        else if (!(p_isNeighbour_4_ instanceof BlockStateBase))
        {
            return false;
        }
        else
        {
            BlockStateBase blockstatebase = (BlockStateBase)p_isNeighbour_4_;
            Block block = blockstatebase.getBlock();
            int i = blockstatebase.getMetadata();
            return block == p_isNeighbour_2_.getBlock() && i == p_isNeighbour_7_;
        }
    }

    private static TextureAtlasSprite getNeighbourIcon(IBlockAccess p_getNeighbourIcon_0_, IBlockState p_getNeighbourIcon_1_, BlockPos p_getNeighbourIcon_2_, IBlockState p_getNeighbourIcon_3_, int p_getNeighbourIcon_4_)
    {
        p_getNeighbourIcon_3_ = p_getNeighbourIcon_3_.getBlock().getActualState(p_getNeighbourIcon_3_, p_getNeighbourIcon_0_, p_getNeighbourIcon_2_);
        IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(p_getNeighbourIcon_3_);

        if (ibakedmodel == null)
        {
            return null;
        }
        else
        {
            EnumFacing enumfacing = getFacing(p_getNeighbourIcon_4_);
            List list = ibakedmodel.getQuads(p_getNeighbourIcon_3_, enumfacing, 0L);

            if (Config.isBetterGrass())
            {
                list = BetterGrass.getFaceQuads(p_getNeighbourIcon_0_, p_getNeighbourIcon_3_, p_getNeighbourIcon_2_, enumfacing, list);
            }

            if (list.size() > 0)
            {
                BakedQuad bakedquad1 = (BakedQuad)list.get(0);
                return bakedquad1.getSprite();
            }
            else
            {
                List list1 = ibakedmodel.getQuads(p_getNeighbourIcon_3_, (EnumFacing)null, 0L);

                for (int i = 0; i < list1.size(); ++i)
                {
                    BakedQuad bakedquad = (BakedQuad)list1.get(i);

                    if (bakedquad.getFace() == enumfacing)
                    {
                        return bakedquad.getSprite();
                    }
                }

                return null;
            }
        }
    }

    private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties p_getConnectedTextureHorizontal_0_, IBlockAccess p_getConnectedTextureHorizontal_1_, IBlockState p_getConnectedTextureHorizontal_2_, BlockPos p_getConnectedTextureHorizontal_3_, int p_getConnectedTextureHorizontal_4_, int p_getConnectedTextureHorizontal_5_, TextureAtlasSprite p_getConnectedTextureHorizontal_6_, int p_getConnectedTextureHorizontal_7_)
    {
        boolean flag;
        boolean flag1;
        flag = false;
        flag1 = false;
        label49:

        switch (p_getConnectedTextureHorizontal_4_)
        {
            case 0:
                switch (p_getConnectedTextureHorizontal_5_)
                {
                    case 0:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 1:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 2:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 3:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 4:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 5:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);

                    default:
                        break label49;
                }

            case 1:
                switch (p_getConnectedTextureHorizontal_5_)
                {
                    case 0:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 1:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 2:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 3:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 4:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break label49;

                    case 5:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);

                    default:
                        break label49;
                }

            case 2:
                switch (p_getConnectedTextureHorizontal_5_)
                {
                    case 0:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;

                    case 1:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;

                    case 2:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;

                    case 3:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;

                    case 4:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;

                    case 5:
                        flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                }
        }

        int i = 3;

        if (flag)
        {
            if (flag1)
            {
                i = 1;
            }
            else
            {
                i = 2;
            }
        }
        else if (flag1)
        {
            i = 0;
        }
        else
        {
            i = 3;
        }

        return p_getConnectedTextureHorizontal_0_.tileIcons[i];
    }

    private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties p_getConnectedTextureVertical_0_, IBlockAccess p_getConnectedTextureVertical_1_, IBlockState p_getConnectedTextureVertical_2_, BlockPos p_getConnectedTextureVertical_3_, int p_getConnectedTextureVertical_4_, int p_getConnectedTextureVertical_5_, TextureAtlasSprite p_getConnectedTextureVertical_6_, int p_getConnectedTextureVertical_7_)
    {
        boolean flag = false;
        boolean flag1 = false;

        switch (p_getConnectedTextureVertical_4_)
        {
            case 0:
                if (p_getConnectedTextureVertical_5_ == 1)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else if (p_getConnectedTextureVertical_5_ == 0)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }

                break;

            case 1:
                if (p_getConnectedTextureVertical_5_ == 3)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else if (p_getConnectedTextureVertical_5_ == 2)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }

                break;

            case 2:
                if (p_getConnectedTextureVertical_5_ == 5)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else if (p_getConnectedTextureVertical_5_ == 4)
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
                else
                {
                    flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.west(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                    flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.east(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                }
        }

        int i = 3;

        if (flag)
        {
            if (flag1)
            {
                i = 1;
            }
            else
            {
                i = 2;
            }
        }
        else if (flag1)
        {
            i = 0;
        }
        else
        {
            i = 3;
        }

        return p_getConnectedTextureVertical_0_.tileIcons[i];
    }

    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties p_getConnectedTextureHorizontalVertical_0_, IBlockAccess p_getConnectedTextureHorizontalVertical_1_, IBlockState p_getConnectedTextureHorizontalVertical_2_, BlockPos p_getConnectedTextureHorizontalVertical_3_, int p_getConnectedTextureHorizontalVertical_4_, int p_getConnectedTextureHorizontalVertical_5_, TextureAtlasSprite p_getConnectedTextureHorizontalVertical_6_, int p_getConnectedTextureHorizontalVertical_7_)
    {
        TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureHorizontalVertical_0_.tileIcons;
        TextureAtlasSprite textureatlassprite = getConnectedTextureHorizontal(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);

        if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureHorizontalVertical_6_ && textureatlassprite != atextureatlassprite[3])
        {
            return textureatlassprite;
        }
        else
        {
            TextureAtlasSprite textureatlassprite1 = getConnectedTextureVertical(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);

            if (textureatlassprite1 == atextureatlassprite[0])
            {
                return atextureatlassprite[4];
            }
            else if (textureatlassprite1 == atextureatlassprite[1])
            {
                return atextureatlassprite[5];
            }
            else
            {
                return textureatlassprite1 == atextureatlassprite[2] ? atextureatlassprite[6] : textureatlassprite1;
            }
        }
    }

    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties p_getConnectedTextureVerticalHorizontal_0_, IBlockAccess p_getConnectedTextureVerticalHorizontal_1_, IBlockState p_getConnectedTextureVerticalHorizontal_2_, BlockPos p_getConnectedTextureVerticalHorizontal_3_, int p_getConnectedTextureVerticalHorizontal_4_, int p_getConnectedTextureVerticalHorizontal_5_, TextureAtlasSprite p_getConnectedTextureVerticalHorizontal_6_, int p_getConnectedTextureVerticalHorizontal_7_)
    {
        TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureVerticalHorizontal_0_.tileIcons;
        TextureAtlasSprite textureatlassprite = getConnectedTextureVertical(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);

        if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureVerticalHorizontal_6_ && textureatlassprite != atextureatlassprite[3])
        {
            return textureatlassprite;
        }
        else
        {
            TextureAtlasSprite textureatlassprite1 = getConnectedTextureHorizontal(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);

            if (textureatlassprite1 == atextureatlassprite[0])
            {
                return atextureatlassprite[4];
            }
            else if (textureatlassprite1 == atextureatlassprite[1])
            {
                return atextureatlassprite[5];
            }
            else
            {
                return textureatlassprite1 == atextureatlassprite[2] ? atextureatlassprite[6] : textureatlassprite1;
            }
        }
    }

    private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties p_getConnectedTextureTop_0_, IBlockAccess p_getConnectedTextureTop_1_, IBlockState p_getConnectedTextureTop_2_, BlockPos p_getConnectedTextureTop_3_, int p_getConnectedTextureTop_4_, int p_getConnectedTextureTop_5_, TextureAtlasSprite p_getConnectedTextureTop_6_, int p_getConnectedTextureTop_7_)
    {
        boolean flag = false;

        switch (p_getConnectedTextureTop_4_)
        {
            case 0:
                if (p_getConnectedTextureTop_5_ == 1 || p_getConnectedTextureTop_5_ == 0)
                {
                    return null;
                }

                flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.up(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
                break;

            case 1:
                if (p_getConnectedTextureTop_5_ == 3 || p_getConnectedTextureTop_5_ == 2)
                {
                    return null;
                }

                flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.south(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
                break;

            case 2:
                if (p_getConnectedTextureTop_5_ == 5 || p_getConnectedTextureTop_5_ == 4)
                {
                    return null;
                }

                flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.east(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
        }

        if (flag)
        {
            return p_getConnectedTextureTop_0_.tileIcons[0];
        }
        else
        {
            return null;
        }
    }

    public static void updateIcons(TextureMap p_updateIcons_0_)
    {
        blockProperties = (ConnectedProperties[][])null;
        tileProperties = (ConnectedProperties[][])null;
        spriteQuadMaps = null;
        spriteQuadCompactMaps = (Map[][])null;

        if (Config.isConnectedTextures())
        {
            IResourcePack[] airesourcepack = Config.getResourcePacks();

            for (int i = airesourcepack.length - 1; i >= 0; --i)
            {
                IResourcePack iresourcepack = airesourcepack[i];
                updateIcons(p_updateIcons_0_, iresourcepack);
            }

            updateIcons(p_updateIcons_0_, Config.getDefaultResourcePack());
            ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
            emptySprite = p_updateIcons_0_.registerSprite(resourcelocation);
            spriteQuadMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1];
            spriteQuadFullMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1];
            spriteQuadCompactMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1][];

            if (blockProperties.length <= 0)
            {
                blockProperties = (ConnectedProperties[][])null;
            }

            if (tileProperties.length <= 0)
            {
                tileProperties = (ConnectedProperties[][])null;
            }
        }
    }

    private static void updateIconEmpty(TextureMap p_updateIconEmpty_0_)
    {
    }

    public static void updateIcons(TextureMap p_updateIcons_0_, IResourcePack p_updateIcons_1_)
    {
        String[] astring = ResUtils.collectFiles(p_updateIcons_1_, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
        Arrays.sort((Object[])astring);
        List list = makePropertyList(tileProperties);
        List list1 = makePropertyList(blockProperties);

        for (int i = 0; i < astring.length; ++i)
        {
            String s = astring[i];
            Config.dbg("ConnectedTextures: " + s);

            try
            {
                ResourceLocation resourcelocation = new ResourceLocation(s);
                InputStream inputstream = p_updateIcons_1_.getInputStream(resourcelocation);

                if (inputstream == null)
                {
                    Config.warn("ConnectedTextures file not found: " + s);
                }
                else
                {
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    ConnectedProperties connectedproperties = new ConnectedProperties(properties, s);

                    if (connectedproperties.isValid(s))
                    {
                        connectedproperties.updateIcons(p_updateIcons_0_);
                        addToTileList(connectedproperties, list);
                        addToBlockList(connectedproperties, list1);
                    }
                }
            }
            catch (FileNotFoundException var11)
            {
                Config.warn("ConnectedTextures file not found: " + s);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        blockProperties = propertyListToArray(list1);
        tileProperties = propertyListToArray(list);
        multipass = detectMultipass();
        Config.dbg("Multipass connected textures: " + multipass);
    }

    private static List makePropertyList(ConnectedProperties[][] p_makePropertyList_0_)
    {
        List list = new ArrayList();

        if (p_makePropertyList_0_ != null)
        {
            for (int i = 0; i < p_makePropertyList_0_.length; ++i)
            {
                ConnectedProperties[] aconnectedproperties = p_makePropertyList_0_[i];
                List list1 = null;

                if (aconnectedproperties != null)
                {
                    list1 = new ArrayList(Arrays.asList(aconnectedproperties));
                }

                list.add(list1);
            }
        }

        return list;
    }

    private static boolean detectMultipass()
    {
        List list = new ArrayList();

        for (int i = 0; i < tileProperties.length; ++i)
        {
            ConnectedProperties[] aconnectedproperties = tileProperties[i];

            if (aconnectedproperties != null)
            {
                list.addAll(Arrays.asList(aconnectedproperties));
            }
        }

        for (int k = 0; k < blockProperties.length; ++k)
        {
            ConnectedProperties[] aconnectedproperties2 = blockProperties[k];

            if (aconnectedproperties2 != null)
            {
                list.addAll(Arrays.asList(aconnectedproperties2));
            }
        }

        ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray(new ConnectedProperties[list.size()]);
        Set set1 = new HashSet();
        Set set = new HashSet();

        for (int j = 0; j < aconnectedproperties1.length; ++j)
        {
            ConnectedProperties connectedproperties = aconnectedproperties1[j];

            if (connectedproperties.matchTileIcons != null)
            {
                set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
            }

            if (connectedproperties.tileIcons != null)
            {
                set.addAll(Arrays.asList(connectedproperties.tileIcons));
            }
        }

        set1.retainAll(set);
        return !set1.isEmpty();
    }

    private static ConnectedProperties[][] propertyListToArray(List p_propertyListToArray_0_)
    {
        ConnectedProperties[][] aconnectedproperties = new ConnectedProperties[p_propertyListToArray_0_.size()][];

        for (int i = 0; i < p_propertyListToArray_0_.size(); ++i)
        {
            List list = (List)p_propertyListToArray_0_.get(i);

            if (list != null)
            {
                ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray(new ConnectedProperties[list.size()]);
                aconnectedproperties[i] = aconnectedproperties1;
            }
        }

        return aconnectedproperties;
    }

    private static void addToTileList(ConnectedProperties p_addToTileList_0_, List p_addToTileList_1_)
    {
        if (p_addToTileList_0_.matchTileIcons != null)
        {
            for (int i = 0; i < p_addToTileList_0_.matchTileIcons.length; ++i)
            {
                TextureAtlasSprite textureatlassprite = p_addToTileList_0_.matchTileIcons[i];

                if (!(textureatlassprite instanceof TextureAtlasSprite))
                {
                    Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
                }
                else
                {
                    int j = textureatlassprite.getIndexInMap();

                    if (j < 0)
                    {
                        Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
                    }
                    else
                    {
                        addToList(p_addToTileList_0_, p_addToTileList_1_, j);
                    }
                }
            }
        }
    }

    private static void addToBlockList(ConnectedProperties p_addToBlockList_0_, List p_addToBlockList_1_)
    {
        if (p_addToBlockList_0_.matchBlocks != null)
        {
            for (int i = 0; i < p_addToBlockList_0_.matchBlocks.length; ++i)
            {
                int j = p_addToBlockList_0_.matchBlocks[i].getBlockId();

                if (j < 0)
                {
                    Config.warn("Invalid block ID: " + j);
                }
                else
                {
                    addToList(p_addToBlockList_0_, p_addToBlockList_1_, j);
                }
            }
        }
    }

    private static void addToList(ConnectedProperties p_addToList_0_, List p_addToList_1_, int p_addToList_2_)
    {
        while (p_addToList_2_ >= p_addToList_1_.size())
        {
            p_addToList_1_.add((Object)null);
        }

        List list = (List)p_addToList_1_.get(p_addToList_2_);

        if (list == null)
        {
            list = new ArrayList();
            p_addToList_1_.set(p_addToList_2_, list);
        }

        list.add(p_addToList_0_);
    }

    private static String[] getDefaultCtmPaths()
    {
        List list = new ArrayList();
        String s = "mcpatcher/ctm/default/";

        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png")))
        {
            list.add(s + "glass.properties");
            list.add(s + "glasspane.properties");
        }

        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png")))
        {
            list.add(s + "bookshelf.properties");
        }

        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png")))
        {
            list.add(s + "sandstone.properties");
        }

        String[] astring = new String[] {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};

        for (int i = 0; i < astring.length; ++i)
        {
            String s1 = astring[i];

            if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png")))
            {
                list.add(s + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
                list.add(s + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
            }
        }

        String[] astring1 = (String[])list.toArray(new String[list.size()]);
        return astring1;
    }
}
