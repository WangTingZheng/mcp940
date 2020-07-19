package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase {
   public String func_71517_b() {
      return "time";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.time.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length > 1) {
         if ("set".equals(p_184881_3_[0])) {
            int i1;
            if ("day".equals(p_184881_3_[1])) {
               i1 = 1000;
            } else if ("night".equals(p_184881_3_[1])) {
               i1 = 13000;
            } else {
               i1 = func_180528_a(p_184881_3_[1], 0);
            }

            this.func_184929_a(p_184881_1_, i1);
            func_152373_a(p_184881_2_, this, "commands.time.set", new Object[]{i1});
            return;
         }

         if ("add".equals(p_184881_3_[0])) {
            int l = func_180528_a(p_184881_3_[1], 0);
            this.func_184928_b(p_184881_1_, l);
            func_152373_a(p_184881_2_, this, "commands.time.added", new Object[]{l});
            return;
         }

         if ("query".equals(p_184881_3_[0])) {
            if ("daytime".equals(p_184881_3_[1])) {
               int k = (int)(p_184881_2_.func_130014_f_().func_72820_D() % 24000L);
               p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, k);
               func_152373_a(p_184881_2_, this, "commands.time.query", new Object[]{k});
               return;
            }

            if ("day".equals(p_184881_3_[1])) {
               int j = (int)(p_184881_2_.func_130014_f_().func_72820_D() / 24000L % 2147483647L);
               p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, j);
               func_152373_a(p_184881_2_, this, "commands.time.query", new Object[]{j});
               return;
            }

            if ("gametime".equals(p_184881_3_[1])) {
               int i = (int)(p_184881_2_.func_130014_f_().func_82737_E() % 2147483647L);
               p_184881_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, i);
               func_152373_a(p_184881_2_, this, "commands.time.query", new Object[]{i});
               return;
            }
         }
      }

      throw new WrongUsageException("commands.time.usage", new Object[0]);
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"set", "add", "query"});
      } else if (p_184883_3_.length == 2 && "set".equals(p_184883_3_[0])) {
         return func_71530_a(p_184883_3_, new String[]{"day", "night"});
      } else {
         return p_184883_3_.length == 2 && "query".equals(p_184883_3_[0]) ? func_71530_a(p_184883_3_, new String[]{"daytime", "gametime", "day"}) : Collections.emptyList();
      }
   }

   protected void func_184929_a(MinecraftServer p_184929_1_, int p_184929_2_) {
      for(int i = 0; i < p_184929_1_.field_71305_c.length; ++i) {
         p_184929_1_.field_71305_c[i].func_72877_b((long)p_184929_2_);
      }

   }

   protected void func_184928_b(MinecraftServer p_184928_1_, int p_184928_2_) {
      for(int i = 0; i < p_184928_1_.field_71305_c.length; ++i) {
         WorldServer worldserver = p_184928_1_.field_71305_c[i];
         worldserver.func_72877_b(worldserver.func_72820_D() + (long)p_184928_2_);
      }

   }
}
