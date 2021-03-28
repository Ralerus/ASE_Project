package layer.domain;

import layer.data.Player;
import layer.data.Rules;
import layer.data.Text;
import layer.presentation.GameUI;
import layer.presentation.LoginUI;

import java.util.*;

public class Game implements GameListener {
    protected Text text;
    protected Map<Player, Double> results;
    protected List<Player> playersLeft;
    protected Rules rule;
    protected Round currentRound;
    
    private LoginUI loginUI;

    public Game(){
        this.text = new Text();
        List<Player> players = new ArrayList<>();
        //players.add(Application.getSession().getLoggedInPlayer());
        this.playersLeft = players;
        this.results = new HashMap<>();

        this.rule = new Rules();
        this.currentRound = null;
        this.loginUI = new LoginUI();
        loginUI.setListener(this);
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
        loginUI.drawLoginUIFor(playersLeft.remove(0));
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
            GameUI.drawResults(results);
        }else {
            this.loginUI.drawLoginUIFor(playersLeft.remove(0));
        }
    }

    @Override
    public void endRoundFor(Player p, double duration) {
        this.results.put(p,duration);
        this.gotoNextPlayer();
    }
}
