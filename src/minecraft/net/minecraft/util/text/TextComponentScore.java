package net.minecraft.util.text;

import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

public class TextComponentScore extends TextComponentBase
{
    private final String name;
    private final String objective;

    /** The value displayed instead of the real score (may be null) */
    private String value = "";

    public TextComponentScore(String nameIn, String objectiveIn)
    {
        this.name = nameIn;
        this.objective = objectiveIn;
    }

    /**
     * Gets the name of the entity who owns this score.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the name of the objective for this score.
     */
    public String getObjective()
    {
        return this.objective;
    }

    /**
     * Sets the value that is displayed for the score. Generally, you do not want to call this as the score is resolved
     * automatically. (If you want to manually set text, use a {@link TextComponentString})
     */
    public void setValue(String valueIn)
    {
        this.value = valueIn;
    }

    /**
     * Gets the raw content of this component (but not its sibling components), without any formatting codes. For
     * example, this is the raw text in a {@link TextComponentString}, but it's the translated text for a {@link
     * TextComponentTranslation} and it's the score value for a {@link TextComponentScore}.
     */
    public String getUnformattedComponentText()
    {
        return this.value;
    }

    /**
     * Resolves the value of the score on this component.
     */
    public void resolve(ICommandSender sender)
    {
        MinecraftServer minecraftserver = sender.getServer();

        if (minecraftserver != null && minecraftserver.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value))
        {
            Scoreboard scoreboard = minecraftserver.getWorld(0).getScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjective(this.objective);

            if (scoreboard.entityHasObjective(this.name, scoreobjective))
            {
                Score score = scoreboard.getOrCreateScore(this.name, scoreobjective);
                this.setValue(String.format("%d", score.getScorePoints()));
            }
            else
            {
                this.value = "";
            }
        }
    }

    /**
     * Creates a copy of this component.  Almost a deep copy, except the style is shallow-copied.
     */
    public TextComponentScore createCopy()
    {
        TextComponentScore textcomponentscore = new TextComponentScore(this.name, this.objective);
        textcomponentscore.setValue(this.value);
        textcomponentscore.setStyle(this.getStyle().createShallowCopy());

        for (ITextComponent itextcomponent : this.getSiblings())
        {
            textcomponentscore.appendSibling(itextcomponent.createCopy());
        }

        return textcomponentscore;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof TextComponentScore))
        {
            return false;
        }
        else
        {
            TextComponentScore textcomponentscore = (TextComponentScore)p_equals_1_;
            return this.name.equals(textcomponentscore.name) && this.objective.equals(textcomponentscore.objective) && super.equals(p_equals_1_);
        }
    }

    public String toString()
    {
        return "ScoreComponent{name='" + this.name + '\'' + "objective='" + this.objective + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }
}
