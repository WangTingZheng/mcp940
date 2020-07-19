package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).registerTypeHierarchyAdapter(Advancement.Builder.class, new JsonDeserializer<Advancement.Builder>()
    {
        public Advancement.Builder deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "advancement");
            return Advancement.Builder.deserialize(jsonobject, p_deserialize_3_);
        }
    }).registerTypeAdapter(AdvancementRewards.class, new AdvancementRewards.Deserializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
    private static final AdvancementList ADVANCEMENT_LIST = new AdvancementList();

    /** The directory where this manager looks for custom advancement files. */
    private final File advancementsDir;
    private boolean hasErrored;

    public AdvancementManager(@Nullable File advancementsDirIn)
    {
        this.advancementsDir = advancementsDirIn;
        this.reload();
    }

    public void reload()
    {
        this.hasErrored = false;
        ADVANCEMENT_LIST.clear();
        Map<ResourceLocation, Advancement.Builder> map = this.loadCustomAdvancements();
        this.loadBuiltInAdvancements(map);
        ADVANCEMENT_LIST.loadAdvancements(map);

        for (Advancement advancement : ADVANCEMENT_LIST.getRoots())
        {
            if (advancement.getDisplay() != null)
            {
                AdvancementTreeNode.layout(advancement);
            }
        }
    }

    public boolean hasErrored()
    {
        return this.hasErrored;
    }

    private Map<ResourceLocation, Advancement.Builder> loadCustomAdvancements()
    {
        if (this.advancementsDir == null)
        {
            return Maps.<ResourceLocation, Advancement.Builder>newHashMap();
        }
        else
        {
            Map<ResourceLocation, Advancement.Builder> map = Maps.<ResourceLocation, Advancement.Builder>newHashMap();
            this.advancementsDir.mkdirs();

            for (File file1 : FileUtils.listFiles(this.advancementsDir, new String[] {"json"}, true))
            {
                String s = FilenameUtils.removeExtension(this.advancementsDir.toURI().relativize(file1.toURI()).toString());
                String[] astring = s.split("/", 2);

                if (astring.length == 2)
                {
                    ResourceLocation resourcelocation = new ResourceLocation(astring[0], astring[1]);

                    try
                    {
                        Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.gsonDeserialize(GSON, FileUtils.readFileToString(file1, StandardCharsets.UTF_8), Advancement.Builder.class);

                        if (advancement$builder == null)
                        {
                            LOGGER.error("Couldn't load custom advancement " + resourcelocation + " from " + file1 + " as it's empty or null");
                        }
                        else
                        {
                            map.put(resourcelocation, advancement$builder);
                        }
                    }
                    catch (IllegalArgumentException | JsonParseException jsonparseexception)
                    {
                        LOGGER.error("Parsing error loading custom advancement " + resourcelocation, (Throwable)jsonparseexception);
                        this.hasErrored = true;
                    }
                    catch (IOException ioexception)
                    {
                        LOGGER.error("Couldn't read custom advancement " + resourcelocation + " from " + file1, (Throwable)ioexception);
                        this.hasErrored = true;
                    }
                }
            }
            return map;
        }
    }

    private void loadBuiltInAdvancements(Map<ResourceLocation, Advancement.Builder> map)
    {
        FileSystem filesystem = null;

        try
        {
            URL url = AdvancementManager.class.getResource("/assets/.mcassetsroot");

            if (url != null)
            {
                URI uri = url.toURI();
                Path path;

                if ("file".equals(uri.getScheme()))
                {
                    path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/advancements").toURI());
                }
                else
                {
                    if (!"jar".equals(uri.getScheme()))
                    {
                        LOGGER.error("Unsupported scheme " + uri + " trying to list all built-in advancements (NYI?)");
                        this.hasErrored = true;
                        return;
                    }

                    filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    path = filesystem.getPath("/assets/minecraft/advancements");
                }

                Iterator<Path> iterator = Files.walk(path).iterator();

                while (iterator.hasNext())
                {
                    Path path1 = iterator.next();

                    if ("json".equals(FilenameUtils.getExtension(path1.toString())))
                    {
                        Path path2 = path.relativize(path1);
                        String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
                        ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);

                        if (!map.containsKey(resourcelocation))
                        {
                            BufferedReader bufferedreader = null;

                            try
                            {
                                bufferedreader = Files.newBufferedReader(path1);
                                Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.fromJson(GSON, bufferedreader, Advancement.Builder.class);
                                map.put(resourcelocation, advancement$builder);
                            }
                            catch (JsonParseException jsonparseexception)
                            {
                                LOGGER.error("Parsing error loading built-in advancement " + resourcelocation, (Throwable)jsonparseexception);
                                this.hasErrored = true;
                            }
                            catch (IOException ioexception)
                            {
                                LOGGER.error("Couldn't read advancement " + resourcelocation + " from " + path1, (Throwable)ioexception);
                                this.hasErrored = true;
                            }
                            finally
                            {
                                IOUtils.closeQuietly((Reader)bufferedreader);
                            }
                        }
                    }
                }

                return;
            }

            LOGGER.error("Couldn't find .mcassetsroot");
            this.hasErrored = true;
        }
        catch (IOException | URISyntaxException urisyntaxexception)
        {
            LOGGER.error("Couldn't get a list of all built-in advancement files", (Throwable)urisyntaxexception);
            this.hasErrored = true;
            return;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)filesystem);
        }
    }

    @Nullable
    public Advancement getAdvancement(ResourceLocation id)
    {
        return ADVANCEMENT_LIST.getAdvancement(id);
    }

    public Iterable<Advancement> getAdvancements()
    {
        return ADVANCEMENT_LIST.getAdvancements();
    }
}
