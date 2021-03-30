package layer.presentation;

import layer.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationUI extends JFrame implements UIListener {
    public ApplicationUI(){
        LoginUI login = new LoginUI();
        login.setUiListener(this);
        login.drawLoginUIFor(null);
    }

    @Override
    public void drawUI() {
        this.setTitle("Tippduell - "+ Application.getSession().getLoggedInPlayer().getUsername()+" ist angemeldet");
        JTabbedPane tabbedpane = new JTabbedPane();

        GameUI gameUI = new GameUI();

        JPanel statsPanel = new JPanel();
        JPanel settingsPanel = new JPanel();

        tabbedpane.addTab("Wettkampf", gameUI.getCompetitionUI());
        tabbedpane.addTab("Training", gameUI.getTrainingUI());
        tabbedpane.addTab("Statistik", statsPanel);
        tabbedpane.addTab("Einstellungen", settingsPanel);

        this.add(tabbedpane);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
