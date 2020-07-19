package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.item.Item;

public class JsonUtils {
   public static boolean func_151205_a(JsonObject p_151205_0_, String p_151205_1_) {
      return !func_151201_f(p_151205_0_, p_151205_1_) ? false : p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
   }

   public static boolean func_151211_a(JsonElement p_151211_0_) {
      return !p_151211_0_.isJsonPrimitive() ? false : p_151211_0_.getAsJsonPrimitive().isString();
   }

   public static boolean func_188175_b(JsonElement p_188175_0_) {
      return !p_188175_0_.isJsonPrimitive() ? false : p_188175_0_.getAsJsonPrimitive().isNumber();
   }

   public static boolean func_180199_c(JsonObject p_180199_0_, String p_180199_1_) {
      return !func_151201_f(p_180199_0_, p_180199_1_) ? false : p_180199_0_.getAsJsonPrimitive(p_180199_1_).isBoolean();
   }

   public static boolean func_151202_d(JsonObject p_151202_0_, String p_151202_1_) {
      return !func_151204_g(p_151202_0_, p_151202_1_) ? false : p_151202_0_.get(p_151202_1_).isJsonArray();
   }

   public static boolean func_151201_f(JsonObject p_151201_0_, String p_151201_1_) {
      return !func_151204_g(p_151201_0_, p_151201_1_) ? false : p_151201_0_.get(p_151201_1_).isJsonPrimitive();
   }

   public static boolean func_151204_g(JsonObject p_151204_0_, String p_151204_1_) {
      if (p_151204_0_ == null) {
         return false;
      } else {
         return p_151204_0_.get(p_151204_1_) != null;
      }
   }

