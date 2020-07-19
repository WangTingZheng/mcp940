package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryTable extends LootEntry
{
    protected final ResourceLocation table;

    public LootEntryTable(ResourceLocation tableIn, int weightIn, int qualityIn, LootCondition[] conditionsIn)
    {
        super(weightIn, qualityIn, conditionsIn);
        this.table = tableIn;
    }

    public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context)
    {
        LootTable loottable = context.getLootTableManager().getLootTableFromLocation(this.table);
        Collection<ItemStack> collection = loottable.generateLootForPools(rand, context);
        stacks.addAll(collection);
    }

    protected void serialize(JsonObject json, JsonSerializationContext context)
    {
        json.addProperty("name", this.table.toString());
    }

    public static LootEntryTable deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn)
    {
        ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(object, "name"));
        return new LootEntryTable(resourcelocation, weightIn, qualityIn, conditionsIn);
    }
}
