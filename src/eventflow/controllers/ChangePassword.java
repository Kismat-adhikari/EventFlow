package eventflow.dao;

import database.DbConnection;
import eventflow.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Get user's balance by user ID
    public double getBalanceById(int userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Default if user not found
    }

    // Get user by email and password (login)
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get user by ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create new user (signup)
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (fullname, email, password, balance, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDouble(4, user.getBalance());
            stmt.setInt(5, user.getIsAdmin());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDouble("balance"),
                rs.getInt("is_admin"));
    }

    /**
     * Update user balance by adding amount (positive or negative) inside an
     * existing connection.
     * Used within transaction.
     */
    public boolean updateUserBalance(int userId, double amount, Connection conn) throws SQLException {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    /**
     * Process payment transaction: deduct amount from buyer, add to creator
     * atomically.
     * Returns true if successful, false if failure or insufficient balance.
     */
    public boolean processPayment(int buyerId, int creatorId, double amount) {
        String checkBalanceSql = "SELECT balance FROM users WHERE id = ?";
        Connection conn = null;
        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Check buyer's balance
            double buyerBalance = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql)) {
                checkStmt.setInt(1, buyerId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        buyerBalance = rs.getDouble("balance");
                    } else {
                        conn.rollback();
                        return false; // Buyer not found
                    }
                }
            }

            if (buyerBalance < amount) {
                conn.rollback();
                return false; // Insufficient funds
            }

            // Deduct from buyer
            boolean deducted = updateUserBalance(buyerId, -amount, conn);
            if (!deducted) {
                conn.rollback();
                return false;
            }

            // Add to creator
            boolean credited = updateUserBalance(creatorId, amount, conn);
            if (!credited) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true; // Success

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete user account by user ID
     * This will also delete related data due to foreign key constraints
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update user password by user ID
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
