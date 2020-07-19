package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetMetadata extends LootFunction
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final RandomValueRange metaRange;

    public SetMetadata(LootCondition[] conditionsIn, RandomValueRange metaRangeIn)
    {
        super(conditionsIn);
        this.metaRange = metaRangeIn;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
        if (stack.isItemStackDamageable())
        {
            LOGGER.warn("Couldn't set data of loot item {}", (Object)stack);
        }
        else
        {
            stack.setItemDamage(this.metaRange.generateInt(rand));
        }

        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<SetMetadata>
    {
        protected Serializer()
        {
            super(new ResourceLocation("set_data"), SetMetadata.class);
        }

        public void serialize(JsonObject object, SetMetadata functionClazz, JsonSerializationContext serializationContext)
        {
            object.add("data", serializationContext.serialize(functionClazz.metaRange));
        }

        public SetMetadata deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
        {
            return new SetMetadata(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "data", deserializationContext, RandomValueRange.class));
        }
    }
}
