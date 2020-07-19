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
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class PlacedBlockTrigger implements ICriterionTrigger<PlacedBlockTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("placed_block");
    private final Map<PlayerAdvancements, PlacedBlockTrigger.Listeners> listeners = Maps.<PlayerAdvancements, PlacedBlockTrigger.Listeners>newHashMap();

    public ResourceLocation getId()
    {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener)
    {
        PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (placedblocktrigger$listeners == null)
        {
            placedblocktrigger$listeners = new PlacedBlockTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, placedblocktrigger$listeners);
        }

        placedblocktrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener)
    {
        PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (placedblocktrigger$listeners != null)
        {
            placedblocktrigger$listeners.remove(listener);

            if (placedblocktrigger$listeners.isEmpty())
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
    public PlacedBlockTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
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

        LocationPredicate locationpredicate = LocationPredicate.deserialize(json.get("location"));
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
        return new PlacedBlockTrigger.Instance(block, map, locationpredicate, itempredicate);
    }

    public void trigger(EntityPlayerMP player, BlockPos pos, ItemStack item)
    {
        IBlockState iblockstate = player.world.getBlockState(pos);
        PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (placedblocktrigger$listeners != null)
        {
            placedblocktrigger$listeners.trigger(iblockstate, pos, player.getServerWorld(), item);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final Block block;
        private final Map < IProperty<?>, Object > properties;
        private final LocationPredicate location;
        private final ItemPredicate item;

        public Instance(@Nullable Block block, @Nullable Map < IProperty<?>, Object > propertiesIn, LocationPredicate locationIn, ItemPredicate itemIn)
        {
            super(PlacedBlockTrigger.ID);
            this.block = block;
            this.properties = propertiesIn;
            this.location = locationIn;
            this.item = itemIn;
        }

        public boolean test(IBlockState state, BlockPos pos, WorldServer world, ItemStack item)
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

                if (!this.location.test(world, (float)pos.getX(), (float)pos.getY(), (float)pos.getZ()))
                {
                    return false;
                }
                else
                {
                    return this.item.test(item);
                }
            }
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(IBlockState state, BlockPos pos, WorldServer world, ItemStack item)
        {
            List<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener : this.listeners)
            {
                if (((PlacedBlockTrigger.Instance)listener.getCriterionInstance()).test(state, pos, world, item))
                {
                    if (list == null)
                    {
                        list = Lists.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
