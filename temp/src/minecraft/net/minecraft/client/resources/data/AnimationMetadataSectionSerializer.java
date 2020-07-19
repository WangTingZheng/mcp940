package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer<AnimationMetadataSection> implements JsonSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
      List<AnimationFrame> list = Lists.<AnimationFrame>newArrayList();
      JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "metadata section");
      int i = JsonUtils.func_151208_a(jsonobject, "frametime", 1);
      if (i != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)i, "Invalid default frame time");
      }

      if (jsonobject.has("frames")) {
         try {
            JsonArray jsonarray = JsonUtils.func_151214_t(jsonobject, "frames");

            for(int j = 0; j < jsonarray.size(); ++j) {
               JsonElement jsonelement = jsonarray.get(j);
               AnimationFrame animationframe = this.func_110492_a(j, jsonelement);
               if (animationframe != null) {
                  list.add(animationframe);
               }
            }
         } catch (ClassCastException classcastexception) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonobject.get("frames"), classcastexception);
         }
      }

      int k = JsonUtils.func_151208_a(jsonobject, "width", -1);
      int l = JsonUtils.func_151208_a(jsonobject, "height", -1);
      if (k != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)k, "Invalid width");
      }

      if (l != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)l, "Invalid height");
      }

      boolean flag = JsonUtils.func_151209_a(jsonobject, "interpolate", false);
      return new AnimationMetadataSection(list, k, l, i, flag);
   }

   private AnimationFrame func_110492_a(int p_110492_1_, JsonElement p_110492_2_) {
      if (p_110492_2_.isJsonPrimitive()) {
         return new AnimationFrame(JsonUtils.func_151215_f(p_110492_2_, "frames[" + p_110492_1_ + "]"));
      } else if (p_110492_2_.isJsonObject()) {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_110492_2_, "frames[" + p_110492_1_ + "]");
         int i = JsonUtils.func_151208_a(jsonobject, "time", -1);
         if (jsonobject.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)i, "Invalid frame time");
         }

         int j = JsonUtils.func_151203_m(jsonobject, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)j, "Invalid frame index");
         return new AnimationFrame(j, i);
      } else {
         return null;
      }
   }

   public JsonElement serialize(AnimationMetadataSection p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("frametime", Integer.valueOf(p_serialize_1_.func_110469_d()));
      if (p_serialize_1_.func_110474_b() != -1) {
         jsonobject.addProperty("width", Integer.valueOf(p_serialize_1_.func_110474_b()));
      }

      if (p_serialize_1_.func_110471_a() != -1) {
         jsonobject.addProperty("height", Integer.valueOf(p_serialize_1_.func_110471_a()));
      }

      if (p_serialize_1_.func_110473_c() > 0) {
         JsonArray jsonarray = new JsonArray();

         for(int i = 0; i < p_serialize_1_.func_110473_c(); ++i) {
            if (p_serialize_1_.func_110470_b(i)) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("index", Integer.valueOf(p_serialize_1_.func_110468_c(i)));
               jsonobject1.addProperty("time", Integer.valueOf(p_serialize_1_.func_110472_a(i)));
               jsonarray.add(jsonobject1);
            } else {
               jsonarray.add(new JsonPrimitive(p_serialize_1_.func_110468_c(i)));
            }
         }

         jsonobject.add("frames", jsonarray);
      }

      return jsonobject;
   }

   public String func_110483_a() {
      return "animation";
   }
}
