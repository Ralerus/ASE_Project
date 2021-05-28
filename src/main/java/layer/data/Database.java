package layer.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Database {
    private static final String url = "jdbc:sqlite:src/main/resources/tippduell.db";//"jdbc:sqlite:resources/tippduell.db";
    public static Connection connect(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public static void updateEntry(String sql, String value1, String value2) throws SQLException{
        Connection conn = Database.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,value1);
        if(!value2.equals("")){
            pstmt.setString(2, value2);
        }
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void createNewDatabase(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setup() {
        createNewDatabase(url);
        List<String> createTables = new LinkedList<>();
        createTables.add("CREATE TABLE IF NOT EXISTS player (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	fullname text\n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS text (\n"
                + "	title text PRIMARY KEY,\n"
                + "	text text NOT NULL,\n"
                + "	difficulty integer NOT NULL,\n"
                + " length integer NOT NULL \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS game (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	textTitle text NOT NULL,\n"
                + "	date text NOT NULL,\n"
                + " FOREIGN KEY (textTitle) REFERENCES text(title) ON UPDATE CASCADE ON DELETE SET NULL \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS result (\n"
                + "	gameId integer NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	duration real NOT NULL,\n"
                + " FOREIGN KEY (gameId) REFERENCES game(id) ON UPDATE CASCADE ON DELETE CASCADE \n"
                + " FOREIGN KEY (username) REFERENCES player(username) ON UPDATE CASCADE ON DELETE CASCADE \n"
                + " PRIMARY KEY (gameId,username) \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS training (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	username text NOT NULL,\n"
                + "	duration real NOT NULL,\n"
                + "	textTitle text NOT NULL,\n"
                + "	date text NOT NULL,\n"
                + " FOREIGN KEY (textTitle) REFERENCES text(title) ON UPDATE CASCADE ON DELETE SET NULL \n"
                + " FOREIGN KEY (username) REFERENCES player(username) ON UPDATE CASCADE ON DELETE CASCADE \n"
                + ");");
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            for(String sql : createTables){
                stmt.execute(sql);
            }
            System.out.println("Tables created");
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }
}
