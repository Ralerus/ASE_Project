package layer.data;

import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class TextRepository {
    public static void createText(String title, String text, Difficulty difficulty) throws SQLException, ObjectAlreadyExistsException {
        String sql = "INSERT INTO text(title, text, difficulty, length) VALUES (?,?,?,?)";
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,title);
            pstmt.setString(2,text);
            pstmt.setInt(3,difficulty.ordinal());
            pstmt.setInt(4,text.length());
            pstmt.executeUpdate();
        }catch(SQLiteException ex){
            if(ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed" +
                    " (UNIQUE constraint failed: text.title)")){
                throw new ObjectAlreadyExistsException("Titel bereits vergeben.");
            }else{
                throw ex;
            }
        }
    }

    public static Text getRandomTextBasedOn(Rules rules) throws ObjectNotFoundException{
        String sql = "SELECT title, text, difficulty FROM text WHERE difficulty = ? AND length >= ? AND length <= ?";
        String text = null;
        String title;
        Difficulty difficulty = null;
        List<Text> matchingTexts = new LinkedList<>();
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
                matchingTexts.add(new Text(title,text,text.length()));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            throw new ObjectNotFoundException("Kein Text für diese Regeln gefunden!");
        }
        if(text==null || difficulty==null ){
            throw new ObjectNotFoundException("Kein Text für diese Regeln gefunden!");
        }

        Random r = new Random();
        int indexOfRandomText = r.nextInt(matchingTexts.size());
        return matchingTexts.get(indexOfRandomText);
    }

    public static Text getTextByTitle(String title) throws ObjectNotFoundException {
        String sql = "SELECT * FROM text WHERE title= ?";
        String text = null;
        Difficulty difficulty = null;
        try(Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                text = rs.getString("text");
                int difficultyInt = rs.getInt("difficulty");
                switch (difficultyInt) {
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
            throw new ObjectNotFoundException("Kein Text mit diesem Titel gefunden!");
        }
        if(text==null || difficulty == null){
            throw new ObjectNotFoundException("Kein Text mit diesem Titel gefunden!");
        }
        return new Text(title,text,text.length());
    }

    public static void deleteText(Text text) throws SQLException {
        Database.updateEntry("DELETE FROM text WHERE title = ?", text.getTitle(),"");
    }

}
