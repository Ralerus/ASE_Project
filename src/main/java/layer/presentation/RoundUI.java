package layer.presentation;

import application.Application;
import layer.data.Player;
import layer.domain.RoundListener;
import layer.domain.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class RoundUI {
    private static JDialog jDialog;
    private static JTextArea textArea;
    private static RoundListener listener;
    public static void setListener(RoundListener listener) {
        RoundUI.listener = listener;
    }

    public static void displayRoundFor(String text){
        Player p = Session.getLoggedInPlayer();
        System.out.println(p.getUsername()+" plays round");
        JOptionPane.showConfirmDialog(Application.getUi(),  "Bist du bereit, "+p.getUsername()+"?",
                "Bereit?", JOptionPane.DEFAULT_OPTION);
        jDialog = new JDialog(Application.getUi(),"Runde von "+p.getUsername(), true);

        JPanel dialogContent = new JPanel();
        dialogContent.setLayout(new GridLayout(3,1));
        textArea = new JTextArea(text,3,50);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(new Color(248,248,248));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Serif", Font.PLAIN,16));
        JTextArea userInput = new JTextArea(8,50);
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        userInput.setFont(new Font("Arial", Font.PLAIN, 15));
        userInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(listener.checkCurrentInputChar(e.getKeyChar())){
                    textArea.setText(listener.getTextLeft());
                }else{
                    JOptionPane.showMessageDialog(jDialog, "Vertippt!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        listener.setStartTime();
        dialogContent.add(textArea);
        dialogContent.add(userInput);
        dialogContent.add(new JLabel("Wettkampf läuft ..."));
        dialogContent.setBorder(new EmptyBorder(8,8,8,8));
        jDialog.add(dialogContent);
        jDialog.setSize(600,375);
        jDialog.setLocationRelativeTo(Application.getUi());
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setVisible(true);
    }

    public static void closeRound(){
        jDialog.setVisible(false);
        jDialog.dispose();
    }
}
