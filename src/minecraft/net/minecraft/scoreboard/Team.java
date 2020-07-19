package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;

public abstract class Team
{
    /**
     * Same as ==
     */
    public boolean isSameTeam(@Nullable Team other)
    {
        if (other == null)
        {
            return false;
        }
        else
        {
            return this == other;
        }
    }

    /**
     * Retrieve the name by which this team is registered in the scoreboard
     */
    public abstract String getName();

    /**
     * Formats the given text as a member of this team, using the prefix and suffix.
     */
    public abstract String formatString(String input);

    /**
     * Checks whether members of this team can see other members that are invisible.
     */
    public abstract boolean getSeeFriendlyInvisiblesEnabled();

    /**
     * Checks whether friendly fire (PVP between members of the team) is allowed.
     */
    public abstract boolean getAllowFriendlyFire();

    /**
     * Gets the visibility flags for player name tags.
     */
    public abstract Team.EnumVisible getNameTagVisibility();

    /**
     * Gets the color for this team. The team color is used mainly for team kill objectives and team-specific setDisplay
     * usage; it does _not_ affect all situations (for instance, the prefix is used for the glowing effect).
     */
    public abstract TextFormatting getColor();

    public abstract Collection<String> getMembershipCollection();

    /**
     * Gets the visibility flags for player death messages.
     */
    public abstract Team.EnumVisible getDeathMessageVisibility();

    /**
     * Gets the rule to be used for handling collisions with members of this team.
     */
    public abstract Team.CollisionRule getCollisionRule();

    public static enum CollisionRule
    {
        ALWAYS("always", 0),
        NEVER("never", 1),
        HIDE_FOR_OTHER_TEAMS("pushOtherTeams", 2),
        HIDE_FOR_OWN_TEAM("pushOwnTeam", 3);

        private static final Map<String, Team.CollisionRule> nameMap = Maps.<String, Team.CollisionRule>newHashMap();
        public final String name;
        public final int id;

        public static String[] getNames()
        {
            return (String[])nameMap.keySet().toArray(new String[nameMap.size()]);
        }

        @Nullable
        public static Team.CollisionRule getByName(String nameIn)
        {
            return nameMap.get(nameIn);
        }

        private CollisionRule(String nameIn, int idIn)
        {
            this.name = nameIn;
            this.id = idIn;
        }

        static {
            for (Team.CollisionRule team$collisionrule : values())
            {
                nameMap.put(team$collisionrule.name, team$collisionrule);
            }
        }
    }

    public static enum EnumVisible
    {
        ALWAYS("always", 0),
        NEVER("never", 1),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

        private static final Map<String, Team.EnumVisible> nameMap = Maps.<String, Team.EnumVisible>newHashMap();
        public final String internalName;
        public final int id;

        public static String[] getNames()
        {
            return (String[])nameMap.keySet().toArray(new String[nameMap.size()]);
        }

        @Nullable
        public static Team.EnumVisible getByName(String nameIn)
        {
            return nameMap.get(nameIn);
        }

        private EnumVisible(String nameIn, int idIn)
        {
            this.internalName = nameIn;
            this.id = idIn;
        }

        static {
            for (Team.EnumVisible team$enumvisible : values())
            {
                nameMap.put(team$enumvisible.internalName, team$enumvisible);
            }
        }
    }
}
