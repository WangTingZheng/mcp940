package net.minecraft.scoreboard;

public class ScoreCriteria implements IScoreCriteria
{
    private final String dummyName;

    public ScoreCriteria(String name)
    {
        this.dummyName = name;
        IScoreCriteria.INSTANCES.put(name, this);
    }

    public String getName()
    {
        return this.dummyName;
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
