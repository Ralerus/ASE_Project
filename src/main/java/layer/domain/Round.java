package layer.domain;

import layer.data.Player;
import layer.data.Text;
import layer.presentation.RoundUI;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Round implements RoundListener{
    private List<Character> textLeft;
    private Instant startTime;
    private Instant endTime;
    private GameListener listener;

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

    public Round(Text text) {
        this.textLeft = text.getText().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }
    @Override
    public boolean checkCurrentInputChar(char c){
        if(c == textLeft.get(0)){
            textLeft.remove(0);
            return true;
        }
        return false;
    }
    @Override
    public String getTextLeft() {
        StringBuilder sb = new StringBuilder();
        for (Character c : textLeft) {
            sb.append(c);
        }
        
        if(sb.toString().isEmpty()) {
        	this.setEndTime();
        }
        return sb.toString();
    }
    
    public void startRound() {
        RoundUI.setListener(this);;
        RoundUI.displayRoundFor(this.getTextLeft());
    }

    @Override
    public void setStartTime() {
        this.startTime = Instant.now();
    }

    private void setEndTime() {
    	this.endTime = Instant.now();
        double duration = (double) Duration.between(startTime, endTime).toMillis() / 1000;
        RoundUI.closeRound();
        this.listener.endRound(duration);
    }
}
