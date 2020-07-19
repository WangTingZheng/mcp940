package net.minecraft.client.renderer.block.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class MultipartBakedModel implements IBakedModel
{
    private final Map<Predicate<IBlockState>, IBakedModel> selectors;
    protected final boolean ambientOcclusion;
    protected final boolean gui3D;
    protected final TextureAtlasSprite particleTexture;
    protected final ItemCameraTransforms cameraTransforms;
    protected final ItemOverrideList overrides;

    public MultipartBakedModel(Map<Predicate<IBlockState>, IBakedModel> selectorsIn)
    {
        this.selectors = selectorsIn;
        IBakedModel ibakedmodel = selectorsIn.values().iterator().next();
        this.ambientOcclusion = ibakedmodel.isAmbientOcclusion();
        this.gui3D = ibakedmodel.isGui3d();
        this.particleTexture = ibakedmodel.getParticleTexture();
        this.cameraTransforms = ibakedmodel.getItemCameraTransforms();
        this.overrides = ibakedmodel.getOverrides();
    }

    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
    {
        List<BakedQuad> list = Lists.<BakedQuad>newArrayList();

        if (state != null)
        {
            for (Entry<Predicate<IBlockState>, IBakedModel> entry : this.selectors.entrySet())
            {
                if (((Predicate)entry.getKey()).apply(state))
                {
                    list.addAll((entry.getValue()).getQuads(state, side, rand++));
                }
            }
        }

        return list;
    }

    public boolean isAmbientOcclusion()
    {
        return this.ambientOcclusion;
    }

    public boolean isGui3d()
    {
        return this.gui3D;
    }

    public boolean isBuiltInRenderer()
    {
        return false;
    }

    public TextureAtlasSprite getParticleTexture()
    {
        return this.particleTexture;
    }

    public ItemCameraTransforms getItemCameraTransforms()
    {
        return this.cameraTransforms;
    }

    public ItemOverrideList getOverrides()
    {
        return this.overrides;
    }

    public static class Builder
    {
        private final Map<Predicate<IBlockState>, IBakedModel> builderSelectors = Maps.<Predicate<IBlockState>, IBakedModel>newLinkedHashMap();

        public void putModel(Predicate<IBlockState> predicate, IBakedModel model)
        {
            this.builderSelectors.put(predicate, model);
        }

        public IBakedModel makeMultipartModel()
        {
            return new MultipartBakedModel(this.builderSelectors);
        }
    }
}
