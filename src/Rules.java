public class Rules {
    private Difficulty difficulty;
    private int lengthOfText;

    public Rules(Difficulty difficulty, int lengthOfText) {
        this.difficulty = difficulty;
        this.lengthOfText = lengthOfText;
    }

    public String selectTextBasedOnRules(){
        return "Lorem ipsum dolor sit amet.";
    }
}
