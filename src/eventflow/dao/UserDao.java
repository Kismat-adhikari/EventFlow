package eventflow.dao;

import database.DbConnection;
import eventflow.models.User;
import java.sql.*;

public class UserDAO {

    // Login: get user by email and password
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDouble("balance"),
                    rs.getInt("is_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Signup: insert new user
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (fullname, email, password, balance, is_admin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDouble(4, user.getBalance()); // usually 0 for new users
            stmt.setInt(5, user.getIsAdmin());    // usually 0 for normal users

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
