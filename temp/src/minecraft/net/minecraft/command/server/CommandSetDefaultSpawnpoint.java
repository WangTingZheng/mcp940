package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSetDefaultSpawnpoint extends CommandBase {
   public String func_71517_b() {
      return "setworldspawn";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.setworldspawn.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      BlockPos blockpos;
      if (p_184881_3_.length == 0) {
         blockpos = func_71521_c(p_184881_2_).func_180425_c();
      } else {
         if (p_184881_3_.length != 3 || p_184881_2_.func_130014_f_() == null) {
            throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
         }

         blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, true);
      }

      p_184881_2_.func_130014_f_().func_175652_B(blockpos);
      p_184881_1_.func_184103_al().func_148540_a(new SPacketSpawnPosition(blockpos));
      func_152373_a(p_184881_2_, this, "commands.setworldspawn.success", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length > 0 && p_184883_3_.length <= 3 ? func_175771_a(p_184883_3_, 0, p_184883_4_) : Collections.emptyList();
   }
}
