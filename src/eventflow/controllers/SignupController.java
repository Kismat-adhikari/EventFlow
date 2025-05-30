package eventflow.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DbConnection;

public class SignupController {

    public String handleSignup(String fullName, String email, String password) {
        try (Connection conn = DbConnection.getConnection()) {

            // Check if email already exists
            String checkSql = "SELECT id FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return "This email is already registered.";
                }
            }

            // Insert if email not found
            String sql = "INSERT INTO users (fullname, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fullName);
                stmt.setString(2, email);
                stmt.setString(3, password);

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0 ? "success" : "Signup failed. Try again.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Signup failed due to a database error.";
        }
    }
}
