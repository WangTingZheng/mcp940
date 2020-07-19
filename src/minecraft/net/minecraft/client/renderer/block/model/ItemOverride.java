package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverride
{
    private final ResourceLocation location;
    private final Map<ResourceLocation, Float> mapResourceValues;

    public ItemOverride(ResourceLocation locationIn, Map<ResourceLocation, Float> propertyValues)
    {
        this.location = locationIn;
        this.mapResourceValues = propertyValues;
    }

    /**
     * Get the location of the target model
     */
    public ResourceLocation getLocation()
    {
        return this.location;
    }

    boolean matchesItemStack(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase livingEntity)
    {
        Item item = stack.getItem();

        for (Entry<ResourceLocation, Float> entry : this.mapResourceValues.entrySet())
        {
            IItemPropertyGetter iitempropertygetter = item.getPropertyGetter(entry.getKey());

            if (iitempropertygetter == null || iitempropertygetter.apply(stack, worldIn, livingEntity) < ((Float)entry.getValue()).floatValue())
            {
                return false;
            }
        }

        return true;
    }

    static class Deserializer implements JsonDeserializer<ItemOverride>
    {
        public ItemOverride deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "model"));
            Map<ResourceLocation, Float> map = this.makeMapResourceValues(jsonobject);
            return new ItemOverride(resourcelocation, map);
        }

        protected Map<ResourceLocation, Float> makeMapResourceValues(JsonObject p_188025_1_)
        {
            Map<ResourceLocation, Float> map = Maps.<ResourceLocation, Float>newLinkedHashMap();
            JsonObject jsonobject = JsonUtils.getJsonObject(p_188025_1_, "predicate");

            for (Entry<String, JsonElement> entry : jsonobject.entrySet())
            {
                map.put(new ResourceLocation(entry.getKey()), Float.valueOf(JsonUtils.getFloat(entry.getValue(), entry.getKey())));
            }

            return map;
        }
    }
}
