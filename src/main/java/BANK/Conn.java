package BANK;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName(DBInfo.DRIVER); // Load the Oracle JDBC driver
                conn = DriverManager.getConnection(DBInfo.DB_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}






