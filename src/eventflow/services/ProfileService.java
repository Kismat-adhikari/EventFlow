package eventflow.services;

import database.DbConnection;
import eventflow.models.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileService {    public static Profile getProfileByUserId(int userId) {
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "SELECT * FROM profiles WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String bio = rs.getString("bio");
                String location = rs.getString("location");  // new field
                Profile profile = new Profile();
                profile.setUserId(userId);
                profile.setBio(bio);
                profile.setLocation(location);
                System.out.println("Profile loaded for user " + userId + ": bio='" + bio + "', location='" + location + "'");
                return profile;
            } else {
                System.out.println("No profile found for user " + userId);
            }
        } catch (Exception e) {
            System.err.println("Error loading profile for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }public static void updateBio(int userId, String newBio) {
        try (Connection conn = DbConnection.getConnection()) {
            // Use INSERT ... ON DUPLICATE KEY UPDATE to handle both insert and update
            String sql = "INSERT INTO profiles (user_id, bio, location) VALUES (?, ?, '') " +
                        "ON DUPLICATE KEY UPDATE bio = VALUES(bio)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, newBio);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateLocation(int userId, String newLocation) {
        try (Connection conn = DbConnection.getConnection()) {
            // Use INSERT ... ON DUPLICATE KEY UPDATE to handle both insert and update
            String sql = "INSERT INTO profiles (user_id, bio, location) VALUES (?, '', ?) " +
                        "ON DUPLICATE KEY UPDATE location = VALUES(location)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, newLocation);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void updateProfile(int userId, String newBio, String newLocation) {
        try (Connection conn = DbConnection.getConnection()) {
            // Use INSERT ... ON DUPLICATE KEY UPDATE to handle both insert and update
            String sql = "INSERT INTO profiles (user_id, bio, location) VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE bio = VALUES(bio), location = VALUES(location)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, newBio != null ? newBio : "");
            stmt.setString(3, newLocation != null ? newLocation : "");
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Profile updated for user " + userId + ". Rows affected: " + rowsAffected);
        } catch (Exception e) {
            System.err.println("Error updating profile for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}