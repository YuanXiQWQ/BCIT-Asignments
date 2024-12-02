/**
 * AbstractGame class implements the Game interface and provides basic game statistics.
 *
 * @author Jiarui Xing
 */
public abstract class AbstractGame implements Game {
    protected int gamesWon;
    protected int gamesLost;
    protected int totalSuccessfulPlacements;

    public AbstractGame()
    {
        gamesWon = 0;
        gamesLost = 0;
        totalSuccessfulPlacements = 0;
    }

    @Override
    public abstract void start();

    public void incrementGamesWon()
    {
        gamesWon++;
    }

    public void incrementGamesLost()
    {
        gamesLost++;
    }

    public void addSuccessfulPlacements(final int count)
    {
        totalSuccessfulPlacements += count;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public int getGamesLost()
    {
        return gamesLost;
    }

    public int getTotalSuccessfulPlacements()
    {
        return totalSuccessfulPlacements;
    }
}
