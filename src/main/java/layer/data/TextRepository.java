package layer.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TextRepository {
    public static boolean createText(String title, String text, Difficulty difficulty){
        String sql = "INSERT INTO text(title, text, difficulty, length) VALUES (?,?,?,?)";
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,title);
            pstmt.setString(2,text);
            pstmt.setInt(3,difficulty.ordinal());
            pstmt.setInt(4,text.length());
            pstmt.executeUpdate();
        } catch (SQLException e){
            return false;
        }
        return true;
    }

    public static Text getRandomTextBasedOn(Rules rules) throws TextNotFoundException{
        String sql = "SELECT title, text, difficulty FROM text WHERE difficulty = ? AND length >= ? AND length <= ?";
        String text = null;
        String title = null;
        Difficulty difficulty = null;

        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,rules.getDifficulty().ordinal());
            pstmt.setInt(2,rules.getMinLengthOfText());
            pstmt.setInt(3,rules.getMaxLengthOfText());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                text = rs.getString("text");
                title = rs.getString("title");
                int difficultyInt = rs.getInt("difficulty");
                switch(difficultyInt){
                    case 0:
                        difficulty = Difficulty.Easy;
                        break;
                    case 1:
                        difficulty = Difficulty.Medium;
                        break;
                    case 2:
                        difficulty = Difficulty.Hard;
                        break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            throw new TextNotFoundException("Kein Text für diese Regeln gefunden!");
        }
        if(text==null || difficulty==null ){
            throw new TextNotFoundException("Kein Text für diese Regeln gefunden!");
        }

        return new Text(title,text,difficulty,text.length()); //TODO für was wird difficulty im frontend gebraucht?
    }

    public static class TextNotFoundException extends Exception {
        public TextNotFoundException(){
            super();
        }
        public TextNotFoundException(String s){
            super(s);
        }
    }
}
