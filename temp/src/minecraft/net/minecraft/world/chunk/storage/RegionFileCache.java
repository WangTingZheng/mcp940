package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RegionFileCache {
   private static final Map<File, RegionFile> field_76553_a = Maps.<File, RegionFile>newHashMap();

   public static synchronized RegionFile func_76550_a(File p_76550_0_, int p_76550_1_, int p_76550_2_) {
      File file1 = new File(p_76550_0_, "region");
      File file2 = new File(file1, "r." + (p_76550_1_ >> 5) + "." + (p_76550_2_ >> 5) + ".mca");
      RegionFile regionfile = field_76553_a.get(file2);
      if (regionfile != null) {
         return regionfile;
      } else {
         if (!file1.exists()) {
            file1.mkdirs();
         }

         if (field_76553_a.size() >= 256) {
            func_76551_a();
         }

         RegionFile regionfile1 = new RegionFile(file2);
         field_76553_a.put(file2, regionfile1);
         return regionfile1;
      }
   }

   public static synchronized RegionFile func_191065_b(File p_191065_0_, int p_191065_1_, int p_191065_2_) {
      File file1 = new File(p_191065_0_, "region");
      File file2 = new File(file1, "r." + (p_191065_1_ >> 5) + "." + (p_191065_2_ >> 5) + ".mca");
      RegionFile regionfile = field_76553_a.get(file2);
      if (regionfile != null) {
         return regionfile;
      } else if (file1.exists() && file2.exists()) {
         if (field_76553_a.size() >= 256) {
            func_76551_a();
         }

         RegionFile regionfile1 = new RegionFile(file2);
         field_76553_a.put(file2, regionfile1);
         return regionfile1;
      } else {
         return null;
      }
   }

   public static synchronized void func_76551_a() {
      for(RegionFile regionfile : field_76553_a.values()) {
         try {
            if (regionfile != null) {
               regionfile.func_76708_c();
            }
         } catch (IOException ioexception) {
            ioexception.printStackTrace();
         }
      }

      field_76553_a.clear();
   }

   public static DataInputStream func_76549_c(File p_76549_0_, int p_76549_1_, int p_76549_2_) {
      RegionFile regionfile = func_76550_a(p_76549_0_, p_76549_1_, p_76549_2_);
      return regionfile.func_76704_a(p_76549_1_ & 31, p_76549_2_ & 31);
   }

   public static DataOutputStream func_76552_d(File p_76552_0_, int p_76552_1_, int p_76552_2_) {
      RegionFile regionfile = func_76550_a(p_76552_0_, p_76552_1_, p_76552_2_);
      return regionfile.func_76710_b(p_76552_1_ & 31, p_76552_2_ & 31);
   }

   public static boolean func_191064_f(File p_191064_0_, int p_191064_1_, int p_191064_2_) {
      RegionFile regionfile = func_191065_b(p_191064_0_, p_191064_1_, p_191064_2_);
      return regionfile != null ? regionfile.func_76709_c(p_191064_1_ & 31, p_191064_2_ & 31) : false;
   }
}
