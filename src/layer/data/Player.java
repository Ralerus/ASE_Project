package layer.data;

import java.util.Objects;

public class Player {
    private String username;
    private String fullname;
    private long currentResult;

    public Player(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
        this.currentResult = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public long getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(long currentResult) {
        this.currentResult = currentResult;
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
