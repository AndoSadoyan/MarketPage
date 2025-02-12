package am.personal.acc_management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnectionJDBC {
    private static DBconnectionJDBC instance;
    private final Connection conn;

    private DBconnectionJDBC()
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

    public static DBconnectionJDBC getDB()
    {
        try {
            if(instance == null || instance.conn.isClosed())
                instance = new DBconnectionJDBC();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
