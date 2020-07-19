package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.resources.Language;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer<LanguageMetadataSection>
{
    public LanguageMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
    {
        JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        Set<Language> set = Sets.<Language>newHashSet();

        for (Entry<String, JsonElement> entry : jsonobject.entrySet())
        {
            String s = entry.getKey();

            if (s.length() > 16)
            {
                throw new JsonParseException("Invalid language->'" + s + "': language code must not be more than " + 16 + " characters long");
            }

            JsonObject jsonobject1 = JsonUtils.getJsonObject(entry.getValue(), "language");
            String s1 = JsonUtils.getString(jsonobject1, "region");
            String s2 = JsonUtils.getString(jsonobject1, "name");
            boolean flag = JsonUtils.getBoolean(jsonobject1, "bidirectional", false);

            if (s1.isEmpty())
            {
                throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
            }

            if (s2.isEmpty())
            {
                throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
            }

            if (!set.add(new Language(s, s1, s2, flag)))
            {
                throw new JsonParseException("Duplicate language->'" + s + "' defined");
            }
        }

        return new LanguageMetadataSection(set);
    }

    /**
     * The name of this section type as it appears in JSON.
     */
    public String getSectionName()
    {
        return "language";
    }
}
