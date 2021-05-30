package layer.presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class SettingsUI {
    public static JPanel getSettingsUI() {
        JPanel settingsUI = new JPanel();
        settingsUI.setLayout(new BorderLayout());
        settingsUI.add(UserManagementUI.getUI(), BorderLayout.WEST);
        settingsUI.add(TextManagementUI.getUI(), BorderLayout.EAST);
        settingsUI.setBorder(new EmptyBorder(5,5,2,5));
        return settingsUI;
    }


}
