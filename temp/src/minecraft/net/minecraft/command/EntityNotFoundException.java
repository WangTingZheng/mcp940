package net.minecraft.command;

public class EntityNotFoundException extends CommandException {
   public EntityNotFoundException(String p_i47332_1_) {
      this("commands.generic.entity.notFound", p_i47332_1_);
   }

   public EntityNotFoundException(String p_i46035_1_, Object... p_i46035_2_) {
      super(p_i46035_1_, p_i46035_2_);
   }

   public synchronized Throwable fillInStackTrace() {
      return this;
   }
}
