package layer.data;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class StatsRepository {
    public static GameStats getStats() throws SQLException {
        int numberOfCompetitions = 0;
        int numberOfTrainings = 0;
        int numberOfPlayers = 0;
        String sql1="SELECT COUNT(*) FROM game";
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
        return new GameStats(numberOfCompetitions,numberOfTrainings, numberOfPlayers, getHighscoreList());
    }
    public static PlayerStats getStatsFor(String username) throws SQLException {
        int numberOfCompetitions = 0;
        int numberOfTrainings = 0;
        List<HistoryEntry> highscore = new LinkedList<>();
        String sql1="SELECT COUNT(*) FROM result WHERE username = ?";
        String sql2="SELECT COUNT(*) FROM training WHERE username = ?";
        String getHighscore="SELECT (t.length/r.duration) AS value, t.title, g.date FROM result AS r,game AS g,text AS t  \n"
                +"WHERE r.gameId = g.id AND g.textTitle = t.title AND r.username = ? ORDER BY value DESC LIMIT 5";
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
        pstmt = conn.prepareStatement(getHighscore);
        pstmt.setString(1,username);
        rs = pstmt.executeQuery();
        while(rs.next()) {
            double roundedValue = Math.round(rs.getDouble("value")*100.0)/100.0;
            Instant dateInstant = Instant.parse(rs.getString("date"));
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.GERMANY).withZone(ZoneId.systemDefault());
            String formattedDate = formatter.format(dateInstant);
            HistoryEntry historyEntry = new HistoryEntry(username,roundedValue,
                    rs.getString("title"),formattedDate);
            highscore.add(historyEntry);
        }
        conn.close();
        return new PlayerStats(numberOfCompetitions,numberOfTrainings, getHistoryListFor(username), highscore);
    }
    private static List<HistoryEntry> getHighscoreList() throws SQLException {
        List<HistoryEntry> highscore = new LinkedList<>();
        String sql =  "SELECT r.username, (t.length/r.duration) AS value, t.title, g.date FROM result AS r,game AS g,text AS t  \n"
                     +"WHERE r.gameId = g.id AND g.textTitle = t.title ORDER BY value DESC LIMIT 10";
        Connection conn = Database.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            double roundedValue = Math.round(rs.getDouble("value")*100.0)/100.0;
            Instant dateInstant = Instant.parse(rs.getString("date"));
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.GERMANY).withZone(ZoneId.systemDefault());
            String formattedDate = formatter.format(dateInstant);
            HistoryEntry historyEntry = new HistoryEntry(rs.getString("username"),roundedValue,
                    rs.getString("title"),formattedDate);
            highscore.add(historyEntry);
        }
        conn.close();
        return highscore;
    }
    private static List<HistoryEntry> getHistoryListFor(String username) throws SQLException {
        List<HistoryEntry> history = new LinkedList<>();
        String sql =  "SELECT (t.length/r.duration) AS value, t.title, g.date FROM result AS r,game AS g,text AS t  \n"
                +"WHERE r.gameId = g.id AND g.textTitle = t.title AND r.username = ? ORDER BY g.date DESC LIMIT 5";
        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            double roundedValue = Math.round(rs.getDouble("value")*100.0)/100.0;
            Instant dateInstant = Instant.parse(rs.getString("date"));
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.GERMANY).withZone(ZoneId.systemDefault());
            String formattedDate = formatter.format(dateInstant);
            HistoryEntry historyEntry = new HistoryEntry(username,roundedValue, rs.getString("title"),
                    formattedDate);
            history.add(historyEntry);
        }
        conn.close();
        return history;
    }
}
