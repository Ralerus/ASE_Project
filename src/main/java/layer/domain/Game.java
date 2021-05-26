package layer.domain;

import application.Application;
import layer.data.*;
import layer.presentation.GameUI;
import layer.presentation.UserUI;

import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

public class Game implements GameListener {
    private Text text;
    private Map<Player, Double> results;
    private List<Player> playersLeft;
    private List<Player> allPlayers;
    private Rules rule;
    private Round currentRound;
    private boolean isCompetition;

    public Game(List<Player> playersLeft, Rules rule, boolean isCompetition) throws TextRepository.TextNotFoundException {
        this.text = TextRepository.getRandomTextBasedOn(rule);
        this.results = new HashMap<>();
        this.playersLeft = playersLeft;
        this.allPlayers = new ArrayList<>(playersLeft);
        this.rule = rule;
        this.currentRound = null;
        this.isCompetition = isCompetition;
        UserUI.setListener(this);
    }

    public void start(){
        Collections.shuffle(playersLeft);
        this.gotoNextPlayer();
    }

    @Override
    public void playAgain() {
        this.playersLeft.addAll(allPlayers);
        this.results.clear();
        this.start();
    }

    @Override
    public void startRoundFor(Player p) {
        this.currentRound = new Round(this.text, p);
        currentRound.setListener(this);
        currentRound.startRound();
    }
    private void gotoNextPlayer() {
        if(playersLeft.isEmpty()){
            this.writeGameToStats();
            GameUI.drawResults(sortMapByValue(results));
        }else {
            Player nextPlayer = playersLeft.remove(0);
            if(!nextPlayer.equals(Application.getSession().getLoggedInPlayer())){
                UserUI.drawLoginUIFor(nextPlayer);
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

    private void writeGameToStats(){
        Map<String, Double> resultsWithUsername = new HashMap<>();
        for(Player p : results.keySet()) {
            resultsWithUsername.put(p.getUsername(), results.get(p));
        }
        try {
            if(isCompetition){
                GameRepository.writeCompetitionToStats(text.getTitle(), resultsWithUsername, Instant.now());
            }else{
                if(resultsWithUsername.size()==1) {
                    String username = resultsWithUsername.keySet().toArray()[0].toString();
                    GameRepository.writeTrainingToStats(text.getTitle(), username,resultsWithUsername.get(username),
                            Instant.now());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
