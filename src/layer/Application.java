package layer;

import layer.domain.Session;
import layer.presentation.ApplicationUI;

public class Application {
    private static Session session = new Session();
    private static ApplicationUI ui;

    public static void main(String[] args) {
        Application.ui = new ApplicationUI();
    }

    public static Session getSession() {
        return session;
    }
}
