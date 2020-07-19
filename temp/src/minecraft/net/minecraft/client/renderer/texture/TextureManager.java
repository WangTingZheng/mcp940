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
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger field_147646_a = LogManager.getLogger();
   public static final ResourceLocation field_194008_a = new ResourceLocation("");
   private final Map<ResourceLocation, ITextureObject> field_110585_a = Maps.<ResourceLocation, ITextureObject>newHashMap();
   private final List<ITickable> field_110583_b = Lists.<ITickable>newArrayList();
   private final Map<String, Integer> field_110584_c = Maps.<String, Integer>newHashMap();
   private final IResourceManager field_110582_d;

   public TextureManager(IResourceManager p_i1284_1_) {
      this.field_110582_d = p_i1284_1_;
   }

   public void func_110577_a(ResourceLocation p_110577_1_) {
      ITextureObject itextureobject = this.field_110585_a.get(p_110577_1_);
      if (itextureobject == null) {
         itextureobject = new SimpleTexture(p_110577_1_);
         this.func_110579_a(p_110577_1_, itextureobject);
      }

      TextureUtil.func_94277_a(itextureobject.func_110552_b());
   }

   public boolean func_110580_a(ResourceLocation p_110580_1_, ITickableTextureObject p_110580_2_) {
      if (this.func_110579_a(p_110580_1_, p_110580_2_)) {
         this.field_110583_b.add(p_110580_2_);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_110579_a(ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
      boolean flag = true;

      try {
         p_110579_2_.func_110551_a(this.field_110582_d);
      } catch (IOException ioexception) {
         if (p_110579_1_ != field_194008_a) {
            field_147646_a.warn("Failed to load texture: {}", p_110579_1_, ioexception);
         }

         p_110579_2_ = TextureUtil.field_111001_a;
         this.field_110585_a.put(p_110579_1_, p_110579_2_);
         flag = false;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Registering texture");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Resource location being registered");
         crashreportcategory.func_71507_a("Resource location", p_110579_1_);
         crashreportcategory.func_189529_a("Texture object class", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               return p_110579_2_.getClass().getName();
            }
         });
         throw new ReportedException(crashreport);
      }

      this.field_110585_a.put(p_110579_1_, p_110579_2_);
      return flag;
   }

   public ITextureObject func_110581_b(ResourceLocation p_110581_1_) {
      return this.field_110585_a.get(p_110581_1_);
   }

   public ResourceLocation func_110578_a(String p_110578_1_, DynamicTexture p_110578_2_) {
      Integer integer = this.field_110584_c.get(p_110578_1_);
      if (integer == null) {
         integer = Integer.valueOf(1);
      } else {
         integer = integer.intValue() + 1;
      }

      this.field_110584_c.put(p_110578_1_, integer);
      ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", p_110578_1_, integer));
      this.func_110579_a(resourcelocation, p_110578_2_);
      return resourcelocation;
   }

   public void func_110550_d() {
      for(ITickable itickable : this.field_110583_b) {
         itickable.func_110550_d();
      }

   }

   public void func_147645_c(ResourceLocation p_147645_1_) {
      ITextureObject itextureobject = this.func_110581_b(p_147645_1_);
      if (itextureobject != null) {
         TextureUtil.func_147942_a(itextureobject.func_110552_b());
      }

   }

   public void func_110549_a(IResourceManager p_110549_1_) {
      Iterator<Entry<ResourceLocation, ITextureObject>> iterator = this.field_110585_a.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry<ResourceLocation, ITextureObject> entry = (Entry)iterator.next();
         ITextureObject itextureobject = entry.getValue();
         if (itextureobject == TextureUtil.field_111001_a) {
            iterator.remove();
         } else {
            this.func_110579_a(entry.getKey(), itextureobject);
         }
      }

   }
}
