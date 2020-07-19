package net.minecraft.advancements.critereon;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.util.ResourceLocation;

public class ImpossibleTrigger implements ICriterionTrigger<ImpossibleTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("impossible");

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ImpossibleTrigger.Instance> listener)
    {
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ImpossibleTrigger.Instance> listener)
    {
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn)
    {
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     */
    public ImpossibleTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new ImpossibleTrigger.Instance();
    }

    public static class Instance extends AbstractCriterionInstance
    {
        public Instance()
        {
            super(ImpossibleTrigger.ID);
        }
    }
}
