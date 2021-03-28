package layer.domain;

public interface RoundListener {
    boolean checkCurrentInputChar(char c);
    String getTextLeft();
}
