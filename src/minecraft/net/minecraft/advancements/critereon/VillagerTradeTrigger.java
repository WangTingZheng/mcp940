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
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class VillagerTradeTrigger implements ICriterionTrigger<VillagerTradeTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("villager_trade");
    private final Map<PlayerAdvancements, VillagerTradeTrigger.Listeners> listeners = Maps.<PlayerAdvancements, VillagerTradeTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener)
    {
        VillagerTradeTrigger.Listeners villagertradetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (villagertradetrigger$listeners == null)
        {
            villagertradetrigger$listeners = new VillagerTradeTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, villagertradetrigger$listeners);
        }

        villagertradetrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener)
    {
        VillagerTradeTrigger.Listeners villagertradetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (villagertradetrigger$listeners != null)
        {
            villagertradetrigger$listeners.remove(listener);

            if (villagertradetrigger$listeners.isEmpty())
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
    public VillagerTradeTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("villager"));
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
        return new VillagerTradeTrigger.Instance(entitypredicate, itempredicate);
    }

    public void trigger(EntityPlayerMP player, EntityVillager villager, ItemStack item)
    {
        VillagerTradeTrigger.Listeners villagertradetrigger$listeners = this.listeners.get(player.getAdvancements());

        if (villagertradetrigger$listeners != null)
        {
            villagertradetrigger$listeners.trigger(player, villager, item);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final EntityPredicate villager;
        private final ItemPredicate item;

        public Instance(EntityPredicate villager, ItemPredicate item)
        {
            super(VillagerTradeTrigger.ID);
            this.villager = villager;
            this.item = item;
        }

        public boolean test(EntityPlayerMP player, EntityVillager villager, ItemStack item)
        {
            if (!this.villager.test(player, villager))
            {
                return false;
            }
            else
            {
                return this.item.test(item);
            }
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player, EntityVillager villager, ItemStack item)
        {
            List<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener : this.listeners)
            {
                if (((VillagerTradeTrigger.Instance)listener.getCriterionInstance()).test(player, villager, item))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
