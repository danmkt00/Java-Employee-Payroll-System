import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseConnection {
    private final String filePath;
    private Connection conn;

    public DatabaseConnection(String filePath) {
        this.filePath = filePath;
        this.conn = null;
    }

    public Connection getConnection() {
        if (conn == null) {
            Properties props = new Properties();

            try (FileInputStream fin = new FileInputStream(filePath)) {
                props.load(fin);
            } catch (IOException e) {
                System.out.println("Error loading database configuration: " + e.getMessage());
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            try {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established successfully.");
            } catch (SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());
            }
        }

        return conn;

    }
}
