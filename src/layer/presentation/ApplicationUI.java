package layer.presentation;

import layer.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationUI extends JFrame implements UIListener {
    public ApplicationUI(){
        UserUI userUI = new UserUI();
        userUI.setUiListener(this);
        userUI.drawLoginUIFor(null);
    }

    @Override
    public void drawUI() {
        this.setTitle("Tippduell - "+Application.getSession().getLoggedInPlayer().getUsername()+" angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        GameUI gameUI = new GameUI();
        SettingsUI settingsUI = new SettingsUI();

        JPanel statsPanel = new JPanel();

        tabbedpane.addTab("Wettkampf", gameUI.getCompetitionUI());
        tabbedpane.addTab("Training", gameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", statsPanel);
        tabbedpane.addTab("Einstellungen", settingsUI.getSettingsUI());

        this.add(tabbedpane);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
