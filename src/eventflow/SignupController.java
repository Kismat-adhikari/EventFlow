package eventflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DbConnection;

public class SignupController {

    public boolean handleSignup(String fullName, String email, String password) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // shows error in console — useful!
            return false;
        }

    }
}
