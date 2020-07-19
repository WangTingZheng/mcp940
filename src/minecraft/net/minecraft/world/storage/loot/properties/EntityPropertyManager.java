package net.minecraft.world.storage.loot.properties;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class EntityPropertyManager
{
    private static final Map < ResourceLocation, EntityProperty.Serializer<? >> NAME_TO_SERIALIZER_MAP = Maps. < ResourceLocation, EntityProperty.Serializer<? >> newHashMap();
    private static final Map < Class <? extends EntityProperty > , EntityProperty.Serializer<? >> CLASS_TO_SERIALIZER_MAP = Maps. < Class <? extends EntityProperty > , EntityProperty.Serializer<? >> newHashMap();

    public static <T extends EntityProperty> void registerProperty(EntityProperty.Serializer <? extends T > serializer)
    {
        ResourceLocation resourcelocation = serializer.getName();
        Class<T> oclass = (Class<T>)serializer.getPropertyClass();

        if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation))
        {
            throw new IllegalArgumentException("Can't re-register entity property name " + resourcelocation);
        }
        else if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass))
        {
            throw new IllegalArgumentException("Can't re-register entity property class " + oclass.getName());
        }
        else
        {
            NAME_TO_SERIALIZER_MAP.put(resourcelocation, serializer);
            CLASS_TO_SERIALIZER_MAP.put(oclass, serializer);
        }
    }

    public static EntityProperty.Serializer<?> getSerializerForName(ResourceLocation name)
    {
        EntityProperty.Serializer<?> serializer = (EntityProperty.Serializer)NAME_TO_SERIALIZER_MAP.get(name);

        if (serializer == null)
        {
            throw new IllegalArgumentException("Unknown loot entity property '" + name + "'");
        }
        else
        {
            return serializer;
        }
    }

    public static <T extends EntityProperty> EntityProperty.Serializer<T> getSerializerFor(T property)
    {
        EntityProperty.Serializer<?> serializer = (EntityProperty.Serializer)CLASS_TO_SERIALIZER_MAP.get(property.getClass());

        if (serializer == null)
        {
            throw new IllegalArgumentException("Unknown loot entity property " + property);
        }
        else
        {
            return (EntityProperty.Serializer<T>)serializer;
        }
    }

    static
    {
        registerProperty(new EntityOnFire.Serializer());
    }
}
