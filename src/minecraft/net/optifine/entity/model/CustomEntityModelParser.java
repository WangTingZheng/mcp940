package net.optifine.entity.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedParser;
import net.minecraft.src.Json;
import net.minecraft.src.PlayerItemParser;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;

public class CustomEntityModelParser
{
    public static final String ENTITY = "entity";
    public static final String TEXTURE = "texture";
    public static final String SHADOW_SIZE = "shadowSize";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_TEXTURE_SIZE = "textureSize";
    public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
    public static final String ITEM_MODELS = "models";
    public static final String ITEM_ANIMATIONS = "animations";
    public static final String MODEL_ID = "id";
    public static final String MODEL_BASE_ID = "baseId";
    public static final String MODEL_MODEL = "model";
    public static final String MODEL_TYPE = "type";
    public static final String MODEL_PART = "part";
    public static final String MODEL_ATTACH = "attach";
    public static final String MODEL_INVERT_AXIS = "invertAxis";
    public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
    public static final String MODEL_TRANSLATE = "translate";
    public static final String MODEL_ROTATE = "rotate";
    public static final String MODEL_SCALE = "scale";
    public static final String MODEL_BOXES = "boxes";
    public static final String MODEL_SPRITES = "sprites";
    public static final String MODEL_SUBMODEL = "submodel";
    public static final String MODEL_SUBMODELS = "submodels";
    public static final String BOX_TEXTURE_OFFSET = "textureOffset";
    public static final String BOX_COORDINATES = "coordinates";
    public static final String BOX_SIZE_ADD = "sizeAdd";
    public static final String ENTITY_MODEL = "EntityModel";
    public static final String ENTITY_MODEL_PART = "EntityModelPart";

    public static CustomEntityRenderer parseEntityRender(JsonObject obj, String path)
    {
        ConnectedParser connectedparser = new ConnectedParser("CustomEntityModels");
        String s = connectedparser.parseName(path);
        String s1 = connectedparser.parseBasePath(path);
        String s2 = Json.getString(obj, "texture");
        int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
        float f = Json.getFloat(obj, "shadowSize", -1.0F);
        JsonArray jsonarray = (JsonArray)obj.get("models");
        checkNull(jsonarray, "Missing models");
        Map map = new HashMap();
        List list = new ArrayList();

        for (int i = 0; i < jsonarray.size(); ++i)
        {
            JsonObject jsonobject = (JsonObject)jsonarray.get(i);
            processBaseId(jsonobject, map);
            processExternalModel(jsonobject, map, s1);
            processId(jsonobject, map);
            CustomModelRenderer custommodelrenderer = parseCustomModelRenderer(jsonobject, aint, s1);

            if (custommodelrenderer != null)
            {
                list.add(custommodelrenderer);
            }
        }

        CustomModelRenderer[] acustommodelrenderer = (CustomModelRenderer[])list.toArray(new CustomModelRenderer[list.size()]);
        ResourceLocation resourcelocation = null;

        if (s2 != null)
        {
            resourcelocation = getResourceLocation(s1, s2, ".png");
        }

        CustomEntityRenderer customentityrenderer = new CustomEntityRenderer(s, s1, resourcelocation, acustommodelrenderer, f);
        return customentityrenderer;
    }

    private static void processBaseId(JsonObject elem, Map mapModelJsons)
    {
        String s = Json.getString(elem, "baseId");

        if (s != null)
        {
            JsonObject jsonobject = (JsonObject)mapModelJsons.get(s);

            if (jsonobject == null)
            {
                Config.warn("BaseID not found: " + s);
            }
            else
            {
                copyJsonElements(jsonobject, elem);
            }
        }
    }