   public static String func_151206_a(JsonElement p_151206_0_, String p_151206_1_) {
      if (p_151206_0_.isJsonPrimitive()) {
         return p_151206_0_.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + func_151222_d(p_151206_0_));
      }
   }

   public static String func_151200_h(JsonObject p_151200_0_, String p_151200_1_) {
      if (p_151200_0_.has(p_151200_1_)) {
         return func_151206_a(p_151200_0_.get(p_151200_1_), p_151200_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
      }
   }

   public static String func_151219_a(JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
      return p_151219_0_.has(p_151219_1_) ? func_151206_a(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
   }

   public static Item func_188172_b(JsonElement p_188172_0_, String p_188172_1_) {
      if (p_188172_0_.isJsonPrimitive()) {
         String s = p_188172_0_.getAsString();
         Item item = Item.func_111206_d(s);
         if (item == null) {
            throw new JsonSyntaxException("Expected " + p_188172_1_ + " to be an item, was unknown string '" + s + "'");
         } else {
            return item;
         }
      } else {
         throw new JsonSyntaxException("Expected " + p_188172_1_ + " to be an item, was " + func_151222_d(p_188172_0_));
      }
   }

   public static Item func_188180_i(JsonObject p_188180_0_, String p_188180_1_) {
      if (p_188180_0_.has(p_188180_1_)) {
         return func_188172_b(p_188180_0_.get(p_188180_1_), p_188180_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_188180_1_ + ", expected to find an item");
      }
   }

   public static boolean func_151216_b(JsonElement p_151216_0_, String p_151216_1_) {
      if (p_151216_0_.isJsonPrimitive()) {
         return p_151216_0_.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + func_151222_d(p_151216_0_));
      }
   }

   public static boolean func_151212_i(JsonObject p_151212_0_, String p_151212_1_) {
      if (p_151212_0_.has(p_151212_1_)) {
         return func_151216_b(p_151212_0_.get(p_151212_1_), p_151212_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
      }
   }

   public static boolean func_151209_a(JsonObject p_151209_0_, String p_151209_1_, boolean p_151209_2_) {
      return p_151209_0_.has(p_151209_1_) ? func_151216_b(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
   }

   public static float func_151220_d(JsonElement p_151220_0_, String p_151220_1_) {
      if (p_151220_0_.isJsonPrimitive() && p_151220_0_.getAsJsonPrimitive().isNumber()) {
         return p_151220_0_.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + func_151222_d(p_151220_0_));
      }
   }

   public static float func_151217_k(JsonObject p_151217_0_, String p_151217_1_) {
      if (p_151217_0_.has(p_151217_1_)) {
         return func_151220_d(p_151217_0_.get(p_151217_1_), p_151217_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
      }
   }

   public static float func_151221_a(JsonObject p_151221_0_, String p_151221_1_, float p_151221_2_) {
      return p_151221_0_.has(p_151221_1_) ? func_151220_d(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
   }

   public static int func_151215_f(JsonElement p_151215_0_, String p_151215_1_) {
      if (p_151215_0_.isJsonPrimitive() && p_151215_0_.getAsJsonPrimitive().isNumber()) {
         return p_151215_0_.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + func_151222_d(p_151215_0_));
      }
   }

   public static int func_151203_m(JsonObject p_151203_0_, String p_151203_1_) {
      if (p_151203_0_.has(p_151203_1_)) {
         return func_151215_f(p_151203_0_.get(p_151203_1_), p_151203_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
      }
   }

   public static int func_151208_a(JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
      return p_151208_0_.has(p_151208_1_) ? func_151215_f(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
   }

   public static JsonObject func_151210_l(JsonElement p_151210_0_, String p_151210_1_) {
      if (p_151210_0_.isJsonObject()) {
         return p_151210_0_.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + func_151222_d(p_151210_0_));
      }
   }

   public static JsonObject func_152754_s(JsonObject p_152754_0_, String p_152754_1_) {
      if (p_152754_0_.has(p_152754_1_)) {
         return func_151210_l(p_152754_0_.get(p_152754_1_), p_152754_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_152754_1_ + ", expected to find a JsonObject");
      }
   }

   public static JsonObject func_151218_a(JsonObject p_151218_0_, String p_151218_1_, JsonObject p_151218_2_) {
      return p_151218_0_.has(p_151218_1_) ? func_151210_l(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
   }

   public static JsonArray func_151207_m(JsonElement p_151207_0_, String p_151207_1_) {
      if (p_151207_0_.isJsonArray()) {
         return p_151207_0_.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + func_151222_d(p_151207_0_));
      }
   }

   public static JsonArray func_151214_t(JsonObject p_151214_0_, String p_151214_1_) {
      if (p_151214_0_.has(p_151214_1_)) {
         return func_151207_m(p_151214_0_.get(p_151214_1_), p_151214_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
      }
   }

   public static JsonArray func_151213_a(JsonObject p_151213_0_, String p_151213_1_, @Nullable JsonArray p_151213_2_) {
      return p_151213_0_.has(p_151213_1_) ? func_151207_m(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
   }

   public static <T> T func_188179_a(@Nullable JsonElement p_188179_0_, String p_188179_1_, JsonDeserializationContext p_188179_2_, Class<? extends T> p_188179_3_) {
      if (p_188179_0_ != null) {
         return (T)p_188179_2_.deserialize(p_188179_0_, p_188179_3_);
      } else {
         throw new JsonSyntaxException("Missing " + p_188179_1_);
      }
   }

   public static <T> T func_188174_a(JsonObject p_188174_0_, String p_188174_1_, JsonDeserializationContext p_188174_2_, Class<? extends T> p_188174_3_) {
      if (p_188174_0_.has(p_188174_1_)) {
         return (T)func_188179_a(p_188174_0_.get(p_188174_1_), p_188174_1_, p_188174_2_, p_188174_3_);
      } else {
         throw new JsonSyntaxException("Missing " + p_188174_1_);
      }
   }

   public static <T> T func_188177_a(JsonObject p_188177_0_, String p_188177_1_, T p_188177_2_, JsonDeserializationContext p_188177_3_, Class<? extends T> p_188177_4_) {
      return (T)(p_188177_0_.has(p_188177_1_) ? func_188179_a(p_188177_0_.get(p_188177_1_), p_188177_1_, p_188177_3_, p_188177_4_) : p_188177_2_);
   }

   public static String func_151222_d(JsonElement p_151222_0_) {
      String s = org.apache.commons.lang3.StringUtils.abbreviateMiddle(String.valueOf((Object)p_151222_0_), "...", 10);
      if (p_151222_0_ == null) {
         return "null (missing)";
      } else if (p_151222_0_.isJsonNull()) {
         return "null (json)";
      } else if (p_151222_0_.isJsonArray()) {
         return "an array (" + s + ")";
      } else if (p_151222_0_.isJsonObject()) {
         return "an object (" + s + ")";
      } else {
         if (p_151222_0_.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = p_151222_0_.getAsJsonPrimitive();
            if (jsonprimitive.isNumber()) {
               return "a number (" + s + ")";
            }

            if (jsonprimitive.isBoolean()) {
               return "a boolean (" + s + ")";
            }
         }

         return s;
      }
   }

   @Nullable
   public static <T> T func_188173_a(Gson p_188173_0_, Reader p_188173_1_, Class<T> p_188173_2_, boolean p_188173_3_) {
      try {
         JsonReader jsonreader = new JsonReader(p_188173_1_);
         jsonreader.setLenient(p_188173_3_);
         return (T)p_188173_0_.getAdapter(p_188173_2_).read(jsonreader);
      } catch (IOException ioexception) {
         throw new JsonParseException(ioexception);
      }
   }

   @Nullable
   public static <T> T func_193838_a(Gson p_193838_0_, Reader p_193838_1_, Type p_193838_2_, boolean p_193838_3_) {
      try {
         JsonReader jsonreader = new JsonReader(p_193838_1_);
         jsonreader.setLenient(p_193838_3_);
         return (T)p_193838_0_.getAdapter(TypeToken.get(p_193838_2_)).read(jsonreader);
      } catch (IOException ioexception) {
         throw new JsonParseException(ioexception);
      }
   }

   @Nullable
   public static <T> T func_193837_a(Gson p_193837_0_, String p_193837_1_, Type p_193837_2_, boolean p_193837_3_) {
      return (T)func_193838_a(p_193837_0_, new StringReader(p_193837_1_), p_193837_2_, p_193837_3_);
   }

   @Nullable
   public static <T> T func_188176_a(Gson p_188176_0_, String p_188176_1_, Class<T> p_188176_2_, boolean p_188176_3_) {
      return (T)func_188173_a(p_188176_0_, new StringReader(p_188176_1_), p_188176_2_, p_188176_3_);
   }

   @Nullable
   public static <T> T func_193841_a(Gson p_193841_0_, Reader p_193841_1_, Type p_193841_2_) {
      return (T)func_193838_a(p_193841_0_, p_193841_1_, p_193841_2_, false);
   }

   @Nullable
   public static <T> T func_193840_a(Gson p_193840_0_, String p_193840_1_, Type p_193840_2_) {
      return (T)func_193837_a(p_193840_0_, p_193840_1_, p_193840_2_, false);
   }

   @Nullable
   public static <T> T func_193839_a(Gson p_193839_0_, Reader p_193839_1_, Class<T> p_193839_2_) {
      return (T)func_188173_a(p_193839_0_, p_193839_1_, p_193839_2_, false);
   }

   @Nullable
   public static <T> T func_188178_a(Gson p_188178_0_, String p_188178_1_, Class<T> p_188178_2_) {
      return (T)func_188176_a(p_188178_0_, p_188178_1_, p_188178_2_, false);
   }
}
