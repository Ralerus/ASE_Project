package layer;

import layer.domain.Competition;
import layer.domain.Game;
import layer.domain.Session;
import layer.domain.Training;
import layer.presentation.LoginUI;

public class Application {
    private static Session session = new Session();

    public static void main(String[] args) {
        //LoginUI.displayLogin();
    	Competition competition = new Competition();
    	competition.addPlayer("Philipp");
    	competition.addPlayer("Teresa");
    	competition.start();
    }

    public static Session getSession() {
        return session;
    }

    /*public static Game getCompetition() {
        return competition;
    }*/

}
