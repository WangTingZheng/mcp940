package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandStats extends CommandBase {
   public String func_71517_b() {
      return "stats";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.stats.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.stats.usage", new Object[0]);
      } else {
         boolean flag;
         if ("entity".equals(p_184881_3_[0])) {
            flag = false;
         } else {
            if (!"block".equals(p_184881_3_[0])) {
               throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            flag = true;
         }

         int i;
         if (flag) {
            if (p_184881_3_.length < 5) {
               throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
            }

            i = 4;
         } else {
            if (p_184881_3_.length < 3) {
               throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
            }

            i = 2;
         }

         String s = p_184881_3_[i++];
         if ("set".equals(s)) {
            if (p_184881_3_.length < i + 3) {
               if (i == 5) {
                  throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
               }

               throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
            }
         } else {
            if (!"clear".equals(s)) {
               throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            if (p_184881_3_.length < i + 1) {
               if (i == 5) {
                  throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
               }

               throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
            }
         }

         CommandResultStats.Type commandresultstats$type = CommandResultStats.Type.func_179635_a(p_184881_3_[i++]);
         if (commandresultstats$type == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
         } else {
            World world = p_184881_2_.func_130014_f_();
            CommandResultStats commandresultstats;
            if (flag) {
               BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 1, false);
               TileEntity tileentity = world.func_175625_s(blockpos);
               if (tileentity == null) {
                  throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
               }

               if (tileentity instanceof TileEntityCommandBlock) {
                  commandresultstats = ((TileEntityCommandBlock)tileentity).func_175124_c();
               } else {
                  if (!(tileentity instanceof TileEntitySign)) {
                     throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
                  }

                  commandresultstats = ((TileEntitySign)tileentity).func_174880_d();
               }
            } else {
               Entity entity = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[1]);
               commandresultstats = entity.func_174807_aT();
            }

            if ("set".equals(s)) {
               String s1 = p_184881_3_[i++];
               String s2 = p_184881_3_[i];
               if (s1.isEmpty() || s2.isEmpty()) {
                  throw new CommandException("commands.stats.failed", new Object[0]);
               }

               CommandResultStats.func_179667_a(commandresultstats, commandresultstats$type, s1, s2);
               func_152373_a(p_184881_2_, this, "commands.stats.success", new Object[]{commandresultstats$type.func_179637_b(), s2, s1});
            } else if ("clear".equals(s)) {
               CommandResultStats.func_179667_a(commandresultstats, commandresultstats$type, (String)null, (String)null);
               func_152373_a(p_184881_2_, this, "commands.stats.cleared", new Object[]{commandresultstats$type.func_179637_b()});
            }

            if (flag) {
               BlockPos blockpos1 = func_175757_a(p_184881_2_, p_184881_3_, 1, false);
               TileEntity tileentity1 = world.func_175625_s(blockpos1);
               tileentity1.func_70296_d();
            }

         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"entity", "block"});
      } else if (p_184883_3_.length == 2 && "entity".equals(p_184883_3_[0])) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else if (p_184883_3_.length >= 2 && p_184883_3_.length <= 4 && "block".equals(p_184883_3_[0])) {
         return func_175771_a(p_184883_3_, 1, p_184883_4_);
      } else if ((p_184883_3_.length != 3 || !"entity".equals(p_184883_3_[0])) && (p_184883_3_.length != 5 || !"block".equals(p_184883_3_[0]))) {
         if ((p_184883_3_.length != 4 || !"entity".equals(p_184883_3_[0])) && (p_184883_3_.length != 6 || !"block".equals(p_184883_3_[0]))) {
            return (p_184883_3_.length != 6 || !"entity".equals(p_184883_3_[0])) && (p_184883_3_.length != 8 || !"block".equals(p_184883_3_[0])) ? Collections.emptyList() : func_175762_a(p_184883_3_, this.func_184927_a(p_184883_1_));
         } else {
            return func_71530_a(p_184883_3_, CommandResultStats.Type.func_179634_c());
         }
      } else {
         return func_71530_a(p_184883_3_, new String[]{"set", "clear"});
      }
   }

   protected List<String> func_184927_a(MinecraftServer p_184927_1_) {
      Collection<ScoreObjective> collection = p_184927_1_.func_71218_a(0).func_96441_U().func_96514_c();
      List<String> list = Lists.<String>newArrayList();

      for(ScoreObjective scoreobjective : collection) {
         if (!scoreobjective.func_96680_c().func_96637_b()) {
            list.add(scoreobjective.func_96679_b());
         }
      }

      return list;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_1_.length > 0 && "entity".equals(p_82358_1_[0]) && p_82358_2_ == 1;
   }
}
