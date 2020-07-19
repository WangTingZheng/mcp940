package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;

public interface IVertexConsumer
{
    VertexFormat getVertexFormat();

    void setQuadTint(int var1);

    void setQuadOrientation(EnumFacing var1);

    void setQuadColored();

    void put(int var1, float... var2);
}
