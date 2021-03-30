package layer.domain;

import layer.Application;
import layer.data.Player;
import layer.data.Rules;
import layer.data.Text;
import layer.data.TextRepository;
import layer.presentation.GameUI;
import layer.presentation.UserUI;

import java.util.*;

public class Game implements GameListener {
    protected Text text;
    protected Map<Player, Double> results;
    protected List<Player> playersLeft;
    protected Rules rule;
    protected Round currentRound;
    
    private UserUI userUI;

    public Game(){
        this.text = new Text();
        List<Player> players = new ArrayList<>();
        //players.add(Application.getSession().getLoggedInPlayer());
        this.playersLeft = players;
        this.results = new HashMap<>();

        this.rule = new Rules();
        this.currentRound = null;
        this.userUI = new UserUI();
        userUI.setListener(this);
    }

    public Game(List<Player> playersLeft, Rules rule) {
        this.text = TextRepository.getRandomTextBasedOn(rule);
        this.results = new HashMap<>();
        this.playersLeft = playersLeft;
        this.rule = rule;
        this.currentRound = null;
        this.userUI = new UserUI();
        userUI.setListener(this);
    }
    /*public void start(){
        Collections.shuffle(players);
        for(Player p : players){
        	System.out.println("draw ui");
            this.loginUI.drawLoginUIFor(p);
            System.out.println("Wait starts");
            System.out.println("Wait ends");
            if(Application.getSession().getLoggedInPlayer().equals(p)) {
            	System.out.println(p.getUsername() + " logged in");            	
            }else {
            	System.out.println("error");
            }
            this.currentRound = new Round(this.text, p);
            //long duration = currentRound.playRound();
            //p.setCurrentResult(duration);
        }
        GameUI.drawResults(this.prepareResults());
        this.addGameToStats();
    }*/

    public void start(){
        Collections.shuffle(playersLeft);
        this.gotoNextPlayer();
    }

    /*private void addGameToStats() {
    	//check if Competition or Training was startet
    	StatsRepository.incrementCompetitionCounter();
    	StatsRepository.writeRoundTimesToPlayerStats(this.prepareResults(), new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    }*/
    
    @Override
    public void startRoundFor(Player p) {
        this.currentRound = new Round(this.text, p);
        currentRound.setListener(this);
        currentRound.startRound();
    }
    private void gotoNextPlayer() {
        if(playersLeft.isEmpty()){
            GameUI.drawResults(sortMapByValue(results));
        }else {
            Player nextPlayer = playersLeft.remove(0);
            if(!nextPlayer.equals(Application.getSession().getLoggedInPlayer())){
                this.userUI.drawLoginUIFor(nextPlayer);
            }else{
                this.startRoundFor(nextPlayer);
            }
        }
    }

    @Override
    public void endRoundFor(Player p, double duration) {
        this.results.put(p,duration);
        this.gotoNextPlayer();
    }

    private Map<Player, Double> sortMapByValue(Map<Player,Double> map){
        List<Map.Entry<Player,Double>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Player, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Player,Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
