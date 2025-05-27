package eventflow;

import database.DbConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class EventFlow {
    public static void main(String[] args) throws SQLException {
        Connection conn = DbConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Database connected successfully!");
        } else {
            System.out.println("❌ Failed to connect to the database.");
        }
    }
}
