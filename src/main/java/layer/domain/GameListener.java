package layer.domain;

import layer.presentation.LoginListener;

public interface GameListener extends LoginListener {
	void startRound();
	void endRound(double duration);
}
