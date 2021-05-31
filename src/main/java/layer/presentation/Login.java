package layer.presentation;

import application.Application;
import layer.data.ObjectNotFoundException;
import layer.data.Player;
import layer.data.Security;
import layer.domain.GameListener;
import layer.domain.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("AccessStaticViaInstance")
public final class Login {
    private String title;
    private Player p;
    private boolean withRegisterButton;
    private static LoginListener loginListener;

    private Login(){
        this.withRegisterButton = false;
        this.loginListener = null;
    }

    public static Login create() {
        return new Login();
    }
    public CreateableLogin withTitle(String title) {
        this.title = title;
        return new CreateableLogin();
    }

    @SuppressWarnings("AccessStaticViaInstance")
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
            Login.this.loginListener = gameListener;
            return this;
        }
        public CreateableLogin atAppStart(LoginListener loginListener){
            Login.this.loginListener = loginListener;
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
            newUser.addActionListener(e -> Registration.drawUI(false));
            loginPanel.add(newUser);
        }
        JButton loginButton = new JButton("Anmelden");
        loginButton.addActionListener(e -> {
            String usernameValue = username.getText();
            char[] passwordValue = password.getPassword();
            if(!usernameValue.isEmpty() && (passwordValue.length!=0)){
                try {
                    String passwordHash = Security.getSecureHash(new String(passwordValue));
                    Session.login(usernameValue, passwordHash);
                    loginDialog.setVisible(false);
                    loginDialog.dispose();
                    if(loginListener!=null){
                        loginListener.goOn();
                    }
                } catch (ObjectNotFoundException | Session.WrongPasswordException ex) {
                    JOptionPane.showMessageDialog(loginDialog, "Benutzername oder Passwort inkorrekt!",
                            "Anmeldung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    password.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(loginDialog,"Bitte gebe Benutzername und Password ein!",
                        "Anmeldung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
            }
        });
        try {
            loginDialog.setIconImage(ApplicationUI.getAppIcon());
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        loginPanel.add(loginButton);
        loginPanel.setBorder(new EmptyBorder(5,5,2,5));
        loginDialog.add(loginPanel);
        loginDialog.setSize(350,150);
        loginDialog.setLocationRelativeTo(Application.getUi());
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog.setVisible(true);
    }

}
