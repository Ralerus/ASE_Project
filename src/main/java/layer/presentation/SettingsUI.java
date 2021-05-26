package layer.presentation;

import javax.swing.*;
import java.awt.*;

public class SettingsUI {
    public JPanel getSettingsUI() {
        JPanel settingsUI = new JPanel();
        settingsUI.setLayout(new BorderLayout());
        settingsUI.add(UserManagementUI.getUserMananagementUI(), BorderLayout.WEST);
        settingsUI.add(TextManagementUI.getAddTextUI(), BorderLayout.EAST);
        return settingsUI;
    }


}
