package layer.presentation;

import layer.Application;
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
    public void setListener(RoundListener listener) {
        this.listener = listener;
    }

    public void displayRoundFor(Player p, String text){
        System.out.println(p.getUsername()+" plays round");
        jDialog = new JDialog(Application.getUi(),"Runde von "+p.getUsername(), true);
        jDialog.setLayout(new GridLayout(8,1));
        JPanel jpanel = new JPanel();
        textArea = new JTextArea(text,3,50);
        textArea.setEditable(false);
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

        jpanel.add(textArea);
        jpanel.add(userInput);
        jpanel.add(new JLabel("Wettkampf läuft ..."));
        jDialog.add(jpanel);

        jDialog.setSize(1000,800);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setVisible(true);
    }

    public void closeRound(){
        jDialog.setVisible(false);
        jDialog.dispose();
    }
}
