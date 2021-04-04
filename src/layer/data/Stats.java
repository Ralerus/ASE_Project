package layer.data;

public class Stats {
    private int numberOfCompetitons;
    private int numberOfTrainings;

    public Stats(int numberOfCompetitons, int numberOfTrainings) {
        this.numberOfCompetitons = numberOfCompetitons;
        this.numberOfTrainings = numberOfTrainings;
    }

    public int getNumberOfCompetitons() {
        return numberOfCompetitons;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }
}
