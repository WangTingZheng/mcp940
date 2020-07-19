package net.minecraft.src;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;

public class RenderInfoLazy
{
    private RenderChunk renderChunk;
    private RenderGlobal.ContainerLocalRenderInformation renderInfo;

    public RenderChunk getRenderChunk()
    {
        return this.renderChunk;
    }

    public void setRenderChunk(RenderChunk p_setRenderChunk_1_)
    {
        this.renderChunk = p_setRenderChunk_1_;
        this.renderInfo = null;
    }

    public RenderGlobal.ContainerLocalRenderInformation getRenderInfo()
    {
        if (this.renderInfo == null)
        {
            this.renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this.renderChunk, (EnumFacing)null, 0);
        }

        return this.renderInfo;
    }
}
