package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private static final Logger field_147635_d = LogManager.getLogger();
   public static final ResourceLocation field_174945_f = new ResourceLocation("missingno");
   public static final ResourceLocation field_110575_b = new ResourceLocation("textures/atlas/blocks.png");
   private final List<TextureAtlasSprite> field_94258_i;
   private final Map<String, TextureAtlasSprite> field_110574_e;
   private final Map<String, TextureAtlasSprite> field_94252_e;
   private final String field_94254_c;
   private final ITextureMapPopulator field_174946_m;
   private int field_147636_j;
   private final TextureAtlasSprite field_94249_f;

   public TextureMap(String p_i46099_1_) {
      this(p_i46099_1_, (ITextureMapPopulator)null);
   }

   public TextureMap(String p_i46100_1_, @Nullable ITextureMapPopulator p_i46100_2_) {
      this.field_94258_i = Lists.<TextureAtlasSprite>newArrayList();
      this.field_110574_e = Maps.<String, TextureAtlasSprite>newHashMap();
      this.field_94252_e = Maps.<String, TextureAtlasSprite>newHashMap();
      this.field_94249_f = new TextureAtlasSprite("missingno");
      this.field_94254_c = p_i46100_1_;
      this.field_174946_m = p_i46100_2_;
   }

   private void func_110569_e() {
      int[] aint = TextureUtil.field_110999_b;
      this.field_94249_f.func_110966_b(16);
      this.field_94249_f.func_110969_c(16);
      int[][] aint1 = new int[this.field_147636_j + 1][];
      aint1[0] = aint;
      this.field_94249_f.func_110968_a(Lists.newArrayList(aint1));
   }

   public void func_110551_a(IResourceManager p_110551_1_) throws IOException {
      if (this.field_174946_m != null) {
         this.func_174943_a(p_110551_1_, this.field_174946_m);
      }

   }

   public void func_174943_a(IResourceManager p_174943_1_, ITextureMapPopulator p_174943_2_) {
      this.field_110574_e.clear();
      p_174943_2_.func_177059_a(this);
      this.func_110569_e();
      this.func_147631_c();
      this.func_110571_b(p_174943_1_);
   }

   public void func_110571_b(IResourceManager p_110571_1_) {
      int i = Minecraft.func_71369_N();
      Stitcher stitcher = new Stitcher(i, i, 0, this.field_147636_j);
      this.field_94252_e.clear();
      this.field_94258_i.clear();
      int j = Integer.MAX_VALUE;
      int k = 1 << this.field_147636_j;

      for(Entry<String, TextureAtlasSprite> entry : this.field_110574_e.entrySet()) {
         TextureAtlasSprite textureatlassprite = entry.getValue();
         ResourceLocation resourcelocation = this.func_184396_a(textureatlassprite);
         IResource iresource = null;

         try {
            PngSizeInfo pngsizeinfo = PngSizeInfo.func_188532_a(p_110571_1_.func_110536_a(resourcelocation));
            iresource = p_110571_1_.func_110536_a(resourcelocation);
            boolean flag = iresource.func_110526_a("animation") != null;
            textureatlassprite.func_188538_a(pngsizeinfo, flag);
         } catch (RuntimeException runtimeexception) {
            field_147635_d.error("Unable to parse metadata from {}", resourcelocation, runtimeexception);
            continue;
         } catch (IOException ioexception) {
            field_147635_d.error("Using missing texture, unable to load {}", resourcelocation, ioexception);
            continue;
         } finally {
            IOUtils.closeQuietly((Closeable)iresource);
         }

         j = Math.min(j, Math.min(textureatlassprite.func_94211_a(), textureatlassprite.func_94216_b()));
         int j1 = Math.min(Integer.lowestOneBit(textureatlassprite.func_94211_a()), Integer.lowestOneBit(textureatlassprite.func_94216_b()));
         if (lvt_11_4_ < k) {
            field_147635_d.warn("Texture {} with size {}x{} limits mip level from {} to {}", resourcelocation, Integer.valueOf(textureatlassprite.func_94211_a()), Integer.valueOf(textureatlassprite.func_94216_b()), Integer.valueOf(MathHelper.func_151239_c(k)), Integer.valueOf(MathHelper.func_151239_c(j1)));
            k = j1;
         }

         stitcher.func_110934_a(textureatlassprite);
      }

      int l = Math.min(j, k);
      int i1 = MathHelper.func_151239_c(l);
      if (i1 < this.field_147636_j) {
         field_147635_d.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.field_94254_c, Integer.valueOf(this.field_147636_j), Integer.valueOf(i1), Integer.valueOf(l));
         this.field_147636_j = i1;
      }

      this.field_94249_f.func_147963_d(this.field_147636_j);
      stitcher.func_110934_a(this.field_94249_f);

      try {
         stitcher.func_94305_f();
      } catch (StitcherException stitcherexception) {
         throw stitcherexception;
      }

      field_147635_d.info("Created: {}x{} {}-atlas", Integer.valueOf(stitcher.func_110935_a()), Integer.valueOf(stitcher.func_110936_b()), this.field_94254_c);
      TextureUtil.func_180600_a(this.func_110552_b(), this.field_147636_j, stitcher.func_110935_a(), stitcher.func_110936_b());
      Map<String, TextureAtlasSprite> map = Maps.<String, TextureAtlasSprite>newHashMap(this.field_110574_e);

      for(TextureAtlasSprite textureatlassprite1 : stitcher.func_94309_g()) {
         if (textureatlassprite1 == this.field_94249_f || this.func_184397_a(p_110571_1_, textureatlassprite1)) {
            String s = textureatlassprite1.func_94215_i();
            map.remove(s);
            this.field_94252_e.put(s, textureatlassprite1);

            try {
               TextureUtil.func_147955_a(textureatlassprite1.func_147965_a(0), textureatlassprite1.func_94211_a(), textureatlassprite1.func_94216_b(), textureatlassprite1.func_130010_a(), textureatlassprite1.func_110967_i(), false, false);
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.func_85055_a(throwable, "Stitching texture atlas");
               CrashReportCategory crashreportcategory = crashreport.func_85058_a("Texture being stitched together");
               crashreportcategory.func_71507_a("Atlas path", this.field_94254_c);
               crashreportcategory.func_71507_a("Sprite", textureatlassprite1);
               throw new ReportedException(crashreport);
            }

            if (textureatlassprite1.func_130098_m()) {
               this.field_94258_i.add(textureatlassprite1);
            }
         }
      }

      for(TextureAtlasSprite textureatlassprite2 : map.values()) {
         textureatlassprite2.func_94217_a(this.field_94249_f);
      }

   }

   private boolean func_184397_a(IResourceManager p_184397_1_, final TextureAtlasSprite p_184397_2_) {
      ResourceLocation resourcelocation = this.func_184396_a(p_184397_2_);
      IResource iresource = null;

      label62: {
         boolean flag;
         try {
            iresource = p_184397_1_.func_110536_a(resourcelocation);
            p_184397_2_.func_188539_a(iresource, this.field_147636_j + 1);
            break label62;
         } catch (RuntimeException runtimeexception) {
            field_147635_d.error("Unable to parse metadata from {}", resourcelocation, runtimeexception);
            flag = false;
         } catch (IOException ioexception) {
            field_147635_d.error("Using missing texture, unable to load {}", resourcelocation, ioexception);
            flag = false;
            return flag;
         } finally {
            IOUtils.closeQuietly((Closeable)iresource);
         }

         return flag;
      }

      try {
         p_184397_2_.func_147963_d(this.field_147636_j);
         return true;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Applying mipmap");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Sprite being mipmapped");
         crashreportcategory.func_189529_a("Sprite name", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               return p_184397_2_.func_94215_i();
            }
         });
         crashreportcategory.func_189529_a("Sprite size", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               return p_184397_2_.func_94211_a() + " x " + p_184397_2_.func_94216_b();
            }
         });
         crashreportcategory.func_189529_a("Sprite frames", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               return p_184397_2_.func_110970_k() + " frames";
            }
         });
         crashreportcategory.func_71507_a("Mipmap levels", Integer.valueOf(this.field_147636_j));
         throw new ReportedException(crashreport);
      }
   }

   private ResourceLocation func_184396_a(TextureAtlasSprite p_184396_1_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_184396_1_.func_94215_i());
      return new ResourceLocation(resourcelocation.func_110624_b(), String.format("%s/%s%s", this.field_94254_c, resourcelocation.func_110623_a(), ".png"));
   }

   public TextureAtlasSprite func_110572_b(String p_110572_1_) {
      TextureAtlasSprite textureatlassprite = this.field_94252_e.get(p_110572_1_);
      if (textureatlassprite == null) {
         textureatlassprite = this.field_94249_f;
      }

      return textureatlassprite;
   }

   public void func_94248_c() {
      TextureUtil.func_94277_a(this.func_110552_b());

      for(TextureAtlasSprite textureatlassprite : this.field_94258_i) {
         textureatlassprite.func_94219_l();
      }

   }

   public TextureAtlasSprite func_174942_a(ResourceLocation p_174942_1_) {
      if (p_174942_1_ == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         TextureAtlasSprite textureatlassprite = this.field_110574_e.get(p_174942_1_);
         if (textureatlassprite == null) {
            textureatlassprite = TextureAtlasSprite.func_176604_a(p_174942_1_);
            this.field_110574_e.put(p_174942_1_.toString(), textureatlassprite);
         }

         return textureatlassprite;
      }
   }

   public void func_110550_d() {
      this.func_94248_c();
   }

   public void func_147633_a(int p_147633_1_) {
      this.field_147636_j = p_147633_1_;
   }

   public TextureAtlasSprite func_174944_f() {
      return this.field_94249_f;
   }
}
