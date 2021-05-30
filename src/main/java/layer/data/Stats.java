package layer.data;

import java.util.List;

public abstract class Stats {
    private final int numberOfCompetitons;
    private final int numberOfTrainings;
    private final List<StatsEntry> highscore;

    public Stats(int numberOfCompetitons, int numberOfTrainings, List<StatsEntry> highscore) {
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

    public List<StatsEntry> getHighscore() {
        return highscore;
    }
}
