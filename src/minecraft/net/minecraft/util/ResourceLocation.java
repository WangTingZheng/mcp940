package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Locale;
import org.apache.commons.lang3.Validate;

public class ResourceLocation implements Comparable<ResourceLocation>
{
    protected final String resourceDomain;
    protected final String resourcePath;

    protected ResourceLocation(int unused, String... resourceName)
    {
        this.resourceDomain = org.apache.commons.lang3.StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase(Locale.ROOT);
        this.resourcePath = resourceName[1].toLowerCase(Locale.ROOT);
        Validate.notNull(this.resourcePath);
    }

    public ResourceLocation(String resourceName)
    {
        this(0, splitObjectName(resourceName));
    }

    public ResourceLocation(String resourceDomainIn, String resourcePathIn)
    {
        this(0, resourceDomainIn, resourcePathIn);
    }

    /**
     * Splits an object name (such as minecraft:apple) into the domain and path parts and returns these as an array of
     * length 2. If no colon is present in the passed value the returned array will contain {null, toSplit}.
     */
    protected static String[] splitObjectName(String toSplit)
    {
        String[] astring = new String[] {"minecraft", toSplit};
        int i = toSplit.indexOf(58);

        if (i >= 0)
        {
            astring[1] = toSplit.substring(i + 1, toSplit.length());

            if (i > 1)
            {
                astring[0] = toSplit.substring(0, i);
            }
        }

        return astring;
    }

    public String getResourcePath()
    {
        return this.resourcePath;
    }

    public String getResourceDomain()
    {
        return this.resourceDomain;
    }

    public String toString()
    {
        return this.resourceDomain + ':' + this.resourcePath;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ResourceLocation))
        {
            return false;
        }
        else
        {
            ResourceLocation resourcelocation = (ResourceLocation)p_equals_1_;
            return this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath);
        }
    }

    public int hashCode()
    {
        return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }

    public int compareTo(ResourceLocation p_compareTo_1_)
    {
        int i = this.resourceDomain.compareTo(p_compareTo_1_.resourceDomain);

        if (i == 0)
        {
            i = this.resourcePath.compareTo(p_compareTo_1_.resourcePath);
        }

        return i;
    }

    public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation>
    {
        public ResourceLocation deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            return new ResourceLocation(JsonUtils.getString(p_deserialize_1_, "location"));
        }

        public JsonElement serialize(ResourceLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            return new JsonPrimitive(p_serialize_1_.toString());
        }
    }
}
