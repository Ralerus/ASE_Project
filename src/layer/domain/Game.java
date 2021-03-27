package layer.domain;

import layer.Application;
import layer.data.Player;
import layer.data.Rules;
import layer.data.Text;
import layer.presentation.LoginUI;
import layer.presentation.RoundUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    protected Text text;
    protected List<Player> players;
    protected Rules rule;
    protected Round currentRound;

    public Game(){
        this.text = new Text();
        List<Player> players = new ArrayList<>();
        players.add(Application.getSession().getLoggedInPlayer());
        this.players = players;
        this.rule = new Rules();
        this.currentRound = null;
    }

    public void start(){
        Collections.shuffle(players);
        for(Player p : players){
            LoginUI.displayLoginFor(p);
            this.currentRound = new Round(this.text);
            RoundUI.displayRoundFor(p);

            //if round has finished
            p.setCurrentResult(currentRound.getDuration());
        }
    }

    public Round getCurrentRound() {
        return currentRound;
    }
}
