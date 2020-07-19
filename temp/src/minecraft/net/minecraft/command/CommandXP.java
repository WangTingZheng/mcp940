package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandXP extends CommandBase {
   public String func_71517_b() {
      return "xp";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.xp.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length <= 0) {
         throw new WrongUsageException("commands.xp.usage", new Object[0]);
      } else {
         String s = p_184881_3_[0];
         boolean flag = s.endsWith("l") || s.endsWith("L");
         if (flag && s.length() > 1) {
            s = s.substring(0, s.length() - 1);
         }

         int i = func_175755_a(s);
         boolean flag1 = i < 0;
         if (flag1) {
            i *= -1;
         }

         EntityPlayer entityplayer = p_184881_3_.length > 1 ? func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[1]) : func_71521_c(p_184881_2_);
         if (flag) {
            p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, entityplayer.field_71068_ca);
            if (flag1) {
               entityplayer.func_82242_a(-i);
               func_152373_a(p_184881_2_, this, "commands.xp.success.negative.levels", new Object[]{i, entityplayer.func_70005_c_()});
            } else {
               entityplayer.func_82242_a(i);
               func_152373_a(p_184881_2_, this, "commands.xp.success.levels", new Object[]{i, entityplayer.func_70005_c_()});
            }
         } else {
            p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, entityplayer.field_71067_cb);
            if (flag1) {
               throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
            }

            entityplayer.func_71023_q(i);
            func_152373_a(p_184881_2_, this, "commands.xp.success", new Object[]{i, entityplayer.func_70005_c_()});
         }

      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 2 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 1;
   }
}
