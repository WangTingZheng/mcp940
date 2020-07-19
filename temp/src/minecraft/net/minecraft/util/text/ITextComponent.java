package net.minecraft.util.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;

public interface ITextComponent extends Iterable<ITextComponent> {
   ITextComponent func_150255_a(Style var1);

   Style func_150256_b();

   ITextComponent func_150258_a(String var1);

   ITextComponent func_150257_a(ITextComponent var1);

   String func_150261_e();

   String func_150260_c();

   String func_150254_d();

   List<ITextComponent> func_150253_a();

   ITextComponent func_150259_f();

   public static class Serializer implements JsonDeserializer<ITextComponent>, JsonSerializer<ITextComponent> {
      private static final Gson field_150700_a;

      public ITextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonPrimitive()) {
            return new TextComponentString(p_deserialize_1_.getAsString());
         } else if (!p_deserialize_1_.isJsonObject()) {
            if (p_deserialize_1_.isJsonArray()) {
               JsonArray jsonarray1 = p_deserialize_1_.getAsJsonArray();
               ITextComponent itextcomponent1 = null;

               for(JsonElement jsonelement : jsonarray1) {
                  ITextComponent itextcomponent2 = this.deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
                  if (itextcomponent1 == null) {
                     itextcomponent1 = itextcomponent2;
                  } else {
                     itextcomponent1.func_150257_a(itextcomponent2);
                  }
               }

               return itextcomponent1;
            } else {
               throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
            }
         } else {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            ITextComponent itextcomponent;
            if (jsonobject.has("text")) {
               itextcomponent = new TextComponentString(jsonobject.get("text").getAsString());
            } else if (jsonobject.has("translate")) {
               String s = jsonobject.get("translate").getAsString();
               if (jsonobject.has("with")) {
                  JsonArray jsonarray = jsonobject.getAsJsonArray("with");
                  Object[] aobject = new Object[jsonarray.size()];

                  for(int i = 0; i < aobject.length; ++i) {
                     aobject[i] = this.deserialize(jsonarray.get(i), p_deserialize_2_, p_deserialize_3_);
                     if (aobject[i] instanceof TextComponentString) {
                        TextComponentString textcomponentstring = (TextComponentString)aobject[i];
                        if (textcomponentstring.func_150256_b().func_150229_g() && textcomponentstring.func_150253_a().isEmpty()) {
                           aobject[i] = textcomponentstring.func_150265_g();
                        }
                     }
                  }

                  itextcomponent = new TextComponentTranslation(s, aobject);
               } else {
                  itextcomponent = new TextComponentTranslation(s, new Object[0]);
               }
            } else if (jsonobject.has("score")) {
               JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
               if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               itextcomponent = new TextComponentScore(JsonUtils.func_151200_h(jsonobject1, "name"), JsonUtils.func_151200_h(jsonobject1, "objective"));
               if (jsonobject1.has("value")) {
                  ((TextComponentScore)itextcomponent).func_179997_b(JsonUtils.func_151200_h(jsonobject1, "value"));
               }
            } else if (jsonobject.has("selector")) {
               itextcomponent = new TextComponentSelector(JsonUtils.func_151200_h(jsonobject, "selector"));
            } else {
               if (!jsonobject.has("keybind")) {
                  throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
               }

               itextcomponent = new TextComponentKeybind(JsonUtils.func_151200_h(jsonobject, "keybind"));
            }

            if (jsonobject.has("extra")) {
               JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
               if (jsonarray2.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int j = 0; j < jsonarray2.size(); ++j) {
                  itextcomponent.func_150257_a(this.deserialize(jsonarray2.get(j), p_deserialize_2_, p_deserialize_3_));
               }
            }

            itextcomponent.func_150255_a((Style)p_deserialize_3_.deserialize(p_deserialize_1_, Style.class));
            return itextcomponent;
         }
      }

      private void func_150695_a(Style p_150695_1_, JsonObject p_150695_2_, JsonSerializationContext p_150695_3_) {
         JsonElement jsonelement = p_150695_3_.serialize(p_150695_1_);
         if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = (JsonObject)jsonelement;

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               p_150695_2_.add(entry.getKey(), entry.getValue());
            }
         }

      }

      public JsonElement serialize(ITextComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         if (!p_serialize_1_.func_150256_b().func_150229_g()) {
            this.func_150695_a(p_serialize_1_.func_150256_b(), jsonobject, p_serialize_3_);
         }

         if (!p_serialize_1_.func_150253_a().isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(ITextComponent itextcomponent : p_serialize_1_.func_150253_a()) {
               jsonarray.add(this.serialize(itextcomponent, itextcomponent.getClass(), p_serialize_3_));
            }

            jsonobject.add("extra", jsonarray);
         }

         if (p_serialize_1_ instanceof TextComponentString) {
            jsonobject.addProperty("text", ((TextComponentString)p_serialize_1_).func_150265_g());
         } else if (p_serialize_1_ instanceof TextComponentTranslation) {
            TextComponentTranslation textcomponenttranslation = (TextComponentTranslation)p_serialize_1_;
            jsonobject.addProperty("translate", textcomponenttranslation.func_150268_i());
            if (textcomponenttranslation.func_150271_j() != null && textcomponenttranslation.func_150271_j().length > 0) {
               JsonArray jsonarray1 = new JsonArray();

               for(Object object : textcomponenttranslation.func_150271_j()) {
                  if (object instanceof ITextComponent) {
                     jsonarray1.add(this.serialize((ITextComponent)object, object.getClass(), p_serialize_3_));
                  } else {
                     jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                  }
               }

               jsonobject.add("with", jsonarray1);
            }
         } else if (p_serialize_1_ instanceof TextComponentScore) {
            TextComponentScore textcomponentscore = (TextComponentScore)p_serialize_1_;
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("name", textcomponentscore.func_179995_g());
            jsonobject1.addProperty("objective", textcomponentscore.func_179994_h());
            jsonobject1.addProperty("value", textcomponentscore.func_150261_e());
            jsonobject.add("score", jsonobject1);
         } else if (p_serialize_1_ instanceof TextComponentSelector) {
            TextComponentSelector textcomponentselector = (TextComponentSelector)p_serialize_1_;
            jsonobject.addProperty("selector", textcomponentselector.func_179992_g());
         } else {
            if (!(p_serialize_1_ instanceof TextComponentKeybind)) {
               throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
            }

            TextComponentKeybind textcomponentkeybind = (TextComponentKeybind)p_serialize_1_;
            jsonobject.addProperty("keybind", textcomponentkeybind.func_193633_h());
         }

         return jsonobject;
      }

      public static String func_150696_a(ITextComponent p_150696_0_) {
         return field_150700_a.toJson(p_150696_0_);
      }

      @Nullable
      public static ITextComponent func_150699_a(String p_150699_0_) {
         return (ITextComponent)JsonUtils.func_188176_a(field_150700_a, p_150699_0_, ITextComponent.class, false);
      }

      @Nullable
      public static ITextComponent func_186877_b(String p_186877_0_) {
         return (ITextComponent)JsonUtils.func_188176_a(field_150700_a, p_186877_0_, ITextComponent.class, true);
      }

      static {
         GsonBuilder gsonbuilder = new GsonBuilder();
         gsonbuilder.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
         gsonbuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         field_150700_a = gsonbuilder.create();
      }
   }
}
