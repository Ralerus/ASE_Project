package layer.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import layer.Application;
import layer.data.Player;
import layer.data.PlayerNotFoundException;
import layer.domain.GameListener;
import layer.domain.WrongPasswordException;

public class LoginUI {
	private static JFrame jframe;
	private GameListener listener;
	private UIListener uiListener;

    public void setListener(GameListener listener) {
    	this.listener = listener;
    }
    public void setUiListener(UIListener listener) { this.uiListener = listener;}

    public void drawLoginUIFor(Player p) {
    	jframe = new JFrame("Login");
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
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Application.getSession().login(username.getText(), new String(password.getPassword()));//TODO return boolean needed? use exceptions
                    System.out.println("Succesfully logged in");
                    jframe.setVisible(false);
                    //replace if with polymorphism?
                    if (gameLogin) {
                        listener.startRoundFor(Application.getSession().getLoggedInPlayer());
                    } else {
                        uiListener.drawUI();
                    }
                } catch (PlayerNotFoundException|WrongPasswordException ex) {
                    JOptionPane.showMessageDialog(jframe, "Benutzername oder Passwort inkorrekt!", "Login fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    password.setText("");
                }
            }
            });
        loginPanel.add(loginButton);
        jframe.add(loginPanel);
        jframe.setSize(250,150);
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jframe.setVisible(true);
    }
}
