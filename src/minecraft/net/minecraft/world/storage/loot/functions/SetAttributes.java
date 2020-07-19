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

public class SetAttributes extends LootFunction
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final SetAttributes.Modifier[] modifiers;

    public SetAttributes(LootCondition[] conditionsIn, SetAttributes.Modifier[] modifiersIn)
    {
        super(conditionsIn);
        this.modifiers = modifiersIn;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
        for (SetAttributes.Modifier setattributes$modifier : this.modifiers)
        {
            UUID uuid = setattributes$modifier.uuid;

            if (uuid == null)
            {
                uuid = UUID.randomUUID();
            }

            EntityEquipmentSlot entityequipmentslot = setattributes$modifier.slots[rand.nextInt(setattributes$modifier.slots.length)];
            stack.addAttributeModifier(setattributes$modifier.attributeName, new AttributeModifier(uuid, setattributes$modifier.modifierName, (double)setattributes$modifier.amount.generateFloat(rand), setattributes$modifier.operation), entityequipmentslot);
        }

        return stack;
    }

    static class Modifier
    {
        private final String modifierName;
        private final String attributeName;
        private final int operation;
        private final RandomValueRange amount;
        @Nullable
        private final UUID uuid;
        private final EntityEquipmentSlot[] slots;

        private Modifier(String modifName, String attrName, int operationIn, RandomValueRange randomAmount, EntityEquipmentSlot[] slotsIn, @Nullable UUID uuidIn)
        {
            this.modifierName = modifName;
            this.attributeName = attrName;
            this.operation = operationIn;
            this.amount = randomAmount;
            this.uuid = uuidIn;
            this.slots = slotsIn;
        }

        public JsonObject serialize(JsonSerializationContext context)
        {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("name", this.modifierName);
            jsonobject.addProperty("attribute", this.attributeName);
            jsonobject.addProperty("operation", getOperationFromStr(this.operation));
            jsonobject.add("amount", context.serialize(this.amount));

            if (this.uuid != null)
            {
                jsonobject.addProperty("id", this.uuid.toString());
            }

            if (this.slots.length == 1)
            {
                jsonobject.addProperty("slot", this.slots[0].getName());
            }
            else
            {
                JsonArray jsonarray = new JsonArray();

                for (EntityEquipmentSlot entityequipmentslot : this.slots)
                {
                    jsonarray.add(new JsonPrimitive(entityequipmentslot.getName()));
                }

                jsonobject.add("slot", jsonarray);
            }

            return jsonobject;
        }

        public static SetAttributes.Modifier deserialize(JsonObject jsonObj, JsonDeserializationContext context)
        {
            String s = JsonUtils.getString(jsonObj, "name");
            String s1 = JsonUtils.getString(jsonObj, "attribute");
            int i = getOperationFromInt(JsonUtils.getString(jsonObj, "operation"));
            RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.deserializeClass(jsonObj, "amount", context, RandomValueRange.class);
            UUID uuid = null;
            EntityEquipmentSlot[] aentityequipmentslot;

            if (JsonUtils.isString(jsonObj, "slot"))
            {
                aentityequipmentslot = new EntityEquipmentSlot[] {EntityEquipmentSlot.fromString(JsonUtils.getString(jsonObj, "slot"))};
            }
            else
            {
                if (!JsonUtils.isJsonArray(jsonObj, "slot"))
                {
                    throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
                }

                JsonArray jsonarray = JsonUtils.getJsonArray(jsonObj, "slot");
                aentityequipmentslot = new EntityEquipmentSlot[jsonarray.size()];
                int j = 0;

                for (JsonElement jsonelement : jsonarray)
                {
                    aentityequipmentslot[j++] = EntityEquipmentSlot.fromString(JsonUtils.getString(jsonelement, "slot"));
                }

                if (aentityequipmentslot.length == 0)
                {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            }

            if (jsonObj.has("id"))
            {
                String s2 = JsonUtils.getString(jsonObj, "id");

                try
                {
                    uuid = UUID.fromString(s2);
                }
                catch (IllegalArgumentException var12)
                {
                    throw new JsonSyntaxException("Invalid attribute modifier id '" + s2 + "' (must be UUID format, with dashes)");
                }
            }

            return new SetAttributes.Modifier(s, s1, i, randomvaluerange, aentityequipmentslot, uuid);
        }

        private static String getOperationFromStr(int operationIn)
        {
            switch (operationIn)
            {
                case 0:
                    return "addition";

                case 1:
                    return "multiply_base";

                case 2:
                    return "multiply_total";

                default:
                    throw new IllegalArgumentException("Unknown operation " + operationIn);
            }
        }

        private static int getOperationFromInt(String operationIn)
        {
            if ("addition".equals(operationIn))
            {
                return 0;
            }
            else if ("multiply_base".equals(operationIn))
            {
                return 1;
            }
            else if ("multiply_total".equals(operationIn))
            {
                return 2;
            }
            else
            {
                throw new JsonSyntaxException("Unknown attribute modifier operation " + operationIn);
            }
        }
    }

    public static class Serializer extends LootFunction.Serializer<SetAttributes>
    {
        public Serializer()
        {
            super(new ResourceLocation("set_attributes"), SetAttributes.class);
        }

        public void serialize(JsonObject object, SetAttributes functionClazz, JsonSerializationContext serializationContext)
        {
            JsonArray jsonarray = new JsonArray();

            for (SetAttributes.Modifier setattributes$modifier : functionClazz.modifiers)
            {
                jsonarray.add(setattributes$modifier.serialize(serializationContext));
            }

            object.add("modifiers", jsonarray);
        }

        public SetAttributes deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
        {
            JsonArray jsonarray = JsonUtils.getJsonArray(object, "modifiers");
            SetAttributes.Modifier[] asetattributes$modifier = new SetAttributes.Modifier[jsonarray.size()];
            int i = 0;

            for (JsonElement jsonelement : jsonarray)
            {
                asetattributes$modifier[i++] = SetAttributes.Modifier.deserialize(JsonUtils.getJsonObject(jsonelement, "modifier"), deserializationContext);
            }

            if (asetattributes$modifier.length == 0)
            {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            }
            else
            {
                return new SetAttributes(conditionsIn, asetattributes$modifier);
            }
        }
    }
}
