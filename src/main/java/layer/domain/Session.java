package layer.domain;

import application.Application;
import layer.data.ObjectNotFoundException;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.presentation.ApplicationUI;

public class Session {
    private Player loggedInPlayer = new Player("","");

    public void login(String username, String password) throws ObjectNotFoundException,
            WrongPasswordException {
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

    public static class WrongPasswordException extends Exception {
        public WrongPasswordException() {
            super();
        }
        public WrongPasswordException(String message) {
            super(message);
        }
    }
}
