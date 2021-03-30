package layer.data;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super("Spieler existiert nicht");
    }
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
