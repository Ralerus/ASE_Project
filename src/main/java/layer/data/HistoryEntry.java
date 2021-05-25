package layer.data;

import java.time.Instant;

public class HistoryEntry {
    private String username;
    private double value;
    private String textTitle;
    private String date; //TODO change to real date format

    public HistoryEntry(String username, double value, String textTitle, String date) {
        this.username = username;
        this.value = value;
        this.textTitle = textTitle;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public double getValue() {
        return value;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getDate() {
        return date;
    }
}
