package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager {
   private static final Logger field_192782_a = LogManager.getLogger();
   private static final Gson field_192783_b = (new GsonBuilder()).registerTypeHierarchyAdapter(Advancement.Builder.class, new JsonDeserializer<Advancement.Builder>() {
      public Advancement.Builder deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "advancement");
         return Advancement.Builder.func_192059_a(jsonobject, p_deserialize_3_);
      }
   }).registerTypeAdapter(AdvancementRewards.class, new AdvancementRewards.Deserializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
   private static final AdvancementList field_192784_c = new AdvancementList();
   private final File field_192785_d;
   private boolean field_193768_e;

   public AdvancementManager(@Nullable File p_i47421_1_) {
      this.field_192785_d = p_i47421_1_;
      this.func_192779_a();
   }

   public void func_192779_a() {
      this.field_193768_e = false;
      field_192784_c.func_192087_a();
      Map<ResourceLocation, Advancement.Builder> map = this.func_192781_c();
      this.func_192777_a(map);
      field_192784_c.func_192083_a(map);

      for(Advancement advancement : field_192784_c.func_192088_b()) {
         if (advancement.func_192068_c() != null) {
            AdvancementTreeNode.func_192323_a(advancement);
         }
      }

   }

   public boolean func_193767_b() {
      return this.field_193768_e;
   }

   private Map<ResourceLocation, Advancement.Builder> func_192781_c() {
      if (this.field_192785_d == null) {
         return Maps.<ResourceLocation, Advancement.Builder>newHashMap();
      } else {
         Map<ResourceLocation, Advancement.Builder> map = Maps.<ResourceLocation, Advancement.Builder>newHashMap();
         this.field_192785_d.mkdirs();

         for(File file1 : FileUtils.listFiles(this.field_192785_d, new String[]{"json"}, true)) {
            String s = FilenameUtils.removeExtension(this.field_192785_d.toURI().relativize(file1.toURI()).toString());
            String[] astring = s.split("/", 2);
            if (astring.length == 2) {
               ResourceLocation resourcelocation = new ResourceLocation(astring[0], astring[1]);

               try {
                  Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.func_188178_a(field_192783_b, FileUtils.readFileToString(file1, StandardCharsets.UTF_8), Advancement.Builder.class);
                  if (advancement$builder == null) {
                     field_192782_a.error("Couldn't load custom advancement " + resourcelocation + " from " + file1 + " as it's empty or null");
                  } else {
                     map.put(resourcelocation, advancement$builder);
                  }
               } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                  field_192782_a.error("Parsing error loading custom advancement " + resourcelocation, (Throwable)jsonparseexception);
                  this.field_193768_e = true;
               } catch (IOException ioexception) {
                  field_192782_a.error("Couldn't read custom advancement " + resourcelocation + " from " + file1, (Throwable)ioexception);
                  this.field_193768_e = true;
               }
            }
         }

         return map;
      }
   }

   private void func_192777_a(Map<ResourceLocation, Advancement.Builder> p_192777_1_) {
      FileSystem filesystem = null;

      try {
         URL url = AdvancementManager.class.getResource("/assets/.mcassetsroot");
         if (url != null) {
            URI uri = url.toURI();
            Path path;
            if ("file".equals(uri.getScheme())) {
               path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/advancements").toURI());
            } else {
               if (!"jar".equals(uri.getScheme())) {
                  field_192782_a.error("Unsupported scheme " + uri + " trying to list all built-in advancements (NYI?)");
                  this.field_193768_e = true;
                  return;
               }

               filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
               path = filesystem.getPath("/assets/minecraft/advancements");
            }

            Iterator<Path> iterator = Files.walk(path).iterator();

            while(iterator.hasNext()) {
               Path path1 = iterator.next();
               if ("json".equals(FilenameUtils.getExtension(path1.toString()))) {
                  Path path2 = path.relativize(path1);
                  String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
                  ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
                  if (!p_192777_1_.containsKey(resourcelocation)) {
                     BufferedReader bufferedreader = null;

                     try {
                        bufferedreader = Files.newBufferedReader(path1);
                        Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.func_193839_a(field_192783_b, bufferedreader, Advancement.Builder.class);
                        p_192777_1_.put(resourcelocation, advancement$builder);
                     } catch (JsonParseException jsonparseexception) {
                        field_192782_a.error("Parsing error loading built-in advancement " + resourcelocation, (Throwable)jsonparseexception);
                        this.field_193768_e = true;
                     } catch (IOException ioexception) {
                        field_192782_a.error("Couldn't read advancement " + resourcelocation + " from " + path1, (Throwable)ioexception);
                        this.field_193768_e = true;
                     } finally {
                        IOUtils.closeQuietly((Reader)bufferedreader);
                     }
                  }
               }
            }

            return;
         }

         field_192782_a.error("Couldn't find .mcassetsroot");
         this.field_193768_e = true;
      } catch (IOException | URISyntaxException urisyntaxexception) {
         field_192782_a.error("Couldn't get a list of all built-in advancement files", (Throwable)urisyntaxexception);
         this.field_193768_e = true;
         return;
      } finally {
         IOUtils.closeQuietly((Closeable)filesystem);
      }

   }

   @Nullable
   public Advancement func_192778_a(ResourceLocation p_192778_1_) {
      return field_192784_c.func_192084_a(p_192778_1_);
   }

   public Iterable<Advancement> func_192780_b() {
      return field_192784_c.func_192089_c();
   }
}
