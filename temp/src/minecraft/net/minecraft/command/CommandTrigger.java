package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandTrigger extends CommandBase {
   public String func_71517_b() {
      return "trigger";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.trigger.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 3) {
         throw new WrongUsageException("commands.trigger.usage", new Object[0]);
      } else {
         EntityPlayerMP entityplayermp;
         if (p_184881_2_ instanceof EntityPlayerMP) {
            entityplayermp = (EntityPlayerMP)p_184881_2_;
         } else {
            Entity entity = p_184881_2_.func_174793_f();
            if (!(entity instanceof EntityPlayerMP)) {
               throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }

            entityplayermp = (EntityPlayerMP)entity;
         }

         Scoreboard scoreboard = p_184881_1_.func_71218_a(0).func_96441_U();
         ScoreObjective scoreobjective = scoreboard.func_96518_b(p_184881_3_[0]);
         if (scoreobjective != null && scoreobjective.func_96680_c() == IScoreCriteria.field_178791_c) {
            int i = func_175755_a(p_184881_3_[2]);
            if (!scoreboard.func_178819_b(entityplayermp.func_70005_c_(), scoreobjective)) {
               throw new CommandException("commands.trigger.invalidObjective", new Object[]{p_184881_3_[0]});
            } else {
               Score score = scoreboard.func_96529_a(entityplayermp.func_70005_c_(), scoreobjective);
               if (score.func_178816_g()) {
                  throw new CommandException("commands.trigger.disabled", new Object[]{p_184881_3_[0]});
               } else {
                  if ("set".equals(p_184881_3_[1])) {
                     score.func_96647_c(i);
                  } else {
                     if (!"add".equals(p_184881_3_[1])) {
                        throw new CommandException("commands.trigger.invalidMode", new Object[]{p_184881_3_[1]});
                     }

                     score.func_96649_a(i);
                  }

                  score.func_178815_a(true);
                  if (entityplayermp.field_71134_c.func_73083_d()) {
                     func_152373_a(p_184881_2_, this, "commands.trigger.success", new Object[]{p_184881_3_[0], p_184881_3_[1], p_184881_3_[2]});
                  }

               }
            }
         } else {
            throw new CommandException("commands.trigger.invalidObjective", new Object[]{p_184881_3_[0]});
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         Scoreboard scoreboard = p_184883_1_.func_71218_a(0).func_96441_U();
         List<String> list = Lists.<String>newArrayList();

         for(ScoreObjective scoreobjective : scoreboard.func_96514_c()) {
            if (scoreobjective.func_96680_c() == IScoreCriteria.field_178791_c) {
               list.add(scoreobjective.func_96679_b());
            }
         }

         return func_71530_a(p_184883_3_, (String[])list.toArray(new String[list.size()]));
      } else {
         return p_184883_3_.length == 2 ? func_71530_a(p_184883_3_, new String[]{"add", "set"}) : Collections.emptyList();
      }
   }
}
