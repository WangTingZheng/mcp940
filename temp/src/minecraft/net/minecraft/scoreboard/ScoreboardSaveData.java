package net.minecraft.scoreboard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData {
   private static final Logger field_151481_a = LogManager.getLogger();
   private Scoreboard field_96507_a;
   private NBTTagCompound field_96506_b;

   public ScoreboardSaveData() {
      this("scoreboard");
   }

   public ScoreboardSaveData(String p_i2310_1_) {
      super(p_i2310_1_);
   }

   public void func_96499_a(Scoreboard p_96499_1_) {
      this.field_96507_a = p_96499_1_;
      if (this.field_96506_b != null) {
         this.func_76184_a(this.field_96506_b);
      }

   }

   public void func_76184_a(NBTTagCompound p_76184_1_) {
      if (this.field_96507_a == null) {
         this.field_96506_b = p_76184_1_;
      } else {
         this.func_96501_b(p_76184_1_.func_150295_c("Objectives", 10));
         this.func_96500_c(p_76184_1_.func_150295_c("PlayerScores", 10));
         if (p_76184_1_.func_150297_b("DisplaySlots", 10)) {
            this.func_96504_c(p_76184_1_.func_74775_l("DisplaySlots"));
         }

         if (p_76184_1_.func_150297_b("Teams", 9)) {
            this.func_96498_a(p_76184_1_.func_150295_c("Teams", 10));
         }

      }
   }

   protected void func_96498_a(NBTTagList p_96498_1_) {
      for(int i = 0; i < p_96498_1_.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = p_96498_1_.func_150305_b(i);
         String s = nbttagcompound.func_74779_i("Name");
         if (s.length() > 16) {
            s = s.substring(0, 16);
         }

         ScorePlayerTeam scoreplayerteam = this.field_96507_a.func_96527_f(s);
         String s1 = nbttagcompound.func_74779_i("DisplayName");
         if (s1.length() > 32) {
            s1 = s1.substring(0, 32);
         }

         scoreplayerteam.func_96664_a(s1);
         if (nbttagcompound.func_150297_b("TeamColor", 8)) {
            scoreplayerteam.func_178774_a(TextFormatting.func_96300_b(nbttagcompound.func_74779_i("TeamColor")));
         }

         scoreplayerteam.func_96666_b(nbttagcompound.func_74779_i("Prefix"));
         scoreplayerteam.func_96662_c(nbttagcompound.func_74779_i("Suffix"));
         if (nbttagcompound.func_150297_b("AllowFriendlyFire", 99)) {
            scoreplayerteam.func_96660_a(nbttagcompound.func_74767_n("AllowFriendlyFire"));
         }

         if (nbttagcompound.func_150297_b("SeeFriendlyInvisibles", 99)) {
            scoreplayerteam.func_98300_b(nbttagcompound.func_74767_n("SeeFriendlyInvisibles"));
         }

         if (nbttagcompound.func_150297_b("NameTagVisibility", 8)) {
            Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(nbttagcompound.func_74779_i("NameTagVisibility"));
            if (team$enumvisible != null) {
               scoreplayerteam.func_178772_a(team$enumvisible);
            }
         }

         if (nbttagcompound.func_150297_b("DeathMessageVisibility", 8)) {
            Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(nbttagcompound.func_74779_i("DeathMessageVisibility"));
            if (team$enumvisible1 != null) {
               scoreplayerteam.func_178773_b(team$enumvisible1);
            }
         }

         if (nbttagcompound.func_150297_b("CollisionRule", 8)) {
            Team.CollisionRule team$collisionrule = Team.CollisionRule.func_186686_a(nbttagcompound.func_74779_i("CollisionRule"));
            if (team$collisionrule != null) {
               scoreplayerteam.func_186682_a(team$collisionrule);
            }
         }

         this.func_96502_a(scoreplayerteam, nbttagcompound.func_150295_c("Players", 8));
      }

   }

   protected void func_96502_a(ScorePlayerTeam p_96502_1_, NBTTagList p_96502_2_) {
      for(int i = 0; i < p_96502_2_.func_74745_c(); ++i) {
         this.field_96507_a.func_151392_a(p_96502_2_.func_150307_f(i), p_96502_1_.func_96661_b());
      }

   }

   protected void func_96504_c(NBTTagCompound p_96504_1_) {
      for(int i = 0; i < 19; ++i) {
         if (p_96504_1_.func_150297_b("slot_" + i, 8)) {
            String s = p_96504_1_.func_74779_i("slot_" + i);
            ScoreObjective scoreobjective = this.field_96507_a.func_96518_b(s);
            this.field_96507_a.func_96530_a(i, scoreobjective);
         }
      }

   }

   protected void func_96501_b(NBTTagList p_96501_1_) {
      for(int i = 0; i < p_96501_1_.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = p_96501_1_.func_150305_b(i);
         IScoreCriteria iscorecriteria = IScoreCriteria.field_96643_a.get(nbttagcompound.func_74779_i("CriteriaName"));
         if (iscorecriteria != null) {
            String s = nbttagcompound.func_74779_i("Name");
            if (s.length() > 16) {
               s = s.substring(0, 16);
            }

            ScoreObjective scoreobjective = this.field_96507_a.func_96535_a(s, iscorecriteria);
            scoreobjective.func_96681_a(nbttagcompound.func_74779_i("DisplayName"));
            scoreobjective.func_178767_a(IScoreCriteria.EnumRenderType.func_178795_a(nbttagcompound.func_74779_i("RenderType")));
         }
      }

   }

   protected void func_96500_c(NBTTagList p_96500_1_) {
      for(int i = 0; i < p_96500_1_.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = p_96500_1_.func_150305_b(i);
         ScoreObjective scoreobjective = this.field_96507_a.func_96518_b(nbttagcompound.func_74779_i("Objective"));
         String s = nbttagcompound.func_74779_i("Name");
         if (s.length() > 40) {
            s = s.substring(0, 40);
         }

         Score score = this.field_96507_a.func_96529_a(s, scoreobjective);
         score.func_96647_c(nbttagcompound.func_74762_e("Score"));
         if (nbttagcompound.func_74764_b("Locked")) {
            score.func_178815_a(nbttagcompound.func_74767_n("Locked"));
         }
      }

   }

   public NBTTagCompound func_189551_b(NBTTagCompound p_189551_1_) {
      if (this.field_96507_a == null) {
         field_151481_a.warn("Tried to save scoreboard without having a scoreboard...");
         return p_189551_1_;
      } else {
         p_189551_1_.func_74782_a("Objectives", this.func_96505_b());
         p_189551_1_.func_74782_a("PlayerScores", this.func_96503_e());
         p_189551_1_.func_74782_a("Teams", this.func_96496_a());
         this.func_96497_d(p_189551_1_);
         return p_189551_1_;
      }
   }

   protected NBTTagList func_96496_a() {
      NBTTagList nbttaglist = new NBTTagList();

      for(ScorePlayerTeam scoreplayerteam : this.field_96507_a.func_96525_g()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74778_a("Name", scoreplayerteam.func_96661_b());
         nbttagcompound.func_74778_a("DisplayName", scoreplayerteam.func_96669_c());
         if (scoreplayerteam.func_178775_l().func_175746_b() >= 0) {
            nbttagcompound.func_74778_a("TeamColor", scoreplayerteam.func_178775_l().func_96297_d());
         }

         nbttagcompound.func_74778_a("Prefix", scoreplayerteam.func_96668_e());
         nbttagcompound.func_74778_a("Suffix", scoreplayerteam.func_96663_f());
         nbttagcompound.func_74757_a("AllowFriendlyFire", scoreplayerteam.func_96665_g());
         nbttagcompound.func_74757_a("SeeFriendlyInvisibles", scoreplayerteam.func_98297_h());
         nbttagcompound.func_74778_a("NameTagVisibility", scoreplayerteam.func_178770_i().field_178830_e);
         nbttagcompound.func_74778_a("DeathMessageVisibility", scoreplayerteam.func_178771_j().field_178830_e);
         nbttagcompound.func_74778_a("CollisionRule", scoreplayerteam.func_186681_k().field_186693_e);
         NBTTagList nbttaglist1 = new NBTTagList();

         for(String s : scoreplayerteam.func_96670_d()) {
            nbttaglist1.func_74742_a(new NBTTagString(s));
         }

         nbttagcompound.func_74782_a("Players", nbttaglist1);
         nbttaglist.func_74742_a(nbttagcompound);
      }

      return nbttaglist;
   }

   protected void func_96497_d(NBTTagCompound p_96497_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      boolean flag = false;

      for(int i = 0; i < 19; ++i) {
         ScoreObjective scoreobjective = this.field_96507_a.func_96539_a(i);
         if (scoreobjective != null) {
            nbttagcompound.func_74778_a("slot_" + i, scoreobjective.func_96679_b());
            flag = true;
         }
      }

      if (flag) {
         p_96497_1_.func_74782_a("DisplaySlots", nbttagcompound);
      }

   }

   protected NBTTagList func_96505_b() {
      NBTTagList nbttaglist = new NBTTagList();

      for(ScoreObjective scoreobjective : this.field_96507_a.func_96514_c()) {
         if (scoreobjective.func_96680_c() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74778_a("Name", scoreobjective.func_96679_b());
            nbttagcompound.func_74778_a("CriteriaName", scoreobjective.func_96680_c().func_96636_a());
            nbttagcompound.func_74778_a("DisplayName", scoreobjective.func_96678_d());
            nbttagcompound.func_74778_a("RenderType", scoreobjective.func_178766_e().func_178796_a());
            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   protected NBTTagList func_96503_e() {
      NBTTagList nbttaglist = new NBTTagList();

      for(Score score : this.field_96507_a.func_96528_e()) {
         if (score.func_96645_d() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74778_a("Name", score.func_96653_e());
            nbttagcompound.func_74778_a("Objective", score.func_96645_d().func_96679_b());
            nbttagcompound.func_74768_a("Score", score.func_96652_c());
            nbttagcompound.func_74757_a("Locked", score.func_178816_g());
            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      return nbttaglist;
   }
}
