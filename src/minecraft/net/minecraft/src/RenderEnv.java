package net.minecraft.src;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class RenderEnv
{
    private IBlockAccess blockAccess;
    private IBlockState blockState;
    private BlockPos blockPos;
    private int blockId = -1;
    private int metadata = -1;
    private int breakingAnimation = -1;
    private int smartLeaves = -1;
    private float[] quadBounds = new float[EnumFacing.VALUES.length * 2];
    private BitSet boundsFlags = new BitSet(3);
    private BlockModelRenderer.AmbientOcclusionFace aoFace = new BlockModelRenderer.AmbientOcclusionFace();
    private BlockPosM colorizerBlockPosM = null;
    private boolean[] borderFlags = null;
    private boolean[] borderFlags2 = null;
    private boolean[] borderFlags3 = null;
    private EnumFacing[] borderDirections = null;
    private List<BakedQuad> listQuadsCustomizer = new ArrayList<BakedQuad>();
    private List<BakedQuad> listQuadsCtmMultipass = new ArrayList<BakedQuad>();
    private BakedQuad[] arrayQuadsCtm1 = new BakedQuad[1];
    private BakedQuad[] arrayQuadsCtm2 = new BakedQuad[2];
    private BakedQuad[] arrayQuadsCtm3 = new BakedQuad[3];
    private BakedQuad[] arrayQuadsCtm4 = new BakedQuad[4];
    private RegionRenderCacheBuilder regionRenderCacheBuilder = null;
    private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[BlockRenderLayer.values().length];
    private boolean overlaysRendered = false;
    private static final int UNKNOWN = -1;
    private static final int FALSE = 0;
    private static final int TRUE = 1;

    public RenderEnv(IBlockAccess p_i93_1_, IBlockState p_i93_2_, BlockPos p_i93_3_)
    {
        this.blockAccess = p_i93_1_;
        this.blockState = p_i93_2_;
        this.blockPos = p_i93_3_;
    }

    public void reset(IBlockAccess p_reset_1_, IBlockState p_reset_2_, BlockPos p_reset_3_)
    {
        if (this.blockAccess != p_reset_1_ || this.blockState != p_reset_2_ || this.blockPos != p_reset_3_)
        {
            this.blockAccess = p_reset_1_;
            this.blockState = p_reset_2_;
            this.blockPos = p_reset_3_;
            this.blockId = -1;
            this.metadata = -1;
            this.breakingAnimation = -1;
            this.smartLeaves = -1;
            this.boundsFlags.clear();
        }
    }

    public int getBlockId()
    {
        if (this.blockId < 0)
        {
            if (this.blockState instanceof BlockStateBase)
            {
                BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
                this.blockId = blockstatebase.getBlockId();
            }
            else
            {
                this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
            }
        }

        return this.blockId;
    }

    public int getMetadata()
    {
        if (this.metadata < 0)
        {
            if (this.blockState instanceof BlockStateBase)
            {
                BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
                this.metadata = blockstatebase.getMetadata();
            }
            else
            {
                this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
            }
        }

        return this.metadata;
    }

    public float[] getQuadBounds()
    {
        return this.quadBounds;
    }

    public BitSet getBoundsFlags()
    {
        return this.boundsFlags;
    }

    public BlockModelRenderer.AmbientOcclusionFace getAoFace()
    {
        return this.aoFace;
    }

    public boolean isBreakingAnimation(List p_isBreakingAnimation_1_)
    {
        if (this.breakingAnimation == -1 && p_isBreakingAnimation_1_.size() > 0)
        {
            if (p_isBreakingAnimation_1_.get(0) instanceof BakedQuadRetextured)
            {
                this.breakingAnimation = 1;
            }
            else
            {
                this.breakingAnimation = 0;
            }
        }

        return this.breakingAnimation == 1;
    }

    public boolean isBreakingAnimation(BakedQuad p_isBreakingAnimation_1_)
    {
        if (this.breakingAnimation < 0)
        {
            if (p_isBreakingAnimation_1_ instanceof BakedQuadRetextured)
            {
                this.breakingAnimation = 1;
            }
            else
            {
                this.breakingAnimation = 0;
            }
        }

        return this.breakingAnimation == 1;
    }

    public boolean isBreakingAnimation()
    {
        return this.breakingAnimation == 1;
    }

    public IBlockState getBlockState()
    {
        return this.blockState;
    }

    public BlockPosM getColorizerBlockPosM()
    {
        if (this.colorizerBlockPosM == null)
        {
            this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
        }

        return this.colorizerBlockPosM;
    }

    public boolean[] getBorderFlags()
    {
        if (this.borderFlags == null)
        {
            this.borderFlags = new boolean[4];
        }

        return this.borderFlags;
    }

    public boolean[] getBorderFlags2()
    {
        if (this.borderFlags2 == null)
        {
            this.borderFlags2 = new boolean[4];
        }

        return this.borderFlags2;
    }

    public boolean[] getBorderFlags3()
    {
        if (this.borderFlags3 == null)
        {
            this.borderFlags3 = new boolean[4];
        }

        return this.borderFlags3;
    }

    public EnumFacing[] getBorderDirections()
    {
        if (this.borderDirections == null)
        {
            this.borderDirections = new EnumFacing[4];
        }

        return this.borderDirections;
    }

    public EnumFacing[] getBorderDirections(EnumFacing p_getBorderDirections_1_, EnumFacing p_getBorderDirections_2_, EnumFacing p_getBorderDirections_3_, EnumFacing p_getBorderDirections_4_)
    {
        EnumFacing[] aenumfacing = this.getBorderDirections();
        aenumfacing[0] = p_getBorderDirections_1_;
        aenumfacing[1] = p_getBorderDirections_2_;
        aenumfacing[2] = p_getBorderDirections_3_;
        aenumfacing[3] = p_getBorderDirections_4_;
        return aenumfacing;
    }

    public boolean isSmartLeaves()
    {
        if (this.smartLeaves == -1)
        {
            if (Config.isTreesSmart() && this.blockState.getBlock() instanceof BlockLeaves)
            {
                this.smartLeaves = 1;
            }
            else
            {
                this.smartLeaves = 0;
            }
        }

        return this.smartLeaves == 1;
    }

    public List<BakedQuad> getListQuadsCustomizer()
    {
        return this.listQuadsCustomizer;
    }

    public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_)
    {
        this.arrayQuadsCtm1[0] = p_getArrayQuadsCtm_1_;
        return this.arrayQuadsCtm1;
    }

    public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_)
    {
        this.arrayQuadsCtm2[0] = p_getArrayQuadsCtm_1_;
        this.arrayQuadsCtm2[1] = p_getArrayQuadsCtm_2_;
        return this.arrayQuadsCtm2;
    }

    public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_, BakedQuad p_getArrayQuadsCtm_3_)
    {
        this.arrayQuadsCtm3[0] = p_getArrayQuadsCtm_1_;
        this.arrayQuadsCtm3[1] = p_getArrayQuadsCtm_2_;
        this.arrayQuadsCtm3[2] = p_getArrayQuadsCtm_3_;
        return this.arrayQuadsCtm3;
    }

    public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_, BakedQuad p_getArrayQuadsCtm_3_, BakedQuad p_getArrayQuadsCtm_4_)
    {
        this.arrayQuadsCtm4[0] = p_getArrayQuadsCtm_1_;
        this.arrayQuadsCtm4[1] = p_getArrayQuadsCtm_2_;
        this.arrayQuadsCtm4[2] = p_getArrayQuadsCtm_3_;
        this.arrayQuadsCtm4[3] = p_getArrayQuadsCtm_4_;
        return this.arrayQuadsCtm4;
    }

    public List<BakedQuad> getListQuadsCtmMultipass(BakedQuad[] p_getListQuadsCtmMultipass_1_)
    {
        this.listQuadsCtmMultipass.clear();

        if (p_getListQuadsCtmMultipass_1_ != null)
        {
            for (int i = 0; i < p_getListQuadsCtmMultipass_1_.length; ++i)
            {
                BakedQuad bakedquad = p_getListQuadsCtmMultipass_1_[i];
                this.listQuadsCtmMultipass.add(bakedquad);
            }
        }

        return this.listQuadsCtmMultipass;
    }

    public RegionRenderCacheBuilder getRegionRenderCacheBuilder()
    {
        return this.regionRenderCacheBuilder;
    }

    public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder p_setRegionRenderCacheBuilder_1_)
    {
        this.regionRenderCacheBuilder = p_setRegionRenderCacheBuilder_1_;
    }

    public ListQuadsOverlay getListQuadsOverlay(BlockRenderLayer p_getListQuadsOverlay_1_)
    {
        ListQuadsOverlay listquadsoverlay = this.listsQuadsOverlay[p_getListQuadsOverlay_1_.ordinal()];

        if (listquadsoverlay == null)
        {
            listquadsoverlay = new ListQuadsOverlay();
            this.listsQuadsOverlay[p_getListQuadsOverlay_1_.ordinal()] = listquadsoverlay;
        }

        return listquadsoverlay;
    }

    public boolean isOverlaysRendered()
    {
        return this.overlaysRendered;
    }

    public void setOverlaysRendered(boolean p_setOverlaysRendered_1_)
    {
        this.overlaysRendered = p_setOverlaysRendered_1_;
    }
}
