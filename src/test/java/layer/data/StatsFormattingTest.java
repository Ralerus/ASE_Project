package layer.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class StatsFormattingTest {
    private StatsEntry result;

    @BeforeEach
    void setUp(){
        result = StatsRepository.getFormattedStatsEntry(
                "Testuser",4.867265723,"Titel","2021-05-29T12:55:45.092793900Z");
    }

    @Test
    void checkSpeedRounding() {
        assertEquals(result.getSpeed(), 4.87);
    }
    @Test
    void checkDateFormatting() {
        assertEquals(result.getFormattedDate(),"29.05.21, 14:55");
    }
    @Test
    void checkWrongDateInput(){
        assertThrows(DateTimeParseException.class, ()->StatsRepository.getFormattedStatsEntry(
                "Testuser",4.863265723,"Titel","dasdf793900Z"));
    }
}