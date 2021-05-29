package layer.domain;

import application.Application;
import layer.data.*;
import layer.presentation.Login;
import layer.presentation.GameUI;
import layer.presentation.StatsUI;

import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

public class Game implements GameListener {
    private Text text;
    private Map<Player, Double> results;
    private List<Player> playersLeft;
    private List<Player> allPlayers;
    private Player originallyLoggedInPlayer;
    private Round currentRound;
    private boolean isCompetition;

    public Game(List<Player> playersLeft, Rules rules, boolean isCompetition) throws ObjectNotFoundException {
        this.text = TextRepository.getRandomTextBasedOn(rules);
        this.originallyLoggedInPlayer = Session.getLoggedInPlayer();
        this.results = new HashMap<>();
        this.playersLeft = playersLeft;
        this.allPlayers = new ArrayList<>(playersLeft);
        this.currentRound = null;
        this.isCompetition = isCompetition;
    }

    public void start(){
        Collections.shuffle(playersLeft);
        this.gotoNextPlayer();
    }

    public void playAgain() {
        this.playersLeft.addAll(allPlayers);
        this.results.clear();
        this.start();
    }

    @Override
    public void startRound() {
        this.currentRound = new Round(this.text);
        currentRound.setListener(this);
        currentRound.startRound();
    }

    @Override
    public void endRound(double duration) {
        this.results.put(Session.getLoggedInPlayer(), duration);
        this.gotoNextPlayer();
    }

    private void gotoNextPlayer() {
        if(playersLeft.isEmpty()){
            Login.create().withTitle("Anmeldung des Spielleiters für Ergebnisse").forPlayer(originallyLoggedInPlayer).build();
            this.writeGameToStats();
            GameUI.drawResults(sortMapByValue(results), text.getLength());
        }else {
            Player nextPlayer = playersLeft.remove(0);
            if(!nextPlayer.equals(Session.getLoggedInPlayer())){
                Login.create().withTitle("Anmeldung des nächsten Spielers").forPlayer(nextPlayer).duringGame(this).build();
            }else{
                this.startRound();
            }
        }
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

    private void writeGameToStats(){
        Map<String, Double> resultsWithUsername = new HashMap<>();
        for(Player p : results.keySet()) {
            resultsWithUsername.put(p.getUsername(), results.get(p));
        }
        try {
            if(isCompetition){
                StatsRepository.writeCompetitionToStats(text.getTitle(), resultsWithUsername, Instant.now());
            }else{
                if(resultsWithUsername.size()==1) {
                    String username = resultsWithUsername.keySet().toArray()[0].toString();
                    StatsRepository.writeTrainingToStats(text.getTitle(), username,resultsWithUsername.get(username),
                            Instant.now());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StatsUI.refreshStatsUI();
    }
}
