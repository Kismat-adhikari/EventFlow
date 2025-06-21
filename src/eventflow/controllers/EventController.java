package eventflow.controllers;

import eventflow.dao.TicketDao;
import eventflow.dao.TicketDao.TicketWithEventDetails;
import eventflow.models.Ticket;
import java.util.List;

public class EventController {
    
    private final TicketDao ticketDao;
    
    public EventController() {
        this.ticketDao = new TicketDao();
    }
    
    /**
     * Create a ticket when a user purchases an event
     */
    public boolean purchaseTicket(int userId, int eventId, double pricePaid) {
        try {
            Ticket ticket = new Ticket(userId, eventId, pricePaid);
            boolean success = ticketDao.createTicket(ticket);
            
            if (success) {
                System.out.println("Ticket created successfully for user " + userId + " for event " + eventId);
            } else {
                System.out.println("Failed to create ticket");
            }
            
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all tickets for a specific user
     */
    public List<Ticket> getUserTickets(int userId) {
        return ticketDao.getTicketsByUserId(userId);
    }
    
    /**
     * Get all tickets with event details for a specific user (for UI display)
     */
    public List<TicketWithEventDetails> getUserTicketsWithEventDetails(int userId) {
        return ticketDao.getTicketsWithEventDetailsByUserId(userId);
    }
    
    /**
     * Get all tickets for a specific event
     */
    public List<Ticket> getEventTickets(int eventId) {
        return ticketDao.getTicketsByEventId(eventId);
    }
    
    /**
     * Get ticket by ID
     */
    public Ticket getTicketById(int ticketId) {
        return ticketDao.getTicketById(ticketId);
    }
    
    /**
     * Get total number of tickets sold for an event
     */
    public int getTicketCount(int eventId) {
        List<Ticket> tickets = ticketDao.getTicketsByEventId(eventId);
        return tickets.size();
    }
    
    /**
     * Get total revenue for an event
     */
    public double getEventRevenue(int eventId) {
        List<Ticket> tickets = ticketDao.getTicketsByEventId(eventId);
        return tickets.stream().mapToDouble(Ticket::getPricePaid).sum();
    }
}
