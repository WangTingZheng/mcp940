package net.minecraft.util;

public enum BlockRenderLayer
{
    SOLID("Solid"),
    CUTOUT_MIPPED("Mipped Cutout"),
    CUTOUT("Cutout"),
    TRANSLUCENT("Translucent");

    private final String layerName;

    private BlockRenderLayer(String layerNameIn)
    {
        this.layerName = layerNameIn;
    }

    public String toString()
    {
        return this.layerName;
    }
}
