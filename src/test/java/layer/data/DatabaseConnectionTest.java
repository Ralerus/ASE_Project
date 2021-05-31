package layer.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DatabaseConnectionTest {

    @Test
    void connect() {
        assertDoesNotThrow(Database::connect);
    }
}