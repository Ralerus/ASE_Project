package layer.domain;

import layer.data.Player;
import layer.data.Text;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Round {
    private List<Character> textLeft = new ArrayList<>();
    private long duration;
    private Player p;
    private Instant starttime;
    private Instant endtime;

    public Round(Text text, Player p) {
        this.textLeft = text.getText().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        this.duration = 0;
        this.p = p;
    }

    public boolean checkCurrentInputChar(char c){
        if(c == textLeft.get(0)){
            textLeft.remove(0);
            return true;
        }
        return false;
    }

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

    public long getDuration() {
        return duration;
    }
    
    public long playRound() {
        Instant start = Instant.now();
    	while(!this.textLeft.isEmpty()) {
         	//UI calls method check currentinputchar
    		//refresh string displayed on ui
    	}
    	Instant finish = Instant.now();
    	return Duration.between(start, finish).toMillis();
    }
    
    public void setStartTime() {
    	this.starttime = Instant.now();
    }
    public void setEndTime() {
    	this.endtime = Instant.now();
    	//compute time, send time to Game
    }
    
    
    
    //start & end time variable auf Klassenebene, bei playRound starten, Duration später berechnen
}
