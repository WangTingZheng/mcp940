package net.minecraft.scoreboard;

import net.minecraft.util.text.TextFormatting;

public class ScoreCriteriaColored implements IScoreCriteria
{
    private final String goalName;

    public ScoreCriteriaColored(String name, TextFormatting format)
    {
        this.goalName = name + format.getFriendlyName();
        IScoreCriteria.INSTANCES.put(this.goalName, this);
    }

    public String getName()
    {
        return this.goalName;
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public IScoreCriteria.EnumRenderType getRenderType()
    {
        return IScoreCriteria.EnumRenderType.INTEGER;
    }
}
