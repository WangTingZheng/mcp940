package net.minecraft.command;

public class NumberInvalidException extends CommandException {
   public NumberInvalidException() {
      this("commands.generic.num.invalid");
   }

   public NumberInvalidException(String p_i1360_1_, Object... p_i1360_2_) {
      super(p_i1360_1_, p_i1360_2_);
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
