package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream extends PrintStream {
   protected static final Logger field_179884_a = LogManager.getLogger();
   protected final String field_179883_b;

   public LoggingPrintStream(String p_i45927_1_, OutputStream p_i45927_2_) {
      super(p_i45927_2_);
      this.field_179883_b = p_i45927_1_;
   }

   public void println(String p_println_1_) {
      this.func_179882_a(p_println_1_);
   }

   public void println(Object p_println_1_) {
      this.func_179882_a(String.valueOf(p_println_1_));
   }

   protected void func_179882_a(String p_179882_1_) {
      field_179884_a.info("[{}]: {}", this.field_179883_b, p_179882_1_);
   }
}
