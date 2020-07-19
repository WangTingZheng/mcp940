package net.minecraft.client.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private final BlockModelShapes blockModelShapes;
    private final BlockModelRenderer blockModelRenderer;
    private final ChestRenderer chestRenderer = new ChestRenderer();
    private final BlockFluidRenderer fluidRenderer;

    public BlockRendererDispatcher(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_)
    {
        this.blockModelShapes = p_i46577_1_;
        this.blockModelRenderer = new BlockModelRenderer(p_i46577_2_);
        this.fluidRenderer = new BlockFluidRenderer(p_i46577_2_);
    }

    public BlockModelShapes getBlockModelShapes()
    {
        return this.blockModelShapes;
    }

    public void renderBlockDamage(IBlockState state, BlockPos pos, TextureAtlasSprite texture, IBlockAccess blockAccess)
    {
        if (state.getRenderType() == EnumBlockRenderType.MODEL)
        {
            state = state.getActualState(blockAccess, pos);
            IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
            IBakedModel ibakedmodel1 = (new SimpleBakedModel.Builder(state, ibakedmodel, texture, pos)).makeBakedModel();
            this.blockModelRenderer.renderModel(blockAccess, ibakedmodel1, state, pos, Tessellator.getInstance().getBuffer(), true);
        }
    }

    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder worldRendererIn)
    {
        try
        {
            EnumBlockRenderType enumblockrendertype = state.getRenderType();

            if (enumblockrendertype == EnumBlockRenderType.INVISIBLE)
            {
                return false;
            }
            else
            {
                if (blockAccess.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES)
                {
                    try
                    {
                        state = state.getActualState(blockAccess, pos);
                    }
                    catch (Exception var8)
                    {
                        ;
                    }
                }

                switch (enumblockrendertype)
                {
                    case MODEL:
                        return this.blockModelRenderer.renderModel(blockAccess, this.getModelForState(state), state, pos, worldRendererIn, true);

                    case ENTITYBLOCK_ANIMATED:
                        return false;

                    case LIQUID:
                        return this.fluidRenderer.renderFluid(blockAccess, state, pos, worldRendererIn);

                    default:
                        return false;
                }
            }
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
            throw new ReportedException(crashreport);
        }
    }

    public BlockModelRenderer getBlockModelRenderer()
    {
        return this.blockModelRenderer;
    }

    public IBakedModel getModelForState(IBlockState state)
    {
        return this.blockModelShapes.getModelForState(state);
    }

    @SuppressWarnings("incomplete-switch")
    public void renderBlockBrightness(IBlockState state, float brightness)
    {
        EnumBlockRenderType enumblockrendertype = state.getRenderType();

        if (enumblockrendertype != EnumBlockRenderType.INVISIBLE)
        {
            switch (enumblockrendertype)
            {
                case MODEL:
                    IBakedModel ibakedmodel = this.getModelForState(state);
                    this.blockModelRenderer.renderModelBrightness(ibakedmodel, state, brightness, true);
                    break;

                case ENTITYBLOCK_ANIMATED:
                    this.chestRenderer.renderChestBrightness(state.getBlock(), brightness);

                case LIQUID:
            }
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        this.fluidRenderer.initAtlasSprites();
    }
}
