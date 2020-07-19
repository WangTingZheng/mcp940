package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;

public class MinMaxBounds {
   public static final MinMaxBounds field_192516_a = new MinMaxBounds((Float)null, (Float)null);
   private final Float field_192517_b;
   private final Float field_192518_c;

   public MinMaxBounds(@Nullable Float p_i47431_1_, @Nullable Float p_i47431_2_) {
      this.field_192517_b = p_i47431_1_;
      this.field_192518_c = p_i47431_2_;
   }

   public boolean func_192514_a(float p_192514_1_) {
      if (this.field_192517_b != null && this.field_192517_b.floatValue() > p_192514_1_) {
         return false;
      } else {
         return this.field_192518_c == null || this.field_192518_c.floatValue() >= p_192514_1_;
      }
   }

   public boolean func_192513_a(double p_192513_1_) {
      if (this.field_192517_b != null && (double)(this.field_192517_b.floatValue() * this.field_192517_b.floatValue()) > p_192513_1_) {
         return false;
      } else {
         return this.field_192518_c == null || (double)(this.field_192518_c.floatValue() * this.field_192518_c.floatValue()) >= p_192513_1_;
      }
   }

   public static MinMaxBounds func_192515_a(@Nullable JsonElement p_192515_0_) {
      if (p_192515_0_ != null && !p_192515_0_.isJsonNull()) {
         if (JsonUtils.func_188175_b(p_192515_0_)) {
            float f2 = JsonUtils.func_151220_d(p_192515_0_, "value");
            return new MinMaxBounds(f2, f2);
         } else {
            JsonObject jsonobject = JsonUtils.func_151210_l(p_192515_0_, "value");
            Float f = jsonobject.has("min") ? JsonUtils.func_151217_k(jsonobject, "min") : null;
            Float f1 = jsonobject.has("max") ? JsonUtils.func_151217_k(jsonobject, "max") : null;
            return new MinMaxBounds(f, f1);
         }
      } else {
         return field_192516_a;
      }
   }
}
