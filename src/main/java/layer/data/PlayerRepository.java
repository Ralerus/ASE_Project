package layer.data;

import java.sql.*;

public class PlayerRepository {
    private Player player;
    private String password;

    private PlayerRepository(String username, String fullname, String password) {
        this.player = new Player(username,fullname);
        this.password = password;
    }

    public boolean changeUserName(String newUsername){
        try{
            Database.updateEntry("UPDATE player SET username = ? WHERE username = ?",newUsername,this.player.getUsername());
        } catch (SQLException e){
            return false;
        }
        return true;
    }
    public boolean changePassword(String newPassword){
        try{
            Database.updateEntry("UPDATE player SET password = ? WHERE username = ?",newPassword,this.player.getUsername());
        } catch (SQLException e){
            return false;
        }
        return true;
    }
    public boolean changeFullname(String newFullname){
        try{
            Database.updateEntry("UPDATE player SET fullname = ? WHERE username = ?",newFullname,this.player.getUsername());
        } catch (SQLException e){
            return false;
        }
        return true;
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

    public static boolean createPlayer(Player p, String password){
        String sql = "INSERT INTO player(username, password, fullname) VALUES (?,?,?)";
        try(Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,p.getUsername());
            pstmt.setString(2,password);
            pstmt.setString(3,p.getFullname());
            pstmt.executeUpdate();
        } catch (SQLException e){
            return false;
        }
        return true;
    }

    public boolean deleteUser(){
        try{
            Database.updateEntry("DELETE FROM player WHERE username = ?",this.player.getUsername(),"");
        } catch (SQLException e){
            return false;
        }
        return true;
    }
}
