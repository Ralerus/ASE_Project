package layer.presentation;

import application.Application;

import javax.swing.*;

public class ApplicationUI extends JFrame implements UIListener {
    private GameUI gameUI;
    public ApplicationUI(){
        UserUI userUI = new UserUI();
        userUI.setUiListener(this);
        userUI.drawLoginUIFor(null);
    }

    @Override
    public void drawUI() {
        this.setTitle("Tippduell - "+Application.getSession().getLoggedInPlayer().getUsername()+" angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        gameUI = new GameUI();
        SettingsUI settingsUI = new SettingsUI();
        StatsUI statsUI = new StatsUI();

        tabbedpane.addTab("Wettkampf", gameUI.getCompetitionUI());
        tabbedpane.addTab("Training", gameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", statsUI.getStatsUI());
        tabbedpane.addTab("Einstellungen", settingsUI.getSettingsUI());

        this.add(tabbedpane);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public GameUI getGameUI(){
        return gameUI;
    }
}
