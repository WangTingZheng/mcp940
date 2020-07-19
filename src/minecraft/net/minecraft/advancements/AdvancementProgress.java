package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;

public class AdvancementProgress implements Comparable<AdvancementProgress>
{
    private final Map<String, CriterionProgress> criteria = Maps.<String, CriterionProgress>newHashMap();
    private String[][] requirements = new String[0][];

    /**
     * Update this AdvancementProgress' criteria and requirements
     */
    public void update(Map<String, Criterion> criteriaIn, String[][] requirements)
    {
        Set<String> set = criteriaIn.keySet();
        Iterator<Entry<String, CriterionProgress>> iterator = this.criteria.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry<String, CriterionProgress> entry = (Entry)iterator.next();

            if (!set.contains(entry.getKey()))
            {
                iterator.remove();
            }
        }

        for (String s : set)
        {
            if (!this.criteria.containsKey(s))
            {
                this.criteria.put(s, new CriterionProgress(this));
            }
        }

        this.requirements = requirements;
    }

    public boolean isDone()
    {
        if (this.requirements.length == 0)
        {
            return false;
        }
        else
        {
            for (String[] astring : this.requirements)
            {
                boolean flag = false;

                for (String s : astring)
                {
                    CriterionProgress criterionprogress = this.getCriterionProgress(s);

                    if (criterionprogress != null && criterionprogress.isObtained())
                    {
                        flag = true;
                        break;
                    }
                }

                if (!flag)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean hasProgress()
    {
        for (CriterionProgress criterionprogress : this.criteria.values())
        {
            if (criterionprogress.isObtained())
            {
                return true;
            }
        }

        return false;
    }

    public boolean grantCriterion(String criterionIn)
    {
        CriterionProgress criterionprogress = this.criteria.get(criterionIn);

        if (criterionprogress != null && !criterionprogress.isObtained())
        {
            criterionprogress.obtain();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean revokeCriterion(String criterionIn)
    {
        CriterionProgress criterionprogress = this.criteria.get(criterionIn);

        if (criterionprogress != null && criterionprogress.isObtained())
        {
            criterionprogress.reset();
            return true;
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        return "AdvancementProgress{criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + '}';
    }

    public void serializeToNetwork(PacketBuffer p_192104_1_)
    {
        p_192104_1_.writeVarInt(this.criteria.size());

        for (Entry<String, CriterionProgress> entry : this.criteria.entrySet())
        {
            p_192104_1_.writeString(entry.getKey());
            ((CriterionProgress)entry.getValue()).write(p_192104_1_);
        }
    }

    public static AdvancementProgress fromNetwork(PacketBuffer p_192100_0_)
    {
        AdvancementProgress advancementprogress = new AdvancementProgress();
        int i = p_192100_0_.readVarInt();

        for (int j = 0; j < i; ++j)
        {
            advancementprogress.criteria.put(p_192100_0_.readString(32767), CriterionProgress.read(p_192100_0_, advancementprogress));
        }

        return advancementprogress;
    }

    @Nullable
    public CriterionProgress getCriterionProgress(String criterionIn)
    {
        return this.criteria.get(criterionIn);
    }

    public float getPercent()
    {
        if (this.criteria.isEmpty())
        {
            return 0.0F;
        }
        else
        {
            float f = (float)this.requirements.length;
            float f1 = (float)this.countCompletedRequirements();
            return f1 / f;
        }
    }

    @Nullable
    public String getProgressText()
    {
        if (this.criteria.isEmpty())
        {
            return null;
        }
        else
        {
            int i = this.requirements.length;

            if (i <= 1)
            {
                return null;
            }
            else
            {
                int j = this.countCompletedRequirements();
                return j + "/" + i;
            }
        }
    }

    private int countCompletedRequirements()
    {
        int i = 0;

        for (String[] astring : this.requirements)
        {
            boolean flag = false;

            for (String s : astring)
            {
                CriterionProgress criterionprogress = this.getCriterionProgress(s);

                if (criterionprogress != null && criterionprogress.isObtained())
                {
                    flag = true;
                    break;
                }
            }

            if (flag)
            {
                ++i;
            }
        }

        return i;
    }

    public Iterable<String> getRemaningCriteria()
    {
        List<String> list = Lists.<String>newArrayList();

        for (Entry<String, CriterionProgress> entry : this.criteria.entrySet())
        {
            if (!((CriterionProgress)entry.getValue()).isObtained())
            {
                list.add(entry.getKey());
            }
        }

        return list;
    }

    public Iterable<String> getCompletedCriteria()
    {
        List<String> list = Lists.<String>newArrayList();

        for (Entry<String, CriterionProgress> entry : this.criteria.entrySet())
        {
            if (((CriterionProgress)entry.getValue()).isObtained())
            {
                list.add(entry.getKey());
            }
        }

        return list;
    }

    @Nullable
    public Date getFirstProgressDate()
    {
        Date date = null;

        for (CriterionProgress criterionprogress : this.criteria.values())
        {
            if (criterionprogress.isObtained() && (date == null || criterionprogress.getObtained().before(date)))
            {
                date = criterionprogress.getObtained();
            }
        }

        return date;
    }

    public int compareTo(AdvancementProgress p_compareTo_1_)
    {
        Date date = this.getFirstProgressDate();
        Date date1 = p_compareTo_1_.getFirstProgressDate();

        if (date == null && date1 != null)
        {
            return 1;
        }
        else if (date != null && date1 == null)
        {
            return -1;
        }
        else
        {
            return date == null && date1 == null ? 0 : date.compareTo(date1);
        }
    }

    public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress>
    {
        public JsonElement serialize(AdvancementProgress p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            JsonObject jsonobject = new JsonObject();
            JsonObject jsonobject1 = new JsonObject();

            for (Entry<String, CriterionProgress> entry : p_serialize_1_.criteria.entrySet())
            {
                CriterionProgress criterionprogress = entry.getValue();

                if (criterionprogress.isObtained())
                {
                    jsonobject1.add(entry.getKey(), criterionprogress.serialize());
                }
            }

            if (!jsonobject1.entrySet().isEmpty())
            {
                jsonobject.add("criteria", jsonobject1);
            }

            jsonobject.addProperty("done", Boolean.valueOf(p_serialize_1_.isDone()));
            return jsonobject;
        }

        public AdvancementProgress deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "advancement");
            JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "criteria", new JsonObject());
            AdvancementProgress advancementprogress = new AdvancementProgress();

            for (Entry<String, JsonElement> entry : jsonobject1.entrySet())
            {
                String s = entry.getKey();
                advancementprogress.criteria.put(s, CriterionProgress.fromDateTime(advancementprogress, JsonUtils.getString(entry.getValue(), s)));
            }

            return advancementprogress;
        }
    }
}
