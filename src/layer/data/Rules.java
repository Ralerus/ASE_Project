package layer.data;

public class Rules {
    private Difficulty difficulty;
    private int maxLengthOfText;
    private int minLengthOfText;

    public Rules(Difficulty difficulty,int minLengthOfText, int maxLengthOfText) {
        this.difficulty = difficulty;
        this.maxLengthOfText = maxLengthOfText;
        this.minLengthOfText = minLengthOfText;
    }

    public Rules(){
        this(Difficulty.Medium, 200,500);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMaxLengthOfText() {
        return maxLengthOfText;
    }

    public int getMinLengthOfText() {
        return minLengthOfText;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setMaxLengthOfText(int maxLengthOfText) {
        this.maxLengthOfText = maxLengthOfText;
    }

    public void setMinLengthOfText(int minLengthOfText) {
        this.minLengthOfText = minLengthOfText;
    }
}
