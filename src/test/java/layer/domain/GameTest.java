package layer.domain;

import layer.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() throws TextRepository.TextNotFoundException {
        List<Player> players = new ArrayList<>();
        players.add(mock(Player.class));
        players.add(mock(Player.class));

        Rules rules = new Rules(Difficulty.Easy,0,150); //TODO mock
        //when(TextRepository.getRandomTextBasedOn(rules)).thenReturn(mock(Text.class));
        game = new Game(players,rules,true);

    }

    //TODO test cases?

}