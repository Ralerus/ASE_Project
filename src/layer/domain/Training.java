package layer.domain;

import layer.data.Player;
import layer.data.Rules;
import layer.data.Text;

import java.util.List;

public class Training extends Game{
    public Training(Text text, List<Player> players, Rules rule) {
        super(text, players, rule);
    }

    public Training(){
        super();
    }
}
