package eventflow.dao;

import database.DbConnection;
import eventflow.models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    private Connection getConnection() throws SQLException {
        return DbConnection.getConnection();
    }

    public boolean createEvent(Event event) {
        String sql = "INSERT INTO events (eventTitle, eventDesc, eventTickets, eventPrice, eventDate, eventTime, eventLocation, user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getEventTitle());
            stmt.setString(2, event.getEventDesc());
            stmt.setInt(3, event.getEventTickets());
            stmt.setDouble(4, event.getEventPrice());
            stmt.setString(5, event.getEventDate());
            stmt.setString(6, event.getEventTime());
            stmt.setString(7, event.getEventLocation());
            stmt.setInt(8, event.getUserId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EventWithUser> getAllEventsWithUploader() {
        List<EventWithUser> list = new ArrayList<>();
        String sql = "SELECT e.id, e.eventTitle, e.eventDesc, e.eventTickets, e.eventPrice, " +
                     "e.eventDate, e.eventTime, e.eventLocation, u.fullname AS uploaderFullname " +
                     "FROM events e JOIN users u ON e.user_id = u.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EventWithUser e = new EventWithUser();
                e.setId(rs.getInt("id"));
                e.setEventTitle(rs.getString("eventTitle"));
                e.setEventDesc(rs.getString("eventDesc"));
                e.setEventTickets(rs.getInt("eventTickets"));
                e.setEventPrice(rs.getDouble("eventPrice"));
                e.setEventDate(rs.getString("eventDate"));
                e.setEventTime(rs.getString("eventTime"));
                e.setEventLocation(rs.getString("eventLocation"));
                e.setUploaderFullname(rs.getString("uploaderFullname"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // New method to fetch events by specific user/uploader id
    public List<EventWithUser> getEventsByUploader(int userId) {
        List<EventWithUser> list = new ArrayList<>();
        String sql = "SELECT e.id, e.eventTitle, e.eventDesc, e.eventTickets, e.eventPrice, " +
                     "e.eventDate, e.eventTime, e.eventLocation, u.fullname AS uploaderFullname " +
                     "FROM events e JOIN users u ON e.user_id = u.id WHERE u.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EventWithUser e = new EventWithUser();
                e.setId(rs.getInt("id"));
                e.setEventTitle(rs.getString("eventTitle"));
                e.setEventDesc(rs.getString("eventDesc"));
                e.setEventTickets(rs.getInt("eventTickets"));
                e.setEventPrice(rs.getDouble("eventPrice"));
                e.setEventDate(rs.getString("eventDate"));
                e.setEventTime(rs.getString("eventTime"));
                e.setEventLocation(rs.getString("eventLocation"));
                e.setUploaderFullname(rs.getString("uploaderFullname"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Inner class to hold event details with uploader info
    public static class EventWithUser {
        private int id;
        private String eventTitle;
        private String eventDesc;
        private int eventTickets;
        private double eventPrice;
        private String eventDate;
        private String eventTime;
        private String eventLocation;
        private String uploaderFullname;

        // Getters and setters

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getEventTitle() { return eventTitle; }
        public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

        public String getEventDesc() { return eventDesc; }
        public void setEventDesc(String eventDesc) { this.eventDesc = eventDesc; }

        public int getEventTickets() { return eventTickets; }
        public void setEventTickets(int eventTickets) { this.eventTickets = eventTickets; }

        public double getEventPrice() { return eventPrice; }
        public void setEventPrice(double eventPrice) { this.eventPrice = eventPrice; }

        public String getEventDate() { return eventDate; }
        public void setEventDate(String eventDate) { this.eventDate = eventDate; }

        public String getEventTime() { return eventTime; }
        public void setEventTime(String eventTime) { this.eventTime = eventTime; }

        public String getEventLocation() { return eventLocation; }
        public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }

        public String getUploaderFullname() { return uploaderFullname; }
        public void setUploaderFullname(String uploaderFullname) { this.uploaderFullname = uploaderFullname; }
    }
}
