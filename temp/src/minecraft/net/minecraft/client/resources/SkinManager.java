package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
   private static final ExecutorService field_152794_b = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
   private final TextureManager field_152795_c;
   private final File field_152796_d;
   private final MinecraftSessionService field_152797_e;
   private final LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>> field_152798_f;

   public SkinManager(TextureManager p_i1044_1_, File p_i1044_2_, MinecraftSessionService p_i1044_3_) {
      this.field_152795_c = p_i1044_1_;
      this.field_152796_d = p_i1044_2_;
      this.field_152797_e = p_i1044_3_;
      this.field_152798_f = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).<GameProfile, Map<Type, MinecraftProfileTexture>>build(new CacheLoader<GameProfile, Map<Type, MinecraftProfileTexture>>() {
         public Map<Type, MinecraftProfileTexture> load(GameProfile p_load_1_) throws Exception {
            try {
               return Minecraft.func_71410_x().func_152347_ac().getTextures(p_load_1_, false);
            } catch (Throwable var3) {
               return Maps.<Type, MinecraftProfileTexture>newHashMap();
            }
         }
      });
   }

   public ResourceLocation func_152792_a(MinecraftProfileTexture p_152792_1_, Type p_152792_2_) {
      return this.func_152789_a(p_152792_1_, p_152792_2_, (SkinManager.SkinAvailableCallback)null);
   }

   public ResourceLocation func_152789_a(final MinecraftProfileTexture p_152789_1_, final Type p_152789_2_, @Nullable final SkinManager.SkinAvailableCallback p_152789_3_) {
      final ResourceLocation resourcelocation = new ResourceLocation("skins/" + p_152789_1_.getHash());
      ITextureObject itextureobject = this.field_152795_c.func_110581_b(resourcelocation);
      if (itextureobject != null) {
         if (p_152789_3_ != null) {
            p_152789_3_.func_180521_a(p_152789_2_, resourcelocation, p_152789_1_);
         }
      } else {
         File file1 = new File(this.field_152796_d, p_152789_1_.getHash().length() > 2 ? p_152789_1_.getHash().substring(0, 2) : "xx");
         File file2 = new File(file1, p_152789_1_.getHash());
         final IImageBuffer iimagebuffer = p_152789_2_ == Type.SKIN ? new ImageBufferDownload() : null;
         ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(file2, p_152789_1_.getUrl(), DefaultPlayerSkin.func_177335_a(), new IImageBuffer() {
            public BufferedImage func_78432_a(BufferedImage p_78432_1_) {
               if (iimagebuffer != null) {
                  p_78432_1_ = iimagebuffer.func_78432_a(p_78432_1_);
               }

               return p_78432_1_;
            }

            public void func_152634_a() {
               if (iimagebuffer != null) {
                  iimagebuffer.func_152634_a();
               }

               if (p_152789_3_ != null) {
                  p_152789_3_.func_180521_a(p_152789_2_, resourcelocation, p_152789_1_);
               }

            }
         });
         this.field_152795_c.func_110579_a(resourcelocation, threaddownloadimagedata);
      }

      return resourcelocation;
   }

   public void func_152790_a(final GameProfile p_152790_1_, final SkinManager.SkinAvailableCallback p_152790_2_, final boolean p_152790_3_) {
      field_152794_b.submit(new Runnable() {
         public void run() {
            final Map<Type, MinecraftProfileTexture> map = Maps.<Type, MinecraftProfileTexture>newHashMap();

            try {
               map.putAll(SkinManager.this.field_152797_e.getTextures(p_152790_1_, p_152790_3_));
            } catch (InsecureTextureException var3) {
               ;
            }

            if (map.isEmpty() && p_152790_1_.getId().equals(Minecraft.func_71410_x().func_110432_I().func_148256_e().getId())) {
               p_152790_1_.getProperties().clear();
               p_152790_1_.getProperties().putAll(Minecraft.func_71410_x().func_181037_M());
               map.putAll(SkinManager.this.field_152797_e.getTextures(p_152790_1_, false));
            }

            Minecraft.func_71410_x().func_152344_a(new Runnable() {
               public void run() {
                  if (map.containsKey(Type.SKIN)) {
                     SkinManager.this.func_152789_a(map.get(Type.SKIN), Type.SKIN, p_152790_2_);
                  }

                  if (map.containsKey(Type.CAPE)) {
                     SkinManager.this.func_152789_a(map.get(Type.CAPE), Type.CAPE, p_152790_2_);
                  }

               }
            });
         }
      });
   }

   public Map<Type, MinecraftProfileTexture> func_152788_a(GameProfile p_152788_1_) {
      return (Map)this.field_152798_f.getUnchecked(p_152788_1_);
   }

   public interface SkinAvailableCallback {
      void func_180521_a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
   }
}
