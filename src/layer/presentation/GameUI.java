package layer.presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import layer.Application;
import layer.data.*;
import layer.domain.Game;

import javax.swing.*;

public class GameUI {
	private JRadioButton radioEasy;
	private JRadioButton radioMedium;
	private JRadioButton radioHard;
	private JSlider textLengthSlider;
	private List<Player> players = new ArrayList<>();

	public JPanel getCompetitionUI() {
		JPanel competitionPanel = new JPanel();
		competitionPanel.setLayout(new BorderLayout());

		competitionPanel.add(setUpConfigurationPanel(), BorderLayout.WEST);
		competitionPanel.add(setUpAddUserPanel(), BorderLayout.EAST);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulty difficulty = Difficulty.Easy;
				if(radioEasy.isSelected()){
					difficulty = Difficulty.Easy;
				}else if(radioMedium.isSelected()){
					difficulty = Difficulty.Medium;
				}else if(radioHard.isSelected()){
					difficulty = Difficulty.Hard;
				}
				Rules rules = new Rules(difficulty, textLengthSlider.getValue(),50); //TODO set min text length
				Game game = new Game(players,rules); //TODO builder pattern?
				game.start();
			}
		});
		competitionPanel.add(startButton, BorderLayout.SOUTH);
		return competitionPanel;
	}
	
	public JPanel getTrainingUI() {
		JPanel trainingPanel = new JPanel();
		trainingPanel.setLayout(new BorderLayout());

		trainingPanel.add(setUpConfigurationPanel(), BorderLayout.WEST);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO listens to same radios as competition ui?
				Difficulty difficulty = Difficulty.Easy;
				if(radioEasy.isSelected()){
					difficulty = Difficulty.Easy;
				}else if(radioMedium.isSelected()){
					difficulty = Difficulty.Medium;
				}else if(radioHard.isSelected()){
					difficulty = Difficulty.Hard;
				}
				Rules rules = new Rules(difficulty, textLengthSlider.getValue(),50); //TODO set min text length
				List<Player> singleplayer = new ArrayList<>();
				singleplayer.add(Application.getSession().getLoggedInPlayer());
				Game game = new Game(singleplayer,rules); //TODO builder pattern?
				game.start();
			}
		});
		trainingPanel.add(startButton, BorderLayout.SOUTH);
		return trainingPanel;
	}
	public static void drawResults(Map<Player, Double> results) {
		JDialog jDialog = new JDialog(Application.getUi(), "Ergebnisse", true);
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(results.keySet().size(),3));
		System.out.println("Game over");
		int counter = 1;
		for(Player p: results.keySet()){
			if(counter == 1){
				JOptionPane.showMessageDialog(jDialog,p.getUsername()+" hat gewonnen!", "Gewinner ermittelt", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println("Player "+p.getUsername()+" has "+results.get(p)+" seconds.");
			resultsPanel.add(new JLabel(counter+"."));
			resultsPanel.add(new JLabel(p.getUsername()));
			resultsPanel.add(new JLabel(results.get(p)+" Sekunden"));
			counter++;
		}
		jDialog.add(resultsPanel);
		jDialog.setSize(500,400);
		jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jDialog.setVisible(true);
	}

	private JPanel setUpConfigurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BorderLayout());
		configurationPanel.add(new JLabel("Konfiguration"), BorderLayout.NORTH);
		JPanel configurationOptions = new JPanel();
		configurationOptions.setLayout(new GridLayout(2, 2));
		configurationOptions.add(new JLabel("Schwierigkeitsgrad:"));

		JPanel difficultyRadiosPanel = new JPanel();
		difficultyRadiosPanel.setLayout(new GridLayout(3, 1));

		ButtonGroup difficultyRadios = new ButtonGroup();
		radioEasy = new JRadioButton("Easy");
		radioMedium = new JRadioButton("Medium");
		radioHard = new JRadioButton("Hard");
		difficultyRadios.add(radioEasy);
		difficultyRadios.add(radioMedium);
		difficultyRadios.add(radioHard);
		difficultyRadiosPanel.add(radioEasy);
		difficultyRadiosPanel.add(radioMedium);
		difficultyRadiosPanel.add(radioHard);

		configurationOptions.add(difficultyRadiosPanel);

		configurationOptions.add(new JLabel("Textlänge:"));
		textLengthSlider = new JSlider(JSlider.HORIZONTAL, 50, 500, 250);
		textLengthSlider.setMinorTickSpacing(25);
		textLengthSlider.setMajorTickSpacing(100);
		textLengthSlider.setPaintTicks(true);
		textLengthSlider.setPaintLabels(true);
		configurationOptions.add(textLengthSlider);
		configurationPanel.add(configurationOptions, BorderLayout.CENTER);

		return configurationPanel;
	}

	private JPanel setUpAddUserPanel(){
		JPanel addUserPanel = new JPanel();
		addUserPanel.setLayout(new GridLayout(21,1));
		addUserPanel.add(new JLabel("Nutzer hinzufügen:"));

		JPanel addUserInputField = new JPanel();
		addUserInputField.setLayout(new GridLayout(1,3));
		addUserInputField.add(new JLabel("Benutzername:"));
		JTextField username = new JTextField();
		addUserInputField.add(username);
		JButton addUser = new JButton("+");
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					players.add(PlayerRepository.getPlayerRepository(username.getText()).getPlayer());
					addUserPanel.add(new JLabel(username.getText())); //TODO remove player button
					username.setText("");
				}catch (PlayerNotFoundException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Spieler nicht gefunden", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addUserInputField.add(addUser);
		addUserPanel.add(addUserInputField);
		players.add(Application.getSession().getLoggedInPlayer());
		addUserPanel.add(new JLabel((Application.getSession().getLoggedInPlayer().getUsername())));
		return addUserPanel;
	}
}
