package eventflow.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    private final Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

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

        // Getters and setters for all fields

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

    public List<EventWithUser> getAllEventsWithUploader() throws SQLException {
        List<EventWithUser> list = new ArrayList<>();

        String sql = "SELECT e.id, e.eventTitle, e.eventDesc, e.eventTickets, e.eventPrice, e.eventDate, e.eventTime, e.eventLocation, u.fullname " +
                     "FROM events e " +
                     "JOIN users u ON e.user_id = u.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
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
                e.setUploaderFullname(rs.getString("fullname"));
                list.add(e);
            }
        }
        return list;
    }
}
