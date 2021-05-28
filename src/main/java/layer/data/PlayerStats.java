package layer.data;

import java.util.List;

public class PlayerStats extends Stats{
    private List<StatsEntry> history;

    public PlayerStats(int numberOfCompetitons, int numberOfTrainings, List<StatsEntry> history, List<StatsEntry> highscore) {
        super(numberOfCompetitons, numberOfTrainings, highscore);
        this.history = history;
    }

    public List<StatsEntry> getHistory() {
        return history;
    }
}
