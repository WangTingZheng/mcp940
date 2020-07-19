package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSetSpawnpoint extends CommandBase {
   public String func_71517_b() {
      return "spawnpoint";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.spawnpoint.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length > 1 && p_184881_3_.length < 4) {
         throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
      } else {
         EntityPlayerMP entityplayermp = p_184881_3_.length > 0 ? func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]) : func_71521_c(p_184881_2_);
         BlockPos blockpos = p_184881_3_.length > 3 ? func_175757_a(p_184881_2_, p_184881_3_, 1, true) : entityplayermp.func_180425_c();
         if (entityplayermp.field_70170_p != null) {
            entityplayermp.func_180473_a(blockpos, true);
            func_152373_a(p_184881_2_, this, "commands.spawnpoint.success", new Object[]{entityplayermp.func_70005_c_(), blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
         }

      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length > 1 && p_184883_3_.length <= 4 ? func_175771_a(p_184883_3_, 1, p_184883_4_) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
