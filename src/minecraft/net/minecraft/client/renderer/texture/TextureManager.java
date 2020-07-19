package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.src.Config;
import net.minecraft.src.RandomMobs;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadersmod.client.ShadersTex;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation RESOURCE_LOCATION_EMPTY = new ResourceLocation("");
    private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.<ResourceLocation, ITextureObject>newHashMap();
    private final List<ITickable> listTickables = Lists.<ITickable>newArrayList();
    private final Map<String, Integer> mapTextureCounters = Maps.<String, Integer>newHashMap();
    private final IResourceManager resourceManager;

    public TextureManager(IResourceManager resourceManager)
    {
        this.resourceManager = resourceManager;
    }

    public void bindTexture(ResourceLocation resource)
    {
        if (Config.isRandomMobs())
        {
            resource = RandomMobs.getTextureLocation(resource);
        }

        ITextureObject itextureobject = this.mapTextureObjects.get(resource);

        if (itextureobject == null)
        {
            itextureobject = new SimpleTexture(resource);
            this.loadTexture(resource, itextureobject);
        }

        if (Config.isShaders())
        {
            ShadersTex.bindTexture(itextureobject);
        }
        else
        {
            TextureUtil.bindTexture(itextureobject.getGlTextureId());
        }
    }

    public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj)
    {
        if (this.loadTexture(textureLocation, textureObj))
        {
            this.listTickables.add(textureObj);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj)
    {
        boolean flag = true;

        try
        {
            textureObj.loadTexture(this.resourceManager);
        }
        catch (IOException ioexception)
        {
            if (textureLocation != RESOURCE_LOCATION_EMPTY)
            {
                LOGGER.warn("Failed to load texture: {}", textureLocation, ioexception);
            }

            textureObj = TextureUtil.MISSING_TEXTURE;
            this.mapTextureObjects.put(textureLocation, textureObj);
            flag = false;
        }
        catch (Throwable throwable)
        {
            final ITextureObject textureObjf = textureObj;
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", textureLocation);
            crashreportcategory.addDetail("Texture object class", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    return textureObjf.getClass().getName();
                }
            });
            throw new ReportedException(crashreport);
        }

        this.mapTextureObjects.put(textureLocation, textureObj);
        return flag;
    }

    public ITextureObject getTexture(ResourceLocation textureLocation)
    {
        return this.mapTextureObjects.get(textureLocation);
    }

    public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture)
    {
        if (name.equals("logo"))
        {
            texture = Config.getMojangLogoTexture(texture);
        }

        Integer integer = this.mapTextureCounters.get(name);

        if (integer == null)
        {
            integer = Integer.valueOf(1);
        }
        else
        {
            integer = integer.intValue() + 1;
        }

        this.mapTextureCounters.put(name, integer);
        ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", name, integer));
        this.loadTexture(resourcelocation, texture);
        return resourcelocation;
    }

    public void tick()
    {
        for (ITickable itickable : this.listTickables)
        {
            itickable.tick();
        }
    }

    public void deleteTexture(ResourceLocation textureLocation)
    {
        ITextureObject itextureobject = this.getTexture(textureLocation);

        if (itextureobject != null)
        {
            this.mapTextureObjects.remove(textureLocation);
            TextureUtil.deleteTexture(itextureobject.getGlTextureId());
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        Config.dbg("*** Reloading textures ***");
        Config.log("Resource packs: " + Config.getResourcePackNames());
        Iterator iterator = this.mapTextureObjects.keySet().iterator();

        while (iterator.hasNext())
        {
            ResourceLocation resourcelocation = (ResourceLocation)iterator.next();
            String s = resourcelocation.getResourcePath();

            if (s.startsWith("mcpatcher/") || s.startsWith("optifine/"))
            {
                ITextureObject itextureobject = this.mapTextureObjects.get(resourcelocation);

                if (itextureobject instanceof AbstractTexture)
                {
                    AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
                    abstracttexture.deleteGlTexture();
                }

                iterator.remove();
            }
        }

        Iterator<Entry<ResourceLocation, ITextureObject>> iterator1 = this.mapTextureObjects.entrySet().iterator();

        while (iterator1.hasNext())
        {
            Entry<ResourceLocation, ITextureObject> entry = (Entry)iterator1.next();
            ITextureObject itextureobject1 = entry.getValue();

            if (itextureobject1 == TextureUtil.MISSING_TEXTURE)
            {
                iterator1.remove();
            }
            else
            {
                this.loadTexture(entry.getKey(), itextureobject1);
            }
        }
    }

    public void reloadBannerTextures()
    {
        for (Entry<ResourceLocation, ITextureObject> entry : this.mapTextureObjects.entrySet())
        {
            ResourceLocation resourcelocation = entry.getKey();
            ITextureObject itextureobject = entry.getValue();

            if (itextureobject instanceof LayeredColorMaskTexture)
            {
                this.loadTexture(resourcelocation, itextureobject);
            }
        }
    }
}
