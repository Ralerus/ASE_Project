package layer.domain;

import layer.data.Player;
import layer.data.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class CheckTestLeft {
    private Round round;

    @BeforeEach
    void setUp() {
        Text text = mock(Text.class);
        when(text.getText()).thenReturn("Testtext");

        Player p = mock(Player.class);
        round = new Round(text,p);
    }

    @Test
    void checkFullTextLeft(){
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("Testtext"));
    }

    @Test
    void checkTextLeftAfterCorrectInput(){
        round.checkCurrentInputChar('T');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("esttext"));
    }

    @Test
    void checkTextLeftAfterCorrectInputs(){
        round.checkCurrentInputChar('T');
        round.checkCurrentInputChar('e');
        round.checkCurrentInputChar('s');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("ttext"));
    }

    @Test
    void checkTextLeftAfterIncorrectInputs(){
        round.checkCurrentInputChar('J');
        round.checkCurrentInputChar('.');
        round.checkCurrentInputChar('$');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("Testtext"));
    }

    @Test
    void checkTextLeftAfterMixedInputs(){
        round.checkCurrentInputChar('T');
        round.checkCurrentInputChar('g');
        round.checkCurrentInputChar('e');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("sttext"));
    }
}