    private static void processExternalModel(JsonObject elem, Map mapModelJsons, String basePath)
    {
        String s = Json.getString(elem, "model");

        if (s != null)
        {
            ResourceLocation resourcelocation = getResourceLocation(basePath, s, ".jpm");

            try
            {
                JsonObject jsonobject = loadJson(resourcelocation);

                if (jsonobject == null)
                {
                    Config.warn("Model not found: " + resourcelocation);
                    return;
                }

                copyJsonElements(jsonobject, elem);
            }
            catch (IOException ioexception)
            {
                Config.error("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
            }
            catch (JsonParseException jsonparseexception)
            {
                Config.error("" + jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private static void copyJsonElements(JsonObject objFrom, JsonObject objTo)
    {
        for (Entry<String, JsonElement> entry : objFrom.entrySet())
        {
            if (!((String)entry.getKey()).equals("id") && !objTo.has(entry.getKey()))
            {
                objTo.add(entry.getKey(), entry.getValue());
            }
        }
    }

    public static ResourceLocation getResourceLocation(String basePath, String path, String extension)
    {
        if (!path.endsWith(extension))
        {
            path = path + extension;
        }

        if (!path.contains("/"))
        {
            path = basePath + "/" + path;
        }
        else if (path.startsWith("./"))
        {
            path = basePath + "/" + path.substring(2);
        }
        else if (path.startsWith("~/"))
        {
            path = "optifine/" + path.substring(2);
        }

        return new ResourceLocation(path);
    }

    private static void processId(JsonObject elem, Map mapModelJsons)
    {
        String s = Json.getString(elem, "id");

        if (s != null)
        {
            if (s.length() < 1)
            {
                Config.warn("Empty model ID: " + s);
            }
            else if (mapModelJsons.containsKey(s))
            {
                Config.warn("Duplicate model ID: " + s);
            }
            else
            {
                mapModelJsons.put(s, elem);
            }
        }
    }

    public static CustomModelRenderer parseCustomModelRenderer(JsonObject elem, int[] textureSize, String basePath)
    {
        String s = Json.getString(elem, "part");
        checkNull(s, "Model part not specified, missing \"replace\" or \"attachTo\".");
        boolean flag = Json.getBoolean(elem, "attach", false);
        ModelBase modelbase = new CustomEntityModel();

        if (textureSize != null)
        {
            modelbase.textureWidth = textureSize[0];
            modelbase.textureHeight = textureSize[1];
        }

        ModelUpdater modelupdater = null;
        JsonArray jsonarray = (JsonArray)elem.get("animations");

        if (jsonarray != null)
        {
            List<ModelVariableUpdater> list = new ArrayList<ModelVariableUpdater>();

            for (int i = 0; i < jsonarray.size(); ++i)
            {
                JsonObject jsonobject = (JsonObject)jsonarray.get(i);

                for (Entry<String, JsonElement> entry : jsonobject.entrySet())
                {
                    String s1 = entry.getKey();
                    String s2 = ((JsonElement)entry.getValue()).getAsString();
                    ModelVariableUpdater modelvariableupdater = new ModelVariableUpdater(s1, s2);
                    list.add(modelvariableupdater);
                }
            }

            if (list.size() > 0)
            {
                ModelVariableUpdater[] amodelvariableupdater = (ModelVariableUpdater[])list.toArray(new ModelVariableUpdater[list.size()]);
                modelupdater = new ModelUpdater(amodelvariableupdater);
            }
        }

        ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, textureSize, basePath);
        CustomModelRenderer custommodelrenderer = new CustomModelRenderer(s, flag, modelrenderer, modelupdater);
        return custommodelrenderer;
    }

    private static void checkNull(Object obj, String msg)
    {
        if (obj == null)
        {
            throw new JsonParseException(msg);
        }
    }

    public static JsonObject loadJson(ResourceLocation location) throws IOException, JsonParseException
    {
        InputStream inputstream = Config.getResourceStream(location);

        if (inputstream == null)
        {
            return null;
        }
        else
        {
            String s = Config.readInputStream(inputstream, "ASCII");
            inputstream.close();
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonobject = (JsonObject)jsonparser.parse(s);
            return jsonobject;
        }
    }
}
