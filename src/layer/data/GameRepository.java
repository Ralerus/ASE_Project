package layer.data;


import javax.xml.transform.Result;
import java.sql.*;
import java.time.Instant;
import java.util.Map;

public class GameRepository {
	//TODO: Besserer Stil Try-Catch nur auf oberster Ebene?
	public static boolean writeGameToStats(String textTitle, Map<String, Double> results, Instant date) throws SQLException{ //TODO kein Game übergeben, damit keine Abhängigkeiten nach Außen?
		String sql = "INSERT INTO game (date, textTitle) VALUES (?,?)";
		Connection conn = Database.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,date.toString());
		pstmt.setString(2,textTitle);
		pstmt.executeUpdate();
		pstmt.close();
		String sql2 = "SELECT MAX(id) AS id FROM game"; //TODO geht bestimmt besser, stored procedure?
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
		return true;
	}
}
