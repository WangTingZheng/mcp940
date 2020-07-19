package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;

public class CommandPublishLocalServer extends CommandBase {
   public String func_71517_b() {
      return "publish";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.publish.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      String s = p_184881_1_.func_71206_a(GameType.SURVIVAL, false);
      if (s != null) {
         func_152373_a(p_184881_2_, this, "commands.publish.started", new Object[]{s});
      } else {
         func_152373_a(p_184881_2_, this, "commands.publish.failed", new Object[0]);
      }

   }
}
