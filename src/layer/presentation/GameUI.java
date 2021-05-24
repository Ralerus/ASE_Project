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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameUI implements GameUIListener {
	private JRadioButton radioEasy;
	private JRadioButton radioMedium;
	private JRadioButton radioHard;
	private JSlider textLengthSliderMin;
	private JSlider textLengthSliderMax;
	private List<Player> players = new ArrayList<>();
	private static Game lastGame;
	private JPanel playersList;
	//private int maxLength;
	//private int minLength;

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
				Rules rules = new Rules(difficulty,  textLengthSliderMin.getValue(),textLengthSliderMax.getValue());
				Game game = null; //TODO builder pattern?
				try {
					game = new Game(players,rules,true);
					lastGame = game;
					game.start();
				} catch (TextRepository.TextNotFoundException ex) {
					JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				}
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
				Rules rules = new Rules(difficulty, textLengthSliderMin.getValue(),textLengthSliderMax.getValue());
				List<Player> singleplayer = new ArrayList<>();
				singleplayer.add(Application.getSession().getLoggedInPlayer());
				Game game = null; //TODO builder pattern?
				try {
					game = new Game(singleplayer,rules,false);
					lastGame = game;
					game.start();
				} catch (TextRepository.TextNotFoundException ex) {
					JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		trainingPanel.add(startButton, BorderLayout.SOUTH);
		return trainingPanel;
	}
	public static void drawResults(Map<Player, Double> results) {
		JDialog jDialog = new JDialog(Application.getUi(), "Ergebnisse", true);
		JPanel resultsPanelWithButton = new JPanel();
		resultsPanelWithButton.setLayout(new BoxLayout(resultsPanelWithButton, BoxLayout.PAGE_AXIS));
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(results.keySet().size(),3));
		System.out.println("Game over");
		int counter = 1;
		for(Player p: results.keySet()){
			if(counter == 1){
				JOptionPane.showMessageDialog(Application.getUi(),p.getUsername()+" hat gewonnen!", "Gewinner ermittelt", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println("Player "+p.getUsername()+" has "+results.get(p)+" seconds.");
			resultsPanel.add(new JLabel(counter+"."));
			resultsPanel.add(new JLabel(p.getUsername()));
			resultsPanel.add(new JLabel(results.get(p)+" Sekunden"));
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
		jDialog.add(resultsPanelWithButton);

		jDialog.setSize(500,400);
		jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jDialog.setVisible(true);

	}

	private JPanel setUpConfigurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BorderLayout());
		configurationPanel.add(new JLabel("Konfiguration"), BorderLayout.NORTH);
		JPanel configurationOptions = new JPanel();
		configurationOptions.setLayout(new GridLayout(3, 2));
		configurationOptions.add(new JLabel("Schwierigkeitsgrad:"));

		JPanel difficultyRadiosPanel = new JPanel();
		difficultyRadiosPanel.setLayout(new GridLayout(3, 1));

		ButtonGroup difficultyRadios = new ButtonGroup();
		radioEasy = new JRadioButton("Einfach");
		radioMedium = new JRadioButton("Mittel");
		radioHard = new JRadioButton("Schwer");
		difficultyRadios.add(radioEasy);
		difficultyRadios.add(radioMedium);
		difficultyRadios.add(radioHard);
		difficultyRadiosPanel.add(radioEasy);
		difficultyRadiosPanel.add(radioMedium);
		difficultyRadiosPanel.add(radioHard);

		configurationOptions.add(difficultyRadiosPanel);

		configurationOptions.add(new JLabel("Textlänge maximal:"));
		textLengthSliderMax = new JSlider(JSlider.HORIZONTAL, 50, 500, 250);
		textLengthSliderMax.setMinorTickSpacing(25);
		textLengthSliderMax.setMajorTickSpacing(100);
		textLengthSliderMax.setPaintTicks(true);
		textLengthSliderMax.setPaintLabels(true);
		configurationOptions.add(textLengthSliderMax);
		/*textLengthSliderMax.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxLength = textLengthSliderMax.getValue();
			}
		});*/
		configurationOptions.add(new JLabel("Textlänge minimal:"));
		textLengthSliderMin = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
		textLengthSliderMin.setMinorTickSpacing(25);
		textLengthSliderMin.setMajorTickSpacing(100);
		textLengthSliderMin.setPaintTicks(true);
		textLengthSliderMin.setPaintLabels(true);
		configurationOptions.add(textLengthSliderMin);
		/*textLengthSliderMin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minLength = textLengthSliderMin.getValue();
			}
		});*/
		configurationPanel.add(configurationOptions, BorderLayout.CENTER);

		return configurationPanel;
	}

	private JPanel setUpAddUserPanel(){
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BorderLayout());
		JPanel addUserPanel = new JPanel();
		addUserPanel.setLayout(new GridLayout(4,1));
		addUserPanel.add(new JLabel("Nutzer hinzufügen:"));

		JPanel addUserInputField = new JPanel();
		addUserInputField.setLayout(new GridLayout(1,3));
		addUserInputField.add(new JLabel("Benutzername:"));
		JTextField username = new JTextField();
		addUserInputField.add(username);
		JButton addUser = new JButton("+");
		playersList = new JPanel();
		playersList.setLayout(new GridLayout(21,1));
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					players.add(PlayerRepository.getPlayerRepository(username.getText()).getPlayer());
					refreshPlayersList();
					username.setText("");
				}catch (PlayerNotFoundException ex){
					JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(), "Spieler nicht gefunden", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addUserInputField.add(addUser);
		JButton newUser = new JButton("Neuen Nutzer anlegen");
		newUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserUI userUI = new UserUI();
				userUI.setGameUIListener(Application.getUi().getGameUI());
				userUI.drawRegisterUI(true);
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

	private void refreshPlayersList(){
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
					} catch (PlayerNotFoundException playerNotFoundException) {
						playerNotFoundException.printStackTrace();
					}
				}
			});
			user.add(removePlayer);
			playersList.add(user);
		}
		playersList.repaint();
	}

	@Override
	public void addToGame(Player p) {
		players.add(p);
		refreshPlayersList();
	}
}
