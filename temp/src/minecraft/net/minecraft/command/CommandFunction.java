package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CommandFunction extends CommandBase {
   public String func_71517_b() {
      return "function";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.function.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length != 1 && p_184881_3_.length != 3) {
         throw new WrongUsageException("commands.function.usage", new Object[0]);
      } else {
         ResourceLocation resourcelocation = new ResourceLocation(p_184881_3_[0]);
         FunctionObject functionobject = p_184881_1_.func_193030_aL().func_193058_a(resourcelocation);
         if (functionobject == null) {
            throw new CommandException("commands.function.unknown", new Object[]{resourcelocation});
         } else {
            if (p_184881_3_.length == 3) {
               String s = p_184881_3_[1];
               boolean flag;
               if ("if".equals(s)) {
                  flag = true;
               } else {
                  if (!"unless".equals(s)) {
                     throw new WrongUsageException("commands.function.usage", new Object[0]);
                  }

                  flag = false;
               }

               boolean flag1 = false;

               try {
                  flag1 = !func_184890_c(p_184881_1_, p_184881_2_, p_184881_3_[2]).isEmpty();
               } catch (EntityNotFoundException var10) {
                  ;
               }

               if (flag != flag1) {
                  throw new CommandException("commands.function.skipped", new Object[]{resourcelocation});
               }
            }

            int i = p_184881_1_.func_193030_aL().func_194019_a(functionobject, CommandSenderWrapper.func_193998_a(p_184881_2_).func_194000_i().func_193999_a(2).func_194001_a(false));
            func_152373_a(p_184881_2_, this, "commands.function.success", new Object[]{resourcelocation, i});
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_175762_a(p_184883_3_, p_184883_1_.func_193030_aL().func_193066_d().keySet());
      } else if (p_184883_3_.length == 2) {
         return func_71530_a(p_184883_3_, new String[]{"if", "unless"});
      } else {
         return p_184883_3_.length == 3 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
      }
   }
}
