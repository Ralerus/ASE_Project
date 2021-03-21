/**
 * TODO: Spielablaufsteuerung (durch Threads?), warten auf letzte Eingabe, dann Fenster schließen und nächster ist dran
 */

public class App {
    private static Player loggedInPlayer;
    private static GameUI gameui;
    private static Game game;

    public static void main(String[] args) {
        gameui = new GameUI();
        gameui.drawUI();
    }

    public static boolean loginPlayer(Player player){
        if(player.login()){
            loggedInPlayer = player;
            return true;
        }
        return false;
    }

    public static Player getLoggedInPlayer(){
        return loggedInPlayer;
    }

    public static GameUI getGameUI(){
        return gameui;
    }

    public static void setGame(Game game) {
        App.game = game;
    }

    public static Game getGame() {
        return game;
    }
}
