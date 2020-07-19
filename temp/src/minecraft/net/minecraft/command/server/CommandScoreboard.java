package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandScoreboard extends CommandBase {
   public String func_71517_b() {
      return "scoreboard";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.scoreboard.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (!this.func_184909_b(p_184881_1_, p_184881_2_, p_184881_3_)) {
         if (p_184881_3_.length < 1) {
            throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
         } else {
            if ("objectives".equalsIgnoreCase(p_184881_3_[0])) {
               if (p_184881_3_.length == 1) {
                  throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
               }

               if ("list".equalsIgnoreCase(p_184881_3_[1])) {
                  this.func_184925_a(p_184881_2_, p_184881_1_);
               } else if ("add".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 4) {
                     throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                  }

                  this.func_184908_a(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("remove".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 3) {
                     throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                  }

                  this.func_184905_a(p_184881_2_, p_184881_3_[2], p_184881_1_);
               } else {
                  if (!"setdisplay".equalsIgnoreCase(p_184881_3_[1])) {
                     throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                  }

                  if (p_184881_3_.length != 3 && p_184881_3_.length != 4) {
                     throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                  }

                  this.func_184919_i(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               }
            } else if ("players".equalsIgnoreCase(p_184881_3_[0])) {
               if (p_184881_3_.length == 1) {
                  throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
               }

               if ("list".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length > 3) {
                     throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                  }

                  this.func_184920_j(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("add".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 5) {
                     throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                  }

                  this.func_184918_k(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("remove".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 5) {
                     throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                  }

                  this.func_184918_k(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("set".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 5) {
                     throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                  }

                  this.func_184918_k(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("reset".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 3 && p_184881_3_.length != 4) {
                     throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                  }

                  this.func_184912_l(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("enable".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 4) {
                     throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                  }

                  this.func_184914_m(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("test".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 5 && p_184881_3_.length != 6) {
                     throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                  }

                  this.func_184907_n(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("operation".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 7) {
                     throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                  }

                  this.func_184906_o(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else {
                  if (!"tag".equalsIgnoreCase(p_184881_3_[1])) {
                     throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                  }

                  if (p_184881_3_.length < 4) {
                     throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
                  }

                  this.func_184924_a(p_184881_1_, p_184881_2_, p_184881_3_, 2);
               }
            } else {
               if (!"teams".equalsIgnoreCase(p_184881_3_[0])) {
                  throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
               }

               if (p_184881_3_.length == 1) {
                  throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
               }

               if ("list".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length > 3) {
                     throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                  }

                  this.func_184922_e(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("add".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 3) {
                     throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                  }

                  this.func_184910_b(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("remove".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 3) {
                     throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                  }

                  this.func_184921_d(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("empty".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length != 3) {
                     throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                  }

                  this.func_184917_h(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("join".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 4 && (p_184881_3_.length != 3 || !(p_184881_2_ instanceof EntityPlayer))) {
                     throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                  }

                  this.func_184916_f(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else if ("leave".equalsIgnoreCase(p_184881_3_[1])) {
                  if (p_184881_3_.length < 3 && !(p_184881_2_ instanceof EntityPlayer)) {
                     throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                  }

                  this.func_184911_g(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               } else {
                  if (!"option".equalsIgnoreCase(p_184881_3_[1])) {
                     throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                  }

                  if (p_184881_3_.length != 4 && p_184881_3_.length != 5) {
                     throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                  }

                  this.func_184923_c(p_184881_2_, p_184881_3_, 2, p_184881_1_);
               }
            }

         }
      }
   }

   private boolean func_184909_b(MinecraftServer p_184909_1_, ICommandSender p_184909_2_, String[] p_184909_3_) throws CommandException {
      int i = -1;

      for(int j = 0; j < p_184909_3_.length; ++j) {
         if (this.func_82358_a(p_184909_3_, j) && "*".equals(p_184909_3_[j])) {
            if (i >= 0) {
               throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
            }

            i = j;
         }
      }

      if (i < 0) {
         return false;
      } else {
         List<String> list1 = Lists.newArrayList(this.func_184913_a(p_184909_1_).func_96526_d());
         String s = p_184909_3_[i];
         List<String> list = Lists.<String>newArrayList();

         for(String s1 : list1) {
            p_184909_3_[i] = s1;

            try {
               this.func_184881_a(p_184909_1_, p_184909_2_, p_184909_3_);
               list.add(s1);
            } catch (CommandException commandexception) {
               TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(commandexception.getMessage(), commandexception.func_74844_a());
               textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.RED);
               p_184909_2_.func_145747_a(textcomponenttranslation);
            }
         }

         p_184909_3_[i] = s;
         p_184909_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
         if (list.isEmpty()) {
            throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
         } else {
            return true;
         }
      }
   }

   protected Scoreboard func_184913_a(MinecraftServer p_184913_1_) {
      return p_184913_1_.func_71218_a(0).func_96441_U();
   }

   protected ScoreObjective func_184903_a(String p_184903_1_, boolean p_184903_2_, MinecraftServer p_184903_3_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184903_3_);
      ScoreObjective scoreobjective = scoreboard.func_96518_b(p_184903_1_);
      if (scoreobjective == null) {
         throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[]{p_184903_1_});
      } else if (p_184903_2_ && scoreobjective.func_96680_c().func_96637_b()) {
         throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[]{p_184903_1_});
      } else {
         return scoreobjective;
      }
   }

   protected ScorePlayerTeam func_184915_a(String p_184915_1_, MinecraftServer p_184915_2_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184915_2_);
      ScorePlayerTeam scoreplayerteam = scoreboard.func_96508_e(p_184915_1_);
      if (scoreplayerteam == null) {
         throw new CommandException("commands.scoreboard.teamNotFound", new Object[]{p_184915_1_});
      } else {
         return scoreplayerteam;
      }
   }

   protected void func_184908_a(ICommandSender p_184908_1_, String[] p_184908_2_, int p_184908_3_, MinecraftServer p_184908_4_) throws CommandException {
      String s = p_184908_2_[p_184908_3_++];
      String s1 = p_184908_2_[p_184908_3_++];
      Scoreboard scoreboard = this.func_184913_a(p_184908_4_);
      IScoreCriteria iscorecriteria = IScoreCriteria.field_96643_a.get(s1);
      if (iscorecriteria == null) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[]{s1});
      } else if (scoreboard.func_96518_b(s) != null) {
         throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[]{s});
      } else if (s.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[]{s, Integer.valueOf(16)});
      } else if (s.isEmpty()) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
      } else {
         if (p_184908_2_.length > p_184908_3_) {
            String s2 = func_147178_a(p_184908_1_, p_184908_2_, p_184908_3_).func_150260_c();
            if (s2.length() > 32) {
               throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[]{s2, Integer.valueOf(32)});
            }

            if (s2.isEmpty()) {
               scoreboard.func_96535_a(s, iscorecriteria);
            } else {
               scoreboard.func_96535_a(s, iscorecriteria).func_96681_a(s2);
            }
         } else {
            scoreboard.func_96535_a(s, iscorecriteria);
         }

         func_152373_a(p_184908_1_, this, "commands.scoreboard.objectives.add.success", new Object[]{s});
      }
   }

   protected void func_184910_b(ICommandSender p_184910_1_, String[] p_184910_2_, int p_184910_3_, MinecraftServer p_184910_4_) throws CommandException {
      String s = p_184910_2_[p_184910_3_++];
      Scoreboard scoreboard = this.func_184913_a(p_184910_4_);
      if (scoreboard.func_96508_e(s) != null) {
         throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[]{s});
      } else if (s.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[]{s, Integer.valueOf(16)});
      } else if (s.isEmpty()) {
         throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
      } else {
         if (p_184910_2_.length > p_184910_3_) {
            String s1 = func_147178_a(p_184910_1_, p_184910_2_, p_184910_3_).func_150260_c();
            if (s1.length() > 32) {
               throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[]{s1, Integer.valueOf(32)});
            }

            if (s1.isEmpty()) {
               scoreboard.func_96527_f(s);
            } else {
               scoreboard.func_96527_f(s).func_96664_a(s1);
            }
         } else {
            scoreboard.func_96527_f(s);
         }

         func_152373_a(p_184910_1_, this, "commands.scoreboard.teams.add.success", new Object[]{s});
      }
   }

   protected void func_184923_c(ICommandSender p_184923_1_, String[] p_184923_2_, int p_184923_3_, MinecraftServer p_184923_4_) throws CommandException {
      ScorePlayerTeam scoreplayerteam = this.func_184915_a(p_184923_2_[p_184923_3_++], p_184923_4_);
      if (scoreplayerteam != null) {
         String s = p_184923_2_[p_184923_3_++].toLowerCase(Locale.ROOT);
         if (!"color".equalsIgnoreCase(s) && !"friendlyfire".equalsIgnoreCase(s) && !"seeFriendlyInvisibles".equalsIgnoreCase(s) && !"nametagVisibility".equalsIgnoreCase(s) && !"deathMessageVisibility".equalsIgnoreCase(s) && !"collisionRule".equalsIgnoreCase(s)) {
            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
         } else if (p_184923_2_.length == 4) {
            if ("color".equalsIgnoreCase(s)) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_96333_a(TextFormatting.func_96296_a(true, false))});
            } else if (!"friendlyfire".equalsIgnoreCase(s) && !"seeFriendlyInvisibles".equalsIgnoreCase(s)) {
               if (!"nametagVisibility".equalsIgnoreCase(s) && !"deathMessageVisibility".equalsIgnoreCase(s)) {
                  if ("collisionRule".equalsIgnoreCase(s)) {
                     throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_71527_a(Team.CollisionRule.func_186687_a())});
                  } else {
                     throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                  }
               } else {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_71527_a(Team.EnumVisible.func_178825_a())});
               }
            } else {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_96333_a(Arrays.asList("true", "false"))});
            }
         } else {
            String s1 = p_184923_2_[p_184923_3_];
            if ("color".equalsIgnoreCase(s)) {
               TextFormatting textformatting = TextFormatting.func_96300_b(s1);
               if (textformatting == null || textformatting.func_96301_b()) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_96333_a(TextFormatting.func_96296_a(true, false))});
               }

               scoreplayerteam.func_178774_a(textformatting);
               scoreplayerteam.func_96666_b(textformatting.toString());
               scoreplayerteam.func_96662_c(TextFormatting.RESET.toString());
            } else if ("friendlyfire".equalsIgnoreCase(s)) {
               if (!"true".equalsIgnoreCase(s1) && !"false".equalsIgnoreCase(s1)) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_96333_a(Arrays.asList("true", "false"))});
               }

               scoreplayerteam.func_96660_a("true".equalsIgnoreCase(s1));
            } else if ("seeFriendlyInvisibles".equalsIgnoreCase(s)) {
               if (!"true".equalsIgnoreCase(s1) && !"false".equalsIgnoreCase(s1)) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_96333_a(Arrays.asList("true", "false"))});
               }

               scoreplayerteam.func_98300_b("true".equalsIgnoreCase(s1));
            } else if ("nametagVisibility".equalsIgnoreCase(s)) {
               Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(s1);
               if (team$enumvisible == null) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_71527_a(Team.EnumVisible.func_178825_a())});
               }

               scoreplayerteam.func_178772_a(team$enumvisible);
            } else if ("deathMessageVisibility".equalsIgnoreCase(s)) {
               Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(s1);
               if (team$enumvisible1 == null) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_71527_a(Team.EnumVisible.func_178825_a())});
               }

               scoreplayerteam.func_178773_b(team$enumvisible1);
            } else if ("collisionRule".equalsIgnoreCase(s)) {
               Team.CollisionRule team$collisionrule = Team.CollisionRule.func_186686_a(s1);
               if (team$collisionrule == null) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{s, func_71527_a(Team.CollisionRule.func_186687_a())});
               }

               scoreplayerteam.func_186682_a(team$collisionrule);
            }

            func_152373_a(p_184923_1_, this, "commands.scoreboard.teams.option.success", new Object[]{s, scoreplayerteam.func_96661_b(), s1});
         }
      }
   }

   protected void func_184921_d(ICommandSender p_184921_1_, String[] p_184921_2_, int p_184921_3_, MinecraftServer p_184921_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184921_4_);
      ScorePlayerTeam scoreplayerteam = this.func_184915_a(p_184921_2_[p_184921_3_], p_184921_4_);
      if (scoreplayerteam != null) {
         scoreboard.func_96511_d(scoreplayerteam);
         func_152373_a(p_184921_1_, this, "commands.scoreboard.teams.remove.success", new Object[]{scoreplayerteam.func_96661_b()});
      }
   }

   protected void func_184922_e(ICommandSender p_184922_1_, String[] p_184922_2_, int p_184922_3_, MinecraftServer p_184922_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184922_4_);
      if (p_184922_2_.length > p_184922_3_) {
         ScorePlayerTeam scoreplayerteam = this.func_184915_a(p_184922_2_[p_184922_3_], p_184922_4_);
         if (scoreplayerteam == null) {
            return;
         }

         Collection<String> collection = scoreplayerteam.func_96670_d();
         p_184922_1_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, collection.size());
         if (collection.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[]{scoreplayerteam.func_96661_b()});
         }

         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[]{collection.size(), scoreplayerteam.func_96661_b()});
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184922_1_.func_145747_a(textcomponenttranslation);
         p_184922_1_.func_145747_a(new TextComponentString(func_71527_a(collection.toArray())));
      } else {
         Collection<ScorePlayerTeam> collection1 = scoreboard.func_96525_g();
         p_184922_1_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, collection1.size());
         if (collection1.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
         }

         TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.scoreboard.teams.list.count", new Object[]{collection1.size()});
         textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184922_1_.func_145747_a(textcomponenttranslation1);

         for(ScorePlayerTeam scoreplayerteam1 : collection1) {
            p_184922_1_.func_145747_a(new TextComponentTranslation("commands.scoreboard.teams.list.entry", new Object[]{scoreplayerteam1.func_96661_b(), scoreplayerteam1.func_96669_c(), scoreplayerteam1.func_96670_d().size()}));
         }
      }

   }

   protected void func_184916_f(ICommandSender p_184916_1_, String[] p_184916_2_, int p_184916_3_, MinecraftServer p_184916_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184916_4_);
      String s = p_184916_2_[p_184916_3_++];
      Set<String> set = Sets.<String>newHashSet();
      Set<String> set1 = Sets.<String>newHashSet();
      if (p_184916_1_ instanceof EntityPlayer && p_184916_3_ == p_184916_2_.length) {
         String s4 = func_71521_c(p_184916_1_).func_70005_c_();
         if (scoreboard.func_151392_a(s4, s)) {
            set.add(s4);
         } else {
            set1.add(s4);
         }
      } else {
         while(p_184916_3_ < p_184916_2_.length) {
            String s1 = p_184916_2_[p_184916_3_++];
            if (EntitySelector.func_82378_b(s1)) {
               for(Entity entity : func_184890_c(p_184916_4_, p_184916_1_, s1)) {
                  String s3 = func_184891_e(p_184916_4_, p_184916_1_, entity.func_189512_bd());
                  if (scoreboard.func_151392_a(s3, s)) {
                     set.add(s3);
                  } else {
                     set1.add(s3);
                  }
               }
            } else {
               String s2 = func_184891_e(p_184916_4_, p_184916_1_, s1);
               if (scoreboard.func_151392_a(s2, s)) {
                  set.add(s2);
               } else {
                  set1.add(s2);
               }
            }
         }
      }

      if (!set.isEmpty()) {
         p_184916_1_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
         func_152373_a(p_184916_1_, this, "commands.scoreboard.teams.join.success", new Object[]{set.size(), s, func_71527_a(set.toArray(new String[set.size()]))});
      }

      if (!set1.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.join.failure", new Object[]{set1.size(), s, func_71527_a(set1.toArray(new String[set1.size()]))});
      }
   }

   protected void func_184911_g(ICommandSender p_184911_1_, String[] p_184911_2_, int p_184911_3_, MinecraftServer p_184911_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184911_4_);
      Set<String> set = Sets.<String>newHashSet();
      Set<String> set1 = Sets.<String>newHashSet();
      if (p_184911_1_ instanceof EntityPlayer && p_184911_3_ == p_184911_2_.length) {
         String s3 = func_71521_c(p_184911_1_).func_70005_c_();
         if (scoreboard.func_96524_g(s3)) {
            set.add(s3);
         } else {
            set1.add(s3);
         }
      } else {
         while(p_184911_3_ < p_184911_2_.length) {
            String s = p_184911_2_[p_184911_3_++];
            if (EntitySelector.func_82378_b(s)) {
               for(Entity entity : func_184890_c(p_184911_4_, p_184911_1_, s)) {
                  String s2 = func_184891_e(p_184911_4_, p_184911_1_, entity.func_189512_bd());
                  if (scoreboard.func_96524_g(s2)) {
                     set.add(s2);
                  } else {
                     set1.add(s2);
                  }
               }
            } else {
               String s1 = func_184891_e(p_184911_4_, p_184911_1_, s);
               if (scoreboard.func_96524_g(s1)) {
                  set.add(s1);
               } else {
                  set1.add(s1);
               }
            }
         }
      }

      if (!set.isEmpty()) {
         p_184911_1_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
         func_152373_a(p_184911_1_, this, "commands.scoreboard.teams.leave.success", new Object[]{set.size(), func_71527_a(set.toArray(new String[set.size()]))});
      }

      if (!set1.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[]{set1.size(), func_71527_a(set1.toArray(new String[set1.size()]))});
      }
   }

   protected void func_184917_h(ICommandSender p_184917_1_, String[] p_184917_2_, int p_184917_3_, MinecraftServer p_184917_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184917_4_);
      ScorePlayerTeam scoreplayerteam = this.func_184915_a(p_184917_2_[p_184917_3_], p_184917_4_);
      if (scoreplayerteam != null) {
         Collection<String> collection = Lists.newArrayList(scoreplayerteam.func_96670_d());
         p_184917_1_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, collection.size());
         if (collection.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[]{scoreplayerteam.func_96661_b()});
         } else {
            for(String s : collection) {
               scoreboard.func_96512_b(s, scoreplayerteam);
            }

            func_152373_a(p_184917_1_, this, "commands.scoreboard.teams.empty.success", new Object[]{collection.size(), scoreplayerteam.func_96661_b()});
         }
      }
   }

   protected void func_184905_a(ICommandSender p_184905_1_, String p_184905_2_, MinecraftServer p_184905_3_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184905_3_);
      ScoreObjective scoreobjective = this.func_184903_a(p_184905_2_, false, p_184905_3_);
      scoreboard.func_96519_k(scoreobjective);
      func_152373_a(p_184905_1_, this, "commands.scoreboard.objectives.remove.success", new Object[]{p_184905_2_});
   }

   protected void func_184925_a(ICommandSender p_184925_1_, MinecraftServer p_184925_2_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184925_2_);
      Collection<ScoreObjective> collection = scoreboard.func_96514_c();
      if (collection.isEmpty()) {
         throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
      } else {
         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.objectives.list.count", new Object[]{collection.size()});
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184925_1_.func_145747_a(textcomponenttranslation);

         for(ScoreObjective scoreobjective : collection) {
            p_184925_1_.func_145747_a(new TextComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[]{scoreobjective.func_96679_b(), scoreobjective.func_96678_d(), scoreobjective.func_96680_c().func_96636_a()}));
         }

      }
   }

   protected void func_184919_i(ICommandSender p_184919_1_, String[] p_184919_2_, int p_184919_3_, MinecraftServer p_184919_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184919_4_);
      String s = p_184919_2_[p_184919_3_++];
      int i = Scoreboard.func_96537_j(s);
      ScoreObjective scoreobjective = null;
      if (p_184919_2_.length == 4) {
         scoreobjective = this.func_184903_a(p_184919_2_[p_184919_3_], false, p_184919_4_);
      }

      if (i < 0) {
         throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[]{s});
      } else {
         scoreboard.func_96530_a(i, scoreobjective);
         if (scoreobjective != null) {
            func_152373_a(p_184919_1_, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[]{Scoreboard.func_96517_b(i), scoreobjective.func_96679_b()});
         } else {
            func_152373_a(p_184919_1_, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[]{Scoreboard.func_96517_b(i)});
         }

      }
   }

   protected void func_184920_j(ICommandSender p_184920_1_, String[] p_184920_2_, int p_184920_3_, MinecraftServer p_184920_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184920_4_);
      if (p_184920_2_.length > p_184920_3_) {
         String s = func_184891_e(p_184920_4_, p_184920_1_, p_184920_2_[p_184920_3_]);
         Map<ScoreObjective, Score> map = scoreboard.func_96510_d(s);
         p_184920_1_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, map.size());
         if (map.isEmpty()) {
            throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[]{s});
         }

         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.players.list.player.count", new Object[]{map.size(), s});
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184920_1_.func_145747_a(textcomponenttranslation);

         for(Score score : map.values()) {
            p_184920_1_.func_145747_a(new TextComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[]{score.func_96652_c(), score.func_96645_d().func_96678_d(), score.func_96645_d().func_96679_b()}));
         }
      } else {
         Collection<String> collection = scoreboard.func_96526_d();
         p_184920_1_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, collection.size());
         if (collection.isEmpty()) {
            throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
         }

         TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.scoreboard.players.list.count", new Object[]{collection.size()});
         textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184920_1_.func_145747_a(textcomponenttranslation1);
         p_184920_1_.func_145747_a(new TextComponentString(func_71527_a(collection.toArray())));
      }

   }

   protected void func_184918_k(ICommandSender p_184918_1_, String[] p_184918_2_, int p_184918_3_, MinecraftServer p_184918_4_) throws CommandException {
      String s = p_184918_2_[p_184918_3_ - 1];
      int i = p_184918_3_;
      String s1 = func_184891_e(p_184918_4_, p_184918_1_, p_184918_2_[p_184918_3_++]);
      if (s1.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{s1, Integer.valueOf(40)});
      } else {
         ScoreObjective scoreobjective = this.func_184903_a(p_184918_2_[p_184918_3_++], true, p_184918_4_);
         int j = "set".equalsIgnoreCase(s) ? func_175755_a(p_184918_2_[p_184918_3_++]) : func_180528_a(p_184918_2_[p_184918_3_++], 0);
         if (p_184918_2_.length > p_184918_3_) {
            Entity entity = func_184885_b(p_184918_4_, p_184918_1_, p_184918_2_[i]);

            try {
               NBTTagCompound nbttagcompound = JsonToNBT.func_180713_a(func_180529_a(p_184918_2_, p_184918_3_));
               NBTTagCompound nbttagcompound1 = func_184887_a(entity);
               if (!NBTUtil.func_181123_a(nbttagcompound, nbttagcompound1, true)) {
                  throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[]{s1});
               }
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.scoreboard.players.set.tagError", new Object[]{nbtexception.getMessage()});
            }
         }

         Scoreboard scoreboard = this.func_184913_a(p_184918_4_);
         Score score = scoreboard.func_96529_a(s1, scoreobjective);
         if ("set".equalsIgnoreCase(s)) {
            score.func_96647_c(j);
         } else if ("add".equalsIgnoreCase(s)) {
            score.func_96649_a(j);
         } else {
            score.func_96646_b(j);
         }

         func_152373_a(p_184918_1_, this, "commands.scoreboard.players.set.success", new Object[]{scoreobjective.func_96679_b(), s1, score.func_96652_c()});
      }
   }

   protected void func_184912_l(ICommandSender p_184912_1_, String[] p_184912_2_, int p_184912_3_, MinecraftServer p_184912_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184912_4_);
      String s = func_184891_e(p_184912_4_, p_184912_1_, p_184912_2_[p_184912_3_++]);
      if (p_184912_2_.length > p_184912_3_) {
         ScoreObjective scoreobjective = this.func_184903_a(p_184912_2_[p_184912_3_++], false, p_184912_4_);
         scoreboard.func_178822_d(s, scoreobjective);
         func_152373_a(p_184912_1_, this, "commands.scoreboard.players.resetscore.success", new Object[]{scoreobjective.func_96679_b(), s});
      } else {
         scoreboard.func_178822_d(s, (ScoreObjective)null);
         func_152373_a(p_184912_1_, this, "commands.scoreboard.players.reset.success", new Object[]{s});
      }

   }

   protected void func_184914_m(ICommandSender p_184914_1_, String[] p_184914_2_, int p_184914_3_, MinecraftServer p_184914_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184914_4_);
      String s = func_184886_d(p_184914_4_, p_184914_1_, p_184914_2_[p_184914_3_++]);
      if (s.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{s, Integer.valueOf(40)});
      } else {
         ScoreObjective scoreobjective = this.func_184903_a(p_184914_2_[p_184914_3_], false, p_184914_4_);
         if (scoreobjective.func_96680_c() != IScoreCriteria.field_178791_c) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[]{scoreobjective.func_96679_b()});
         } else {
            Score score = scoreboard.func_96529_a(s, scoreobjective);
            score.func_178815_a(false);
            func_152373_a(p_184914_1_, this, "commands.scoreboard.players.enable.success", new Object[]{scoreobjective.func_96679_b(), s});
         }
      }
   }

   protected void func_184907_n(ICommandSender p_184907_1_, String[] p_184907_2_, int p_184907_3_, MinecraftServer p_184907_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184907_4_);
      String s = func_184891_e(p_184907_4_, p_184907_1_, p_184907_2_[p_184907_3_++]);
      if (s.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{s, Integer.valueOf(40)});
      } else {
         ScoreObjective scoreobjective = this.func_184903_a(p_184907_2_[p_184907_3_++], false, p_184907_4_);
         if (!scoreboard.func_178819_b(s, scoreobjective)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[]{scoreobjective.func_96679_b(), s});
         } else {
            int i = p_184907_2_[p_184907_3_].equals("*") ? Integer.MIN_VALUE : func_175755_a(p_184907_2_[p_184907_3_]);
            ++p_184907_3_;
            int j = p_184907_3_ < p_184907_2_.length && !p_184907_2_[p_184907_3_].equals("*") ? func_180528_a(p_184907_2_[p_184907_3_], i) : Integer.MAX_VALUE;
            Score score = scoreboard.func_96529_a(s, scoreobjective);
            if (score.func_96652_c() >= i && score.func_96652_c() <= j) {
               func_152373_a(p_184907_1_, this, "commands.scoreboard.players.test.success", new Object[]{score.func_96652_c(), i, j});
            } else {
               throw new CommandException("commands.scoreboard.players.test.failed", new Object[]{score.func_96652_c(), i, j});
            }
         }
      }
   }

   protected void func_184906_o(ICommandSender p_184906_1_, String[] p_184906_2_, int p_184906_3_, MinecraftServer p_184906_4_) throws CommandException {
      Scoreboard scoreboard = this.func_184913_a(p_184906_4_);
      String s = func_184891_e(p_184906_4_, p_184906_1_, p_184906_2_[p_184906_3_++]);
      ScoreObjective scoreobjective = this.func_184903_a(p_184906_2_[p_184906_3_++], true, p_184906_4_);
      String s1 = p_184906_2_[p_184906_3_++];
      String s2 = func_184891_e(p_184906_4_, p_184906_1_, p_184906_2_[p_184906_3_++]);
      ScoreObjective scoreobjective1 = this.func_184903_a(p_184906_2_[p_184906_3_], false, p_184906_4_);
      if (s.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{s, Integer.valueOf(40)});
      } else if (s2.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{s2, Integer.valueOf(40)});
      } else {
         Score score = scoreboard.func_96529_a(s, scoreobjective);
         if (!scoreboard.func_178819_b(s2, scoreobjective1)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[]{scoreobjective1.func_96679_b(), s2});
         } else {
            Score score1 = scoreboard.func_96529_a(s2, scoreobjective1);
            if ("+=".equals(s1)) {
               score.func_96647_c(score.func_96652_c() + score1.func_96652_c());
            } else if ("-=".equals(s1)) {
               score.func_96647_c(score.func_96652_c() - score1.func_96652_c());
            } else if ("*=".equals(s1)) {
               score.func_96647_c(score.func_96652_c() * score1.func_96652_c());
            } else if ("/=".equals(s1)) {
               if (score1.func_96652_c() != 0) {
                  score.func_96647_c(score.func_96652_c() / score1.func_96652_c());
               }
            } else if ("%=".equals(s1)) {
               if (score1.func_96652_c() != 0) {
                  score.func_96647_c(score.func_96652_c() % score1.func_96652_c());
               }
            } else if ("=".equals(s1)) {
               score.func_96647_c(score1.func_96652_c());
            } else if ("<".equals(s1)) {
               score.func_96647_c(Math.min(score.func_96652_c(), score1.func_96652_c()));
            } else if (">".equals(s1)) {
               score.func_96647_c(Math.max(score.func_96652_c(), score1.func_96652_c()));
            } else {
               if (!"><".equals(s1)) {
                  throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[]{s1});
               }

               int i = score.func_96652_c();
               score.func_96647_c(score1.func_96652_c());
               score1.func_96647_c(i);
            }

            func_152373_a(p_184906_1_, this, "commands.scoreboard.players.operation.success", new Object[0]);
         }
      }
   }

   protected void func_184924_a(MinecraftServer p_184924_1_, ICommandSender p_184924_2_, String[] p_184924_3_, int p_184924_4_) throws CommandException {
      String s = func_184891_e(p_184924_1_, p_184924_2_, p_184924_3_[p_184924_4_]);
      Entity entity = func_184885_b(p_184924_1_, p_184924_2_, p_184924_3_[p_184924_4_++]);
      String s1 = p_184924_3_[p_184924_4_++];
      Set<String> set = entity.func_184216_O();
      if ("list".equals(s1)) {
         if (!set.isEmpty()) {
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.players.tag.list", new Object[]{s});
            textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
            p_184924_2_.func_145747_a(textcomponenttranslation);
            p_184924_2_.func_145747_a(new TextComponentString(func_71527_a(set.toArray())));
         }

         p_184924_2_.func_174794_a(CommandResultStats.Type.QUERY_RESULT, set.size());
      } else if (p_184924_3_.length < 5) {
         throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
      } else {
         String s2 = p_184924_3_[p_184924_4_++];
         if (p_184924_3_.length > p_184924_4_) {
            try {
               NBTTagCompound nbttagcompound = JsonToNBT.func_180713_a(func_180529_a(p_184924_3_, p_184924_4_));
               NBTTagCompound nbttagcompound1 = func_184887_a(entity);
               if (!NBTUtil.func_181123_a(nbttagcompound, nbttagcompound1, true)) {
                  throw new CommandException("commands.scoreboard.players.tag.tagMismatch", new Object[]{s});
               }
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.scoreboard.players.tag.tagError", new Object[]{nbtexception.getMessage()});
            }
         }

         if ("add".equals(s1)) {
            if (!entity.func_184211_a(s2)) {
               throw new CommandException("commands.scoreboard.players.tag.tooMany", new Object[]{Integer.valueOf(1024)});
            }

            func_152373_a(p_184924_2_, this, "commands.scoreboard.players.tag.success.add", new Object[]{s2});
         } else {
            if (!"remove".equals(s1)) {
               throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
            }

            if (!entity.func_184197_b(s2)) {
               throw new CommandException("commands.scoreboard.players.tag.notFound", new Object[]{s2});
            }

            func_152373_a(p_184924_2_, this, "commands.scoreboard.players.tag.success.remove", new Object[]{s2});
         }

      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"objectives", "players", "teams"});
      } else {
         if ("objectives".equalsIgnoreCase(p_184883_3_[0])) {
            if (p_184883_3_.length == 2) {
               return func_71530_a(p_184883_3_, new String[]{"list", "add", "remove", "setdisplay"});
            }

            if ("add".equalsIgnoreCase(p_184883_3_[1])) {
               if (p_184883_3_.length == 4) {
                  Set<String> set = IScoreCriteria.field_96643_a.keySet();
                  return func_175762_a(p_184883_3_, set);
               }
            } else if ("remove".equalsIgnoreCase(p_184883_3_[1])) {
               if (p_184883_3_.length == 3) {
                  return func_175762_a(p_184883_3_, this.func_184926_a(false, p_184883_1_));
               }
            } else if ("setdisplay".equalsIgnoreCase(p_184883_3_[1])) {
               if (p_184883_3_.length == 3) {
                  return func_71530_a(p_184883_3_, Scoreboard.func_178821_h());
               }

               if (p_184883_3_.length == 4) {
                  return func_175762_a(p_184883_3_, this.func_184926_a(false, p_184883_1_));
               }
            }
         } else if ("players".equalsIgnoreCase(p_184883_3_[0])) {
            if (p_184883_3_.length == 2) {
               return func_71530_a(p_184883_3_, new String[]{"set", "add", "remove", "reset", "list", "enable", "test", "operation", "tag"});
            }

            if (!"set".equalsIgnoreCase(p_184883_3_[1]) && !"add".equalsIgnoreCase(p_184883_3_[1]) && !"remove".equalsIgnoreCase(p_184883_3_[1]) && !"reset".equalsIgnoreCase(p_184883_3_[1])) {
               if ("enable".equalsIgnoreCase(p_184883_3_[1])) {
                  if (p_184883_3_.length == 3) {
                     return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
                  }

                  if (p_184883_3_.length == 4) {
                     return func_175762_a(p_184883_3_, this.func_184904_b(p_184883_1_));
                  }
               } else if (!"list".equalsIgnoreCase(p_184883_3_[1]) && !"test".equalsIgnoreCase(p_184883_3_[1])) {
                  if ("operation".equalsIgnoreCase(p_184883_3_[1])) {
                     if (p_184883_3_.length == 3) {
                        return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96526_d());
                     }

                     if (p_184883_3_.length == 4) {
                        return func_175762_a(p_184883_3_, this.func_184926_a(true, p_184883_1_));
                     }

                     if (p_184883_3_.length == 5) {
                        return func_71530_a(p_184883_3_, new String[]{"+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><"});
                     }

                     if (p_184883_3_.length == 6) {
                        return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
                     }

                     if (p_184883_3_.length == 7) {
                        return func_175762_a(p_184883_3_, this.func_184926_a(false, p_184883_1_));
                     }
                  } else if ("tag".equalsIgnoreCase(p_184883_3_[1])) {
                     if (p_184883_3_.length == 3) {
                        return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96526_d());
                     }

                     if (p_184883_3_.length == 4) {
                        return func_71530_a(p_184883_3_, new String[]{"add", "remove", "list"});
                     }
                  }
               } else {
                  if (p_184883_3_.length == 3) {
                     return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96526_d());
                  }

                  if (p_184883_3_.length == 4 && "test".equalsIgnoreCase(p_184883_3_[1])) {
                     return func_175762_a(p_184883_3_, this.func_184926_a(false, p_184883_1_));
                  }
               }
            } else {
               if (p_184883_3_.length == 3) {
                  return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
               }

               if (p_184883_3_.length == 4) {
                  return func_175762_a(p_184883_3_, this.func_184926_a(true, p_184883_1_));
               }
            }
         } else if ("teams".equalsIgnoreCase(p_184883_3_[0])) {
            if (p_184883_3_.length == 2) {
               return func_71530_a(p_184883_3_, new String[]{"add", "remove", "join", "leave", "empty", "list", "option"});
            }

            if ("join".equalsIgnoreCase(p_184883_3_[1])) {
               if (p_184883_3_.length == 3) {
                  return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96531_f());
               }

               if (p_184883_3_.length >= 4) {
                  return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
               }
            } else {
               if ("leave".equalsIgnoreCase(p_184883_3_[1])) {
                  return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
               }

               if (!"empty".equalsIgnoreCase(p_184883_3_[1]) && !"list".equalsIgnoreCase(p_184883_3_[1]) && !"remove".equalsIgnoreCase(p_184883_3_[1])) {
                  if ("option".equalsIgnoreCase(p_184883_3_[1])) {
                     if (p_184883_3_.length == 3) {
                        return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96531_f());
                     }

                     if (p_184883_3_.length == 4) {
                        return func_71530_a(p_184883_3_, new String[]{"color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility", "collisionRule"});
                     }

                     if (p_184883_3_.length == 5) {
                        if ("color".equalsIgnoreCase(p_184883_3_[3])) {
                           return func_175762_a(p_184883_3_, TextFormatting.func_96296_a(true, false));
                        }

                        if ("nametagVisibility".equalsIgnoreCase(p_184883_3_[3]) || "deathMessageVisibility".equalsIgnoreCase(p_184883_3_[3])) {
                           return func_71530_a(p_184883_3_, Team.EnumVisible.func_178825_a());
                        }

                        if ("collisionRule".equalsIgnoreCase(p_184883_3_[3])) {
                           return func_71530_a(p_184883_3_, Team.CollisionRule.func_186687_a());
                        }

                        if ("friendlyfire".equalsIgnoreCase(p_184883_3_[3]) || "seeFriendlyInvisibles".equalsIgnoreCase(p_184883_3_[3])) {
                           return func_71530_a(p_184883_3_, new String[]{"true", "false"});
                        }
                     }
                  }
               } else if (p_184883_3_.length == 3) {
                  return func_175762_a(p_184883_3_, this.func_184913_a(p_184883_1_).func_96531_f());
               }
            }
         }

         return Collections.<String>emptyList();
      }
   }

   protected List<String> func_184926_a(boolean p_184926_1_, MinecraftServer p_184926_2_) {
      Collection<ScoreObjective> collection = this.func_184913_a(p_184926_2_).func_96514_c();
      List<String> list = Lists.<String>newArrayList();

      for(ScoreObjective scoreobjective : collection) {
         if (!p_184926_1_ || !scoreobjective.func_96680_c().func_96637_b()) {
            list.add(scoreobjective.func_96679_b());
         }
      }

      return list;
   }

   protected List<String> func_184904_b(MinecraftServer p_184904_1_) {
      Collection<ScoreObjective> collection = this.func_184913_a(p_184904_1_).func_96514_c();
      List<String> list = Lists.<String>newArrayList();

      for(ScoreObjective scoreobjective : collection) {
         if (scoreobjective.func_96680_c() == IScoreCriteria.field_178791_c) {
            list.add(scoreobjective.func_96679_b());
         }
      }

      return list;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      if (!"players".equalsIgnoreCase(p_82358_1_[0])) {
         if ("teams".equalsIgnoreCase(p_82358_1_[0])) {
            return p_82358_2_ == 2;
         } else {
            return false;
         }
      } else if (p_82358_1_.length > 1 && "operation".equalsIgnoreCase(p_82358_1_[1])) {
         return p_82358_2_ == 2 || p_82358_2_ == 5;
      } else {
         return p_82358_2_ == 2;
      }
   }
}
