package net.minecraft.world.gen;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.init.Biomes;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.Biome;

public class ChunkGeneratorSettings
{
    public final float coordinateScale;
    public final float heightScale;
    public final float upperLimitScale;
    public final float lowerLimitScale;
    public final float depthNoiseScaleX;
    public final float depthNoiseScaleZ;
    public final float depthNoiseScaleExponent;
    public final float mainNoiseScaleX;
    public final float mainNoiseScaleY;
    public final float mainNoiseScaleZ;
    public final float baseSize;
    public final float stretchY;
    public final float biomeDepthWeight;
    public final float biomeDepthOffSet;
    public final float biomeScaleWeight;
    public final float biomeScaleOffset;
    public final int seaLevel;
    public final boolean useCaves;
    public final boolean useDungeons;
    public final int dungeonChance;
    public final boolean useStrongholds;
    public final boolean useVillages;
    public final boolean useMineShafts;
    public final boolean useTemples;
    public final boolean useMonuments;
    public final boolean useMansions;
    public final boolean useRavines;
    public final boolean useWaterLakes;
    public final int waterLakeChance;
    public final boolean useLavaLakes;
    public final int lavaLakeChance;
    public final boolean useLavaOceans;
    public final int fixedBiome;
    public final int biomeSize;
    public final int riverSize;
    public final int dirtSize;
    public final int dirtCount;
    public final int dirtMinHeight;
    public final int dirtMaxHeight;
    public final int gravelSize;
    public final int gravelCount;
    public final int gravelMinHeight;
    public final int gravelMaxHeight;
    public final int graniteSize;
    public final int graniteCount;
    public final int graniteMinHeight;
    public final int graniteMaxHeight;
    public final int dioriteSize;
    public final int dioriteCount;
    public final int dioriteMinHeight;
    public final int dioriteMaxHeight;
    public final int andesiteSize;
    public final int andesiteCount;
    public final int andesiteMinHeight;
    public final int andesiteMaxHeight;
    public final int coalSize;
    public final int coalCount;
    public final int coalMinHeight;
    public final int coalMaxHeight;
    public final int ironSize;
    public final int ironCount;
    public final int ironMinHeight;
    public final int ironMaxHeight;
    public final int goldSize;
    public final int goldCount;
    public final int goldMinHeight;
    public final int goldMaxHeight;
    public final int redstoneSize;
    public final int redstoneCount;
    public final int redstoneMinHeight;
    public final int redstoneMaxHeight;
    public final int diamondSize;
    public final int diamondCount;
    public final int diamondMinHeight;
    public final int diamondMaxHeight;
    public final int lapisSize;
    public final int lapisCount;
    public final int lapisCenterHeight;
    public final int lapisSpread;

    private ChunkGeneratorSettings(ChunkGeneratorSettings.Factory settingsFactory)
    {
        this.coordinateScale = settingsFactory.coordinateScale;
        this.heightScale = settingsFactory.heightScale;
        this.upperLimitScale = settingsFactory.upperLimitScale;
        this.lowerLimitScale = settingsFactory.lowerLimitScale;
        this.depthNoiseScaleX = settingsFactory.depthNoiseScaleX;
        this.depthNoiseScaleZ = settingsFactory.depthNoiseScaleZ;
        this.depthNoiseScaleExponent = settingsFactory.depthNoiseScaleExponent;
        this.mainNoiseScaleX = settingsFactory.mainNoiseScaleX;
        this.mainNoiseScaleY = settingsFactory.mainNoiseScaleY;
        this.mainNoiseScaleZ = settingsFactory.mainNoiseScaleZ;
        this.baseSize = settingsFactory.baseSize;
        this.stretchY = settingsFactory.stretchY;
        this.biomeDepthWeight = settingsFactory.biomeDepthWeight;
        this.biomeDepthOffSet = settingsFactory.biomeDepthOffset;
        this.biomeScaleWeight = settingsFactory.biomeScaleWeight;
        this.biomeScaleOffset = settingsFactory.biomeScaleOffset;
        this.seaLevel = settingsFactory.seaLevel;
        this.useCaves = settingsFactory.useCaves;
        this.useDungeons = settingsFactory.useDungeons;
        this.dungeonChance = settingsFactory.dungeonChance;
        this.useStrongholds = settingsFactory.useStrongholds;
        this.useVillages = settingsFactory.useVillages;
        this.useMineShafts = settingsFactory.useMineShafts;
        this.useTemples = settingsFactory.useTemples;
        this.useMonuments = settingsFactory.useMonuments;
        this.useMansions = settingsFactory.useMansions;
        this.useRavines = settingsFactory.useRavines;
        this.useWaterLakes = settingsFactory.useWaterLakes;
        this.waterLakeChance = settingsFactory.waterLakeChance;
        this.useLavaLakes = settingsFactory.useLavaLakes;
        this.lavaLakeChance = settingsFactory.lavaLakeChance;
        this.useLavaOceans = settingsFactory.useLavaOceans;
        this.fixedBiome = settingsFactory.fixedBiome;
        this.biomeSize = settingsFactory.biomeSize;
        this.riverSize = settingsFactory.riverSize;
        this.dirtSize = settingsFactory.dirtSize;
        this.dirtCount = settingsFactory.dirtCount;
        this.dirtMinHeight = settingsFactory.dirtMinHeight;
        this.dirtMaxHeight = settingsFactory.dirtMaxHeight;
        this.gravelSize = settingsFactory.gravelSize;
        this.gravelCount = settingsFactory.gravelCount;
        this.gravelMinHeight = settingsFactory.gravelMinHeight;
        this.gravelMaxHeight = settingsFactory.gravelMaxHeight;
        this.graniteSize = settingsFactory.graniteSize;
        this.graniteCount = settingsFactory.graniteCount;
        this.graniteMinHeight = settingsFactory.graniteMinHeight;
        this.graniteMaxHeight = settingsFactory.graniteMaxHeight;
        this.dioriteSize = settingsFactory.dioriteSize;
        this.dioriteCount = settingsFactory.dioriteCount;
        this.dioriteMinHeight = settingsFactory.dioriteMinHeight;
        this.dioriteMaxHeight = settingsFactory.dioriteMaxHeight;
        this.andesiteSize = settingsFactory.andesiteSize;
        this.andesiteCount = settingsFactory.andesiteCount;
        this.andesiteMinHeight = settingsFactory.andesiteMinHeight;
        this.andesiteMaxHeight = settingsFactory.andesiteMaxHeight;
        this.coalSize = settingsFactory.coalSize;
        this.coalCount = settingsFactory.coalCount;
        this.coalMinHeight = settingsFactory.coalMinHeight;
        this.coalMaxHeight = settingsFactory.coalMaxHeight;
        this.ironSize = settingsFactory.ironSize;
        this.ironCount = settingsFactory.ironCount;
        this.ironMinHeight = settingsFactory.ironMinHeight;
        this.ironMaxHeight = settingsFactory.ironMaxHeight;
        this.goldSize = settingsFactory.goldSize;
        this.goldCount = settingsFactory.goldCount;
        this.goldMinHeight = settingsFactory.goldMinHeight;
        this.goldMaxHeight = settingsFactory.goldMaxHeight;
        this.redstoneSize = settingsFactory.redstoneSize;
        this.redstoneCount = settingsFactory.redstoneCount;
        this.redstoneMinHeight = settingsFactory.redstoneMinHeight;
        this.redstoneMaxHeight = settingsFactory.redstoneMaxHeight;
        this.diamondSize = settingsFactory.diamondSize;
        this.diamondCount = settingsFactory.diamondCount;
        this.diamondMinHeight = settingsFactory.diamondMinHeight;
        this.diamondMaxHeight = settingsFactory.diamondMaxHeight;
        this.lapisSize = settingsFactory.lapisSize;
        this.lapisCount = settingsFactory.lapisCount;
        this.lapisCenterHeight = settingsFactory.lapisCenterHeight;
        this.lapisSpread = settingsFactory.lapisSpread;
    }

