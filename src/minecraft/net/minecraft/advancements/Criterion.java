package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion
{
    private final ICriterionInstance criterionInstance;

    public Criterion(ICriterionInstance p_i47470_1_)
    {
        this.criterionInstance = p_i47470_1_;
    }

    public Criterion()
    {
        this.criterionInstance = null;
    }

    public void serializeToNetwork(PacketBuffer p_192140_1_)
    {
    }

    public static Criterion criterionFromJson(JsonObject p_192145_0_, JsonDeserializationContext p_192145_1_)
    {
        ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192145_0_, "trigger"));
        ICriterionTrigger<?> icriteriontrigger = CriteriaTriggers.get(resourcelocation);

        if (icriteriontrigger == null)
        {
            throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
        }
        else
        {
            ICriterionInstance icriterioninstance = icriteriontrigger.deserializeInstance(JsonUtils.getJsonObject(p_192145_0_, "conditions", new JsonObject()), p_192145_1_);
            return new Criterion(icriterioninstance);
        }
    }

    public static Criterion criterionFromNetwork(PacketBuffer p_192146_0_)
    {
        return new Criterion();
    }

    public static Map<String, Criterion> criteriaFromJson(JsonObject p_192144_0_, JsonDeserializationContext p_192144_1_)
    {
        Map<String, Criterion> map = Maps.<String, Criterion>newHashMap();

        for (Entry<String, JsonElement> entry : p_192144_0_.entrySet())
        {
            map.put(entry.getKey(), criterionFromJson(JsonUtils.getJsonObject(entry.getValue(), "criterion"), p_192144_1_));
        }

        return map;
    }

    public static Map<String, Criterion> criteriaFromNetwork(PacketBuffer p_192142_0_)
    {
        Map<String, Criterion> map = Maps.<String, Criterion>newHashMap();
        int i = p_192142_0_.readVarInt();

        for (int j = 0; j < i; ++j)
        {
            map.put(p_192142_0_.readString(32767), criterionFromNetwork(p_192142_0_));
        }

        return map;
    }

    public static void serializeToNetwork(Map<String, Criterion> p_192141_0_, PacketBuffer p_192141_1_)
    {
        p_192141_1_.writeVarInt(p_192141_0_.size());

        for (Entry<String, Criterion> entry : p_192141_0_.entrySet())
        {
            p_192141_1_.writeString(entry.getKey());
            ((Criterion)entry.getValue()).serializeToNetwork(p_192141_1_);
        }
    }

    @Nullable
    public ICriterionInstance getCriterionInstance()
    {
        return this.criterionInstance;
    }
}
