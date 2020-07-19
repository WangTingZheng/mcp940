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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ConsumeItemTrigger implements ICriterionTrigger<ConsumeItemTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("consume_item");
    private final Map<PlayerAdvancements, ConsumeItemTrigger.Listeners> listeners = Maps.<PlayerAdvancements, ConsumeItemTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener)
    {
        ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners == null)
        {
            consumeitemtrigger$listeners = new ConsumeItemTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
        }

        consumeitemtrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener)
    {
        ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners != null)
        {
            consumeitemtrigger$listeners.remove(listener);

            if (consumeitemtrigger$listeners.isEmpty())
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
    public ConsumeItemTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
        return new ConsumeItemTrigger.Instance(itempredicate);
    }

    public void trigger(EntityPlayerMP player, ItemStack item)
    {
        ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(player.getAdvancements());

        if (consumeitemtrigger$listeners != null)
        {
            consumeitemtrigger$listeners.trigger(item);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final ItemPredicate item;

        public Instance(ItemPredicate item)
        {
            super(ConsumeItemTrigger.ID);
            this.item = item;
        }

        public boolean test(ItemStack item)
        {
            return this.item.test(item);
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(ItemStack item)
        {
            List<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener : this.listeners)
            {
                if (((ConsumeItemTrigger.Instance)listener.getCriterionInstance()).test(item))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
