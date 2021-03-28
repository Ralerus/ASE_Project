package layer.data;

import java.util.Map;

public class StatsRepository {
	public static boolean incrementCompetitionCounter() {
		//DB increment competition counter
		return true;
	}
	public static boolean incrementTrainingCounter() {
		//DB increment training counter
		return true;
	}
	public static boolean incrementPlayerCounter() {
		//DB increment player counter
		return true;
	}
	public static boolean decrementPlayerCounter() {
		//DB decrement player counter
		return true;
	}
	public static boolean writeRoundTimesToPlayerStats(Map<Player, Double> roundtimes, String timeStamp) {
		//DB add current round time to stats of each player
		return true;
	}
}
