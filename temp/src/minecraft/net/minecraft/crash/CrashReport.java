package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.minecraft.util.ReportedException;
import net.minecraft.world.gen.layer.IntCache;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger field_147150_a = LogManager.getLogger();
   private final String field_71513_a;
   private final Throwable field_71511_b;
   private final CrashReportCategory field_85061_c = new CrashReportCategory(this, "System Details");
   private final List<CrashReportCategory> field_71512_c = Lists.<CrashReportCategory>newArrayList();
   private File field_71510_d;
   private boolean field_85059_f = true;
   private StackTraceElement[] field_85060_g = new StackTraceElement[0];

   public CrashReport(String p_i1348_1_, Throwable p_i1348_2_) {
      this.field_71513_a = p_i1348_1_;
      this.field_71511_b = p_i1348_2_;
      this.func_71504_g();
   }

   private void func_71504_g() {
      this.field_85061_c.func_189529_a("Minecraft Version", new ICrashReportDetail<String>() {
         public String call() {
            return "1.12";
         }
      });
      this.field_85061_c.func_189529_a("Operating System", new ICrashReportDetail<String>() {
         public String call() {
            return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
         }
      });
      this.field_85061_c.func_189529_a("Java Version", new ICrashReportDetail<String>() {
         public String call() {
            return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
         }
      });
      this.field_85061_c.func_189529_a("Java VM Version", new ICrashReportDetail<String>() {
         public String call() {
            return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
         }
      });
      this.field_85061_c.func_189529_a("Memory", new ICrashReportDetail<String>() {
         public String call() {
            Runtime runtime = Runtime.getRuntime();
            long i = runtime.maxMemory();
            long j = runtime.totalMemory();
            long k = runtime.freeMemory();
            long l = i / 1024L / 1024L;
            long i1 = j / 1024L / 1024L;
            long j1 = k / 1024L / 1024L;
            return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
         }
      });
      this.field_85061_c.func_189529_a("JVM Flags", new ICrashReportDetail<String>() {
         public String call() {
            RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
            List<String> list = runtimemxbean.getInputArguments();
            int i = 0;
            StringBuilder stringbuilder = new StringBuilder();

            for(String s : list) {
               if (s.startsWith("-X")) {
                  if (i++ > 0) {
                     stringbuilder.append(" ");
                  }

                  stringbuilder.append(s);
               }
            }

            return String.format("%d total; %s", i, stringbuilder.toString());
         }
      });
      this.field_85061_c.func_189529_a("IntCache", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return IntCache.func_85144_b();
         }
      });
   }

   public String func_71501_a() {
      return this.field_71513_a;
   }

   public Throwable func_71505_b() {
      return this.field_71511_b;
   }

   public void func_71506_a(StringBuilder p_71506_1_) {
      if ((this.field_85060_g == null || this.field_85060_g.length <= 0) && !this.field_71512_c.isEmpty()) {
         this.field_85060_g = (StackTraceElement[])ArrayUtils.subarray(((CrashReportCategory)this.field_71512_c.get(0)).func_147152_a(), 0, 1);
      }

      if (this.field_85060_g != null && this.field_85060_g.length > 0) {
         p_71506_1_.append("-- Head --\n");
         p_71506_1_.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         p_71506_1_.append("Stacktrace:\n");

         for(StackTraceElement stacktraceelement : this.field_85060_g) {
            p_71506_1_.append("\t").append("at ").append((Object)stacktraceelement);
            p_71506_1_.append("\n");
         }

         p_71506_1_.append("\n");
      }

      for(CrashReportCategory crashreportcategory : this.field_71512_c) {
         crashreportcategory.func_85072_a(p_71506_1_);
         p_71506_1_.append("\n\n");
      }

      this.field_85061_c.func_85072_a(p_71506_1_);
   }

   public String func_71498_d() {
      StringWriter stringwriter = null;
      PrintWriter printwriter = null;
      Throwable throwable = this.field_71511_b;
      if (throwable.getMessage() == null) {
         if (throwable instanceof NullPointerException) {
            throwable = new NullPointerException(this.field_71513_a);
         } else if (throwable instanceof StackOverflowError) {
            throwable = new StackOverflowError(this.field_71513_a);
         } else if (throwable instanceof OutOfMemoryError) {
            throwable = new OutOfMemoryError(this.field_71513_a);
         }

         throwable.setStackTrace(this.field_71511_b.getStackTrace());
      }

      String s = throwable.toString();

      try {
         stringwriter = new StringWriter();
         printwriter = new PrintWriter(stringwriter);
         throwable.printStackTrace(printwriter);
         s = stringwriter.toString();
      } finally {
         IOUtils.closeQuietly((Writer)stringwriter);
         IOUtils.closeQuietly((Writer)printwriter);
      }

      return s;
   }

   public String func_71502_e() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Crash Report ----\n");
      stringbuilder.append("// ");
      stringbuilder.append(func_71503_h());
      stringbuilder.append("\n\n");
      stringbuilder.append("Time: ");
      stringbuilder.append((new SimpleDateFormat()).format(new Date()));
      stringbuilder.append("\n");
      stringbuilder.append("Description: ");
      stringbuilder.append(this.field_71513_a);
      stringbuilder.append("\n\n");
      stringbuilder.append(this.func_71498_d());
      stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int i = 0; i < 87; ++i) {
         stringbuilder.append("-");
      }

      stringbuilder.append("\n\n");
      this.func_71506_a(stringbuilder);
      return stringbuilder.toString();
   }

   public File func_71497_f() {
      return this.field_71510_d;
   }

   public boolean func_147149_a(File p_147149_1_) {
      if (this.field_71510_d != null) {
         return false;
      } else {
         if (p_147149_1_.getParentFile() != null) {
            p_147149_1_.getParentFile().mkdirs();
         }

         Writer writer = null;

         boolean flag1;
         try {
            writer = new OutputStreamWriter(new FileOutputStream(p_147149_1_), StandardCharsets.UTF_8);
            writer.write(this.func_71502_e());
            this.field_71510_d = p_147149_1_;
            boolean lvt_3_1_ = true;
            return lvt_3_1_;
         } catch (Throwable throwable) {
            field_147150_a.error("Could not save crash report to {}", p_147149_1_, throwable);
            flag1 = false;
         } finally {
            IOUtils.closeQuietly(writer);
         }

         return flag1;
      }
   }

   public CrashReportCategory func_85056_g() {
      return this.field_85061_c;
   }

   public CrashReportCategory func_85058_a(String p_85058_1_) {
      return this.func_85057_a(p_85058_1_, 1);
   }

   public CrashReportCategory func_85057_a(String p_85057_1_, int p_85057_2_) {
      CrashReportCategory crashreportcategory = new CrashReportCategory(this, p_85057_1_);
      if (this.field_85059_f) {
         int i = crashreportcategory.func_85073_a(p_85057_2_);
         StackTraceElement[] astacktraceelement = this.field_71511_b.getStackTrace();
         StackTraceElement stacktraceelement = null;
         StackTraceElement stacktraceelement1 = null;
         int j = astacktraceelement.length - i;
         if (j < 0) {
            System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
         }

         if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
            stacktraceelement = astacktraceelement[j];
            if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
               stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
            }
         }

         this.field_85059_f = crashreportcategory.func_85069_a(stacktraceelement, stacktraceelement1);
         if (i > 0 && !this.field_71512_c.isEmpty()) {
            CrashReportCategory crashreportcategory1 = this.field_71512_c.get(this.field_71512_c.size() - 1);
            crashreportcategory1.func_85070_b(i);
         } else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
            this.field_85060_g = new StackTraceElement[j];
            System.arraycopy(astacktraceelement, 0, this.field_85060_g, 0, this.field_85060_g.length);
         } else {
            this.field_85059_f = false;
         }
      }

      this.field_71512_c.add(crashreportcategory);
      return crashreportcategory;
   }

   private static String func_71503_h() {
      String[] astring = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};

      try {
         return astring[(int)(System.nanoTime() % (long)astring.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport func_85055_a(Throwable p_85055_0_, String p_85055_1_) {
      CrashReport crashreport;
      if (p_85055_0_ instanceof ReportedException) {
         crashreport = ((ReportedException)p_85055_0_).func_71575_a();
      } else {
         crashreport = new CrashReport(p_85055_1_, p_85055_0_);
      }

      return crashreport;
   }
}
