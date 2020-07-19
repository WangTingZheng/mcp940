package net.minecraft.scoreboard;

public class ScoreObjective
{
    private final Scoreboard scoreboard;
    private final String name;

    /** The ScoreObjectiveCriteria for this objetive */
    private final IScoreCriteria objectiveCriteria;
    private IScoreCriteria.EnumRenderType renderType;
    private String displayName;

    public ScoreObjective(Scoreboard scoreboard, String nameIn, IScoreCriteria objectiveCriteriaIn)
    {
        this.scoreboard = scoreboard;
        this.name = nameIn;
        this.objectiveCriteria = objectiveCriteriaIn;
        this.displayName = nameIn;
        this.renderType = objectiveCriteriaIn.getRenderType();
    }

    public Scoreboard getScoreboard()
    {
        return this.scoreboard;
    }

    public String getName()
    {
        return this.name;
    }

    public IScoreCriteria getCriteria()
    {
        return this.objectiveCriteria;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public void setDisplayName(String nameIn)
    {
        this.displayName = nameIn;
        this.scoreboard.onObjectiveDisplayNameChanged(this);
    }

    public IScoreCriteria.EnumRenderType getRenderType()
    {
        return this.renderType;
    }

    public void setRenderType(IScoreCriteria.EnumRenderType type)
    {
        this.renderType = type;
        this.scoreboard.onObjectiveDisplayNameChanged(this);
    }
}
