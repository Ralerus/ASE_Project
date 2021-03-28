package layer.presentation;

import java.util.Map;

import layer.data.Player;

public class GameUI {
	public static void drawCompetitionUI() {
		
	}
	
	public static void drawTrainingUI() {
		
	}
	
	public static void drawResults(Map<Player, Double> results) {
		System.out.println("Game over");
		for(Player p: results.keySet()){
			System.out.println("Player "+p.getUsername()+" has "+results.get(p)+" seconds.");
		}
	}
	
}
