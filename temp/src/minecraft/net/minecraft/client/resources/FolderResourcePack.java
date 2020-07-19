package net.minecraft.client.resources;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class FolderResourcePack extends AbstractResourcePack {
   private static final boolean field_191386_b = Util.func_110647_a() == Util.EnumOS.WINDOWS;
   private static final CharMatcher field_191387_c = CharMatcher.is('\\');

   public FolderResourcePack(File p_i1291_1_) {
      super(p_i1291_1_);
   }

   protected static boolean func_191384_a(File p_191384_0_, String p_191384_1_) throws IOException {
      String s = p_191384_0_.getCanonicalPath();
      if (field_191386_b) {
         s = field_191387_c.replaceFrom(s, '/');
      }

      return s.endsWith(p_191384_1_);
   }

   protected InputStream func_110591_a(String p_110591_1_) throws IOException {
      File file1 = this.func_191385_d(p_110591_1_);
      if (file1 == null) {
         throw new ResourcePackFileNotFoundException(this.field_110597_b, p_110591_1_);
      } else {
         return new BufferedInputStream(new FileInputStream(file1));
      }
   }

   protected boolean func_110593_b(String p_110593_1_) {
      return this.func_191385_d(p_110593_1_) != null;
   }

   @Nullable
   private File func_191385_d(String p_191385_1_) {
      try {
         File file1 = new File(this.field_110597_b, p_191385_1_);
         if (file1.isFile() && func_191384_a(file1, p_191385_1_)) {
            return file1;
         }
      } catch (IOException var3) {
         ;
      }

      return null;
   }

   public Set<String> func_110587_b() {
      Set<String> set = Sets.<String>newHashSet();
      File file1 = new File(this.field_110597_b, "assets/");
      if (file1.isDirectory()) {
         for(File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
            String s = func_110595_a(file1, file2);
            if (s.equals(s.toLowerCase(java.util.Locale.ROOT))) {
               set.add(s.substring(0, s.length() - 1));
            } else {
               this.func_110594_c(s);
            }
         }
      }

      return set;
   }
}
