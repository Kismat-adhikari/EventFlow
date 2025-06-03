package eventflow.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DbConnection;
import eventflow.views.LoginForm;
import eventflow.views.SignupForm;

public class SignupController {

    private SignupForm view;
    

    public SignupController(SignupForm view) {
        this.view = view;
    }

    // Called when user clicks the label to go to Login
    public void handleSignupToLoginClick() {
        new LoginForm().setVisible(true);
        view.dispose();
    }

    // Called when user clicks the Sign Up button
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

        // Insert new user with balance = 1000
        String sql = "INSERT INTO users (fullname, email, password, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setDouble(4, 1000.0);  // initial balance
            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0 ? "success" : "Signup failed. Try again.";
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return "Signup failed due to a database error.";
    }
}

}
