package net.minecraft.world;

public class ColorizerFoliage
{
    /** Color buffer for foliage */
    private static int[] foliageBuffer = new int[65536];

    public static void setFoliageBiomeColorizer(int[] foliageBufferIn)
    {
        foliageBuffer = foliageBufferIn;
    }

    /**
     * Gets the color modifier to use for foliage.
     */
    public static int getFoliageColor(double temperature, double humidity)
    {
        humidity = humidity * temperature;
        int i = (int)((1.0D - temperature) * 255.0D);
        int j = (int)((1.0D - humidity) * 255.0D);
        return foliageBuffer[j << 8 | i];
    }

    /**
     * Gets the foliage color for pine type (metadata 1) trees
     */
    public static int getFoliageColorPine()
    {
        return 6396257;
    }

    /**
     * Gets the foliage color for birch type (metadata 2) trees
     */
    public static int getFoliageColorBirch()
    {
        return 8431445;
    }

    public static int getFoliageColorBasic()
    {
        return 4764952;
    }
}
