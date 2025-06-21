package eventflow.dao;

import database.DbConnection;
import eventflow.models.Ticket;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {

    private Connection getConnection() throws SQLException {
        return DbConnection.getConnection();
    }

    // Create a new ticket
    public boolean createTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (user_id, event_id, purchase_date, price_paid) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getUserId());
            stmt.setInt(2, ticket.getEventId());
            stmt.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseDate()));
            stmt.setDouble(4, ticket.getPricePaid());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all tickets for a specific user
    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ? ORDER BY purchase_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = mapResultSetToTicket(rs);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Get all tickets for a specific event
    public List<Ticket> getTicketsByEventId(int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE event_id = ? ORDER BY purchase_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = mapResultSetToTicket(rs);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Get ticket by ID
    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTicket(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to map ResultSet to Ticket object
    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        return new Ticket(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("event_id"),
            rs.getTimestamp("purchase_date").toLocalDateTime(),
            rs.getDouble("price_paid")
        );
    }

    // Get tickets with event details for a user (for displaying in UI)
    public List<TicketWithEventDetails> getTicketsWithEventDetailsByUserId(int userId) {
        List<TicketWithEventDetails> tickets = new ArrayList<>();
        String sql = "SELECT t.*, e.eventTitle, e.eventDesc, e.eventDate, e.eventTime, e.eventLocation " +
                     "FROM tickets t JOIN events e ON t.event_id = e.id " +
                     "WHERE t.user_id = ? ORDER BY t.purchase_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TicketWithEventDetails ticket = new TicketWithEventDetails();
                    ticket.setTicketId(rs.getInt("id"));
                    ticket.setUserId(rs.getInt("user_id"));
                    ticket.setEventId(rs.getInt("event_id"));
                    ticket.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());
                    ticket.setPricePaid(rs.getDouble("price_paid"));
                    ticket.setEventTitle(rs.getString("eventTitle"));
                    ticket.setEventDesc(rs.getString("eventDesc"));
                    ticket.setEventDate(rs.getString("eventDate"));
                    ticket.setEventTime(rs.getString("eventTime"));
                    ticket.setEventLocation(rs.getString("eventLocation"));
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Inner class for ticket with event details
    public static class TicketWithEventDetails {
        private int ticketId;
        private int userId;
        private int eventId;
        private LocalDateTime purchaseDate;
        private double pricePaid;
        private String eventTitle;
        private String eventDesc;
        private String eventDate;
        private String eventTime;
        private String eventLocation;

        // Getters and setters
        public int getTicketId() { return ticketId; }
        public void setTicketId(int ticketId) { this.ticketId = ticketId; }

        public int getUserId() { return userId; }
        public void setUserId(int userId) { this.userId = userId; }

        public int getEventId() { return eventId; }
        public void setEventId(int eventId) { this.eventId = eventId; }

        public LocalDateTime getPurchaseDate() { return purchaseDate; }
        public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

        public double getPricePaid() { return pricePaid; }
        public void setPricePaid(double pricePaid) { this.pricePaid = pricePaid; }

        public String getEventTitle() { return eventTitle; }
        public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

        public String getEventDesc() { return eventDesc; }
        public void setEventDesc(String eventDesc) { this.eventDesc = eventDesc; }

        public String getEventDate() { return eventDate; }
        public void setEventDate(String eventDate) { this.eventDate = eventDate; }

        public String getEventTime() { return eventTime; }
        public void setEventTime(String eventTime) { this.eventTime = eventTime; }

        public String getEventLocation() { return eventLocation; }
        public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    }
}
