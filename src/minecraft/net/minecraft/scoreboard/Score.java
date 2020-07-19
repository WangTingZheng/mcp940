package net.minecraft.scoreboard;

import java.util.Comparator;

public class Score
{
    public static final Comparator<Score> SCORE_COMPARATOR = new Comparator<Score>()
    {
        public int compare(Score p_compare_1_, Score p_compare_2_)
        {
            if (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints())
            {
                return 1;
            }
            else
            {
                return p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints() ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName());
            }
        }
    };
    private final Scoreboard scoreboard;
    private final ScoreObjective objective;
    private final String scorePlayerName;
    private int scorePoints;
    private boolean locked;
    private boolean forceUpdate;

    public Score(Scoreboard scoreboard, ScoreObjective objective, String playerName)
    {
        this.scoreboard = scoreboard;
        this.objective = objective;
        this.scorePlayerName = playerName;
        this.forceUpdate = true;
    }

    public void increaseScore(int amount)
    {
        if (this.objective.getCriteria().isReadOnly())
        {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        else
        {
            this.setScorePoints(this.getScorePoints() + amount);
        }
    }

    public void decreaseScore(int amount)
    {
        if (this.objective.getCriteria().isReadOnly())
        {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        else
        {
            this.setScorePoints(this.getScorePoints() - amount);
        }
    }

    public void incrementScore()
    {
        if (this.objective.getCriteria().isReadOnly())
        {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        else
        {
            this.increaseScore(1);
        }
    }

    public int getScorePoints()
    {
        return this.scorePoints;
    }

    public void setScorePoints(int points)
    {
        int i = this.scorePoints;
        this.scorePoints = points;

        if (i != points || this.forceUpdate)
        {
            this.forceUpdate = false;
            this.getScoreScoreboard().onScoreUpdated(this);
        }
    }

    public ScoreObjective getObjective()
    {
        return this.objective;
    }

    /**
     * Returns the name of the player this score belongs to
     */
    public String getPlayerName()
    {
        return this.scorePlayerName;
    }

    public Scoreboard getScoreScoreboard()
    {
        return this.scoreboard;
    }

    public boolean isLocked()
    {
        return this.locked;
    }

    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }
}
