package layer.domain;

import application.Application;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.presentation.ApplicationUI;

public class Session {
    private Player loggedInPlayer = new Player("","");

    public void login(String username, String password) throws PlayerRepository.PlayerNotFoundException, WrongPasswordException {
        PlayerRepository p = PlayerRepository.getPlayerRepository(username);
        if(p.isPasswordCorrect(password)){
            this.loggedInPlayer = p.getPlayer();
        }else{
            throw new WrongPasswordException();
        }
    }

    public void logoff(){
        this.loggedInPlayer = null;
        Application.getUi().dispose();
        Application.setUi(new ApplicationUI());
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