    public static class Factory
    {
        @VisibleForTesting
        static final Gson JSON_ADAPTER = (new GsonBuilder()).registerTypeAdapter(ChunkGeneratorSettings.Factory.class, new ChunkGeneratorSettings.Serializer()).create();
        public float coordinateScale = 684.412F;
        public float heightScale = 684.412F;
        public float upperLimitScale = 512.0F;
        public float lowerLimitScale = 512.0F;
        public float depthNoiseScaleX = 200.0F;
        public float depthNoiseScaleZ = 200.0F;
        public float depthNoiseScaleExponent = 0.5F;
        public float mainNoiseScaleX = 80.0F;
        public float mainNoiseScaleY = 160.0F;
        public float mainNoiseScaleZ = 80.0F;
        public float baseSize = 8.5F;
        public float stretchY = 12.0F;
        public float biomeDepthWeight = 1.0F;
        public float biomeDepthOffset;
        public float biomeScaleWeight = 1.0F;
        public float biomeScaleOffset;
        public int seaLevel = 63;
        public boolean useCaves = true;
        public boolean useDungeons = true;
        public int dungeonChance = 8;
        public boolean useStrongholds = true;
        public boolean useVillages = true;
        public boolean useMineShafts = true;
        public boolean useTemples = true;
        public boolean useMonuments = true;
        public boolean useMansions = true;
        public boolean useRavines = true;
        public boolean useWaterLakes = true;
        public int waterLakeChance = 4;
        public boolean useLavaLakes = true;
        public int lavaLakeChance = 80;
        public boolean useLavaOceans;
        public int fixedBiome = -1;
        public int biomeSize = 4;
        public int riverSize = 4;
        public int dirtSize = 33;
        public int dirtCount = 10;
        public int dirtMinHeight;
        public int dirtMaxHeight = 256;
        public int gravelSize = 33;
        public int gravelCount = 8;
        public int gravelMinHeight;
        public int gravelMaxHeight = 256;
        public int graniteSize = 33;
        public int graniteCount = 10;
        public int graniteMinHeight;
        public int graniteMaxHeight = 80;
        public int dioriteSize = 33;
        public int dioriteCount = 10;
        public int dioriteMinHeight;
        public int dioriteMaxHeight = 80;
        public int andesiteSize = 33;
        public int andesiteCount = 10;
        public int andesiteMinHeight;
        public int andesiteMaxHeight = 80;
        public int coalSize = 17;
        public int coalCount = 20;
        public int coalMinHeight;
        public int coalMaxHeight = 128;
        public int ironSize = 9;
        public int ironCount = 20;
        public int ironMinHeight;
        public int ironMaxHeight = 64;
        public int goldSize = 9;
        public int goldCount = 2;
        public int goldMinHeight;
        public int goldMaxHeight = 32;
        public int redstoneSize = 8;
        public int redstoneCount = 8;
        public int redstoneMinHeight;
        public int redstoneMaxHeight = 16;
        public int diamondSize = 8;
        public int diamondCount = 1;
        public int diamondMinHeight;
        public int diamondMaxHeight = 16;
        public int lapisSize = 7;
        public int lapisCount = 1;
        public int lapisCenterHeight = 16;
        public int lapisSpread = 16;

        public static ChunkGeneratorSettings.Factory jsonToFactory(String p_177865_0_)
        {
            if (p_177865_0_.isEmpty())
            {
                return new ChunkGeneratorSettings.Factory();
            }
            else
            {
                try
                {
                    return (ChunkGeneratorSettings.Factory)JsonUtils.gsonDeserialize(JSON_ADAPTER, p_177865_0_, ChunkGeneratorSettings.Factory.class);
                }
                catch (Exception var2)
                {
                    return new ChunkGeneratorSettings.Factory();
                }
            }
        }

        public String toString()
        {
            return JSON_ADAPTER.toJson(this);
        }

        public Factory()
        {
            this.setDefaults();
        }

        public void setDefaults()
        {
            this.coordinateScale = 684.412F;
            this.heightScale = 684.412F;
            this.upperLimitScale = 512.0F;
            this.lowerLimitScale = 512.0F;
            this.depthNoiseScaleX = 200.0F;
            this.depthNoiseScaleZ = 200.0F;
            this.depthNoiseScaleExponent = 0.5F;
            this.mainNoiseScaleX = 80.0F;
            this.mainNoiseScaleY = 160.0F;
            this.mainNoiseScaleZ = 80.0F;
            this.baseSize = 8.5F;
            this.stretchY = 12.0F;
            this.biomeDepthWeight = 1.0F;
            this.biomeDepthOffset = 0.0F;
            this.biomeScaleWeight = 1.0F;
            this.biomeScaleOffset = 0.0F;
            this.seaLevel = 63;
            this.useCaves = true;
            this.useDungeons = true;
            this.dungeonChance = 8;
            this.useStrongholds = true;
            this.useVillages = true;
            this.useMineShafts = true;
            this.useTemples = true;
            this.useMonuments = true;
            this.useMansions = true;
            this.useRavines = true;
            this.useWaterLakes = true;
            this.waterLakeChance = 4;
            this.useLavaLakes = true;
            this.lavaLakeChance = 80;
            this.useLavaOceans = false;
            this.fixedBiome = -1;
            this.biomeSize = 4;
            this.riverSize = 4;
            this.dirtSize = 33;
            this.dirtCount = 10;
            this.dirtMinHeight = 0;
            this.dirtMaxHeight = 256;
            this.gravelSize = 33;
            this.gravelCount = 8;
            this.gravelMinHeight = 0;
            this.gravelMaxHeight = 256;
            this.graniteSize = 33;
            this.graniteCount = 10;
            this.graniteMinHeight = 0;
            this.graniteMaxHeight = 80;
            this.dioriteSize = 33;
            this.dioriteCount = 10;
            this.dioriteMinHeight = 0;
            this.dioriteMaxHeight = 80;
            this.andesiteSize = 33;
            this.andesiteCount = 10;
            this.andesiteMinHeight = 0;
            this.andesiteMaxHeight = 80;
            this.coalSize = 17;
            this.coalCount = 20;
            this.coalMinHeight = 0;
            this.coalMaxHeight = 128;
            this.ironSize = 9;
            this.ironCount = 20;
            this.ironMinHeight = 0;
            this.ironMaxHeight = 64;
            this.goldSize = 9;
            this.goldCount = 2;
            this.goldMinHeight = 0;
            this.goldMaxHeight = 32;
            this.redstoneSize = 8;
            this.redstoneCount = 8;
            this.redstoneMinHeight = 0;
            this.redstoneMaxHeight = 16;
            this.diamondSize = 8;
            this.diamondCount = 1;
            this.diamondMinHeight = 0;
            this.diamondMaxHeight = 16;
            this.lapisSize = 7;
            this.lapisCount = 1;
            this.lapisCenterHeight = 16;
            this.lapisSpread = 16;
        }

