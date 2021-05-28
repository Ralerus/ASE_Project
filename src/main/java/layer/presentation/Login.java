package layer.presentation;

import application.Application;
import layer.data.Player;
import layer.data.PlayerRepository;
import layer.data.Security;
import layer.domain.GameListener;
import layer.domain.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Login {
    private String title;
    private Player p;
    private boolean isDuringGame;
    private boolean isAtAppStart;
    private boolean withRegisterButton;
    private static GameListener gameListener;
    private static UIListener uiListener;

    private Login(){
        this.isDuringGame = false;
        this.isAtAppStart = false;
        this.withRegisterButton = false;
    }

    public static Login create() {
        return new Login();
    }
    public CreateableLogin withTitle(String title) {
        this.title = title;
        return new CreateableLogin();
    }

    public class CreateableLogin {
        private CreateableLogin(){}
        public CreateableLogin forPlayer(Player p){
            Login.this.p = p;
            return this;
        }
        public CreateableLogin withRegisterButton(){
            Login.this.withRegisterButton=true;
            return this;
        }
        public CreateableLogin duringGame(GameListener gameListener){
            Login.this.isDuringGame=true;
            Login.this.gameListener = gameListener;
            return this;
        }
        public CreateableLogin atAppStart(UIListener uiListener){
            Login.this.isAtAppStart =true;
            Login.this.uiListener = uiListener;
            return this;
        }
        public void build(){
            Login.this.build();
        }
    }
    private void build(){
        JDialog loginDialog = new JDialog(Application.getUi(), title, true);
        JPanel loginPanel = new JPanel();

        loginPanel.setLayout(new GridLayout(3,2,6,3));
        loginPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();

        if(p!=null){
            username.setText(p.getUsername());
            username.setEditable(false);
            username.setFocusable(false);
        }
        loginPanel.add(username);
        loginPanel.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        loginPanel.add(password);
        if(withRegisterButton){
            JButton newUser = new JButton("Registrieren");
            newUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Registration registration = new Registration();
                    registration.drawUI(false);
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
                if(!usernameValue.isEmpty() && (passwordValue.length!=0)){
                    try {
                        String passwordHash = Security.getSecureHash(new String(passwordValue));
                        Application.getSession().login(usernameValue, passwordHash);
                        loginDialog.setVisible(false);
                        loginDialog.dispose();
                        if (isDuringGame) {
                            gameListener.startRound();
                        }else if(isAtAppStart){
                            uiListener.drawUI();
                        }
                    } catch (PlayerRepository.PlayerNotFoundException| Session.WrongPasswordException ex) {
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
        try {
            loginDialog.setIconImage(ApplicationUI.getAppIcon());
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        loginPanel.add(loginButton);
        loginDialog.add(loginPanel);
        loginDialog.setSize(300,150);
        loginDialog.setLocationRelativeTo(Application.getUi());
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog.setVisible(true);
    }

}
