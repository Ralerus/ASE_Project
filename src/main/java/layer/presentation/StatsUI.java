package layer.presentation;

import application.Application;
import layer.data.GameStats;
import layer.data.HistoryEntry;
import layer.data.PlayerStats;
import layer.data.StatsRepository;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StatsUI {
    private final static JPanel userStatsPanel = new JPanel();
    private final static JPanel userHistoryPanel = new JPanel();
    private final static JPanel userHighscorePanel = new JPanel();
    private final static JPanel gameStatsPanel = new JPanel();
    private final static JPanel gameHighscorePanel = new JPanel();

    public static JPanel getStatsUI(){
        JPanel statsUI = new JPanel();
        statsUI.setLayout(new BorderLayout());
        statsUI.add(StatsUI.getGameStatsUI(), BorderLayout.WEST);
        statsUI.add(StatsUI.getUserStatsUI(), BorderLayout.EAST);
        return statsUI;
    }

    private static JPanel getUserStatsUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        StatsUI.refreshUserStats();
        panel.add(userStatsPanel, BorderLayout.NORTH);
        panel.add(userHighscorePanel, BorderLayout.CENTER);
        panel.add(userHistoryPanel, BorderLayout.SOUTH);
        return panel;
    }

    private static JPanel getGameStatsUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        gameStatsPanel.setLayout(new BoxLayout(gameStatsPanel, BoxLayout.PAGE_AXIS));
        gameStatsPanel.add(new JLabel("<html><h2>Allgemeine Spielstatistik</h2></html>"));
        StatsUI.refreshGameStats();
        panel.add(gameStatsPanel, BorderLayout.NORTH);
        panel.add(gameHighscorePanel, BorderLayout.CENTER);
        return panel;
    }

    public static void refreshStatsUI(){
        StatsUI.refreshUserStats();
        StatsUI.refreshGameStats();
    }

    public static void refreshUserStats(){
        userStatsPanel.removeAll();
        userHistoryPanel.removeAll();
        userHighscorePanel.removeAll();

        PlayerStats playerStats = null;
        try {
            playerStats = StatsRepository.getStatsFor(Application.getSession().getLoggedInPlayer().getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(playerStats != null) {
            userStatsPanel.setLayout(new BoxLayout(userStatsPanel, BoxLayout.PAGE_AXIS));
            userStatsPanel.add(new JLabel("<html><h2>Spielerstatistik für " +
                    Application.getSession().getLoggedInPlayer().getUsername()+"</h2></html>"));
            userStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: " + playerStats.getNumberOfCompetitons()));
            userStatsPanel.add(new JLabel("Durchgeführte Trainings: " + playerStats.getNumberOfTrainings()));
            userStatsPanel.add(new JLabel("<html><h3>Beste Spiele:</h3></html>"));

            userHighscorePanel.setLayout(new GridLayout(6,4));
            userHighscorePanel.add(new JLabel("<html><h3>Platz</h3></html>"));
            userHighscorePanel.add(new JLabel("<html><h3>Tempo</h3></html>"));
            userHighscorePanel.add(new JLabel("<html><h3>Texttitel</h3></html>"));
            userHighscorePanel.add(new JLabel("<html><h3>Datum</h3></html>"));
            int counter=1;
            for(HistoryEntry h: playerStats.getHighscore()){
                userHighscorePanel.add(new JLabel(counter+"."));
                userHighscorePanel.add(new JLabel(h.getValue()+" Zeichen/s"));
                userHighscorePanel.add(new JLabel(h.getTextTitle()));
                userHighscorePanel.add(new JLabel(h.getFormattedDate()));
                counter++;
            }

            userHistoryPanel.setLayout(new BorderLayout());
            userHistoryPanel.add(new JLabel("<html><h3>Letzte Spiele:</h3></html>"), BorderLayout.NORTH);
            JPanel historyEntries = new JPanel();
            historyEntries.setLayout(new GridLayout(6, 3));
            historyEntries.add(new JLabel("<html><h3>Datum</h3></html>"));
            historyEntries.add(new JLabel("<html><h3>Tempo</h3></html>"));
            historyEntries.add(new JLabel("<html><h3>Texttitel</h3></html>"));
            for(HistoryEntry h : playerStats.getHistory()){
                historyEntries.add(new JLabel(h.getFormattedDate()));
                historyEntries.add(new JLabel(h.getValue()+" Zeichen/s"));
                historyEntries.add(new JLabel(h.getTextTitle()));
            }
            userHistoryPanel.add(historyEntries,BorderLayout.CENTER);
        }
        userStatsPanel.revalidate();
        userStatsPanel.repaint();
        userHistoryPanel.revalidate();
        userHistoryPanel.repaint();
        userHighscorePanel.revalidate();
        userHighscorePanel.repaint();
    }

    public static void refreshGameStats(){
        gameStatsPanel.removeAll();
        gameHighscorePanel.removeAll();

        GameStats gameStats = null;
        try {
            gameStats = StatsRepository.getStats();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(gameStats !=null) {
            gameStatsPanel.add(new JLabel("<html><h2>Allgemeine Spielstatistik</h2></html>"));
            gameStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: "+ gameStats.getNumberOfCompetitons()));
            gameStatsPanel.add(new JLabel("Durchgeführte Trainings: "+ gameStats.getNumberOfTrainings()));
            gameStatsPanel.add(new JLabel("Registrierte Spieler*innen: "+ gameStats.getNumberOfPlayers()));
            gameStatsPanel.add(new JLabel("<html><h3>Highscore-Liste:</h3></html>"));
            gameHighscorePanel.setLayout(new GridLayout(11, 5));
            gameHighscorePanel.add(new JLabel("<html><h3>Platz</h3></html>"));
            gameHighscorePanel.add(new JLabel("<html><h3>Spieler*in</h3></html>"));
            gameHighscorePanel.add(new JLabel("<html><h3>Tempo</h3></html>"));
            gameHighscorePanel.add(new JLabel("<html><h3>Texttitel</h3></html>"));
            gameHighscorePanel.add(new JLabel("<html><h3>Datum</h3></html>"));
            int counter = 1;
            for(HistoryEntry h : gameStats.getHighscore()){
                gameHighscorePanel.add(new JLabel(counter+"."));
                gameHighscorePanel.add(new JLabel(h.getUsername()));
                gameHighscorePanel.add(new JLabel(h.getValue()+" Zeichen/s"));
                gameHighscorePanel.add(new JLabel(h.getTextTitle()));
                gameHighscorePanel.add(new JLabel(h.getFormattedDate()));
                counter++;
            }
        }
        gameStatsPanel.revalidate();
        gameStatsPanel.repaint();
        gameHighscorePanel.revalidate();
        gameHighscorePanel.repaint();
    }
}
