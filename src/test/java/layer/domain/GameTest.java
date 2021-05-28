package layer.domain;

import layer.data.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() throws ObjectNotFoundException {
        List<Player> players = new ArrayList<>();
        players.add(mock(Player.class));
        players.add(mock(Player.class));

        Rules rules = new Rules(Difficulty.Easy,0,150); //TODO mock
        //when(TextRepository.getRandomTextBasedOn(rules)).thenReturn(mock(Text.class));
        game = new Game(players,rules,true);

    }

    //TODO test cases?

}