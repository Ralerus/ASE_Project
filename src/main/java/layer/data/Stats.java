package layer.data;

import java.util.List;

public abstract class Stats {
    private int numberOfCompetitons;
    private int numberOfTrainings;
    private List<HistoryEntry> highscore;

    public Stats(int numberOfCompetitons, int numberOfTrainings, List<HistoryEntry> highscore) {
        this.numberOfCompetitons = numberOfCompetitons;
        this.numberOfTrainings = numberOfTrainings;
        this.highscore = highscore;
    }

    public int getNumberOfCompetitons() {
        return numberOfCompetitons;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }

    public List<HistoryEntry> getHighscore() {
        return highscore;
    }
}
