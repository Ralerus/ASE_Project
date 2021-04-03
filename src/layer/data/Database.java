package layer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    public static Connection connect(){
        String url = "jdbc:sqlite:C:/Users/Nutzer/sqlite/db/Tippduell.db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public static void updateEntry(String sql, String identifier, String newValue) throws SQLException{
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,newValue);
            pstmt.setString(2, identifier);
            pstmt.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }
}
