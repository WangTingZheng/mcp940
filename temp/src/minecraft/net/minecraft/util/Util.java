package net.minecraft.util;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Logger;

public class Util {
   public static Util.EnumOS func_110647_a() {
      String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (s.contains("win")) {
         return Util.EnumOS.WINDOWS;
      } else if (s.contains("mac")) {
         return Util.EnumOS.OSX;
      } else if (s.contains("solaris")) {
         return Util.EnumOS.SOLARIS;
      } else if (s.contains("sunos")) {
         return Util.EnumOS.SOLARIS;
      } else if (s.contains("linux")) {
         return Util.EnumOS.LINUX;
      } else {
         return s.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
      }
   }

   @Nullable
   public static <V> V func_181617_a(FutureTask<V> p_181617_0_, Logger p_181617_1_) {
      try {
         p_181617_0_.run();
         return p_181617_0_.get();
      } catch (ExecutionException executionexception) {
         p_181617_1_.fatal("Error executing task", (Throwable)executionexception);
      } catch (InterruptedException interruptedexception) {
         p_181617_1_.fatal("Error executing task", (Throwable)interruptedexception);
      }

      return (V)null;
   }

   public static <T> T func_184878_a(List<T> p_184878_0_) {
      return p_184878_0_.get(p_184878_0_.size() - 1);
   }

   public static enum EnumOS {
      LINUX,
      SOLARIS,
      WINDOWS,
      OSX,
      UNKNOWN;
   }
}
