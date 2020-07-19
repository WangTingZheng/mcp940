package net.minecraft.server;

import java.io.OutputStream;
import net.minecraft.util.LoggingPrintStream;

public class DebugLoggingPrintStream extends LoggingPrintStream {
   public DebugLoggingPrintStream(String p_i47315_1_, OutputStream p_i47315_2_) {
      super(p_i47315_1_, p_i47315_2_);
   }

   protected void func_179882_a(String p_179882_1_) {
      StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
      StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
      field_179884_a.info("[{}]@.({}:{}): {}", this.field_179883_b, stacktraceelement.getFileName(), Integer.valueOf(stacktraceelement.getLineNumber()), p_179882_1_);
   }
}
