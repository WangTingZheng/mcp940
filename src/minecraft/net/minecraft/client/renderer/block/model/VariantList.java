package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;

public class VariantList
{
    private final List<Variant> variantList;

    public VariantList(List<Variant> variantListIn)
    {
        this.variantList = variantListIn;
    }

    public List<Variant> getVariantList()
    {
        return this.variantList;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ instanceof VariantList)
        {
            VariantList variantlist = (VariantList)p_equals_1_;
            return this.variantList.equals(variantlist.variantList);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.variantList.hashCode();
    }

    public static class Deserializer implements JsonDeserializer<VariantList>
    {
        public VariantList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            List<Variant> list = Lists.<Variant>newArrayList();

            if (p_deserialize_1_.isJsonArray())
            {
                JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();

                if (jsonarray.size() == 0)
                {
                    throw new JsonParseException("Empty variant array");
                }

                for (JsonElement jsonelement : jsonarray)
                {
                    list.add((Variant)p_deserialize_3_.deserialize(jsonelement, Variant.class));
                }
            }
            else
            {
                list.add((Variant)p_deserialize_3_.deserialize(p_deserialize_1_, Variant.class));
            }

            return new VariantList(list);
        }
    }
}
