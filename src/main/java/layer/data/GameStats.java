package layer.data;

import java.util.List;

public class GameStats extends Stats {
    private int numberOfPlayers;

    public GameStats(int numberOfCompetitons, int numberOfTrainings, int numberOfPlayers, List<StatsEntry> highscore) {
        super(numberOfCompetitons,numberOfTrainings, highscore);
        this.numberOfPlayers = numberOfPlayers;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
