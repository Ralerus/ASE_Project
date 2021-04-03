package layer.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
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

    public static void main(String[] args) {
        String url = "jdbc:sqlite:C:/Users/Nutzer/sqlite/db/Tippduell.db";
        createNewDatabase(url);
        String createPlayerTable = "CREATE TABLE IF NOT EXISTS player (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	fullname text\n"
                + ");";
        String createTextTable = "CREATE TABLE IF NOT EXISTS text (\n"
                + "	title text PRIMARY KEY,\n"
                + "	text text NOT NULL,\n"
                + "	difficulty integer NOT NULL,\n"
                + " length integer NOT NULL \n"
                + ");";
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            stmt.execute(createPlayerTable);
            stmt.execute(createTextTable);
            System.out.println("Table created");
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }

}
