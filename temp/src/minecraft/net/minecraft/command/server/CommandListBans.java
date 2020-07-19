package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandListBans extends CommandBase {
   public String func_71517_b() {
      return "banlist";
   }

   public int func_82362_a() {
      return 3;
   }

   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return (p_184882_1_.func_184103_al().func_72363_f().func_152689_b() || p_184882_1_.func_184103_al().func_152608_h().func_152689_b()) && super.func_184882_a(p_184882_1_, p_184882_2_);
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.banlist.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length >= 1 && "ips".equalsIgnoreCase(p_184881_3_[0])) {
         p_184881_2_.func_145747_a(new TextComponentTranslation("commands.banlist.ips", new Object[]{p_184881_1_.func_184103_al().func_72363_f().func_152685_a().length}));
         p_184881_2_.func_145747_a(new TextComponentString(func_71527_a(p_184881_1_.func_184103_al().func_72363_f().func_152685_a())));
      } else {
         p_184881_2_.func_145747_a(new TextComponentTranslation("commands.banlist.players", new Object[]{p_184881_1_.func_184103_al().func_152608_h().func_152685_a().length}));
         p_184881_2_.func_145747_a(new TextComponentString(func_71527_a(p_184881_1_.func_184103_al().func_152608_h().func_152685_a())));
      }

   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, new String[]{"players", "ips"}) : Collections.emptyList();
   }
}
