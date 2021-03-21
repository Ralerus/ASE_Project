import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class GameUI {
    private JFrame jframe;
    private JFrame gameFrame;
    private JDialog loginDialog;
    private JDialog newUserDialog;

    private JRadioButton radioEasy;
    private JRadioButton radioMedium;
    private JRadioButton radioHard;
    private JSlider textLengthSlider;

    private JLabel currentPlayer = new JLabel("");
    JTextArea textArea;

    public void drawUI(){
        this.jframe = new JFrame("Tippduell");

        JTabbedPane tabbedpane = new JTabbedPane();

        JPanel trainingPanel = new JPanel();
        JPanel statsPanel = new JPanel();
        JPanel settingsPanel = new JPanel();

        tabbedpane.addTab("Wettkampf", setUpCompetitionPanel());
        tabbedpane.addTab("Training", trainingPanel);
        tabbedpane.addTab("Statistik", statsPanel);
        tabbedpane.addTab("Einstellungen", settingsPanel);

        this.jframe.add(tabbedpane);
        this.jframe.setSize(1000,800);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setVisible(true);

        loginDialog = new JDialog(this.jframe, "Login", true);
        loginDialog.add(setUpLoginPanel(true, ""));
        loginDialog.pack();
        loginDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginDialog.setVisible(true);


    }

    private JPanel setUpLoginPanel(boolean newUserButtonOn, String usernameValue){
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,2,6,3));
        loginPanel.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();

        if(!usernameValue.equals("")){
            username.setText(usernameValue);
            username.setEditable(false);
        }

        loginPanel.add(username);
        loginPanel.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        loginPanel.add(password);
        if(newUserButtonOn) {
            JButton newUserButton = new JButton("Neuen Nutzer anlegen");
            loginPanel.add(newUserButton);
            newUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newUserDialog = new JDialog(loginDialog, "Neuen Nutzer anlegen", true);
                    newUserDialog.add(setUpNewUserPanelTop());
                    newUserDialog.pack();
                    newUserDialog.setVisible(true);
                }
            });
        }
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = new Player(username.getText(),password.getPassword());
                if(App.loginPlayer(player)){
                    loginDialog.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(jframe,"Benutzername oder Passwort inkorrekt!","Login fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginPanel.add(loginButton);

        return loginPanel;
    }

    private JPanel setUpNewUserPanelTop(){
        JPanel newUserPanel = new JPanel();
        newUserPanel.setLayout(new BorderLayout());

        JPanel newUserPanelTop = new JPanel();
        newUserPanelTop.setLayout(new GridLayout(3,2,6,3));
        newUserPanelTop.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();
        newUserPanelTop.add(username);
        newUserPanelTop.add(new JLabel("Passwort:"));
        JPasswordField password = new JPasswordField();
        newUserPanelTop.add(password);
        newUserPanelTop.add(new JLabel("Passwort bestätigen:"));
        JPasswordField password_confirmation = new JPasswordField();
        newUserPanelTop.add(password_confirmation);

        JButton createUserButton = new JButton("Nutzer anlegen");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Arrays.equals(password.getPassword(), password_confirmation.getPassword())){
                    Player player = new Player(username.getText(), password.getPassword());
                    //create user
                    if(App.loginPlayer(player)){
                        JOptionPane.showMessageDialog(jframe, "Nutzer erfolgreich angelegt und angemeldet!", "Nutzer angelegt", JOptionPane.INFORMATION_MESSAGE);
                        newUserDialog.setVisible(false);
                        loginDialog.setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(jframe,"Fehler beim Anlegen des Nutzers!","Anlegen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(newUserDialog, "Passwörter stimmen nicht überein!", "Anlegen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        newUserPanel.add(newUserPanelTop, BorderLayout.NORTH);
        newUserPanel.add(createUserButton, BorderLayout.CENTER);

        return newUserPanel;
    }

    private JPanel setUpCompetitionPanel(){
        Game game = new Game();
        JPanel competitionPanel = new JPanel();
        competitionPanel.setLayout(new BorderLayout());

        competitionPanel.add(setUpConfigurationPanel(), BorderLayout.WEST);
        competitionPanel.add(setUpAddUserPanel(game), BorderLayout.EAST);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
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
                Rules rules = new Rules(difficulty, textLengthSlider.getValue());
                game.addRulesToGame(rules);
                App.setGame(game);
                game.play();
            }
        });
        competitionPanel.add(startButton, BorderLayout.SOUTH);
        return competitionPanel;
    }

    private JPanel setUpConfigurationPanel() {
        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new BorderLayout());
        configurationPanel.add(new JLabel("Konfiguration"), BorderLayout.NORTH);
        JPanel configurationOptions = new JPanel();
        configurationOptions.setLayout(new GridLayout(2, 2));
        configurationOptions.add(new JLabel("Schwierigkeitsgrad:"));

        JPanel difficultyRadiosPanel = new JPanel();
        difficultyRadiosPanel.setLayout(new GridLayout(3, 1));

        ButtonGroup difficultyRadios = new ButtonGroup();
        radioEasy = new JRadioButton("Easy");
        radioMedium = new JRadioButton("Medium");
        radioHard = new JRadioButton("Hard");
        difficultyRadios.add(radioEasy);
        difficultyRadios.add(radioMedium);
        difficultyRadios.add(radioHard);
        difficultyRadiosPanel.add(radioEasy);
        difficultyRadiosPanel.add(radioMedium);
        difficultyRadiosPanel.add(radioHard);

        configurationOptions.add(difficultyRadiosPanel);

        configurationOptions.add(new JLabel("Textlänge:"));
        textLengthSlider = new JSlider(JSlider.HORIZONTAL, 50, 500, 250);
        textLengthSlider.setMinorTickSpacing(25);
        textLengthSlider.setMajorTickSpacing(100);
        textLengthSlider.setPaintTicks(true);
        textLengthSlider.setPaintLabels(true);
        configurationOptions.add(textLengthSlider);
        configurationPanel.add(configurationOptions, BorderLayout.CENTER);

        return configurationPanel;
    }

    private JPanel setUpAddUserPanel(Game game){
        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridLayout(21,1));
        addUserPanel.add(new JLabel("Nutzer hinzufügen:"));

        JPanel addUserInputField = new JPanel();
        addUserInputField.setLayout(new GridLayout(1,3));
        addUserInputField.add(new JLabel("Benutzername:"));
        JTextField username = new JTextField();
        addUserInputField.add(username);
        JButton addUser = new JButton("+");

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.getPlayerWithResult().containsKey(App.getLoggedInPlayer())){
                    addUserPanel.add(new JLabel(App.getLoggedInPlayer().getUsername()));
                    game.addPlayerToGame(App.getLoggedInPlayer());
                }
                if(game.addPlayerToGame(username.getText())){
                    addUserPanel.add(new JLabel((username.getText())));
                    username.setText("");
                }else{
                    JOptionPane.showMessageDialog(jframe, "Spieler "+username.getText()+ " nicht gefunden.", "Spieler nicht gefunden", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addUserInputField.add(addUser);
        addUserPanel.add(addUserInputField);
        return addUserPanel;
    }

    public void drawGameUI(String title){
        this.gameFrame = new JFrame(title);

        gameFrame.setLayout(new GridLayout(8,1));

        gameFrame.add(currentPlayer);

        textArea = new JTextArea( App.getGame().getTextAsString(),3,50);
        textArea.setEditable(false);
        JTextArea userInput = new JTextArea(4,50);
        userInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(App.getGame().checkCurrentInputChar(e.getKeyChar())){
                    textArea.setText(App.getGame().getTextAsString());
                }else{
                    JOptionPane.showMessageDialog(gameFrame, "Vertippt!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e)  {}
        });

        gameFrame.add(textArea);
        gameFrame.add(userInput);
        gameFrame.add(new JLabel("Wettkampf läuft ..."));

        this.gameFrame.setSize(1000,800);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
        this.jframe.setVisible(false);
    }

    public void displayLoginForGame(Player player){
        loginDialog = new JDialog(this.gameFrame, "Login", true);
        loginDialog.add(setUpLoginPanel(false,player.getUsername()));
        loginDialog.pack();
        loginDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginDialog.setVisible(true);
        currentPlayer.setText("Spieler: "+player.getUsername());
    }
}
