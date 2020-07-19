package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileResourcePack extends AbstractResourcePack implements Closeable {
   public static final Splitter field_110601_c = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile field_110600_d;

   public FileResourcePack(File p_i1290_1_) {
      super(p_i1290_1_);
   }

   private ZipFile func_110599_c() throws IOException {
      if (this.field_110600_d == null) {
         this.field_110600_d = new ZipFile(this.field_110597_b);
      }

      return this.field_110600_d;
   }

   protected InputStream func_110591_a(String p_110591_1_) throws IOException {
      ZipFile zipfile = this.func_110599_c();
      ZipEntry zipentry = zipfile.getEntry(p_110591_1_);
      if (zipentry == null) {
         throw new ResourcePackFileNotFoundException(this.field_110597_b, p_110591_1_);
      } else {
         return zipfile.getInputStream(zipentry);
      }
   }

   public boolean func_110593_b(String p_110593_1_) {
      try {
         return this.func_110599_c().getEntry(p_110593_1_) != null;
      } catch (IOException var3) {
         return false;
      }
   }

   public Set<String> func_110587_b() {
      ZipFile zipfile;
      try {
         zipfile = this.func_110599_c();
      } catch (IOException var8) {
         return Collections.<String>emptySet();
      }

      Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
      Set<String> set = Sets.<String>newHashSet();

      while(enumeration.hasMoreElements()) {
         ZipEntry zipentry = enumeration.nextElement();
         String s = zipentry.getName();
         if (s.startsWith("assets/")) {
            List<String> list = Lists.newArrayList(field_110601_c.split(s));
            if (list.size() > 1) {
               String s1 = list.get(1);
               if (s1.equals(s1.toLowerCase(java.util.Locale.ROOT))) {
                  set.add(s1);
               } else {
                  this.func_110594_c(s1);
               }
            }
         }
      }

      return set;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   public void close() throws IOException {
      if (this.field_110600_d != null) {
         this.field_110600_d.close();
         this.field_110600_d = null;
      }

   }
}
