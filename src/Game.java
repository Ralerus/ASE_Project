import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private Map<Player, Long> playerWithResult = new HashMap<>();
    private Rules rules;
    private List<Character> text = new ArrayList<>();
    private List<Character> text_copy = new ArrayList<>();

    public Game(Rules rules) {
        this.addRulesToGame(rules);
    }

    public Game(){
    }

    public void play(){
        for(Player player:playerWithResult.keySet()) {
            player.loginForGame();
            //App.getGameUI().drawGameUI("Wettkampf");
            long resultTime = this.playRound(player);
            System.out.println(resultTime);
            playerWithResult.replace(player, resultTime);
            player.logoff();
            this.text = text_copy;
        }
        this.displayResults();
        this.writeToStats();
    }

    private long playRound(Player player){
        App.getGameUI().drawGameUI("Wettkampf");
        Instant start = Instant.now();
        //while(text.size()>0){

        //}
        //round

        Instant finish = Instant.now();
        return Duration.between(start, finish).toMillis();
    }

    private void displayResults(){
    }

    private void writeToStats(){

    }

    public boolean addPlayerToGame(String username){
        //check if player exists
        Player player = new Player(username);
        playerWithResult.put(player, 0l);
        return true;
    }
    public boolean addPlayerToGame(Player player){
        playerWithResult.put(player, 0l);
        return true;
    }

    public void addRulesToGame(Rules rules){
        this.rules = rules;
        this.text = rules.selectTextBasedOnRules().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        this.text_copy = this.text;
    }

    public Map<Player, Long> getPlayerWithResult() {
        return playerWithResult;
    }

    public void setText(List<Character> text) {
        this.text = text;
    }

    public List<Character> getText() {
        return text;
    }

    public String getTextAsString(){
        StringBuilder sb = new StringBuilder();
        for (Character c : text){
            sb.append(c);
        }
        return sb.toString();
    }

    public boolean checkCurrentInputChar(char c){
        if(text.isEmpty()){
            System.exit(0);
            return true;
        }else {
            if (c == text.get(0)) {
                text.remove(0);
                return true;
            } else {
                return false;
            }
        }
    }
}

