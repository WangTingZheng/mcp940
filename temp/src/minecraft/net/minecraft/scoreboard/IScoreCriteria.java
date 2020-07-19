package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.text.TextFormatting;

public interface IScoreCriteria {
   Map<String, IScoreCriteria> field_96643_a = Maps.<String, IScoreCriteria>newHashMap();
   IScoreCriteria field_96641_b = new ScoreCriteria("dummy");
   IScoreCriteria field_178791_c = new ScoreCriteria("trigger");
   IScoreCriteria field_96642_c = new ScoreCriteria("deathCount");
   IScoreCriteria field_96639_d = new ScoreCriteria("playerKillCount");
   IScoreCriteria field_96640_e = new ScoreCriteria("totalKillCount");
   IScoreCriteria field_96638_f = new ScoreCriteriaHealth("health");
   IScoreCriteria field_186698_h = new ScoreCriteriaReadOnly("food");
   IScoreCriteria field_186699_i = new ScoreCriteriaReadOnly("air");
   IScoreCriteria field_186700_j = new ScoreCriteriaReadOnly("armor");
   IScoreCriteria field_186701_k = new ScoreCriteriaReadOnly("xp");
   IScoreCriteria field_186702_l = new ScoreCriteriaReadOnly("level");
   IScoreCriteria[] field_178792_h = new IScoreCriteria[]{new ScoreCriteriaColored("teamkill.", TextFormatting.BLACK), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_RED), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.GOLD), new ScoreCriteriaColored("teamkill.", TextFormatting.GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.RED), new ScoreCriteriaColored("teamkill.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.YELLOW), new ScoreCriteriaColored("teamkill.", TextFormatting.WHITE)};
   IScoreCriteria[] field_178793_i = new IScoreCriteria[]{new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLACK), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GOLD), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.YELLOW), new ScoreCriteriaColored("killedByTeam.", TextFormatting.WHITE)};

   String func_96636_a();

   boolean func_96637_b();

   IScoreCriteria.EnumRenderType func_178790_c();

   public static enum EnumRenderType {
      INTEGER("integer"),
      HEARTS("hearts");

      private static final Map<String, IScoreCriteria.EnumRenderType> field_178801_c = Maps.<String, IScoreCriteria.EnumRenderType>newHashMap();
      private final String field_178798_d;

      private EnumRenderType(String p_i45548_3_) {
         this.field_178798_d = p_i45548_3_;
      }

      public String func_178796_a() {
         return this.field_178798_d;
      }

      public static IScoreCriteria.EnumRenderType func_178795_a(String p_178795_0_) {
         IScoreCriteria.EnumRenderType iscorecriteria$enumrendertype = field_178801_c.get(p_178795_0_);
         return iscorecriteria$enumrendertype == null ? INTEGER : iscorecriteria$enumrendertype;
      }

      static {
         for(IScoreCriteria.EnumRenderType iscorecriteria$enumrendertype : values()) {
            field_178801_c.put(iscorecriteria$enumrendertype.func_178796_a(), iscorecriteria$enumrendertype);
         }

      }
   }
}
