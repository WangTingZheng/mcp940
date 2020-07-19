package net.minecraft.client.renderer;

import net.minecraft.util.BlockRenderLayer;

public class RegionRenderCacheBuilder
{
    private final BufferBuilder[] worldRenderers = new BufferBuilder[BlockRenderLayer.values().length];

    public RegionRenderCacheBuilder()
    {
        this.worldRenderers[BlockRenderLayer.SOLID.ordinal()] = new BufferBuilder(2097152);
        this.worldRenderers[BlockRenderLayer.CUTOUT.ordinal()] = new BufferBuilder(131072);
        this.worldRenderers[BlockRenderLayer.CUTOUT_MIPPED.ordinal()] = new BufferBuilder(131072);
        this.worldRenderers[BlockRenderLayer.TRANSLUCENT.ordinal()] = new BufferBuilder(262144);
    }

    public BufferBuilder getWorldRendererByLayer(BlockRenderLayer layer)
    {
        return this.worldRenderers[layer.ordinal()];
    }

    public BufferBuilder getWorldRendererByLayerId(int id)
    {
        return this.worldRenderers[id];
    }
}
