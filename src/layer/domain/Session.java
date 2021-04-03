package layer.domain;

import layer.data.Player;
import layer.data.PlayerNotFoundException;
import layer.data.PlayerRepository;

public class Session {
    private Player loggedInPlayer = new Player("","");

    public void login(String username, String password) throws PlayerNotFoundException, WrongPasswordException {
        PlayerRepository p = PlayerRepository.getPlayerRepository(username);
        if(p.isPasswordCorrect(password)){
            this.loggedInPlayer = p.getPlayer();
        }else{
            throw new WrongPasswordException();
        }
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

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }
}
