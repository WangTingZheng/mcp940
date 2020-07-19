package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandReload extends CommandBase {
   public String func_71517_b() {
      return "reload";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.reload.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length > 0) {
         throw new WrongUsageException("commands.reload.usage", new Object[0]);
      } else {
         p_184881_1_.func_193031_aM();
         func_152373_a(p_184881_2_, this, "commands.reload.success", new Object[0]);
      }
   }
}
