package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {
   public String func_71517_b() {
      return "save-off";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.save-off.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      boolean flag = false;

      for(int i = 0; i < p_184881_1_.field_71305_c.length; ++i) {
         if (p_184881_1_.field_71305_c[i] != null) {
            WorldServer worldserver = p_184881_1_.field_71305_c[i];
            if (!worldserver.field_73058_d) {
               worldserver.field_73058_d = true;
               flag = true;
            }
         }
      }

      if (flag) {
         func_152373_a(p_184881_2_, this, "commands.save.disabled", new Object[0]);
      } else {
         throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
      }
   }
}
