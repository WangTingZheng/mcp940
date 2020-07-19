package net.minecraft.advancements.critereon;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EnterBlockTrigger implements ICriterionTrigger<EnterBlockTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("enter_block");
    private final Map<PlayerAdvancements, EnterBlockTrigger.Listeners> listeners = Maps.<PlayerAdvancements, EnterBlockTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener)
    {
        EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (enterblocktrigger$listeners == null)
        {
            enterblocktrigger$listeners = new EnterBlockTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, enterblocktrigger$listeners);
        }

        enterblocktrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener)
    {
        EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (enterblocktrigger$listeners != null)
        {
            enterblocktrigger$listeners.remove(listener);

            if (enterblocktrigger$listeners.isEmpty())
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
    public EnterBlockTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        Block block = null;

        if (json.has("block"))
        {
            ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(json, "block"));

            if (!Block.REGISTRY.containsKey(resourcelocation))
            {
                throw new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
            }

            block = Block.REGISTRY.getObject(resourcelocation);
        }

        Map < IProperty<?>, Object > map = null;

        if (json.has("state"))
        {
            if (block == null)
            {
                throw new JsonSyntaxException("Can't define block state without a specific block type");
            }

            BlockStateContainer blockstatecontainer = block.getBlockState();

            for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "state").entrySet())
            {
                IProperty<?> iproperty = blockstatecontainer.getProperty(entry.getKey());

                if (iproperty == null)
                {
                    throw new JsonSyntaxException("Unknown block state property '" + (String)entry.getKey() + "' for block '" + Block.REGISTRY.getNameForObject(block) + "'");
                }

                String s = JsonUtils.getString(entry.getValue(), entry.getKey());
                Optional<?> optional = iproperty.parseValue(s);

                if (!optional.isPresent())
                {
                    throw new JsonSyntaxException("Invalid block state value '" + s + "' for property '" + (String)entry.getKey() + "' on block '" + Block.REGISTRY.getNameForObject(block) + "'");
                }

                if (map == null)
                {
                    map = Maps. < IProperty<?>, Object > newHashMap();
                }

                map.put(iproperty, optional.get());
            }
        }

        return new EnterBlockTrigger.Instance(block, map);
    }

    public void trigger(EntityPlayerMP player, IBlockState state)
    {
        EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null)
        {
            enterblocktrigger$listeners.trigger(state);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final Block block;
        private final Map < IProperty<?>, Object > properties;

        public Instance(@Nullable Block blockIn, @Nullable Map < IProperty<?>, Object > propertiesIn)
        {
            super(EnterBlockTrigger.ID);
            this.block = blockIn;
            this.properties = propertiesIn;
        }

        public boolean test(IBlockState state)
        {
            if (this.block != null && state.getBlock() != this.block)
            {
                return false;
            }
            else
            {
                if (this.properties != null)
                {
                    for (Entry < IProperty<?>, Object > entry : this.properties.entrySet())
                    {
                        if (state.getValue(entry.getKey()) != entry.getValue())
                        {
                            return false;
                        }
                    }
                }

                return true;
            }
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(IBlockState state)
        {
            List<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener : this.listeners)
            {
                if (((EnterBlockTrigger.Instance)listener.getCriterionInstance()).test(state))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
