package layer.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import layer.Application;
import layer.data.Player;
import layer.data.PlayerNotFoundException;
import layer.data.PlayerRepository;
import layer.domain.GameListener;
import layer.domain.WrongPasswordException;

public class UserUI {
	private JDialog registerDialog;
    private JDialog loginDialog;
	private GameListener listener;
	private UIListener uiListener;

    public void setListener(GameListener listener) {
    	this.listener = listener;
    }
    public void setUiListener(UIListener listener) { this.uiListener = listener;}

    public void drawLoginUIFor(Player p) {
        loginDialog = new JDialog(Application.getUi(), "Login", true);
    	JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,2,6,3));
        loginPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();

        boolean gameLogin;
        if(p!=null){
            username.setText(p.getUsername());
            username.setEditable(false);
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
                    userUI.drawRegisterUI();
                }
            });
            loginPanel.add(newUser);
        }
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Application.getSession().login(username.getText(), new String(password.getPassword()));//TODO return boolean needed? use exceptions
                    System.out.println("Succesfully logged in");
                    loginDialog.setVisible(false);
                    loginDialog.dispose();
                    if (gameLogin) {
                        listener.startRoundFor(Application.getSession().getLoggedInPlayer());
                    } else {
                        uiListener.drawUI();
                    }
                } catch (PlayerNotFoundException|WrongPasswordException ex) {
                    JOptionPane.showMessageDialog(loginDialog, "Benutzername oder Passwort inkorrekt!", "Login fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    password.setText("");
                }
            }
            });
        loginPanel.add(loginButton);
        loginDialog.add(loginPanel);
        loginDialog.setSize(300,150);
        loginDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginDialog.setVisible(true);
    }

    public void drawRegisterUI(){
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
                try {
                    PlayerRepository.createPlayer(new Player(username.getText(), fullname.getText()),new String(password.getPassword()));
                    System.out.println("User succesfully created");
                    registerDialog.setVisible(false);
                    registerDialog.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(registerDialog, "Fehler beim Anlegen des Nutzers", "Fehler", JOptionPane.ERROR_MESSAGE);
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
