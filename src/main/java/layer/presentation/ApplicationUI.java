package layer.presentation;

import application.Application;

import javax.swing.*;

public class ApplicationUI extends JFrame implements UIListener {
    private static final GameUI gameUI = new GameUI();
    public ApplicationUI(){
        UserUI.setUiListener(this);
        UserUI.drawLoginUIFor(null);
    }

    @Override
    public void drawUI() {
        this.setTitle("Tippduell - "+Application.getSession().getLoggedInPlayer().getUsername()+" angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        tabbedpane.addTab("Wettkampf", gameUI.getCompetitionUI());
        tabbedpane.addTab("Training", gameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", StatsUI.getStatsUI());
        tabbedpane.addTab("Einstellungen", SettingsUI.getSettingsUI());

        this.add(tabbedpane);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public GameUI getGameUI(){
        return gameUI;
    }
}
