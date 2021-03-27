package layer.domain;

import layer.data.Player;
import layer.data.Text;

public class Round {
    private String textLeft;
    private long duration;

    public Round(Text text) {
        this.textLeft = text.getText();
        this.duration = 0;
    }

    public boolean checkCurrentInputChar(char c){
        if(c == textLeft.toCharArray()[0]){
            //remove first char of textLeft
            return true;
        }
        return false;
    }

    public String getTextLeft() {
        return textLeft;
    }

    public long getDuration() {
        return duration;
    }
}
