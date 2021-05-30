package layer.data;

import org.sqlite.SQLiteException;

import java.sql.*;

public final class PlayerRepository {
    private final Player player;
    private final String password;

    private PlayerRepository(String username, String fullname, String password, boolean firstLogin) {
        this.player = new Player(username,fullname, firstLogin);
        this.password = password;
    }

    public void changeUserName(String newUsername) throws SQLException, ObjectAlreadyExistsException {
        try {
            Database.updateEntry("UPDATE player SET username = ? WHERE username = ?", newUsername, this.player.getUsername());
        }catch (SQLiteException ex) {
            if (ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed" +
                    " (UNIQUE constraint failed: player.username)")) {
                throw new ObjectAlreadyExistsException("Benutzername bereits vergeben.");
            }else{
                throw ex;
            }
        }
    }
    public void changePassword(String newPassword) throws SQLException {
        Database.updateEntry("UPDATE player SET password = ? WHERE username = ?",newPassword,this.player.getUsername());
    }
    public void changeFullname(String newFullname) throws SQLException {
        Database.updateEntry("UPDATE player SET fullname = ? WHERE username = ?", newFullname, this.player.getUsername());
    }

    public void setFirstLogin(boolean firstLogin) throws SQLException {
        Database.updateEntry("UPDATE player SET firstLogin = ? WHERE username = ?",(firstLogin?"1":"0"), this.player.getUsername());
    }

    public boolean isPasswordCorrect(String password){
        return this.password.equals(password);
    }

    public Player getPlayer(){
        return this.player;
    }

    public static PlayerRepository getPlayerRepository(String username) throws ObjectNotFoundException{
        String sql = "SELECT username, password, fullname, firstLogin FROM player WHERE username = ?";
        String fullname=null;
        String password=null;
        boolean firstLogin=false;
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                fullname = rs.getString("fullname");
                password = rs.getString("password");
                firstLogin = (rs.getInt("firstLogin") == 1);
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new ObjectNotFoundException("Spieler*in "+username+" existiert nicht.");
        }
        if(fullname==null || password == null){
            throw new ObjectNotFoundException("Spieler*in nicht gefunden.");
        }

        return new PlayerRepository(username, fullname, password, firstLogin);
    }

    public static PlayerRepository getPlayerRepository(Player p) throws ObjectNotFoundException{
        return PlayerRepository.getPlayerRepository(p.getUsername());
    }

    public static void createPlayer(Player p, String password) throws ObjectAlreadyExistsException, SQLException {
        String sql = "INSERT INTO player (username, password, fullname, firstLogin) VALUES (?,?,?,?)";
        try(Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,p.getUsername());
            pstmt.setString(2,password);
            pstmt.setString(3,p.getFullName());
            pstmt.setString(4,(p.isFirstLogin()?"1":"0"));
            pstmt.executeUpdate();
        }catch(SQLiteException ex){
            if(ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed" +
                    " (UNIQUE constraint failed: player.username)")){
                throw new ObjectAlreadyExistsException("Benutzername bereits vergeben.");
            }else{
                throw ex;
            }
        }
    }

    public void deleteUser() throws SQLException {
        Database.updateEntry("DELETE FROM player WHERE username = ?",this.player.getUsername(),"");
    }
}
