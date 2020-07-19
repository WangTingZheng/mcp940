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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TameAnimalTrigger implements ICriterionTrigger<TameAnimalTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("tame_animal");
    private final Map<PlayerAdvancements, TameAnimalTrigger.Listeners> listeners = Maps.<PlayerAdvancements, TameAnimalTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener)
    {
        TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (tameanimaltrigger$listeners == null)
        {
            tameanimaltrigger$listeners = new TameAnimalTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, tameanimaltrigger$listeners);
        }

        tameanimaltrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener)
    {
        TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (tameanimaltrigger$listeners != null)
        {
            tameanimaltrigger$listeners.remove(listener);

            if (tameanimaltrigger$listeners.isEmpty())
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
    public TameAnimalTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("entity"));
        return new TameAnimalTrigger.Instance(entitypredicate);
    }

    public void trigger(EntityPlayerMP player, EntityAnimal entity)
    {
        TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.listeners.get(player.getAdvancements());

        if (tameanimaltrigger$listeners != null)
        {
            tameanimaltrigger$listeners.trigger(player, entity);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final EntityPredicate entity;

        public Instance(EntityPredicate entity)
        {
            super(TameAnimalTrigger.ID);
            this.entity = entity;
        }

        public boolean test(EntityPlayerMP player, EntityAnimal entity)
        {
            return this.entity.test(player, entity);
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player, EntityAnimal entity)
        {
            List<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener : this.listeners)
            {
                if (((TameAnimalTrigger.Instance)listener.getCriterionInstance()).test(player, entity))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
