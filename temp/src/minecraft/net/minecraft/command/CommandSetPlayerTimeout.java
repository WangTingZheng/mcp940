package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {
   public String func_71517_b() {
      return "setidletimeout";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.setidletimeout.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length != 1) {
         throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
      } else {
         int i = func_180528_a(p_184881_3_[0], 0);
         p_184881_1_.func_143006_e(i);
         func_152373_a(p_184881_2_, this, "commands.setidletimeout.success", new Object[]{i});
      }
   }
}
