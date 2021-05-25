package layer.data;

import java.util.List;

public class PlayerStats extends Stats{
    private List<HistoryEntry> history;

    public PlayerStats(int numberOfCompetitons, int numberOfTrainings, List<HistoryEntry> history) {
        super(numberOfCompetitons, numberOfTrainings);
        this.history = history;
    }

    public List<HistoryEntry> getHistory() {
        return history;
    }
}
