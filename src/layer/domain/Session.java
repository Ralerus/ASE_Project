package layer.domain;

import layer.data.Player;
import layer.data.PlayerRepository;

public class Session {
    private Player loggedInPlayer;

    public boolean login(String username, String password){
        PlayerRepository p = PlayerRepository.getPlayerRepository(username);
        if(p.isPasswordCorrect(password)){
            this.loggedInPlayer = p.getPlayer();
            return true;
        }
        return false;
    }

    public boolean logoff(Player p){
        if(loggedInPlayer.equals(p)){
            loggedInPlayer = null;
            return true;
        }
        return false;
    }

    public boolean isLoggedIn(Player p){
        return p.equals(loggedInPlayer);
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }
}
