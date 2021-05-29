package layer.data;

import java.util.Objects;

public final class Text {
    private final String title;
    private final String text;
    private final int length;

    public Text(String title, String text, int length) {
        this.text = text;
        this.length = length;
        this.title = title;
    }

    public Text(){
        this("no title", "no text set", 11);
    }

    public String getText() {
        return text;
    }

    public int getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return length == text1.length && Objects.equals(title, text1.title) && Objects.equals(text, text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text, length);
    }
}
