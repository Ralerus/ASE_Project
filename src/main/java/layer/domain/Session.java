package layer.domain;

import application.Application;
import layer.data.ObjectNotFoundException;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.presentation.ApplicationUI;

public abstract class Session {
    private static Player loggedInPlayer;

    public static void login(String username, String password) throws ObjectNotFoundException,
            WrongPasswordException {
        PlayerRepository p = PlayerRepository.getPlayerRepository(username);
        if(p.isPasswordCorrect(password)){
            loggedInPlayer = p.getPlayer();
        }else{
            throw new WrongPasswordException();
        }
    }

    public static void logoff(){
        loggedInPlayer = null;
        Application.getUi().dispose();
        Application.setUi(new ApplicationUI());
    }

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player newLoggedInPlayer) {
        loggedInPlayer = newLoggedInPlayer;
    }

    public static class WrongPasswordException extends Exception {
        public WrongPasswordException() {
            super();
        }
    }
}
