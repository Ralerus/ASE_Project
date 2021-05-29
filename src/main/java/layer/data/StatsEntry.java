package layer.data;

import java.util.Objects;

public final class StatsEntry {
    private final String username;
    private final double speed;
    private final String textTitle;
    private final String formattedDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsEntry that = (StatsEntry) o;
        return Double.compare(that.speed, speed) == 0 && Objects.equals(username, that.username) && Objects.equals(textTitle, that.textTitle) && Objects.equals(formattedDate, that.formattedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, speed, textTitle, formattedDate);
    }
}
