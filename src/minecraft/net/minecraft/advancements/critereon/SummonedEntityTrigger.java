package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class SummonedEntityTrigger implements ICriterionTrigger<SummonedEntityTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("summoned_entity");
    private final Map<PlayerAdvancements, SummonedEntityTrigger.Listeners> listeners = Maps.<PlayerAdvancements, SummonedEntityTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener)
    {
        SummonedEntityTrigger.Listeners summonedentitytrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (summonedentitytrigger$listeners == null)
        {
            summonedentitytrigger$listeners = new SummonedEntityTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, summonedentitytrigger$listeners);
        }

        summonedentitytrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener)
    {
        SummonedEntityTrigger.Listeners summonedentitytrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (summonedentitytrigger$listeners != null)
        {
            summonedentitytrigger$listeners.remove(listener);

            if (summonedentitytrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn)
    {
        this.listeners.remove(playerAdvancementsIn);
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     */
    public SummonedEntityTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("entity"));
        return new SummonedEntityTrigger.Instance(entitypredicate);
    }

    public void trigger(EntityPlayerMP player, Entity entity)
    {
        SummonedEntityTrigger.Listeners summonedentitytrigger$listeners = this.listeners.get(player.getAdvancements());

        if (summonedentitytrigger$listeners != null)
        {
            summonedentitytrigger$listeners.trigger(player, entity);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final EntityPredicate entity;

        public Instance(EntityPredicate entity)
        {
            super(SummonedEntityTrigger.ID);
            this.entity = entity;
        }

        public boolean test(EntityPlayerMP player, Entity entity)
        {
            return this.entity.test(player, entity);
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player, Entity entity)
        {
            List<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener : this.listeners)
            {
                if (((SummonedEntityTrigger.Instance)listener.getCriterionInstance()).test(player, entity))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
