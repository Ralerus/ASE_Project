package layer.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {

    @Test
    void getHashForShortPassword() {
        String returnedHash = Security.getSecureHash("abc");
        assertTrue(returnedHash.equals("ddaf35a193617abacc417349ae20413112e6fa4e89a97ea" +
                "20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f"));
    }

    @Test
    void getHashForLongPassword() {
        String returnedHash = Security.getSecureHash("Djkcile$2-d456__3d1^-d");
        assertTrue(returnedHash.equals("b6d894dcd5d8245de1dfe822b55fb02a6ca8020db308b253" +
                "c7851fad715869ebbc5e9a251e1e55cbc7da201b23a751d34544e64352df624f61bcb3ddce67404d"));
    }

    @Test
    void getHashForEmptyPassword(){
        assertThrows(IllegalArgumentException.class, () -> Security.getSecureHash(""));
    }
}