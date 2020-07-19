package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class Locale {
   private static final Splitter field_135030_b = Splitter.on('=').limit(2);
   private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   Map<String, String> field_135032_a = Maps.<String, String>newHashMap();
   private boolean field_135029_d;

   public synchronized void func_135022_a(IResourceManager p_135022_1_, List<String> p_135022_2_) {
      this.field_135032_a.clear();

      for(String s : p_135022_2_) {
         String s1 = String.format("lang/%s.lang", s);

         for(String s2 : p_135022_1_.func_135055_a()) {
            try {
               this.func_135028_a(p_135022_1_.func_135056_b(new ResourceLocation(s2, s1)));
            } catch (IOException var9) {
               ;
            }
         }
      }

      this.func_135024_b();
   }

   public boolean func_135025_a() {
      return this.field_135029_d;
   }

   private void func_135024_b() {
      this.field_135029_d = false;
      int i = 0;
      int j = 0;

      for(String s : this.field_135032_a.values()) {
         int k = s.length();
         j += k;

         for(int l = 0; l < k; ++l) {
            if (s.charAt(l) >= 256) {
               ++i;
            }
         }
      }

      float f = (float)i / (float)j;
      this.field_135029_d = (double)f > 0.1D;
   }

   private void func_135028_a(List<IResource> p_135028_1_) throws IOException {
      for(IResource iresource : p_135028_1_) {
         InputStream inputstream = iresource.func_110527_b();

         try {
            this.func_135021_a(inputstream);
         } finally {
            IOUtils.closeQuietly(inputstream);
         }
      }

   }

   private void func_135021_a(InputStream p_135021_1_) throws IOException {
      for(String s : IOUtils.readLines(p_135021_1_, StandardCharsets.UTF_8)) {
         if (!s.isEmpty() && s.charAt(0) != '#') {
            String[] astring = (String[])Iterables.toArray(field_135030_b.split(s), String.class);
            if (astring != null && astring.length == 2) {
               String s1 = astring[0];
               String s2 = field_135031_c.matcher(astring[1]).replaceAll("%$1s");
               this.field_135032_a.put(s1, s2);
            }
         }
      }

   }

   private String func_135026_c(String p_135026_1_) {
      String s = this.field_135032_a.get(p_135026_1_);
      return s == null ? p_135026_1_ : s;
   }

   public String func_135023_a(String p_135023_1_, Object[] p_135023_2_) {
      String s = this.func_135026_c(p_135023_1_);

      try {
         return String.format(s, p_135023_2_);
      } catch (IllegalFormatException var5) {
         return "Format error: " + s;
      }
   }

   public boolean func_188568_a(String p_188568_1_) {
      return this.field_135032_a.containsKey(p_188568_1_);
   }
}
