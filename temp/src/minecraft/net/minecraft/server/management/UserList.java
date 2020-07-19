package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>> {
   protected static final Logger field_152693_a = LogManager.getLogger();
   protected final Gson field_152694_b;
   private final File field_152695_c;
   private final Map<String, V> field_152696_d = Maps.<String, V>newHashMap();
   private boolean field_152697_e = true;
   private static final ParameterizedType field_152698_f = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public UserList(File p_i1144_1_) {
      this.field_152695_c = p_i1144_1_;
      GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
      gsonbuilder.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
      this.field_152694_b = gsonbuilder.create();
   }

   public boolean func_152689_b() {
      return this.field_152697_e;
   }

   public void func_152686_a(boolean p_152686_1_) {
      this.field_152697_e = p_152686_1_;
   }

   public void func_152687_a(V p_152687_1_) {
      this.field_152696_d.put(this.func_152681_a(p_152687_1_.func_152640_f()), p_152687_1_);

      try {
         this.func_152678_f();
      } catch (IOException ioexception) {
         field_152693_a.warn("Could not save the list after adding a user.", (Throwable)ioexception);
      }

   }

   public V func_152683_b(K p_152683_1_) {
      this.func_152680_h();
      return (V)(this.field_152696_d.get(this.func_152681_a(p_152683_1_)));
   }

   public void func_152684_c(K p_152684_1_) {
      this.field_152696_d.remove(this.func_152681_a(p_152684_1_));

      try {
         this.func_152678_f();
      } catch (IOException ioexception) {
         field_152693_a.warn("Could not save the list after removing a user.", (Throwable)ioexception);
      }

   }

   public String[] func_152685_a() {
      return (String[])this.field_152696_d.keySet().toArray(new String[this.field_152696_d.size()]);
   }

   protected String func_152681_a(K p_152681_1_) {
      return p_152681_1_.toString();
   }

   protected boolean func_152692_d(K p_152692_1_) {
      return this.field_152696_d.containsKey(this.func_152681_a(p_152692_1_));
   }

   private void func_152680_h() {
      List<K> list = Lists.<K>newArrayList();

      for(V v : this.field_152696_d.values()) {
         if (v.func_73682_e()) {
            list.add(v.func_152640_f());
         }
      }

      for(K k : list) {
         this.field_152696_d.remove(k);
      }

   }

   protected UserListEntry<K> func_152682_a(JsonObject p_152682_1_) {
      return new UserListEntry<K>((Object)null, p_152682_1_);
   }

   protected Map<String, V> func_152688_e() {
      return this.field_152696_d;
   }

   public void func_152678_f() throws IOException {
      Collection<V> collection = this.field_152696_d.values();
      String s = this.field_152694_b.toJson(collection);
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = Files.newWriter(this.field_152695_c, StandardCharsets.UTF_8);
         bufferedwriter.write(s);
      } finally {
         IOUtils.closeQuietly((Writer)bufferedwriter);
      }

   }

   class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>> {
      private Serializer() {
      }

      public JsonElement serialize(UserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         p_serialize_1_.func_152641_a(jsonobject);
         return jsonobject;
      }

      public UserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonObject()) {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            return UserList.this.func_152682_a(jsonobject);
         } else {
            return null;
         }
      }
   }
}
