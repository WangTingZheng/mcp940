package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class CommandWeather extends CommandBase {
   public String func_71517_b() {
      return "weather";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.weather.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length >= 1 && p_184881_3_.length <= 2) {
         int i = (300 + (new Random()).nextInt(600)) * 20;
         if (p_184881_3_.length >= 2) {
            i = func_175764_a(p_184881_3_[1], 1, 1000000) * 20;
         }

         World world = p_184881_1_.field_71305_c[0];
         WorldInfo worldinfo = world.func_72912_H();
         if ("clear".equalsIgnoreCase(p_184881_3_[0])) {
            worldinfo.func_176142_i(i);
            worldinfo.func_76080_g(0);
            worldinfo.func_76090_f(0);
            worldinfo.func_76084_b(false);
            worldinfo.func_76069_a(false);
            func_152373_a(p_184881_2_, this, "commands.weather.clear", new Object[0]);
         } else if ("rain".equalsIgnoreCase(p_184881_3_[0])) {
            worldinfo.func_176142_i(0);
            worldinfo.func_76080_g(i);
            worldinfo.func_76090_f(i);
            worldinfo.func_76084_b(true);
            worldinfo.func_76069_a(false);
            func_152373_a(p_184881_2_, this, "commands.weather.rain", new Object[0]);
         } else {
            if (!"thunder".equalsIgnoreCase(p_184881_3_[0])) {
               throw new WrongUsageException("commands.weather.usage", new Object[0]);
            }

            worldinfo.func_176142_i(0);
            worldinfo.func_76080_g(i);
            worldinfo.func_76090_f(i);
            worldinfo.func_76084_b(true);
            worldinfo.func_76069_a(true);
            func_152373_a(p_184881_2_, this, "commands.weather.thunder", new Object[0]);
         }

      } else {
         throw new WrongUsageException("commands.weather.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, new String[]{"clear", "rain", "thunder"}) : Collections.emptyList();
   }
}
