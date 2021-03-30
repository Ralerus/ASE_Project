package layer.domain;

import layer.data.Player;

public interface GameListener {
	void startRoundFor(Player p); //p not needed, static Session with loggedinPlayer?
	void endRoundFor(Player p,double duration);
	//observer pattern
}
