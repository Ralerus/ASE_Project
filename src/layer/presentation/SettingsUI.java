package layer.presentation;

import layer.Application;
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
        userManagement.setLayout(new GridLayout(7,1));
        userManagement.add(new JLabel("Nutzerverwaltung"));
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
        return userManagement;
    }

    private JPanel getAddTextUI(){
        JPanel addText = new JPanel();
        addText.setLayout(new GridLayout(5,1));
        addText.add(new JLabel("Texte hinzufügen"));

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
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Hinzufügen des Textes", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addText.add(ok);
        return addText;
    }
}
