package layer.presentation;

import application.Application;
import layer.data.Player;
import layer.domain.RoundListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RoundUI {
    private static JDialog jDialog;
    private static JTextArea textArea;
    private static RoundListener listener;
    public static void setListener(RoundListener listener) {
        RoundUI.listener = listener;
    }

    public static void displayRoundFor(String text){
        Player p = Application.getSession().getLoggedInPlayer();
        System.out.println(p.getUsername()+" plays round");
        JOptionPane.showConfirmDialog(Application.getUi(),  "Bist du bereit, "+p.getUsername()+"?",
                "Bereit?", JOptionPane.DEFAULT_OPTION);
        jDialog = new JDialog(Application.getUi(),"Runde von "+p.getUsername(), true);
        jDialog.setLayout(new GridLayout(3,1));
        textArea = new JTextArea(text,3,50);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(new Color(238,238,238));
        JTextArea userInput = new JTextArea(4,50);
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
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e)  {}
        });
        listener.setStartTime();
        jDialog.add(textArea);
        jDialog.add(userInput);
        jDialog.add(new JLabel("Wettkampf läuft ..."));
        jDialog.setSize(600,300);
        jDialog.setLocationRelativeTo(Application.getUi());
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setVisible(true);
    }

    public static void closeRound(){
        jDialog.setVisible(false);
        jDialog.dispose();
    }
}
