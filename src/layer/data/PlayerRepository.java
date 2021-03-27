package layer.data;

public class PlayerRepository {
    private Player player;
    private String password;

    private PlayerRepository(String username, String fullname, String password) {
        this.player = new Player(username,fullname);
        this.password = password;
    }

    public boolean changeUserName(String newUsername){
        //DB set new username
        return true;
    }
    public boolean changePassword(String oldPassword, String newPassword){
        if(this.isPasswordCorrect(oldPassword)){
            //DB set new password
            return true;
        }
        return false;
    }
    public boolean changeFullname(String newFullname){
        //DB set new fullname
        return true;
    }
    public boolean isPasswordCorrect(String password){
        return this.password.equals(password);
    }

    public Player getPlayer(){
        return this.player;
    }

    public static PlayerRepository getPlayerRepository(String username){
        //read corresponding data from database to given username

        String fullname = "";
        String password = "";
        //create PlayerRepository with data

        //throw error if player not found

        return new PlayerRepository(username, fullname, password);
    }

    public static boolean createPlayer(Player p, String password){
        //create Player in DB
        return true;
    }
}
