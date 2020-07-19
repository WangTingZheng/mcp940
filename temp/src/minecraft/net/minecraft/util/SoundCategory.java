package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

public enum SoundCategory {
   MASTER("master"),
   MUSIC("music"),
   RECORDS("record"),
   WEATHER("weather"),
   BLOCKS("block"),
   HOSTILE("hostile"),
   NEUTRAL("neutral"),
   PLAYERS("player"),
   AMBIENT("ambient"),
   VOICE("voice");

   private static final Map<String, SoundCategory> field_187961_k = Maps.<String, SoundCategory>newHashMap();
   private final String field_187962_l;

   private SoundCategory(String p_i46833_3_) {
      this.field_187962_l = p_i46833_3_;
   }

   public String func_187948_a() {
      return this.field_187962_l;
   }

   public static SoundCategory func_187950_a(String p_187950_0_) {
      return field_187961_k.get(p_187950_0_);
   }

   public static Set<String> func_187949_b() {
      return field_187961_k.keySet();
   }

   static {
      for(SoundCategory soundcategory : values()) {
         if (field_187961_k.containsKey(soundcategory.func_187948_a())) {
            throw new Error("Clash in Sound Category name pools! Cannot insert " + soundcategory);
         }

         field_187961_k.put(soundcategory.func_187948_a(), soundcategory);
      }

   }
}
