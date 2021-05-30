package layer.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Database {
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
                System.out.println("Connection to database established.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setup() {
        System.out.println("---Start database setup---");
        createNewDatabase(url);
        createTablesIfNotExist();
        insertInitialTextsIfNotExist();
        System.out.println("---End database setup---");
    }

    private static void createTablesIfNotExist(){
        List<String> createTables = new LinkedList<>();
        createTables.add("CREATE TABLE IF NOT EXISTS player (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	fullname text,\n"
                + " firstLogin integer NOT NULL "
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS text (\n"
                + "	title text PRIMARY KEY,\n"
                + "	text text NOT NULL,\n"
                + "	difficulty integer NOT NULL,\n"
                + " length integer NOT NULL \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS competition (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	textTitle text NOT NULL,\n"
                + "	date text NOT NULL,\n"
                + " FOREIGN KEY (textTitle) REFERENCES text(title) ON UPDATE CASCADE ON DELETE SET NULL \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS competitionResult (\n"
                + "	competitionId integer NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	duration real NOT NULL,\n"
                + " FOREIGN KEY (competitionId) REFERENCES competition(id) ON UPDATE CASCADE ON DELETE CASCADE \n"
                + " FOREIGN KEY (username) REFERENCES player(username) ON UPDATE CASCADE ON DELETE CASCADE \n"
                + " PRIMARY KEY (competitionId,username) \n"
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
    private static void insertInitialTextsIfNotExist(){
        try {
            TextRepository.createText("Einstieg", "Dieser Text dient zum Einstieg in Tippduell", Difficulty.Easy);
            TextRepository.createText("Geübt", "Wenn du (hoffentlich) schon länger Tipperfahrung sammeln konntest, ist dieser kurze Text\n mit mehr Zeichen und vor allem ein paar Sonderzeichen etwas für dich! Oder etwa nicht?!", Difficulty.Medium);
            TextRepository.createText("Schwierig", "Viel Spaß mit diesem äußerst schwierigen Kurztext - naja so\n richtig komplex ist auch dieser Übungstext nicht.\n Weist auch nicht über 120 Zeichen auf " +
                    "und enthält bis auf ein paar Sonderzeichen wie % oder $ auch nichts Unüberwindbares.\n Wenn dir schwierigere Textvorlagen einfallen, ergänze sie gerne in der Textverwaltung.\n\n Adiós!", Difficulty.Hard);
            System.out.println("Initial texts set");
        } catch (SQLException throwables) {
            System.err.println("Fehler beim Anlegen der initialen Texte: "+throwables.getMessage());
        } catch (ObjectAlreadyExistsException ignored) {

        }

    }
}
