package am.personal.acc_management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static DBconnection instance;
    private final Connection conn;

    private DBconnection()
    {
        final String url = "jdbc:postgresql://localhost:5432/registerpage";
        final String user = "postgres";
        final String password = "Ando1337";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBconnection getDB()
    {
        try {
            if(instance == null || instance.conn.isClosed())
                instance = new DBconnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
