package layer.data;

import java.util.List;

public final class GameStats extends Stats {
    private final int numberOfPlayers;

    public GameStats(int numberOfCompetitons, int numberOfTrainings, int numberOfPlayers, List<StatsEntry> highscore) {
        super(numberOfCompetitons,numberOfTrainings, highscore);
        this.numberOfPlayers = numberOfPlayers;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
