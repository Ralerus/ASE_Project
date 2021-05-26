package layer.presentation;

import application.Application;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.data.Security;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagementUI {
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
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameValue = username.getText();
                String fullnameValue = fullName.getText();
                char[] passwordValue = password.getPassword();
                char[] passwordRepetitionValue = password_repetition.getPassword();

                if(!usernameValue.isEmpty() || !fullnameValue.isEmpty() || !(passwordValue.length==0) ||
                        !(passwordRepetitionValue.length==0)){
                    PlayerRepository player = null;
                    try {
                        player = PlayerRepository.getPlayerRepository(Application.getSession().getLoggedInPlayer());
                    } catch (PlayerRepository.PlayerNotFoundException playerNotFoundException) {
                        playerNotFoundException.printStackTrace();
                    }
                    UserManagementUI.changeUsernameIfNotEmpty(usernameValue,player);
                    UserManagementUI.changeFullNameIfNotEmpty(fullnameValue,player);
                    UserManagementUI.changePasswordIfNotEmpty(passwordValue,passwordRepetitionValue,player);
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Bitte gebe entweder einen neuen" +
                                    " Benutzernamen, vollständigen Name \noder ein neues Passwort an!",
                            "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return changeButton;
    }

    private static JButton getDeleteButton(){
        JButton deleteButton = new JButton("Nutzer*in löschen");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(Application.getUi(),  "Nutzer*in wirklich" +
                        " löschen?", "Löschbestätigung", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(response==0){
                    try {
                        PlayerRepository player = PlayerRepository.getPlayerRepository(
                                Application.getSession().getLoggedInPlayer());
                        if(player.deleteUser()){
                            JOptionPane.showMessageDialog(Application.getUi(),"Nutzer*in erfolgreich gelöscht," +
                                            "\ndu wirst nun abgemeldet.", "Löschen erfolgreich",
                                    JOptionPane.INFORMATION_MESSAGE);
                            Application.getSession().logoff();
                        }else{
                            JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Löschen des bzw." +
                                    " der Nutzer*in", "Löschen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (PlayerRepository.PlayerNotFoundException playerNotFoundException) {
                        playerNotFoundException.printStackTrace();
                    }
                }
            }
        });
        return deleteButton;
    }

    private static JButton getLogoffButton(){
        JButton logoff = new JButton("Abmelden");
        logoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSession().logoff();
            }
        });
        return logoff;
    }

    private static void changeUsernameIfNotEmpty(String usernameValue, PlayerRepository player){
        if(!usernameValue.isEmpty()){
            if(player!=null){
                if(player.changeUserName(usernameValue)){
                    JOptionPane.showMessageDialog(Application.getUi(),"Nutzername erfolgreich " +
                            "geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                    Application.getSession().setLoggedInPlayer(new Player(usernameValue,
                            Application.getSession().getLoggedInPlayer().getFullname()));
                    Application.getUi().setTitle("Tippduell - "+usernameValue+" angemeldet");
                    username.setText("");
                }else {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                            " Nutzernames", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static void changeFullNameIfNotEmpty(String fullnameValue, PlayerRepository player){
        if(!fullnameValue.isEmpty()){
            if(player!=null){
                if(player.changeFullname(fullnameValue)){
                    JOptionPane.showMessageDialog(Application.getUi(),"Vollständiger Name" +
                                    " erfolgreich geändert", "Änderung erfolgreich",
                            JOptionPane.INFORMATION_MESSAGE);
                    Application.getSession().setLoggedInPlayer(new Player(
                            Application.getSession().getLoggedInPlayer().getUsername(),fullnameValue));
                    fullName.setText("");
                }else {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des" +
                                    " vollständigen Namens", "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static void changePasswordIfNotEmpty(char[] passwordValue, char[] passwordRepetitionValue,
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
                        password.setText("");
                        password_repetition.setText("");
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
                password_repetition.setText("");
            }
        }else{
            if(passwordValue.length>0 || passwordRepetitionValue.length >0){
                JOptionPane.showMessageDialog(Application.getUi(), "Bitte gib das neue Passwort" +
                                " ein und \nwiederhole es!", "Änderung fehlgeschlagen",
                        JOptionPane.ERROR_MESSAGE);
                password.setText("");
                password_repetition.setText("");
            }
        }
    }
}
