package layer.data;

import java.util.List;

public class GameStats extends Stats {
    private int numberOfPlayers;
    private List<HistoryEntry> highscore;

    public GameStats(int numberOfCompetitons, int numberOfTrainings, int numberOfPlayers, List<HistoryEntry> highscore) {
        super(numberOfCompetitons,numberOfTrainings);
        this.numberOfPlayers = numberOfPlayers;
        this.highscore = highscore;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public List<HistoryEntry> getHighscore() {
        return highscore;
    }
}
