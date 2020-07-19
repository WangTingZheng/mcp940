package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion {
   private final ICriterionInstance field_192147_a;

   public Criterion(ICriterionInstance p_i47470_1_) {
      this.field_192147_a = p_i47470_1_;
   }

   public Criterion() {
      this.field_192147_a = null;
   }

   public void func_192140_a(PacketBuffer p_192140_1_) {
   }

   public static Criterion func_192145_a(JsonObject p_192145_0_, JsonDeserializationContext p_192145_1_) {
      ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(p_192145_0_, "trigger"));
      ICriterionTrigger<?> icriteriontrigger = CriteriaTriggers.func_192119_a(resourcelocation);
      if (icriteriontrigger == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
      } else {
         ICriterionInstance icriterioninstance = icriteriontrigger.func_192166_a(JsonUtils.func_151218_a(p_192145_0_, "conditions", new JsonObject()), p_192145_1_);
         return new Criterion(icriterioninstance);
      }
   }

   public static Criterion func_192146_b(PacketBuffer p_192146_0_) {
      return new Criterion();
   }

   public static Map<String, Criterion> func_192144_b(JsonObject p_192144_0_, JsonDeserializationContext p_192144_1_) {
      Map<String, Criterion> map = Maps.<String, Criterion>newHashMap();

      for(Entry<String, JsonElement> entry : p_192144_0_.entrySet()) {
         map.put(entry.getKey(), func_192145_a(JsonUtils.func_151210_l(entry.getValue(), "criterion"), p_192144_1_));
      }

      return map;
   }

   public static Map<String, Criterion> func_192142_c(PacketBuffer p_192142_0_) {
      Map<String, Criterion> map = Maps.<String, Criterion>newHashMap();
      int i = p_192142_0_.func_150792_a();

      for(int j = 0; j < i; ++j) {
         map.put(p_192142_0_.func_150789_c(32767), func_192146_b(p_192142_0_));
      }

      return map;
   }

   public static void func_192141_a(Map<String, Criterion> p_192141_0_, PacketBuffer p_192141_1_) {
      p_192141_1_.func_150787_b(p_192141_0_.size());

      for(Entry<String, Criterion> entry : p_192141_0_.entrySet()) {
         p_192141_1_.func_180714_a(entry.getKey());
         ((Criterion)entry.getValue()).func_192140_a(p_192141_1_);
      }

   }

   @Nullable
   public ICriterionInstance func_192143_a() {
      return this.field_192147_a;
   }
}
