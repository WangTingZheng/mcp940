package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.text.TextFormatting;

public interface IScoreCriteria
{
    Map<String, IScoreCriteria> INSTANCES = Maps.<String, IScoreCriteria>newHashMap();
    IScoreCriteria DUMMY = new ScoreCriteria("dummy");
    IScoreCriteria TRIGGER = new ScoreCriteria("trigger");
    IScoreCriteria DEATH_COUNT = new ScoreCriteria("deathCount");
    IScoreCriteria PLAYER_KILL_COUNT = new ScoreCriteria("playerKillCount");
    IScoreCriteria TOTAL_KILL_COUNT = new ScoreCriteria("totalKillCount");
    IScoreCriteria HEALTH = new ScoreCriteriaHealth("health");
    IScoreCriteria FOOD = new ScoreCriteriaReadOnly("food");
    IScoreCriteria AIR = new ScoreCriteriaReadOnly("air");
    IScoreCriteria ARMOR = new ScoreCriteriaReadOnly("armor");
    IScoreCriteria XP = new ScoreCriteriaReadOnly("xp");
    IScoreCriteria LEVEL = new ScoreCriteriaReadOnly("level");
    IScoreCriteria[] TEAM_KILL = new IScoreCriteria[] {new ScoreCriteriaColored("teamkill.", TextFormatting.BLACK), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_RED), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.GOLD), new ScoreCriteriaColored("teamkill.", TextFormatting.GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.RED), new ScoreCriteriaColored("teamkill.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.YELLOW), new ScoreCriteriaColored("teamkill.", TextFormatting.WHITE)};
    IScoreCriteria[] KILLED_BY_TEAM = new IScoreCriteria[] {new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLACK), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GOLD), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.YELLOW), new ScoreCriteriaColored("killedByTeam.", TextFormatting.WHITE)};

    String getName();

    boolean isReadOnly();

    IScoreCriteria.EnumRenderType getRenderType();

    public static enum EnumRenderType
    {
        INTEGER("integer"),
        HEARTS("hearts");

        private static final Map<String, IScoreCriteria.EnumRenderType> BY_NAME = Maps.<String, IScoreCriteria.EnumRenderType>newHashMap();
        private final String renderType;

        private EnumRenderType(String renderTypeIn)
        {
            this.renderType = renderTypeIn;
        }

        public String getRenderType()
        {
            return this.renderType;
        }

        public static IScoreCriteria.EnumRenderType getByName(String name)
        {
            IScoreCriteria.EnumRenderType iscorecriteria$enumrendertype = BY_NAME.get(name);
            return iscorecriteria$enumrendertype == null ? INTEGER : iscorecriteria$enumrendertype;
        }

        static {
            for (IScoreCriteria.EnumRenderType iscorecriteria$enumrendertype : values())
            {
                BY_NAME.put(iscorecriteria$enumrendertype.getRenderType(), iscorecriteria$enumrendertype);
            }
        }
    }
}
