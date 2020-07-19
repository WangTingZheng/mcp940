package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty extends CommandBase {
   public String func_71517_b() {
      return "difficulty";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.difficulty.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length <= 0) {
         throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
      } else {
         EnumDifficulty enumdifficulty = this.func_180531_e(p_184881_3_[0]);
         p_184881_1_.func_147139_a(enumdifficulty);
         func_152373_a(p_184881_2_, this, "commands.difficulty.success", new Object[]{new TextComponentTranslation(enumdifficulty.func_151526_b(), new Object[0])});
      }
   }

   protected EnumDifficulty func_180531_e(String p_180531_1_) throws CommandException, NumberInvalidException {
      if (!"peaceful".equalsIgnoreCase(p_180531_1_) && !"p".equalsIgnoreCase(p_180531_1_)) {
         if (!"easy".equalsIgnoreCase(p_180531_1_) && !"e".equalsIgnoreCase(p_180531_1_)) {
            if (!"normal".equalsIgnoreCase(p_180531_1_) && !"n".equalsIgnoreCase(p_180531_1_)) {
               return !"hard".equalsIgnoreCase(p_180531_1_) && !"h".equalsIgnoreCase(p_180531_1_) ? EnumDifficulty.func_151523_a(func_175764_a(p_180531_1_, 0, 3)) : EnumDifficulty.HARD;
            } else {
               return EnumDifficulty.NORMAL;
            }
         } else {
            return EnumDifficulty.EASY;
         }
      } else {
         return EnumDifficulty.PEACEFUL;
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, new String[]{"peaceful", "easy", "normal", "hard"}) : Collections.emptyList();
   }
}
