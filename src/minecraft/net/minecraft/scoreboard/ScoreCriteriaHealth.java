package net.minecraft.scoreboard;

public class ScoreCriteriaHealth extends ScoreCriteria
{
    public ScoreCriteriaHealth(String name)
    {
        super(name);
    }

    public boolean isReadOnly()
    {
        return true;
    }

    public IScoreCriteria.EnumRenderType getRenderType()
    {
        return IScoreCriteria.EnumRenderType.HEARTS;
    }
}
