package layer.presentation;

import application.Application;
import layer.data.ObjectNotFoundException;
import layer.data.PlayerRepository;
import layer.domain.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ApplicationUI extends JFrame implements LoginListener {
    private static Image appIcon;
    public ApplicationUI(){
        try {
            appIcon = new ImageIcon(this.getClass().getResource("icon.png")).getImage();
            setIconImage(appIcon);
        }catch(NullPointerException ex){
            System.err.println("Icon not found");
        }
        Login.create().withTitle("Anmeldung").atAppStart(this).withRegisterButton().build();
    }

    @Override
    public void goOn() {
        this.setTitle("Tippduell - "+ Session.getLoggedInPlayer().getUsername()+" angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        tabbedpane.addTab("Wettkampf", GameUI.getCompetitionUI());
        tabbedpane.addTab("Training", GameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", StatsUI.getStatsUI());
        tabbedpane.addTab("Einstellungen", SettingsUI.getSettingsUI());

        this.add(tabbedpane);

        this.setSize(900,520);
        this.setLocationRelativeTo(Application.getUi());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        if(Session.getLoggedInPlayer().isFirstLogin()){
            int result = JOptionPane.showConfirmDialog(Application.getUi(), "Herzlich willkommen bei Tippduell, "+Session.getLoggedInPlayer().getFullName()+"!\n" +
                    "Dieses Pop-Up erklärt dir, wie ein Spiel funktioniert. Dann kannst du auch schon loslegen.\n\n" +
                    "1. Wähle die Spielkonfiguration und füge für Wettkämpfe andere Spieler*innen hinzu, Trainings bestreitest du alleine.\n" +
                    "2. Nach Rundenstart erscheint ein Text, den du möglichst fehlerfrei abtippen musst. Tippfehler müssen nicht korrigiert werden.\n" +
                    "3. Der*die schnellste Spieler*in gewinnt den Wettkampf.\n\n" +
                    "Viel Spaß!\n" +
                    "Dieses Pop-Up erneut anzeigen?", "Erste Schritte",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(result==1){
                try {
                    PlayerRepository.getPlayerRepository(Session.getLoggedInPlayer().getUsername()).setFirstLogin(false);
                } catch (SQLException | ObjectNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static Image getAppIcon() {
        return appIcon;
    }
}
