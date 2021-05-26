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
        JPanel historyPanel = new JPanel();
        JPanel userStatsPanel = new JPanel();
        userStatsPanel.setLayout(new BoxLayout(userStatsPanel, BoxLayout.PAGE_AXIS));
        PlayerStats playerStats = null;
        try {
            playerStats = StatsRepository.getStatsFor(Application.getSession().getLoggedInPlayer().getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(playerStats != null) {
            userStatsPanel.add(new JLabel("<html><h2>Spielerstatistik für " +
                    Application.getSession().getLoggedInPlayer().getUsername()+"</h2></html>"));
            userStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: " + playerStats.getNumberOfCompetitons()));
            userStatsPanel.add(new JLabel("Durchgeführte Trainings: " + playerStats.getNumberOfTrainings()));
            userStatsPanel.add(new JLabel("Letzte Ergebnisse:"));
            userStatsPanel.add(new JLabel("Beste Spiele:"));
            historyPanel.setLayout(new GridLayout(6, 3));
            historyPanel.add(new JLabel("<html><h3>Datum</h3></html>"));
            historyPanel.add(new JLabel("<html><h3>Geschwindigkeit</h3></html>"));
            historyPanel.add(new JLabel("<html><h3>Text</h3></html>"));
            for(HistoryEntry h : playerStats.getHistory()){
                historyPanel.add(new JLabel(h.getDate()));
                historyPanel.add(new JLabel(h.getValue()+" Zeichen/s"));
                historyPanel.add(new JLabel(h.getTextTitle()));
            }
        }
        panel.add(userStatsPanel, BorderLayout.NORTH);
        panel.add(historyPanel, BorderLayout.SOUTH);
        return panel;
    }

    private static JPanel getGameStatsUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel gameStatsPanel = new JPanel();
        JPanel highscorePanel = new JPanel();
        gameStatsPanel.setLayout(new BoxLayout(gameStatsPanel, BoxLayout.PAGE_AXIS));
        gameStatsPanel.add(new JLabel("<html><h2>Allgemeine Spielstatistik</h2></html>"));
        GameStats gameStats = null;
        try {
            gameStats = StatsRepository.getStats();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(gameStats !=null) {
            gameStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: "+ gameStats.getNumberOfCompetitons()));
            gameStatsPanel.add(new JLabel("Durchgeführte Trainings: "+ gameStats.getNumberOfTrainings()));
            gameStatsPanel.add(new JLabel("Registrierte Spieler*innen:"+ gameStats.getNumberOfPlayers()));
            gameStatsPanel.add(new JLabel("Highscore:"));
            highscorePanel.setLayout(new GridLayout(11, 5));
            highscorePanel.add(new JLabel("<html><h3>Platz</h3></html>"));
            highscorePanel.add(new JLabel("<html><h3>Spieler*in</h3></html>"));
            highscorePanel.add(new JLabel("<html><h3>Geschwindigkeit</h3></html>"));
            highscorePanel.add(new JLabel("<html><h3>Text</h3></html>"));
            highscorePanel.add(new JLabel("<html><h3>Datum</h3></html>"));
            int counter = 1;
            for(HistoryEntry h : gameStats.getHighscore()){
                highscorePanel.add(new JLabel(counter+"."));
                highscorePanel.add(new JLabel(h.getUsername()));
                highscorePanel.add(new JLabel(h.getValue()+" Zeichen/s"));
                highscorePanel.add(new JLabel(h.getTextTitle()));
                highscorePanel.add(new JLabel(h.getDate()));
                counter++;
            }
        }
        panel.add(gameStatsPanel, BorderLayout.NORTH);
        panel.add(highscorePanel, BorderLayout.SOUTH);
        return panel;
    }
}
