package layer.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Security {
    public static String getSecureHash(String passwordToHash) throws IllegalArgumentException{
        if(passwordToHash.isEmpty()){
            throw new IllegalArgumentException("Passwort darf nicht leer sein.");
        }else {
            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                byte[] bytes = md.digest(passwordToHash.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte aByte : bytes) {
                    sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    }
}
