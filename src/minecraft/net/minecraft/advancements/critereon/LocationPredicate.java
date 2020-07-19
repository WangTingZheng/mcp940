package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

public class LocationPredicate
{
    public static LocationPredicate ANY = new LocationPredicate(MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED, (Biome)null, (String)null, (DimensionType)null);
    private final MinMaxBounds x;
    private final MinMaxBounds y;
    private final MinMaxBounds z;
    @Nullable
    final Biome biome;
    @Nullable
    private final String feature;
    @Nullable
    private final DimensionType dimension;

    public LocationPredicate(MinMaxBounds x, MinMaxBounds y, MinMaxBounds z, @Nullable Biome biome, @Nullable String feature, @Nullable DimensionType dimension)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.biome = biome;
        this.feature = feature;
        this.dimension = dimension;
    }

    public boolean test(WorldServer world, double x, double y, double z)
    {
        return this.test(world, (float)x, (float)y, (float)z);
    }

    public boolean test(WorldServer world, float x, float y, float z)
    {
        if (!this.x.test(x))
        {
            return false;
        }
        else if (!this.y.test(y))
        {
            return false;
        }
        else if (!this.z.test(z))
        {
            return false;
        }
        else if (this.dimension != null && this.dimension != world.provider.getDimensionType())
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos((double)x, (double)y, (double)z);

            if (this.biome != null && this.biome != world.getBiome(blockpos))
            {
                return false;
            }
            else
            {
                return this.feature == null || world.getChunkProvider().isInsideStructure(world, this.feature, blockpos);
            }
        }
    }

    public static LocationPredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "location");
            JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "position", new JsonObject());
            MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(jsonobject1.get("x"));
            MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(jsonobject1.get("y"));
            MinMaxBounds minmaxbounds2 = MinMaxBounds.deserialize(jsonobject1.get("z"));
            DimensionType dimensiontype = jsonobject.has("dimension") ? DimensionType.byName(JsonUtils.getString(jsonobject, "dimension")) : null;
            String s = jsonobject.has("feature") ? JsonUtils.getString(jsonobject, "feature") : null;
            Biome biome = null;

            if (jsonobject.has("biome"))
            {
                ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "biome"));
                biome = Biome.REGISTRY.getObject(resourcelocation);

                if (biome == null)
                {
                    throw new JsonSyntaxException("Unknown biome '" + resourcelocation + "'");
                }
            }

            return new LocationPredicate(minmaxbounds, minmaxbounds1, minmaxbounds2, biome, s, dimensiontype);
        }
        else
        {
            return ANY;
        }
    }
}
