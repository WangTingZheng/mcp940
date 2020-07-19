package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] field_74845_a;

   public CommandException(String p_i1359_1_, Object... p_i1359_2_) {
      super(p_i1359_1_);
      this.field_74845_a = p_i1359_2_;
   }

   public Object[] func_74844_a() {
      return this.field_74845_a;
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
