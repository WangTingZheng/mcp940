package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandPardonIp extends CommandBase {
   public String func_71517_b() {
      return "pardon-ip";
   }

   public int func_82362_a() {
      return 3;
   }

   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return p_184882_1_.func_184103_al().func_72363_f().func_152689_b() && super.func_184882_a(p_184882_1_, p_184882_2_);
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.unbanip.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length == 1 && p_184881_3_[0].length() > 1) {
         Matcher matcher = CommandBanIp.field_147211_a.matcher(p_184881_3_[0]);
         if (matcher.matches()) {
            p_184881_1_.func_184103_al().func_72363_f().func_152684_c(p_184881_3_[0]);
            func_152373_a(p_184881_2_, this, "commands.unbanip.success", new Object[]{p_184881_3_[0]});
         } else {
            throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
         }
      } else {
         throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_184103_al().func_72363_f().func_152685_a()) : Collections.emptyList();
   }
}
