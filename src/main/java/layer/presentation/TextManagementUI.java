package layer.presentation;

import application.Application;
import layer.data.Difficulty;
import layer.data.Text;
import layer.data.TextRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextManagementUI {
    public static JPanel getAddTextUI(){
        JPanel textManagement = new JPanel();
        textManagement.setLayout(new BoxLayout(textManagement, BoxLayout.PAGE_AXIS));
        textManagement.add(new JLabel("<html><h2>Text hinzufügen</h2></html>"));
        JPanel addText = new JPanel();
        addText.setLayout(new BoxLayout(addText, BoxLayout.PAGE_AXIS));

        JPanel titleInput = new JPanel();
        titleInput.setLayout(new GridLayout(1,2));
        titleInput.add(new JLabel("Texttitel:"));
        JTextField title = new JTextField();
        title.setColumns(1);
        titleInput.add(title);
        addText.add(titleInput);

        JPanel textInput = new JPanel();
        textInput.setLayout(new GridLayout(1,2));
        textInput.add(new JLabel("Text:"));
        JTextArea text = new JTextArea();
        text.setRows(15);
        text.setColumns(20);
        textInput.add(text);
        addText.add(textInput);

        JPanel difficultyInput = new JPanel();
        difficultyInput.setLayout(new GridLayout(1,2));
        difficultyInput.add(new JLabel("Schwierigkeitsgrad:"));
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
        difficultyInput.add(difficultyRadiosPanel);
        addText.add(difficultyInput);

        JButton ok = new JButton("Hinzufügen");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleValue = title.getText();
                String textValue = text.getText();

                if(!titleValue.isEmpty() && !textValue.isEmpty()){
                    Difficulty difficulty = Difficulty.Easy;
                    if(radioEasy.isSelected()){
                        difficulty = Difficulty.Easy;
                    }else if(radioMedium.isSelected()){
                        difficulty = Difficulty.Medium;
                    }else if(radioHard.isSelected()){
                        difficulty = Difficulty.Hard;
                    }
                    if(TextRepository.createText(titleValue, textValue, difficulty)){
                        JOptionPane.showMessageDialog(Application.getUi(),title.getText()+" erfolgreich " +
                                "hinzugefügt.", "Text erfolgreich hinzugefügt", JOptionPane.INFORMATION_MESSAGE);
                        title.setText("");
                        text.setText("");
                        radioEasy.setSelected(false);
                        radioMedium.setSelected(false);
                        radioHard.setSelected(false);
                    }else{
                        JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Hinzufügen des Textes",
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Bitte fülle alle Felder aus!",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addText.add(ok);
        textManagement.add(addText);
        textManagement.add(new JLabel("<html><h2>Text suchen</h2></html>"));

        JPanel searchTexts = new JPanel();
        searchTexts.setLayout(new BoxLayout(searchTexts,BoxLayout.PAGE_AXIS));
        JPanel searchLine = new JPanel();
        searchLine.setLayout(new GridLayout(1, 3));
        searchLine.add(new JLabel("Titel:"));
        JTextField searchField = new JTextField();
        searchLine.add(searchField);
        JButton searchButton = new JButton("Suchen");
        JPanel searchResults = new JPanel();
        searchResults.setLayout(new GridLayout(1,3));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = searchField.getText();
                if(!searchValue.isEmpty()){
                    try {
                        Text text = TextRepository.getTextByTitle(searchValue);
                        searchResults.removeAll();
                        searchResults.add(new JLabel(searchValue));
                        JButton showButton = new JButton("Anzeigen");
                        showButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(Application.getUi(),text.getText(),text.getTitle(),
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        });
                        searchResults.add(showButton);
                        JButton deleteButton = new JButton("Löschen");
                        deleteButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int response = JOptionPane.showConfirmDialog(Application.getUi(),  "Text "+
                                                text.getTitle()+" wirklich löschen?", "Löschbestätigung",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if(response==0){
                                    if(TextRepository.deleteText(text)){
                                        JOptionPane.showMessageDialog(Application.getUi(),"Text "+
                                                text.getTitle()+"erfolgreich gelöscht!","Löschen" +
                                                " erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                                        searchResults.removeAll();
                                        searchResults.revalidate();
                                        searchResults.repaint();
                                    }else{
                                        JOptionPane.showMessageDialog(Application.getUi(),"Fehler beim Löschen" +
                                                " des Textes","Fehler",JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        });
                        searchResults.add(deleteButton);
                        searchResults.revalidate();
                        searchResults.repaint();
                        searchField.setText("");
                    } catch (TextRepository.TextNotFoundException textNotFoundException) {
                        JOptionPane.showMessageDialog(Application.getUi(), textNotFoundException.getMessage(),
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                        searchField.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(Application.getUi(), "Bitte gebe einen Titel zur Suche ein!",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchLine.add(searchButton);
        searchTexts.add(searchLine);
        searchTexts.add(searchResults);
        textManagement.add(searchTexts);

        return textManagement;
    }
}
