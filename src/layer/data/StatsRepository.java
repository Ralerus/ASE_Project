package layer.data;

import java.sql.*;

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
        return new GameStats(numberOfCompetitions,numberOfTrainings, numberOfPlayers);
    }
    public static PlayerStats getStatsFor(String username) throws SQLException {
        int numberOfCompetitions = 0;
        int numberOfTrainings = 0;
        String sql1="SELECT COUNT(*) FROM result WHERE username = ?";
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
        return new PlayerStats(numberOfCompetitions,numberOfTrainings);
    }
}
