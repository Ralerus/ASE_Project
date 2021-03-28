package layer.domain;

import layer.Application;
import layer.data.Player;
import layer.data.Rules;
import layer.data.StatsRepository;
import layer.data.Text;
import layer.presentation.GameUI;
import layer.presentation.LoginUI;
import layer.presentation.RoundUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements MyListener {
    protected Text text;
    protected List<Player> players;
    protected Rules rule;
    protected Round currentRound;
    
    private LoginUI loginUI;

    public Game(){
        this.text = new Text();
        List<Player> players = new ArrayList<>();
        players.add(Application.getSession().getLoggedInPlayer());
        this.players = players;
        this.rule = new Rules();
        this.currentRound = null;
        this.loginUI = new LoginUI();
        loginUI.addListener(this);
    }

    public void start(){
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
    }

    public Round getCurrentRound() {
        return currentRound;
    }
    
    private Map<Player, Double> prepareResults(){
    	Map<Player, Double> results = new HashMap<>();
    	for(Player p: players) {
    		double timeInSeconds = (double) Math.round((p.getCurrentResult() / 1000)*100)/100.0d;
    		results.put(p, timeInSeconds);
    	}
    	return results;
    }
    
    private void addGameToStats() {
    	//check if Competition or Training was startet
    	StatsRepository.incrementCompetitionCounter();
    	StatsRepository.writeRoundTimesToPlayerStats(this.prepareResults(), new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    }
    
    @Override
    public void loggedIn() {
    	System.out.println("logged in!!");
    	
    }
}
