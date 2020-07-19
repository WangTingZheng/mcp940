package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.HttpUtil;

public class Snooper {
   private final Map<String, Object> field_152773_a = Maps.<String, Object>newHashMap();
   private final Map<String, Object> field_152774_b = Maps.<String, Object>newHashMap();
   private final String field_76480_b = UUID.randomUUID().toString();
   private final URL field_76481_c;
   private final ISnooperInfo field_76478_d;
   private final Timer field_76479_e = new Timer("Snooper Timer", true);
   private final Object field_76476_f = new Object();
   private final long field_98224_g;
   private boolean field_76477_g;
   private int field_76483_h;

   public Snooper(String p_i1563_1_, ISnooperInfo p_i1563_2_, long p_i1563_3_) {
      try {
         this.field_76481_c = new URL("http://snoop.minecraft.net/" + p_i1563_1_ + "?version=" + 2);
      } catch (MalformedURLException var6) {
         throw new IllegalArgumentException();
      }

      this.field_76478_d = p_i1563_2_;
      this.field_98224_g = p_i1563_3_;
   }

   public void func_76463_a() {
      if (!this.field_76477_g) {
         this.field_76477_g = true;
         this.func_152766_h();
         this.field_76479_e.schedule(new TimerTask() {
            public void run() {
               if (Snooper.this.field_76478_d.func_70002_Q()) {
                  Map<String, Object> map;
                  synchronized(Snooper.this.field_76476_f) {
                     map = Maps.<String, Object>newHashMap(Snooper.this.field_152774_b);
                     if (Snooper.this.field_76483_h == 0) {
                        map.putAll(Snooper.this.field_152773_a);
                     }

                     map.put("snooper_count", Integer.valueOf(Snooper.this.field_76483_h++));
                     map.put("snooper_token", Snooper.this.field_76480_b);
                  }

                  MinecraftServer minecraftserver = Snooper.this.field_76478_d instanceof MinecraftServer ? (MinecraftServer)Snooper.this.field_76478_d : null;
                  HttpUtil.func_151226_a(Snooper.this.field_76481_c, map, true, minecraftserver == null ? null : minecraftserver.func_110454_ao());
               }
            }
         }, 0L, 900000L);
      }
   }

   private void func_152766_h() {
      this.func_76467_g();
      this.func_152768_a("snooper_token", this.field_76480_b);
      this.func_152767_b("snooper_token", this.field_76480_b);
      this.func_152767_b("os_name", System.getProperty("os.name"));
      this.func_152767_b("os_version", System.getProperty("os.version"));
      this.func_152767_b("os_architecture", System.getProperty("os.arch"));
      this.func_152767_b("java_version", System.getProperty("java.version"));
      this.func_152768_a("version", "1.12");
      this.field_76478_d.func_70001_b(this);
   }

   private void func_76467_g() {
      RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
      List<String> list = runtimemxbean.getInputArguments();
      int i = 0;

      for(String s : list) {
         if (s.startsWith("-X")) {
            this.func_152768_a("jvm_arg[" + i++ + "]", s);
         }
      }

      this.func_152768_a("jvm_args", Integer.valueOf(i));
   }

   public void func_76471_b() {
      this.func_152767_b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
      this.func_152767_b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
      this.func_152767_b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
      this.func_152767_b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
      this.field_76478_d.func_70000_a(this);
   }

   public void func_152768_a(String p_152768_1_, Object p_152768_2_) {
      synchronized(this.field_76476_f) {
         this.field_152774_b.put(p_152768_1_, p_152768_2_);
      }
   }

   public void func_152767_b(String p_152767_1_, Object p_152767_2_) {
      synchronized(this.field_76476_f) {
         this.field_152773_a.put(p_152767_1_, p_152767_2_);
      }
   }

   public Map<String, String> func_76465_c() {
      Map<String, String> map = Maps.<String, String>newLinkedHashMap();
      synchronized(this.field_76476_f) {
         this.func_76471_b();

         for(Entry<String, Object> entry : this.field_152773_a.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
         }

         for(Entry<String, Object> entry1 : this.field_152774_b.entrySet()) {
            map.put(entry1.getKey(), entry1.getValue().toString());
         }

         return map;
      }
   }

   public boolean func_76468_d() {
      return this.field_76477_g;
   }

   public void func_76470_e() {
      this.field_76479_e.cancel();
   }

   public String func_80006_f() {
      return this.field_76480_b;
   }

   public long func_130105_g() {
      return this.field_98224_g;
   }
}
