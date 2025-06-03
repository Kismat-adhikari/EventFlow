package eventflow.services;

import database.DbConnection;
import eventflow.models.User;
import java.sql.*;

public class AuthService {
    public User login(String email, String password) {
    try {
        Connection conn = DbConnection.getConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
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
            rs.getInt("isAdmin") // ✅ make sure this is passed
        );

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
