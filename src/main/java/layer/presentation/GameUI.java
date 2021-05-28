package layer.presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.Application;
import layer.data.*;
import layer.domain.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameUI{
	private static List<Player> players;
	private static Game lastGame;
	private static JPanel playersList;
	private static Difficulty difficulty;
	private static int maxLength;
	private static int minLength;

	public static JPanel getCompetitionUI() {
		players = new ArrayList<>();
		JPanel competitionPanel = new JPanel();
		competitionPanel.setLayout(new BorderLayout());

		competitionPanel.add(setUpConfigurationPanel(), BorderLayout.WEST);
		competitionPanel.add(setUpAddUserPanel(), BorderLayout.EAST);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!players.isEmpty()){
					Rules rules = new Rules(difficulty,minLength,maxLength);
					Game game;
					try {
						game = new Game(players,rules,true);
						lastGame = game;
						game.start();
						players.add(Application.getSession().getLoggedInPlayer());
						refreshPlayersList();
					} catch (ObjectNotFoundException ex) {
						JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Fehler",
								JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(Application.getUi(), "Bitte füge zuerst Spieler hinzu," +
							" bevor du ein Spiel startest!", "Keine Spieler", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		competitionPanel.add(startButton, BorderLayout.SOUTH);
		competitionPanel.setBorder(new EmptyBorder(5,5,2,5));
		return competitionPanel;
	}
	
	public static JPanel getTrainingUI() {
		JPanel trainingPanel = new JPanel();
		trainingPanel.setLayout(new BorderLayout());

		trainingPanel.add(setUpConfigurationPanel(), BorderLayout.WEST);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Rules rules = new Rules(difficulty,minLength,maxLength);
				List<Player> singleplayer = new ArrayList<>();
				singleplayer.add(Application.getSession().getLoggedInPlayer());
				Game game;
				try {
					game = new Game(singleplayer,rules,false);
					lastGame = game;
					game.start();
				} catch (ObjectNotFoundException ex) {
					JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		trainingPanel.add(startButton, BorderLayout.SOUTH);
		trainingPanel.setBorder(new EmptyBorder(5,5,2,5));
		return trainingPanel;
	}
	public static void drawResults(Map<Player, Double> results, int textLength) {
		JDialog jDialog = new JDialog(Application.getUi(), "Ergebnisse", true);
		JPanel resultsPanelWithButton = new JPanel();
		resultsPanelWithButton.setLayout(new BoxLayout(resultsPanelWithButton, BoxLayout.PAGE_AXIS));
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(results.keySet().size(),4));
		System.out.println("Game over");
		int counter = 1;
		for(Player p: results.keySet()){
			if(counter == 1){
				JOptionPane.showMessageDialog(Application.getUi(),p.getUsername()+" hat gewonnen!",
						"Gewinner ermittelt", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println("Player "+p.getUsername()+" has "+results.get(p)+" seconds.");
			resultsPanel.add(new JLabel(counter+"."));
			resultsPanel.add(new JLabel(p.getUsername()));
			resultsPanel.add(new JLabel(results.get(p)+" Sekunden"));
			double roundedLettersPerSecond = Math.round((textLength/results.get(p))*100.0)/100.0;
			resultsPanel.add(new JLabel(roundedLettersPerSecond+" Zeichen/s"));
			counter++;
		}
		resultsPanelWithButton.add(resultsPanel);

		JButton playAgain = new JButton("Nochmal");
		playAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog.dispose();
				lastGame.playAgain();
			}
		});
		resultsPanelWithButton.add(playAgain);
		resultsPanelWithButton.setBorder(new EmptyBorder(5,5,2,5));
		jDialog.add(resultsPanelWithButton);

		jDialog.setSize(500,(100+results.keySet().size()*25));
		jDialog.setLocationRelativeTo(Application.getUi());
		jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jDialog.setVisible(true);

	}

	private static JPanel setUpConfigurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BorderLayout());
		configurationPanel.add(new JLabel("<html><h2>Konfiguration</h2></html>"), BorderLayout.NORTH);
		JPanel configurationOptions = new JPanel();
		configurationOptions.setLayout(new GridLayout(3, 2));
		configurationOptions.add(new JLabel("Schwierigkeitsgrad:"));

		JPanel difficultyRadiosPanel = new JPanel();
		difficultyRadiosPanel.setLayout(new GridLayout(3, 1));

		ButtonGroup difficultyRadios = new ButtonGroup();
		JRadioButton radioEasy = new JRadioButton("Einfach");
		radioEasy.setSelected(true);
		JRadioButton radioMedium = new JRadioButton("Mittel");
		JRadioButton radioHard = new JRadioButton("Schwer");
		difficultyRadios.add(radioEasy);
		difficultyRadios.add(radioMedium);
		difficultyRadios.add(radioHard);
		difficultyRadiosPanel.add(radioEasy);
		difficultyRadiosPanel.add(radioMedium);
		difficultyRadiosPanel.add(radioHard);

		ActionListener selectedRadio = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(difficultyRadios.getSelection().equals(radioEasy.getModel())){
					difficulty = Difficulty.Easy;
				}else if(difficultyRadios.getSelection().equals(radioMedium.getModel())){
					difficulty = Difficulty.Medium;
				}else if(difficultyRadios.getSelection().equals(radioHard.getModel())) {
					difficulty = Difficulty.Hard;
				}
			}
		};
		difficulty = Difficulty.Easy;
		radioMedium.addActionListener(selectedRadio);
		radioEasy.addActionListener(selectedRadio);
		radioHard.addActionListener(selectedRadio);

		configurationOptions.add(difficultyRadiosPanel);

		configurationOptions.add(new JLabel("Textlänge maximal:"));
		JSlider textLengthSliderMax = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		textLengthSliderMax.setMinorTickSpacing(25);
		textLengthSliderMax.setMajorTickSpacing(100);
		textLengthSliderMax.setPaintTicks(true);
		textLengthSliderMax.setPaintLabels(true);
		configurationOptions.add(textLengthSliderMax);
		textLengthSliderMax.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxLength = textLengthSliderMax.getValue();
			}
		});
		maxLength = textLengthSliderMax.getValue();
		configurationOptions.add(new JLabel("Textlänge minimal:"));
		JSlider textLengthSliderMin = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
		textLengthSliderMin.setMinorTickSpacing(25);
		textLengthSliderMin.setMajorTickSpacing(100);
		textLengthSliderMin.setPaintTicks(true);
		textLengthSliderMin.setPaintLabels(true);
		configurationOptions.add(textLengthSliderMin);
		textLengthSliderMin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minLength = textLengthSliderMin.getValue();
			}
		});
		minLength = textLengthSliderMin.getValue();
		configurationPanel.add(configurationOptions, BorderLayout.CENTER);

		return configurationPanel;
	}

	private static JPanel setUpAddUserPanel(){
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BorderLayout());
		JPanel addUserPanel = new JPanel();
		addUserPanel.setLayout(new GridLayout(4,1));
		addUserPanel.add(new JLabel("<html><h3>Spieler*innen hinzufügen</h3></html>"));

		JPanel addUserInputField = new JPanel();
		addUserInputField.setLayout(new GridLayout(1,3));
		addUserInputField.add(new JLabel("Benutzername:"));
		JTextField username = new JTextField();
		addUserInputField.add(username);
		JButton addUser = new JButton("+");
		playersList = new JPanel();
		playersList.setLayout(new GridLayout(12,1));
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Player p = PlayerRepository.getPlayerRepository(username.getText()).getPlayer();
					if(!players.contains(p)) {
						players.add(p);
						refreshPlayersList();
					}else{
						JOptionPane.showMessageDialog(Application.getUi(), "Du hast diese*n Spieler*in bereits" +
								" hinzugefügt.", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
					username.setText("");
				}catch (ObjectNotFoundException ex){
					JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Spieler*in nicht gefunden"
							, JOptionPane.ERROR_MESSAGE);
					username.setText("");
				}
			}
		});
		addUserInputField.add(addUser);
		JButton newUser = new JButton("Neuen Nutzer anlegen");
		newUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Registration.drawUI(true);
			}
		});
		addUserPanel.add(addUserInputField);
		addUserPanel.add(newUser);
		players.add(Application.getSession().getLoggedInPlayer());
		refreshPlayersList();
		playerPanel.add(addUserPanel, BorderLayout.NORTH);
		playerPanel.add(playersList, BorderLayout.CENTER);
		return playerPanel;
	}

	private static void refreshPlayersList(){
		playersList.removeAll();
		playersList.revalidate();
		for(Player p : players){
			JPanel user = new JPanel();
			user.setLayout(new GridLayout(1, 2));
			user.add(new JLabel(p.getUsername()));
			JButton removePlayer = new JButton("Entfernen");
			removePlayer.setActionCommand(p.getUsername());
			removePlayer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						players.remove(PlayerRepository.getPlayerRepository(removePlayer.getActionCommand()).getPlayer());
						refreshPlayersList();
					} catch (ObjectNotFoundException playerNotFoundException) {
						playerNotFoundException.printStackTrace();
					}
				}
			});
			user.add(removePlayer);
			playersList.add(user);
		}
		playersList.repaint();
	}

	public static void addToGame(Player p) {
		players.add(p);
		refreshPlayersList();
	}
}
