package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandOp extends CommandBase {
   public String func_71517_b() {
      return "op";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.op.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length == 1 && p_184881_3_[0].length() > 0) {
         GameProfile gameprofile = p_184881_1_.func_152358_ax().func_152655_a(p_184881_3_[0]);
         if (gameprofile == null) {
            throw new CommandException("commands.op.failed", new Object[]{p_184881_3_[0]});
         } else {
            p_184881_1_.func_184103_al().func_152605_a(gameprofile);
            func_152373_a(p_184881_2_, this, "commands.op.success", new Object[]{p_184881_3_[0]});
         }
      } else {
         throw new WrongUsageException("commands.op.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         String s = p_184883_3_[p_184883_3_.length - 1];
         List<String> list = Lists.<String>newArrayList();

         for(GameProfile gameprofile : p_184883_1_.func_152357_F()) {
            if (!p_184883_1_.func_184103_al().func_152596_g(gameprofile) && func_71523_a(s, gameprofile.getName())) {
               list.add(gameprofile.getName());
            }
         }

         return list;
      } else {
         return Collections.<String>emptyList();
      }
   }
}
