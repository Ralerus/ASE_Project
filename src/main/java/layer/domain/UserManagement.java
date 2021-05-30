package layer.domain;

import application.Application;
import layer.data.ObjectAlreadyExistsException;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.data.Security;

import javax.swing.*;
import java.sql.SQLException;

public abstract class UserManagement {
    public static boolean isUsernameFieldClearNeeded(String usernameValue, PlayerRepository player){
        if(!usernameValue.isEmpty() && player!=null){
            int response = JOptionPane.showConfirmDialog(Application.getUi(),  "Benutzername wirklich" +
                    " ändern?\nDeine Spielerstatistik ist an den Benutzernamen\ngebunden und startet bei neuem Namen neu.", "Änderungsbestätigung", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(response==0) {
                try {
                    player.changeUserName(usernameValue);
                    JOptionPane.showMessageDialog(Application.getUi(), "Benutzername erfolgreich " +
                            "geändert. \nDu wirst nun abgemeldet, bitte melde dich\nneu an!", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                    Session.setLoggedInPlayer(new Player(usernameValue,
                            Session.getLoggedInPlayer().getFullName(),false));
                    Session.logoff();
                    return true;
                } catch (ObjectAlreadyExistsException e) {
                    JOptionPane.showMessageDialog(Application.getUi(), e.getMessage(), "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                            " Benutzernamens", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isFullNameChanged(String fullnameValue, PlayerRepository player){
        if(!fullnameValue.isEmpty() && player != null) {
            try {
                player.changeFullname(fullnameValue);
                JOptionPane.showMessageDialog(Application.getUi(), "Vollständiger Name" +
                                " erfolgreich geändert", "Änderung erfolgreich",
                        JOptionPane.INFORMATION_MESSAGE);
                Session.setLoggedInPlayer(new Player(
                        Session.getLoggedInPlayer().getUsername(), fullnameValue, false));
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                                " vollständigen Namens", "Änderung fehlgeschlagen",
                        JOptionPane.ERROR_MESSAGE);
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
                    try {
                        player.changePassword(passwordHash);
                        JOptionPane.showMessageDialog(Application.getUi(),"Password erfolgreich" +
                                " geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } catch (SQLException throwables) {
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
