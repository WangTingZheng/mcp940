package net.minecraft.world.storage.loot.properties;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class EntityPropertyManager {
   private static final Map<ResourceLocation, EntityProperty.Serializer<?>> field_186647_a = Maps.<ResourceLocation, EntityProperty.Serializer<?>>newHashMap();
   private static final Map<Class<? extends EntityProperty>, EntityProperty.Serializer<?>> field_186648_b = Maps.<Class<? extends EntityProperty>, EntityProperty.Serializer<?>>newHashMap();

   public static <T extends EntityProperty> void func_186644_a(EntityProperty.Serializer<? extends T> p_186644_0_) {
      ResourceLocation resourcelocation = p_186644_0_.func_186649_a();
      Class<T> oclass = p_186644_0_.func_186651_b();
      if (field_186647_a.containsKey(resourcelocation)) {
         throw new IllegalArgumentException("Can't re-register entity property name " + resourcelocation);
      } else if (field_186648_b.containsKey(oclass)) {
         throw new IllegalArgumentException("Can't re-register entity property class " + oclass.getName());
      } else {
         field_186647_a.put(resourcelocation, p_186644_0_);
         field_186648_b.put(oclass, p_186644_0_);
      }
   }

   public static EntityProperty.Serializer<?> func_186646_a(ResourceLocation p_186646_0_) {
      EntityProperty.Serializer<?> serializer = (EntityProperty.Serializer)field_186647_a.get(p_186646_0_);
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot entity property '" + p_186646_0_ + "'");
      } else {
         return serializer;
      }
   }

   public static <T extends EntityProperty> EntityProperty.Serializer<T> func_186645_a(T p_186645_0_) {
      EntityProperty.Serializer<?> serializer = (EntityProperty.Serializer)field_186648_b.get(p_186645_0_.getClass());
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot entity property " + p_186645_0_);
      } else {
         return serializer;
      }
   }

   static {
      func_186644_a(new EntityOnFire.Serializer());
   }
}
