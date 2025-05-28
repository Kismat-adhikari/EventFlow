package eventflow.services;

import database.DbConnection;
import eventflow.models.User;
import java.sql.*;

public class AuthService {
    public User login(String email, String password) {
        Connection conn = DbConnection.getConnection();
        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDouble("balance")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
