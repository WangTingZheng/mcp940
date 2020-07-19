package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;

public class VariantList {
   private final List<Variant> field_188115_a;

   public VariantList(List<Variant> p_i46568_1_) {
      this.field_188115_a = p_i46568_1_;
   }

   public List<Variant> func_188114_a() {
      return this.field_188115_a;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ instanceof VariantList) {
         VariantList variantlist = (VariantList)p_equals_1_;
         return this.field_188115_a.equals(variantlist.field_188115_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_188115_a.hashCode();
   }

   public static class Deserializer implements JsonDeserializer<VariantList> {
      public VariantList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         List<Variant> list = Lists.<Variant>newArrayList();
         if (p_deserialize_1_.isJsonArray()) {
            JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();
            if (jsonarray.size() == 0) {
               throw new JsonParseException("Empty variant array");
            }

            for(JsonElement jsonelement : jsonarray) {
               list.add((Variant)p_deserialize_3_.deserialize(jsonelement, Variant.class));
            }
         } else {
            list.add((Variant)p_deserialize_3_.deserialize(p_deserialize_1_, Variant.class));
         }

         return new VariantList(list);
      }
   }
}
