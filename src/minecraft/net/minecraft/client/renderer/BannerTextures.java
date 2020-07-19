package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;

public class BannerTextures
{
    /** An array of all the banner patterns that are being currently rendered */
    public static final BannerTextures.Cache BANNER_DESIGNS = new BannerTextures.Cache("B", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/");

    /** An array of all the shield patterns that are being currently rendered */
    public static final BannerTextures.Cache SHIELD_DESIGNS = new BannerTextures.Cache("S", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/");
    public static final ResourceLocation SHIELD_BASE_TEXTURE = new ResourceLocation("textures/entity/shield_base_nopattern.png");
    public static final ResourceLocation BANNER_BASE_TEXTURE = new ResourceLocation("textures/entity/banner/base.png");

    public static class Cache
    {
        private final Map<String, BannerTextures.CacheEntry> cacheMap = Maps.<String, BannerTextures.CacheEntry>newLinkedHashMap();
        private final ResourceLocation cacheResourceLocation;
        private final String cacheResourceBase;
        private final String cacheId;

        public Cache(String id, ResourceLocation baseResource, String resourcePath)
        {
            this.cacheId = id;
            this.cacheResourceLocation = baseResource;
            this.cacheResourceBase = resourcePath;
        }

        @Nullable
        public ResourceLocation getResourceLocation(String id, List<BannerPattern> patternList, List<EnumDyeColor> colorList)
        {
            if (id.isEmpty())
            {
                return null;
            }
            else
            {
                id = this.cacheId + id;
                BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(id);

                if (bannertextures$cacheentry == null)
                {
                    if (this.cacheMap.size() >= 256 && !this.freeCacheSlot())
                    {
                        return BannerTextures.BANNER_BASE_TEXTURE;
                    }

                    List<String> list = Lists.<String>newArrayList();

                    for (BannerPattern bannerpattern : patternList)
                    {
                        list.add(this.cacheResourceBase + bannerpattern.getFileName() + ".png");
                    }

                    bannertextures$cacheentry = new BannerTextures.CacheEntry();
                    bannertextures$cacheentry.textureLocation = new ResourceLocation(id);
                    Minecraft.getMinecraft().getTextureManager().loadTexture(bannertextures$cacheentry.textureLocation, new LayeredColorMaskTexture(this.cacheResourceLocation, list, colorList));
                    this.cacheMap.put(id, bannertextures$cacheentry);
                }

                bannertextures$cacheentry.lastUseMillis = System.currentTimeMillis();
                return bannertextures$cacheentry.textureLocation;
            }
        }

        private boolean freeCacheSlot()
        {
            long i = System.currentTimeMillis();
            Iterator<String> iterator = this.cacheMap.keySet().iterator();

            while (iterator.hasNext())
            {
                String s = iterator.next();
                BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(s);

                if (i - bannertextures$cacheentry.lastUseMillis > 5000L)
                {
                    Minecraft.getMinecraft().getTextureManager().deleteTexture(bannertextures$cacheentry.textureLocation);
                    iterator.remove();
                    return true;
                }
            }

            return this.cacheMap.size() < 256;
        }
    }

    static class CacheEntry
    {
        public long lastUseMillis;
        public ResourceLocation textureLocation;

        private CacheEntry()
        {
        }
    }
}
