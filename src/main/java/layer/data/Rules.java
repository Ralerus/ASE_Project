package layer.data;

import java.util.Objects;

public final class Rules {
    private final Difficulty difficulty;
    private final int maxLengthOfText;
    private final int minLengthOfText;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rules rules = (Rules) o;
        return maxLengthOfText == rules.maxLengthOfText && minLengthOfText == rules.minLengthOfText && difficulty == rules.difficulty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(difficulty, maxLengthOfText, minLengthOfText);
    }
}
