package eventflow.services;

import database.DbConnection;
import eventflow.models.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileService {

    public static Profile getProfileByUserId(int userId) {
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
                return profile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateBio(int userId, String newBio) {
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "UPDATE profiles SET bio = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newBio);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateLocation(int userId, String newLocation) {
        try (Connection conn = DbConnection.getConnection()) {
            String sql = "UPDATE profiles SET location = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newLocation);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}