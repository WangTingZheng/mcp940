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

public class BannerTextures {
   public static final BannerTextures.Cache field_178466_c = new BannerTextures.Cache("B", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/");
   public static final BannerTextures.Cache field_187485_b = new BannerTextures.Cache("S", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/");
   public static final ResourceLocation field_187486_c = new ResourceLocation("textures/entity/shield_base_nopattern.png");
   public static final ResourceLocation field_187487_d = new ResourceLocation("textures/entity/banner/base.png");

   public static class Cache {
      private final Map<String, BannerTextures.CacheEntry> field_187479_a = Maps.<String, BannerTextures.CacheEntry>newLinkedHashMap();
      private final ResourceLocation field_187480_b;
      private final String field_187481_c;
      private final String field_187482_d;

      public Cache(String p_i46998_1_, ResourceLocation p_i46998_2_, String p_i46998_3_) {
         this.field_187482_d = p_i46998_1_;
         this.field_187480_b = p_i46998_2_;
         this.field_187481_c = p_i46998_3_;
      }

      @Nullable
      public ResourceLocation func_187478_a(String p_187478_1_, List<BannerPattern> p_187478_2_, List<EnumDyeColor> p_187478_3_) {
         if (p_187478_1_.isEmpty()) {
            return null;
         } else {
            p_187478_1_ = this.field_187482_d + p_187478_1_;
            BannerTextures.CacheEntry bannertextures$cacheentry = this.field_187479_a.get(p_187478_1_);
            if (bannertextures$cacheentry == null) {
               if (this.field_187479_a.size() >= 256 && !this.func_187477_a()) {
                  return BannerTextures.field_187487_d;
               }

               List<String> list = Lists.<String>newArrayList();

               for(BannerPattern bannerpattern : p_187478_2_) {
                  list.add(this.field_187481_c + bannerpattern.func_190997_a() + ".png");
               }

               bannertextures$cacheentry = new BannerTextures.CacheEntry();
               bannertextures$cacheentry.field_187484_b = new ResourceLocation(p_187478_1_);
               Minecraft.func_71410_x().func_110434_K().func_110579_a(bannertextures$cacheentry.field_187484_b, new LayeredColorMaskTexture(this.field_187480_b, list, p_187478_3_));
               this.field_187479_a.put(p_187478_1_, bannertextures$cacheentry);
            }

            bannertextures$cacheentry.field_187483_a = System.currentTimeMillis();
            return bannertextures$cacheentry.field_187484_b;
         }
      }

      private boolean func_187477_a() {
         long i = System.currentTimeMillis();
         Iterator<String> iterator = this.field_187479_a.keySet().iterator();

         while(iterator.hasNext()) {
            String s = iterator.next();
            BannerTextures.CacheEntry bannertextures$cacheentry = this.field_187479_a.get(s);
            if (i - bannertextures$cacheentry.field_187483_a > 5000L) {
               Minecraft.func_71410_x().func_110434_K().func_147645_c(bannertextures$cacheentry.field_187484_b);
               iterator.remove();
               return true;
            }
         }

         return this.field_187479_a.size() < 256;
      }
   }

   static class CacheEntry {
      public long field_187483_a;
      public ResourceLocation field_187484_b;

      private CacheEntry() {
      }
   }
}
