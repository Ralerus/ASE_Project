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
    void forCorrectInputChar() {
        boolean result = round.checkCurrentInputChar('T');

        assertTrue(result);
    }

    @Test
    void forIncorrectInputChar() {
        boolean result = round.checkCurrentInputChar('h');

        assertFalse(result);
    }

    @Test
    void forCorrectInputCharSequence(){
        boolean result1 = round.checkCurrentInputChar('T');
        boolean result2 = round.checkCurrentInputChar('e');
        boolean result3 = round.checkCurrentInputChar('s');

        assertTrue(result1 && result2 && result3);
    }

    @Test
    void forIncorrectInputCharSequence(){
        boolean result1 = round.checkCurrentInputChar('Ö');
        boolean result2 = round.checkCurrentInputChar('5');
        boolean result3 = round.checkCurrentInputChar('_');

        assertFalse(result1 || result2 || result3);
    }
}