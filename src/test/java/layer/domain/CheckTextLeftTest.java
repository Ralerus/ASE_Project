package layer.domain;

import layer.data.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTextLeftTest {
    private Round round;

    @BeforeEach
    void setUp() {
        Text text = mock(Text.class);
        when(text.getText()).thenReturn("Testtext");

        round = new Round(text);
    }

    @Test
    void forFullTextLeft(){
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("Testtext"));
    }

    @Test
    void forCorrectInput(){
        round.checkCurrentInputChar('T');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("esttext"));
    }

    @Test
    void forIncorrectInput(){
        round.checkCurrentInputChar(' ');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("Testtext"));
    }

    @Test
    void forCorrectInputs(){
        round.checkCurrentInputChar('T');
        round.checkCurrentInputChar('e');
        round.checkCurrentInputChar('s');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("ttext"));
    }

    @Test
    void forIncorrectInputs(){
        round.checkCurrentInputChar('J');
        round.checkCurrentInputChar('.');
        round.checkCurrentInputChar('$');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("Testtext"));
    }

    @Test
    void forMixedInputs(){
        round.checkCurrentInputChar('T');
        round.checkCurrentInputChar('g');
        round.checkCurrentInputChar('e');
        String textLeft = round.getTextLeft();
        assertTrue(textLeft.equals("sttext"));
    }
}
