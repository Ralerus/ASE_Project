package layer.presentation;

import layer.data.Player;

public class RoundUI {
    public static void displayRoundFor(Player p){
        System.out.println(p.getUsername()+" plays round");
    }
}
