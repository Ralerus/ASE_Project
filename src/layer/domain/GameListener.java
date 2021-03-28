package layer.domain;

import layer.data.Player;

public interface GameListener {
	void startRoundFor(Player p);
	void endRoundFor(Player p,double duration);
	//observer pattern
}
