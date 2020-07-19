package net.minecraft.client.renderer;

import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.BetterSnow;
import net.minecraft.src.BlockModelCustomizer;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.ListQuadsOverlay;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderEnv;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import shadersmod.client.SVertexBuilder;

public class BlockModelRenderer
{
    private final BlockColors blockColors;
    private static float aoLightValueOpaque = 0.2F;
    private static final BlockRenderLayer[] OVERLAY_LAYERS = new BlockRenderLayer[] {BlockRenderLayer.CUTOUT, BlockRenderLayer.CUTOUT_MIPPED, BlockRenderLayer.TRANSLUCENT};

    public BlockModelRenderer(BlockColors blockColorsIn)
    {
        this.blockColors = blockColorsIn;

        if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists())
        {
            Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.valueOf(false));
        }
    }

    public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides)
    {
        return this.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, buffer, checkSides, MathHelper.getPositionRandom(blockPosIn));
    }

    public boolean renderModel(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand)
    {
        boolean flag = Minecraft.isAmbientOcclusionEnabled() && stateIn.getLightValue() == 0 && modelIn.isAmbientOcclusion();

        try
        {
            if (Config.isShaders())
            {
                SVertexBuilder.pushEntity(stateIn, posIn, worldIn, buffer);
            }

            if (!Config.isAlternateBlocks())
            {
                rand = 0L;
            }

            RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn);
            modelIn = BlockModelCustomizer.getRenderModel(modelIn, stateIn, renderenv);
            boolean flag1 = flag ? this.renderModelSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand) : this.renderModelFlat(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);

            if (flag1)
            {
                this.renderOverlayModels(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand, renderenv, flag);
            }

            if (Config.isShaders())
            {
                SVertexBuilder.popEntity(buffer);
            }

            return flag1;
        }
        catch (Throwable throwable1)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Tesselating block model");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, posIn, stateIn);
            crashreportcategory.addCrashSection("Using AO", Boolean.valueOf(flag));
            throw new ReportedException(crashreport);
        }
    }

    public boolean renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand)
    {
        boolean flag = false;
        RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn);

        for (EnumFacing enumfacing : EnumFacing.VALUES)
        {
            List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);

            if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing)))
            {
                list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, enumfacing, rand, renderenv);
                this.renderQuadsSmooth(worldIn, stateIn, posIn, buffer, list, renderenv);
                flag = true;
            }
        }

        List<BakedQuad> list1 = modelIn.getQuads(stateIn, (EnumFacing)null, rand);

        if (!list1.isEmpty())
        {
            list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, (EnumFacing)null, rand, renderenv);
            this.renderQuadsSmooth(worldIn, stateIn, posIn, buffer, list1, renderenv);
            flag = true;
        }

        return flag;
    }

    public boolean renderModelFlat(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand)
    {
        boolean flag = false;
        RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn);

        for (EnumFacing enumfacing : EnumFacing.VALUES)
        {
            List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);

            if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing)))
            {
                int i = stateIn.getPackedLightmapCoords(worldIn, posIn.offset(enumfacing));
                list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, enumfacing, rand, renderenv);
                this.renderQuadsFlat(worldIn, stateIn, posIn, i, false, buffer, list, renderenv);
                flag = true;
            }
        }

        List<BakedQuad> list1 = modelIn.getQuads(stateIn, (EnumFacing)null, rand);

        if (!list1.isEmpty())
        {
            list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, (EnumFacing)null, rand, renderenv);
            this.renderQuadsFlat(worldIn, stateIn, posIn, -1, true, buffer, list1, renderenv);
            flag = true;
        }

        return flag;
    }

    private void renderQuadsSmooth(IBlockAccess p_renderQuadsSmooth_1_, IBlockState p_renderQuadsSmooth_2_, BlockPos p_renderQuadsSmooth_3_, BufferBuilder p_renderQuadsSmooth_4_, List<BakedQuad> p_renderQuadsSmooth_5_, RenderEnv p_renderQuadsSmooth_6_)
    {
        float[] afloat = p_renderQuadsSmooth_6_.getQuadBounds();
        BitSet bitset = p_renderQuadsSmooth_6_.getBoundsFlags();
        BlockModelRenderer.AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderQuadsSmooth_6_.getAoFace();
        Vec3d vec3d = p_renderQuadsSmooth_2_.getOffset(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_);
        double d0 = (double)p_renderQuadsSmooth_3_.getX() + vec3d.x;
        double d1 = (double)p_renderQuadsSmooth_3_.getY() + vec3d.y;
        double d2 = (double)p_renderQuadsSmooth_3_.getZ() + vec3d.z;
        int i = 0;

        for (int j = p_renderQuadsSmooth_5_.size(); i < j; ++i)
        {
            BakedQuad bakedquad = p_renderQuadsSmooth_5_.get(i);
            this.fillQuadBounds(p_renderQuadsSmooth_2_, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
            blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_3_, bakedquad.getFace(), afloat, bitset);

            if (p_renderQuadsSmooth_4_.isMultiTexture())
            {
                p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexDataSingle());
                p_renderQuadsSmooth_4_.putSprite(bakedquad.getSprite());
            }
            else
            {
                p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexData());
            }

            p_renderQuadsSmooth_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);

            if (bakedquad.shouldApplyDiffuseLighting())
            {
                float f = FaceBakery.getFaceBrightness(bakedquad.getFace());
                float[] afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
                afloat1[0] *= f;
                afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
                afloat1[1] *= f;
                afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
                afloat1[2] *= f;
                afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
                afloat1[3] *= f;
            }

            int l = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, p_renderQuadsSmooth_6_);

            if (!bakedquad.hasTintIndex() && l == -1)
            {
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
            }
            else
            {
                int k = l;

                if (l == -1)
                {
                    k = this.blockColors.colorMultiplier(p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, bakedquad.getTintIndex());
                }

                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f1 = (float)(k >> 16 & 255) / 255.0F;
                float f2 = (float)(k >> 8 & 255) / 255.0F;
                float f3 = (float)(k & 255) / 255.0F;
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f3, 4);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f3, 3);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f3, 2);
                p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f3, 1);
            }

            p_renderQuadsSmooth_4_.putPosition(d0, d1, d2);
        }
    }

    private void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags)
    {
        float f = 32.0F;
        float f1 = 32.0F;
        float f2 = 32.0F;
        float f3 = -32.0F;
        float f4 = -32.0F;
        float f5 = -32.0F;
        int i = vertexData.length / 4;

        for (int j = 0; j < 4; ++j)
        {
            float f6 = Float.intBitsToFloat(vertexData[j * i]);
            float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
            float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
            f = Math.min(f, f6);
            f1 = Math.min(f1, f7);
            f2 = Math.min(f2, f8);
            f3 = Math.max(f3, f6);
            f4 = Math.max(f4, f7);
            f5 = Math.max(f5, f8);
        }

        if (quadBounds != null)
        {
            quadBounds[EnumFacing.WEST.getIndex()] = f;
            quadBounds[EnumFacing.EAST.getIndex()] = f3;
            quadBounds[EnumFacing.DOWN.getIndex()] = f1;
            quadBounds[EnumFacing.UP.getIndex()] = f4;
            quadBounds[EnumFacing.NORTH.getIndex()] = f2;
            quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
            int k = EnumFacing.VALUES.length;
            quadBounds[EnumFacing.WEST.getIndex() + k] = 1.0F - f;
            quadBounds[EnumFacing.EAST.getIndex() + k] = 1.0F - f3;
            quadBounds[EnumFacing.DOWN.getIndex() + k] = 1.0F - f1;
            quadBounds[EnumFacing.UP.getIndex() + k] = 1.0F - f4;
            quadBounds[EnumFacing.NORTH.getIndex() + k] = 1.0F - f2;
            quadBounds[EnumFacing.SOUTH.getIndex() + k] = 1.0F - f5;
        }

        float f9 = 1.0E-4F;
        float f10 = 0.9999F;

        switch (face)
        {
            case DOWN:
                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f1 < 1.0E-4F || stateIn.isFullCube()) && f1 == f4);
                break;

            case UP:
                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f4 > 0.9999F || stateIn.isFullCube()) && f1 == f4);
                break;

            case NORTH:
                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                boundsFlags.set(0, (f2 < 1.0E-4F || stateIn.isFullCube()) && f2 == f5);
                break;

            case SOUTH:
                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                boundsFlags.set(0, (f5 > 0.9999F || stateIn.isFullCube()) && f2 == f5);
                break;

            case WEST:
                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f < 1.0E-4F || stateIn.isFullCube()) && f == f3);
                break;

            case EAST:
                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f3 > 0.9999F || stateIn.isFullCube()) && f == f3);
        }
    }

    private void renderQuadsFlat(IBlockAccess p_renderQuadsFlat_1_, IBlockState p_renderQuadsFlat_2_, BlockPos p_renderQuadsFlat_3_, int p_renderQuadsFlat_4_, boolean p_renderQuadsFlat_5_, BufferBuilder p_renderQuadsFlat_6_, List<BakedQuad> p_renderQuadsFlat_7_, RenderEnv p_renderQuadsFlat_8_)
    {
        BitSet bitset = p_renderQuadsFlat_8_.getBoundsFlags();
        Vec3d vec3d = p_renderQuadsFlat_2_.getOffset(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_);
        double d0 = (double)p_renderQuadsFlat_3_.getX() + vec3d.x;
        double d1 = (double)p_renderQuadsFlat_3_.getY() + vec3d.y;
        double d2 = (double)p_renderQuadsFlat_3_.getZ() + vec3d.z;
        int i = 0;

        for (int j = p_renderQuadsFlat_7_.size(); i < j; ++i)
        {
            BakedQuad bakedquad = p_renderQuadsFlat_7_.get(i);

            if (p_renderQuadsFlat_5_)
            {
                this.fillQuadBounds(p_renderQuadsFlat_2_, bakedquad.getVertexData(), bakedquad.getFace(), (float[])null, bitset);
                BlockPos blockpos = bitset.get(0) ? p_renderQuadsFlat_3_.offset(bakedquad.getFace()) : p_renderQuadsFlat_3_;
                p_renderQuadsFlat_4_ = p_renderQuadsFlat_2_.getPackedLightmapCoords(p_renderQuadsFlat_1_, blockpos);
            }

            if (p_renderQuadsFlat_6_.isMultiTexture())
            {
                p_renderQuadsFlat_6_.addVertexData(bakedquad.getVertexDataSingle());
                p_renderQuadsFlat_6_.putSprite(bakedquad.getSprite());
            }
            else
            {
                p_renderQuadsFlat_6_.addVertexData(bakedquad.getVertexData());
            }

            p_renderQuadsFlat_6_.putBrightness4(p_renderQuadsFlat_4_, p_renderQuadsFlat_4_, p_renderQuadsFlat_4_, p_renderQuadsFlat_4_);
            int l = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, p_renderQuadsFlat_8_);

            if (!bakedquad.hasTintIndex() && l == -1)
            {
                if (bakedquad.shouldApplyDiffuseLighting())
                {
                    float f4 = FaceBakery.getFaceBrightness(bakedquad.getFace());
                    p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 4);
                    p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 3);
                    p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 2);
                    p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 1);
                }
            }
            else
            {
                int k = l;

                if (l == -1)
                {
                    k = this.blockColors.colorMultiplier(p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, bakedquad.getTintIndex());
                }

                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (float)(k >> 16 & 255) / 255.0F;
                float f1 = (float)(k >> 8 & 255) / 255.0F;
                float f2 = (float)(k & 255) / 255.0F;

                if (bakedquad.shouldApplyDiffuseLighting())
                {
                    float f3 = FaceBakery.getFaceBrightness(bakedquad.getFace());
                    f *= f3;
                    f1 *= f3;
                    f2 *= f3;
                }

                p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 4);
                p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 3);
                p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 2);
                p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 1);
            }

            p_renderQuadsFlat_6_.putPosition(d0, d1, d2);
        }
    }

    public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float red, float green, float blue)
    {
        this.renderModelBrightnessColor((IBlockState)null, bakedModel, p_178262_2_, red, green, blue);
    }

    public void renderModelBrightnessColor(IBlockState state, IBakedModel p_187495_2_, float p_187495_3_, float p_187495_4_, float p_187495_5_, float p_187495_6_)
    {
        for (EnumFacing enumfacing : EnumFacing.VALUES)
        {
            this.renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.getQuads(state, enumfacing, 0L));
        }

        this.renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.getQuads(state, (EnumFacing)null, 0L));
    }

    public void renderModelBrightness(IBakedModel model, IBlockState state, float brightness, boolean p_178266_4_)
    {
        Block block = state.getBlock();
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        int i = this.blockColors.colorMultiplier(state, (IBlockAccess)null, (BlockPos)null, 0);

        if (EntityRenderer.anaglyphEnable)
        {
            i = TextureUtil.anaglyphColor(i);
        }

        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;

        if (!p_178266_4_)
        {
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
        }

        this.renderModelBrightnessColor(state, model, brightness, f, f1, f2);
    }

    private void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue, List<BakedQuad> listQuads)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i = 0;

        for (int j = listQuads.size(); i < j; ++i)
        {
            BakedQuad bakedquad = listQuads.get(i);
            bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
            bufferbuilder.addVertexData(bakedquad.getVertexData());

            if (bakedquad.hasTintIndex())
            {
                bufferbuilder.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
            }
            else
            {
                bufferbuilder.putColorRGB_F4(brightness, brightness, brightness);
            }

            Vec3i vec3i = bakedquad.getFace().getDirectionVec();
            bufferbuilder.putNormal((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
            tessellator.draw();
        }
    }

    public static float fixAoLightValue(float p_fixAoLightValue_0_)
    {
        return p_fixAoLightValue_0_ == 0.2F ? aoLightValueOpaque : p_fixAoLightValue_0_;
    }

    public static void updateAoLightValue()
    {
        aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
    }

    private void renderOverlayModels(IBlockAccess p_renderOverlayModels_1_, IBakedModel p_renderOverlayModels_2_, IBlockState p_renderOverlayModels_3_, BlockPos p_renderOverlayModels_4_, BufferBuilder p_renderOverlayModels_5_, boolean p_renderOverlayModels_6_, long p_renderOverlayModels_7_, RenderEnv p_renderOverlayModels_9_, boolean p_renderOverlayModels_10_)
    {
        if (p_renderOverlayModels_9_.isOverlaysRendered())
        {
            for (int i = 0; i < OVERLAY_LAYERS.length; ++i)
            {
                BlockRenderLayer blockrenderlayer = OVERLAY_LAYERS[i];
                ListQuadsOverlay listquadsoverlay = p_renderOverlayModels_9_.getListQuadsOverlay(blockrenderlayer);

                if (listquadsoverlay.size() > 0)
                {
                    RegionRenderCacheBuilder regionrendercachebuilder = p_renderOverlayModels_9_.getRegionRenderCacheBuilder();

                    if (regionrendercachebuilder != null)
                    {
                        BufferBuilder bufferbuilder = regionrendercachebuilder.getWorldRendererByLayer(blockrenderlayer);

                        if (!bufferbuilder.isDrawing())
                        {
                            bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
                            bufferbuilder.setTranslation(p_renderOverlayModels_5_.getXOffset(), p_renderOverlayModels_5_.getYOffset(), p_renderOverlayModels_5_.getZOffset());
                        }

                        for (int j = 0; j < listquadsoverlay.size(); ++j)
                        {
                            BakedQuad bakedquad = listquadsoverlay.getQuad(j);
                            List<BakedQuad> list = listquadsoverlay.getListQuadsSingle(bakedquad);
                            IBlockState iblockstate = listquadsoverlay.getBlockState(j);
                            p_renderOverlayModels_9_.reset(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_);

                            if (p_renderOverlayModels_10_)
                            {
                                this.renderQuadsSmooth(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, bufferbuilder, list, p_renderOverlayModels_9_);
                            }
                            else
                            {
                                int k = iblockstate.getPackedLightmapCoords(p_renderOverlayModels_1_, p_renderOverlayModels_4_.offset(bakedquad.getFace()));
                                this.renderQuadsFlat(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, k, false, bufferbuilder, list, p_renderOverlayModels_9_);
                            }
                        }
                    }

                    listquadsoverlay.clear();
                }
            }
        }

        if (Config.isBetterSnow() && !p_renderOverlayModels_9_.isBreakingAnimation() && BetterSnow.shouldRender(p_renderOverlayModels_1_, p_renderOverlayModels_3_, p_renderOverlayModels_4_))
        {
            IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
            IBlockState iblockstate1 = BetterSnow.getStateSnowLayer();
            this.renderModel(p_renderOverlayModels_1_, ibakedmodel, iblockstate1, p_renderOverlayModels_4_, p_renderOverlayModels_5_, p_renderOverlayModels_6_, p_renderOverlayModels_7_);
        }
    }

    public static class AmbientOcclusionFace
    {
        private final float[] vertexColorMultiplier = new float[4];
        private final int[] vertexBrightness = new int[4];

        public AmbientOcclusionFace()
        {
        }

        public AmbientOcclusionFace(BlockModelRenderer p_i46235_1_)
        {
        }

        public void updateVertexBrightness(IBlockAccess worldIn, IBlockState state, BlockPos centerPos, EnumFacing direction, float[] faceShape, BitSet shapeState)
        {
            BlockPos blockpos = shapeState.get(0) ? centerPos.offset(direction) : centerPos;
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
            BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(direction);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(blockpos).move(blockmodelrenderer$enumneighborinfo.corners[0]);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain(blockpos).move(blockmodelrenderer$enumneighborinfo.corners[1]);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos3 = BlockPos.PooledMutableBlockPos.retain(blockpos).move(blockmodelrenderer$enumneighborinfo.corners[2]);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos4 = BlockPos.PooledMutableBlockPos.retain(blockpos).move(blockmodelrenderer$enumneighborinfo.corners[3]);
            int i = state.getPackedLightmapCoords(worldIn, blockpos$pooledmutableblockpos1);
            int j = state.getPackedLightmapCoords(worldIn, blockpos$pooledmutableblockpos2);
            int k = state.getPackedLightmapCoords(worldIn, blockpos$pooledmutableblockpos3);
            int l = state.getPackedLightmapCoords(worldIn, blockpos$pooledmutableblockpos4);
            float f = worldIn.getBlockState(blockpos$pooledmutableblockpos1).getAmbientOcclusionLightValue();
            float f1 = worldIn.getBlockState(blockpos$pooledmutableblockpos2).getAmbientOcclusionLightValue();
            float f2 = worldIn.getBlockState(blockpos$pooledmutableblockpos3).getAmbientOcclusionLightValue();
            float f3 = worldIn.getBlockState(blockpos$pooledmutableblockpos4).getAmbientOcclusionLightValue();
            f = BlockModelRenderer.fixAoLightValue(f);
            f1 = BlockModelRenderer.fixAoLightValue(f1);
            f2 = BlockModelRenderer.fixAoLightValue(f2);
            f3 = BlockModelRenderer.fixAoLightValue(f3);
            boolean flag = worldIn.getBlockState(blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos1).move(direction)).isTranslucent();
            boolean flag1 = worldIn.getBlockState(blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos2).move(direction)).isTranslucent();
            boolean flag2 = worldIn.getBlockState(blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos3).move(direction)).isTranslucent();
            boolean flag3 = worldIn.getBlockState(blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos4).move(direction)).isTranslucent();
            int i1;
            float f25;

            if (!flag2 && !flag)
            {
                f25 = f;
                i1 = i;
            }
            else
            {
                BlockPos blockpos1 = blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos1).move(blockmodelrenderer$enumneighborinfo.corners[2]);
                f25 = worldIn.getBlockState(blockpos1).getAmbientOcclusionLightValue();
                f25 = BlockModelRenderer.fixAoLightValue(f25);
                i1 = state.getPackedLightmapCoords(worldIn, blockpos1);
            }

            int j1;
            float f26;

            if (!flag3 && !flag)
            {
                f26 = f;
                j1 = i;
            }
            else
            {
                BlockPos blockpos2 = blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos1).move(blockmodelrenderer$enumneighborinfo.corners[3]);
                f26 = worldIn.getBlockState(blockpos2).getAmbientOcclusionLightValue();
                f26 = BlockModelRenderer.fixAoLightValue(f26);
                j1 = state.getPackedLightmapCoords(worldIn, blockpos2);
            }

            int k1;
            float f27;

            if (!flag2 && !flag1)
            {
                f27 = f1;
                k1 = j;
            }
            else
            {
                BlockPos blockpos3 = blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos2).move(blockmodelrenderer$enumneighborinfo.corners[2]);
                f27 = worldIn.getBlockState(blockpos3).getAmbientOcclusionLightValue();
                f27 = BlockModelRenderer.fixAoLightValue(f27);
                k1 = state.getPackedLightmapCoords(worldIn, blockpos3);
            }

            int l1;
            float f28;

            if (!flag3 && !flag1)
            {
                f28 = f1;
                l1 = j;
            }
            else
            {
                BlockPos blockpos4 = blockpos$pooledmutableblockpos.setPos(blockpos$pooledmutableblockpos2).move(blockmodelrenderer$enumneighborinfo.corners[3]);
                f28 = worldIn.getBlockState(blockpos4).getAmbientOcclusionLightValue();
                f28 = BlockModelRenderer.fixAoLightValue(f28);
                l1 = state.getPackedLightmapCoords(worldIn, blockpos4);
            }

            int i3 = state.getPackedLightmapCoords(worldIn, centerPos);

            if (shapeState.get(0) || !worldIn.getBlockState(centerPos.offset(direction)).isOpaqueCube())
            {
                i3 = state.getPackedLightmapCoords(worldIn, centerPos.offset(direction));
            }

            float f4 = shapeState.get(0) ? worldIn.getBlockState(blockpos).getAmbientOcclusionLightValue() : worldIn.getBlockState(centerPos).getAmbientOcclusionLightValue();
            f4 = BlockModelRenderer.fixAoLightValue(f4);
            BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(direction);
            blockpos$pooledmutableblockpos.release();
            blockpos$pooledmutableblockpos1.release();
            blockpos$pooledmutableblockpos2.release();
            blockpos$pooledmutableblockpos3.release();
            blockpos$pooledmutableblockpos4.release();

            if (shapeState.get(1) && blockmodelrenderer$enumneighborinfo.doNonCubicWeight)
            {
                float f29 = (f3 + f + f26 + f4) * 0.25F;
                float f30 = (f2 + f + f25 + f4) * 0.25F;
                float f31 = (f2 + f1 + f27 + f4) * 0.25F;
                float f32 = (f3 + f1 + f28 + f4) * 0.25F;
                float f9 = faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[0].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[1].shape];
                float f10 = faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[2].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[3].shape];
                float f11 = faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[4].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[5].shape];
                float f12 = faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[6].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert0Weights[7].shape];
                float f13 = faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[0].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[1].shape];
                float f14 = faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[2].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[3].shape];
                float f15 = faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[4].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[5].shape];
                float f16 = faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[6].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert1Weights[7].shape];
                float f17 = faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[0].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[1].shape];
                float f18 = faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[2].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[3].shape];
                float f19 = faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[4].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[5].shape];
                float f20 = faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[6].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert2Weights[7].shape];
                float f21 = faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[0].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[1].shape];
                float f22 = faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[2].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[3].shape];
                float f23 = faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[4].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[5].shape];
                float f24 = faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[6].shape] * faceShape[blockmodelrenderer$enumneighborinfo.vert3Weights[7].shape];
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f29 * f9 + f30 * f10 + f31 * f11 + f32 * f12;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f29 * f13 + f30 * f14 + f31 * f15 + f32 * f16;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f29 * f17 + f30 * f18 + f31 * f19 + f32 * f20;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f29 * f21 + f30 * f22 + f31 * f23 + f32 * f24;
                int i2 = this.getAoBrightness(l, i, j1, i3);
                int j2 = this.getAoBrightness(k, i, i1, i3);
                int k2 = this.getAoBrightness(k, j, k1, i3);
                int l2 = this.getAoBrightness(l, j, l1, i3);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getVertexBrightness(i2, j2, k2, l2, f9, f10, f11, f12);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getVertexBrightness(i2, j2, k2, l2, f13, f14, f15, f16);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getVertexBrightness(i2, j2, k2, l2, f17, f18, f19, f20);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getVertexBrightness(i2, j2, k2, l2, f21, f22, f23, f24);
            }
            else
            {
                float f5 = (f3 + f + f26 + f4) * 0.25F;
                float f6 = (f2 + f + f25 + f4) * 0.25F;
                float f7 = (f2 + f1 + f27 + f4) * 0.25F;
                float f8 = (f3 + f1 + f28 + f4) * 0.25F;
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getAoBrightness(l, i, j1, i3);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getAoBrightness(k, i, i1, i3);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getAoBrightness(k, j, k1, i3);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getAoBrightness(l, j, l1, i3);
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f5;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f6;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f7;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f8;
            }
        }

        private int getAoBrightness(int br1, int br2, int br3, int br4)
        {
            if (br1 == 0)
            {
                br1 = br4;
            }

            if (br2 == 0)
            {
                br2 = br4;
            }

            if (br3 == 0)
            {
                br3 = br4;
            }

            return br1 + br2 + br3 + br4 >> 2 & 16711935;
        }

        private int getVertexBrightness(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_)
        {
            int i = (int)((float)(p_178203_1_ >> 16 & 255) * p_178203_5_ + (float)(p_178203_2_ >> 16 & 255) * p_178203_6_ + (float)(p_178203_3_ >> 16 & 255) * p_178203_7_ + (float)(p_178203_4_ >> 16 & 255) * p_178203_8_) & 255;
            int j = (int)((float)(p_178203_1_ & 255) * p_178203_5_ + (float)(p_178203_2_ & 255) * p_178203_6_ + (float)(p_178203_3_ & 255) * p_178203_7_ + (float)(p_178203_4_ & 255) * p_178203_8_) & 255;
            return i << 16 | j;
        }
    }

    public static enum EnumNeighborInfo
    {
        DOWN(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.5F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH}),
        UP(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH}, 1.0F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH}),
        NORTH(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST}),
        SOUTH(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST}),
        WEST(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH}),
        EAST(new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH});

        private final EnumFacing[] corners;
        private final float shadeWeight;
        private final boolean doNonCubicWeight;
        private final BlockModelRenderer.Orientation[] vert0Weights;
        private final BlockModelRenderer.Orientation[] vert1Weights;
        private final BlockModelRenderer.Orientation[] vert2Weights;
        private final BlockModelRenderer.Orientation[] vert3Weights;
        private static final BlockModelRenderer.EnumNeighborInfo[] VALUES = new BlockModelRenderer.EnumNeighborInfo[6];

        private EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_)
        {
            this.corners = p_i46236_3_;
            this.shadeWeight = p_i46236_4_;
            this.doNonCubicWeight = p_i46236_5_;
            this.vert0Weights = p_i46236_6_;
            this.vert1Weights = p_i46236_7_;
            this.vert2Weights = p_i46236_8_;
            this.vert3Weights = p_i46236_9_;
        }

        public static BlockModelRenderer.EnumNeighborInfo getNeighbourInfo(EnumFacing p_178273_0_)
        {
            return VALUES[p_178273_0_.getIndex()];
        }

        static {
            VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
            VALUES[EnumFacing.UP.getIndex()] = UP;
            VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
            VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
            VALUES[EnumFacing.WEST.getIndex()] = WEST;
            VALUES[EnumFacing.EAST.getIndex()] = EAST;
        }
    }

    public static enum Orientation
    {
        DOWN(EnumFacing.DOWN, false),
        UP(EnumFacing.UP, false),
        NORTH(EnumFacing.NORTH, false),
        SOUTH(EnumFacing.SOUTH, false),
        WEST(EnumFacing.WEST, false),
        EAST(EnumFacing.EAST, false),
        FLIP_DOWN(EnumFacing.DOWN, true),
        FLIP_UP(EnumFacing.UP, true),
        FLIP_NORTH(EnumFacing.NORTH, true),
        FLIP_SOUTH(EnumFacing.SOUTH, true),
        FLIP_WEST(EnumFacing.WEST, true),
        FLIP_EAST(EnumFacing.EAST, true);

        private final int shape;

        private Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_)
        {
            this.shape = p_i46233_3_.getIndex() + (p_i46233_4_ ? EnumFacing.values().length : 0);
        }
    }

    static enum VertexTranslations
    {
        DOWN(0, 1, 2, 3),
        UP(2, 3, 0, 1),
        NORTH(3, 0, 1, 2),
        SOUTH(0, 1, 2, 3),
        WEST(3, 0, 1, 2),
        EAST(1, 2, 3, 0);

        private final int vert0;
        private final int vert1;
        private final int vert2;
        private final int vert3;
        private static final BlockModelRenderer.VertexTranslations[] VALUES = new BlockModelRenderer.VertexTranslations[6];

        private VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_)
        {
            this.vert0 = p_i46234_3_;
            this.vert1 = p_i46234_4_;
            this.vert2 = p_i46234_5_;
            this.vert3 = p_i46234_6_;
        }

        public static BlockModelRenderer.VertexTranslations getVertexTranslations(EnumFacing p_178184_0_)
        {
            return VALUES[p_178184_0_.getIndex()];
        }

        static {
            VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
            VALUES[EnumFacing.UP.getIndex()] = UP;
            VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
            VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
            VALUES[EnumFacing.WEST.getIndex()] = WEST;
            VALUES[EnumFacing.EAST.getIndex()] = EAST;
        }
    }
}
