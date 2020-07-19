package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger field_178313_f = LogManager.getLogger();
   @VisibleForTesting
   static final Gson field_178319_a = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer()).create();
   private final List<BlockPart> field_178314_g;
   private final boolean field_178321_h;
   private final boolean field_178322_i;
   private final ItemCameraTransforms field_178320_j;
   private final List<ItemOverride> field_187968_k;
   public String field_178317_b = "";
   @VisibleForTesting
   protected final Map<String, String> field_178318_c;
   @VisibleForTesting
   protected ModelBlock field_178315_d;
   @VisibleForTesting
   protected ResourceLocation field_178316_e;

   public static ModelBlock func_178307_a(Reader p_178307_0_) {
      return (ModelBlock)JsonUtils.func_188173_a(field_178319_a, p_178307_0_, ModelBlock.class, false);
   }

   public static ModelBlock func_178294_a(String p_178294_0_) {
      return func_178307_a(new StringReader(p_178294_0_));
   }

   public ModelBlock(@Nullable ResourceLocation p_i46573_1_, List<BlockPart> p_i46573_2_, Map<String, String> p_i46573_3_, boolean p_i46573_4_, boolean p_i46573_5_, ItemCameraTransforms p_i46573_6_, List<ItemOverride> p_i46573_7_) {
      this.field_178314_g = p_i46573_2_;
      this.field_178322_i = p_i46573_4_;
      this.field_178321_h = p_i46573_5_;
      this.field_178318_c = p_i46573_3_;
      this.field_178316_e = p_i46573_1_;
      this.field_178320_j = p_i46573_6_;
      this.field_187968_k = p_i46573_7_;
   }

   public List<BlockPart> func_178298_a() {
      return this.field_178314_g.isEmpty() && this.func_178295_k() ? this.field_178315_d.func_178298_a() : this.field_178314_g;
   }

   private boolean func_178295_k() {
      return this.field_178315_d != null;
   }

   public boolean func_178309_b() {
      return this.func_178295_k() ? this.field_178315_d.func_178309_b() : this.field_178322_i;
   }

   public boolean func_178311_c() {
      return this.field_178321_h;
   }

   public boolean func_178303_d() {
      return this.field_178316_e == null || this.field_178315_d != null && this.field_178315_d.func_178303_d();
   }

   public void func_178299_a(Map<ResourceLocation, ModelBlock> p_178299_1_) {
      if (this.field_178316_e != null) {
         this.field_178315_d = p_178299_1_.get(this.field_178316_e);
      }

   }

   public Collection<ResourceLocation> func_187965_e() {
      Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();

      for(ItemOverride itemoverride : this.field_187968_k) {
         set.add(itemoverride.func_188026_a());
      }

      return set;
   }

   protected List<ItemOverride> func_187966_f() {
      return this.field_187968_k;
   }

   public ItemOverrideList func_187967_g() {
      return this.field_187968_k.isEmpty() ? ItemOverrideList.field_188022_a : new ItemOverrideList(this.field_187968_k);
   }

   public boolean func_178300_b(String p_178300_1_) {
      return !"missingno".equals(this.func_178308_c(p_178300_1_));
   }

   public String func_178308_c(String p_178308_1_) {
      if (!this.func_178304_d(p_178308_1_)) {
         p_178308_1_ = '#' + p_178308_1_;
      }

      return this.func_178302_a(p_178308_1_, new ModelBlock.Bookkeep(this));
   }

   private String func_178302_a(String p_178302_1_, ModelBlock.Bookkeep p_178302_2_) {
      if (this.func_178304_d(p_178302_1_)) {
         if (this == p_178302_2_.field_178323_b) {
            field_178313_f.warn("Unable to resolve texture due to upward reference: {} in {}", p_178302_1_, this.field_178317_b);
            return "missingno";
         } else {
            String s = this.field_178318_c.get(p_178302_1_.substring(1));
            if (s == null && this.func_178295_k()) {
               s = this.field_178315_d.func_178302_a(p_178302_1_, p_178302_2_);
            }

            p_178302_2_.field_178323_b = this;
            if (s != null && this.func_178304_d(s)) {
               s = p_178302_2_.field_178324_a.func_178302_a(s, p_178302_2_);
            }

            return s != null && !this.func_178304_d(s) ? s : "missingno";
         }
      } else {
         return p_178302_1_;
      }
   }

   private boolean func_178304_d(String p_178304_1_) {
      return p_178304_1_.charAt(0) == '#';
   }

   @Nullable
   public ResourceLocation func_178305_e() {
      return this.field_178316_e;
   }

   public ModelBlock func_178310_f() {
      return this.func_178295_k() ? this.field_178315_d.func_178310_f() : this;
   }

   public ItemCameraTransforms func_181682_g() {
      ItemTransformVec3f itemtransformvec3f = this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
      ItemTransformVec3f itemtransformvec3f1 = this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
      ItemTransformVec3f itemtransformvec3f2 = this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
      ItemTransformVec3f itemtransformvec3f3 = this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
      ItemTransformVec3f itemtransformvec3f4 = this.func_181681_a(ItemCameraTransforms.TransformType.HEAD);
      ItemTransformVec3f itemtransformvec3f5 = this.func_181681_a(ItemCameraTransforms.TransformType.GUI);
      ItemTransformVec3f itemtransformvec3f6 = this.func_181681_a(ItemCameraTransforms.TransformType.GROUND);
      ItemTransformVec3f itemtransformvec3f7 = this.func_181681_a(ItemCameraTransforms.TransformType.FIXED);
      return new ItemCameraTransforms(itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7);
   }

   private ItemTransformVec3f func_181681_a(ItemCameraTransforms.TransformType p_181681_1_) {
      return this.field_178315_d != null && !this.field_178320_j.func_181687_c(p_181681_1_) ? this.field_178315_d.func_181681_a(p_181681_1_) : this.field_178320_j.func_181688_b(p_181681_1_);
   }

   public static void func_178312_b(Map<ResourceLocation, ModelBlock> p_178312_0_) {
      for(ModelBlock modelblock : p_178312_0_.values()) {
         try {
            ModelBlock modelblock1 = modelblock.field_178315_d;

            for(ModelBlock modelblock2 = modelblock1.field_178315_d; modelblock1 != modelblock2; modelblock2 = modelblock2.field_178315_d.field_178315_d) {
               modelblock1 = modelblock1.field_178315_d;
            }

            throw new ModelBlock.LoopException();
         } catch (NullPointerException var5) {
            ;
         }
      }

   }

   static final class Bookkeep {
      public final ModelBlock field_178324_a;
      public ModelBlock field_178323_b;

      private Bookkeep(ModelBlock p_i46223_1_) {
         this.field_178324_a = p_i46223_1_;
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlock> {
      public ModelBlock deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         List<BlockPart> list = this.func_178325_a(p_deserialize_3_, jsonobject);
         String s = this.func_178326_c(jsonobject);
         Map<String, String> map = this.func_178329_b(jsonobject);
         boolean flag = this.func_178328_a(jsonobject);
         ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.field_178357_a;
         if (jsonobject.has("display")) {
            JsonObject jsonobject1 = JsonUtils.func_152754_s(jsonobject, "display");
            itemcameratransforms = (ItemCameraTransforms)p_deserialize_3_.deserialize(jsonobject1, ItemCameraTransforms.class);
         }

         List<ItemOverride> list1 = this.func_187964_a(p_deserialize_3_, jsonobject);
         ResourceLocation resourcelocation = s.isEmpty() ? null : new ResourceLocation(s);
         return new ModelBlock(resourcelocation, list, map, flag, true, itemcameratransforms, list1);
      }

      protected List<ItemOverride> func_187964_a(JsonDeserializationContext p_187964_1_, JsonObject p_187964_2_) {
         List<ItemOverride> list = Lists.<ItemOverride>newArrayList();
         if (p_187964_2_.has("overrides")) {
            for(JsonElement jsonelement : JsonUtils.func_151214_t(p_187964_2_, "overrides")) {
               list.add((ItemOverride)p_187964_1_.deserialize(jsonelement, ItemOverride.class));
            }
         }

         return list;
      }

      private Map<String, String> func_178329_b(JsonObject p_178329_1_) {
         Map<String, String> map = Maps.<String, String>newHashMap();
         if (p_178329_1_.has("textures")) {
            JsonObject jsonobject = p_178329_1_.getAsJsonObject("textures");

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               map.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }
         }

         return map;
      }

      private String func_178326_c(JsonObject p_178326_1_) {
         return JsonUtils.func_151219_a(p_178326_1_, "parent", "");
      }

      protected boolean func_178328_a(JsonObject p_178328_1_) {
         return JsonUtils.func_151209_a(p_178328_1_, "ambientocclusion", true);
      }

      protected List<BlockPart> func_178325_a(JsonDeserializationContext p_178325_1_, JsonObject p_178325_2_) {
         List<BlockPart> list = Lists.<BlockPart>newArrayList();
         if (p_178325_2_.has("elements")) {
            for(JsonElement jsonelement : JsonUtils.func_151214_t(p_178325_2_, "elements")) {
               list.add((BlockPart)p_178325_1_.deserialize(jsonelement, BlockPart.class));
            }
         }

         return list;
      }
   }

   public static class LoopException extends RuntimeException {
   }
}
