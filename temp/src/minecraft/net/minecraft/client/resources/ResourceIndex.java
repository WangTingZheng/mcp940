package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   private static final Logger field_152783_a = LogManager.getLogger();
   private final Map<String, File> field_152784_b = Maps.<String, File>newHashMap();

   protected ResourceIndex() {
   }

   public ResourceIndex(File p_i1047_1_, String p_i1047_2_) {
      File file1 = new File(p_i1047_1_, "objects");
      File file2 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = Files.newReader(file2, StandardCharsets.UTF_8);
         JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
         JsonObject jsonobject1 = JsonUtils.func_151218_a(jsonobject, "objects", (JsonObject)null);
         if (jsonobject1 != null) {
            for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
               JsonObject jsonobject2 = (JsonObject)entry.getValue();
               String s = entry.getKey();
               String[] astring = s.split("/", 2);
               String s1 = astring.length == 1 ? astring[0] : astring[0] + ":" + astring[1];
               String s2 = JsonUtils.func_151200_h(jsonobject2, "hash");
               File file3 = new File(file1, s2.substring(0, 2) + "/" + s2);
               this.field_152784_b.put(s1, file3);
            }
         }
      } catch (JsonParseException var20) {
         field_152783_a.error("Unable to parse resource index file: {}", (Object)file2);
      } catch (FileNotFoundException var21) {
         field_152783_a.error("Can't find the resource index file: {}", (Object)file2);
      } finally {
         IOUtils.closeQuietly((Reader)bufferedreader);
      }

   }

   @Nullable
   public File func_188547_a(ResourceLocation p_188547_1_) {
      String s = p_188547_1_.toString();
      return this.field_152784_b.get(s);
   }

   public boolean func_188545_b(ResourceLocation p_188545_1_) {
      File file1 = this.func_188547_a(p_188545_1_);
      return file1 != null && file1.isFile();
   }

   public File func_188546_a() {
      return this.field_152784_b.get("pack.mcmeta");
   }
}
