package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandListPlayers extends CommandBase {
   public String func_71517_b() {
      return "list";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.players.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      int i = p_184881_1_.func_71233_x();
      p_184881_2_.func_145747_a(new TextComponentTranslation("commands.players.list", new Object[]{i, p_184881_1_.func_71275_y()}));
      p_184881_2_.func_145747_a(new TextComponentString(p_184881_1_.func_184103_al().func_181058_b(p_184881_3_.length > 0 && "uuids".equalsIgnoreCase(p_184881_3_[0]))));
      p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, i);
   }
}