        public boolean equals(Object p_equals_1_)
        {
            if (this == p_equals_1_)
            {
                return true;
            }
            else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
            {
                ChunkGeneratorSettings.Factory chunkgeneratorsettings$factory = (ChunkGeneratorSettings.Factory)p_equals_1_;

                if (this.andesiteCount != chunkgeneratorsettings$factory.andesiteCount)
                {
                    return false;
                }
                else if (this.andesiteMaxHeight != chunkgeneratorsettings$factory.andesiteMaxHeight)
                {
                    return false;
                }
                else if (this.andesiteMinHeight != chunkgeneratorsettings$factory.andesiteMinHeight)
                {
                    return false;
                }
                else if (this.andesiteSize != chunkgeneratorsettings$factory.andesiteSize)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.baseSize, this.baseSize) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.biomeDepthOffset, this.biomeDepthOffset) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.biomeDepthWeight, this.biomeDepthWeight) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.biomeScaleOffset, this.biomeScaleOffset) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.biomeScaleWeight, this.biomeScaleWeight) != 0)
                {
                    return false;
                }
                else if (this.biomeSize != chunkgeneratorsettings$factory.biomeSize)
                {
                    return false;
                }
                else if (this.coalCount != chunkgeneratorsettings$factory.coalCount)
                {
                    return false;
                }
                else if (this.coalMaxHeight != chunkgeneratorsettings$factory.coalMaxHeight)
                {
                    return false;
                }
                else if (this.coalMinHeight != chunkgeneratorsettings$factory.coalMinHeight)
                {
                    return false;
                }
                else if (this.coalSize != chunkgeneratorsettings$factory.coalSize)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.coordinateScale, this.coordinateScale) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleX, this.depthNoiseScaleX) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0)
                {
                    return false;
                }
                else if (this.diamondCount != chunkgeneratorsettings$factory.diamondCount)
                {
                    return false;
                }
                else if (this.diamondMaxHeight != chunkgeneratorsettings$factory.diamondMaxHeight)
                {
                    return false;
                }
                else if (this.diamondMinHeight != chunkgeneratorsettings$factory.diamondMinHeight)
                {
                    return false;
                }
                else if (this.diamondSize != chunkgeneratorsettings$factory.diamondSize)
                {
                    return false;
                }
                else if (this.dioriteCount != chunkgeneratorsettings$factory.dioriteCount)
                {
                    return false;
                }
                else if (this.dioriteMaxHeight != chunkgeneratorsettings$factory.dioriteMaxHeight)
                {
                    return false;
                }
                else if (this.dioriteMinHeight != chunkgeneratorsettings$factory.dioriteMinHeight)
                {
                    return false;
                }
                else if (this.dioriteSize != chunkgeneratorsettings$factory.dioriteSize)
                {
                    return false;
                }
                else if (this.dirtCount != chunkgeneratorsettings$factory.dirtCount)
                {
                    return false;
                }
                else if (this.dirtMaxHeight != chunkgeneratorsettings$factory.dirtMaxHeight)
                {
                    return false;
                }
                else if (this.dirtMinHeight != chunkgeneratorsettings$factory.dirtMinHeight)
                {
                    return false;
                }
                else if (this.dirtSize != chunkgeneratorsettings$factory.dirtSize)
                {
                    return false;
                }
                else if (this.dungeonChance != chunkgeneratorsettings$factory.dungeonChance)
                {
                    return false;
                }
                else if (this.fixedBiome != chunkgeneratorsettings$factory.fixedBiome)
                {
                    return false;
                }
                else if (this.goldCount != chunkgeneratorsettings$factory.goldCount)
                {
                    return false;
                }
                else if (this.goldMaxHeight != chunkgeneratorsettings$factory.goldMaxHeight)
                {
                    return false;
                }
                else if (this.goldMinHeight != chunkgeneratorsettings$factory.goldMinHeight)
                {
                    return false;
                }
                else if (this.goldSize != chunkgeneratorsettings$factory.goldSize)
                {
                    return false;
                }
                else if (this.graniteCount != chunkgeneratorsettings$factory.graniteCount)
                {
                    return false;
                }
                else if (this.graniteMaxHeight != chunkgeneratorsettings$factory.graniteMaxHeight)
                {
                    return false;
                }
                else if (this.graniteMinHeight != chunkgeneratorsettings$factory.graniteMinHeight)
                {
                    return false;
                }
                else if (this.graniteSize != chunkgeneratorsettings$factory.graniteSize)
                {
                    return false;
                }
                else if (this.gravelCount != chunkgeneratorsettings$factory.gravelCount)
                {
                    return false;
                }
                else if (this.gravelMaxHeight != chunkgeneratorsettings$factory.gravelMaxHeight)
                {
                    return false;
                }
                else if (this.gravelMinHeight != chunkgeneratorsettings$factory.gravelMinHeight)
                {
                    return false;
                }
                else if (this.gravelSize != chunkgeneratorsettings$factory.gravelSize)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.heightScale, this.heightScale) != 0)
                {
                    return false;
                }
                else if (this.ironCount != chunkgeneratorsettings$factory.ironCount)
                {
                    return false;
                }
                else if (this.ironMaxHeight != chunkgeneratorsettings$factory.ironMaxHeight)
                {
                    return false;
                }
                else if (this.ironMinHeight != chunkgeneratorsettings$factory.ironMinHeight)
                {
                    return false;
                }
                else if (this.ironSize != chunkgeneratorsettings$factory.ironSize)
                {
                    return false;
                }
                else if (this.lapisCenterHeight != chunkgeneratorsettings$factory.lapisCenterHeight)
                {
                    return false;
                }
                else if (this.lapisCount != chunkgeneratorsettings$factory.lapisCount)
                {
                    return false;
                }
                else if (this.lapisSize != chunkgeneratorsettings$factory.lapisSize)
                {
                    return false;
                }
                else if (this.lapisSpread != chunkgeneratorsettings$factory.lapisSpread)
                {
                    return false;
                }
                else if (this.lavaLakeChance != chunkgeneratorsettings$factory.lavaLakeChance)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.lowerLimitScale, this.lowerLimitScale) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleX, this.mainNoiseScaleX) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleY, this.mainNoiseScaleY) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0)
                {
                    return false;
                }
                else if (this.redstoneCount != chunkgeneratorsettings$factory.redstoneCount)
                {
                    return false;
                }
                else if (this.redstoneMaxHeight != chunkgeneratorsettings$factory.redstoneMaxHeight)
                {
                    return false;
                }
                else if (this.redstoneMinHeight != chunkgeneratorsettings$factory.redstoneMinHeight)
                {
                    return false;
                }
                else if (this.redstoneSize != chunkgeneratorsettings$factory.redstoneSize)
                {
                    return false;
                }
                else if (this.riverSize != chunkgeneratorsettings$factory.riverSize)
                {
                    return false;
                }
                else if (this.seaLevel != chunkgeneratorsettings$factory.seaLevel)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.stretchY, this.stretchY) != 0)
                {
                    return false;
                }
                else if (Float.compare(chunkgeneratorsettings$factory.upperLimitScale, this.upperLimitScale) != 0)
                {
                    return false;
                }
                else if (this.useCaves != chunkgeneratorsettings$factory.useCaves)
                {
                    return false;
                }
                else if (this.useDungeons != chunkgeneratorsettings$factory.useDungeons)
                {
                    return false;
                }
                else if (this.useLavaLakes != chunkgeneratorsettings$factory.useLavaLakes)
                {
                    return false;
                }
                else if (this.useLavaOceans != chunkgeneratorsettings$factory.useLavaOceans)
                {
                    return false;
                }
                else if (this.useMineShafts != chunkgeneratorsettings$factory.useMineShafts)
                {
                    return false;
                }
                else if (this.useRavines != chunkgeneratorsettings$factory.useRavines)
                {
                    return false;
                }
                else if (this.useStrongholds != chunkgeneratorsettings$factory.useStrongholds)
                {
                    return false;
                }
                else if (this.useTemples != chunkgeneratorsettings$factory.useTemples)
                {
                    return false;
                }
                else if (this.useMonuments != chunkgeneratorsettings$factory.useMonuments)
                {
                    return false;
                }
                else if (this.useMansions != chunkgeneratorsettings$factory.useMansions)
                {
                    return false;
                }
                else if (this.useVillages != chunkgeneratorsettings$factory.useVillages)
                {
                    return false;
                }
                else if (this.useWaterLakes != chunkgeneratorsettings$factory.useWaterLakes)
                {
                    return false;
                }
                else
                {
                    return this.waterLakeChance == chunkgeneratorsettings$factory.waterLakeChance;
                }
            }
            else
            {
                return false;
            }
        }

        public int hashCode()
        {
            int i = this.coordinateScale == 0.0F ? 0 : Float.floatToIntBits(this.coordinateScale);
            i = 31 * i + (this.heightScale == 0.0F ? 0 : Float.floatToIntBits(this.heightScale));
            i = 31 * i + (this.upperLimitScale == 0.0F ? 0 : Float.floatToIntBits(this.upperLimitScale));
            i = 31 * i + (this.lowerLimitScale == 0.0F ? 0 : Float.floatToIntBits(this.lowerLimitScale));
            i = 31 * i + (this.depthNoiseScaleX == 0.0F ? 0 : Float.floatToIntBits(this.depthNoiseScaleX));
            i = 31 * i + (this.depthNoiseScaleZ == 0.0F ? 0 : Float.floatToIntBits(this.depthNoiseScaleZ));
            i = 31 * i + (this.depthNoiseScaleExponent == 0.0F ? 0 : Float.floatToIntBits(this.depthNoiseScaleExponent));
            i = 31 * i + (this.mainNoiseScaleX == 0.0F ? 0 : Float.floatToIntBits(this.mainNoiseScaleX));
            i = 31 * i + (this.mainNoiseScaleY == 0.0F ? 0 : Float.floatToIntBits(this.mainNoiseScaleY));
            i = 31 * i + (this.mainNoiseScaleZ == 0.0F ? 0 : Float.floatToIntBits(this.mainNoiseScaleZ));
            i = 31 * i + (this.baseSize == 0.0F ? 0 : Float.floatToIntBits(this.baseSize));
            i = 31 * i + (this.stretchY == 0.0F ? 0 : Float.floatToIntBits(this.stretchY));
            i = 31 * i + (this.biomeDepthWeight == 0.0F ? 0 : Float.floatToIntBits(this.biomeDepthWeight));
            i = 31 * i + (this.biomeDepthOffset == 0.0F ? 0 : Float.floatToIntBits(this.biomeDepthOffset));
            i = 31 * i + (this.biomeScaleWeight == 0.0F ? 0 : Float.floatToIntBits(this.biomeScaleWeight));
            i = 31 * i + (this.biomeScaleOffset == 0.0F ? 0 : Float.floatToIntBits(this.biomeScaleOffset));
            i = 31 * i + this.seaLevel;
            i = 31 * i + (this.useCaves ? 1 : 0);
            i = 31 * i + (this.useDungeons ? 1 : 0);
            i = 31 * i + this.dungeonChance;
            i = 31 * i + (this.useStrongholds ? 1 : 0);
            i = 31 * i + (this.useVillages ? 1 : 0);
            i = 31 * i + (this.useMineShafts ? 1 : 0);
            i = 31 * i + (this.useTemples ? 1 : 0);
            i = 31 * i + (this.useMonuments ? 1 : 0);
            i = 31 * i + (this.useMansions ? 1 : 0);
            i = 31 * i + (this.useRavines ? 1 : 0);
            i = 31 * i + (this.useWaterLakes ? 1 : 0);
            i = 31 * i + this.waterLakeChance;
            i = 31 * i + (this.useLavaLakes ? 1 : 0);
            i = 31 * i + this.lavaLakeChance;
            i = 31 * i + (this.useLavaOceans ? 1 : 0);
            i = 31 * i + this.fixedBiome;
            i = 31 * i + this.biomeSize;
            i = 31 * i + this.riverSize;
            i = 31 * i + this.dirtSize;
            i = 31 * i + this.dirtCount;
            i = 31 * i + this.dirtMinHeight;
            i = 31 * i + this.dirtMaxHeight;
            i = 31 * i + this.gravelSize;
            i = 31 * i + this.gravelCount;
            i = 31 * i + this.gravelMinHeight;
            i = 31 * i + this.gravelMaxHeight;
            i = 31 * i + this.graniteSize;
            i = 31 * i + this.graniteCount;
            i = 31 * i + this.graniteMinHeight;
            i = 31 * i + this.graniteMaxHeight;
            i = 31 * i + this.dioriteSize;
            i = 31 * i + this.dioriteCount;
            i = 31 * i + this.dioriteMinHeight;
            i = 31 * i + this.dioriteMaxHeight;
            i = 31 * i + this.andesiteSize;
            i = 31 * i + this.andesiteCount;
            i = 31 * i + this.andesiteMinHeight;
            i = 31 * i + this.andesiteMaxHeight;
            i = 31 * i + this.coalSize;
            i = 31 * i + this.coalCount;
            i = 31 * i + this.coalMinHeight;
            i = 31 * i + this.coalMaxHeight;
            i = 31 * i + this.ironSize;
            i = 31 * i + this.ironCount;
            i = 31 * i + this.ironMinHeight;
            i = 31 * i + this.ironMaxHeight;
            i = 31 * i + this.goldSize;
            i = 31 * i + this.goldCount;
            i = 31 * i + this.goldMinHeight;
            i = 31 * i + this.goldMaxHeight;
            i = 31 * i + this.redstoneSize;
            i = 31 * i + this.redstoneCount;
            i = 31 * i + this.redstoneMinHeight;
            i = 31 * i + this.redstoneMaxHeight;
            i = 31 * i + this.diamondSize;
            i = 31 * i + this.diamondCount;
            i = 31 * i + this.diamondMinHeight;
            i = 31 * i + this.diamondMaxHeight;
            i = 31 * i + this.lapisSize;
            i = 31 * i + this.lapisCount;
            i = 31 * i + this.lapisCenterHeight;
            i = 31 * i + this.lapisSpread;
            return i;
        }

        public ChunkGeneratorSettings build()
        {
            return new ChunkGeneratorSettings(this);
        }
    }

    public static class Serializer implements JsonDeserializer<ChunkGeneratorSettings.Factory>, JsonSerializer<ChunkGeneratorSettings.Factory>
    {
        public ChunkGeneratorSettings.Factory deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            ChunkGeneratorSettings.Factory chunkgeneratorsettings$factory = new ChunkGeneratorSettings.Factory();

            try
            {
                chunkgeneratorsettings$factory.coordinateScale = JsonUtils.getFloat(jsonobject, "coordinateScale", chunkgeneratorsettings$factory.coordinateScale);
                chunkgeneratorsettings$factory.heightScale = JsonUtils.getFloat(jsonobject, "heightScale", chunkgeneratorsettings$factory.heightScale);
                chunkgeneratorsettings$factory.lowerLimitScale = JsonUtils.getFloat(jsonobject, "lowerLimitScale", chunkgeneratorsettings$factory.lowerLimitScale);
                chunkgeneratorsettings$factory.upperLimitScale = JsonUtils.getFloat(jsonobject, "upperLimitScale", chunkgeneratorsettings$factory.upperLimitScale);
                chunkgeneratorsettings$factory.depthNoiseScaleX = JsonUtils.getFloat(jsonobject, "depthNoiseScaleX", chunkgeneratorsettings$factory.depthNoiseScaleX);
                chunkgeneratorsettings$factory.depthNoiseScaleZ = JsonUtils.getFloat(jsonobject, "depthNoiseScaleZ", chunkgeneratorsettings$factory.depthNoiseScaleZ);
                chunkgeneratorsettings$factory.depthNoiseScaleExponent = JsonUtils.getFloat(jsonobject, "depthNoiseScaleExponent", chunkgeneratorsettings$factory.depthNoiseScaleExponent);
                chunkgeneratorsettings$factory.mainNoiseScaleX = JsonUtils.getFloat(jsonobject, "mainNoiseScaleX", chunkgeneratorsettings$factory.mainNoiseScaleX);
                chunkgeneratorsettings$factory.mainNoiseScaleY = JsonUtils.getFloat(jsonobject, "mainNoiseScaleY", chunkgeneratorsettings$factory.mainNoiseScaleY);
                chunkgeneratorsettings$factory.mainNoiseScaleZ = JsonUtils.getFloat(jsonobject, "mainNoiseScaleZ", chunkgeneratorsettings$factory.mainNoiseScaleZ);
                chunkgeneratorsettings$factory.baseSize = JsonUtils.getFloat(jsonobject, "baseSize", chunkgeneratorsettings$factory.baseSize);
                chunkgeneratorsettings$factory.stretchY = JsonUtils.getFloat(jsonobject, "stretchY", chunkgeneratorsettings$factory.stretchY);
                chunkgeneratorsettings$factory.biomeDepthWeight = JsonUtils.getFloat(jsonobject, "biomeDepthWeight", chunkgeneratorsettings$factory.biomeDepthWeight);
                chunkgeneratorsettings$factory.biomeDepthOffset = JsonUtils.getFloat(jsonobject, "biomeDepthOffset", chunkgeneratorsettings$factory.biomeDepthOffset);
                chunkgeneratorsettings$factory.biomeScaleWeight = JsonUtils.getFloat(jsonobject, "biomeScaleWeight", chunkgeneratorsettings$factory.biomeScaleWeight);
                chunkgeneratorsettings$factory.biomeScaleOffset = JsonUtils.getFloat(jsonobject, "biomeScaleOffset", chunkgeneratorsettings$factory.biomeScaleOffset);
                chunkgeneratorsettings$factory.seaLevel = JsonUtils.getInt(jsonobject, "seaLevel", chunkgeneratorsettings$factory.seaLevel);
                chunkgeneratorsettings$factory.useCaves = JsonUtils.getBoolean(jsonobject, "useCaves", chunkgeneratorsettings$factory.useCaves);
                chunkgeneratorsettings$factory.useDungeons = JsonUtils.getBoolean(jsonobject, "useDungeons", chunkgeneratorsettings$factory.useDungeons);
                chunkgeneratorsettings$factory.dungeonChance = JsonUtils.getInt(jsonobject, "dungeonChance", chunkgeneratorsettings$factory.dungeonChance);
                chunkgeneratorsettings$factory.useStrongholds = JsonUtils.getBoolean(jsonobject, "useStrongholds", chunkgeneratorsettings$factory.useStrongholds);
                chunkgeneratorsettings$factory.useVillages = JsonUtils.getBoolean(jsonobject, "useVillages", chunkgeneratorsettings$factory.useVillages);
                chunkgeneratorsettings$factory.useMineShafts = JsonUtils.getBoolean(jsonobject, "useMineShafts", chunkgeneratorsettings$factory.useMineShafts);
                chunkgeneratorsettings$factory.useTemples = JsonUtils.getBoolean(jsonobject, "useTemples", chunkgeneratorsettings$factory.useTemples);
                chunkgeneratorsettings$factory.useMonuments = JsonUtils.getBoolean(jsonobject, "useMonuments", chunkgeneratorsettings$factory.useMonuments);
                chunkgeneratorsettings$factory.useMansions = JsonUtils.getBoolean(jsonobject, "useMansions", chunkgeneratorsettings$factory.useMansions);
                chunkgeneratorsettings$factory.useRavines = JsonUtils.getBoolean(jsonobject, "useRavines", chunkgeneratorsettings$factory.useRavines);
                chunkgeneratorsettings$factory.useWaterLakes = JsonUtils.getBoolean(jsonobject, "useWaterLakes", chunkgeneratorsettings$factory.useWaterLakes);
                chunkgeneratorsettings$factory.waterLakeChance = JsonUtils.getInt(jsonobject, "waterLakeChance", chunkgeneratorsettings$factory.waterLakeChance);
                chunkgeneratorsettings$factory.useLavaLakes = JsonUtils.getBoolean(jsonobject, "useLavaLakes", chunkgeneratorsettings$factory.useLavaLakes);
                chunkgeneratorsettings$factory.lavaLakeChance = JsonUtils.getInt(jsonobject, "lavaLakeChance", chunkgeneratorsettings$factory.lavaLakeChance);
                chunkgeneratorsettings$factory.useLavaOceans = JsonUtils.getBoolean(jsonobject, "useLavaOceans", chunkgeneratorsettings$factory.useLavaOceans);
                chunkgeneratorsettings$factory.fixedBiome = JsonUtils.getInt(jsonobject, "fixedBiome", chunkgeneratorsettings$factory.fixedBiome);

                if (chunkgeneratorsettings$factory.fixedBiome < 38 && chunkgeneratorsettings$factory.fixedBiome >= -1)
                {
                    if (chunkgeneratorsettings$factory.fixedBiome >= Biome.getIdForBiome(Biomes.HELL))
                    {
                        chunkgeneratorsettings$factory.fixedBiome += 2;
                    }
                }
                else
                {
                    chunkgeneratorsettings$factory.fixedBiome = -1;
                }

                chunkgeneratorsettings$factory.biomeSize = JsonUtils.getInt(jsonobject, "biomeSize", chunkgeneratorsettings$factory.biomeSize);
                chunkgeneratorsettings$factory.riverSize = JsonUtils.getInt(jsonobject, "riverSize", chunkgeneratorsettings$factory.riverSize);
                chunkgeneratorsettings$factory.dirtSize = JsonUtils.getInt(jsonobject, "dirtSize", chunkgeneratorsettings$factory.dirtSize);
                chunkgeneratorsettings$factory.dirtCount = JsonUtils.getInt(jsonobject, "dirtCount", chunkgeneratorsettings$factory.dirtCount);
                chunkgeneratorsettings$factory.dirtMinHeight = JsonUtils.getInt(jsonobject, "dirtMinHeight", chunkgeneratorsettings$factory.dirtMinHeight);
                chunkgeneratorsettings$factory.dirtMaxHeight = JsonUtils.getInt(jsonobject, "dirtMaxHeight", chunkgeneratorsettings$factory.dirtMaxHeight);
                chunkgeneratorsettings$factory.gravelSize = JsonUtils.getInt(jsonobject, "gravelSize", chunkgeneratorsettings$factory.gravelSize);
                chunkgeneratorsettings$factory.gravelCount = JsonUtils.getInt(jsonobject, "gravelCount", chunkgeneratorsettings$factory.gravelCount);
                chunkgeneratorsettings$factory.gravelMinHeight = JsonUtils.getInt(jsonobject, "gravelMinHeight", chunkgeneratorsettings$factory.gravelMinHeight);
                chunkgeneratorsettings$factory.gravelMaxHeight = JsonUtils.getInt(jsonobject, "gravelMaxHeight", chunkgeneratorsettings$factory.gravelMaxHeight);
                chunkgeneratorsettings$factory.graniteSize = JsonUtils.getInt(jsonobject, "graniteSize", chunkgeneratorsettings$factory.graniteSize);
                chunkgeneratorsettings$factory.graniteCount = JsonUtils.getInt(jsonobject, "graniteCount", chunkgeneratorsettings$factory.graniteCount);
                chunkgeneratorsettings$factory.graniteMinHeight = JsonUtils.getInt(jsonobject, "graniteMinHeight", chunkgeneratorsettings$factory.graniteMinHeight);
                chunkgeneratorsettings$factory.graniteMaxHeight = JsonUtils.getInt(jsonobject, "graniteMaxHeight", chunkgeneratorsettings$factory.graniteMaxHeight);
                chunkgeneratorsettings$factory.dioriteSize = JsonUtils.getInt(jsonobject, "dioriteSize", chunkgeneratorsettings$factory.dioriteSize);
                chunkgeneratorsettings$factory.dioriteCount = JsonUtils.getInt(jsonobject, "dioriteCount", chunkgeneratorsettings$factory.dioriteCount);
                chunkgeneratorsettings$factory.dioriteMinHeight = JsonUtils.getInt(jsonobject, "dioriteMinHeight", chunkgeneratorsettings$factory.dioriteMinHeight);
                chunkgeneratorsettings$factory.dioriteMaxHeight = JsonUtils.getInt(jsonobject, "dioriteMaxHeight", chunkgeneratorsettings$factory.dioriteMaxHeight);
                chunkgeneratorsettings$factory.andesiteSize = JsonUtils.getInt(jsonobject, "andesiteSize", chunkgeneratorsettings$factory.andesiteSize);
                chunkgeneratorsettings$factory.andesiteCount = JsonUtils.getInt(jsonobject, "andesiteCount", chunkgeneratorsettings$factory.andesiteCount);
                chunkgeneratorsettings$factory.andesiteMinHeight = JsonUtils.getInt(jsonobject, "andesiteMinHeight", chunkgeneratorsettings$factory.andesiteMinHeight);
                chunkgeneratorsettings$factory.andesiteMaxHeight = JsonUtils.getInt(jsonobject, "andesiteMaxHeight", chunkgeneratorsettings$factory.andesiteMaxHeight);
                chunkgeneratorsettings$factory.coalSize = JsonUtils.getInt(jsonobject, "coalSize", chunkgeneratorsettings$factory.coalSize);
                chunkgeneratorsettings$factory.coalCount = JsonUtils.getInt(jsonobject, "coalCount", chunkgeneratorsettings$factory.coalCount);
                chunkgeneratorsettings$factory.coalMinHeight = JsonUtils.getInt(jsonobject, "coalMinHeight", chunkgeneratorsettings$factory.coalMinHeight);
                chunkgeneratorsettings$factory.coalMaxHeight = JsonUtils.getInt(jsonobject, "coalMaxHeight", chunkgeneratorsettings$factory.coalMaxHeight);
                chunkgeneratorsettings$factory.ironSize = JsonUtils.getInt(jsonobject, "ironSize", chunkgeneratorsettings$factory.ironSize);
                chunkgeneratorsettings$factory.ironCount = JsonUtils.getInt(jsonobject, "ironCount", chunkgeneratorsettings$factory.ironCount);
                chunkgeneratorsettings$factory.ironMinHeight = JsonUtils.getInt(jsonobject, "ironMinHeight", chunkgeneratorsettings$factory.ironMinHeight);
                chunkgeneratorsettings$factory.ironMaxHeight = JsonUtils.getInt(jsonobject, "ironMaxHeight", chunkgeneratorsettings$factory.ironMaxHeight);
                chunkgeneratorsettings$factory.goldSize = JsonUtils.getInt(jsonobject, "goldSize", chunkgeneratorsettings$factory.goldSize);
                chunkgeneratorsettings$factory.goldCount = JsonUtils.getInt(jsonobject, "goldCount", chunkgeneratorsettings$factory.goldCount);
                chunkgeneratorsettings$factory.goldMinHeight = JsonUtils.getInt(jsonobject, "goldMinHeight", chunkgeneratorsettings$factory.goldMinHeight);
                chunkgeneratorsettings$factory.goldMaxHeight = JsonUtils.getInt(jsonobject, "goldMaxHeight", chunkgeneratorsettings$factory.goldMaxHeight);
                chunkgeneratorsettings$factory.redstoneSize = JsonUtils.getInt(jsonobject, "redstoneSize", chunkgeneratorsettings$factory.redstoneSize);
                chunkgeneratorsettings$factory.redstoneCount = JsonUtils.getInt(jsonobject, "redstoneCount", chunkgeneratorsettings$factory.redstoneCount);
                chunkgeneratorsettings$factory.redstoneMinHeight = JsonUtils.getInt(jsonobject, "redstoneMinHeight", chunkgeneratorsettings$factory.redstoneMinHeight);
                chunkgeneratorsettings$factory.redstoneMaxHeight = JsonUtils.getInt(jsonobject, "redstoneMaxHeight", chunkgeneratorsettings$factory.redstoneMaxHeight);
                chunkgeneratorsettings$factory.diamondSize = JsonUtils.getInt(jsonobject, "diamondSize", chunkgeneratorsettings$factory.diamondSize);
                chunkgeneratorsettings$factory.diamondCount = JsonUtils.getInt(jsonobject, "diamondCount", chunkgeneratorsettings$factory.diamondCount);
                chunkgeneratorsettings$factory.diamondMinHeight = JsonUtils.getInt(jsonobject, "diamondMinHeight", chunkgeneratorsettings$factory.diamondMinHeight);
                chunkgeneratorsettings$factory.diamondMaxHeight = JsonUtils.getInt(jsonobject, "diamondMaxHeight", chunkgeneratorsettings$factory.diamondMaxHeight);
                chunkgeneratorsettings$factory.lapisSize = JsonUtils.getInt(jsonobject, "lapisSize", chunkgeneratorsettings$factory.lapisSize);
                chunkgeneratorsettings$factory.lapisCount = JsonUtils.getInt(jsonobject, "lapisCount", chunkgeneratorsettings$factory.lapisCount);
                chunkgeneratorsettings$factory.lapisCenterHeight = JsonUtils.getInt(jsonobject, "lapisCenterHeight", chunkgeneratorsettings$factory.lapisCenterHeight);
                chunkgeneratorsettings$factory.lapisSpread = JsonUtils.getInt(jsonobject, "lapisSpread", chunkgeneratorsettings$factory.lapisSpread);
            }
            catch (Exception var7)
            {
                ;
            }

            return chunkgeneratorsettings$factory;
        }

        public JsonElement serialize(ChunkGeneratorSettings.Factory p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("coordinateScale", Float.valueOf(p_serialize_1_.coordinateScale));
            jsonobject.addProperty("heightScale", Float.valueOf(p_serialize_1_.heightScale));
            jsonobject.addProperty("lowerLimitScale", Float.valueOf(p_serialize_1_.lowerLimitScale));
            jsonobject.addProperty("upperLimitScale", Float.valueOf(p_serialize_1_.upperLimitScale));
            jsonobject.addProperty("depthNoiseScaleX", Float.valueOf(p_serialize_1_.depthNoiseScaleX));
            jsonobject.addProperty("depthNoiseScaleZ", Float.valueOf(p_serialize_1_.depthNoiseScaleZ));
            jsonobject.addProperty("depthNoiseScaleExponent", Float.valueOf(p_serialize_1_.depthNoiseScaleExponent));
            jsonobject.addProperty("mainNoiseScaleX", Float.valueOf(p_serialize_1_.mainNoiseScaleX));
            jsonobject.addProperty("mainNoiseScaleY", Float.valueOf(p_serialize_1_.mainNoiseScaleY));
            jsonobject.addProperty("mainNoiseScaleZ", Float.valueOf(p_serialize_1_.mainNoiseScaleZ));
            jsonobject.addProperty("baseSize", Float.valueOf(p_serialize_1_.baseSize));
            jsonobject.addProperty("stretchY", Float.valueOf(p_serialize_1_.stretchY));
            jsonobject.addProperty("biomeDepthWeight", Float.valueOf(p_serialize_1_.biomeDepthWeight));
            jsonobject.addProperty("biomeDepthOffset", Float.valueOf(p_serialize_1_.biomeDepthOffset));
            jsonobject.addProperty("biomeScaleWeight", Float.valueOf(p_serialize_1_.biomeScaleWeight));
            jsonobject.addProperty("biomeScaleOffset", Float.valueOf(p_serialize_1_.biomeScaleOffset));
            jsonobject.addProperty("seaLevel", Integer.valueOf(p_serialize_1_.seaLevel));
            jsonobject.addProperty("useCaves", Boolean.valueOf(p_serialize_1_.useCaves));
            jsonobject.addProperty("useDungeons", Boolean.valueOf(p_serialize_1_.useDungeons));
            jsonobject.addProperty("dungeonChance", Integer.valueOf(p_serialize_1_.dungeonChance));
            jsonobject.addProperty("useStrongholds", Boolean.valueOf(p_serialize_1_.useStrongholds));
            jsonobject.addProperty("useVillages", Boolean.valueOf(p_serialize_1_.useVillages));
            jsonobject.addProperty("useMineShafts", Boolean.valueOf(p_serialize_1_.useMineShafts));
            jsonobject.addProperty("useTemples", Boolean.valueOf(p_serialize_1_.useTemples));
            jsonobject.addProperty("useMonuments", Boolean.valueOf(p_serialize_1_.useMonuments));
            jsonobject.addProperty("useMansions", Boolean.valueOf(p_serialize_1_.useMansions));
            jsonobject.addProperty("useRavines", Boolean.valueOf(p_serialize_1_.useRavines));
            jsonobject.addProperty("useWaterLakes", Boolean.valueOf(p_serialize_1_.useWaterLakes));
            jsonobject.addProperty("waterLakeChance", Integer.valueOf(p_serialize_1_.waterLakeChance));
            jsonobject.addProperty("useLavaLakes", Boolean.valueOf(p_serialize_1_.useLavaLakes));
            jsonobject.addProperty("lavaLakeChance", Integer.valueOf(p_serialize_1_.lavaLakeChance));
            jsonobject.addProperty("useLavaOceans", Boolean.valueOf(p_serialize_1_.useLavaOceans));
            jsonobject.addProperty("fixedBiome", Integer.valueOf(p_serialize_1_.fixedBiome));
            jsonobject.addProperty("biomeSize", Integer.valueOf(p_serialize_1_.biomeSize));
            jsonobject.addProperty("riverSize", Integer.valueOf(p_serialize_1_.riverSize));
            jsonobject.addProperty("dirtSize", Integer.valueOf(p_serialize_1_.dirtSize));
            jsonobject.addProperty("dirtCount", Integer.valueOf(p_serialize_1_.dirtCount));
            jsonobject.addProperty("dirtMinHeight", Integer.valueOf(p_serialize_1_.dirtMinHeight));
            jsonobject.addProperty("dirtMaxHeight", Integer.valueOf(p_serialize_1_.dirtMaxHeight));
            jsonobject.addProperty("gravelSize", Integer.valueOf(p_serialize_1_.gravelSize));
            jsonobject.addProperty("gravelCount", Integer.valueOf(p_serialize_1_.gravelCount));
            jsonobject.addProperty("gravelMinHeight", Integer.valueOf(p_serialize_1_.gravelMinHeight));
            jsonobject.addProperty("gravelMaxHeight", Integer.valueOf(p_serialize_1_.gravelMaxHeight));
            jsonobject.addProperty("graniteSize", Integer.valueOf(p_serialize_1_.graniteSize));
            jsonobject.addProperty("graniteCount", Integer.valueOf(p_serialize_1_.graniteCount));
            jsonobject.addProperty("graniteMinHeight", Integer.valueOf(p_serialize_1_.graniteMinHeight));
            jsonobject.addProperty("graniteMaxHeight", Integer.valueOf(p_serialize_1_.graniteMaxHeight));
            jsonobject.addProperty("dioriteSize", Integer.valueOf(p_serialize_1_.dioriteSize));
            jsonobject.addProperty("dioriteCount", Integer.valueOf(p_serialize_1_.dioriteCount));
            jsonobject.addProperty("dioriteMinHeight", Integer.valueOf(p_serialize_1_.dioriteMinHeight));
            jsonobject.addProperty("dioriteMaxHeight", Integer.valueOf(p_serialize_1_.dioriteMaxHeight));
            jsonobject.addProperty("andesiteSize", Integer.valueOf(p_serialize_1_.andesiteSize));
            jsonobject.addProperty("andesiteCount", Integer.valueOf(p_serialize_1_.andesiteCount));
            jsonobject.addProperty("andesiteMinHeight", Integer.valueOf(p_serialize_1_.andesiteMinHeight));
            jsonobject.addProperty("andesiteMaxHeight", Integer.valueOf(p_serialize_1_.andesiteMaxHeight));
            jsonobject.addProperty("coalSize", Integer.valueOf(p_serialize_1_.coalSize));
            jsonobject.addProperty("coalCount", Integer.valueOf(p_serialize_1_.coalCount));
            jsonobject.addProperty("coalMinHeight", Integer.valueOf(p_serialize_1_.coalMinHeight));
            jsonobject.addProperty("coalMaxHeight", Integer.valueOf(p_serialize_1_.coalMaxHeight));
            jsonobject.addProperty("ironSize", Integer.valueOf(p_serialize_1_.ironSize));
            jsonobject.addProperty("ironCount", Integer.valueOf(p_serialize_1_.ironCount));
            jsonobject.addProperty("ironMinHeight", Integer.valueOf(p_serialize_1_.ironMinHeight));
            jsonobject.addProperty("ironMaxHeight", Integer.valueOf(p_serialize_1_.ironMaxHeight));
            jsonobject.addProperty("goldSize", Integer.valueOf(p_serialize_1_.goldSize));
            jsonobject.addProperty("goldCount", Integer.valueOf(p_serialize_1_.goldCount));
            jsonobject.addProperty("goldMinHeight", Integer.valueOf(p_serialize_1_.goldMinHeight));
            jsonobject.addProperty("goldMaxHeight", Integer.valueOf(p_serialize_1_.goldMaxHeight));
            jsonobject.addProperty("redstoneSize", Integer.valueOf(p_serialize_1_.redstoneSize));
            jsonobject.addProperty("redstoneCount", Integer.valueOf(p_serialize_1_.redstoneCount));
            jsonobject.addProperty("redstoneMinHeight", Integer.valueOf(p_serialize_1_.redstoneMinHeight));
            jsonobject.addProperty("redstoneMaxHeight", Integer.valueOf(p_serialize_1_.redstoneMaxHeight));
            jsonobject.addProperty("diamondSize", Integer.valueOf(p_serialize_1_.diamondSize));
            jsonobject.addProperty("diamondCount", Integer.valueOf(p_serialize_1_.diamondCount));
            jsonobject.addProperty("diamondMinHeight", Integer.valueOf(p_serialize_1_.diamondMinHeight));
            jsonobject.addProperty("diamondMaxHeight", Integer.valueOf(p_serialize_1_.diamondMaxHeight));
            jsonobject.addProperty("lapisSize", Integer.valueOf(p_serialize_1_.lapisSize));
            jsonobject.addProperty("lapisCount", Integer.valueOf(p_serialize_1_.lapisCount));
            jsonobject.addProperty("lapisCenterHeight", Integer.valueOf(p_serialize_1_.lapisCenterHeight));
            jsonobject.addProperty("lapisSpread", Integer.valueOf(p_serialize_1_.lapisSpread));
            return jsonobject;
        }
    }
}
