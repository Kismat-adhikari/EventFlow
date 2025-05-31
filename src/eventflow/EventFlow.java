package eventflow;

import database.DbConnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class EventFlow {
    public static void main(String[] args) {
        // First check DB connection (optional)
        try {
            Connection conn = DbConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
            } else {
                System.out.println("❌ Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Then open Signup window on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupForm().setVisible(true);  // Replace Signup with your Signup JFrame class name
            }
        });
    }
}
