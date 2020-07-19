package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import org.apache.commons.lang3.ArrayUtils;

public class LootPool {
   private final LootEntry[] field_186453_a;
   private final LootCondition[] field_186454_b;
   private final RandomValueRange field_186455_c;
   private final RandomValueRange field_186456_d;

   public LootPool(LootEntry[] p_i46643_1_, LootCondition[] p_i46643_2_, RandomValueRange p_i46643_3_, RandomValueRange p_i46643_4_) {
      this.field_186453_a = p_i46643_1_;
      this.field_186454_b = p_i46643_2_;
      this.field_186455_c = p_i46643_3_;
      this.field_186456_d = p_i46643_4_;
   }

   protected void func_186452_a(Collection<ItemStack> p_186452_1_, Random p_186452_2_, LootContext p_186452_3_) {
      List<LootEntry> list = Lists.<LootEntry>newArrayList();
      int i = 0;

      for(LootEntry lootentry : this.field_186453_a) {
         if (LootConditionManager.func_186638_a(lootentry.field_186366_e, p_186452_2_, p_186452_3_)) {
            int j = lootentry.func_186361_a(p_186452_3_.func_186491_f());
            if (j > 0) {
               list.add(lootentry);
               i += j;
            }
         }
      }

      if (i != 0 && !list.isEmpty()) {
         int k = p_186452_2_.nextInt(i);

         for(LootEntry lootentry1 : list) {
            k -= lootentry1.func_186361_a(p_186452_3_.func_186491_f());
            if (k < 0) {
               lootentry1.func_186363_a(p_186452_1_, p_186452_2_, p_186452_3_);
               return;
            }
         }

      }
   }

   public void func_186449_b(Collection<ItemStack> p_186449_1_, Random p_186449_2_, LootContext p_186449_3_) {
      if (LootConditionManager.func_186638_a(this.field_186454_b, p_186449_2_, p_186449_3_)) {
         int i = this.field_186455_c.func_186511_a(p_186449_2_) + MathHelper.func_76141_d(this.field_186456_d.func_186507_b(p_186449_2_) * p_186449_3_.func_186491_f());

         for(int j = 0; j < i; ++j) {
            this.func_186452_a(p_186449_1_, p_186449_2_, p_186449_3_);
         }

      }
   }

   public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
      public LootPool deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "loot pool");
         LootEntry[] alootentry = (LootEntry[])JsonUtils.func_188174_a(jsonobject, "entries", p_deserialize_3_, LootEntry[].class);
         LootCondition[] alootcondition = (LootCondition[])JsonUtils.func_188177_a(jsonobject, "conditions", new LootCondition[0], p_deserialize_3_, LootCondition[].class);
         RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.func_188174_a(jsonobject, "rolls", p_deserialize_3_, RandomValueRange.class);
         RandomValueRange randomvaluerange1 = (RandomValueRange)JsonUtils.func_188177_a(jsonobject, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), p_deserialize_3_, RandomValueRange.class);
         return new LootPool(alootentry, alootcondition, randomvaluerange, randomvaluerange1);
      }

      public JsonElement serialize(LootPool p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("entries", p_serialize_3_.serialize(p_serialize_1_.field_186453_a));
         jsonobject.add("rolls", p_serialize_3_.serialize(p_serialize_1_.field_186455_c));
         if (p_serialize_1_.field_186456_d.func_186509_a() != 0.0F && p_serialize_1_.field_186456_d.func_186512_b() != 0.0F) {
            jsonobject.add("bonus_rolls", p_serialize_3_.serialize(p_serialize_1_.field_186456_d));
         }

         if (!ArrayUtils.isEmpty((Object[])p_serialize_1_.field_186454_b)) {
            jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.field_186454_b));
         }

         return jsonobject;
      }
   }
}
