package layer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    public static Connection connect(){
        String url = "jdbc:sqlite:C:/Users/Nutzer/sqlite/db/Tippduell.db"; //TODO change path
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public static void updateEntry(String sql, String value1, String value2) throws SQLException{
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,value1);
            if(value2!=""){
                pstmt.setString(2, value2);
            }
            pstmt.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }
}
