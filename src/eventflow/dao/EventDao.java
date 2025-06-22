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

    // Insert a new event into the database
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO events (eventTitle, eventDesc, eventTickets, eventPrice, eventDate, eventTime, eventLocation, user_id) "
                +
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
    } // Retrieve all events with uploader's fullname

    public List<EventWithUser> getAllEventsWithUploader() {
        List<EventWithUser> list = new ArrayList<>();
        String sql = "SELECT e.id, e.eventTitle, e.eventDesc, e.eventTickets, e.eventPrice, " +
                "e.eventDate, e.eventTime, e.eventLocation, e.user_id, u.fullname AS uploaderFullname " +
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
                e.setCreatorUserId(rs.getInt("user_id"));
                e.setUploaderFullname(rs.getString("uploaderFullname"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    } // Retrieve events uploaded by a specific user

    public List<EventWithUser> getEventsByUploader(int userId) {
        List<EventWithUser> list = new ArrayList<>();
        String sql = "SELECT e.id, e.eventTitle, e.eventDesc, e.eventTickets, e.eventPrice, " +
                "e.eventDate, e.eventTime, e.eventLocation, e.user_id, u.fullname AS uploaderFullname " +
                "FROM events e JOIN users u ON e.user_id = u.id WHERE u.id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
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
                    e.setCreatorUserId(rs.getInt("user_id"));
                    e.setUploaderFullname(rs.getString("uploaderFullname"));
                    list.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get the creator user ID of an event by event ID (needed for payment)
    public int getCreatorIdByEventId(int eventId) {
        String sql = "SELECT user_id FROM events WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // not found or error
    } // Delete an event from the database with automatic refunds

    public boolean deleteEvent(int eventId) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Get all tickets for this event to calculate refunds
            String getTicketsSql = "SELECT user_id, price_paid FROM tickets WHERE event_id = ?";
            PreparedStatement getTicketsStmt = conn.prepareStatement(getTicketsSql);
            getTicketsStmt.setInt(1, eventId);
            ResultSet ticketsRS = getTicketsStmt.executeQuery();

            // Step 2: Process refunds for each ticket holder
            eventflow.dao.UserDAO userDao = new eventflow.dao.UserDAO();
            int refundCount = 0;
            double totalRefunded = 0.0;

            while (ticketsRS.next()) {
                int userId = ticketsRS.getInt("user_id");
                double pricePaid = ticketsRS.getDouble("price_paid");

                // Refund the user
                boolean refundSuccess = userDao.updateUserBalance(userId, pricePaid, conn);
                if (!refundSuccess) {
                    conn.rollback();
                    return false;
                }
                refundCount++;
                totalRefunded += pricePaid;
            }
            ticketsRS.close();
            getTicketsStmt.close();

            // Step 3: Delete all tickets for this event
            String deleteTicketsSql = "DELETE FROM tickets WHERE event_id = ?";
            PreparedStatement deleteTicketsStmt = conn.prepareStatement(deleteTicketsSql);
            deleteTicketsStmt.setInt(1, eventId);
            deleteTicketsStmt.executeUpdate();
            deleteTicketsStmt.close();

            // Step 4: Delete the event itself
            String deleteEventSql = "DELETE FROM events WHERE id = ?";
            PreparedStatement deleteEventStmt = conn.prepareStatement(deleteEventSql);
            deleteEventStmt.setInt(1, eventId);
            int rowsAffected = deleteEventStmt.executeUpdate();
            deleteEventStmt.close();

            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Event " + eventId + " deleted successfully. Refunded " + refundCount +
                        " users with total amount: Rs " + totalRefunded);
                return true;
            } else {
                conn.rollback();
                return false;
            }

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

    // Get refund information for an event before deletion (for confirmation dialog)
    public RefundInfo getRefundInfo(int eventId) {
        String sql = "SELECT COUNT(*) as ticket_count, SUM(price_paid) as total_refund FROM tickets WHERE event_id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int ticketCount = rs.getInt("ticket_count");
                    double totalRefund = rs.getDouble("total_refund");
                    return new RefundInfo(ticketCount, totalRefund);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new RefundInfo(0, 0.0);
    }

    // Get ticket availability information for an event
    public TicketAvailability getTicketAvailability(int eventId) {
        String sql = "SELECT e.eventTickets, COUNT(t.id) as sold FROM events e " +
                "LEFT JOIN tickets t ON e.id = t.event_id " +
                "WHERE e.id = ? GROUP BY e.id, e.eventTickets";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int totalTickets = rs.getInt("eventTickets");
                    int soldTickets = rs.getInt("sold");
                    int availableTickets = totalTickets - soldTickets;
                    boolean isSoldOut = availableTickets <= 0;

                    return new TicketAvailability(totalTickets, soldTickets, availableTickets, isSoldOut);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TicketAvailability(0, 0, 0, true); // Default to sold out if error
    }

    // Helper class for ticket availability information
    public static class TicketAvailability {
        private final int totalTickets;
        private final int soldTickets;
        private final int availableTickets;
        private final boolean isSoldOut;

        public TicketAvailability(int totalTickets, int soldTickets, int availableTickets, boolean isSoldOut) {
            this.totalTickets = totalTickets;
            this.soldTickets = soldTickets;
            this.availableTickets = availableTickets;
            this.isSoldOut = isSoldOut;
        }

        public int getTotalTickets() {
            return totalTickets;
        }

        public int getSoldTickets() {
            return soldTickets;
        }

        public int getAvailableTickets() {
            return availableTickets;
        }

        public boolean isSoldOut() {
            return isSoldOut;
        }

        public String getAvailabilityText() {
            if (isSoldOut) {
                return "SOLD OUT";
            } else {
                return availableTickets + "/" + totalTickets + " tickets available";
            }
        }
    }

    // Helper class for refund information
    public static class RefundInfo {
        private final int ticketCount;
        private final double totalRefund;

        public RefundInfo(int ticketCount, double totalRefund) {
            this.ticketCount = ticketCount;
            this.totalRefund = totalRefund;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public double getTotalRefund() {
            return totalRefund;
        }
    }

    // Inner class to represent event + uploader info together
    public static class EventWithUser {
        private int id;
        private String eventTitle;
        private String eventDesc;
        private int eventTickets;
        private double eventPrice;
        private String eventDate;
        private String eventTime;
        private String eventLocation;
        private int creatorUserId;
        private String uploaderFullname;

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public String getEventDesc() {
            return eventDesc;
        }

        public void setEventDesc(String eventDesc) {
            this.eventDesc = eventDesc;
        }

        public int getEventTickets() {
            return eventTickets;
        }

        public void setEventTickets(int eventTickets) {
            this.eventTickets = eventTickets;
        }

        public double getEventPrice() {
            return eventPrice;
        }

        public void setEventPrice(double eventPrice) {
            this.eventPrice = eventPrice;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getEventLocation() {
            return eventLocation;
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation = eventLocation;
        }

        public int getCreatorUserId() {
            return creatorUserId;
        }

        public void setCreatorUserId(int creatorUserId) {
            this.creatorUserId = creatorUserId;
        }

        public String getUploaderFullname() {
            return uploaderFullname;
        }

        public void setUploaderFullname(String uploaderFullname) {
            this.uploaderFullname = uploaderFullname;
        }
    }
}
