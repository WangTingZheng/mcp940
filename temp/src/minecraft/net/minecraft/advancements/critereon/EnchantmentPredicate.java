package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EnchantmentPredicate {
   public static final EnchantmentPredicate field_192466_a = new EnchantmentPredicate();
   private final Enchantment field_192467_b;
   private final MinMaxBounds field_192468_c;

   public EnchantmentPredicate() {
      this.field_192467_b = null;
      this.field_192468_c = MinMaxBounds.field_192516_a;
   }

   public EnchantmentPredicate(@Nullable Enchantment p_i47436_1_, MinMaxBounds p_i47436_2_) {
      this.field_192467_b = p_i47436_1_;
      this.field_192468_c = p_i47436_2_;
   }

   public boolean func_192463_a(Map<Enchantment, Integer> p_192463_1_) {
      if (this.field_192467_b != null) {
         if (!p_192463_1_.containsKey(this.field_192467_b)) {
            return false;
         }

         int i = ((Integer)p_192463_1_.get(this.field_192467_b)).intValue();
         if (this.field_192468_c != null && !this.field_192468_c.func_192514_a((float)i)) {
            return false;
         }
      } else if (this.field_192468_c != null) {
         for(Integer integer : p_192463_1_.values()) {
            if (this.field_192468_c.func_192514_a((float)integer.intValue())) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public static EnchantmentPredicate func_192464_a(@Nullable JsonElement p_192464_0_) {
      if (p_192464_0_ != null && !p_192464_0_.isJsonNull()) {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_192464_0_, "enchantment");
         Enchantment enchantment = null;
         if (jsonobject.has("enchantment")) {
            ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(jsonobject, "enchantment"));
            enchantment = Enchantment.field_185264_b.func_82594_a(resourcelocation);
            if (enchantment == null) {
               throw new JsonSyntaxException("Unknown enchantment '" + resourcelocation + "'");
            }
         }

         MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("levels"));
         return new EnchantmentPredicate(enchantment, minmaxbounds);
      } else {
         return field_192466_a;
      }
   }

   public static EnchantmentPredicate[] func_192465_b(@Nullable JsonElement p_192465_0_) {
      if (p_192465_0_ != null && !p_192465_0_.isJsonNull()) {
         JsonArray jsonarray = JsonUtils.func_151207_m(p_192465_0_, "enchantments");
         EnchantmentPredicate[] aenchantmentpredicate = new EnchantmentPredicate[jsonarray.size()];

         for(int i = 0; i < aenchantmentpredicate.length; ++i) {
            aenchantmentpredicate[i] = func_192464_a(jsonarray.get(i));
         }

         return aenchantmentpredicate;
      } else {
         return new EnchantmentPredicate[0];
      }
   }
}
