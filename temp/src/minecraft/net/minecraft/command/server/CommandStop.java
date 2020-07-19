package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {
   public String func_71517_b() {
      return "stop";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.stop.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_1_.field_71305_c != null) {
         func_152373_a(p_184881_2_, this, "commands.stop.start", new Object[0]);
      }

      p_184881_1_.func_71263_m();
   }
}
