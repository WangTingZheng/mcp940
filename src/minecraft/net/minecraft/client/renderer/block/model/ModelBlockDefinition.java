package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.multipart.Multipart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.util.JsonUtils;

public class ModelBlockDefinition
{
    @VisibleForTesting
    static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).registerTypeAdapter(VariantList.class, new VariantList.Deserializer()).registerTypeAdapter(Multipart.class, new Multipart.Deserializer()).registerTypeAdapter(Selector.class, new Selector.Deserializer()).create();
    private final Map<String, VariantList> mapVariants = Maps.<String, VariantList>newHashMap();
    private Multipart multipart;

    public static ModelBlockDefinition parseFromReader(Reader reader)
    {
        return (ModelBlockDefinition)JsonUtils.fromJson(GSON, reader, ModelBlockDefinition.class);
    }

    public ModelBlockDefinition(Map<String, VariantList> variants, Multipart multipartIn)
    {
        this.multipart = multipartIn;
        this.mapVariants.putAll(variants);
    }

    public ModelBlockDefinition(List<ModelBlockDefinition> p_i46222_1_)
    {
        ModelBlockDefinition modelblockdefinition = null;

        for (ModelBlockDefinition modelblockdefinition1 : p_i46222_1_)
        {
            if (modelblockdefinition1.hasMultipartData())
            {
                this.mapVariants.clear();
                modelblockdefinition = modelblockdefinition1;
            }

            this.mapVariants.putAll(modelblockdefinition1.mapVariants);
        }

        if (modelblockdefinition != null)
        {
            this.multipart = modelblockdefinition.multipart;
        }
    }

    public boolean hasVariant(String p_188000_1_)
    {
        return this.mapVariants.get(p_188000_1_) != null;
    }

    public VariantList getVariant(String p_188004_1_)
    {
        VariantList variantlist = this.mapVariants.get(p_188004_1_);

        if (variantlist == null)
        {
            throw new ModelBlockDefinition.MissingVariantException();
        }
        else
        {
            return variantlist;
        }
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else
        {
            if (p_equals_1_ instanceof ModelBlockDefinition)
            {
                ModelBlockDefinition modelblockdefinition = (ModelBlockDefinition)p_equals_1_;

                if (this.mapVariants.equals(modelblockdefinition.mapVariants))
                {
                    return this.hasMultipartData() ? this.multipart.equals(modelblockdefinition.multipart) : !modelblockdefinition.hasMultipartData();
                }
            }

            return false;
        }
    }

    public int hashCode()
    {
        return 31 * this.mapVariants.hashCode() + (this.hasMultipartData() ? this.multipart.hashCode() : 0);
    }

    public Set<VariantList> getMultipartVariants()
    {
        Set<VariantList> set = Sets.newHashSet(this.mapVariants.values());

        if (this.hasMultipartData())
        {
            set.addAll(this.multipart.getVariants());
        }

        return set;
    }

    public boolean hasMultipartData()
    {
        return this.multipart != null;
    }

    public Multipart getMultipartData()
    {
        return this.multipart;
    }

    public static class Deserializer implements JsonDeserializer<ModelBlockDefinition>
    {
        public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            Map<String, VariantList> map = this.parseMapVariants(p_deserialize_3_, jsonobject);
            Multipart multipart = this.parseMultipart(p_deserialize_3_, jsonobject);

            if (!map.isEmpty() || multipart != null && !multipart.getVariants().isEmpty())
            {
                return new ModelBlockDefinition(map, multipart);
            }
            else
            {
                throw new JsonParseException("Neither 'variants' nor 'multipart' found");
            }
        }

        protected Map<String, VariantList> parseMapVariants(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            Map<String, VariantList> map = Maps.<String, VariantList>newHashMap();

            if (object.has("variants"))
            {
                JsonObject jsonobject = JsonUtils.getJsonObject(object, "variants");

                for (Entry<String, JsonElement> entry : jsonobject.entrySet())
                {
                    map.put(entry.getKey(), (VariantList)deserializationContext.deserialize(entry.getValue(), VariantList.class));
                }
            }

            return map;
        }

        @Nullable
        protected Multipart parseMultipart(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            if (!object.has("multipart"))
            {
                return null;
            }
            else
            {
                JsonArray jsonarray = JsonUtils.getJsonArray(object, "multipart");
                return (Multipart)deserializationContext.deserialize(jsonarray, Multipart.class);
            }
        }
    }

    public class MissingVariantException extends RuntimeException
    {
    }
}
