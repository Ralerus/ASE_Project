package layer.domain;

import layer.data.Player;
import layer.data.Text;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void checkCurrentInputChar() {
        Text text = mock(Text.class);
        when(text.getText()).thenReturn("Testtext");

        Player p = mock(Player.class);
        Round round = new Round(text,p);

        boolean result = round.checkCurrentInputChar('T');

        assertTrue(result);
    }

    @Test
    public void Round(){

    }

    @Test
    void getTextLeft() {
    }
}