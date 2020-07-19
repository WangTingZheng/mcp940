package net.minecraft.command;

public class PlayerNotFoundException extends CommandException {
   public PlayerNotFoundException(String p_i47330_1_) {
      super(p_i47330_1_);
   }

   public PlayerNotFoundException(String p_i1362_1_, Object... p_i1362_2_) {
      super(p_i1362_1_, p_i1362_2_);
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
