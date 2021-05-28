package layer.presentation;

import application.Application;

import javax.swing.*;
import java.awt.*;

public class ApplicationUI extends JFrame implements UIListener {
    private static Image appIcon;
    public ApplicationUI(){
        try {
            appIcon = new ImageIcon(this.getClass().getResource("icon.png")).getImage();
            setIconImage(appIcon);
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        Login.create().withTitle("Anmeldung").atAppStart(this).withRegisterButton().build();
    }

    @Override
    public void drawUI() {
        this.setTitle("Tippduell - "+Application.getSession().getLoggedInPlayer().getUsername()+" angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        tabbedpane.addTab("Wettkampf", GameUI.getCompetitionUI());
        tabbedpane.addTab("Training", GameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", StatsUI.getStatsUI());
        tabbedpane.addTab("Einstellungen", SettingsUI.getSettingsUI());

        this.add(tabbedpane);

        this.setSize(830,520);
        this.setLocationRelativeTo(Application.getUi());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static Image getAppIcon() {
        return appIcon;
    }
}
