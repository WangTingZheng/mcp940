package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;

public class RandomValueRange {
   private final float field_186514_a;
   private final float field_186515_b;

   public RandomValueRange(float p_i46629_1_, float p_i46629_2_) {
      this.field_186514_a = p_i46629_1_;
      this.field_186515_b = p_i46629_2_;
   }

   public RandomValueRange(float p_i46630_1_) {
      this.field_186514_a = p_i46630_1_;
      this.field_186515_b = p_i46630_1_;
   }

   public float func_186509_a() {
      return this.field_186514_a;
   }

   public float func_186512_b() {
      return this.field_186515_b;
   }

   public int func_186511_a(Random p_186511_1_) {
      return MathHelper.func_76136_a(p_186511_1_, MathHelper.func_76141_d(this.field_186514_a), MathHelper.func_76141_d(this.field_186515_b));
   }

   public float func_186507_b(Random p_186507_1_) {
      return MathHelper.func_151240_a(p_186507_1_, this.field_186514_a, this.field_186515_b);
   }

   public boolean func_186510_a(int p_186510_1_) {
      return (float)p_186510_1_ <= this.field_186515_b && (float)p_186510_1_ >= this.field_186514_a;
   }

   public static class Serializer implements JsonDeserializer<RandomValueRange>, JsonSerializer<RandomValueRange> {
      public RandomValueRange deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (JsonUtils.func_188175_b(p_deserialize_1_)) {
            return new RandomValueRange(JsonUtils.func_151220_d(p_deserialize_1_, "value"));
         } else {
            JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "value");
            float f = JsonUtils.func_151217_k(jsonobject, "min");
            float f1 = JsonUtils.func_151217_k(jsonobject, "max");
            return new RandomValueRange(f, f1);
         }
      }

      public JsonElement serialize(RandomValueRange p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         if (p_serialize_1_.field_186514_a == p_serialize_1_.field_186515_b) {
            return new JsonPrimitive(p_serialize_1_.field_186514_a);
         } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("min", Float.valueOf(p_serialize_1_.field_186514_a));
            jsonobject.addProperty("max", Float.valueOf(p_serialize_1_.field_186515_b));
            return jsonobject;
         }
      }
   }
}
