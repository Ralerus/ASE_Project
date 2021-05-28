package layer.presentation;

import application.Application;
import layer.data.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        statsUI.add(new JLabel("alle Tempoangaben sind in Zeichen/Sekunde (Z/s)"), BorderLayout.SOUTH);
        statsUI.setBorder(new EmptyBorder(5,5,2,5));
        return statsUI;
    }

    private static JPanel getUserStatsUI() {
        JPanel panel = new JPanel();
        GroupLayout lyt = new GroupLayout(panel);
        StatsUI.refreshUserStats();
        panel.setLayout(lyt);

        lyt.setVerticalGroup(
                lyt.createSequentialGroup().addComponent(userStatsPanel).addComponent(userHighscorePanel).addComponent(userHistoryPanel)
        );
        lyt.setHorizontalGroup(lyt.createSequentialGroup().addGroup(lyt.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(userStatsPanel).addComponent(userHighscorePanel).addComponent(userHistoryPanel)));
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

            userHighscorePanel.setLayout(new BorderLayout());
            userHighscorePanel.add(new JLabel("<html><h3>Beste Wettkämpfe:</h3></html>"), BorderLayout.NORTH);
            String[] columnNames = {"Nummer","Tempo","Texttitel","Datum"};
            userHighscorePanel.add(createTable(playerStats.getHighscore(),columnNames), BorderLayout.CENTER);

            userHistoryPanel.setLayout(new BorderLayout());
            userHistoryPanel.add(new JLabel("<html><h3>Letzte Wettkämpfe:</h3></html>"), BorderLayout.NORTH);
            String[] columnNamesHistory = {"Tempo","Texttitel","Datum"};
            userHistoryPanel.add(createTable(playerStats.getHistory(),columnNamesHistory),BorderLayout.CENTER);
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
            String[] columnNames = {"Nummer","Spieler*in","Tempo","Texttitel","Datum"};
            gameHighscorePanel.add(createTable(gameStats.getHighscore(),columnNames));
        }
        gameStatsPanel.revalidate();
        gameStatsPanel.repaint();
        gameHighscorePanel.revalidate();
        gameHighscorePanel.repaint();
    }

    private static JScrollPane createTable(List<StatsEntry> data, String[] columnNames){
        if(data.size()>=1) {
            String[][] tableData = new String[data.size()][columnNames.length];
            List<String> columnNamesList = new ArrayList<>(Arrays.asList(columnNames));

            int row = 0;
            for (StatsEntry s : data) {
                int col = 0;
                if (columnNamesList.contains("Nummer")) {
                    tableData[row][col] = (row + 1) + ".";
                    col++;
                }
                if (columnNamesList.contains("Spieler*in")) {
                    tableData[row][col] = s.getUsername();
                    col++;
                }
                tableData[row][col++] = s.getSpeed() + " Z/s";
                tableData[row][col++] = s.getTextTitle();
                tableData[row][col] = s.getFormattedDate();
                row++;
            }

            JTable table = new JTable(tableData, columnNames);
            table.setRowHeight(23);
            table.setEnabled(false);
            table.getColumnModel().getColumn((columnNames.length - 1)).setPreferredWidth(90);
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            table.setFillsViewportHeight(true);

            return new JScrollPane(table);
        }else{
            return new JScrollPane(new JLabel("Noch keine Daten vorhanden."));
        }
    }
}
