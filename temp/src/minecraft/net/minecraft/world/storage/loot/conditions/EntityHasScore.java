package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;

public class EntityHasScore implements LootCondition {
   private final Map<String, RandomValueRange> field_186634_a;
   private final LootContext.EntityTarget field_186635_b;

   public EntityHasScore(Map<String, RandomValueRange> p_i46618_1_, LootContext.EntityTarget p_i46618_2_) {
      this.field_186634_a = p_i46618_1_;
      this.field_186635_b = p_i46618_2_;
   }

   public boolean func_186618_a(Random p_186618_1_, LootContext p_186618_2_) {
      Entity entity = p_186618_2_.func_186494_a(this.field_186635_b);
      if (entity == null) {
         return false;
      } else {
         Scoreboard scoreboard = entity.field_70170_p.func_96441_U();

         for(Entry<String, RandomValueRange> entry : this.field_186634_a.entrySet()) {
            if (!this.func_186631_a(entity, scoreboard, entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean func_186631_a(Entity p_186631_1_, Scoreboard p_186631_2_, String p_186631_3_, RandomValueRange p_186631_4_) {
      ScoreObjective scoreobjective = p_186631_2_.func_96518_b(p_186631_3_);
      if (scoreobjective == null) {
         return false;
      } else {
         String s = p_186631_1_ instanceof EntityPlayerMP ? p_186631_1_.func_70005_c_() : p_186631_1_.func_189512_bd();
         return !p_186631_2_.func_178819_b(s, scoreobjective) ? false : p_186631_4_.func_186510_a(p_186631_2_.func_96529_a(s, scoreobjective).func_96652_c());
      }
   }

   public static class Serializer extends LootCondition.Serializer<EntityHasScore> {
      protected Serializer() {
         super(new ResourceLocation("entity_scores"), EntityHasScore.class);
      }

      public void func_186605_a(JsonObject p_186605_1_, EntityHasScore p_186605_2_, JsonSerializationContext p_186605_3_) {
         JsonObject jsonobject = new JsonObject();

         for(Entry<String, RandomValueRange> entry : p_186605_2_.field_186634_a.entrySet()) {
            jsonobject.add(entry.getKey(), p_186605_3_.serialize(entry.getValue()));
         }

         p_186605_1_.add("scores", jsonobject);
         p_186605_1_.add("entity", p_186605_3_.serialize(p_186605_2_.field_186635_b));
      }

      public EntityHasScore func_186603_b(JsonObject p_186603_1_, JsonDeserializationContext p_186603_2_) {
         Set<Entry<String, JsonElement>> set = JsonUtils.func_152754_s(p_186603_1_, "scores").entrySet();
         Map<String, RandomValueRange> map = Maps.<String, RandomValueRange>newLinkedHashMap();

         for(Entry<String, JsonElement> entry : set) {
            map.put(entry.getKey(), JsonUtils.func_188179_a(entry.getValue(), "score", p_186603_2_, RandomValueRange.class));
         }

         return new EntityHasScore(map, (LootContext.EntityTarget)JsonUtils.func_188174_a(p_186603_1_, "entity", p_186603_2_, LootContext.EntityTarget.class));
      }
   }
}
