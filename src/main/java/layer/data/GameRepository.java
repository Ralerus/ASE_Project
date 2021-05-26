package layer.data;


import java.sql.*;
import java.time.Instant;
import java.util.Map;

public class GameRepository {
	public static void writeCompetitionToStats(String textTitle, Map<String, Double> results, Instant date)
			throws SQLException{
		String sql = "INSERT INTO game (date, textTitle) VALUES (?,?)";
		Connection conn = Database.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,date.toString());
		pstmt.setString(2,textTitle);
		pstmt.executeUpdate();
		pstmt.close();
		String sql2 = "SELECT MAX(id) AS id FROM game";
		String sql3 = "INSERT INTO result (gameId, username, duration) VALUES (?,?,?)";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql2);
		pstmt = conn.prepareStatement(sql3);
		int gameId = 0;
		while(rs.next()){
			gameId = rs.getInt("id");
		}
		for(String username : results.keySet()){
			pstmt.setInt(1,gameId);
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
}
