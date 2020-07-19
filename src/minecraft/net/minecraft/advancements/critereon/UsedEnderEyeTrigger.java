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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class UsedEnderEyeTrigger implements ICriterionTrigger<UsedEnderEyeTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("used_ender_eye");
    private final Map<PlayerAdvancements, UsedEnderEyeTrigger.Listeners> listeners = Maps.<PlayerAdvancements, UsedEnderEyeTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener)
    {
        UsedEnderEyeTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (usedendereyetrigger$listeners == null)
        {
            usedendereyetrigger$listeners = new UsedEnderEyeTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, usedendereyetrigger$listeners);
        }

        usedendereyetrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener)
    {
        UsedEnderEyeTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (usedendereyetrigger$listeners != null)
        {
            usedendereyetrigger$listeners.remove(listener);

            if (usedendereyetrigger$listeners.isEmpty())
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
    public UsedEnderEyeTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(json.get("distance"));
        return new UsedEnderEyeTrigger.Instance(minmaxbounds);
    }

    public void trigger(EntityPlayerMP player, BlockPos pos)
    {
        UsedEnderEyeTrigger.Listeners usedendereyetrigger$listeners = this.listeners.get(player.getAdvancements());

        if (usedendereyetrigger$listeners != null)
        {
            double d0 = player.posX - (double)pos.getX();
            double d1 = player.posZ - (double)pos.getZ();
            usedendereyetrigger$listeners.trigger(d0 * d0 + d1 * d1);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final MinMaxBounds distance;

        public Instance(MinMaxBounds distance)
        {
            super(UsedEnderEyeTrigger.ID);
            this.distance = distance;
        }

        public boolean test(double distanceSq)
        {
            return this.distance.testSquare(distanceSq);
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(double distanceSq)
        {
            List<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener : this.listeners)
            {
                if (((UsedEnderEyeTrigger.Instance)listener.getCriterionInstance()).test(distanceSq))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
