package layer.domain;

import layer.data.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CheckCurrentInputCharTest {
    private Round round;

    @BeforeEach
    void setUp() {
        Text text = mock(Text.class);
        when(text.getText()).thenReturn("Testtext");

        round = new Round(text);
    }

    @Test
    void checkCorrectInputChar() {
        boolean result = round.checkCurrentInputChar('T');

        assertTrue(result);
    }

    @Test
    void checkWrongInputChar() {
        boolean result = round.checkCurrentInputChar('h');

        assertFalse(result);
    }

    @Test
    void checkInputCharSequence(){
        boolean result1 = round.checkCurrentInputChar('T');
        boolean result2 = round.checkCurrentInputChar('e');
        boolean result3 = round.checkCurrentInputChar('s');

        assertTrue(result1 && result2 && result3);
    }
}