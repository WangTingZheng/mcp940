package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class InventoryChangeTrigger implements ICriterionTrigger<InventoryChangeTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("inventory_changed");
    private final Map<PlayerAdvancements, InventoryChangeTrigger.Listeners> listeners = Maps.<PlayerAdvancements, InventoryChangeTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener)
    {
        InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (inventorychangetrigger$listeners == null)
        {
            inventorychangetrigger$listeners = new InventoryChangeTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, inventorychangetrigger$listeners);
        }

        inventorychangetrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener)
    {
        InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (inventorychangetrigger$listeners != null)
        {
            inventorychangetrigger$listeners.remove(listener);

            if (inventorychangetrigger$listeners.isEmpty())
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
    public InventoryChangeTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        JsonObject jsonobject = JsonUtils.getJsonObject(json, "slots", new JsonObject());
        MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(jsonobject.get("occupied"));
        MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(jsonobject.get("full"));
        MinMaxBounds minmaxbounds2 = MinMaxBounds.deserialize(jsonobject.get("empty"));
        ItemPredicate[] aitempredicate = ItemPredicate.deserializeArray(json.get("items"));
        return new InventoryChangeTrigger.Instance(minmaxbounds, minmaxbounds1, minmaxbounds2, aitempredicate);
    }

    public void trigger(EntityPlayerMP player, InventoryPlayer inventory)
    {
        InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.listeners.get(player.getAdvancements());

        if (inventorychangetrigger$listeners != null)
        {
            inventorychangetrigger$listeners.trigger(inventory);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final MinMaxBounds occupied;
        private final MinMaxBounds full;
        private final MinMaxBounds empty;
        private final ItemPredicate[] items;

        public Instance(MinMaxBounds occupied, MinMaxBounds full, MinMaxBounds empty, ItemPredicate[] items)
        {
            super(InventoryChangeTrigger.ID);
            this.occupied = occupied;
            this.full = full;
            this.empty = empty;
            this.items = items;
        }

        public boolean test(InventoryPlayer inventory)
        {
            int i = 0;
            int j = 0;
            int k = 0;
            List<ItemPredicate> list = Lists.newArrayList(this.items);

            for (int l = 0; l < inventory.getSizeInventory(); ++l)
            {
                ItemStack itemstack = inventory.getStackInSlot(l);

                if (itemstack.isEmpty())
                {
                    ++j;
                }
                else
                {
                    ++k;

                    if (itemstack.getCount() >= itemstack.getMaxStackSize())
                    {
                        ++i;
                    }

                    Iterator<ItemPredicate> iterator = list.iterator();

                    while (iterator.hasNext())
                    {
                        ItemPredicate itempredicate = iterator.next();

                        if (itempredicate.test(itemstack))
                        {
                            iterator.remove();
                        }
                    }
                }
            }

            if (!this.full.test((float)i))
            {
                return false;
            }
            else if (!this.empty.test((float)j))
            {
                return false;
            }
            else if (!this.occupied.test((float)k))
            {
                return false;
            }
            else if (!list.isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(InventoryPlayer inventory)
        {
            List<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener : this.listeners)
            {
                if (((InventoryChangeTrigger.Instance)listener.getCriterionInstance()).test(inventory))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
