package layer.presentation;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import application.Application;
import layer.data.*;

public abstract class Registration {
	private static JDialog registerDialog;

    public static void drawUI(boolean addToGame){
        registerDialog = new JDialog(Application.getUi(), "Registrierung", true);
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(4,2,6,3));
        registerPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();
        registerPanel.add(username);
        registerPanel.add(new JLabel("Voller Name:"));
        JTextField fullname = new JTextField();
        registerPanel.add(fullname);
        registerPanel.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        registerPanel.add(password);
        JButton registerButton = new JButton("Registrieren");
        registerButton.addActionListener(e -> {
            String usernameValue = username.getText();
            String fullnameValue = fullname.getText();
            char[] passwordValue = password.getPassword();
            if(!usernameValue.isEmpty() && !fullnameValue.isEmpty() && (passwordValue.length!=0)){
                if(passwordValue.length>5){
                    try {
                        String passwordHash = Security.getSecureHash(new String(password.getPassword()));
                        Player p = new Player(username.getText(), fullname.getText(), true);
                        PlayerRepository.createPlayer(p,passwordHash);
                        registerDialog.setVisible(false);
                        registerDialog.dispose();
                        JOptionPane.showMessageDialog(registerDialog, "Spieler*in "+username.getText()+" erfolgreich registriert.\n",
                                "Spieler*in registriert", JOptionPane.INFORMATION_MESSAGE);

                        if(addToGame){
                            GameUI.addToGame(p);
                            StatsUI.refreshGameStats();
                        }
                    }catch(ObjectAlreadyExistsException ex1) {
                        JOptionPane.showMessageDialog(registerDialog, ex1.getMessage(), "Fehler",
                                JOptionPane.ERROR_MESSAGE);
                    }catch (Exception ex2) {
                        ex2.printStackTrace();
                        JOptionPane.showMessageDialog(registerDialog, "Fehler beim Anlegen des bzw. der Spieler*in",
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(registerDialog, "Das Password muss mindestens 6 Zeichen" +
                            " aufweisen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(registerDialog, "Bitte fülle alle Felder aus!", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        registerPanel.add(registerButton);
        registerPanel.setBorder(new EmptyBorder(5,5,2,5));
        registerDialog.add(registerPanel);
        try {
            registerDialog.setIconImage(ApplicationUI.getAppIcon());
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        registerDialog.setSize(300,190);
        registerDialog.setLocationRelativeTo(Application.getUi());
        registerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerDialog.setVisible(true);
    }
}
