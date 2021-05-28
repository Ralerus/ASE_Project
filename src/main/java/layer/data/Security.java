package layer.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static String getSecureHash(String passwordToHash) throws IllegalArgumentException{
        if(passwordToHash.isEmpty()){
            throw new IllegalArgumentException("Password can not be empty");
        }else {
            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                byte[] bytes = md.digest(passwordToHash.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    }
}
