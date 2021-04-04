package layer.presentation;

import layer.Application;
import layer.data.GameStats;
import layer.data.PlayerStats;
import layer.data.StatsRepository;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StatsUI {
    public JPanel getStatsUI(){
        JPanel statsUI = new JPanel();
        statsUI.setLayout(new BorderLayout());
        statsUI.add(this.getGameStatsUI(), BorderLayout.WEST);
        statsUI.add(this.getUserStatsUI(), BorderLayout.EAST);
        return statsUI;
    }

    private JPanel getUserStatsUI() {
        JPanel userStatsPanel = new JPanel();
        userStatsPanel.setLayout(new BoxLayout(userStatsPanel, BoxLayout.PAGE_AXIS));
        PlayerStats playerStats = null;
        try {
            playerStats = StatsRepository.getStatsFor(Application.getSession().getLoggedInPlayer().getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userStatsPanel.add(new JLabel("Spielerstatistik für "+ Application.getSession().getLoggedInPlayer().getUsername()));
        userStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: "+ playerStats.getNumberOfCompetitons()));
        userStatsPanel.add(new JLabel("Durchgeführte Trainings: "+ playerStats.getNumberOfTrainings()));
        userStatsPanel.add(new JLabel("Letzte Ergebnisse:"));
        userStatsPanel.add(new JLabel("Beste Spiele:"));
        return userStatsPanel;
    }

    private JPanel getGameStatsUI() {
        JPanel gameStatsPanel = new JPanel();
        gameStatsPanel.setLayout(new BoxLayout(gameStatsPanel, BoxLayout.PAGE_AXIS));
        gameStatsPanel.add(new JLabel("Allgemeine Spielstatistik"));
        GameStats gameStats = null;
        try {
            gameStats = StatsRepository.getStats();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(gameStats !=null) {
            gameStatsPanel.add(new JLabel("Durchgeführte Wettkämpfe: "+ gameStats.getNumberOfCompetitons()));
            gameStatsPanel.add(new JLabel("Durchgeführte Trainings: "+ gameStats.getNumberOfTrainings()));
            gameStatsPanel.add(new JLabel("Registrierte Spieler:innen:"+ gameStats.getNumberOfPlayers()));
            gameStatsPanel.add(new JLabel("Highscore:"));
        }
        return gameStatsPanel;
    }
}
