package layer.data;

public class StatsEntry {
    private String username;
    private double speed;
    private String textTitle;
    private String formattedDate;

    public StatsEntry(String username, double speed, String textTitle, String formattedDate) {
        this.username = username;
        this.speed = speed;
        this.textTitle = textTitle;
        this.formattedDate = formattedDate;
    }

    public String getUsername() {
        return username;
    }

    public double getSpeed() {
        return speed;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
