package layer.data;

public class HistoryEntry {
    private String username;
    private double value;
    private String textTitle;
    private String formattedDate;

    public HistoryEntry(String username, double value, String textTitle, String formattedDate) {
        this.username = username;
        this.value = value;
        this.textTitle = textTitle;
        this.formattedDate = formattedDate;
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

    public String getFormattedDate() {
        return formattedDate;
    }
}
