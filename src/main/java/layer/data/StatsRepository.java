package layer.data;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class StatsRepository {
    public static void writeCompetitionToStats(String textTitle, Map<String, Double> results, Instant date)
            throws SQLException{
        String sql = "INSERT INTO competition (date, textTitle) VALUES (?,?)";
        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,date.toString());
        pstmt.setString(2,textTitle);
        pstmt.executeUpdate();
        pstmt.close();
        String sql2 = "SELECT MAX(id) AS id FROM competition";
        String sql3 = "INSERT INTO competitionResult (competitionId, username, duration) VALUES (?,?,?)";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql2);
        pstmt = conn.prepareStatement(sql3);
        int competitionId = 0;
        while(rs.next()){
            competitionId = rs.getInt("id");
        }
        for(String username : results.keySet()){
            pstmt.setInt(1,competitionId);
            pstmt.setString(2,username);
            pstmt.setDouble(3,results.get(username));
            pstmt.executeUpdate();
        }
        conn.close();
    }
    public static void writeTrainingToStats(String textTitle, String username, Double duration, Instant date)
            throws SQLException{
        String sql = "INSERT INTO training (username, duration, textTitle, date) VALUES (?,?,?,?)";
        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setDouble(2,duration);
        pstmt.setString(3,textTitle);
        pstmt.setString(4,date.toString());
        pstmt.executeUpdate();
        conn.close();
    }
    public static GameStats getStats() throws SQLException {
        int numberOfCompetitions = 0;
        int numberOfTrainings = 0;
        int numberOfPlayers = 0;
        String sql1="SELECT COUNT(*) FROM competition";
        String sql2="SELECT COUNT(*) FROM training";
        String sql3="SELECT COUNT(*) FROM player";

        Connection conn = Database.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
        while(rs.next()) {
            numberOfCompetitions = rs.getInt("COUNT(*)");
        }
        rs = stmt.executeQuery(sql2);
        while(rs.next()) {
            numberOfTrainings = rs.getInt("COUNT(*)");
        }
        rs = stmt.executeQuery(sql3);
        while(rs.next()) {
            numberOfPlayers = rs.getInt("COUNT(*)");
        }
        conn.close();
        return new GameStats(numberOfCompetitions,numberOfTrainings, numberOfPlayers, getHighscoreListFor(null));
    }
    public static PlayerStats getStatsFor(String username) throws SQLException {
        int numberOfCompetitions = 0;
        int numberOfTrainings = 0;
        String sql1="SELECT COUNT(*) FROM competitionResult WHERE username = ?";
        String sql2="SELECT COUNT(*) FROM training WHERE username = ?";

        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        pstmt.setString(1,username);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            numberOfCompetitions = rs.getInt("COUNT(*)");
        }
        pstmt = conn.prepareStatement(sql2);
        pstmt.setString(1,username);
        rs = pstmt.executeQuery();
        while(rs.next()) {
            numberOfTrainings = rs.getInt("COUNT(*)");
        }
        conn.close();
        return new PlayerStats(numberOfCompetitions,numberOfTrainings, getHistoryListFor(username),
                getHighscoreListFor(username));
    }
    private static List<StatsEntry> getHighscoreListFor(String username) throws SQLException {
        List<StatsEntry> highscore = new LinkedList<>();
        Connection conn = Database.connect();
        ResultSet rs;
        if(username == null){
            String sql =  "SELECT r.username, (t.length/r.duration) AS value, t.title, g.date FROM competitionResult AS r,competition AS g,text AS t  \n"
                    +"WHERE r.competitionId = g.id AND g.textTitle = t.title ORDER BY value DESC LIMIT 10";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        }else{
            String sql = "SELECT r.username, (t.length/r.duration) AS value, t.title, g.date FROM competitionResult AS r,competition AS g,text AS t  \n"
                    +"WHERE r.competitionId = g.id AND g.textTitle = t.title AND r.username = ? ORDER BY value DESC LIMIT 5";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            rs = pstmt.executeQuery();
        }

        while(rs.next()){
            highscore.add(getFormattedStatsEntry(rs.getString("username"),rs.getDouble("value"),
                    rs.getString("title"),rs.getString("date")));
        }
        conn.close();
        return highscore;
    }
    private static List<StatsEntry> getHistoryListFor(String username) throws SQLException {
        List<StatsEntry> history = new LinkedList<>();
        String sql =  "SELECT (t.length/r.duration) AS value, t.title, g.date FROM competitionResult AS r,competition AS g,text AS t  \n"
                +"WHERE r.competitionId = g.id AND g.textTitle = t.title AND r.username = ? ORDER BY g.date DESC LIMIT 5";
        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            history.add(getFormattedStatsEntry(username,rs.getDouble("value"),
                    rs.getString("title"),rs.getString("date")));
        }
        conn.close();
        return history;
    }

    public static StatsEntry getFormattedStatsEntry(String username, double speed, String textTitle, String date) {
        double roundedValue = Math.round(speed*100.0)/100.0;
        Instant dateInstant = Instant.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).
                withLocale(Locale.GERMANY).withZone(ZoneOffset.ofHours(2));
        String formattedDate = formatter.format(dateInstant);
        return new StatsEntry(username,roundedValue, textTitle,formattedDate);
    }
}
