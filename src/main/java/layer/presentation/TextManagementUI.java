package layer.presentation;

import application.Application;
import layer.data.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public abstract class TextManagementUI {
    private static final JTextField title = new JTextField();
    private static final JTextArea text = new JTextArea();
    private static final JRadioButton radioEasy = new JRadioButton("Einfach");
    private static final JRadioButton radioMedium = new JRadioButton("Mittel");
    private static final JRadioButton radioHard = new JRadioButton("Schwer");
    private static final JPanel searchResults = new JPanel();
    private static final JTextField searchField = new JTextField();

    public static JPanel getUI(){
        JPanel textManagement = new JPanel();
        textManagement.setLayout(new BoxLayout(textManagement, BoxLayout.PAGE_AXIS));
        textManagement.add(new JLabel("<html><h2>Text hinzufügen</h2></html>"));
        textManagement.add(TextManagementUI.getAddTextPanel());
        textManagement.add(new JLabel("<html><h2>Text suchen</h2></html>"));
        textManagement.add(TextManagementUI.getSearchPanel());

        return textManagement;
    }

    private static JPanel getAddTextPanel(){
        JPanel addText = new JPanel();
        addText.setLayout(new BoxLayout(addText, BoxLayout.PAGE_AXIS));

        addText.add(TextManagementUI.getTitleInputField());
        addText.add(TextManagementUI.getTextInputField());
        addText.add(TextManagementUI.getDifficultyInputField());
        addText.add(TextManagementUI.getAddButton());

        return addText;
    }

    private static JPanel getSearchPanel(){
        JPanel searchTexts = new JPanel();
        searchTexts.setLayout(new BoxLayout(searchTexts,BoxLayout.PAGE_AXIS));
        searchTexts.add(TextManagementUI.getSearchLine());
        searchResults.setLayout(new GridLayout(1,3));
        searchTexts.add(searchResults);
        return searchTexts;
    }

    private static JPanel getSearchLine(){
        JPanel searchLine = new JPanel();
        searchLine.setLayout(new GridLayout(1, 3));
        searchLine.add(new JLabel("Titel:"));
        searchLine.add(searchField);
        searchLine.add(TextManagementUI.getSearchButton());
        return searchLine;
    }

    private static JButton getSearchButton(){
        JButton searchButton = new JButton("Suchen");
        searchButton.addActionListener(e -> {
            String searchValue = searchField.getText();
            if(!searchValue.isEmpty()){
                try {
                    Text text = TextRepository.getTextByTitle(searchValue);
                    searchResults.removeAll();
                    searchResults.add(new JLabel(searchValue));
                    searchResults.add(TextManagementUI.getSearchButton(text));
                    searchResults.add(TextManagementUI.getDeleteButton(text));
                    searchResults.revalidate();
                    searchResults.repaint();
                    searchField.setText("");
                } catch (ObjectNotFoundException textNotFoundException) {
                    JOptionPane.showMessageDialog(Application.getUi(), textNotFoundException.getMessage(),
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                    searchField.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(Application.getUi(), "Bitte gebe einen Titel zur Suche ein!",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
        return searchButton;
    }

    private static JButton getSearchButton(Text text){
        JButton showButton = new JButton("Anzeigen");
        showButton.addActionListener(e -> JOptionPane.showMessageDialog(Application.getUi(),text.getText(),text.getTitle(),
                JOptionPane.INFORMATION_MESSAGE));
        return showButton;
    }

    private static JButton getDeleteButton(Text text){
        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(Application.getUi(),  "Text "+
                            text.getTitle()+" wirklich löschen?", "Löschbestätigung",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response==0){
                try {
                    TextRepository.deleteText(text);
                    JOptionPane.showMessageDialog(Application.getUi(),"Text "+
                            text.getTitle()+" erfolgreich gelöscht!","Löschen" +
                            " erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                    searchResults.removeAll();
                    searchResults.revalidate();
                    searchResults.repaint();
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(Application.getUi(),"Fehler beim Löschen" +
                            " des Textes","Fehler",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    private static JPanel getTitleInputField() {
        JPanel titleInput = new JPanel();
        titleInput.setLayout(new GridLayout(1,2));
        titleInput.add(new JLabel("Texttitel:"));
        title.setColumns(1);
        titleInput.add(title);
        return titleInput;
    }

    private static JPanel getTextInputField(){
        JPanel textInput = new JPanel();
        textInput.setLayout(new GridLayout(1,2));
        textInput.add(new JLabel("Text:"));
        text.setRows(15);
        text.setColumns(20);
        textInput.add(text);
        return textInput;
    }

    private static JPanel getDifficultyInputField(){
        JPanel difficultyInput = new JPanel();
        difficultyInput.setLayout(new GridLayout(1,2));
        difficultyInput.add(new JLabel("Schwierigkeitsgrad:"));

        JPanel difficultyRadiosPanel = new JPanel();
        difficultyRadiosPanel.setLayout(new GridLayout(3, 1));
        ButtonGroup difficultyRadios = new ButtonGroup();
        radioEasy.setSelected(true);

        difficultyRadios.add(radioEasy);
        difficultyRadios.add(radioMedium);
        difficultyRadios.add(radioHard);
        difficultyRadiosPanel.add(radioEasy);
        difficultyRadiosPanel.add(radioMedium);
        difficultyRadiosPanel.add(radioHard);

        difficultyInput.add(difficultyRadiosPanel);

        return difficultyInput;
    }

    @SuppressWarnings("ConstantConditions")
    private static JButton getAddButton(){
        JButton addButton = new JButton("Hinzufügen");
        addButton.addActionListener(e -> {
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
                try {
                    TextRepository.createText(titleValue, textValue, difficulty);
                    JOptionPane.showMessageDialog(Application.getUi(),title.getText()+" erfolgreich " +
                            "hinzugefügt.", "Text erfolgreich hinzugefügt", JOptionPane.INFORMATION_MESSAGE);
                    title.setText("");
                    text.setText("");
                    radioEasy.setSelected(false);
                    radioMedium.setSelected(false);
                    radioHard.setSelected(false);
                }catch(ObjectAlreadyExistsException ex){
                    JOptionPane.showMessageDialog(Application.getUi(), ex.getMessage(),
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(Application.getUi(), "Fehler beim Hinzufügen des Textes",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(Application.getUi(), "Bitte fülle alle Felder aus!",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
        return addButton;
    }
}
