package net.minecraft.world.storage.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface EntityProperty
{
    boolean testProperty(Random random, Entity entityIn);

    public abstract static class Serializer<T extends EntityProperty>
    {
        private final ResourceLocation name;
        private final Class<T> propertyClass;

        protected Serializer(ResourceLocation nameIn, Class<T> propertyClassIn)
        {
            this.name = nameIn;
            this.propertyClass = propertyClassIn;
        }

        public ResourceLocation getName()
        {
            return this.name;
        }

        public Class<T> getPropertyClass()
        {
            return this.propertyClass;
        }

        public abstract JsonElement serialize(T property, JsonSerializationContext serializationContext);

        public abstract T deserialize(JsonElement element, JsonDeserializationContext deserializationContext);
    }
}
