package net.minecraft.command.server;

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

public class CommandDeOp extends CommandBase {
   public String func_71517_b() {
      return "deop";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.deop.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length == 1 && p_184881_3_[0].length() > 0) {
         GameProfile gameprofile = p_184881_1_.func_184103_al().func_152603_m().func_152700_a(p_184881_3_[0]);
         if (gameprofile == null) {
            throw new CommandException("commands.deop.failed", new Object[]{p_184881_3_[0]});
         } else {
            p_184881_1_.func_184103_al().func_152610_b(gameprofile);
            func_152373_a(p_184881_2_, this, "commands.deop.success", new Object[]{p_184881_3_[0]});
         }
      } else {
         throw new WrongUsageException("commands.deop.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_184103_al().func_152606_n()) : Collections.emptyList();
   }
}
