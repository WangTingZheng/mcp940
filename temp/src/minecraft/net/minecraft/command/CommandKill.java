package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandKill extends CommandBase {
   public String func_71517_b() {
      return "kill";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.kill.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length == 0) {
         EntityPlayer entityplayer = func_71521_c(p_184881_2_);
         entityplayer.func_174812_G();
         func_152373_a(p_184881_2_, this, "commands.kill.successful", new Object[]{entityplayer.func_145748_c_()});
      } else {
         Entity entity = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         entity.func_174812_G();
         func_152373_a(p_184881_2_, this, "commands.kill.successful", new Object[]{entity.func_145748_c_()});
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }
}
