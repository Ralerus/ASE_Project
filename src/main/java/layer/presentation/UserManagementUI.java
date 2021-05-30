package layer.presentation;

import application.Application;
import layer.data.ObjectNotFoundException;
import layer.data.PlayerRepository;
import layer.domain.Session;
import layer.domain.UserManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public abstract class UserManagementUI {
    private static final JTextField username = new JTextField();
    private static final JTextField fullName = new JTextField();
    private static final JPasswordField password = new JPasswordField();
    private static final JPasswordField password_repetition = new JPasswordField();

    public static JPanel getUI(){
        JPanel userManagement = new JPanel();
        userManagement.setLayout(new BorderLayout());
        userManagement.add(new JLabel("<html><h2>Nutzerverwaltung</h2></html>"), BorderLayout.NORTH);
        userManagement.add(UserManagementUI.getInputFields(), BorderLayout.CENTER);
        userManagement.add(UserManagementUI.getButtons(), BorderLayout.SOUTH);
        return userManagement;
    }

    private static JPanel getInputFields(){
        JPanel userInputFields = new JPanel();
        userInputFields.setLayout(new GridLayout(4,2 ));
        userInputFields.add(new JLabel("Neuer Benutzername:"));
        userInputFields.add(username);
        userInputFields.add(new JLabel("Neuer vollständiger Name:"));
        userInputFields.add(fullName);
        userInputFields.add(new JLabel("Neues Passwort:"));
        userInputFields.add(password);
        userInputFields.add(new JLabel("Passwort bestätigen:"));
        userInputFields.add(password_repetition);

        return userInputFields;
    }

    private static JPanel getButtons(){
        JPanel buttons = new JPanel();
        buttons.add(UserManagementUI.getChangeButton());
        buttons.add(UserManagementUI.getDeleteButton());
        buttons.add(UserManagementUI.getLogoffButton());
        return buttons;
    }

    private static JButton getChangeButton(){
        JButton changeButton = new JButton("Übernehmen");
        changeButton.addActionListener(e -> {
            String usernameValue = username.getText();
            String fullnameValue = fullName.getText();
            char[] passwordValue = password.getPassword();
            char[] passwordRepetitionValue = password_repetition.getPassword();

            if(!usernameValue.isEmpty() || !fullnameValue.isEmpty() || (passwordValue.length!=0) ||
                    (passwordRepetitionValue.length!=0)){
                PlayerRepository player = null;
                try {
                    player = PlayerRepository.getPlayerRepository(Session.getLoggedInPlayer());
                } catch (ObjectNotFoundException playerNotFoundException) {
                    playerNotFoundException.printStackTrace();
                }
                if(UserManagement.isUsernameFieldClearNeeded(usernameValue,player)){
                    username.setText("");
                }
                if(UserManagement.isFullNameChanged(fullnameValue,player)){
                    fullName.setText("");
                }
                if(UserManagement.isPasswordFieldsClearNeeded(passwordValue,passwordRepetitionValue,player)){
                    password.setText("");
                    password_repetition.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(Application.getUi(), "Bitte gebe entweder einen neuen" +
                                " Benutzernamen, vollständigen Name \noder ein neues Passwort an!",
                        "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
            }
        });
        return changeButton;
    }

    private static JButton getDeleteButton(){
        JButton deleteButton = new JButton("Nutzer*in löschen");
        deleteButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(Application.getUi(),  "Nutzer*in wirklich" +
                    " löschen?", "Löschbestätigung", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(response==0){
                try {
                    PlayerRepository player = PlayerRepository.getPlayerRepository(
                            Session.getLoggedInPlayer());
                    try {
                        player.deleteUser();
                        JOptionPane.showMessageDialog(Application.getUi(),"Nutzer*in erfolgreich gelöscht," +
                                        "\ndu wirst nun abgemeldet.", "Löschen erfolgreich",
                                JOptionPane.INFORMATION_MESSAGE);
                        Session.logoff();
                    } catch (SQLException throwables) {
                        JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Löschen des bzw." +
                                " der Nutzer*in", "Löschen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ObjectNotFoundException playerNotFoundException) {
                    JOptionPane.showMessageDialog(Application.getUi(), "Zu löschende*r Nutzer*in konnte " +
                            "nicht gefunden werden.", "Löschen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    private static JButton getLogoffButton(){
        JButton logoff = new JButton("Abmelden");
        logoff.addActionListener(e -> Session.logoff());
        return logoff;
    }
}
