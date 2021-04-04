package layer.data;

public class Text {
    private String title;
    private String text;
    private Difficulty difficulty;
    private int length;

    public Text(String title, String text, Difficulty difficulty, int length) {
        this.text = text;
        this.difficulty = difficulty;
        this.length = length;
        this.title = title;
    }

    public Text(){
        this("no title", "no text set", Difficulty.Easy, 11);
    }

    public String getText() {
        return text;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLength() {
        return length;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }
}
