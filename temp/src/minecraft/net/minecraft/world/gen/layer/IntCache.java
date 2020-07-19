package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;
import java.util.List;

public class IntCache {
   private static int field_76451_a = 256;
   private static final List<int[]> field_76449_b = Lists.<int[]>newArrayList();
   private static final List<int[]> field_76450_c = Lists.<int[]>newArrayList();
   private static final List<int[]> field_76447_d = Lists.<int[]>newArrayList();
   private static final List<int[]> field_76448_e = Lists.<int[]>newArrayList();

   public static synchronized int[] func_76445_a(int p_76445_0_) {
      if (p_76445_0_ <= 256) {
         if (field_76449_b.isEmpty()) {
            int[] aint4 = new int[256];
            field_76450_c.add(aint4);
            return aint4;
         } else {
            int[] aint3 = field_76449_b.remove(field_76449_b.size() - 1);
            field_76450_c.add(aint3);
            return aint3;
         }
      } else if (p_76445_0_ > field_76451_a) {
         field_76451_a = p_76445_0_;
         field_76447_d.clear();
         field_76448_e.clear();
         int[] aint2 = new int[field_76451_a];
         field_76448_e.add(aint2);
         return aint2;
      } else if (field_76447_d.isEmpty()) {
         int[] aint1 = new int[field_76451_a];
         field_76448_e.add(aint1);
         return aint1;
      } else {
         int[] aint = field_76447_d.remove(field_76447_d.size() - 1);
         field_76448_e.add(aint);
         return aint;
      }
   }

   public static synchronized void func_76446_a() {
      if (!field_76447_d.isEmpty()) {
         field_76447_d.remove(field_76447_d.size() - 1);
      }

      if (!field_76449_b.isEmpty()) {
         field_76449_b.remove(field_76449_b.size() - 1);
      }

      field_76447_d.addAll(field_76448_e);
      field_76449_b.addAll(field_76450_c);
      field_76448_e.clear();
      field_76450_c.clear();
   }

   public static synchronized String func_85144_b() {
      return "cache: " + field_76447_d.size() + ", tcache: " + field_76449_b.size() + ", allocated: " + field_76448_e.size() + ", tallocated: " + field_76450_c.size();
   }
}
