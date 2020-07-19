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

public class LootFunctionManager {
   private static final Map<ResourceLocation, LootFunction.Serializer<?>> field_186584_a = Maps.<ResourceLocation, LootFunction.Serializer<?>>newHashMap();
   private static final Map<Class<? extends LootFunction>, LootFunction.Serializer<?>> field_186585_b = Maps.<Class<? extends LootFunction>, LootFunction.Serializer<?>>newHashMap();

   public static <T extends LootFunction> void func_186582_a(LootFunction.Serializer<? extends T> p_186582_0_) {
      ResourceLocation resourcelocation = p_186582_0_.func_186529_a();
      Class<T> oclass = p_186582_0_.func_186531_b();
      if (field_186584_a.containsKey(resourcelocation)) {
         throw new IllegalArgumentException("Can't re-register item function name " + resourcelocation);
      } else if (field_186585_b.containsKey(oclass)) {
         throw new IllegalArgumentException("Can't re-register item function class " + oclass.getName());
      } else {
         field_186584_a.put(resourcelocation, p_186582_0_);
         field_186585_b.put(oclass, p_186582_0_);
      }
   }

   public static LootFunction.Serializer<?> func_186583_a(ResourceLocation p_186583_0_) {
      LootFunction.Serializer<?> serializer = (LootFunction.Serializer)field_186584_a.get(p_186583_0_);
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot item function '" + p_186583_0_ + "'");
      } else {
         return serializer;
      }
   }

   public static <T extends LootFunction> LootFunction.Serializer<T> func_186581_a(T p_186581_0_) {
      LootFunction.Serializer<T> serializer = (LootFunction.Serializer)field_186585_b.get(p_186581_0_.getClass());
      if (serializer == null) {
         throw new IllegalArgumentException("Unknown loot item function " + p_186581_0_);
      } else {
         return serializer;
      }
   }

   static {
      func_186582_a(new SetCount.Serializer());
      func_186582_a(new SetMetadata.Serializer());
      func_186582_a(new EnchantWithLevels.Serializer());
      func_186582_a(new EnchantRandomly.Serializer());
      func_186582_a(new SetNBT.Serializer());
      func_186582_a(new Smelt.Serializer());
      func_186582_a(new LootingEnchantBonus.Serializer());
      func_186582_a(new SetDamage.Serializer());
      func_186582_a(new SetAttributes.Serializer());
   }

   public static class Serializer implements JsonDeserializer<LootFunction>, JsonSerializer<LootFunction> {
      public LootFunction deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "function");
         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(jsonobject, "function"));

         LootFunction.Serializer<?> serializer;
         try {
            serializer = LootFunctionManager.func_186583_a(resourcelocation);
         } catch (IllegalArgumentException var8) {
            throw new JsonSyntaxException("Unknown function '" + resourcelocation + "'");
         }

         return serializer.func_186530_b(jsonobject, p_deserialize_3_, (LootCondition[])JsonUtils.func_188177_a(jsonobject, "conditions", new LootCondition[0], p_deserialize_3_, LootCondition[].class));
      }

      public JsonElement serialize(LootFunction p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         LootFunction.Serializer<LootFunction> serializer = LootFunctionManager.<LootFunction>func_186581_a(p_serialize_1_);
         JsonObject jsonobject = new JsonObject();
         serializer.func_186532_a(jsonobject, p_serialize_1_, p_serialize_3_);
         jsonobject.addProperty("function", serializer.func_186529_a().toString());
         if (p_serialize_1_.func_186554_a() != null && p_serialize_1_.func_186554_a().length > 0) {
            jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.func_186554_a()));
         }

         return jsonobject;
      }
   }
}
