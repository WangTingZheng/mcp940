package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.multipart.Multipart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.util.JsonUtils;

public class ModelBlockDefinition {
   @VisibleForTesting
   static final Gson field_178333_a = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).registerTypeAdapter(VariantList.class, new VariantList.Deserializer()).registerTypeAdapter(Multipart.class, new Multipart.Deserializer()).registerTypeAdapter(Selector.class, new Selector.Deserializer()).create();
   private final Map<String, VariantList> field_178332_b = Maps.<String, VariantList>newHashMap();
   private Multipart field_188005_c;

   public static ModelBlockDefinition func_178331_a(Reader p_178331_0_) {
      return (ModelBlockDefinition)JsonUtils.func_193839_a(field_178333_a, p_178331_0_, ModelBlockDefinition.class);
   }

   public ModelBlockDefinition(Map<String, VariantList> p_i46572_1_, Multipart p_i46572_2_) {
      this.field_188005_c = p_i46572_2_;
      this.field_178332_b.putAll(p_i46572_1_);
   }

   public ModelBlockDefinition(List<ModelBlockDefinition> p_i46222_1_) {
      ModelBlockDefinition modelblockdefinition = null;

      for(ModelBlockDefinition modelblockdefinition1 : p_i46222_1_) {
         if (modelblockdefinition1.func_188002_b()) {
            this.field_178332_b.clear();
            modelblockdefinition = modelblockdefinition1;
         }

         this.field_178332_b.putAll(modelblockdefinition1.field_178332_b);
      }

      if (modelblockdefinition != null) {
         this.field_188005_c = modelblockdefinition.field_188005_c;
      }

   }

   public boolean func_188000_b(String p_188000_1_) {
      return this.field_178332_b.get(p_188000_1_) != null;
   }

   public VariantList func_188004_c(String p_188004_1_) {
      VariantList variantlist = this.field_178332_b.get(p_188004_1_);
      if (variantlist == null) {
         throw new ModelBlockDefinition.MissingVariantException();
      } else {
         return variantlist;
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof ModelBlockDefinition) {
            ModelBlockDefinition modelblockdefinition = (ModelBlockDefinition)p_equals_1_;
            if (this.field_178332_b.equals(modelblockdefinition.field_178332_b)) {
               return this.func_188002_b() ? this.field_188005_c.equals(modelblockdefinition.field_188005_c) : !modelblockdefinition.func_188002_b();
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.field_178332_b.hashCode() + (this.func_188002_b() ? this.field_188005_c.hashCode() : 0);
   }

   public Set<VariantList> func_188003_a() {
      Set<VariantList> set = Sets.newHashSet(this.field_178332_b.values());
      if (this.func_188002_b()) {
         set.addAll(this.field_188005_c.func_188137_b());
      }

      return set;
   }

   public boolean func_188002_b() {
      return this.field_188005_c != null;
   }

   public Multipart func_188001_c() {
      return this.field_188005_c;
   }

   public static class Deserializer implements JsonDeserializer<ModelBlockDefinition> {
      public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         Map<String, VariantList> map = this.func_187999_a(p_deserialize_3_, jsonobject);
         Multipart multipart = this.func_187998_b(p_deserialize_3_, jsonobject);
         if (!map.isEmpty() || multipart != null && !multipart.func_188137_b().isEmpty()) {
            return new ModelBlockDefinition(map, multipart);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, VariantList> func_187999_a(JsonDeserializationContext p_187999_1_, JsonObject p_187999_2_) {
         Map<String, VariantList> map = Maps.<String, VariantList>newHashMap();
         if (p_187999_2_.has("variants")) {
            JsonObject jsonobject = JsonUtils.func_152754_s(p_187999_2_, "variants");

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               map.put(entry.getKey(), (VariantList)p_187999_1_.deserialize(entry.getValue(), VariantList.class));
            }
         }

         return map;
      }

      @Nullable
      protected Multipart func_187998_b(JsonDeserializationContext p_187998_1_, JsonObject p_187998_2_) {
         if (!p_187998_2_.has("multipart")) {
            return null;
         } else {
            JsonArray jsonarray = JsonUtils.func_151214_t(p_187998_2_, "multipart");
            return (Multipart)p_187998_1_.deserialize(jsonarray, Multipart.class);
         }
      }
   }

   public class MissingVariantException extends RuntimeException {
   }
}
