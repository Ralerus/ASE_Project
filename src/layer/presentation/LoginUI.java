package layer.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import layer.Application;
import layer.data.Player;
import layer.domain.MyListener;

public class LoginUI {
	private static JFrame jframe;
	private List<MyListener> listeners = new ArrayList<>();

    public void addListener(MyListener listener) {
    	listeners.add(listener);
    }

    public LoginUI(){
    	
    }
    
    public void drawLoginUIFor(Player p) {
    
    	jframe = new JFrame("Login");
    	JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,2,6,3));
        loginPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();

        if(p!=null){
            username.setText(p.getUsername());
            username.setEditable(false);
        }

        loginPanel.add(username);
        loginPanel.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        loginPanel.add(password);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Application.getSession().login(username.getText(),new String(password.getPassword()))){
                    System.out.println("Succesfully logged in");
                    jframe.setVisible(false);
                    
                    for(MyListener listener: listeners) {
                    	listener.loggedIn();
                    }
                }else{
                    JOptionPane.showMessageDialog(jframe,"Benutzername oder Passwort inkorrekt!","Login fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
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
