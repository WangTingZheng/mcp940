package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public abstract class LootEntry {
   protected final int field_186364_c;
   protected final int field_186365_d;
   protected final LootCondition[] field_186366_e;

   protected LootEntry(int p_i46642_1_, int p_i46642_2_, LootCondition[] p_i46642_3_) {
      this.field_186364_c = p_i46642_1_;
      this.field_186365_d = p_i46642_2_;
      this.field_186366_e = p_i46642_3_;
   }

   public int func_186361_a(float p_186361_1_) {
      return Math.max(MathHelper.func_76141_d((float)this.field_186364_c + (float)this.field_186365_d * p_186361_1_), 0);
   }

   public abstract void func_186363_a(Collection<ItemStack> var1, Random var2, LootContext var3);

   protected abstract void func_186362_a(JsonObject var1, JsonSerializationContext var2);

   public static class Serializer implements JsonDeserializer<LootEntry>, JsonSerializer<LootEntry> {
      public LootEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "loot item");
         String s = JsonUtils.func_151200_h(jsonobject, "type");
         int i = JsonUtils.func_151208_a(jsonobject, "weight", 1);
         int j = JsonUtils.func_151208_a(jsonobject, "quality", 0);
         LootCondition[] alootcondition;
         if (jsonobject.has("conditions")) {
            alootcondition = (LootCondition[])JsonUtils.func_188174_a(jsonobject, "conditions", p_deserialize_3_, LootCondition[].class);
         } else {
            alootcondition = new LootCondition[0];
         }

         if ("item".equals(s)) {
            return LootEntryItem.func_186367_a(jsonobject, p_deserialize_3_, i, j, alootcondition);
         } else if ("loot_table".equals(s)) {
            return LootEntryTable.func_186370_a(jsonobject, p_deserialize_3_, i, j, alootcondition);
         } else if ("empty".equals(s)) {
            return LootEntryEmpty.func_186372_a(jsonobject, p_deserialize_3_, i, j, alootcondition);
         } else {
            throw new JsonSyntaxException("Unknown loot entry type '" + s + "'");
         }
      }

      public JsonElement serialize(LootEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("weight", Integer.valueOf(p_serialize_1_.field_186364_c));
         jsonobject.addProperty("quality", Integer.valueOf(p_serialize_1_.field_186365_d));
         if (p_serialize_1_.field_186366_e.length > 0) {
            jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.field_186366_e));
         }

         if (p_serialize_1_ instanceof LootEntryItem) {
            jsonobject.addProperty("type", "item");
         } else if (p_serialize_1_ instanceof LootEntryTable) {
            jsonobject.addProperty("type", "loot_table");
         } else {
            if (!(p_serialize_1_ instanceof LootEntryEmpty)) {
               throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_);
            }

            jsonobject.addProperty("type", "empty");
         }

         p_serialize_1_.func_186362_a(jsonobject, p_serialize_3_);
         return jsonobject;
      }
   }
}
