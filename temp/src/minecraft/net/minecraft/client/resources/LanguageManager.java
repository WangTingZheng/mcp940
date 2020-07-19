package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.text.translation.LanguageMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener {
   private static final Logger field_147648_b = LogManager.getLogger();
   private final MetadataSerializer field_135047_b;
   private String field_135048_c;
   protected static final Locale field_135049_a = new Locale();
   private final Map<String, Language> field_135046_d = Maps.<String, Language>newHashMap();

   public LanguageManager(MetadataSerializer p_i1304_1_, String p_i1304_2_) {
      this.field_135047_b = p_i1304_1_;
      this.field_135048_c = p_i1304_2_;
      I18n.func_135051_a(field_135049_a);
   }

   public void func_135043_a(List<IResourcePack> p_135043_1_) {
      this.field_135046_d.clear();

      for(IResourcePack iresourcepack : p_135043_1_) {
         try {
            LanguageMetadataSection languagemetadatasection = (LanguageMetadataSection)iresourcepack.func_135058_a(this.field_135047_b, "language");
            if (languagemetadatasection != null) {
               for(Language language : languagemetadatasection.func_135018_a()) {
                  if (!this.field_135046_d.containsKey(language.func_135034_a())) {
                     this.field_135046_d.put(language.func_135034_a(), language);
                  }
               }
            }
         } catch (RuntimeException runtimeexception) {
            field_147648_b.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.func_130077_b(), runtimeexception);
         } catch (IOException ioexception) {
            field_147648_b.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.func_130077_b(), ioexception);
         }
      }

   }

   public void func_110549_a(IResourceManager p_110549_1_) {
      List<String> list = Lists.newArrayList("en_us");
      if (!"en_us".equals(this.field_135048_c)) {
         list.add(this.field_135048_c);
      }

      field_135049_a.func_135022_a(p_110549_1_, list);
      LanguageMap.func_135063_a(field_135049_a.field_135032_a);
   }

   public boolean func_135042_a() {
      return field_135049_a.func_135025_a();
   }

   public boolean func_135044_b() {
      return this.func_135041_c() != null && this.func_135041_c().func_135035_b();
   }

   public void func_135045_a(Language p_135045_1_) {
      this.field_135048_c = p_135045_1_.func_135034_a();
   }

   public Language func_135041_c() {
      String s = this.field_135046_d.containsKey(this.field_135048_c) ? this.field_135048_c : "en_us";
      return this.field_135046_d.get(s);
   }

   public SortedSet<Language> func_135040_d() {
      return Sets.newTreeSet(this.field_135046_d.values());
   }

   public Language func_191960_a(String p_191960_1_) {
      return this.field_135046_d.get(p_191960_1_);
   }
}
