package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.VariantList;
import net.minecraft.util.JsonUtils;

public class Selector {
   private final ICondition field_188167_a;
   private final VariantList field_188168_b;

   public Selector(ICondition p_i46562_1_, VariantList p_i46562_2_) {
      if (p_i46562_1_ == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (p_i46562_2_ == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.field_188167_a = p_i46562_1_;
         this.field_188168_b = p_i46562_2_;
      }
   }

   public VariantList func_188165_a() {
      return this.field_188168_b;
   }

   public Predicate<IBlockState> func_188166_a(BlockStateContainer p_188166_1_) {
      return this.field_188167_a.func_188118_a(p_188166_1_);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof Selector) {
            Selector selector = (Selector)p_equals_1_;
            if (this.field_188167_a.equals(selector.field_188167_a)) {
               return this.field_188168_b.equals(selector.field_188168_b);
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.field_188167_a.hashCode() + this.field_188168_b.hashCode();
   }

   public static class Deserializer implements JsonDeserializer<Selector> {
      private static final Function<JsonElement, ICondition> field_188163_a = new Function<JsonElement, ICondition>() {
         @Nullable
         public ICondition apply(@Nullable JsonElement p_apply_1_) {
            return p_apply_1_ == null ? null : Selector.Deserializer.func_188158_a(p_apply_1_.getAsJsonObject());
         }
      };
      private static final Function<Entry<String, JsonElement>, ICondition> field_188164_b = new Function<Entry<String, JsonElement>, ICondition>() {
         @Nullable
         public ICondition apply(@Nullable Entry<String, JsonElement> p_apply_1_) {
            return p_apply_1_ == null ? null : Selector.Deserializer.func_188161_b(p_apply_1_);
         }
      };

      public Selector deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         return new Selector(this.func_188159_b(jsonobject), (VariantList)p_deserialize_3_.deserialize(jsonobject.get("apply"), VariantList.class));
      }

      private ICondition func_188159_b(JsonObject p_188159_1_) {
         return p_188159_1_.has("when") ? func_188158_a(JsonUtils.func_152754_s(p_188159_1_, "when")) : ICondition.field_188119_a;
      }

      @VisibleForTesting
      static ICondition func_188158_a(JsonObject p_188158_0_) {
         Set<Entry<String, JsonElement>> set = p_188158_0_.entrySet();
         if (set.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (set.size() == 1) {
            if (p_188158_0_.has("OR")) {
               return new ConditionOr(Iterables.transform(JsonUtils.func_151214_t(p_188158_0_, "OR"), field_188163_a));
            } else {
               return (ICondition)(p_188158_0_.has("AND") ? new ConditionAnd(Iterables.transform(JsonUtils.func_151214_t(p_188158_0_, "AND"), field_188163_a)) : func_188161_b(set.iterator().next()));
            }
         } else {
            return new ConditionAnd(Iterables.transform(set, field_188164_b));
         }
      }

      private static ConditionPropertyValue func_188161_b(Entry<String, JsonElement> p_188161_0_) {
         return new ConditionPropertyValue(p_188161_0_.getKey(), ((JsonElement)p_188161_0_.getValue()).getAsString());
      }
   }
}
