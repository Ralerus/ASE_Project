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
    public boolean changePassword(String newPassword){
        //DB set new password
        return true;
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

    public static PlayerRepository getPlayerRepository(String username) throws PlayerNotFoundException{
        //read corresponding data from database to given username

        String fullname = "";
        String password = "abc";
        //create PlayerRepository with data

        if(false){
            throw new PlayerNotFoundException("Spieler "+username+" existiert nicht");
        }

        return new PlayerRepository(username, fullname, password);
    }

    public static PlayerRepository getPlayerRepository(Player p) throws PlayerNotFoundException{
        return PlayerRepository.getPlayerRepository(p.getUsername());
    }

    public static boolean createPlayer(Player p, String password){
        //create Player in DB
        return true;
    }
}
