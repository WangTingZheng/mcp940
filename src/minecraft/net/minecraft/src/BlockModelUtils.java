package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.util.vector.Vector3f;

public class BlockModelUtils
{
    private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;

    public static IBakedModel makeModelCube(String p_makeModelCube_0_, int p_makeModelCube_1_)
    {
        TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(p_makeModelCube_0_);
        return makeModelCube(textureatlassprite, p_makeModelCube_1_);
    }

    public static IBakedModel makeModelCube(TextureAtlasSprite p_makeModelCube_0_, int p_makeModelCube_1_)
    {
        List list = new ArrayList();
        EnumFacing[] aenumfacing = EnumFacing.VALUES;
        Map<EnumFacing, List<BakedQuad>> map = new HashMap<EnumFacing, List<BakedQuad>>();

        for (int i = 0; i < aenumfacing.length; ++i)
        {
            EnumFacing enumfacing = aenumfacing[i];
            List list1 = new ArrayList();
            list1.add(makeBakedQuad(enumfacing, p_makeModelCube_0_, p_makeModelCube_1_));
            map.put(enumfacing, list1);
        }

        ItemOverrideList itemoverridelist = new ItemOverrideList(new ArrayList());
        IBakedModel ibakedmodel = new SimpleBakedModel(list, map, true, true, p_makeModelCube_0_, ItemCameraTransforms.DEFAULT, itemoverridelist);
        return ibakedmodel;
    }

    public static BakedQuad makeBakedQuad(EnumFacing p_makeBakedQuad_0_, TextureAtlasSprite p_makeBakedQuad_1_, int p_makeBakedQuad_2_)
    {
        Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
        Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
        BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] {0.0F, 0.0F, 16.0F, 16.0F}, 0);
        BlockPartFace blockpartface = new BlockPartFace(p_makeBakedQuad_0_, p_makeBakedQuad_2_, "#" + p_makeBakedQuad_0_.getName(), blockfaceuv);
        ModelRotation modelrotation = ModelRotation.X0_Y0;
        BlockPartRotation blockpartrotation = null;
        boolean flag = false;
        boolean flag1 = true;
        FaceBakery facebakery = new FaceBakery();
        BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, p_makeBakedQuad_1_, p_makeBakedQuad_0_, modelrotation, blockpartrotation, flag, flag1);
        return bakedquad;
    }

    public static IBakedModel makeModel(String p_makeModel_0_, String p_makeModel_1_, String p_makeModel_2_)
    {
        TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(p_makeModel_1_);
        TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(p_makeModel_2_);
        return makeModel(p_makeModel_0_, textureatlassprite, textureatlassprite1);
    }

    public static IBakedModel makeModel(String p_makeModel_0_, TextureAtlasSprite p_makeModel_1_, TextureAtlasSprite p_makeModel_2_)
    {
        if (p_makeModel_1_ != null && p_makeModel_2_ != null)
        {
            ModelManager modelmanager = Config.getModelManager();

            if (modelmanager == null)
            {
                return null;
            }
            else
            {
                ModelResourceLocation modelresourcelocation = new ModelResourceLocation(p_makeModel_0_, "normal");
                IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);

                if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel())
                {
                    IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
                    EnumFacing[] aenumfacing = EnumFacing.VALUES;

                    for (int i = 0; i < aenumfacing.length; ++i)
                    {
                        EnumFacing enumfacing = aenumfacing[i];
                        List<BakedQuad> list = ibakedmodel1.getQuads((IBlockState)null, enumfacing, 0L);
                        replaceTexture(list, p_makeModel_1_, p_makeModel_2_);
                    }

                    List<BakedQuad> list1 = ibakedmodel1.getQuads((IBlockState)null, (EnumFacing)null, 0L);
                    replaceTexture(list1, p_makeModel_1_, p_makeModel_2_);
                    return ibakedmodel1;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            return null;
        }
    }

    private static void replaceTexture(List<BakedQuad> p_replaceTexture_0_, TextureAtlasSprite p_replaceTexture_1_, TextureAtlasSprite p_replaceTexture_2_)
    {
        List<BakedQuad> list = new ArrayList<BakedQuad>();

        for (BakedQuad bakedquad : p_replaceTexture_0_)
        {
            if (bakedquad.getSprite() != p_replaceTexture_1_)
            {
                list.add(bakedquad);
                break;
            }

            BakedQuad bakedquad1 = new BakedQuadRetextured(bakedquad, p_replaceTexture_2_);
            list.add(bakedquad1);
        }

        p_replaceTexture_0_.clear();
        p_replaceTexture_0_.addAll(list);
    }

    public static void snapVertexPosition(Vector3f p_snapVertexPosition_0_)
    {
        p_snapVertexPosition_0_.setX(snapVertexCoord(p_snapVertexPosition_0_.getX()));
        p_snapVertexPosition_0_.setY(snapVertexCoord(p_snapVertexPosition_0_.getY()));
        p_snapVertexPosition_0_.setZ(snapVertexCoord(p_snapVertexPosition_0_.getZ()));
    }

    private static float snapVertexCoord(float p_snapVertexCoord_0_)
    {
        if (p_snapVertexCoord_0_ > -1.0E-6F && p_snapVertexCoord_0_ < 1.0E-6F)
        {
            return 0.0F;
        }
        else
        {
            return p_snapVertexCoord_0_ > 0.999999F && p_snapVertexCoord_0_ < 1.000001F ? 1.0F : p_snapVertexCoord_0_;
        }
    }

    public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB p_getOffsetBoundingBox_0_, Block.EnumOffsetType p_getOffsetBoundingBox_1_, BlockPos p_getOffsetBoundingBox_2_)
    {
        int i = p_getOffsetBoundingBox_2_.getX();
        int j = p_getOffsetBoundingBox_2_.getZ();
        long k = (long)(i * 3129871) ^ (long)j * 116129781L;
        k = k * k * 42317861L + k * 11L;
        double d0 = ((double)((float)(k >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
        double d1 = ((double)((float)(k >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        double d2 = 0.0D;

        if (p_getOffsetBoundingBox_1_ == Block.EnumOffsetType.XYZ)
        {
            d2 = ((double)((float)(k >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
        }

        return p_getOffsetBoundingBox_0_.offset(d0, d2, d1);
    }
}
