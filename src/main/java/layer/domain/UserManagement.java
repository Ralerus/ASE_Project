package layer.domain;

import application.Application;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.data.Security;

import javax.swing.*;

public class UserManagement {
    public static boolean isUsernameChanged(String usernameValue, PlayerRepository player){
        if(!usernameValue.isEmpty()){
            if(player!=null){
                if(player.changeUserName(usernameValue)){
                    JOptionPane.showMessageDialog(Application.getUi(),"Nutzername erfolgreich " +
                            "geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                    Application.getSession().setLoggedInPlayer(new Player(usernameValue,
                            Application.getSession().getLoggedInPlayer().getFullname()));
                    return true;
                }else {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                            " Nutzernames", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return false;
    }

    public static boolean isFullNameChanged(String fullnameValue, PlayerRepository player){
        if(!fullnameValue.isEmpty()){
            if(player!=null){
                if(player.changeFullname(fullnameValue)){
                    JOptionPane.showMessageDialog(Application.getUi(),"Vollständiger Name" +
                                    " erfolgreich geändert", "Änderung erfolgreich",
                            JOptionPane.INFORMATION_MESSAGE);
                    Application.getSession().setLoggedInPlayer(new Player(
                            Application.getSession().getLoggedInPlayer().getUsername(),fullnameValue));
                    return true;
                }else {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                                    " vollständigen Namens", "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return false;
    }

    public static boolean isPasswordFieldsClearNeeded(char[] passwordValue, char[] passwordRepetitionValue,
                                                      PlayerRepository player){
        if(passwordValue.length > 0 && passwordRepetitionValue.length > 0){
            String newPasswordString = new String(passwordRepetitionValue);
            String passwordString = new String(passwordValue);
            if(newPasswordString.equals(passwordString) && player !=null){
                if(passwordString.length()>5){
                    String passwordHash = Security.getSecureHash(passwordString);
                    if(player.changePassword(passwordHash)){
                        JOptionPane.showMessageDialog(Application.getUi(),"Password erfolgreich" +
                                " geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    }else {
                        JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern" +
                                        " des Passworts", "Änderung fehlgeschlagen",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Das Password muss" +
                                    " mindestens 6 Zeichen aufweisen.", "Passwort zu kurz",
                            JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(Application.getUi(), "Die Passwortwiederholung" +
                                " stimmt nicht mit \ndem Passwort überein.", "Änderung fehlgeschlagen",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }else{
            if(passwordValue.length>0 || passwordRepetitionValue.length >0){
                JOptionPane.showMessageDialog(Application.getUi(), "Bitte gib das neue Passwort" +
                                " ein und \nwiederhole es!", "Änderung fehlgeschlagen",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }
}
