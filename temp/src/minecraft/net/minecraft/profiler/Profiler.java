package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
   private static final Logger field_151234_b = LogManager.getLogger();
   private final List<String> field_76325_b = Lists.<String>newArrayList();
   private final List<Long> field_76326_c = Lists.<Long>newArrayList();
   public boolean field_76327_a;
   private String field_76323_d = "";
   private final Map<String, Long> field_76324_e = Maps.<String, Long>newHashMap();

   public void func_76317_a() {
      this.field_76324_e.clear();
      this.field_76323_d = "";
      this.field_76325_b.clear();
   }

   public void func_76320_a(String p_76320_1_) {
      if (this.field_76327_a) {
         if (!this.field_76323_d.isEmpty()) {
            this.field_76323_d = this.field_76323_d + ".";
         }

         this.field_76323_d = this.field_76323_d + p_76320_1_;
         this.field_76325_b.add(this.field_76323_d);
         this.field_76326_c.add(Long.valueOf(System.nanoTime()));
      }
   }

   public void func_76319_b() {
      if (this.field_76327_a) {
         long i = System.nanoTime();
         long j = ((Long)this.field_76326_c.remove(this.field_76326_c.size() - 1)).longValue();
         this.field_76325_b.remove(this.field_76325_b.size() - 1);
         long k = i - j;
         if (this.field_76324_e.containsKey(this.field_76323_d)) {
            this.field_76324_e.put(this.field_76323_d, Long.valueOf(((Long)this.field_76324_e.get(this.field_76323_d)).longValue() + k));
         } else {
            this.field_76324_e.put(this.field_76323_d, Long.valueOf(k));
         }

         if (k > 100000000L) {
            field_151234_b.warn("Something's taking too long! '{}' took aprox {} ms", this.field_76323_d, Double.valueOf((double)k / 1000000.0D));
         }

         this.field_76323_d = this.field_76325_b.isEmpty() ? "" : (String)this.field_76325_b.get(this.field_76325_b.size() - 1);
      }
   }

   public List<Profiler.Result> func_76321_b(String p_76321_1_) {
      if (!this.field_76327_a) {
         return Collections.<Profiler.Result>emptyList();
      } else {
         long i = this.field_76324_e.containsKey("root") ? ((Long)this.field_76324_e.get("root")).longValue() : 0L;
         long j = this.field_76324_e.containsKey(p_76321_1_) ? ((Long)this.field_76324_e.get(p_76321_1_)).longValue() : -1L;
         List<Profiler.Result> list = Lists.<Profiler.Result>newArrayList();
         if (!p_76321_1_.isEmpty()) {
            p_76321_1_ = p_76321_1_ + ".";
         }

         long k = 0L;

         for(String s : this.field_76324_e.keySet()) {
            if (s.length() > p_76321_1_.length() && s.startsWith(p_76321_1_) && s.indexOf(".", p_76321_1_.length() + 1) < 0) {
               k += ((Long)this.field_76324_e.get(s)).longValue();
            }
         }

         float f = (float)k;
         if (k < j) {
            k = j;
         }

         if (i < k) {
            i = k;
         }

         for(String s1 : this.field_76324_e.keySet()) {
            if (s1.length() > p_76321_1_.length() && s1.startsWith(p_76321_1_) && s1.indexOf(".", p_76321_1_.length() + 1) < 0) {
               long l = ((Long)this.field_76324_e.get(s1)).longValue();
               double d0 = (double)l * 100.0D / (double)k;
               double d1 = (double)l * 100.0D / (double)i;
               String s2 = s1.substring(p_76321_1_.length());
               list.add(new Profiler.Result(s2, d0, d1));
            }
         }

         for(String s3 : this.field_76324_e.keySet()) {
            this.field_76324_e.put(s3, Long.valueOf(((Long)this.field_76324_e.get(s3)).longValue() * 999L / 1000L));
         }

         if ((float)k > f) {
            list.add(new Profiler.Result("unspecified", (double)((float)k - f) * 100.0D / (double)k, (double)((float)k - f) * 100.0D / (double)i));
         }

         Collections.sort(list);
         list.add(0, new Profiler.Result(p_76321_1_, 100.0D, (double)k * 100.0D / (double)i));
         return list;
      }
   }

   public void func_76318_c(String p_76318_1_) {
      this.func_76319_b();
      this.func_76320_a(p_76318_1_);
   }

   public String func_76322_c() {
      return this.field_76325_b.isEmpty() ? "[UNKNOWN]" : (String)this.field_76325_b.get(this.field_76325_b.size() - 1);
   }

   public static final class Result implements Comparable<Profiler.Result> {
      public double field_76332_a;
      public double field_76330_b;
      public String field_76331_c;

      public Result(String p_i1554_1_, double p_i1554_2_, double p_i1554_4_) {
         this.field_76331_c = p_i1554_1_;
         this.field_76332_a = p_i1554_2_;
         this.field_76330_b = p_i1554_4_;
      }

      public int compareTo(Profiler.Result p_compareTo_1_) {
         if (p_compareTo_1_.field_76332_a < this.field_76332_a) {
            return -1;
         } else {
            return p_compareTo_1_.field_76332_a > this.field_76332_a ? 1 : p_compareTo_1_.field_76331_c.compareTo(this.field_76331_c);
         }
      }

      public int func_76329_a() {
         return (this.field_76331_c.hashCode() & 11184810) + 4473924;
      }
   }
}
