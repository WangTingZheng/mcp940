package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockModelCustomizer
{
    private static final List<BakedQuad> NO_QUADS = ImmutableList.<BakedQuad>of();

    public static IBakedModel getRenderModel(IBakedModel p_getRenderModel_0_, IBlockState p_getRenderModel_1_, RenderEnv p_getRenderModel_2_)
    {
        if (p_getRenderModel_2_.isSmartLeaves())
        {
            p_getRenderModel_0_ = SmartLeaves.getLeavesModel(p_getRenderModel_0_, p_getRenderModel_1_);
        }

        return p_getRenderModel_0_;
    }

    public static List<BakedQuad> getRenderQuads(List<BakedQuad> p_getRenderQuads_0_, IBlockAccess p_getRenderQuads_1_, IBlockState p_getRenderQuads_2_, BlockPos p_getRenderQuads_3_, EnumFacing p_getRenderQuads_4_, long p_getRenderQuads_5_, RenderEnv p_getRenderQuads_7_)
    {
        if (p_getRenderQuads_4_ != null)
        {
            if (p_getRenderQuads_7_.isSmartLeaves() && p_getRenderQuads_1_.getBlockState(p_getRenderQuads_3_.offset(p_getRenderQuads_4_)) == p_getRenderQuads_2_)
            {
                return NO_QUADS;
            }

            if (!p_getRenderQuads_7_.isBreakingAnimation(p_getRenderQuads_0_) && Config.isBetterGrass())
            {
                p_getRenderQuads_0_ = BetterGrass.getFaceQuads(p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_4_, p_getRenderQuads_0_);
            }
        }

        List<BakedQuad> list = p_getRenderQuads_7_.getListQuadsCustomizer();
        list.clear();

        for (int i = 0; i < p_getRenderQuads_0_.size(); ++i)
        {
            BakedQuad bakedquad = p_getRenderQuads_0_.get(i);
            BakedQuad[] abakedquad = getRenderQuads(bakedquad, p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_4_, p_getRenderQuads_5_, p_getRenderQuads_7_);

            if (i == 0 && p_getRenderQuads_0_.size() == 1 && abakedquad.length == 1 && abakedquad[0] == bakedquad)
            {
                return p_getRenderQuads_0_;
            }

            for (int j = 0; j < abakedquad.length; ++j)
            {
                BakedQuad bakedquad1 = abakedquad[j];
                list.add(bakedquad1);
            }
        }

        return list;
    }

    private static BakedQuad[] getRenderQuads(BakedQuad p_getRenderQuads_0_, IBlockAccess p_getRenderQuads_1_, IBlockState p_getRenderQuads_2_, BlockPos p_getRenderQuads_3_, EnumFacing p_getRenderQuads_4_, long p_getRenderQuads_5_, RenderEnv p_getRenderQuads_7_)
    {
        if (p_getRenderQuads_7_.isBreakingAnimation(p_getRenderQuads_0_))
        {
            return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
        }
        else
        {
            BakedQuad bakedquad = p_getRenderQuads_0_;

            if (Config.isConnectedTextures())
            {
                BakedQuad[] abakedquad = ConnectedTextures.getConnectedTexture(p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_0_, p_getRenderQuads_7_);

                if (abakedquad.length != 1 || abakedquad[0] != p_getRenderQuads_0_)
                {
                    return abakedquad;
                }
            }

            if (Config.isNaturalTextures())
            {
                p_getRenderQuads_0_ = NaturalTextures.getNaturalTexture(p_getRenderQuads_3_, p_getRenderQuads_0_);

                if (p_getRenderQuads_0_ != bakedquad)
                {
                    return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
                }
            }

            return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
        }
    }
}
