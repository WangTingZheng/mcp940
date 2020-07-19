package net.minecraft.util.text.translation;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

public class LanguageMap {
   private static final Pattern field_111053_a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   private static final Splitter field_135065_b = Splitter.on('=').limit(2);
   private static final LanguageMap field_74817_a = new LanguageMap();
   private final Map<String, String> field_74816_c = Maps.<String, String>newHashMap();
   private long field_150511_e;

   public LanguageMap() {
      try {
         InputStream inputstream = LanguageMap.class.getResourceAsStream("/assets/minecraft/lang/en_us.lang");

         for(String s : IOUtils.readLines(inputstream, StandardCharsets.UTF_8)) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
               String[] astring = (String[])Iterables.toArray(field_135065_b.split(s), String.class);
               if (astring != null && astring.length == 2) {
                  String s1 = astring[0];
                  String s2 = field_111053_a.matcher(astring[1]).replaceAll("%$1s");
                  this.field_74816_c.put(s1, s2);
               }
            }
         }

         this.field_150511_e = System.currentTimeMillis();
      } catch (IOException var7) {
         ;
      }

   }

   static LanguageMap func_74808_a() {
      return field_74817_a;
   }

   public static synchronized void func_135063_a(Map<String, String> p_135063_0_) {
      field_74817_a.field_74816_c.clear();
      field_74817_a.field_74816_c.putAll(p_135063_0_);
      field_74817_a.field_150511_e = System.currentTimeMillis();
   }

   public synchronized String func_74805_b(String p_74805_1_) {
      return this.func_135064_c(p_74805_1_);
   }

   public synchronized String func_74803_a(String p_74803_1_, Object... p_74803_2_) {
      String s = this.func_135064_c(p_74803_1_);

      try {
         return String.format(s, p_74803_2_);
      } catch (IllegalFormatException var5) {
         return "Format error: " + s;
      }
   }

   private String func_135064_c(String p_135064_1_) {
      String s = this.field_74816_c.get(p_135064_1_);
      return s == null ? p_135064_1_ : s;
   }

   public synchronized boolean func_94520_b(String p_94520_1_) {
      return this.field_74816_c.containsKey(p_94520_1_);
   }

   public long func_150510_c() {
      return this.field_150511_e;
   }
}
