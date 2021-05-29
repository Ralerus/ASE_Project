package layer.data;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void connect() {
        assertDoesNotThrow(()->Database.connect()); //TODO remove if db driver doesnt work?
    }
}