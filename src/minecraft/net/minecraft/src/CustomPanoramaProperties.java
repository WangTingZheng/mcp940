package net.minecraft.src;

import java.util.Properties;
import net.minecraft.util.ResourceLocation;

public class CustomPanoramaProperties
{
    private String path;
    private ResourceLocation[] panoramaLocations;
    private int weight = 1;
    private int blur1 = 64;
    private int blur2 = 3;
    private int blur3 = 3;
    private int overlay1Top = -2130706433;
    private int overlay1Bottom = 16777215;
    private int overlay2Top = 0;
    private int overlay2Bottom = Integer.MIN_VALUE;

    public CustomPanoramaProperties(String p_i31_1_, Properties p_i31_2_)
    {
        ConnectedParser connectedparser = new ConnectedParser("CustomPanorama");
        this.path = p_i31_1_;
        this.panoramaLocations = new ResourceLocation[6];

        for (int i = 0; i < this.panoramaLocations.length; ++i)
        {
            this.panoramaLocations[i] = new ResourceLocation(p_i31_1_ + "/panorama_" + i + ".png");
        }

        this.weight = connectedparser.parseInt(p_i31_2_.getProperty("weight"), 1);
        this.blur1 = connectedparser.parseInt(p_i31_2_.getProperty("blur1"), 64);
        this.blur2 = connectedparser.parseInt(p_i31_2_.getProperty("blur2"), 3);
        this.blur3 = connectedparser.parseInt(p_i31_2_.getProperty("blur3"), 3);
        this.overlay1Top = ConnectedParser.parseColor4(p_i31_2_.getProperty("overlay1.top"), -2130706433);
        this.overlay1Bottom = ConnectedParser.parseColor4(p_i31_2_.getProperty("overlay1.bottom"), 16777215);
        this.overlay2Top = ConnectedParser.parseColor4(p_i31_2_.getProperty("overlay2.top"), 0);
        this.overlay2Bottom = ConnectedParser.parseColor4(p_i31_2_.getProperty("overlay2.bottom"), Integer.MIN_VALUE);
    }

    public ResourceLocation[] getPanoramaLocations()
    {
        return this.panoramaLocations;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public int getBlur1()
    {
        return this.blur1;
    }

    public int getBlur2()
    {
        return this.blur2;
    }

    public int getBlur3()
    {
        return this.blur3;
    }

    public int getOverlay1Top()
    {
        return this.overlay1Top;
    }

    public int getOverlay1Bottom()
    {
        return this.overlay1Bottom;
    }

    public int getOverlay2Top()
    {
        return this.overlay2Top;
    }

    public int getOverlay2Bottom()
    {
        return this.overlay2Bottom;
    }

    public String toString()
    {
        return "" + this.path + ", weight: " + this.weight + ", blur: " + this.blur1 + " " + this.blur2 + " " + this.blur3 + ", overlay: " + this.overlay1Top + " " + this.overlay1Bottom + " " + this.overlay2Top + " " + this.overlay2Bottom;
    }
}
