package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetAttributes extends LootFunction {
   private static final Logger field_186560_a = LogManager.getLogger();
   private final SetAttributes.Modifier[] field_186561_b;

   public SetAttributes(LootCondition[] p_i46624_1_, SetAttributes.Modifier[] p_i46624_2_) {
      super(p_i46624_1_);
      this.field_186561_b = p_i46624_2_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      for(SetAttributes.Modifier setattributes$modifier : this.field_186561_b) {
         UUID uuid = setattributes$modifier.field_186600_e;
         if (uuid == null) {
            uuid = UUID.randomUUID();
         }

         EntityEquipmentSlot entityequipmentslot = setattributes$modifier.field_186601_f[p_186553_2_.nextInt(setattributes$modifier.field_186601_f.length)];
         p_186553_1_.func_185129_a(setattributes$modifier.field_186597_b, new AttributeModifier(uuid, setattributes$modifier.field_186596_a, (double)setattributes$modifier.field_186599_d.func_186507_b(p_186553_2_), setattributes$modifier.field_186598_c), entityequipmentslot);
      }

      return p_186553_1_;
   }

   static class Modifier {
      private final String field_186596_a;
      private final String field_186597_b;
      private final int field_186598_c;
      private final RandomValueRange field_186599_d;
      @Nullable
      private final UUID field_186600_e;
      private final EntityEquipmentSlot[] field_186601_f;

      private Modifier(String p_i46561_1_, String p_i46561_2_, int p_i46561_3_, RandomValueRange p_i46561_4_, EntityEquipmentSlot[] p_i46561_5_, @Nullable UUID p_i46561_6_) {
         this.field_186596_a = p_i46561_1_;
         this.field_186597_b = p_i46561_2_;
         this.field_186598_c = p_i46561_3_;
         this.field_186599_d = p_i46561_4_;
         this.field_186600_e = p_i46561_6_;
         this.field_186601_f = p_i46561_5_;
      }

      public JsonObject func_186592_a(JsonSerializationContext p_186592_1_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("name", this.field_186596_a);
         jsonobject.addProperty("attribute", this.field_186597_b);
         jsonobject.addProperty("operation", func_186594_a(this.field_186598_c));
         jsonobject.add("amount", p_186592_1_.serialize(this.field_186599_d));
         if (this.field_186600_e != null) {
            jsonobject.addProperty("id", this.field_186600_e.toString());
         }

         if (this.field_186601_f.length == 1) {
            jsonobject.addProperty("slot", this.field_186601_f[0].func_188450_d());
         } else {
            JsonArray jsonarray = new JsonArray();

            for(EntityEquipmentSlot entityequipmentslot : this.field_186601_f) {
               jsonarray.add(new JsonPrimitive(entityequipmentslot.func_188450_d()));
            }

            jsonobject.add("slot", jsonarray);
         }

         return jsonobject;
      }

      public static SetAttributes.Modifier func_186586_a(JsonObject p_186586_0_, JsonDeserializationContext p_186586_1_) {
         String s = JsonUtils.func_151200_h(p_186586_0_, "name");
         String s1 = JsonUtils.func_151200_h(p_186586_0_, "attribute");
         int i = func_186595_a(JsonUtils.func_151200_h(p_186586_0_, "operation"));
         RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.func_188174_a(p_186586_0_, "amount", p_186586_1_, RandomValueRange.class);
         UUID uuid = null;
         EntityEquipmentSlot[] aentityequipmentslot;
         if (JsonUtils.func_151205_a(p_186586_0_, "slot")) {
            aentityequipmentslot = new EntityEquipmentSlot[]{EntityEquipmentSlot.func_188451_a(JsonUtils.func_151200_h(p_186586_0_, "slot"))};
         } else {
            if (!JsonUtils.func_151202_d(p_186586_0_, "slot")) {
               throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
            }

            JsonArray jsonarray = JsonUtils.func_151214_t(p_186586_0_, "slot");
            aentityequipmentslot = new EntityEquipmentSlot[jsonarray.size()];
            int j = 0;

            for(JsonElement jsonelement : jsonarray) {
               aentityequipmentslot[j++] = EntityEquipmentSlot.func_188451_a(JsonUtils.func_151206_a(jsonelement, "slot"));
            }

            if (aentityequipmentslot.length == 0) {
               throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
            }
         }

         if (p_186586_0_.has("id")) {
            String s2 = JsonUtils.func_151200_h(p_186586_0_, "id");

            try {
               uuid = UUID.fromString(s2);
            } catch (IllegalArgumentException var12) {
               throw new JsonSyntaxException("Invalid attribute modifier id '" + s2 + "' (must be UUID format, with dashes)");
            }
         }

         return new SetAttributes.Modifier(s, s1, i, randomvaluerange, aentityequipmentslot, uuid);
      }

      private static String func_186594_a(int p_186594_0_) {
         switch(p_186594_0_) {
         case 0:
            return "addition";
         case 1:
            return "multiply_base";
         case 2:
            return "multiply_total";
         default:
            throw new IllegalArgumentException("Unknown operation " + p_186594_0_);
         }
      }

      private static int func_186595_a(String p_186595_0_) {
         if ("addition".equals(p_186595_0_)) {
            return 0;
         } else if ("multiply_base".equals(p_186595_0_)) {
            return 1;
         } else if ("multiply_total".equals(p_186595_0_)) {
            return 2;
         } else {
            throw new JsonSyntaxException("Unknown attribute modifier operation " + p_186595_0_);
         }
      }
   }

   public static class Serializer extends LootFunction.Serializer<SetAttributes> {
      public Serializer() {
         super(new ResourceLocation("set_attributes"), SetAttributes.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, SetAttributes p_186532_2_, JsonSerializationContext p_186532_3_) {
         JsonArray jsonarray = new JsonArray();

         for(SetAttributes.Modifier setattributes$modifier : p_186532_2_.field_186561_b) {
            jsonarray.add(setattributes$modifier.func_186592_a(p_186532_3_));
         }

         p_186532_1_.add("modifiers", jsonarray);
      }

      public SetAttributes func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         JsonArray jsonarray = JsonUtils.func_151214_t(p_186530_1_, "modifiers");
         SetAttributes.Modifier[] asetattributes$modifier = new SetAttributes.Modifier[jsonarray.size()];
         int i = 0;

         for(JsonElement jsonelement : jsonarray) {
            asetattributes$modifier[i++] = SetAttributes.Modifier.func_186586_a(JsonUtils.func_151210_l(jsonelement, "modifier"), p_186530_2_);
         }

         if (asetattributes$modifier.length == 0) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributes(p_186530_3_, asetattributes$modifier);
         }
      }
   }
}
