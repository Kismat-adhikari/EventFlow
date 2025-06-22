package eventflow.controllers;

import eventflow.dao.EventDao;
import eventflow.dao.EventDao.EventWithUser;
import eventflow.dao.UserDAO;
import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.SignupForm;
import eventflow.views.WalletForm;
import java.util.List;

public class DashboardController {

    public static void goToDashboard(User user) {
        Dashboard dashboardPage = new Dashboard(user);

        try {
            EventDao eventDao = new EventDao();
            List<EventWithUser> events = eventDao.getAllEventsWithUploader();

            // Pass event list to Dashboard UI to display
            dashboardPage.loadEvents(events);
            System.out.println("Number of events fetched: " + events.size());

            dashboardPage.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage());
        }
    }

    public static void goToCreate(User user) {
        Create createPage = new Create(user);
        createPage.setVisible(true);
    }

    public static void goToMyEvents(User user) {
        MyEvents myEventsPage = new MyEvents(user);
        myEventsPage.setVisible(true);
    }

    public static void goToMyTickets(User user) {
        MyTickets myTicketsPage = new MyTickets(user);
        myTicketsPage.setVisible(true);
    }

    public static void goToProfile(User user) {
        ProfileForm profilePage = new ProfileForm(user);
        profilePage.setVisible(true);
    }

    public static void goToWallet(User user) {
        WalletForm walletPage = new WalletForm(user);
        walletPage.setVisible(true);
    }

    public static void goToSignup() {
        SignupForm signupPage = new SignupForm();
        signupPage.setVisible(true);
    }

    public static boolean payForEvent(int buyerId, int eventId, double price) {
        EventDao eventDao = new EventDao();
        UserDAO userDao = new UserDAO();

        // Get creator ID of the event
        int creatorId = eventDao.getCreatorIdByEventId(eventId);
        if (creatorId == -1) {
            return false; // event or creator not found
        }

        // Check if buyer has sufficient balance
        double buyerBalance = userDao.getBalanceById(buyerId);
        if (buyerBalance < price) {
            return false; // insufficient funds
        }

        // Process payment: deduct buyer, credit creator
        boolean paymentSuccess = userDao.processPayment(buyerId, creatorId, price);

        if (paymentSuccess) {
            // Create ticket after successful payment
            EventController eventController = new EventController();
            boolean ticketCreated = eventController.purchaseTicket(buyerId, eventId, price);

            if (ticketCreated) {
                System.out.println("Payment successful - Rs. " + price + " transferred from user " + buyerId
                        + " to user " + creatorId);
                System.out.println("Ticket created for user " + buyerId + " for event " + eventId);
            } else {
                System.out.println("Payment successful but ticket creation failed");
            }
        }

        return paymentSuccess;
    }

    public static boolean deleteEvent(int eventId) {
        try {
            EventDao eventDao = new EventDao();
            boolean deleteSuccess = eventDao.deleteEvent(eventId);

            if (deleteSuccess) {
                System.out.println("Event with ID " + eventId + " deleted successfully with automatic refunds");
            } else {
                System.out.println("Failed to delete event with ID " + eventId);
            }

            return deleteSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get refund information for an event
    public static EventDao.RefundInfo getRefundInfo(int eventId) {
        try {
            EventDao eventDao = new EventDao();
            return eventDao.getRefundInfo(eventId);
        } catch (Exception e) {
            e.printStackTrace();
            return new EventDao.RefundInfo(0, 0.0);
        }
    }

    // Get ticket availability for an event
    public static EventDao.TicketAvailability getTicketAvailability(int eventId) {
        try {
            EventDao eventDao = new EventDao();
            return eventDao.getTicketAvailability(eventId);
        } catch (Exception e) {
            e.printStackTrace();
            return new EventDao.TicketAvailability(0, 0, 0, true);
        }
    }

    // Search functionality methods for MVC architecture
    
    /**
     * Search events by title (case-insensitive)
     * @param allEvents List of all available events
     * @param searchQuery Search query string
     * @return List of events matching the search query
     */
    public static List<EventWithUser> searchEventsByTitle(List<EventWithUser> allEvents, String searchQuery) {
        if (allEvents == null || searchQuery == null) {
            return allEvents != null ? allEvents : new java.util.ArrayList<>();
        }
        
        // Normalize search query
        String normalizedQuery = searchQuery.trim();
        
        // If search is empty, return all events
        if (normalizedQuery.isEmpty()) {
            return allEvents;
        }
        
        // Filter events by title (case-insensitive)
        List<EventWithUser> filteredEvents = new java.util.ArrayList<>();
        for (EventWithUser event : allEvents) {
            if (event.getEventTitle().toLowerCase().contains(normalizedQuery.toLowerCase())) {
                filteredEvents.add(event);
            }
        }
        
        return filteredEvents;
    