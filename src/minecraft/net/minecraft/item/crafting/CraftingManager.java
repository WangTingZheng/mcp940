package net.minecraft.item.crafting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CraftingManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static int nextAvailableId;
    public static final RegistryNamespaced<ResourceLocation, IRecipe> REGISTRY = new RegistryNamespaced<ResourceLocation, IRecipe>();

    public static boolean init()
    {
        try
        {
            register("armordye", new RecipesArmorDyes());
            register("bookcloning", new RecipeBookCloning());
            register("mapcloning", new RecipesMapCloning());
            register("mapextending", new RecipesMapExtending());
            register("fireworks", new RecipeFireworks());
            register("repairitem", new RecipeRepairItem());
            register("tippedarrow", new RecipeTippedArrow());
            register("bannerduplicate", new RecipesBanners.RecipeDuplicatePattern());
            register("banneraddpattern", new RecipesBanners.RecipeAddPattern());
            register("shielddecoration", new ShieldRecipes.Decoration());
            register("shulkerboxcoloring", new ShulkerBoxRecipes.ShulkerBoxColoring());
            return parseJsonRecipes();
        }
        catch (Throwable var1)
        {
            return false;
        }
    }

    private static boolean parseJsonRecipes()
    {
        FileSystem filesystem = null;
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        boolean flag1;

        try
        {
            URL url = CraftingManager.class.getResource("/assets/.mcassetsroot");

            if (url != null)
            {
                URI uri = url.toURI();
                Path path;

                if ("file".equals(uri.getScheme()))
                {
                    path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/recipes").toURI());
                }
                else
                {
                    if (!"jar".equals(uri.getScheme()))
                    {
                        LOGGER.error("Unsupported scheme " + uri + " trying to list all recipes");
                        boolean flag2 = false;
                        return flag2;
                    }

                    filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    path = filesystem.getPath("/assets/minecraft/recipes");
                }

                Iterator<Path> iterator = Files.walk(path).iterator();

                while (iterator.hasNext())
                {
                    Path path1 = iterator.next();

                    if ("json".equals(FilenameUtils.getExtension(path1.toString())))
                    {
                        Path path2 = path.relativize(path1);
                        String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
                        ResourceLocation resourcelocation = new ResourceLocation(s);
                        BufferedReader bufferedreader = null;

                        try
                        {
                            boolean flag;

                            try
                            {
                                bufferedreader = Files.newBufferedReader(path1);
                                register(s, parseRecipeJson((JsonObject)JsonUtils.fromJson(gson, bufferedreader, JsonObject.class)));
                            }
                            catch (JsonParseException jsonparseexception)
                            {
                                LOGGER.error("Parsing error loading recipe " + resourcelocation, (Throwable)jsonparseexception);
                                flag = false;
                                return flag;
                            }
                            catch (IOException ioexception)
                            {
                                LOGGER.error("Couldn't read recipe " + resourcelocation + " from " + path1, (Throwable)ioexception);
                                flag = false;
                                return flag;
                            }
                        }
                        finally
                        {
                            IOUtils.closeQuietly((Reader)bufferedreader);
                        }
                    }
                }

                return true;
            }

            LOGGER.error("Couldn't find .mcassetsroot");
            flag1 = false;
        }
        catch (IOException | URISyntaxException urisyntaxexception)
        {
            LOGGER.error("Couldn't get a list of all recipe files", (Throwable)urisyntaxexception);
            flag1 = false;
            return flag1;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)filesystem);
        }

        return flag1;
    }

    private static IRecipe parseRecipeJson(JsonObject p_193376_0_)
    {
        String s = JsonUtils.getString(p_193376_0_, "type");

        if ("crafting_shaped".equals(s))
        {
            return ShapedRecipes.deserialize(p_193376_0_);
        }
        else if ("crafting_shapeless".equals(s))
        {
            return ShapelessRecipes.deserialize(p_193376_0_);
        }
        else
        {
            throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
        }
    }

    public static void register(String name, IRecipe recipe)
    {
        register(new ResourceLocation(name), recipe);
    }

    public static void register(ResourceLocation name, IRecipe recipe)
    {
        if (REGISTRY.containsKey(name))
        {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + name);
        }
        else
        {
            REGISTRY.register(nextAvailableId++, name, recipe);
        }
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public static ItemStack findMatchingResult(InventoryCrafting p_82787_0_, World craftMatrix)
    {
        for (IRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(p_82787_0_, craftMatrix))
            {
                return irecipe.getCraftingResult(p_82787_0_);
            }
        }

        return ItemStack.EMPTY;
    }

    @Nullable
    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(craftMatrix, worldIn))
            {
                return irecipe;
            }
        }

        return null;
    }

    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting p_180303_0_, World craftMatrix)
    {
        for (IRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(p_180303_0_, craftMatrix))
            {
                return irecipe.getRemainingItems(p_180303_0_);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(p_180303_0_.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            nonnulllist.set(i, p_180303_0_.getStackInSlot(i));
        }

        return nonnulllist;
    }

    @Nullable
    public static IRecipe getRecipe(ResourceLocation name)
    {
        return REGISTRY.getObject(name);
    }

    public static int getIDForRecipe(IRecipe recipe)
    {
        return REGISTRY.getIDForObject(recipe);
    }

    @Nullable
    public static IRecipe getRecipeById(int id)
    {
        return REGISTRY.getObjectById(id);
    }
}
