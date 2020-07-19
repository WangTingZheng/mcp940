package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Map;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootFunctionManager
{
    private static final Map < ResourceLocation, LootFunction.Serializer<? >> NAME_TO_SERIALIZER_MAP = Maps. < ResourceLocation, LootFunction.Serializer<? >> newHashMap();
    private static final Map < Class <? extends LootFunction > , LootFunction.Serializer<? >> CLASS_TO_SERIALIZER_MAP = Maps. < Class <? extends LootFunction > , LootFunction.Serializer<? >> newHashMap();

    public static <T extends LootFunction> void registerFunction(LootFunction.Serializer <? extends T > serializer)
    {
        ResourceLocation resourcelocation = serializer.getFunctionName();
        Class<T> oclass = (Class<T>)serializer.getFunctionClass();

        if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation))
        {
            throw new IllegalArgumentException("Can't re-register item function name " + resourcelocation);
        }
        else if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass))
        {
            throw new IllegalArgumentException("Can't re-register item function class " + oclass.getName());
        }
        else
        {
            NAME_TO_SERIALIZER_MAP.put(resourcelocation, serializer);
            CLASS_TO_SERIALIZER_MAP.put(oclass, serializer);
        }
    }

    public static LootFunction.Serializer<?> getSerializerForName(ResourceLocation location)
    {
        LootFunction.Serializer<?> serializer = (LootFunction.Serializer)NAME_TO_SERIALIZER_MAP.get(location);

        if (serializer == null)
        {
            throw new IllegalArgumentException("Unknown loot item function '" + location + "'");
        }
        else
        {
            return serializer;
        }
    }

    public static <T extends LootFunction> LootFunction.Serializer<T> getSerializerFor(T functionClass)
    {
        LootFunction.Serializer<T> serializer = (LootFunction.Serializer)CLASS_TO_SERIALIZER_MAP.get(functionClass.getClass());

        if (serializer == null)
        {
            throw new IllegalArgumentException("Unknown loot item function " + functionClass);
        }
        else
        {
            return serializer;
        }
    }

    static
    {
        registerFunction(new SetCount.Serializer());
        registerFunction(new SetMetadata.Serializer());
        registerFunction(new EnchantWithLevels.Serializer());
        registerFunction(new EnchantRandomly.Serializer());
        registerFunction(new SetNBT.Serializer());
        registerFunction(new Smelt.Serializer());
        registerFunction(new LootingEnchantBonus.Serializer());
        registerFunction(new SetDamage.Serializer());
        registerFunction(new SetAttributes.Serializer());
    }

    public static class Serializer implements JsonDeserializer<LootFunction>, JsonSerializer<LootFunction>
    {
        public LootFunction deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "function");
            ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "function"));
            LootFunction.Serializer<?> serializer;

            try
            {
                serializer = LootFunctionManager.getSerializerForName(resourcelocation);
            }
            catch (IllegalArgumentException var8)
            {
                throw new JsonSyntaxException("Unknown function '" + resourcelocation + "'");
            }

            return serializer.deserialize(jsonobject, p_deserialize_3_, (LootCondition[])JsonUtils.deserializeClass(jsonobject, "conditions", new LootCondition[0], p_deserialize_3_, LootCondition[].class));
        }

        public JsonElement serialize(LootFunction p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            LootFunction.Serializer<LootFunction> serializer = LootFunctionManager.<LootFunction>getSerializerFor(p_serialize_1_);
            JsonObject jsonobject = new JsonObject();
            serializer.serialize(jsonobject, p_serialize_1_, p_serialize_3_);
            jsonobject.addProperty("function", serializer.getFunctionName().toString());

            if (p_serialize_1_.getConditions() != null && p_serialize_1_.getConditions().length > 0)
            {
                jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.getConditions()));
            }

            return jsonobject;
        }
    }
}
