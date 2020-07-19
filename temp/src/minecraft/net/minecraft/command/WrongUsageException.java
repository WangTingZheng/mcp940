package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException {
   public WrongUsageException(String p_i1364_1_, Object... p_i1364_2_) {
      super(p_i1364_1_, p_i1364_2_);
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
