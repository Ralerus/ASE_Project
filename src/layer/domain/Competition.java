package layer.domain;

import layer.data.Player;
import layer.data.PlayerRepository;
import layer.data.Rules;
import layer.data.Text;

import java.util.List;

public class Competition extends Game{

    public Competition(Text text, List<Player> players, Rules rule) {
        super(text, players, rule);
    }
    public Competition(){
        super();
    }
    public void addPlayer(String username){
        this.players.add(PlayerRepository.getPlayerRepository(username).getPlayer());
    }
    public void removePlayer(String username){
        this.players.remove(PlayerRepository.getPlayerRepository(username).getPlayer());
    }
}
