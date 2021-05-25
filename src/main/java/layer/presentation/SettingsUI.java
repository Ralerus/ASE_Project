package layer.presentation;

import application.Application;
import layer.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsUI {
    public JPanel getSettingsUI() {
        JPanel settingsUI = new JPanel();
        settingsUI.setLayout(new BorderLayout());
        settingsUI.add(this.getUserMananagementUI(), BorderLayout.WEST);
        settingsUI.add(this.getAddTextUI(), BorderLayout.EAST);

        return settingsUI;
    }

    private JPanel getUserMananagementUI(){
        JPanel userManagement = new JPanel();
        userManagement.setLayout(new GridLayout(8,1));
        userManagement.add(new JLabel("<html><h2>Nutzerverwaltung</h2></html>"));
        JPanel userInputFields = new JPanel();
        userInputFields.setLayout(new GridLayout(4,2 ));
        userInputFields.add(new JLabel("Neuer Benutzername:"));
        JTextField username = new JTextField();
        userInputFields.add(username);
        userInputFields.add(new JLabel("Neuer vollständiger Name:"));
        JTextField fullname = new JTextField();
        userInputFields.add(fullname);
        userInputFields.add(new JLabel("Neues Passwort:"));
        JPasswordField password = new JPasswordField();
        userInputFields.add(password);
        userInputFields.add(new JLabel("Passwort bestätigen:"));
        JPasswordField password_repetition = new JPasswordField();
        userInputFields.add(password_repetition);
        userManagement.add(userInputFields);

        JButton changeData = new JButton("Übernehmen");
        changeData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerRepository player = null;
                try {
                    player = PlayerRepository.getPlayerRepository(Application.getSession().getLoggedInPlayer());
                } catch (PlayerNotFoundException playerNotFoundException) {
                    playerNotFoundException.printStackTrace();
                }
                if(!username.getText().isEmpty()){
                    if(player!=null){
                        if(player.changeUserName(username.getText())){
                            JOptionPane.showMessageDialog(Application.getUi(),"Nutzername erfolgreich geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                            Application.getSession().setLoggedInPlayer(new Player(username.getText(), Application.getSession().getLoggedInPlayer().getFullname()));
                            Application.getUi().setTitle("Tippduell - "+username.getText()+" angemeldet");
                            username.setText("");
                        }else {
                            JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des Nutzernames", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if(!fullname.getText().isEmpty()){
                    if(player!=null){
                        if(player.changeFullname(fullname.getText())){
                            JOptionPane.showMessageDialog(Application.getUi(),"Vollständiger Name erfolgreich geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                            Application.getSession().setLoggedInPlayer(new Player(Application.getSession().getLoggedInPlayer().getUsername(),fullname.getText()));
                            fullname.setText("");
                        }else {
                            JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des vollständigen Namens", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if(password.getPassword().length > 0 && password_repetition.getPassword().length > 0){
                    String newPasswordString = new String(password_repetition.getPassword());
                    String passwordString = new String(password.getPassword());
                    if(newPasswordString.equals(passwordString) && player !=null){
                        String passwordHash = Security.getSecureHash(passwordString);
                        if(player.changePassword(passwordHash)){
                            JOptionPane.showMessageDialog(Application.getUi(),"Password erfolgreich geändert", "Änderung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                            password.setText("");
                            password_repetition.setText("");
                        }else {
                            JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Ändern des Passworts", "Änderung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        userManagement.add(changeData);
        JButton deleteUser = new JButton("Nutzer löschen");
        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlayerRepository player = PlayerRepository.getPlayerRepository(Application.getSession().getLoggedInPlayer());
                    if(player.deleteUser()){
                        JOptionPane.showMessageDialog(Application.getUi(),"Nutzer erfolgreich gelöscht, sie werden nun ausgeloggt.", "Löschen erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                        Application.getSession().logoff();
                    }else{
                        JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Löschen des Nutzers", "Löschen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (PlayerNotFoundException playerNotFoundException) {
                    playerNotFoundException.printStackTrace();
                }
            }
        });
        userManagement.add(deleteUser);
        JButton logoff = new JButton("Abmelden");
        logoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getSession().logoff();
            }
        });
        userManagement.add(logoff);
        return userManagement;
    }

    private JPanel getAddTextUI(){
        JPanel addText = new JPanel();
        addText.setLayout(new GridLayout(5,1));
        addText.add(new JLabel("<html><h2>Texte hinzufügen</h2></html>"));

        JPanel textInput = new JPanel();
        textInput.setLayout(new GridLayout(3,2));
        textInput.add(new JLabel("Texttitel:"));
        JTextField title = new JTextField();
        textInput.add(title);
        textInput.add(new JLabel("Text:"));
        JTextArea text = new JTextArea();
        textInput.add(text);
        textInput.add(new JLabel("Schwierigkeitsgrad:"));
        JPanel difficultyRadiosPanel = new JPanel();
        difficultyRadiosPanel.setLayout(new GridLayout(3, 1));
        ButtonGroup difficultyRadios = new ButtonGroup();
        JRadioButton radioEasy = new JRadioButton("Einfach");
        JRadioButton radioMedium = new JRadioButton("Mittel");
        JRadioButton radioHard = new JRadioButton("Schwer");
        difficultyRadios.add(radioEasy);
        difficultyRadios.add(radioMedium);
        difficultyRadios.add(radioHard);
        difficultyRadiosPanel.add(radioEasy);
        difficultyRadiosPanel.add(radioMedium);
        difficultyRadiosPanel.add(radioHard);
        textInput.add(difficultyRadiosPanel);
        addText.add(textInput);

        JButton ok = new JButton("Hinzufügen");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Difficulty difficulty = Difficulty.Easy;
                if(radioEasy.isSelected()){
                    difficulty = Difficulty.Easy;
                }else if(radioMedium.isSelected()){
                    difficulty = Difficulty.Medium;
                }else if(radioHard.isSelected()){
                    difficulty = Difficulty.Hard;
                }
                if(TextRepository.createText(title.getText(), text.getText(), difficulty)){
                    JOptionPane.showMessageDialog(Application.getUi(),title.getText()+" erfolgreich hinzugefügt.", "Text erfolgreich hinzugefügt", JOptionPane.INFORMATION_MESSAGE);
                    title.setText("");
                    text.setText("");
                    radioEasy.setSelected(false);
                    radioMedium.setSelected(false);
                    radioHard.setSelected(false);
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Hinzufügen des Textes", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addText.add(ok);
        return addText;
    }
}