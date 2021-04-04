package layer.data;

import java.util.HashMap;
import java.util.Map;

public class GameStats extends Stats {
    private int numberOfPlayers;
    private Map<String, Double> highscore;

    public GameStats(int numberOfCompetitons, int numberOfTrainings, int numberOfPlayers) {
        super(numberOfCompetitons,numberOfTrainings);
        this.numberOfPlayers = numberOfPlayers;
        this.highscore = new HashMap<>();
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
