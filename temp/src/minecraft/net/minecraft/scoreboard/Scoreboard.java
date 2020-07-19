package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class Scoreboard {
   private final Map<String, ScoreObjective> field_96545_a = Maps.<String, ScoreObjective>newHashMap();
   private final Map<IScoreCriteria, List<ScoreObjective>> field_96543_b = Maps.<IScoreCriteria, List<ScoreObjective>>newHashMap();
   private final Map<String, Map<ScoreObjective, Score>> field_96544_c = Maps.<String, Map<ScoreObjective, Score>>newHashMap();
   private final ScoreObjective[] field_96541_d = new ScoreObjective[19];
   private final Map<String, ScorePlayerTeam> field_96542_e = Maps.<String, ScorePlayerTeam>newHashMap();
   private final Map<String, ScorePlayerTeam> field_96540_f = Maps.<String, ScorePlayerTeam>newHashMap();
   private static String[] field_178823_g;

   @Nullable
   public ScoreObjective func_96518_b(String p_96518_1_) {
      return this.field_96545_a.get(p_96518_1_);
   }

   public ScoreObjective func_96535_a(String p_96535_1_, IScoreCriteria p_96535_2_) {
      if (p_96535_1_.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + p_96535_1_ + "' is too long!");
      } else {
         ScoreObjective scoreobjective = this.func_96518_b(p_96535_1_);
         if (scoreobjective != null) {
            throw new IllegalArgumentException("An objective with the name '" + p_96535_1_ + "' already exists!");
         } else {
            scoreobjective = new ScoreObjective(this, p_96535_1_, p_96535_2_);
            List<ScoreObjective> list = (List)this.field_96543_b.get(p_96535_2_);
            if (list == null) {
               list = Lists.<ScoreObjective>newArrayList();
               this.field_96543_b.put(p_96535_2_, list);
            }

            list.add(scoreobjective);
            this.field_96545_a.put(p_96535_1_, scoreobjective);
            this.func_96522_a(scoreobjective);
            return scoreobjective;
         }
      }
   }

   public Collection<ScoreObjective> func_96520_a(IScoreCriteria p_96520_1_) {
      Collection<ScoreObjective> collection = (Collection)this.field_96543_b.get(p_96520_1_);
      return collection == null ? Lists.newArrayList() : Lists.newArrayList(collection);
   }

   public boolean func_178819_b(String p_178819_1_, ScoreObjective p_178819_2_) {
      Map<ScoreObjective, Score> map = (Map)this.field_96544_c.get(p_178819_1_);
      if (map == null) {
         return false;
      } else {
         Score score = map.get(p_178819_2_);
         return score != null;
      }
   }

   public Score func_96529_a(String p_96529_1_, ScoreObjective p_96529_2_) {
      if (p_96529_1_.length() > 40) {
         throw new IllegalArgumentException("The player name '" + p_96529_1_ + "' is too long!");
      } else {
         Map<ScoreObjective, Score> map = (Map)this.field_96544_c.get(p_96529_1_);
         if (map == null) {
            map = Maps.<ScoreObjective, Score>newHashMap();
            this.field_96544_c.put(p_96529_1_, map);
         }

         Score score = map.get(p_96529_2_);
         if (score == null) {
            score = new Score(this, p_96529_2_, p_96529_1_);
            map.put(p_96529_2_, score);
         }

         return score;
      }
   }

   public Collection<Score> func_96534_i(ScoreObjective p_96534_1_) {
      List<Score> list = Lists.<Score>newArrayList();

      for(Map<ScoreObjective, Score> map : this.field_96544_c.values()) {
         Score score = map.get(p_96534_1_);
         if (score != null) {
            list.add(score);
         }
      }

      Collections.sort(list, Score.field_96658_a);
      return list;
   }

   public Collection<ScoreObjective> func_96514_c() {
      return this.field_96545_a.values();
   }

   public Collection<String> func_96526_d() {
      return this.field_96544_c.keySet();
   }

   public void func_178822_d(String p_178822_1_, ScoreObjective p_178822_2_) {
      if (p_178822_2_ == null) {
         Map<ScoreObjective, Score> map = (Map)this.field_96544_c.remove(p_178822_1_);
         if (map != null) {
            this.func_96516_a(p_178822_1_);
         }
      } else {
         Map<ScoreObjective, Score> map2 = (Map)this.field_96544_c.get(p_178822_1_);
         if (map2 != null) {
            Score score = map2.remove(p_178822_2_);
            if (map2.size() < 1) {
               Map<ScoreObjective, Score> map1 = (Map)this.field_96544_c.remove(p_178822_1_);
               if (map1 != null) {
                  this.func_96516_a(p_178822_1_);
               }
            } else if (score != null) {
               this.func_178820_a(p_178822_1_, p_178822_2_);
            }
         }
      }

   }

   public Collection<Score> func_96528_e() {
      Collection<Map<ScoreObjective, Score>> collection = this.field_96544_c.values();
      List<Score> list = Lists.<Score>newArrayList();

      for(Map<ScoreObjective, Score> map : collection) {
         list.addAll(map.values());
      }

      return list;
   }

   public Map<ScoreObjective, Score> func_96510_d(String p_96510_1_) {
      Map<ScoreObjective, Score> map = (Map)this.field_96544_c.get(p_96510_1_);
      if (map == null) {
         map = Maps.<ScoreObjective, Score>newHashMap();
      }

      return map;
   }

   public void func_96519_k(ScoreObjective p_96519_1_) {
      this.field_96545_a.remove(p_96519_1_.func_96679_b());

      for(int i = 0; i < 19; ++i) {
         if (this.func_96539_a(i) == p_96519_1_) {
            this.func_96530_a(i, (ScoreObjective)null);
         }
      }

      List<ScoreObjective> list = (List)this.field_96543_b.get(p_96519_1_.func_96680_c());
      if (list != null) {
         list.remove(p_96519_1_);
      }

      for(Map<ScoreObjective, Score> map : this.field_96544_c.values()) {
         map.remove(p_96519_1_);
      }

      this.func_96533_c(p_96519_1_);
   }

   public void func_96530_a(int p_96530_1_, ScoreObjective p_96530_2_) {
      this.field_96541_d[p_96530_1_] = p_96530_2_;
   }

   @Nullable
   public ScoreObjective func_96539_a(int p_96539_1_) {
      return this.field_96541_d[p_96539_1_];
   }

   public ScorePlayerTeam func_96508_e(String p_96508_1_) {
      return this.field_96542_e.get(p_96508_1_);
   }

   public ScorePlayerTeam func_96527_f(String p_96527_1_) {
      if (p_96527_1_.length() > 16) {
         throw new IllegalArgumentException("The team name '" + p_96527_1_ + "' is too long!");
      } else {
         ScorePlayerTeam scoreplayerteam = this.func_96508_e(p_96527_1_);
         if (scoreplayerteam != null) {
            throw new IllegalArgumentException("A team with the name '" + p_96527_1_ + "' already exists!");
         } else {
            scoreplayerteam = new ScorePlayerTeam(this, p_96527_1_);
            this.field_96542_e.put(p_96527_1_, scoreplayerteam);
            this.func_96523_a(scoreplayerteam);
            return scoreplayerteam;
         }
      }
   }

   public void func_96511_d(ScorePlayerTeam p_96511_1_) {
      this.field_96542_e.remove(p_96511_1_.func_96661_b());

      for(String s : p_96511_1_.func_96670_d()) {
         this.field_96540_f.remove(s);
      }

      this.func_96513_c(p_96511_1_);
   }

   public boolean func_151392_a(String p_151392_1_, String p_151392_2_) {
      if (p_151392_1_.length() > 40) {
         throw new IllegalArgumentException("The player name '" + p_151392_1_ + "' is too long!");
      } else if (!this.field_96542_e.containsKey(p_151392_2_)) {
         return false;
      } else {
         ScorePlayerTeam scoreplayerteam = this.func_96508_e(p_151392_2_);
         if (this.func_96509_i(p_151392_1_) != null) {
            this.func_96524_g(p_151392_1_);
         }

         this.field_96540_f.put(p_151392_1_, scoreplayerteam);
         scoreplayerteam.func_96670_d().add(p_151392_1_);
         return true;
      }
   }

   public boolean func_96524_g(String p_96524_1_) {
      ScorePlayerTeam scoreplayerteam = this.func_96509_i(p_96524_1_);
      if (scoreplayerteam != null) {
         this.func_96512_b(p_96524_1_, scoreplayerteam);
         return true;
      } else {
         return false;
      }
   }

   public void func_96512_b(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
      if (this.func_96509_i(p_96512_1_) != p_96512_2_) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_96512_2_.func_96661_b() + "'.");
      } else {
         this.field_96540_f.remove(p_96512_1_);
         p_96512_2_.func_96670_d().remove(p_96512_1_);
      }
   }

   public Collection<String> func_96531_f() {
      return this.field_96542_e.keySet();
   }

   public Collection<ScorePlayerTeam> func_96525_g() {
      return this.field_96542_e.values();
   }

   @Nullable
   public ScorePlayerTeam func_96509_i(String p_96509_1_) {
      return this.field_96540_f.get(p_96509_1_);
   }

   public void func_96522_a(ScoreObjective p_96522_1_) {
   }

   public void func_96532_b(ScoreObjective p_96532_1_) {
   }

   public void func_96533_c(ScoreObjective p_96533_1_) {
   }

   public void func_96536_a(Score p_96536_1_) {
   }

   public void func_96516_a(String p_96516_1_) {
   }

   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {
   }

   public void func_96523_a(ScorePlayerTeam p_96523_1_) {
   }

   public void func_96538_b(ScorePlayerTeam p_96538_1_) {
   }

   public void func_96513_c(ScorePlayerTeam p_96513_1_) {
   }

   public static String func_96517_b(int p_96517_0_) {
      switch(p_96517_0_) {
      case 0:
         return "list";
      case 1:
         return "sidebar";
      case 2:
         return "belowName";
      default:
         if (p_96517_0_ >= 3 && p_96517_0_ <= 18) {
            TextFormatting textformatting = TextFormatting.func_175744_a(p_96517_0_ - 3);
            if (textformatting != null && textformatting != TextFormatting.RESET) {
               return "sidebar.team." + textformatting.func_96297_d();
            }
         }

         return null;
      }
   }

   public static int func_96537_j(String p_96537_0_) {
      if ("list".equalsIgnoreCase(p_96537_0_)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(p_96537_0_)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(p_96537_0_)) {
         return 2;
      } else {
         if (p_96537_0_.startsWith("sidebar.team.")) {
            String s = p_96537_0_.substring("sidebar.team.".length());
            TextFormatting textformatting = TextFormatting.func_96300_b(s);
            if (textformatting != null && textformatting.func_175746_b() >= 0) {
               return textformatting.func_175746_b() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] func_178821_h() {
      if (field_178823_g == null) {
         field_178823_g = new String[19];

         for(int i = 0; i < 19; ++i) {
            field_178823_g[i] = func_96517_b(i);
         }
      }

      return field_178823_g;
   }

   public void func_181140_a(Entity p_181140_1_) {
      if (p_181140_1_ != null && !(p_181140_1_ instanceof EntityPlayer) && !p_181140_1_.func_70089_S()) {
         String s = p_181140_1_.func_189512_bd();
         this.func_178822_d(s, (ScoreObjective)null);
         this.func_96524_g(s);
      }
   }
}
