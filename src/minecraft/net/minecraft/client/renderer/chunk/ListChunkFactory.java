package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;

public class ListChunkFactory implements IRenderChunkFactory
{
    public RenderChunk create(World worldIn, RenderGlobal renderGlobalIn, int index)
    {
        return new ListedRenderChunk(worldIn, renderGlobalIn, index);
    }
}
