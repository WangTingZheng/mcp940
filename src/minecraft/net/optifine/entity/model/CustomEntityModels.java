package net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomEntityModels
{
    private static boolean active = false;
    private static Map<Class, Render> originalEntityRenderMap = null;
    private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;

    public static void update()
    {
        Map<Class, Render> map = getEntityRenderMap();
        Map<Class, TileEntitySpecialRenderer> map1 = getTileEntityRenderMap();

        if (map == null)
        {
            Config.warn("Entity render map not found, custom entity models are DISABLED.");
        }
        else if (map1 == null)
        {
            Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
        }
        else
        {
            active = false;
            map.clear();
            map1.clear();
            map.putAll(originalEntityRenderMap);
            map1.putAll(originalTileEntityRenderMap);

            if (Config.isCustomEntityModels())
            {
                ResourceLocation[] aresourcelocation = getModelLocations();

                for (int i = 0; i < aresourcelocation.length; ++i)
                {
                    ResourceLocation resourcelocation = aresourcelocation[i];
                    Config.dbg("CustomEntityModel: " + resourcelocation.getResourcePath());
                    IEntityRenderer ientityrenderer = parseEntityRender(resourcelocation);

                    if (ientityrenderer != null)
                    {
                        Class oclass = ientityrenderer.getEntityClass();

                        if (oclass != null)
                        {
                            if (ientityrenderer instanceof Render)
                            {
                                map.put(oclass, (Render)ientityrenderer);
                            }
                            else if (ientityrenderer instanceof TileEntitySpecialRenderer)
                            {
                                map1.put(oclass, (TileEntitySpecialRenderer)ientityrenderer);
                            }
                            else
                            {
                                Config.warn("Unknown renderer type: " + ientityrenderer.getClass().getName());
                            }

                            active = true;
                        }
                    }
                }
            }
        }
    }

    private static Map<Class, Render> getEntityRenderMap()
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        Map<Class, Render> map = rendermanager.getEntityRenderMap();

        if (map == null)
        {
            return null;
        }
        else
        {
            if (originalEntityRenderMap == null)
            {
                originalEntityRenderMap = new HashMap<Class, Render>(map);
            }

            return map;
        }
    }

    private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap()
    {
        Map<Class, TileEntitySpecialRenderer> map = TileEntityRendererDispatcher.instance.renderers;

        if (originalTileEntityRenderMap == null)
        {
            originalTileEntityRenderMap = new HashMap<Class, TileEntitySpecialRenderer>(map);
        }

        return map;
    }

    private static ResourceLocation[] getModelLocations()
    {
        String s = "optifine/cem/";
        String s1 = ".jem";
        List<ResourceLocation> list = new ArrayList<ResourceLocation>();
        String[] astring = CustomModelRegistry.getModelNames();

        for (int i = 0; i < astring.length; ++i)
        {
            String s2 = astring[i];
            String s3 = s + s2 + s1;
            ResourceLocation resourcelocation = new ResourceLocation(s3);

            if (Config.hasResource(resourcelocation))
            {
                list.add(resourcelocation);
            }
        }

        ResourceLocation[] aresourcelocation = (ResourceLocation[])list.toArray(new ResourceLocation[list.size()]);
        return aresourcelocation;
    }

    private static IEntityRenderer parseEntityRender(ResourceLocation location)
    {
        try
        {
            JsonObject jsonobject = CustomEntityModelParser.loadJson(location);
            IEntityRenderer ientityrenderer = parseEntityRender(jsonobject, location.getResourcePath());
            return ientityrenderer;
        }
        catch (IOException ioexception)
        {
            Config.error("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
            return null;
        }
        catch (JsonParseException jsonparseexception)
        {
            Config.error("" + jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
            return null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    private static IEntityRenderer parseEntityRender(JsonObject obj, String path)
    {
        CustomEntityRenderer customentityrenderer = CustomEntityModelParser.parseEntityRender(obj, path);
        String s = customentityrenderer.getName();
        ModelAdapter modeladapter = CustomModelRegistry.getModelAdapter(s);
        checkNull(modeladapter, "Entity not found: " + s);
        Class oclass = modeladapter.getEntityClass();
        checkNull(oclass, "Entity class not found: " + s);
        IEntityRenderer ientityrenderer = makeEntityRender(modeladapter, customentityrenderer);

        if (ientityrenderer == null)
        {
            return null;
        }
        else
        {
            ientityrenderer.setEntityClass(oclass);
            return ientityrenderer;
        }
    }

    private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer)
    {
        ResourceLocation resourcelocation = cer.getTextureLocation();
        CustomModelRenderer[] acustommodelrenderer = cer.getCustomModelRenderers();
        float f = cer.getShadowSize();

        if (f < 0.0F)
        {
            f = modelAdapter.getShadowSize();
        }

        ModelBase modelbase = modelAdapter.makeModel();

        if (modelbase == null)
        {
            return null;
        }
        else
        {
            ModelResolver modelresolver = new ModelResolver(modelAdapter, modelbase, acustommodelrenderer);

            if (!modifyModel(modelAdapter, modelbase, acustommodelrenderer, modelresolver))
            {
                return null;
            }
            else
            {
                IEntityRenderer ientityrenderer = modelAdapter.makeEntityRender(modelbase, f);

                if (ientityrenderer == null)
                {
                    throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
                }
                else
                {
                    if (resourcelocation != null)
                    {
                        ientityrenderer.setLocationTextureCustom(resourcelocation);
                    }

                    return ientityrenderer;
                }
            }
        }
    }

    private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] modelRenderers, ModelResolver mr)
    {
        for (int i = 0; i < modelRenderers.length; ++i)
        {
            CustomModelRenderer custommodelrenderer = modelRenderers[i];

            if (!modifyModel(modelAdapter, model, custommodelrenderer, mr))
            {
                return false;
            }
        }

        return true;
    }

    private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver)
    {
        String s = customModelRenderer.getModelPart();
        ModelRenderer modelrenderer = modelAdapter.getModelRenderer(model, s);

        if (modelrenderer == null)
        {
            Config.warn("Model part not found: " + s + ", model: " + model);
            return false;
        }
        else
        {
            if (!customModelRenderer.isAttach())
            {
                if (modelrenderer.cubeList != null)
                {
                    modelrenderer.cubeList.clear();
                }

                if (modelrenderer.spriteList != null)
                {
                    modelrenderer.spriteList.clear();
                }

                if (modelrenderer.childModels != null)
                {
                    modelrenderer.childModels.clear();
                }
            }

            modelrenderer.addChild(customModelRenderer.getModelRenderer());
            ModelUpdater modelupdater = customModelRenderer.getModelUpdater();

            if (modelupdater != null)
            {
                modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
                modelResolver.setPartModelRenderer(modelrenderer);

                if (!modelupdater.initialize(modelResolver))
                {
                    return false;
                }

                customModelRenderer.getModelRenderer().setModelUpdater(modelupdater);
            }

            return true;
        }
    }

    private static void checkNull(Object obj, String msg)
    {
        if (obj == null)
        {
            throw new JsonParseException(msg);
        }
    }

    public static boolean isActive()
    {
        return active;
    }
}
