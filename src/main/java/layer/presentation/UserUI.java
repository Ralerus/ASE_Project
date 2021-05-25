package layer.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import application.Application;
import layer.data.*;
import layer.domain.GameListener;
import layer.domain.WrongPasswordException;

public class UserUI {
	private JDialog registerDialog;
    private JDialog loginDialog;
	private GameListener listener;
	private UIListener uiListener;
	private GameUIListener gameUIListener;

    public void setListener(GameListener listener) {
    	this.listener = listener;
    }
    public void setUiListener(UIListener listener) { this.uiListener = listener;}
    public void setGameUIListener(GameUIListener listener) { this.gameUIListener = listener;}

    public void drawLoginUIFor(Player p) {
        loginDialog = new JDialog(Application.getUi(), "Anmeldung", true);
    	JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,2,6,3));
        loginPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();

        boolean gameLogin;
        if(p!=null){
            username.setText(p.getUsername());
            username.setEditable(false);
            username.setFocusable(false);
            gameLogin = true;
        }else{
            gameLogin = false;
        }

        loginPanel.add(username);
        loginPanel.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        loginPanel.add(password);
        if(!gameLogin){
            JButton newUser = new JButton("Neuer Nutzer");
            newUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UserUI userUI = new UserUI();
                    userUI.drawRegisterUI(false);
                }
            });
            loginPanel.add(newUser);
        }
        JButton loginButton = new JButton("Anmelden");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameValue = username.getText();
                char[] passwordValue = password.getPassword();
                if(!usernameValue.isEmpty() && !(passwordValue.length==0)){
                    try {
                        String passwordHash = Security.getSecureHash(new String(passwordValue));
                        Application.getSession().login(usernameValue, passwordHash);//TODO return boolean needed? use exceptions
                        loginDialog.setVisible(false);
                        loginDialog.dispose();
                        if (gameLogin) {
                            listener.startRoundFor(Application.getSession().getLoggedInPlayer());
                        } else {
                            uiListener.drawUI();
                        }
                    } catch (PlayerRepository.PlayerNotFoundException|WrongPasswordException ex) {
                        JOptionPane.showMessageDialog(loginDialog, "Benutzername oder Passwort inkorrekt!",
                                "Anmeldung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                        password.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(loginDialog,"Bitte gebe Benutzername und Password ein!",
                            "Anmeldung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
            });
        loginPanel.add(loginButton);
        loginDialog.add(loginPanel);
        loginDialog.setSize(300,150);
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog.setVisible(true);
    }

    public void drawRegisterUI(boolean addToGame){
        registerDialog = new JDialog(Application.getUi(), "Neuen Nutzer registrieren", true);
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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameValue = username.getText();
                String fullnameValue = fullname.getText();
                char[] passwordValue = password.getPassword();
                if(!usernameValue.isEmpty() && !fullnameValue.isEmpty() && !(passwordValue.length==0)){
                    if(passwordValue.length>5){
                        try {
                            String passwordHash = Security.getSecureHash(new String(password.getPassword()));
                            Player p = new Player(username.getText(), fullname.getText());
                            PlayerRepository.createPlayer(p,passwordHash);
                            registerDialog.setVisible(false);
                            registerDialog.dispose();

                            if(addToGame){
                                gameUIListener.addToGame(p);
                            }
                        }catch(PlayerRepository.PlayerAlreadyExistsException ex1) {
                            JOptionPane.showMessageDialog(registerDialog, ex1.getMessage(), "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                        }catch (Exception ex2) {
                            ex2.printStackTrace();
                            JOptionPane.showMessageDialog(registerDialog, "Fehler beim Anlegen des Nutzers",
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
            }
        });
        registerPanel.add(registerButton);
        registerDialog.add(registerPanel);
        registerDialog.setSize(250,150);
        registerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerDialog.setVisible(true);
    }
}
