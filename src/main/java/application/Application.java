package application;

import layer.data.Database;
import layer.presentation.ApplicationUI;

public class Application {
    private static ApplicationUI ui;

    public static void main(String[] args) {
        Database.setup();
        Application.ui = new ApplicationUI();
    }

    public static ApplicationUI getUi() {
        return ui;
    }
    public static void setUi(ApplicationUI ui) {
        Application.ui = ui;
    }
}
