package layer.data;

import java.util.Objects;

public final class Player {
    private final String username;
    private final String fullName;
    private final boolean firstLogin;

    public Player(String username, String fullName, boolean firstLogin) {
        this.username = username;
        this.fullName = fullName;
        this.firstLogin = firstLogin;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
