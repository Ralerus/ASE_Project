package layer.presentation;

import layer.data.Player;
import layer.domain.RoundListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RoundUI {
    private static JFrame jframe;
    private static JTextArea textArea;
    private static RoundListener listener;
    public void setListener(RoundListener listener) {
        this.listener = listener;
    }

    public static void displayRoundFor(Player p, String text){
        System.out.println(p.getUsername()+" plays round");
        jframe = new JFrame("Round of "+p.getUsername());
        jframe.setLayout(new GridLayout(8,1));
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
                    JOptionPane.showMessageDialog(jframe, "Vertippt!", "Fehler", JOptionPane.ERROR_MESSAGE);
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
        jframe.add(jpanel);

        jframe.setSize(1000,800);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }
}
