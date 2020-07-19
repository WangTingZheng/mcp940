package net.minecraft.world.storage.loot;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON_INSTANCE = (new GsonBuilder()).registerTypeAdapter(RandomValueRange.class, new RandomValueRange.Serializer()).registerTypeAdapter(LootPool.class, new LootPool.Serializer()).registerTypeAdapter(LootTable.class, new LootTable.Serializer()).registerTypeHierarchyAdapter(LootEntry.class, new LootEntry.Serializer()).registerTypeHierarchyAdapter(LootFunction.class, new LootFunctionManager.Serializer()).registerTypeHierarchyAdapter(LootCondition.class, new LootConditionManager.Serializer()).registerTypeHierarchyAdapter(LootContext.EntityTarget.class, new LootContext.EntityTarget.Serializer()).create();
    private final LoadingCache<ResourceLocation, LootTable> registeredLootTables = CacheBuilder.newBuilder().<ResourceLocation, LootTable>build(new LootTableManager.Loader());
    private final File baseFolder;

    public LootTableManager(@Nullable File folder)
    {
        this.baseFolder = folder;
        this.reloadLootTables();
    }

    public LootTable getLootTableFromLocation(ResourceLocation ressources)
    {
        return this.registeredLootTables.getUnchecked(ressources);
    }

    public void reloadLootTables()
    {
        this.registeredLootTables.invalidateAll();

        for (ResourceLocation resourcelocation : LootTableList.getAll())
        {
            this.getLootTableFromLocation(resourcelocation);
        }
    }

    class Loader extends CacheLoader<ResourceLocation, LootTable>
    {
        private Loader()
        {
        }

        public LootTable load(ResourceLocation p_load_1_) throws Exception
        {
            if (p_load_1_.getResourcePath().contains("."))
            {
                LootTableManager.LOGGER.debug("Invalid loot table name '{}' (can't contain periods)", (Object)p_load_1_);
                return LootTable.EMPTY_LOOT_TABLE;
            }
            else
            {
                LootTable loottable = this.loadLootTable(p_load_1_);

                if (loottable == null)
                {
                    loottable = this.loadBuiltinLootTable(p_load_1_);
                }

                if (loottable == null)
                {
                    loottable = LootTable.EMPTY_LOOT_TABLE;
                    LootTableManager.LOGGER.warn("Couldn't find resource table {}", (Object)p_load_1_);
                }

                return loottable;
            }
        }

        @Nullable
        private LootTable loadLootTable(ResourceLocation resource)
        {
            if (LootTableManager.this.baseFolder == null)
            {
                return null;
            }
            else
            {
                File file1 = new File(new File(LootTableManager.this.baseFolder, resource.getResourceDomain()), resource.getResourcePath() + ".json");

                if (file1.exists())
                {
                    if (file1.isFile())
                    {
                        String s;

                        try
                        {
                            s = Files.toString(file1, StandardCharsets.UTF_8);
                        }
                        catch (IOException ioexception)
                        {
                            LootTableManager.LOGGER.warn("Couldn't load loot table {} from {}", resource, file1, ioexception);
                            return LootTable.EMPTY_LOOT_TABLE;
                        }

                        try
                        {
                            return (LootTable)JsonUtils.gsonDeserialize(LootTableManager.GSON_INSTANCE, s, LootTable.class);
                        }
                        catch (IllegalArgumentException | JsonParseException jsonparseexception)
                        {
                            LootTableManager.LOGGER.error("Couldn't load loot table {} from {}", resource, file1, jsonparseexception);
                            return LootTable.EMPTY_LOOT_TABLE;
                        }
                    }
                    else
                    {
                        LootTableManager.LOGGER.warn("Expected to find loot table {} at {} but it was a folder.", resource, file1);
                        return LootTable.EMPTY_LOOT_TABLE;
                    }
                }
                else
                {
                    return null;
                }
            }
        }

        @Nullable
        private LootTable loadBuiltinLootTable(ResourceLocation resource)
        {
            URL url = LootTableManager.class.getResource("/assets/" + resource.getResourceDomain() + "/loot_tables/" + resource.getResourcePath() + ".json");

            if (url != null)
            {
                String s;

                try
                {
                    s = Resources.toString(url, StandardCharsets.UTF_8);
                }
                catch (IOException ioexception)
                {
                    LootTableManager.LOGGER.warn("Couldn't load loot table {} from {}", resource, url, ioexception);
                    return LootTable.EMPTY_LOOT_TABLE;
                }

                try
                {
                    return (LootTable)JsonUtils.gsonDeserialize(LootTableManager.GSON_INSTANCE, s, LootTable.class);
                }
                catch (JsonParseException jsonparseexception)
                {
                    LootTableManager.LOGGER.error("Couldn't load loot table {} from {}", resource, url, jsonparseexception);
                    return LootTable.EMPTY_LOOT_TABLE;
                }
            }
            else
            {
                return null;
            }
        }
    }
}
