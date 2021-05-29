package layer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagementTest {

    @Test
    void tryUsernameChangeWithEmptyParams(){
        boolean result = UserManagement.isUsernameFieldClearNeeded("", null);
        assertFalse(result);
    }

    @Test
    void tryFullNameChangeWithEmptyParams(){
        boolean result = UserManagement.isFullNameChanged("",null);
        assertFalse(result);
    }

    @Test
    void tryPasswordChangeWithEmptyParams(){
        char[] emptyPassword = {};
        char[] emptyPasswordRepetition = {};
        boolean result = UserManagement.isPasswordFieldsClearNeeded(emptyPassword, emptyPasswordRepetition,null);
        assertFalse(result);
    }





}