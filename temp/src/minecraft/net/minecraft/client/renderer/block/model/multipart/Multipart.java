package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.VariantList;

public class Multipart {
   private final List<Selector> field_188139_a;
   private BlockStateContainer field_188140_b;

   public Multipart(List<Selector> p_i46564_1_) {
      this.field_188139_a = p_i46564_1_;
   }

   public List<Selector> func_188136_a() {
      return this.field_188139_a;
   }

   public Set<VariantList> func_188137_b() {
      Set<VariantList> set = Sets.<VariantList>newHashSet();

      for(Selector selector : this.field_188139_a) {
         set.add(selector.func_188165_a());
      }

      return set;
   }

   public void func_188138_a(BlockStateContainer p_188138_1_) {
      this.field_188140_b = p_188138_1_;
   }

   public BlockStateContainer func_188135_c() {
      return this.field_188140_b;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof Multipart) {
            Multipart multipart = (Multipart)p_equals_1_;
            if (this.field_188139_a.equals(multipart.field_188139_a)) {
               if (this.field_188140_b == null) {
                  return multipart.field_188140_b == null;
               }

               return this.field_188140_b.equals(multipart.field_188140_b);
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return 31 * this.field_188139_a.hashCode() + (this.field_188140_b == null ? 0 : this.field_188140_b.hashCode());
   }

   public static class Deserializer implements JsonDeserializer<Multipart> {
      public Multipart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return new Multipart(this.func_188133_a(p_deserialize_3_, p_deserialize_1_.getAsJsonArray()));
      }

      private List<Selector> func_188133_a(JsonDeserializationContext p_188133_1_, JsonArray p_188133_2_) {
         List<Selector> list = Lists.<Selector>newArrayList();

         for(JsonElement jsonelement : p_188133_2_) {
            list.add((Selector)p_188133_1_.deserialize(jsonelement, Selector.class));
         }

         return list;
      }
   }
}
