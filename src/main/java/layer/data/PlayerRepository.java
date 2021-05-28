package layer.data;

import org.sqlite.SQLiteException;

import java.sql.*;

public class PlayerRepository {
    private Player player;
    private String password;

    private PlayerRepository(String username, String fullname, String password) {
        this.player = new Player(username,fullname);
        this.password = password;
    }

    public void changeUserName(String newUsername) throws SQLException, PlayerAlreadyExistsException {
        try{
            Database.updateEntry("UPDATE player SET username = ? WHERE username = ?",newUsername,this.player.getUsername());
        }catch(SQLiteException ex){
            if(ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed" +
                    " (UNIQUE constraint failed: player.username)")){
                throw new PlayerAlreadyExistsException();
            }
        }
    }
    public void changePassword(String newPassword) throws SQLException {
        Database.updateEntry("UPDATE player SET password = ? WHERE username = ?",newPassword,this.player.getUsername());
    }
    public void changeFullname(String newFullname) throws SQLException {
        Database.updateEntry("UPDATE player SET fullname = ? WHERE username = ?", newFullname, this.player.getUsername());
    }

    public boolean isPasswordCorrect(String password){
        return this.password.equals(password);
    }

    public Player getPlayer(){
        return this.player;
    }

    public static PlayerRepository getPlayerRepository(String username) throws PlayerNotFoundException{
        String sql = "SELECT username, password, fullname FROM player WHERE username = ?";
        String fullname=null;
        String password=null;
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ){
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                fullname = rs.getString("fullname");
                password = rs.getString("password");
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new PlayerNotFoundException("Spieler "+username+" existiert nicht");
        }
        if(fullname==null || password == null){
            throw new PlayerNotFoundException();
        }

        return new PlayerRepository(username, fullname, password);
    }

    public static PlayerRepository getPlayerRepository(Player p) throws PlayerNotFoundException{
        return PlayerRepository.getPlayerRepository(p.getUsername());
    }

    public static void createPlayer(Player p, String password) throws PlayerAlreadyExistsException, SQLException {
        String sql = "INSERT INTO player (username, password, fullname) VALUES (?,?,?)";
        try(Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,p.getUsername());
            pstmt.setString(2,password);
            pstmt.setString(3,p.getFullname());
            pstmt.executeUpdate();
        }catch(SQLiteException ex){
            if(ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed" +
                    " (UNIQUE constraint failed: player.username)")){
                throw new PlayerAlreadyExistsException();
            }
            ex.printStackTrace();
        }
    }

    public void deleteUser() throws SQLException {
        Database.updateEntry("DELETE FROM player WHERE username = ?",this.player.getUsername(),"");
    }

    public static class PlayerNotFoundException extends Exception {
        public PlayerNotFoundException() {
            super("Spieler*in existiert nicht");
        }
        public PlayerNotFoundException(String message) {
            super(message);
        }
    }

    public static class PlayerAlreadyExistsException extends Exception{
        public PlayerAlreadyExistsException(){
            super("Benutzername bereits vergeben.");
        }
        public PlayerAlreadyExistsException(String message){
            super(message);
        }
    }
}
