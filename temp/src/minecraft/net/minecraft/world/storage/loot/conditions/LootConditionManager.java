package net.minecraft.world.storage.loot.conditions;

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
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class LootConditionManager {
   private static final Map<ResourceLocation, LootCondition.Serializer<?>> field_186642_a = Maps.<ResourceLocation, LootCondition.Serializer<?>>newHashMap();
   private static final Map<Class<? extends LootCondition>, LootCondition.Serializer<?>> field_186643_b = Maps.<Class<? extends LootCondition>, LootCondition.Serializer<?>>newHashMap();

   public static <T extends LootCondition> void func_186639_a(LootCondition.Serializer<? extends T> p_186639_0_) {
      ResourceLocation resourcelocation = p_186639_0_.func_186602_a();
      Class<T> oclass = p_186639_0_.func_186604_b();
      if (field_186642_a.containsKey(resourcelocation)) {
         throw new IllegalArgumentException("Can't re-register item condition name " + resourcelocation);
      } else if (field_186643_b.containsKey(oclass)) {
         throw new IllegalArgumentException("Can't re-register item condition class " + oclass.getName());
      } else {
         field_186642_a.put(resourcelocation, p_186639_0_);
         field_186643_b.put(oclass, p_186639_0_);
      }
   }

   public static boolean func_186638_a(@Nullable LootCondition[] p_186638_0_, Random p_186638_1_, LootContext p_186638_2_) {
      if (p_186638_0_ == null) {
         return true;
      } else {
         for(LootCondition lootcondition : p_186638_0_) {
            if (!lootcondition.func_186618_a(p_186638_1_, p_186638_2_)) {
               return false;
            }
         }

         return true;
      }
   }

   public static LootCondition.Serializer<?> func_186641_a(ResourceLocation p_186641_0_) {
      LootCondition.Serializer<?> serializer = (LootCondition.Serializer)field_186642_a.get(p_186641_0_);
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot item condition '" + p_186641_0_ + "'");
      } else {
         return serializer;
      }
   }

   public static <T extends LootCondition> LootCondition.Serializer<T> func_186640_a(T p_186640_0_) {
      LootCondition.Serializer<T> serializer = (LootCondition.Serializer)field_186643_b.get(p_186640_0_.getClass());
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot item condition " + p_186640_0_);
      } else {
         return serializer;
      }
   }

   static {
      func_186639_a(new RandomChance.Serializer());
      func_186639_a(new RandomChanceWithLooting.Serializer());
      func_186639_a(new EntityHasProperty.Serializer());
      func_186639_a(new KilledByPlayer.Serializer());
      func_186639_a(new EntityHasScore.Serializer());
   }

   public static class Serializer implements JsonDeserializer<LootCondition>, JsonSerializer<LootCondition> {
      public LootCondition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "condition");
         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(jsonobject, "condition"));

         LootCondition.Serializer<?> serializer;
         try {
            serializer = LootConditionManager.func_186641_a(resourcelocation);
         } catch (IllegalArgumentException var8) {
            throw new JsonSyntaxException("Unknown condition '" + resourcelocation + "'");
         }

         return serializer.func_186603_b(jsonobject, p_deserialize_3_);
      }

      public JsonElement serialize(LootCondition p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         LootCondition.Serializer<LootCondition> serializer = LootConditionManager.<LootCondition>func_186640_a(p_serialize_1_);
         JsonObject jsonobject = new JsonObject();
         serializer.func_186605_a(jsonobject, p_serialize_1_, p_serialize_3_);
         jsonobject.addProperty("condition", serializer.func_186602_a().toString());
         return jsonobject;
      }
   }
}
